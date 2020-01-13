package internetshop.service;

import internetshop.model.Item;
import internetshop.model.Order;
import internetshop.model.User;

import java.util.List;

public interface OrderService {
    Order getOrder(Long idOrder);

    Order update(Order order);

    boolean deleteOrder(Order order);

    Order completeOrder(List<Item> items, Long userId);

    List<Order> getUserOrders(User user);
}
