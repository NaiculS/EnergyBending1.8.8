package me.NaiculS.EnergyBending;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;


public class EBListener implements Listener {
    public EBListener() {
    }

    @EventHandler
    public void EnergybendingHandler(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (!event.isCancelled()) {
            if (event.getRightClicked() instanceof Player) {
                Player t = (Player)event.getRightClicked();
                if (EnergybendAbility.playerelements.isEmpty() && !EnergybendAbility.playerelements.containsKey(t.getUniqueId())) {
                    Energybend.EnergybendPlayer(player, player, EnergybendEvent.Result.REMOVED);
                } else {
                    Energybend.EnergybendPlayer(player, player, EnergybendEvent.Result.RESTORED);
                }
            }
        }
    }

    @EventHandler
    public void onEnergybend(EnergybendEvent event) {
        Location loc = event.getLocation();
        loc.getWorld().spawnParticle(Particle.SPELL_MOB, loc, 10, (float)Math.random(), (float)Math.random(), (float)Math.random(), 0);
    }
}
