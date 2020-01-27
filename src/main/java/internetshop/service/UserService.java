package internetshop.service;

import internetshop.exceptions.AuthorizationException;
import internetshop.exceptions.DataProcessingException;
import internetshop.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User create(User user) throws DataProcessingException;

    User get(Long idUser) throws DataProcessingException;

    User update(User user) throws DataProcessingException;

    boolean delete(Long userId) throws DataProcessingException;

    List<User> getAll() throws DataProcessingException;

    User login(String name, String pass) throws AuthorizationException, DataProcessingException;

    Optional<User> getByToken(String token) throws DataProcessingException;
}
