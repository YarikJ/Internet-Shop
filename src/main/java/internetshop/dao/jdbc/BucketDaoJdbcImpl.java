package internetshop.dao.jdbc;

import internetshop.dao.BucketDao;
import internetshop.dao.ItemDao;
import internetshop.dao.UserDao;
import internetshop.exceptions.DataProcessingException;
import internetshop.lib.Dao;
import internetshop.lib.Inject;
import internetshop.model.Bucket;
import internetshop.model.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Dao
public class BucketDaoJdbcImpl extends AbstractDao<Bucket> implements BucketDao {
    @Inject
    private static UserDao userDao;
    @Inject
    private static ItemDao itemDao;

    public BucketDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Bucket create(Bucket bucket) throws DataProcessingException {
        String createBucketQuery = "INSERT INTO buckets(user_id) VALUES (?);";

        try (PreparedStatement stmt = connection.prepareStatement(createBucketQuery,
                Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, bucket.getUser().getUserId());

            stmt.executeUpdate();

            ResultSet resultSet = stmt.getGeneratedKeys();
            if (resultSet.next()) {
                bucket.setIdBucket(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't create new bucket for user"
                    + bucket.getUser().getUserId(), e);
        }
        return bucket;
    }

    @Override
    public Optional<Bucket> getByBucketId(Long bucketId) throws DataProcessingException {
        String getBucketQuery = "SELECT user_id, buckets.bucket_id, item_id FROM buckets"
                + " LEFT JOIN buckets_items ON buckets.bucket_id = buckets_items.bucket_id"
                + " WHERE buckets.bucket_id = ?;";

        return getBucket(getBucketQuery, bucketId);
    }

    @Override
    public Optional<Bucket> getByUserId(Long userId) throws DataProcessingException {
        String getBucketQuery = "SELECT user_id, buckets.bucket_id, item_id FROM buckets"
                + " LEFT JOIN buckets_items ON buckets.bucket_id = buckets_items.bucket_id"
                + " WHERE buckets.user_id = ?;";

        return getBucket(getBucketQuery, userId);
    }

    @Override
    public Bucket update(Bucket bucket) throws DataProcessingException {
        String deleteItemsFromBucketQuery = "DELETE FROM buckets_items WHERE bucket_id=?;";
        String insertItemsToBucketQuery = "INSERT INTO buckets_items (bucket_id, item_id) "
                + "VALUES (?, ?);";

        try (PreparedStatement stmt = connection.prepareStatement(deleteItemsFromBucketQuery)) {
            stmt.setLong(1, bucket.getIdBucket());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't delete items updating bucket  with id"
                    + bucket.getIdBucket(), e);
        }

        try (PreparedStatement stmt = connection.prepareStatement(insertItemsToBucketQuery)) {
            List<Item> items = bucket.getItems();
            stmt.setLong(1, bucket.getIdBucket());

            for (Item item : items) {
                stmt.setLong(2, item.getIdItem());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't update items in the bucket id"
                    + bucket.getIdBucket(), e);
        }
        return bucket;
    }

    @Override
    public boolean delete(Bucket bucket) throws DataProcessingException {
        String deleteBucketQuery = "DELETE FROM buckets WHERE bucket_id=?";
        try (PreparedStatement stmt = connection.prepareStatement(deleteBucketQuery)) {
            stmt.setLong(1, bucket.getIdBucket());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't delete bucket with id"
                    + bucket.getIdBucket(), e);
        }
    }

    private Optional<Bucket> getBucket(String query, Long id)
            throws DataProcessingException {

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                Bucket bucket = new Bucket();
                bucket.setIdBucket(result.getLong("bucket_id"));

                List<Item> items = bucket.getItems();
                long userId = result.getLong("user_id");
                bucket.setUser(userDao.get(userId).orElseThrow(()
                        -> new DataProcessingException("Can't get user with id" + userId)));
                do {
                    long itemId = result.getLong("item_id");
                    if (itemId > 0) {
                        items.add(itemDao.get(result.getLong("item_id")).orElseThrow(()
                                -> new DataProcessingException("Can't get user with id" + itemId)));
                    }
                } while (result.next());
                return Optional.of(bucket);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Could not get bucket", e);
        }
        return Optional.empty();
    }
}
