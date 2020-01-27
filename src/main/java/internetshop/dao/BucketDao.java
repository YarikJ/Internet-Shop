package internetshop.dao;

import internetshop.exceptions.DataProcessingException;
import internetshop.model.Bucket;

import java.util.Optional;

public interface BucketDao {
    Bucket create(Bucket bucket) throws DataProcessingException;

    Optional<Bucket> getByBucketId(Long bucketId) throws DataProcessingException;

    Optional<Bucket> getByUserId(Long userId) throws DataProcessingException;

    Bucket update(Bucket bucket) throws DataProcessingException;

    boolean delete(Bucket bucket) throws DataProcessingException;

}
