# kill multiplayer api

Access kill bonus multiplier information.

***

## Package

```
bg.pikz.shards.api.interfaces.KillMultiplayerAPI
```

***

## Accessing KillMultiplayerAPI

```java
import bg.pikz.shards.api.ShardsAPIProvider;
import bg.pikz.shards.api.interfaces.KillMultiplayerAPI;

ShardsAPI api = ShardsAPIProvider.getAPI();
KillMultiplayerAPI killMultiplier = api.getKillMultiplayer();
```

***

## Methods

### getKillMultiplayer()

Gets the kill multiplier permissions and their corresponding shard values.

```java
HashMap<String, Long> getKillMultiplayer()
```

**Returns:** A map of permission node names to shard bonus amounts

**Example:**

```java
HashMap<String, Long> multipliers = killMultiplier.getKillMultiplayer();
for (Map.Entry<String, Long> entry : multipliers.entrySet()) {
    System.out.println("Permission: shard.killnodes." + entry.getKey() + " = +" + entry.getValue());
}
```

***

### isPreventAbuse()

Checks if abuse prevention is enabled for kill rewards.

```java
boolean isPreventAbuse()
```

**Returns:** `true` if abuse prevention is enabled (prevents farming by killing the same player repeatedly)

**Example:**

```java
if (killMultiplier.isPreventAbuse()) {
    player.sendMessage("Note: You can't farm shards by killing the same player repeatedly!");
}
```

***

### getKillShards(Player player)

Gets the highest kill shard bonus for a player based on their permissions.

```java
long getKillShards(Player player)
```

**Parameters:**

* `player` - The player to check

**Returns:** The highest shard bonus amount, or `0` if no permissions match

**Example:**

```java
long bonus = killMultiplier.getKillShards(player);
player.sendMessage("Your kill bonus: +" + bonus + " shards per kill");
```

***

## Complete Example

```java
ShardsAPI api = ShardsAPIProvider.getAPI();
KillMultiplayerAPI killMultiplier = api.getKillMultiplayer();

// Display player's kill bonus
long bonus = killMultiplier.getKillShards(player);
if (bonus > 0) {
    player.sendMessage("Your kill bonus: +" + bonus + " extra shards per kill!");
} else {
    player.sendMessage("You don't have any kill bonuses.");
}

// Show abuse prevention status
if (killMultiplier.isPreventAbuse()) {
    player.sendMessage("Anti-farming is enabled - kill different players to earn shards!");
}
```

***

## Kill Stats Display Example

```java
public void showKillBonusInfo(Player player) {
    ShardsAPI api = ShardsAPIProvider.getAPI();
    KillMultiplayerAPI killMultiplier = api.getKillMultiplayer();

    player.sendMessage("=== Kill Bonus Info ===");

    // Show player's current bonus
    long playerBonus = killMultiplier.getKillShards(player);
    player.sendMessage("Your bonus: +" + playerBonus + " shards");

    // Show all available bonuses
    player.sendMessage("Available kill bonuses:");
    HashMap<String, Long> allBonuses = killMultiplier.getKillMultiplayer();
    for (Map.Entry<String, Long> entry : allBonuses.entrySet()) {
        boolean hasPermission = player.hasPermission("shard.killnodes." + entry.getKey());
        String status = hasPermission ? " [ACTIVE]" : "";
        player.sendMessage("  - " + entry.getKey() + ": +" + entry.getValue() + status);
    }

    // Show anti-abuse status
    if (killMultiplier.isPreventAbuse()) {
        player.sendMessage("Anti-farming protection is ENABLED");
    }
}
```
