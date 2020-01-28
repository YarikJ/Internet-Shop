package internetshop.service;

import internetshop.exceptions.DataProcessingException;
import internetshop.model.Bucket;
import internetshop.model.Item;

import java.util.List;

public interface BucketService {
    Bucket getByBucketId(Long bucketId) throws DataProcessingException;

    Bucket getByUserId(Long userId) throws DataProcessingException;

    Bucket update(Bucket bucket) throws DataProcessingException;

    boolean delete(Bucket bucket) throws DataProcessingException;

    void addItem(Long userId, Long idItem) throws DataProcessingException;

    void deleteItem(Long userId, Long idItem) throws DataProcessingException;

    void clear(Bucket bucket);

    List<Item> getAllItems(Bucket bucket);
}
