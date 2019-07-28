package NaiculS.EnergyBending;

import com.projectkorra.projectkorra.ProjectKorra;
import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.AvatarAbility;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.permissions.PermissionDefault;

public abstract class EnergybendAbility extends AvatarAbility implements AddonAbility { //just added abstract, remove if causes issues
    public static EnergybendAbility ability;
    public static ConcurrentHashMap<UUID, String> playerelements = new ConcurrentHashMap();

    public EnergybendAbility(Player player) {
        super(player);
    }

    public String getDescription() {
        return "Crouch (default: shift) and hold with this ability bound, then right click on a player and you will remove/restore their bending! This ability has a 20 second cooldown and can only be used in the Avatar State. This ability cannot be used upon a target in the Avatar State!";
    }

    public String getAuthor() {
        return "AlexTheCoder, updated to 1.8.8 by NaiculS";
    }

    public String getVersion() {
        return "v3.0";
    }

    public boolean isShiftAbility() {
        return true;
    }

    public boolean isHarmlessAbility() {
        return false;
    }

    public void load() {
        ProjectKorra.plugin.getLogger().info("Enabling Addon Ability " + this.getName() + " by " + this.getAuthor());
        ProjectKorra.plugin.getServer().getPluginManager().registerEvents(new NaiculS.EnergyBending.EBListener(), ProjectKorra.plugin);
        ProjectKorra.plugin.getServer().getPluginManager().addPermission((new EBPermissions()).energybendPerm);
        ProjectKorra.plugin.getServer().getPluginManager().getPermission("bending.ability.Energybending").setDefault(PermissionDefault.OP);
    }

    public void stop() {
        ProjectKorra.plugin.getLogger().info("Disabled " + this.getName());
        PlayerInteractEntityEvent.getHandlerList().unregister(new NaiculS.EnergyBending.EBListener());
        ProjectKorra.plugin.getServer().getPluginManager().removePermission((new EBPermissions()).energybendPerm);
        if (!playerelements.isEmpty()) {
            Iterator var2 = playerelements.keySet().iterator();

            while(var2.hasNext()) {
                UUID playeruuid = (UUID)var2.next();
                Player player = Bukkit.getPlayer(playeruuid);
                EBMethods.restoreBending(player);
            }

            playerelements.clear();
        }

    }
}
