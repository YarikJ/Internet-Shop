package internetshop.dao.impl;

import internetshop.dao.UserDao;
import internetshop.lib.Dao;
import internetshop.model.User;
import internetshop.storage.Storage;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Dao
public class UserDaoImpl implements UserDao {
    private static Long userId = 1L;

    @Override
    public User create(User user) {
        user.setUserId(++userId);
        Storage.users.add(user);
        return user;
    }

    @Override
    public Optional<User> get(Long idUser) {
        return Storage.users.stream()
                .filter(u -> u.getUserId().equals(idUser)).findFirst();
    }

    @Override
    public User update(User user) {
        User userToUpdate = get(user.getUserId())
                .orElseThrow(() -> new NoSuchElementException("Can't find user to update"));
        userToUpdate.setName(user.getName());
        userToUpdate.setPassword(user.getPassword());
        return userToUpdate;
    }

    @Override
    public boolean delete(User user) {
        return Storage.users.remove(user);
    }

    @Override
    public List<User> getAll() {
        return Storage.users;
    }

    @Override
    public Optional<User> login(String name) {
        return Storage.users.stream()
                .filter(u -> u.getName().equals(name)).findFirst();
    }

    @Override
    public Optional<User> getByToken(String token) {
        return Storage.users.stream().filter(u -> u.getToken().equals(token)).findFirst();
    }
}
