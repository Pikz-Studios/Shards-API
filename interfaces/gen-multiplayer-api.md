# gen multiplayer api

Access generation bonus multiplier information.

***

## Package

```
bg.pikz.shards.api.interfaces.GenMultiplayerAPI
```

***

## Accessing GenMultiplayerAPI

```java
import bg.pikz.shards.api.ShardsAPIProvider;
import bg.pikz.shards.api.interfaces.GenMultiplayerAPI;

ShardsAPI api = ShardsAPIProvider.getAPI();
GenMultiplayerAPI genMultiplier = api.getGenMultiplier();
```

***

## Methods

### getGenMultiplayer()

Gets the generation multiplier permissions and their corresponding shard values.

```java
HashMap<String, Long> getGenMultiplayer()
```

**Returns:** A map of permission node names to shard bonus amounts

**Example:**

```java
HashMap<String, Long> multipliers = genMultiplier.getGenMultiplayer();
for (Map.Entry<String, Long> entry : multipliers.entrySet()) {
    System.out.println("Permission: shard.gennodes." + entry.getKey() + " = +" + entry.getValue());
}
```

***

### hasGenMultiplayerPerm(Player player)

Checks if a player has any generation multiplier permissions.

```java
boolean hasGenMultiplayerPerm(Player player)
```

**Parameters:**

* `player` - The player to check

**Returns:** `true` if the player has at least one gen multiplier permission

**Example:**

```java
if (genMultiplier.hasGenMultiplayerPerm(player)) {
    player.sendMessage("You have a generation bonus!");
}
```

***

### getGenShards(Player player)

Gets the highest generation shard bonus for a player based on their permissions.

```java
long getGenShards(Player player)
```

**Parameters:**

* `player` - The player to check

**Returns:** The highest shard bonus amount, or `0` if no permissions match

**Example:**

```java
long bonus = genMultiplier.getGenShards(player);
player.sendMessage("Your generation bonus: +" + bonus + " shards per cycle");
```

***

## Complete Example

```java
ShardsAPI api = ShardsAPIProvider.getAPI();
GenMultiplayerAPI genMultiplier = api.getGenMultiplier();

// Display player's generation bonus
if (genMultiplier.hasGenMultiplayerPerm(player)) {
    long bonus = genMultiplier.getGenShards(player);
    player.sendMessage("You have a generation bonus of +" + bonus + " shards!");
} else {
    player.sendMessage("You don't have any generation bonuses.");
    player.sendMessage("Check out our store for VIP perks!");
}

// List all available generation bonuses
HashMap<String, Long> allBonuses = genMultiplier.getGenMultiplayer();
player.sendMessage("Available generation bonuses:");
for (Map.Entry<String, Long> entry : allBonuses.entrySet()) {
    String hasIt = player.hasPermission("shard.gennodes." + entry.getKey()) ? " (You have this!)" : "";
    player.sendMessage("  - " + entry.getKey() + ": +" + entry.getValue() + " shards" + hasIt);
}
```

***

## Rank Display Example

```java
public String getPlayerRankBonus(Player player) {
    ShardsAPI api = ShardsAPIProvider.getAPI();
    GenMultiplayerAPI genMultiplier = api.getGenMultiplayer();

    if (!genMultiplier.hasGenMultiplayerPerm(player)) {
        return "No bonus";
    }

    long bonus = genMultiplier.getGenShards(player);
    return "+" + bonus + " shards/cycle";
}
```
