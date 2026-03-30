# offline user api

Manage player shard balances. Works for both online and offline players.

***

## Package

```
bg.pikz.shards.api.interfaces.OfflineUserAPI
```

***

## Accessing OfflineUserAPI

```java
import bg.pikz.shards.api.ShardsAPIProvider;
import bg.pikz.shards.api.interfaces.OfflineUserAPI;

ShardsAPI api = ShardsAPIProvider.getAPI();
OfflineUserAPI user = api.getUserMap().get(player.getUniqueId());
```

***

## Methods

### getUuid()

Gets the player's UUID.

```java
UUID getUuid()
```

**Returns:** The player's UUID

***

### getLastUsername()

Gets the player's last known username.

```java
String getLastUsername()
```

**Returns:** The last known username

***

### getShards()

Gets the current amount of shards.

```java
long getShards()
```

**Returns:** The shard count

**Example:**

```java
long balance = user.getShards();
player.sendMessage("Your balance: " + balance + " shards");
```

***

### setShards(long shards)

Sets the shard amount to an exact value.

```java
void setShards(long shards)
```

**Parameters:**

* `shards` - The new shard amount

**Example:**

```java
user.setShards(1000);
```

***

### addShard()

Adds a single shard to the player's balance.

```java
void addShard()
```

**Example:**

```java
user.addShard(); // Adds 1 shard
```

***

### addShards(long shardsAmount)

Adds multiple shards to the player's balance.

```java
void addShards(long shardsAmount)
```

**Parameters:**

* `shardsAmount` - The amount of shards to add

**Example:**

```java
user.addShards(100); // Adds 100 shards
```

***

### takeShard(long shards)

Removes shards from the player's balance.

```java
void takeShard(long shards)
```

**Parameters:**

* `shards` - The amount of shards to remove

**Example:**

```java
user.takeShard(50); // Removes 50 shards
```

***

## Complete Example

```java
ShardsAPI api = ShardsAPIProvider.getAPI();
UUID playerUuid = player.getUniqueId();

// Get user from the user map
OfflineUserAPI user = api.getUserMap().get(playerUuid);

if (user != null) {
    // Get current balance
    long balance = user.getShards();
    player.sendMessage("Current balance: " + balance);

    // Add shards
    user.addShards(100);
    player.sendMessage("Added 100 shards!");

    // Check if player can afford something
    long cost = 50;
    if (user.getShards() >= cost) {
        user.takeShard(cost);
        player.sendMessage("Purchase successful!");
    } else {
        player.sendMessage("Not enough shards!");
    }

    // Set exact balance
    user.setShards(1000);

    // Get player info
    String username = user.getLastUsername();
    UUID uuid = user.getUuid();
}
```

***

## Purchase Helper Method

```java
public boolean purchaseItem(Player player, long cost) {
    ShardsAPI api = ShardsAPIProvider.getAPI();
    OfflineUserAPI user = api.getUserMap().get(player.getUniqueId());

    if (user == null) {
        player.sendMessage("Error: Could not find your shard data!");
        return false;
    }

    if (user.getShards() < cost) {
        player.sendMessage("You need " + cost + " shards but only have " + user.getShards());
        return false;
    }

    user.takeShard(cost);
    player.sendMessage("Purchase successful! New balance: " + user.getShards());
    return true;
}
```
