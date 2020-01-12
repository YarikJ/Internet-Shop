package internetshop.service.impl;

import internetshop.dao.BucketDao;
import internetshop.dao.ItemDao;
import internetshop.lib.Inject;
import internetshop.lib.Service;
import internetshop.model.Bucket;
import internetshop.model.Item;
import internetshop.service.BucketService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BucketServiceImpl implements BucketService {
    @Inject
    private static BucketDao bucketDao;
    @Inject
    private static ItemDao itemDao;

    @Override
    public Bucket getByBucketId(Long idBucket) {
        return bucketDao.getByBucketId(idBucket).orElseThrow(()
                -> new NoSuchElementException("There is no bucket with id" + idBucket));
    }

    @Override
    public Bucket getByUserId(Long userId) {
        Optional<Bucket> byUserId = bucketDao.getByUserId(userId);

        return byUserId.orElseGet(() -> bucketDao.create(new Bucket(userId)));
    }

    @Override
    public Bucket update(Bucket bucket) {
        return bucketDao.update(bucket);
    }

    @Override
    public boolean delete(Bucket bucket) {
        return bucketDao.delete(bucket);
    }

    @Override
    public void addItem(Long idBucket, Long idItem) {
        Item item = itemDao.get(idItem).orElseThrow(()
                -> new NoSuchElementException("There is no item with id " + idItem));
        getByBucketId(idBucket).getItems().add(item);
    }

    @Override
    public void deleteItem(Bucket bucket, Item item) {
        bucket.getItems().remove(item);
    }

    @Override
    public void clear(Bucket bucket) {
        bucket.getItems().clear();
    }

    @Override
    public List<Item> getAllItems(Bucket bucket) {
        return bucket.getItems();
    }
}
