package bg.pikz.shards.api.interfaces;

import org.bukkit.entity.Player;

import java.util.HashMap;


public interface GenMultiplayerAPI {

    /**
     * Gets the generation multiplayer permissions and their corresponding shard
     * values
     * 
     * @return a map of permission nodes to shard amounts
     */
    HashMap<String, Long> getGenMultiplayer();

    /**
     * Checks if a player has any generation multiplayer permissions
     * 
     * @param player the player to check
     * @return true if the player has at least one gen multiplayer permission
     */
    boolean hasGenMultiplayerPerm(Player player);

    /**
     * Gets the highest generation shard bonus for a player based on their
     * permissions
     * 
     * @param player the player to check
     * @return the highest shard bonus amount, or 0 if no permissions match
     */
    long getGenShards(Player player);
}