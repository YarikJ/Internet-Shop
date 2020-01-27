package internetshop.service.impl;

import internetshop.dao.BucketDao;
import internetshop.dao.ItemDao;
import internetshop.dao.UserDao;
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
    @Inject
    private static UserDao userDao;

    @Override
    public Bucket getByBucketId(Long idBucket) {
        return bucketDao.getByBucketId(idBucket).orElseThrow(()
                -> new NoSuchElementException("There is no bucket with id" + idBucket));
    }

    @Override
    public Bucket getByUserId(Long userId) {
        Optional<Bucket> byUserId = bucketDao.getByUserId(userId);

        return byUserId.orElseGet(()
                -> bucketDao.create(new Bucket(userDao.get(userId).orElseThrow(()
                        -> new NoSuchElementException("There is no user with id" + userId)))));
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
    public void addItem(Long userId, Long itemId) {
        Item item = itemDao.get(itemId).orElseThrow(()
                -> new NoSuchElementException("There is no item with id " + itemId));
        Bucket bucket = getByUserId(userId);
        bucket.getItems().add(item);
        update(bucket);
    }

    @Override
    public void deleteItem(Long userId, Long itemId) {
        Item item = itemDao.get(itemId).orElseThrow(()
                -> new NoSuchElementException("There is no item with id " + itemId));
        Bucket bucket = getByUserId(userId);
        bucket.getItems().remove(item);
        update(bucket);
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
