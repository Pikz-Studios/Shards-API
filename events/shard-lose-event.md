# shard lose event

Fired when a player loses shards. This event is **cancellable**.

***

## Package

```
bg.pikz.shards.api.events.ShardLoseEvent
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

Gets the player losing shards.

```java
Player getPlayer()
```

**Returns:** The player

***

### getAmount()

Gets the amount of shards being lost.

```java
long getAmount()
```

**Returns:** The shard amount

***

### setAmount(long amount)

Sets a new amount of shards to be lost.

```java
void setAmount(long amount)
```

**Parameters:**

* `amount` - The new amount

***

### getReason()

Gets the reason for losing shards.

```java
LoseReason getReason()
```

**Returns:** The LoseReason enum value

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

## LoseReason Enum

| Value           | Description                                    |
| --------------- | ---------------------------------------------- |
| `SHOP_PURCHASE` | Shards spent on a shop purchase                |
| `PAYMENT_SENT`  | Shards sent to another player via `/shard pay` |
| `ADMIN_TAKE`    | Shards taken by an admin command               |
| `OTHER`         | Any other source                               |

***

## Basic Listener Example

```java
import bg.pikz.shards.api.events.ShardLoseEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MyListener implements Listener {

    @EventHandler
    public void onShardLose(ShardLoseEvent event) {
        Player player = event.getPlayer();
        long amount = event.getAmount();
        ShardLoseEvent.LoseReason reason = event.getReason();

        // Log the shard loss
        Bukkit.getLogger().info(player.getName() + " lost " + amount + " shards (" + reason + ")");
    }
}
```

***

## Apply Discount Example

```java
@EventHandler
public void onShardLose(ShardLoseEvent event) {
    Player player = event.getPlayer();

    // Apply discounts on shop purchases
    if (event.getReason() == ShardLoseEvent.LoseReason.SHOP_PURCHASE) {
        long originalCost = event.getAmount();
        long discount = 0;

        // 10% discount for VIP
        if (player.hasPermission("vip.discount")) {
            discount = (long) (originalCost * 0.10);
        }

        // 20% discount for MVP
        if (player.hasPermission("mvp.discount")) {
            discount = (long) (originalCost * 0.20);
        }

        if (discount > 0) {
            event.setAmount(originalCost - discount);
            player.sendMessage("Discount applied! You saved " + discount + " shards!");
        }
    }
}
```

***

## Cancel Event Example

```java
@EventHandler
public void onShardLose(ShardLoseEvent event) {
    Player player = event.getPlayer();

    // Prevent spending during maintenance
    if (isMaintenanceMode()) {
        event.setCancelled(true);
        player.sendMessage("Shard transactions are disabled during maintenance!");
        return;
    }

    // Prevent payments to certain players (banned from trading)
    if (event.getReason() == ShardLoseEvent.LoseReason.PAYMENT_SENT) {
        if (isTradeBanned(player)) {
            event.setCancelled(true);
            player.sendMessage("You are banned from sending shards!");
        }
    }
}
```

***

## Transaction Logging Example

```java
@EventHandler
public void onShardLose(ShardLoseEvent event) {
    Player player = event.getPlayer();
    long amount = event.getAmount();
    ShardLoseEvent.LoseReason reason = event.getReason();

    // Log all transactions to database
    plugin.getTransactionLogger().log(
        player.getUniqueId(),
        "LOSE",
        amount,
        reason.name(),
        System.currentTimeMillis()
    );

    // Alert staff for large transactions
    if (amount >= 10000) {
        for (Player staff : Bukkit.getOnlinePlayers()) {
            if (staff.hasPermission("staff.alerts")) {
                staff.sendMessage("[Alert] " + player.getName() + " spent " + amount + " shards (" + reason + ")");
            }
        }
    }
}
```

***

## Complete Integration Example

```java
public class ShardLoseListener implements Listener {

    private final MyPlugin plugin;

    public ShardLoseListener(MyPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onShardLose(ShardLoseEvent event) {
        Player player = event.getPlayer();
        long amount = event.getAmount();
        ShardLoseEvent.LoseReason reason = event.getReason();

        // Apply discount for shop purchases
        if (reason == ShardLoseEvent.LoseReason.SHOP_PURCHASE) {
            double discount = getPlayerDiscount(player);
            if (discount > 0) {
                long savings = (long) (amount * discount);
                event.setAmount(amount - savings);
                player.sendMessage("You saved " + savings + " shards with your " + (int)(discount * 100) + "% discount!");
            }
        }

        // Log transaction
        logTransaction(player, amount, reason);
    }

    private double getPlayerDiscount(Player player) {
        if (player.hasPermission("rank.legend")) return 0.25;
        if (player.hasPermission("rank.mvp")) return 0.15;
        if (player.hasPermission("rank.vip")) return 0.10;
        return 0;
    }

    private void logTransaction(Player player, long amount, ShardLoseEvent.LoseReason reason) {
        String log = String.format("[TRANSACTION] %s lost %d shards - Reason: %s",
            player.getName(), amount, reason.name());
        plugin.getLogger().info(log);
    }
}
```
