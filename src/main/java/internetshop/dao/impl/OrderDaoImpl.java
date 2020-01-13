package internetshop.dao.impl;

import internetshop.dao.OrderDao;
import internetshop.lib.Dao;
import internetshop.model.Order;
import internetshop.model.User;
import internetshop.storage.Storage;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Dao
public class OrderDaoImpl implements OrderDao {
    private static Long orderId = 0L;

    @Override
    public Order create(Order order) {
        order.setOrderId(++orderId);
        Storage.orders.add(order);
        return order;
    }

    @Override
    public Optional<Order> get(Long idOrder) {
        return Storage.orders.stream().filter(o -> o.getOrderId().equals(idOrder)).findFirst();
    }

    @Override
    public Order update(Order order) {
        Order orderToUpdate = get(order.getOrderId())
                .orElseThrow(() -> new NoSuchElementException("Can't find item to update"));
        orderToUpdate.setUser(order.getUser());
        orderToUpdate.setItems(order.getItems());
        return orderToUpdate;
    }

    @Override
    public boolean delete(Order order) {
        return Storage.orders.remove(order);
    }

    @Override
    public List<Order> getUserOrders(User user) {
        return Storage.orders
                .stream()
                .filter(o -> o.getUser()
                        .equals(user))
                .collect(Collectors.toList());
    }
}
