package internetshop.dao;

import internetshop.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemDao {
    Item create(Item item);

    Optional<Item> get(long id);

    Item update(Item item);

    void delete(Long id);

    void delete(Item item);

    List<Item> getAllItems();
}
