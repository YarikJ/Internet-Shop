package internetshop.dao.jdbc;

import internetshop.dao.ItemDao;
import internetshop.lib.Dao;
import internetshop.model.Item;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.log4j.Logger;

@Dao
public class ItemDaoJdbcImpl extends AbstractDao<Item> implements ItemDao {
    private static Logger logger = Logger.getLogger(ItemDaoJdbcImpl.class);
    private static final String DB_NAME = "internet_shop.items";

    public ItemDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Item create(Item item) {
        String query = String.format("INSERT INTO %s(name, price) VALUES (?, ?);", DB_NAME);

        try (PreparedStatement stmt = connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, item.getName());
            stmt.setDouble(2, item.getPrice());
            stmt.executeUpdate();
            ResultSet resultSet = stmt.getGeneratedKeys();
            while (resultSet.next()) {
                item.setIdItem(resultSet.getLong(1));
                return item;
            }
        } catch (SQLException e) {
            logger.warn("Can't write item to BD" + item.getName(), e);
        }
        return null;
    }

    @Override
    public Optional<Item> get(long id) {
        String query = "SELECT * FROM items WHERE item_id=?;";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                long itemId = rs.getLong("item_id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                Item item = new Item(itemId, name, price);
                return Optional.of(item);
            }
        } catch (SQLException e) {
            logger.warn("Can't get item by id=" + id);
        }
        return Optional.empty();
    }

    @Override
    public Item update(Item item) {
        String query = String.format("UPDATE %s SET name=?, price=? WHERE item_id=?;", DB_NAME);

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, item.getName());
            stmt.setDouble(2, item.getPrice());
            stmt.setLong(3, item.getIdItem());
            stmt.executeUpdate();
            return item;
        } catch (SQLException e) {
            logger.warn("Can't update item in BD with id" + item.getIdItem(), e);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        String query = String.format("DELETE FROM %s WHERE item_id=?;",  DB_NAME);

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.warn("Can't delete from BD item with id " + id, e);
        }
    }

    @Override
    public void delete(Item item) {
        delete(item.getIdItem());
    }

    @Override
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        String query = String.format("SELECT * FROM %s;", DB_NAME);

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                long itemId = rs.getLong("item_id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                Item item = new Item(itemId, name, price);
                items.add(item);
            }
            return items;
        } catch (SQLException e) {
            logger.warn("Can't get items", e);
        }
        return null;
    }
}
