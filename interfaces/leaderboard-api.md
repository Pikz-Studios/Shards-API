# leaderboard api

Open the leaderboard GUI for players.

***

## Package

```
bg.pikz.shards.api.interfaces.LeaderboardAPI
```

***

## Accessing LeaderboardAPI

```java
import bg.pikz.shards.api.ShardsAPIProvider;
import bg.pikz.shards.api.interfaces.LeaderboardAPI;

ShardsAPI api = ShardsAPIProvider.getAPI();
LeaderboardAPI leaderboard = api.getLeaderboardManager();
```

***

## Methods

### open(Player player)

Opens the leaderboard GUI for a player.

```java
void open(Player player)
```

**Parameters:**

* `player` - The player to show the leaderboard to

**Example:**

```java
leaderboard.open(player);
```

***

## Complete Example

```java
ShardsAPI api = ShardsAPIProvider.getAPI();

// Check if leaderboard feature is enabled before opening
if (api.isFeatureEnabled("leaderboard")) {
    api.getLeaderboardManager().open(player);
} else {
    player.sendMessage("Leaderboard is currently disabled!");
}
```

***

## Command Integration Example

```java
@Override
public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!(sender instanceof Player)) {
        sender.sendMessage("Only players can use this command!");
        return true;
    }

    Player player = (Player) sender;
    ShardsAPI api = ShardsAPIProvider.getAPI();

    if (api.isFeatureEnabled("leaderboard")) {
        api.getLeaderboardManager().open(player);
    } else {
        player.sendMessage("Leaderboard is disabled!");
    }

    return true;
}
```
