package internetshop.dao.jdbc;

import internetshop.dao.UserDao;
import internetshop.exceptions.DataProcessingException;
import internetshop.lib.Dao;
import internetshop.model.Role;
import internetshop.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dao
public class UserDaoJdbcImpl extends AbstractDao<User> implements UserDao {

    public UserDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public User create(User user) throws DataProcessingException {
        user.setToken(getToken());
        String query = "INSERT INTO users(user_name, user_pass, user_token) VALUES (?, ?, ?);";
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
            throw new DataProcessingException("Couldn't create new user", e);
        }

        try (PreparedStatement stmt = connection.prepareStatement(query2)) {
            stmt.setLong(1, user.getUserId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't write role to the database", e);
        }
        return user;
    }

    @Override
    public Optional<User> get(Long idUser) throws DataProcessingException {
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
            throw new DataProcessingException("Could not get from DB user with id " + idUser, e);
        }
        return Optional.empty();
    }

    @Override
    public User update(User user) throws DataProcessingException {
        String query = "UPDATE users SET user_name=?, user_pass=? where user_id=?;";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getPassword());
            stmt.setLong(3, user.getUserId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DataProcessingException("Could not update in DB user with id "
                    + user.getUserId(), e);
        }
        return user;
    }

    @Override
    public boolean delete(User user) throws DataProcessingException {
        String query = "DELETE FROM users WHERE user_id=?;";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, user.getUserId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DataProcessingException("Could not delete user with id"
                    + user.getUserId(), e);
        }
    }

    @Override
    public List<User> getAll() throws DataProcessingException {
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
                if (users.get(i + 1).getUserId().equals(users.get(i).getUserId())) {
                    users.get(i).addRole((Role) users.get(i + 1).getRoles().toArray()[0]);
                    users.remove(i + 1);
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Could not get list of all users from DB", e);
        }
        return users;
    }

    @Override
    public Optional<User> login(String name) throws DataProcessingException {
        String query = "SELECT * FROM users WHERE users.user_name=?;";

        return getUser(query, name);
    }

    @Override
    public Optional<User> getByToken(String token) throws DataProcessingException {
        String query = "SELECT * FROM users WHERE users.user_token=?;";

        return getUser(query, token);
    }

    private String getToken() {
        return UUID.randomUUID().toString();
    }

    private Optional<User> getUser(String query, String searchWord)
            throws DataProcessingException {

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, searchWord);

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
            throw new DataProcessingException("Couldn't get user with entered credentials", e);
        }
        return Optional.empty();
    }

}
