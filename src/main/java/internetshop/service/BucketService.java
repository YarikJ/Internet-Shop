package internetshop.service;

import internetshop.model.Bucket;
import internetshop.model.Item;

import java.util.List;

public interface BucketService {
    Bucket getByBucketId(Long bucketId);

    Bucket getByUserId(Long userId);

    Bucket update(Bucket bucket);

    boolean delete(Bucket bucket);

    void addItem(Long idBucket, Long idItem);

    void deleteItem(Bucket bucket, Item item);

    void clear(Bucket bucket);

    List<Item> getAllItems(Bucket bucket);
}
