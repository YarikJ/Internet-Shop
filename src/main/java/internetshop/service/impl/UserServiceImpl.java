package internetshop.service.impl;

import internetshop.dao.UserDao;
import internetshop.exceptions.AuthorizationException;
import internetshop.exceptions.DataProcessingException;
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
    public User create(User user) throws DataProcessingException {
        user.setToken(getToken());
        return userDao.create(user);
    }

    @Override
    public User get(Long idUser) throws DataProcessingException {
        return userDao.get(idUser).orElseThrow(()
                -> new DataProcessingException("There is no user with id" + idUser));
    }

    @Override
    public User update(User user) throws DataProcessingException {
        return userDao.update(user);
    }

    @Override
    public boolean delete(Long userId) throws DataProcessingException {
        return userDao.delete(get(userId));
    }

    @Override
    public List<User> getAll() throws DataProcessingException {
        return userDao.getAll();
    }

    @Override
    public User login(String name, String pass) throws AuthorizationException,
            DataProcessingException {
        Optional<User> user = userDao.login(name);
        if (user.isEmpty() || !user.get().getPassword().equals(pass)) {
            throw new AuthorizationException("incorrect user name or password");
        }
        return user.get();
    }

    @Override
    public Optional<User> getByToken(String token) throws DataProcessingException {
        return userDao.getByToken(token);
    }

    private String getToken() {
        return UUID.randomUUID().toString();
    }
}
