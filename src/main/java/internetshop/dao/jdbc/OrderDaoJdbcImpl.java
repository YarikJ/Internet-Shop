package internetshop.dao.jdbc;

import internetshop.dao.ItemDao;
import internetshop.dao.OrderDao;
import internetshop.dao.UserDao;
import internetshop.lib.Dao;
import internetshop.lib.Inject;
import internetshop.model.Item;
import internetshop.model.Order;
import internetshop.model.User;
import org.apache.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class OrderDaoJdbcImpl extends AbstractDao<Order> implements OrderDao {
    @Inject
    private static UserDao userDao;
    @Inject
    private static ItemDao itemDao;
    private static Logger logger = Logger.getLogger(OrderDaoJdbcImpl.class);

    public OrderDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Order create(Order order) {

        String query = "INSERT INTO orders(user_id)"
                + " VALUES (?);";
        String query2 = "INSERT INTO orders_items (order_id, item_id) VALUES (?, ?);";

        try (PreparedStatement stmt = connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, order.getUser().getUserId());

            stmt.executeUpdate();

            ResultSet resultSet = stmt.getGeneratedKeys();
            if (resultSet.next()) {
                order.setOrderId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            logger.error("Couldn't create new order for user"
                    + order.getUser().getUserId(), e);
            throw new RuntimeException();
        }

        try (PreparedStatement stmt = connection.prepareStatement(query2)) {
            stmt.setLong(1, order.getOrderId());
            for (int i = 0; i < order.getItems().size(); i++) {
                stmt.setLong(2, order.getItems().get(i).getIdItem());

                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            logger.error("Couldn't add items to the order in DB for order id"
                    + order.getOrderId(), e);
            throw new RuntimeException();
        }
        return order;
    }

    @Override
    public Optional<Order> get(Long idOrder) {
        String query = "SELECT user_id, orders.order_id, item_id FROM orders"
                + " INNER JOIN orders_items ON orders.order_id = orders_items.order_id"
                + " WHERE orders.order_id = ?;";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, idOrder);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                Order order = new Order();
                order.setOrderId(idOrder);
                List<Item> items = new ArrayList<>();
                order.setItems(items);

                order.setUser(userDao.get(result.getLong("user_id")).get()); //TODO: get on optional without check
                items.add(itemDao.get(result.getLong("item_id")).get());

                while (result.next()) {
                    items.add(itemDao.get(result.getLong("item_id")).get());
                }
                return Optional.of(order);
            }
        } catch (SQLException e) {
            logger.error("Could not get from DB order with id " + idOrder, e);
            throw new RuntimeException();
        }
        return Optional.empty();
    }

    @Override
    public Order update(Order order) {
        String query = "DELETE FROM orders_items WHERE order_id=?;";
        String query2 = "INSERT INTO orders_items (order_id, item_id) VALUES (?, ?);";
        int affected;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, order.getOrderId());

            affected = stmt.executeUpdate();

        } catch (SQLException e) {
            logger.error("Couldn't delete items from order updating order with id"
                    + order.getOrderId(), e);
            throw new RuntimeException();
        }

        if (affected > 0) {
            try (PreparedStatement stmt = connection.prepareStatement(query2)) {
                List<Item> items = order.getItems();

                for (int i = 0; i < items.size(); i++) {
                    stmt.setLong(1, order.getOrderId());
                    stmt.setLong(2, items.get(i).getIdItem());
                    stmt.executeUpdate();
                }
            } catch (SQLException e) {
                logger.error("Couldn't update items in the order in DB for order id"
                        + order.getOrderId(), e);
                throw new RuntimeException();
            }
        }
        return order;
    }

    @Override
    public boolean delete(Order order) {
        String query = "DELETE FROM orders WHERE order_id=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, order.getOrderId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            logger.error("Couldn't delete order with id" + order.getOrderId(), e);
            throw new RuntimeException();
        }
    }

    @Override
    public List<Order> getUserOrders(User user) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT order_id FROM orders WHERE user_id=?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, user.getUserId());

            ResultSet result = stmt.executeQuery();
            while(result.next()) {
                Long orderId = result.getLong("order_id");
                orders.add(get(orderId).get());
            }

        } catch (SQLException e) {
            logger.error("Couldn't get all orders of user" + user.getUserId(), e);
            throw new RuntimeException();
        }
        return orders;
    }
}
