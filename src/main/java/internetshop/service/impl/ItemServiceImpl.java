package internetshop.service.impl;

import internetshop.dao.ItemDao;
import internetshop.exceptions.DataProcessingException;
import internetshop.lib.Inject;
import internetshop.lib.Service;
import internetshop.model.Item;
import internetshop.service.ItemService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ItemServiceImpl implements ItemService {
    @Inject
    private static ItemDao itemDao;

    @Override
    public List<Item> getAllItems() throws DataProcessingException {
        return itemDao.getAllItems();
    }

    @Override
    public Item create(Item item) throws DataProcessingException {
        return itemDao.create(item);
    }

    @Override
    public Item get(Long id) throws DataProcessingException {
        return itemDao.get(id).orElseThrow(()
                -> new DataProcessingException("There is no item with id" + id));
    }

    @Override
    public Item update(Item item) throws DataProcessingException {
        return itemDao.update(item);
    }

    @Override
    public void delete(Long id) throws DataProcessingException {
        itemDao.delete(id);
    }

    @Override
    public void delete(Item item) throws DataProcessingException {
        itemDao.delete(item);
    }
}
