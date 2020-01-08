package internetshop.dao;

import internetshop.model.Bucket;

import java.util.Optional;

public interface BucketDao {
    Bucket create(Bucket bucket);

    Optional<Bucket> get(Long bucketId);

    Bucket update(Bucket bucket);

    boolean delete(Bucket bucket);
}
