package internetshop.model;

import java.util.List;
import java.util.Objects;

public class Order {
    private Long orderId;
    private List<Item> items;
    private User user;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;
        return Objects.equals(items, order.items)
                && Objects.equals(user, order.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items, user);
    }

    @Override
    public String toString() {
        return "Order{" + "idOrder=" + orderId
                + ", items=" + items
                + ", userId=" + user + '}';
    }
}
