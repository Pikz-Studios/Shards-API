package bg.pikz.shards.api;

import bg.pikz.shards.api.interfaces.*;
import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class ShardsAPIProvider {

    private static ShardsAPI instance;
    @Getter
    private static ShardPlugin plugin;

    /**
     * Called by core module to register the plugin instance
     */
    public static void register(ShardPlugin pluginInstance) {
        plugin = pluginInstance;
        instance = null; // Reset instance so it gets recreated
    }

    public static ShardsAPI getAPI() {
        if (plugin == null) {
            throw new IllegalStateException("Shards plugin is not loaded!");
        }
        if (instance == null) {
            instance = new ShardsAPIImpl();
        }
        return instance;
    }

    private static class ShardsAPIImpl implements ShardsAPI {

        @Override
        public Map<UUID, OfflineUserAPI> getUserMap() {
            Map<UUID, ? extends OfflineUserAPI> original = plugin.getUserMap();
            return Collections.unmodifiableMap((Map<UUID, OfflineUserAPI>) original);
        }

        @Override
        public LeaderboardAPI getLeaderboardManager() {
            return plugin.getLeaderboardManager();
        }

        @Override
        public AFKManagerAPI getAfkManager() {
            return plugin.getAfkManager();
        }

        @Override
        public GenMultiplayerAPI getGenMultiplayer() {
            return plugin.getGenMultiplayer();
        }

        @Override
        public KillMultiplayerAPI getKillMultiplayer() {
            return plugin.getKillMultiplayer();
        }

        @Override
        public ShopAPI getShopManager() {
            return plugin.getShopManager();
        }

        @Override
        public String getVersion() {
            return plugin.getVersion();
        }

        @Override
        public boolean isFeatureEnabled(String feature) {
            switch (feature.toLowerCase()) {
                case "leaderboard":
                    return plugin.isEnableLeaderboard();
                case "shop":
                    return plugin.isEnableShop();
                case "afk":
                    return plugin.isEnableAFK();
                default:
                    return false;
            }
        }
    }
}
