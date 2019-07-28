package NaiculS.EnergyBending;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.event.PlayerCooldownChangeEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class Energybend {
    public static String elements;

    public Energybend() {
    }

    public static void EnergybendPlayer(Player energybender, Player target, EnergybendEvent.Result result) {
        BendingPlayer p = BendingPlayer.getBendingPlayer(energybender);
        if (result.equals(EnergybendEvent.Result.REMOVED) && EBMethods.canEnergybend(energybender) && EBMethods.canEnergybendPlayer(energybender, target)) {
            if (energybender.getHealth() > target.getHealth()) {
                EnergybendEvent ebevent = new EnergybendEvent(energybender, target, EnergybendEvent.Result.REMOVED);
                ProjectKorra.plugin.getServer().getPluginManager().callEvent(ebevent);
                if (ebevent.isCancelled()) {
                    return;
                }

                EBMethods.restoreBending(target);
                p.addCooldown("Energybending", 20L);
                target.sendMessage(ChatColor.RED + energybender.getDisplayName() + " has removed your element(s) with Energybending! Your bending will be restored upon server restart/reload, or if an Avatar restores your bending!");
                energybender.sendMessage(ChatColor.GREEN + "You have removed " + target.getDisplayName() + "'s element(s) with your Energybending! Energybend them again to restore their element(s)!");
            } else {
                energybender.setLastDamageCause(new EntityDamageEvent(target, DamageCause.ENTITY_ATTACK, 0.0D));
                energybender.setHealth(0.0D);
                target.sendMessage(ChatColor.GOLD + energybender.getName() + " tried to Energybend you, but your spirit was more powerful than theirs, and you have kept your element(s)");
                energybender.sendMessage(ChatColor.RED + target.getDisplayName() + "'s spirit was too strong for your energybending!");
            }
        }

        if (result.equals(EnergybendEvent.Result.RESTORED) && EnergybendAbility.playerelements.containsKey(target.getUniqueId()) && EBMethods.canEnergybend(energybender) && EBMethods.canEnergybendPlayer(energybender, target)) {
            EBMethods.restoreBending(target);
            p.addCooldown("Energybending", 20L);
            target.sendMessage(ChatColor.RED + energybender.getDisplayName() + " has restored your element(s) with Energybending!");
            energybender.sendMessage(ChatColor.GREEN + "You have restored " + target.getDisplayName() + "'s element(s) with your Energybending!");
        }

    }
}
