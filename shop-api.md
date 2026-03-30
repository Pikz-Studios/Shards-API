# shop api

Control the shop system.

***

## Package

```
bg.pikz.shards.api.interfaces.ShopAPI
```

***

## Accessing ShopAPI

```java
import bg.pikz.shards.api.ShardsAPIProvider;
import bg.pikz.shards.api.interfaces.ShopAPI;

ShardsAPI api = ShardsAPIProvider.getAPI();
ShopAPI shopManager = api.getShopManager();
```

***

## Methods

### openShop(Player player)

Opens the shop GUI for a player.

```java
void openShop(Player player)
```

**Parameters:**

* `player` - The player to show the shop to

**Example:**

```java
shopManager.openShop(player);
```

***

### reloadShop()

Reloads the shop configuration from files.

```java
void reloadShop()
```

**Example:**

```java
shopManager.reloadShop();
player.sendMessage("Shop reloaded!");
```

***

### isShopLoaded()

Checks if the shop has been loaded successfully.

```java
boolean isShopLoaded()
```

**Returns:** `true` if the shop is loaded and ready

**Example:**

```java
if (shopManager.isShopLoaded()) {
    shopManager.openShop(player);
} else {
    player.sendMessage("Shop is not available!");
}
```

***

## Complete Example

```java
ShardsAPI api = ShardsAPIProvider.getAPI();
ShopAPI shopManager = api.getShopManager();

// Check if shop feature is enabled and loaded
if (api.isFeatureEnabled("shop") && shopManager.isShopLoaded()) {
    shopManager.openShop(player);
} else {
    player.sendMessage("Shop is currently unavailable!");
}
```

***

## Admin Reload Command Example

```java
@Override
public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!sender.hasPermission("myplugin.reloadshop")) {
        sender.sendMessage("No permission!");
        return true;
    }

    ShardsAPI api = ShardsAPIProvider.getAPI();
    ShopAPI shopManager = api.getShopManager();

    shopManager.reloadShop();
    sender.sendMessage("Shards shop has been reloaded!");

    return true;
}
```

***

## NPC Integration Example

```java
@EventHandler
public void onNPCClick(NPCRightClickEvent event) {
    NPC npc = event.getNPC();
    Player player = event.getClicker();

    // Check if this is the shop NPC
    if (npc.getName().equals("Shop Keeper")) {
        ShardsAPI api = ShardsAPIProvider.getAPI();

        if (api.isFeatureEnabled("shop")) {
            api.getShopManager().openShop(player);
        } else {
            player.sendMessage("The shop is closed!");
        }
    }
}
```
