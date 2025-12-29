# Shards API

A developer API for integrating with the Shards economy plugin for Minecraft servers.

## Installation

### Maven

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.Pikz-Studios</groupId>
        <artifactId>Shards-API</artifactId>
        <version>1.0.0</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

### Gradle

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    compileOnly 'com.github.Pikz-Studios:Shards-API:1.0.0'
}
```

## Getting Started

### Accessing the API

```java
import bg.pikz.shards.api.ShardsAPIProvider;
import bg.pikz.shards.api.interfaces.ShardsAPI;

public class MyPlugin extends JavaPlugin {
    
    private ShardsAPI shardsAPI;
    
    @Override
    public void onEnable() {
        // Make sure Shards is loaded
        if (Bukkit.getPluginManager().getPlugin("Shards") != null) {
            shardsAPI = ShardsAPIProvider.getAPI();
        }
    }
}
```

### Add Shards as a Dependency

In your `plugin.yml`:

```yaml
depend: [Shards]
# or use softdepend if Shards is optional
softdepend: [Shards]
```

## API Usage

### Managing Player Shards

```java
ShardsAPI api = ShardsAPIProvider.getAPI();

// Get a player's shard balance
UUID playerUUID = player.getUniqueId();
OfflineUserAPI user = api.getUserMap().get(playerUUID);

if (user != null) {
    long balance = user.getShards();
    
    // Modify shards
    user.setShards(1000);
    user.addShards(500);
    user.addShard(); // Adds 1 shard
    user.takeShard(100);
}
```

### Check Feature Status

```java
ShardsAPI api = ShardsAPIProvider.getAPI();

boolean leaderboardEnabled = api.isFeatureEnabled("leaderboard");
boolean shopEnabled = api.isFeatureEnabled("shop");
boolean afkEnabled = api.isFeatureEnabled("afk");
```

### Shop Integration

```java
ShopAPI shop = ShardsAPIProvider.getAPI().getShopManager();

// Open shop for a player
shop.openShop(player);

// Check if shop is loaded
if (shop.isShopLoaded()) {
    // Shop operations
}

// Reload shop configuration
shop.reloadShop();
```

### AFK Manager

```java
AFKManagerAPI afkManager = ShardsAPIProvider.getAPI().getAfkManager();

// Teleport player to AFK location
afkManager.teleportToAFK(player);

// Cancel ongoing teleport
afkManager.cancelAFKTeleport(player);

// Set AFK location
afkManager.setAFKLocation(location);

// Check if player moved during teleport countdown
boolean moved = afkManager.hasMovedSignificantly(player);
```

### Generation & Kill Multipliers

```java
// Generation multiplier (shards earned in regions)
GenMultiplayerAPI genMultiplayer = ShardsAPIProvider.getAPI().getGenMultiplayer();
long bonusShards = genMultiplayer.getGenShards(player);
boolean hasBonus = genMultiplayer.hasGenMultiplayerPerm(player);

// Kill multiplier (shards earned from kills)
KillMultiplayerAPI killMultiplayer = ShardsAPIProvider.getAPI().getKillMultiplayer();
long killBonus = killMultiplayer.getKillShards(player);
boolean abusePreventionEnabled = killMultiplayer.isPreventAbuse();
```

## Events

### ShardGainEvent

Fired when a player gains shards. Cancellable and allows modifying the amount.

```java
@EventHandler
public void onShardGain(ShardGainEvent event) {
    Player player = event.getPlayer();
    long amount = event.getAmount();
    ShardGainEvent.GainReason reason = event.getReason();
    
    // Modify the amount
    event.setAmount(amount * 2);
    
    // Or cancel it
    event.setCancelled(true);
}
```

**Gain Reasons:**
- `REGION_GENERATION` - Earned from being in a shard region
- `KILL` - Earned from killing a player
- `ADMIN_GIVE` - Given by an admin
- `PAYMENT_RECEIVED` - Received from another player
- `SHOP_PURCHASE` - Refund or shop-related
- `OTHER` - Other sources

### ShardLoseEvent

Fired when a player loses shards. Cancellable and allows modifying the amount.

```java
@EventHandler
public void onShardLose(ShardLoseEvent event) {
    Player player = event.getPlayer();
    long amount = event.getAmount();
    ShardLoseEvent.LoseReason reason = event.getReason();
    
    // Prevent loss for certain players
    if (player.hasPermission("myserver.vip")) {
        event.setCancelled(true);
    }
}
```

**Lose Reasons:**
- `SHOP_PURCHASE` - Spent in shop
- `PAYMENT_SENT` - Sent to another player
- `ADMIN_TAKE` - Taken by an admin
- `OTHER` - Other sources

## Complete Example

```java
import bg.pikz.shards.api.ShardsAPIProvider;
import bg.pikz.shards.api.interfaces.*;
import bg.pikz.shards.api.events.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class MyPlugin extends JavaPlugin implements Listener {
    
    @Override
    public void onEnable() {
        if (getServer().getPluginManager().getPlugin("Shards") == null) {
            getLogger().warning("Shards not found! Disabling integration.");
            return;
        }
        
        getServer().getPluginManager().registerEvents(this, this);
    }
    
    public long getPlayerShards(Player player) {
        ShardsAPI api = ShardsAPIProvider.getAPI();
        OfflineUserAPI user = api.getUserMap().get(player.getUniqueId());
        return user != null ? user.getShards() : 0;
    }
    
    public void giveBonus(Player player, long amount) {
        ShardsAPI api = ShardsAPIProvider.getAPI();
        OfflineUserAPI user = api.getUserMap().get(player.getUniqueId());
        if (user != null) {
            user.addShards(amount);
        }
    }
    
    @EventHandler
    public void onShardGain(ShardGainEvent event) {
        // Double shards on weekends
        if (isWeekend()) {
            event.setAmount(event.getAmount() * 2);
        }
    }
    
    @EventHandler
    public void onShardLose(ShardLoseEvent event) {
        // Log all shard losses
        getLogger().info(event.getPlayer().getName() + " lost " + 
            event.getAmount() + " shards (" + event.getReason() + ")");
    }
}
```

## API Interfaces

| Interface | Description |
|-----------|-------------|
| `ShardsAPI` | Main API entry point |
| `OfflineUserAPI` | Player shard data management |
| `ShopAPI` | Shop functionality |
| `AFKManagerAPI` | AFK teleportation system |
| `LeaderboardAPI` | Leaderboard access |
| `GenMultiplayerAPI` | Generation bonus permissions |
| `KillMultiplayerAPI` | Kill bonus permissions |

## Support

For issues or questions, visit [discord.gg/pikzstudios](https://discord.gg/pikzstudios)
