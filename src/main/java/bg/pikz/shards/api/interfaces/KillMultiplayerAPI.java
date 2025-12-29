package bg.pikz.shards.api.interfaces;

import org.bukkit.entity.Player;

import java.util.HashMap;


public interface KillMultiplayerAPI {

    /**
     * Gets the kill multiplayer permissions and their corresponding shard values
     * 
     * @return a map of permission nodes to shard amounts
     */
    HashMap<String, Long> getKillMultiplayer();

    /**
     * Checks if abuse prevention is enabled for kill rewards
     * 
     * @return true if abuse prevention is enabled
     */
    boolean isPreventAbuse();

    /**
     * Gets the highest kill shard bonus for a player based on their permissions
     * 
     * @param player the player to check
     * @return the highest shard bonus amount, or 0 if no permissions match
     */
    long getKillShards(Player player);
}