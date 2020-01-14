package internetshop.service;

import internetshop.exceptions.AuthorizationException;
import internetshop.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User create(User user);

    User get(Long idUser);

    User update(User user);

    boolean delete(Long userId);

    List<User> getAll();

    User logIn(String name, String pass) throws AuthorizationException;

    Optional<User> getByToken(String token);
}
