package internetshop.service;

import internetshop.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    Item create(Item item);

    Optional<Item> get(long id);

    Item update(Item item);

    void delete(long id);

    void delete(Item item);

    List<Item> getAllItems();
}
