package internetshop.dao;

import internetshop.model.Order;

import java.util.Optional;

public interface OrderDao {
    Order create(Order order);

    Optional<Order> get(Long idOrder);

    public Order update(Order order);

    boolean delete(Order order);
}
