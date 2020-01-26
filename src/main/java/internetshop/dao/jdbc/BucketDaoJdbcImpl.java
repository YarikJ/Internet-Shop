package internetshop.dao.jdbc;

import internetshop.dao.BucketDao;
import internetshop.dao.ItemDao;
import internetshop.dao.UserDao;
import internetshop.lib.Dao;
import internetshop.lib.Inject;
import internetshop.model.Bucket;
import internetshop.model.Item;
import org.apache.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class BucketDaoJdbcImpl extends AbstractDao<Bucket> implements BucketDao {
    @Inject
    private static UserDao userDao;
    @Inject
    private static ItemDao itemDao;

    private static Logger logger = Logger.getLogger(BucketDaoJdbcImpl.class);

    public BucketDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Bucket create(Bucket bucket) {
        String query = "INSERT INTO buckets(user_id) VALUES (?);";

        try (PreparedStatement stmt = connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, bucket.getUser().getUserId());

            stmt.executeUpdate();

            ResultSet resultSet = stmt.getGeneratedKeys();
            if (resultSet.next()) {
                bucket.setIdBucket(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            logger.error("Couldn't create new bucket for user"
                    + bucket.getUser().getUserId(), e);
            throw new RuntimeException();
        }
        return bucket;
    }

    @Override
    public Optional<Bucket> getByBucketId(Long bucketId) {
        String query = "SELECT user_id, buckets.bucket_id, item_id FROM buckets"
                + " LEFT JOIN buckets_items ON buckets.bucket_id = buckets_items.bucket_id"
                + " WHERE buckets.bucket_id = ?;";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, bucketId);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                Bucket bucket = new Bucket();
                bucket.setIdBucket(bucketId);

                List<Item> items = bucket.getItems();

                bucket.setUser(userDao.get(result.getLong("user_id")).get()); //TODO: get on optional without check
                long itemId = result.getLong("item_id");
                if (itemId > 0) {
                    items.add(itemDao.get(itemId).get());
                }
                while (result.next()) {
                    items.add(itemDao.get(result.getLong("item_id")).get());
                }
                return Optional.of(bucket);
            }
        } catch (SQLException e) {
            logger.error("Could not get from DB order with id " + bucketId, e);
            throw new RuntimeException();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Bucket> getByUserId(Long userId) {
        String query = "SELECT user_id, buckets.bucket_id, item_id FROM buckets"
                + " LEFT JOIN buckets_items ON buckets.bucket_id = buckets_items.bucket_id"
                + " WHERE buckets.user_id = ?;";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, userId);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                Bucket bucket = new Bucket();
                bucket.setIdBucket(result.getLong("bucket_id"));
                List<Item> items = bucket.getItems();

                bucket.setUser(userDao.get(userId).get()); //TODO: get on optional without check

                long itemId = result.getLong("item_id");

                if (itemId > 0) {
                    items.add(itemDao.get(itemId).get());
                }
                while (result.next()) {
                    items.add(itemDao.get(result.getLong("item_id")).get());
                }
                return Optional.of(bucket);
            }
        } catch (SQLException e) {
            logger.error("Could not get bucket  of user with id " + userId, e);
            throw new RuntimeException();
        }
        return Optional.empty();
    }

    @Override
    public Bucket update(Bucket bucket) {
        String query = "DELETE FROM buckets_items WHERE bucket_id=?;";
        String query2 = "INSERT INTO buckets_items (bucket_id, item_id) VALUES (?, ?);";
        int affected;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, bucket.getIdBucket());

            stmt.executeUpdate();

        } catch (SQLException e) {
            logger.error("Couldn't delete items updating bucket  with id"
                    + bucket.getIdBucket(), e);
            throw new RuntimeException();
        }

        try (PreparedStatement stmt = connection.prepareStatement(query2)) {
            List<Item> items = bucket.getItems();

            for (int i = 0; i < items.size(); i++) {
                stmt.setLong(1, bucket.getIdBucket());
                stmt.setLong(2, items.get(i).getIdItem());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            logger.error("Couldn't update items in the bucket in DB for bucket id"
                    + bucket.getIdBucket(), e);
            throw new RuntimeException();
        }
        return bucket;
    }

    @Override
    public boolean delete(Bucket bucket) {
        String query = "DELETE FROM buckets WHERE bucket_id=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, bucket.getIdBucket());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            logger.error("Couldn't delete bucket with id" + bucket.getIdBucket(), e);
            throw new RuntimeException();
        }
    }
}
