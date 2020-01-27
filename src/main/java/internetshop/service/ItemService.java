package internetshop.service;

import internetshop.exceptions.DataProcessingException;
import internetshop.model.Item;

import java.util.List;

public interface ItemService {
    Item create(Item item) throws DataProcessingException;

    Item get(Long id) throws DataProcessingException;

    Item update(Item item) throws DataProcessingException;

    void delete(Long id) throws DataProcessingException;

    void delete(Item item) throws DataProcessingException;

    List<Item> getAllItems() throws DataProcessingException;
}
