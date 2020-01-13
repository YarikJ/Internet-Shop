package internetshop.dao.impl;

import internetshop.dao.BucketDao;
import internetshop.lib.Dao;
import internetshop.model.Bucket;
import internetshop.storage.Storage;

import java.util.NoSuchElementException;
import java.util.Optional;

@Dao
public class BucketDaoImpl implements BucketDao {
    private static Long bucketId = 0L;

    @Override
    public Bucket create(Bucket bucket) {
        bucket.setIdBucket(++bucketId);
        Storage.buckets.add(bucket);
        return bucket;
    }

    @Override
    public Optional<Bucket> getByBucketId(Long bucketId) {
        return Storage.buckets.stream()
                .filter(b -> b.getIdBucket().equals(bucketId)).findFirst();
    }

    @Override
    public Optional<Bucket> getByUserId(Long userId) {
        return Storage.buckets.stream()
                .filter(b -> b.getUser().getUserId().equals(userId)).findFirst();
    }

    @Override
    public Bucket update(Bucket bucket) {
        Bucket bucketToUpdate = getByBucketId(bucket.getIdBucket())
                .orElseThrow(() -> new NoSuchElementException("Can't find bucket to update"));
        bucketToUpdate.setItems(bucket.getItems());
        bucketToUpdate.setUser(bucket.getUser());
        return bucketToUpdate;
    }

    @Override
    public boolean delete(Bucket bucket) {
        return Storage.buckets.remove(bucket);
    }
}
