package internetshop.service;

import internetshop.exceptions.DataProcessingException;
import internetshop.model.Item;
import internetshop.model.Order;
import internetshop.model.User;

import java.util.List;

public interface OrderService {
    Order getOrder(Long idOrder) throws DataProcessingException;

    Order update(Order order) throws DataProcessingException;

    boolean deleteOrder(Order order) throws DataProcessingException;

    Order completeOrder(List<Item> items, Long userId) throws DataProcessingException;

    List<Order> getUserOrders(User user) throws DataProcessingException;
}
