package internetshop.service.impl;

import internetshop.dao.BucketDao;
import internetshop.dao.ItemDao;
import internetshop.dao.UserDao;
import internetshop.exceptions.DataProcessingException;
import internetshop.lib.Inject;
import internetshop.lib.Service;
import internetshop.model.Bucket;
import internetshop.model.Item;
import internetshop.model.User;
import internetshop.service.BucketService;

import java.util.List;
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
    public Bucket getByBucketId(Long idBucket) throws DataProcessingException {
        return bucketDao.getByBucketId(idBucket).orElseThrow(()
                -> new DataProcessingException("There is no bucket with id" + idBucket));
    }

    @Override
    public Bucket getByUserId(Long userId) throws DataProcessingException {
        Optional<Bucket> byUserId = bucketDao.getByUserId(userId);
        User user = userDao.get(userId).orElseThrow(()
                -> new DataProcessingException("There is no user with id" + userId));
        if (byUserId.isPresent()) {
            return byUserId.get();
        }
        return bucketDao.create(new Bucket(user));
    }

    @Override
    public Bucket update(Bucket bucket) throws DataProcessingException {
        return bucketDao.update(bucket);
    }

    @Override
    public boolean delete(Bucket bucket) throws DataProcessingException {
        return bucketDao.delete(bucket);
    }

    @Override
    public void addItem(Long userId, Long itemId) throws DataProcessingException {
        Item item = itemDao.get(itemId).orElseThrow(()
                -> new DataProcessingException("There is no item with id " + itemId));
        Bucket bucket = getByUserId(userId);
        bucket.getItems().add(item);
        update(bucket);
    }

    @Override
    public void deleteItem(Long userId, Long itemId) throws DataProcessingException {
        Item item = itemDao.get(itemId).orElseThrow(()
                -> new DataProcessingException("There is no item with id " + itemId));
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
