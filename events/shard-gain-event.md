# shard gain event

Fired when a player gains shards. This event is **cancellable**.

***

## Package

```
bg.pikz.shards.api.events.ShardGainEvent
```

***

## Event Information

| Property    | Value |
| ----------- | ----- |
| Cancellable | Yes   |
| Async       | No    |

***

## Methods

### getPlayer()

Gets the player gaining shards.

```java
Player getPlayer()
```

**Returns:** The player

***

### getAmount()

Gets the amount of shards being gained.

```java
long getAmount()
```

**Returns:** The shard amount

***

### setAmount(long amount)

Sets a new amount of shards to be gained.

```java
void setAmount(long amount)
```

**Parameters:**

* `amount` - The new amount

***

### getReason()

Gets the reason for gaining shards.

```java
GainReason getReason()
```

**Returns:** The GainReason enum value

***

### isCancelled()

Check if the event is cancelled.

```java
boolean isCancelled()
```

**Returns:** `true` if cancelled

***

### setCancelled(boolean cancelled)

Cancel or uncancel the event.

```java
void setCancelled(boolean cancelled)
```

**Parameters:**

* `cancelled` - Whether to cancel

***

## GainReason Enum

| Value               | Description                                          |
| ------------------- | ---------------------------------------------------- |
| `REGION_GENERATION` | Shards gained from being in an AFK region            |
| `KILL`              | Shards gained from killing another player            |
| `ADMIN_GIVE`        | Shards given by an admin command                     |
| `PAYMENT_RECEIVED`  | Shards received from another player via `/shard pay` |
| `SHOP_PURCHASE`     | Shards refunded from a shop purchase (rare)          |
| `OTHER`             | Any other source                                     |

***

## Basic Listener Example

```java
import bg.pikz.shards.api.events.ShardGainEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MyListener implements Listener {

    @EventHandler
    public void onShardGain(ShardGainEvent event) {
        Player player = event.getPlayer();
        long amount = event.getAmount();
        ShardGainEvent.GainReason reason = event.getReason();

        // Log the shard gain
        Bukkit.getLogger().info(player.getName() + " gained " + amount + " shards (" + reason + ")");
    }
}
```

***

## Modify Shard Amount Example

```java
@EventHandler
public void onShardGain(ShardGainEvent event) {
    Player player = event.getPlayer();

    // Double shards from kills for VIP players
    if (event.getReason() == ShardGainEvent.GainReason.KILL) {
        if (player.hasPermission("vip.doublekill")) {
            event.setAmount(event.getAmount() * 2);
            player.sendMessage("VIP bonus: Double kill reward!");
        }
    }

    // 50% bonus shards from AFK generation during weekend
    if (event.getReason() == ShardGainEvent.GainReason.REGION_GENERATION) {
        if (isWeekend()) {
            long bonus = event.getAmount() / 2;
            event.setAmount(event.getAmount() + bonus);
        }
    }
}
```

***

## Cancel Event Example

```java
@EventHandler
public void onShardGain(ShardGainEvent event) {
    Player player = event.getPlayer();

    // Disable shard gain in specific world
    if (player.getWorld().getName().equals("minigames")) {
        event.setCancelled(true);
        return;
    }

    // Prevent AFK farming during certain hours
    if (event.getReason() == ShardGainEvent.GainReason.REGION_GENERATION) {
        if (isMaintenanceHours()) {
            event.setCancelled(true);
            player.sendMessage("Shard generation is disabled during maintenance!");
        }
    }
}
```

***

## Complete Integration Example

```java
public class ShardGainListener implements Listener {

    private final MyPlugin plugin;

    public ShardGainListener(MyPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onShardGain(ShardGainEvent event) {
        Player player = event.getPlayer();
        long originalAmount = event.getAmount();
        ShardGainEvent.GainReason reason = event.getReason();

        // Apply multipliers based on reason
        double multiplier = getMultiplier(player, reason);
        long newAmount = (long) (originalAmount * multiplier);
        event.setAmount(newAmount);

        // Log significant gains
        if (newAmount >= 100) {
            plugin.getLogger().info(String.format(
                "%s gained %d shards (original: %d, reason: %s)",
                player.getName(), newAmount, originalAmount, reason
            ));
        }
    }

    private double getMultiplier(Player player, ShardGainEvent.GainReason reason) {
        double multiplier = 1.0;

        // Base rank multipliers
        if (player.hasPermission("rank.vip")) multiplier += 0.25;
        if (player.hasPermission("rank.mvp")) multiplier += 0.5;

        // Reason-specific multipliers
        if (reason == ShardGainEvent.GainReason.KILL) {
            if (plugin.isDoubleKillEventActive()) {
                multiplier *= 2.0;
            }
        }

        return multiplier;
    }
}
```
