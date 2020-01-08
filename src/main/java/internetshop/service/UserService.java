package internetshop.service;

import internetshop.model.User;

import java.util.Optional;

public interface UserService {
    User create(User user);

    Optional<User> get(Long idUser);

    User update(User user);

    boolean delete(User user);
}
