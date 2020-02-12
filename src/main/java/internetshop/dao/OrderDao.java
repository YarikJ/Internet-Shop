package internetshop.dao;

import internetshop.exceptions.DataProcessingException;
import internetshop.model.Order;
import internetshop.model.User;

import java.util.List;
import java.util.Optional;

public interface OrderDao {
    Order create(Order order) throws DataProcessingException;

    Optional<Order> get(Long idOrder) throws DataProcessingException;

    Order update(Order order) throws DataProcessingException;

    boolean delete(Order order) throws DataProcessingException;

    List<Order> getUserOrders(User user) throws DataProcessingException;
}
