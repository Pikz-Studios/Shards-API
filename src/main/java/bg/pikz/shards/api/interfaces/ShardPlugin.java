package bg.pikz.shards.api.interfaces;

import java.util.Map;
import java.util.UUID;


public interface ShardPlugin {

    Map<UUID, ? extends OfflineUserAPI> getUserMap();

    LeaderboardAPI getLeaderboardManager();

    AFKManagerAPI getAfkManager();

    GenMultiplayerAPI getGenMultiplayer();

    KillMultiplayerAPI getKillMultiplayer();

    ShopAPI getShopManager();

    boolean isEnableLeaderboard();

    boolean isEnableShop();

    boolean isEnableAFK();

    String getVersion();
}
