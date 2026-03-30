# afk manager api

Control the AFK teleportation system.

***

## Package

```
bg.pikz.shards.api.interfaces.AFKManagerAPI
```

***

## Accessing AFKManagerAPI

```java
import bg.pikz.shards.api.ShardsAPIProvider;
import bg.pikz.shards.api.interfaces.AFKManagerAPI;

ShardsAPI api = ShardsAPIProvider.getAPI();
AFKManagerAPI afkManager = api.getAfkManager();
```

***

## Methods

### setAFKLocation(Location location)

Sets the AFK teleport location.

```java
void setAFKLocation(Location location)
```

**Parameters:**

* `location` - The location to set as AFK destination

**Example:**

```java
Location afkSpot = new Location(world, 100, 64, 200);
afkManager.setAFKLocation(afkSpot);
```

***

### teleportToAFK(Player player)

Teleports a player to the AFK location with countdown.

```java
void teleportToAFK(Player player)
```

**Parameters:**

* `player` - The player to teleport

**Example:**

```java
afkManager.teleportToAFK(player);
// Player will see countdown and then be teleported
```

***

### cancelAFKTeleport(Player player)

Cancels an ongoing AFK teleport for a player.

```java
void cancelAFKTeleport(Player player)
```

**Parameters:**

* `player` - The player whose teleport to cancel

**Example:**

```java
afkManager.cancelAFKTeleport(player);
```

***

### hasMovedSignificantly(Player player)

Checks if a player has moved significantly from their starting position.

```java
boolean hasMovedSignificantly(Player player)
```

**Parameters:**

* `player` - The player to check

**Returns:** `true` if the player has moved significantly

**Example:**

```java
if (afkManager.hasMovedSignificantly(player)) {
    afkManager.cancelAFKTeleport(player);
    player.sendMessage("Teleport cancelled - you moved!");
}
```

***

## Complete Example

```java
ShardsAPI api = ShardsAPIProvider.getAPI();
AFKManagerAPI afkManager = api.getAfkManager();

// Check if AFK feature is enabled
if (!api.isFeatureEnabled("afk")) {
    player.sendMessage("AFK system is disabled!");
    return;
}

// Set AFK location (admin command)
if (player.hasPermission("shards.admin")) {
    Location location = player.getLocation();
    afkManager.setAFKLocation(location);
    player.sendMessage("AFK location set!");
}

// Teleport player to AFK zone
afkManager.teleportToAFK(player);
```

***

## Movement Detection Example

```java
@EventHandler
public void onPlayerMove(PlayerMoveEvent event) {
    Player player = event.getPlayer();
    ShardsAPI api = ShardsAPIProvider.getAPI();
    AFKManagerAPI afkManager = api.getAfkManager();

    // Check if player moved during teleport countdown
    if (afkManager.hasMovedSignificantly(player)) {
        afkManager.cancelAFKTeleport(player);
    }
}
```
