package internetshop.service;

import internetshop.model.Item;
import internetshop.model.Order;
import internetshop.model.User;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Optional<Order> getOrder(Long idOrder);

    Order update(Order order);

    boolean deleteOrder(Order order);

    Order completeOrder(List<Item> items, User user);

    List<Order> getUserOrders(User user);
}
