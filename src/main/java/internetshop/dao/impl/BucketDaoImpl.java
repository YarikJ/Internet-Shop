package internetshop.dao.impl;

import internetshop.dao.BucketDao;
import internetshop.lib.Dao;
import internetshop.model.Bucket;
import internetshop.storage.Storage;

import java.util.NoSuchElementException;
import java.util.Optional;

@Dao
public class BucketDaoImpl implements BucketDao {

    @Override
    public Bucket create(Bucket bucket) {
        Storage.addToBuckets(bucket);
        return bucket;
    }

    @Override
    public Optional<Bucket> get(Long bucketId) {
        return Storage.buckets.stream()
                .filter(b -> b.getIdBucket().equals(bucketId)).findFirst();
    }

    @Override
    public Bucket update(Bucket bucket) {
        Bucket bucketToUpdate = get(bucket.getIdBucket())
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
