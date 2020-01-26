package internetshop.dao.jdbc;

import internetshop.dao.UserDao;
import internetshop.lib.Dao;
import internetshop.model.Role;
import internetshop.model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.apache.log4j.Logger;

@Dao
public class UserDaoJdbcImpl extends AbstractDao<User> implements UserDao {
    private static Logger logger = Logger.getLogger(ItemDaoJdbcImpl.class);
    private static final String DB_NAME = "internet_shop.users";

    public UserDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public User create(User user) {
        user.setToken(getToken());
        String query = "INSERT INTO users(user_name, user_pass, user_token)"
                + " VALUES (?, ?, ?);";
        String query2 = "INSERT INTO users_roles (user_id, role_id) VALUES (?, 2);";

        try (PreparedStatement stmt = connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getToken());

            stmt.executeUpdate();

            ResultSet resultSet = stmt.getGeneratedKeys();
            if (resultSet.next()) {
                user.setUserId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            logger.error("Couldn't create new user", e);
            throw new RuntimeException();
        }

        try (PreparedStatement stmt = connection.prepareStatement(query2)) {
            stmt.setLong(1, user.getUserId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Couldn't write role to the database", e);
            throw new RuntimeException();
        }
        return user;
    }

    @Override
    public Optional<User> get(Long idUser) {
        String query = "SELECT users.*, role_name FROM users INNER JOIN users_roles"
                + " ON users.user_id = users_roles.user_id INNER JOIN roles"
                + " ON roles.role_id = users_roles.role_id WHERE users.user_id=?;";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, idUser);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                User user = new User();

                user.setUserId(result.getLong("user_id"));
                user.setName(result.getString("user_name"));
                user.setPassword(result.getString("user_pass"));
                user.setToken(result.getString("user_token"));
                user.addRole(Role.of(result.getString("role_name")));

                while (result.next()) {
                    Role additionalRole = Role.of(result.getString("role_name"));
                    user.addRole(additionalRole);
                }
                return Optional.of(user);
            }
        } catch (SQLException e) {
            logger.error("Could not get from DB user with id " + idUser, e);
            throw new RuntimeException();
        }
        return Optional.empty();
    }


    @Override
    public User update(User user) {
        String query = "UPDATE users SET user_name=?, user_pass=? where user_id=?;";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getPassword());
            stmt.setLong(3, user.getUserId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            logger.error("Could not update in DB user with id " + user.getUserId(), e);
            throw new RuntimeException();
        }
        return user;
    }

    @Override
    public boolean delete(User user) {
        String query = "DELETE FROM users WHERE user_id=?;";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, user.getUserId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            logger.error("Could not delete user with id" + user.getUserId(), e);
            throw new RuntimeException();
        }
    }

    @Override
    public List<User> getAll() {
        String query = "SELECT users.user_id, user_name, role_name FROM users"
                + " INNER JOIN users_roles ON users.user_id = users_roles.user_id"
                + " INNER JOIN roles ON roles.role_id = users_roles.role_id;";

        List<User> users = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                User user = new User();

                user.setUserId(result.getLong("user_id"));
                user.setName(result.getString("user_name"));
                user.addRole(Role.of(result.getString("role_name")));
                users.add(user);
            }
            for (int i = 0; i + 1 < users.size(); i++) {
                if (users.get(i+1).getUserId().equals(users.get(i).getUserId())) {
                    users.get(i).addRole((Role)users.get(i+1).getRoles().toArray()[0]);
                    users.remove(i+1);
                }
            }
        } catch (SQLException e) {
            logger.error("Could not get list of all users from DB", e);
            throw new RuntimeException();
        }
        return users;
    }

    @Override
    public Optional<User> login(String name) {
        String query = "SELECT * FROM users WHERE users.user_name=?;";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                User user = new User();

                user.setUserId(result.getLong("user_id"));
                user.setName(result.getString("user_name"));
                user.setPassword(result.getString("user_pass"));
                user.setToken(result.getString("user_token"));
                return Optional.of(user);
            }
        } catch (SQLException e) {
            logger.error("Could not get from DB user with name " + name, e);
            throw new RuntimeException();
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> getByToken(String token) {
        String query = "SELECT * FROM users WHERE users.user_token=?;";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, token);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                User user = new User();

                user.setUserId(result.getLong("user_id"));
                user.setName(result.getString("user_name"));
                user.setPassword(result.getString("user_pass"));
                user.setToken(result.getString("user_token"));
                return Optional.of(user);
            }
        } catch (SQLException e) {
            logger.error("Could not get from DB user with token " + token, e);
            throw new RuntimeException();
        }
        return Optional.empty();
    }

    private String getToken() {
        return UUID.randomUUID().toString();
    }

}
