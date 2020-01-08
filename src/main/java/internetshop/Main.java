package internetshop;

import internetshop.lib.Inject;
import internetshop.lib.Injector;
import internetshop.model.Item;
import internetshop.model.User;
import internetshop.service.BucketService;
import internetshop.service.ItemService;
import internetshop.service.OrderService;
import internetshop.service.UserService;

import java.util.List;

public class Main {
    @Inject
    private static UserService userService;
    @Inject
    private static OrderService orderService;
    @Inject
    private static ItemService itemService;
    @Inject
    private static BucketService bucketService;

    static {
        try {
            Injector.injectDependency();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        User user1 = new User("Tom", "1234");
        userService.create(user1);
        System.out.println(userService.get(1L));

        Item soap = new Item("Camey", 2.5);
        Item milk = new Item("Milky", 4.5);
        Item pen = new Item("CoolWriter", 1.2);
        itemService.create(soap);
        itemService.create(milk);
        itemService.create(pen);
        List<Item> items =  itemService.getAllItems();
        System.out.println(items);

        soap.setPrice(5);
        itemService.update(soap);
        System.out.println(itemService.getAllItems());

        System.out.println(itemService.get(2).get());
    }
}
