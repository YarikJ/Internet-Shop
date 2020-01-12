package internetshop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Bucket {
    private Long idBucket;
    private List<Item> items;
    private Long userId;

    public Bucket(Long userId) {
        items = new ArrayList<>();
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
                && Objects.equals(userId, bucket.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items, userId);
    }

    @Override
    public String toString() {
        return "Bucket{" + "idBucket=" + idBucket
                + ", items=" + items
                + ", userId=" + userId + '}';
    }
}
