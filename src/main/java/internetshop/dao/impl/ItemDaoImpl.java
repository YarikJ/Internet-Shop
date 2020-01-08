package internetshop.dao.impl;

import internetshop.dao.ItemDao;
import internetshop.lib.Dao;
import internetshop.model.Item;
import internetshop.storage.Storage;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Dao
public class ItemDaoImpl implements ItemDao {
    @Override
    public Item create(Item item) {
        Storage.addToItems(item);
        return item;
    }

    @Override
    public Optional<Item> get(long id) {
        return Storage.items.stream()
                .filter(i -> i.getIdItem().equals(id))
                .findFirst();
    }

    @Override
    public Item update(Item item) {
        Item itemToUpdate = get(item.getIdItem())
                .orElseThrow(() -> new NoSuchElementException("Can't find item to update"));
        itemToUpdate.setName(item.getName());
        itemToUpdate.setPrice(item.getPrice());
        return itemToUpdate;
    }

    @Override
    public void delete(Long id) {
        Storage.items.remove(get(id).orElseThrow(()
                -> new NoSuchElementException("Can't find item to delete")));
    }

    @Override
    public void delete(Item item) {
        delete(item.getIdItem());
    }

    @Override
    public List<Item> getAllItems() {
        return Storage.items;
    }
}
