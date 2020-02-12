package internetshop.service;

import internetshop.exceptions.DataProcessingException;
import internetshop.model.Bucket;

public interface BucketService {

    Bucket getByUserId(Long userId) throws DataProcessingException;

    Bucket update(Bucket bucket) throws DataProcessingException;

    boolean delete(Bucket bucket) throws DataProcessingException;

    void addItem(Long userId, Long idItem) throws DataProcessingException;

    void deleteItem(Long userId, Long idItem) throws DataProcessingException;
}
