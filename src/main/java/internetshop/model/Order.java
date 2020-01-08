package internetshop.model;

import java.util.List;
import java.util.Objects;

public class Order {
    private Long idOrder;
    private List<Item> items;
    private Long userId;

    public Long getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Long idOrder) {
        this.idOrder = idOrder;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
                && Objects.equals(userId, order.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items, userId);
    }

    @Override
    public String toString() {
        return "Order{" + "idOrder=" + idOrder
                + ", items=" + items
                + ", userId=" + userId + '}';
    }
}
