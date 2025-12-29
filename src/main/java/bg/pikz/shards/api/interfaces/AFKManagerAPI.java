package bg.pikz.shards.api.interfaces;

import org.bukkit.Location;
import org.bukkit.entity.Player;


public interface AFKManagerAPI {

    /**
     * Sets the AFK teleport location
     * 
     * @param location the location to set as AFK destination
     */
    void setAFKLocation(Location location);

    /**
     * Teleports a player to the AFK location with countdown
     * 
     * @param player the player to teleport
     */
    void teleportToAFK(Player player);

    /**
     * Cancels an ongoing AFK teleport for a player
     * 
     * @param player the player whose teleport to cancel
     */
    void cancelAFKTeleport(Player player);

    /**
     * Checks if a player has moved significantly from their starting position
     * 
     * @param player the player to check
     * @return true if the player has moved significantly
     */
    boolean hasMovedSignificantly(Player player);
}