package internetshop.service;

import internetshop.model.Bucket;
import internetshop.model.Item;

import java.util.List;

public interface BucketService {
    Bucket getByBucketId(Long bucketId);

    Bucket getByUserId(Long userId);

    Bucket update(Bucket bucket);

    boolean delete(Bucket bucket);

    void addItem(Long userId, Long idItem);

    void deleteItem(Long userId, Long idItem);

    void clear(Bucket bucket);

    List<Item> getAllItems(Bucket bucket);
}
