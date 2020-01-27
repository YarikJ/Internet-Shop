package internetshop.dao.jdbc;

import internetshop.dao.ItemDao;
import internetshop.exceptions.DataProcessingException;
import internetshop.lib.Dao;
import internetshop.model.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class ItemDaoJdbcImpl extends AbstractDao<Item> implements ItemDao {

    public ItemDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Item create(Item item) throws DataProcessingException {
        String query = "INSERT INTO items(name, price) VALUES (?, ?);";

        try (PreparedStatement stmt = connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, item.getName());
            stmt.setDouble(2, item.getPrice());
            stmt.executeUpdate();
            ResultSet resultSet = stmt.getGeneratedKeys();
            if (resultSet.next()) {
                item.setIdItem(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't write item to BD" + item.getName(), e);
        }
        return item;
    }

    @Override
    public Optional<Item> get(long id) throws DataProcessingException {
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
            throw new DataProcessingException("Can't get item by id=" + id, e);
        }
        return Optional.empty();
    }

    @Override
    public Item update(Item item) throws DataProcessingException {
        String query = "UPDATE items SET name=?, price=? WHERE item_id=?;";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, item.getName());
            stmt.setDouble(2, item.getPrice());
            stmt.setLong(3, item.getIdItem());
            stmt.executeUpdate();
            return item;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update item in BD with id"
                    + item.getIdItem(), e);
        }
    }

    @Override
    public void delete(Long id) throws DataProcessingException {
        String query = "DELETE FROM items WHERE item_id=?;";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete from BD item with id " + id, e);
        }
    }

    @Override
    public void delete(Item item) throws DataProcessingException{
        delete(item.getIdItem());
    }

    @Override
    public List<Item> getAllItems() throws DataProcessingException {
        List<Item> items = new ArrayList<>();
        String query = "SELECT * FROM items;";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                long itemId = rs.getLong("item_id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                Item item = new Item(itemId, name, price);
                items.add(item);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get items", e);
        }
        return items;
    }
}
