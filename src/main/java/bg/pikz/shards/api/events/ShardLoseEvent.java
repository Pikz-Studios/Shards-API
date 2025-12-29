package bg.pikz.shards.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ShardLoseEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled = false;

    private final Player player;
    private long amount;
    private final LoseReason reason;

    public ShardLoseEvent(Player player, long amount, LoseReason reason) {
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

    public enum LoseReason {
        SHOP_PURCHASE,
        PAYMENT_SENT,
        ADMIN_TAKE,
        OTHER
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public Player getPlayer() {
        return player;
    }

    public long getAmount() {
        return amount;
    }

    public LoseReason getReason() {
        return reason;
    }
}