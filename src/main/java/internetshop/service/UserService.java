package internetshop.service;

import internetshop.model.User;

public interface UserService {
    User create(User user);

    User get(Long idUser);

    User update(User user);

    boolean delete(User user);
}
