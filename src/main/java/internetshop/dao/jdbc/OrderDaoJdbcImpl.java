package internetshop.dao.jdbc;

import internetshop.dao.ItemDao;
import internetshop.dao.OrderDao;
import internetshop.dao.UserDao;
import internetshop.exceptions.DataProcessingException;
import internetshop.lib.Dao;
import internetshop.lib.Inject;
import internetshop.model.Item;
import internetshop.model.Order;
import internetshop.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class OrderDaoJdbcImpl extends AbstractDao<Order> implements OrderDao {
    @Inject
    private static UserDao userDao;
    @Inject
    private static ItemDao itemDao;

    public OrderDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Order create(Order order) throws DataProcessingException {

        String query = "INSERT INTO orders(user_id)  VALUES (?);";
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
            throw new DataProcessingException("Couldn't create new order for user"
                    + order.getUser().getUserId(), e);
        }

        insertItemsToOrder(order, query2);
        return order;
    }

    @Override
    public Optional<Order> get(Long idOrder) throws DataProcessingException {
        String query = "SELECT user_id, orders.order_id, item_id FROM orders"
                + " LEFT JOIN orders_items ON orders.order_id = orders_items.order_id"
                + " WHERE orders.order_id = ?;";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, idOrder);

            ResultSet result = stmt.executeQuery();
            Order order = new Order();

            if (result.next()) {
                order.setOrderId(idOrder);
                List<Item> items = new ArrayList<>();
                order.setItems(items);

                long userId = result.getLong("user_id");
                order.setUser(userDao.get(userId).orElseThrow(()
                        -> new DataProcessingException("Can't get user with id" + userId)));
                long itemId = result.getLong("item_id");

                if (itemId > 0) {
                    items.add(itemDao.get(itemId).orElseThrow(()
                            -> new DataProcessingException("Can't get user with id" + itemId)));
                }

                while (result.next()) {
                    long itemId1 = result.getLong("item_id");
                    items.add(itemDao.get(itemId1).orElseThrow(()
                            -> new DataProcessingException("Can't get item with id" + itemId1)));
                }
                return Optional.of(order);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Could not get from DB order with id " + idOrder, e);
        }
        return Optional.empty();
    }

    @Override
    public Order update(Order order) throws DataProcessingException {
        String query = "DELETE FROM orders_items WHERE order_id=?;";
        String query2 = "INSERT INTO orders_items (order_id, item_id) VALUES (?, ?);";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, order.getOrderId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't delete items updating order with id"
                    + order.getOrderId(), e);
        }
        insertItemsToOrder(order, query2);
        return order;
    }

    @Override
    public boolean delete(Order order) throws DataProcessingException {
        String query = "DELETE FROM orders WHERE order_id=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, order.getOrderId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't delete order with id"
                    + order.getOrderId(), e);
        }
    }

    @Override
    public List<Order> getUserOrders(User user) throws DataProcessingException {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT order_id FROM orders WHERE user_id=?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, user.getUserId());

            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                Long orderId = result.getLong("order_id");
                orders.add(get(orderId).orElseThrow(()
                        -> new DataProcessingException("Can't get order with id" + orderId)));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get all orders of user"
                    + user.getUserId(), e);
        }
        return orders;
    }

    private void insertItemsToOrder(Order order, String query) throws DataProcessingException {
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            List<Item> items = order.getItems();
            stmt.setLong(1, order.getOrderId());

            for (Item item : items) {
                stmt.setLong(2, item.getIdItem());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't update items in order id"
                    + order.getOrderId(), e);
        }
    }
}
