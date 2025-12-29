package bg.pikz.shards.api.interfaces;


import org.bukkit.entity.Player;

public interface ShopAPI {

    void openShop(Player player);

    void reloadShop();

    boolean isShopLoaded();
}