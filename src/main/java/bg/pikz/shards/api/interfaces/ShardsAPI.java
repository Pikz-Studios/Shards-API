package bg.pikz.shards.api.interfaces;



import java.util.Map;
import java.util.UUID;

public interface ShardsAPI {

    Map<UUID, OfflineUserAPI> getUserMap();

    LeaderboardAPI getLeaderboardManager();

    AFKManagerAPI getAfkManager();

    GenMultiplayerAPI getGenMultiplayer();

    KillMultiplayerAPI getKillMultiplayer();

    ShopAPI getShopManager();

    String getVersion();

    boolean isFeatureEnabled(String feature);
}
