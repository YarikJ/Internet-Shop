package internetshop.model;

import java.util.List;
import java.util.Objects;

public class Bucket {
    private Long idBucket;
    private List<Item> items;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getIdBucket() {
        return idBucket;
    }

    public void setIdBucket(Long idBucket) {
        this.idBucket = idBucket;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Bucket bucket = (Bucket) o;
        return Objects.equals(items, bucket.items)
                && Objects.equals(user, bucket.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items, user);
    }

    @Override
    public String toString() {
        return "Bucket{" + "idBucket=" + idBucket
                + ", items=" + items
                + ", user=" + user + '}';
    }
}
