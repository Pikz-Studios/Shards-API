package bg.pikz.shards.api.events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;


public class ShardGainEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private boolean cancelled = false;
    @Getter
    private final Player player;
    @Getter@Setter
    private long amount;
    @Getter
    private final GainReason reason;

    public ShardGainEvent(Player player, long amount, GainReason reason) {
        this.player = player;
        this.amount = amount;
        this.reason = reason;
    }



    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public enum GainReason {
        REGION_GENERATION,
        KILL,
        ADMIN_GIVE,
        PAYMENT_RECEIVED,
        SHOP_PURCHASE,
        OTHER
    }
}