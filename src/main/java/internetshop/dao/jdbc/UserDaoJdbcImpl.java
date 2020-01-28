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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Dao
public class UserDaoJdbcImpl extends AbstractDao<User> implements UserDao {

    public UserDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public User create(User user) throws DataProcessingException {
        String queryInsert = "INSERT INTO users(user_name, user_pass, user_token) "
                + "VALUES (?, ?, ?);";
        String queryInsertRoles = "INSERT INTO users_roles (user_id, role_id) VALUES (?, 2);";

        try (PreparedStatement stmt = connection.prepareStatement(queryInsert,
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

        try (PreparedStatement stmt = connection.prepareStatement(queryInsertRoles)) {
            stmt.setLong(1, user.getUserId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't write role to the database", e);
        }
        return user;
    }

    @Override
    public Optional<User> get(Long idUser) throws DataProcessingException {
        String getUserQuery = "SELECT * FROM users INNER JOIN users_roles "
                + "USING (user_id) INNER JOIN roles USING (role_id) WHERE users.user_id=?;";

        try (PreparedStatement stmt = connection.prepareStatement(getUserQuery)) {
            stmt.setLong(1, idUser);

            return getUser(stmt);
        } catch (SQLException e) {
            throw new DataProcessingException("Could not get from DB user with id " + idUser, e);
        }
    }

    @Override
    public User update(User user) throws DataProcessingException {
        String updateUserQuery = "UPDATE users SET user_name=?, user_pass=? where user_id=?;";

        try (PreparedStatement stmt = connection.prepareStatement(updateUserQuery)) {
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
        String deleteUserQuery = "DELETE FROM users WHERE user_id=?;";

        try (PreparedStatement stmt = connection.prepareStatement(deleteUserQuery)) {
            stmt.setLong(1, user.getUserId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DataProcessingException("Could not delete user with id"
                    + user.getUserId(), e);
        }
    }

    @Override
    public List<User> getAll() throws DataProcessingException {
        String getAllUsersQuery2 = "SELECT *, GROUP_CONCAT(role_name SEPARATOR ' ') "
                + "as role_names FROM users INNER JOIN users_roles USING (user_id) "
                + "INNER JOIN roles using (role_id) group by user_id;";

        List<User> users = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(getAllUsersQuery2)) {

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                User user = new User();

                user.setUserId(result.getLong("user_id"));
                user.setName(result.getString("user_name"));
                String roles = result.getString("role_names");
                user.addRoles(Arrays.asList(roles.split(" ")));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Could not get list of all users from DB", e);
        }
        return users;
    }

    @Override
    public Optional<User> login(String name) throws DataProcessingException {
        String getUserByLoginQuery = "SELECT * FROM users INNER JOIN users_roles "
                + "USING (user_id) INNER JOIN roles USING (role_id) WHERE users.user_name=?;";

        try (PreparedStatement stmt = connection.prepareStatement(getUserByLoginQuery)) {
            stmt.setString(1, name);
            return getUser(stmt);
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get user with entered credentials", e);
        }
    }

    @Override
    public Optional<User> getByToken(String token) throws DataProcessingException {
        String getUserByTokenQuery = "SELECT * FROM users INNER JOIN users_roles "
                + "USING (user_id) INNER JOIN roles USING (role_id) WHERE users.user_token=?;";

        try (PreparedStatement stmt = connection.prepareStatement(getUserByTokenQuery)) {
            stmt.setString(1, token);
            return getUser(stmt);
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get user with entered credentials", e);
        }
    }

    private Optional<User> getUser(PreparedStatement statement) throws SQLException {

        ResultSet result = statement.executeQuery();

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
        return Optional.empty();
    }

}
