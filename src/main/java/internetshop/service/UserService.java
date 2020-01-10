package internetshop.service;

import internetshop.model.User;

import java.util.List;

public interface UserService {
    User create(User user);

    User get(Long idUser);

    User update(User user);

    boolean delete(User user);

    List<User> getAll();
}
