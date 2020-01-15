package internetshop.dao;

import internetshop.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    User create(User user);

    Optional<User> get(Long idUser);

    User update(User user);

    boolean delete(User user);

    List<User> getAll();

    Optional<User> login(String name);

    Optional<User> getByToken(String token);
}
