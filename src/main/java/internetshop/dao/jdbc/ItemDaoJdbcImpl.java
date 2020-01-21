package internetshop.dao.jdbc;

import internetshop.dao.ItemDao;
import internetshop.lib.Dao;
import internetshop.model.Item;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import org.apache.log4j.Logger;

@Dao
public class ItemDaoJdbcImpl extends AbstractDao<Item> implements ItemDao {
    private static Logger logger = Logger.getLogger(ItemDaoJdbcImpl.class);
    private static String dbName = "internet_shop";

    public ItemDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Item create(Item item) {
        String query = String.format(Locale.ENGLISH,
                "INSERT INTO %s.items(name, price) VALUES ('%s', %.2f);",
                dbName, item.getName(), item.getPrice());

        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(query);
            return item;
        } catch (SQLException e) {
            logger.warn("Can't write to BD", e);
        }
        return null;
    }

    @Override
    public Optional<Item> get(long id) {
        String query = String.format("SELECT * FROM %s.items WHERE item_id=%d;", dbName, id);

        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
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
        String query = String.format(Locale.ENGLISH,
                "UPDATE %s.items SET name='%s', price=%.2f WHERE item_id=%d;",
                dbName, item.getName(), item.getPrice(), item.getIdItem());

        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(query);
            return item;
        } catch (SQLException e) {
            logger.warn("Can't write to BD", e);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        String query = String.format("DELETE FROM %s.items WHERE item_id=%d;", dbName, id);

        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            logger.warn("Can't write to BD", e);
        }
    }

    @Override
    public void delete(Item item) {
        delete(item.getIdItem());
    }

    @Override
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        String query = String.format("SELECT * FROM %s.items;", dbName);

        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
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
