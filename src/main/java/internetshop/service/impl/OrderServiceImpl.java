package internetshop.service.impl;

import internetshop.dao.OrderDao;
import internetshop.dao.UserDao;
import internetshop.exceptions.DataProcessingException;
import internetshop.lib.Inject;
import internetshop.lib.Service;
import internetshop.model.Item;
import internetshop.model.Order;
import internetshop.model.User;
import internetshop.service.OrderService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderServiceImpl implements OrderService {
    @Inject
    private static OrderDao orderDao;
    @Inject
    private static UserDao userDao;

    @Override
    public Order getOrder(Long idOrder) throws DataProcessingException {
        return orderDao.get(idOrder).orElseThrow(()
                -> new DataProcessingException("There is no order with id" + idOrder));
    }

    @Override
    public Order update(Order order) throws DataProcessingException {
        return orderDao.update(order);
    }

    @Override
    public boolean deleteOrder(Order order) throws DataProcessingException {
        return orderDao.delete(order);
    }

    @Override
    public Order completeOrder(List<Item> items, Long userId) throws DataProcessingException {
        Order order = new Order();

        order.setItems(items);
        order.setUser(userDao.get(userId).get());
        orderDao.create(order);

        return order;
    }

    @Override
    public List<Order> getUserOrders(User user) throws DataProcessingException {
        return orderDao.getUserOrders(user);
    }
}
