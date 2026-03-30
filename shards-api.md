# shards api

The main entry point for all Shards API functionality.

***

## Package

```
bg.pikz.shards.api.interfaces.ShardsAPI
```

***

## Accessing ShardsAPI

```java
import bg.pikz.shards.api.ShardsAPIProvider;
import bg.pikz.shards.api.interfaces.ShardsAPI;

ShardsAPI api = ShardsAPIProvider.getAPI();
```

***

## Methods

### getUserMap()

Gets a map of all users (by UUID) and their data.

```java
Map<UUID, OfflineUserAPI> getUserMap()
```

**Returns:** Unmodifiable map of UUID to [OfflineUserAPI](/broken/pages/99ea669309db2d7d6c0707d25021e5c1b68e7484)

**Example:**

```java
Map<UUID, OfflineUserAPI> users = api.getUserMap();
OfflineUserAPI user = users.get(player.getUniqueId());
```

***

### getLeaderboardManager()

Gets the leaderboard manager.

```java
LeaderboardAPI getLeaderboardManager()
```

**Returns:** [LeaderboardAPI](/broken/pages/e8a1f061b7d7bfc2844b246219a6ae8da2113a7f) instance

***

### getAfkManager()

Gets the AFK manager.

```java
AFKManagerAPI getAfkManager()
```

**Returns:** [AFKManagerAPI](/broken/pages/92e5ba0e49cf6cdc57327e0112d8065dfcfed36b) instance

***

### getGenMultiplayer()

Gets the generation multiplier manager.

```java
GenMultiplayerAPI getGenMultiplayer()
```

**Returns:** [GenMultiplayerAPI](/broken/pages/e9533534d44af283a8a03c83839c2d38efed74d4) instance

***

### getKillMultiplayer()

Gets the kill multiplier manager.

```java
KillMultiplayerAPI getKillMultiplayer()
```

**Returns:** [KillMultiplayerAPI](/broken/pages/6c2198c8d3e80daf7c1b9762597e69a0e68b9c55) instance

***

### getShopManager()

Gets the shop manager.

```java
ShopAPI getShopManager()
```

**Returns:** [ShopAPI](/broken/pages/c2753146b8a55c43122bbb9b248dafdd86f4e492) instance

***

### getVersion()

Gets the current plugin version.

```java
String getVersion()
```

**Returns:** Version string (e.g., "2.3.9")

***

### isFeatureEnabled(String feature)

Checks if a specific feature is enabled.

```java
boolean isFeatureEnabled(String feature)
```

**Parameters:**

* `feature` - The feature name: `"leaderboard"`, `"shop"`, or `"afk"`

**Returns:** `true` if the feature is enabled

**Example:**

```java
if (api.isFeatureEnabled("shop")) {
    api.getShopManager().openShop(player);
}
```

***

## Complete Example

```java
ShardsAPI api = ShardsAPIProvider.getAPI();

// Get plugin version
getLogger().info("Shards version: " + api.getVersion());

// Check features
if (api.isFeatureEnabled("shop")) {
    api.getShopManager().openShop(player);
}

if (api.isFeatureEnabled("leaderboard")) {
    api.getLeaderboardManager().open(player);
}

// Get player data
OfflineUserAPI user = api.getUserMap().get(player.getUniqueId());
if (user != null) {
    long balance = user.getShards();
}
```
