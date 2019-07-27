package NaiculS.EnergyBending;

import com.projectkorra.projectkorra.BendingPlayer;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class EnergybendEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private Player target;
    private EnergybendEvent.Result eventresult;
    private boolean cancelled;

    public EnergybendEvent(Player energybender, Player tplayer, EnergybendEvent.Result result) {
        this.player = energybender;
        this.target = tplayer;
    }

    public Player getEnergybender() {
        return this.player;
    }

    public Player getTarget() {
        return this.target;
    }

    public String getEnergybenderName() {
        return this.player.getName();
    }

    public String getTargetName() {
        return this.target.getName();
    }

    public String getEnergybenderUUID() {
        return this.player.getUniqueId().toString();
    }

    public String getTargetUUID() {
        return this.target.getUniqueId().toString();
    }

    public String getEnergybenderDisplayName() {
        return this.player.getDisplayName();
    }

    public String getTargetDisplayName() {
        return this.target.getDisplayName();
    }

    public String getEnergybenderIP() {
        return this.player.getAddress().getHostName();
    }

    public String getTargetIP() {
        return this.player.getAddress().getHostName();
    }

    public GameMode getEnergybenderGameMode() {
        return this.player.getGameMode();
    }

    public GameMode getTargetGameMode() {
        return this.target.getGameMode();
    }

    public BendingPlayer getEnergybenderBPlayer() {
        return BendingPlayer.getBendingPlayer(this.getEnergybenderName());
    }

    public BendingPlayer getTargetBPlayer() {
        return BendingPlayer.getBendingPlayer(this.getTargetName());
    }

    public EnergybendEvent.Result getResult() {
        return this.eventresult;
    }

    public Location getLocation() {
        return this.getEnergybender().getLocation();
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public static enum Result {
        RESTORED,
        REMOVED;

        private Result() {
        }
    }
}
