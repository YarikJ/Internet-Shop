package internetshop.dao;

import internetshop.model.Order;
import internetshop.model.User;

import java.util.List;
import java.util.Optional;

public interface OrderDao {
    Order create(Order order);

    Optional<Order> get(Long idOrder);

    public Order update(Order order);

    boolean delete(Order order);

    List<Order> getUserOrders(User user);
}
