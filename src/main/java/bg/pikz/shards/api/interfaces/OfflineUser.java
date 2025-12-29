package bg.pikz.shards.api.interfaces;

import java.util.UUID;


public interface OfflineUser {

    UUID getUuid();

    String getLastUsername();

    long getShards();

    void setShards(long shards);

    void addShard();

    void addShards(long shardsAmount);

    void takeShard(long shards);
}
