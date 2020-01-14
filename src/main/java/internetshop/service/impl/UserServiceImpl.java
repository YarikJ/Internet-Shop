package internetshop.service.impl;

import internetshop.dao.UserDao;
import internetshop.exceptions.AuthorizationException;
import internetshop.lib.Inject;
import internetshop.lib.Service;
import internetshop.model.User;
import internetshop.service.UserService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Inject
    private static UserDao userDao;

    @Override
    public User create(User user) {
        user.setToken(getToken());
        return userDao.create(user);
    }

    @Override
    public User get(Long idUser) {
        return userDao.get(idUser).orElseThrow(()
                -> new NoSuchElementException("There is no user with id" + idUser));
    }

    @Override
    public User update(User user) {
        return userDao.update(user);
    }

    @Override
    public boolean delete(Long userId) {
        return userDao.delete(get(userId));
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public User logIn(String name, String pass) throws AuthorizationException {
        Optional<User> user = userDao.logIn(name);
        if (user.isEmpty() || !user.get().getPassword().equals(pass)) {
            throw new AuthorizationException("incorrect user name or password");
        }
        return user.get();
    }

    @Override
    public Optional<User> getByToken(String token) {
        return userDao.getByToken(token);
    }

    private String getToken() {
        return UUID.randomUUID().toString();
    }
}
