package internetshop.storage;

import internetshop.model.Bucket;
import internetshop.model.Item;
import internetshop.model.Order;
import internetshop.model.User;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    public static final List<Item> items = new ArrayList<>();
    public static final List<Bucket> buckets = new ArrayList<>();
    public static final List<User> users = new ArrayList<>();
    public static final List<Order> orders = new ArrayList<>();

    static {
        User admin = new User("ADMIN", "111");
        admin.setUserId(1L);
        users.add(admin);
    }
}
