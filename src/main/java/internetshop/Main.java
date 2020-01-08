package internetshop;

import internetshop.lib.Injector;
import internetshop.model.Item;
import internetshop.model.User;
import internetshop.service.ItemService;
import internetshop.service.UserService;
import internetshop.service.impl.ItemServiceImpl;
import internetshop.service.impl.UserServiceImpl;

import java.util.List;

public class Main {
    static {
        try {
            Injector.injectDependency();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        User user1 = new User("Tom", "1234");
        UserService userService = new UserServiceImpl();
        userService.create(user1);
        System.out.println(userService.get(1L));

        Item soap = new Item("Camey", 2.5);
        Item milk = new Item("Milky", 4.5);
        Item pen = new Item("CoolWriter", 1.2);
        ItemService itemService = new ItemServiceImpl();
        itemService.create(soap);
        itemService.create(milk);
        itemService.create(pen);
        List<Item> items =  itemService.getAllItems();
        System.out.println(items);

        soap.setPrice(5);
        itemService.update(soap);
        System.out.println(itemService.getAllItems());
    }
}
