package internetshop.dao.impl;

import internetshop.dao.OrderDao;
import internetshop.lib.Dao;
import internetshop.model.Order;
import internetshop.storage.Storage;

import java.util.NoSuchElementException;
import java.util.Optional;

@Dao
public class OrderDaoImpl implements OrderDao {
    @Override
    public Order create(Order order) {
        Storage.addToOrders(order);
        return order;
    }

    @Override
    public Optional<Order> get(Long idOrder) {
        return Storage.orders.stream().filter(o -> o.getIdOrder().equals(idOrder)).findFirst();
    }

    @Override
    public Order update(Order order) {
        Order orderToUpdate = get(order.getIdOrder())
                .orElseThrow(() -> new NoSuchElementException("Can't find item to update"));
        orderToUpdate.setUser(order.getUser());
        orderToUpdate.setItems(order.getItems());
        return orderToUpdate;
    }

    @Override
    public boolean delete(Order order) {
        return Storage.orders.remove(order);
    }
}
