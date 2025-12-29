package bg.pikz.shards.api.interfaces;

import java.util.UUID;

public interface OfflineUserAPI {

    /**
     * Gets the player's UUID
     * 
     * @return the player's UUID
     */
    UUID getUuid();

    /**
     * Gets the player's last known username
     * 
     * @return the last known username
     */
    String getLastUsername();

    /**
     * Gets the current amount of shards
     * 
     * @return the shard count
     */
    long getShards();

    /**
     * Sets the shard amount
     * 
     * @param shards the new shard amount
     */
    void setShards(long shards);

    /**
     * Adds a single shard
     */
    void addShard();

    /**
     * Adds multiple shards
     * 
     * @param shardsAmount the amount of shards to add
     */
    void addShards(long shardsAmount);

    /**
     * Removes shards
     * 
     * @param shards the amount of shards to remove
     */
    void takeShard(long shards);
}