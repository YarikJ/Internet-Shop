package internetshop.service;

import internetshop.model.Bucket;
import internetshop.model.Item;

import java.util.List;
import java.util.Optional;

public interface BucketService {
    Bucket create(Bucket bucket);

    Optional<Bucket> get(Long idBucket);

    Bucket update(Bucket bucket);

    boolean delete(Bucket bucket);

    void addItem(Long idBucket, Long idItem);

    void deleteItem(Bucket bucket, Item item);

    void clear(Bucket bucket);

    List<Item> getAllItems(Bucket bucket);
}
