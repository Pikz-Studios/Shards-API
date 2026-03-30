# complete example

A full example plugin demonstrating all Shards API features.

***

## Main Plugin Class

```java
package com.example.myplugin;

import bg.pikz.shards.api.ShardsAPIProvider;
import bg.pikz.shards.api.interfaces.*;
import bg.pikz.shards.api.events.ShardGainEvent;
import bg.pikz.shards.api.events.ShardLoseEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class MyPlugin extends JavaPlugin implements Listener {

    private ShardsAPI shardsAPI;
    private boolean shardsEnabled = false;

    @Override
    public void onEnable() {
        // Hook into Shards
        if (getServer().getPluginManager().getPlugin("Shards") != null) {
            shardsAPI = ShardsAPIProvider.getAPI();
            shardsEnabled = true;
            getLogger().info("Successfully hooked into Shards v" + shardsAPI.getVersion());

            // Register event listeners
            getServer().getPluginManager().registerEvents(this, this);
        } else {
            getLogger().warning("Shards not found! Shard features disabled.");
        }
    }

    /**
     * Check if Shards integration is available
     */
    public boolean isShardsEnabled() {
        return shardsEnabled;
    }

    /**
     * Get a player's shard balance
     */
    public long getPlayerShards(Player player) {
        if (!shardsEnabled) return 0;

        OfflineUserAPI user = shardsAPI.getUserMap().get(player.getUniqueId());
        return user != null ? user.getShards() : 0;
    }

    /**
     * Get a player's shard balance by UUID (works for offline players)
     */
    public long getPlayerShards(UUID uuid) {
        if (!shardsEnabled) return 0;

        OfflineUserAPI user = shardsAPI.getUserMap().get(uuid);
        return user != null ? user.getShards() : 0;
    }

    /**
     * Give shards to a player
     */
    public void giveShards(Player player, long amount) {
        if (!shardsEnabled) return;

        OfflineUserAPI user = shardsAPI.getUserMap().get(player.getUniqueId());
        if (user != null) {
            user.addShards(amount);
        }
    }

    /**
     * Take shards from a player
     * @return true if successful, false if player doesn't have enough
     */
    public boolean takeShards(Player player, long amount) {
        if (!shardsEnabled) return false;

        OfflineUserAPI user = shardsAPI.getUserMap().get(player.getUniqueId());
        if (user != null && user.getShards() >= amount) {
            user.takeShard(amount);
            return true;
        }
        return false;
    }

    /**
     * Check if player can afford a cost
     */
    public boolean canAfford(Player player, long cost) {
        return getPlayerShards(player) >= cost;
    }

    /**
     * Open the shop for a player
     */
    public void openShop(Player player) {
        if (!shardsEnabled) return;

        if (shardsAPI.isFeatureEnabled("shop")) {
            shardsAPI.getShopManager().openShop(player);
        }
    }

    /**
     * Open the leaderboard for a player
     */
    public void openLeaderboard(Player player) {
        if (!shardsEnabled) return;

        if (shardsAPI.isFeatureEnabled("leaderboard")) {
            shardsAPI.getLeaderboardManager().open(player);
        }
    }

    /**
     * Teleport player to AFK zone
     */
    public void teleportToAFK(Player player) {
        if (!shardsEnabled) return;

        if (shardsAPI.isFeatureEnabled("afk")) {
            shardsAPI.getAfkManager().teleportToAFK(player);
        }
    }

    /**
     * Get player's generation bonus
     */
    public long getGenerationBonus(Player player) {
        if (!shardsEnabled) return 0;
        return shardsAPI.getGenMultiplayer().getGenShards(player);
    }

    /**
     * Get player's kill bonus
     */
    public long getKillBonus(Player player) {
        if (!shardsEnabled) return 0;
        return shardsAPI.getKillMultiplayer().getKillShards(player);
    }

    // ==================== EVENT HANDLERS ====================

    @EventHandler
    public void onShardGain(ShardGainEvent event) {
        Player player = event.getPlayer();

        // Example: Bonus shards for players with custom permission
        if (player.hasPermission("myplugin.doubleshards")) {
            if (event.getReason() == ShardGainEvent.GainReason.REGION_GENERATION) {
                event.setAmount(event.getAmount() * 2);
                player.sendMessage("Double shard bonus applied!");
            }
        }

        // Example: Log all gains
        getLogger().info(player.getName() + " gained " + event.getAmount() +
                        " shards (" + event.getReason() + ")");
    }

    @EventHandler
    public void onShardLose(ShardLoseEvent event) {
        Player player = event.getPlayer();

        // Example: 10% discount for VIP on shop purchases
        if (event.getReason() == ShardLoseEvent.LoseReason.SHOP_PURCHASE) {
            if (player.hasPermission("myplugin.discount")) {
                long discount = (long) (event.getAmount() * 0.10);
                event.setAmount(event.getAmount() - discount);
                player.sendMessage("VIP discount: -" + discount + " shards!");
            }
        }

        // Example: Log all purchases
        getLogger().info(player.getName() + " spent " + event.getAmount() +
                        " shards (" + event.getReason() + ")");
    }
}
```

***

## Helper Utility Class

```java
package com.example.myplugin.util;

import bg.pikz.shards.api.ShardsAPIProvider;
import bg.pikz.shards.api.interfaces.OfflineUserAPI;
import bg.pikz.shards.api.interfaces.ShardsAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class ShardsUtil {

    /**
     * Check if Shards plugin is available
     */
    public static boolean isShardsAvailable() {
        return Bukkit.getPluginManager().getPlugin("Shards") != null;
    }

    /**
     * Get the Shards API instance
     */
    public static ShardsAPI getAPI() {
        if (!isShardsAvailable()) {
            throw new IllegalStateException("Shards plugin is not loaded!");
        }
        return ShardsAPIProvider.getAPI();
    }

    /**
     * Get top players sorted by shard balance
     */
    public static List<OfflineUserAPI> getTopPlayers(int limit) {
        if (!isShardsAvailable()) return Collections.emptyList();

        return getAPI().getUserMap().values().stream()
                .sorted((a, b) -> Long.compare(b.getShards(), a.getShards()))
                .limit(limit)
                .collect(Collectors.toList());
    }

    /**
     * Get player's rank on the leaderboard
     */
    public static int getPlayerRank(UUID playerUuid) {
        if (!isShardsAvailable()) return -1;

        List<OfflineUserAPI> sorted = getAPI().getUserMap().values().stream()
                .sorted((a, b) -> Long.compare(b.getShards(), a.getShards()))
                .collect(Collectors.toList());

        for (int i = 0; i < sorted.size(); i++) {
            if (sorted.get(i).getUuid().equals(playerUuid)) {
                return i + 1;
            }
        }
        return -1;
    }

    /**
     * Format shard amount with commas
     */
    public static String formatShards(long amount) {
        return String.format("%,d", amount);
    }

    /**
     * Transfer shards between players
     */
    public static boolean transfer(UUID from, UUID to, long amount) {
        if (!isShardsAvailable()) return false;

        ShardsAPI api = getAPI();
        OfflineUserAPI sender = api.getUserMap().get(from);
        OfflineUserAPI receiver = api.getUserMap().get(to);

        if (sender == null || receiver == null) return false;
        if (sender.getShards() < amount) return false;

        sender.takeShard(amount);
        receiver.addShards(amount);
        return true;
    }
}
```

***

## Command Example

```java
package com.example.myplugin.commands;

import com.example.myplugin.MyPlugin;
import com.example.myplugin.util.ShardsUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShardsCommand implements CommandExecutor {

    private final MyPlugin plugin;

    public ShardsCommand(MyPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        Player player = (Player) sender;

        if (!plugin.isShardsEnabled()) {
            player.sendMessage("Shards integration is not available!");
            return true;
        }

        if (args.length == 0) {
            showHelp(player);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "balance":
            case "bal":
                showBalance(player);
                break;

            case "shop":
                plugin.openShop(player);
                break;

            case "leaderboard":
            case "top":
                plugin.openLeaderboard(player);
                break;

            case "afk":
                plugin.teleportToAFK(player);
                break;

            case "rank":
                showRank(player);
                break;

            default:
                showHelp(player);
        }

        return true;
    }

    private void showHelp(Player player) {
        player.sendMessage("=== My Plugin Commands ===");
        player.sendMessage("/mp balance - View your shards");
        player.sendMessage("/mp shop - Open the shop");
        player.sendMessage("/mp leaderboard - View top players");
        player.sendMessage("/mp afk - Go to AFK zone");
        player.sendMessage("/mp rank - View your rank");
    }

    private void showBalance(Player player) {
        long balance = plugin.getPlayerShards(player);
        long genBonus = plugin.getGenerationBonus(player);
        long killBonus = plugin.getKillBonus(player);

        player.sendMessage("=== Your Shards Info ===");
        player.sendMessage("Balance: " + ShardsUtil.formatShards(balance));
        player.sendMessage("Gen Bonus: +" + genBonus + "/cycle");
        player.sendMessage("Kill Bonus: +" + killBonus + "/kill");
    }

    private void showRank(Player player) {
        int rank = ShardsUtil.getPlayerRank(player.getUniqueId());
        long balance = plugin.getPlayerShards(player);

        if (rank > 0) {
            player.sendMessage("Your rank: #" + rank + " with " + ShardsUtil.formatShards(balance) + " shards");
        } else {
            player.sendMessage("Could not determine your rank.");
        }
    }
}
```

***

## plugin.yml

```yaml
name: MyPlugin
version: 1.0.0
main: com.example.myplugin.MyPlugin
api-version: 1.20
softdepend: [Shards]

commands:
  mp:
    description: MyPlugin main command
    usage: /mp <subcommand>
```

***

## build.gradle

```groovy
plugins {
    id 'java'
}

group = 'com.example'
version = '1.0.0'

repositories {
    mavenCentral()
    maven { url 'https://repo.papermc.io/repository/maven-public/' }
    maven { url 'https://jitpack.io' }
}

dependencies {
    compileOnly 'io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT'
    compileOnly 'com.github.Pikz-Studios:Shards-API:main-SNAPSHOT'
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
```
