# Accessing the API

Use `ShardsAPIProvider` to access the API:

```java
import bg.pikz.shards.api.ShardsAPIProvider;
import bg.pikz.shards.api.interfaces.ShardsAPI;

public class MyPlugin extends JavaPlugin {

    private ShardsAPI shardsAPI;

    @Override
    public void onEnable() {
        // Check if Shards is loaded
        if (getServer().getPluginManager().getPlugin("Shards") != null) {
            shardsAPI = ShardsAPIProvider.getAPI();
            getLogger().info("Hooked into Shards v" + shardsAPI.getVersion());
        } else {
            getLogger().warning("Shards not found! Some features will be disabled.");
        }
    }
}
```
