package internetshop.dao;

import internetshop.exceptions.DataProcessingException;
import internetshop.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    User create(User user) throws DataProcessingException;

    Optional<User> get(Long idUser) throws DataProcessingException;

    User update(User user) throws DataProcessingException;

    boolean delete(User user) throws DataProcessingException;

    List<User> getAll() throws DataProcessingException;

    Optional<User> login(String name) throws DataProcessingException;

    Optional<User> getByToken(String token) throws DataProcessingException;
}
