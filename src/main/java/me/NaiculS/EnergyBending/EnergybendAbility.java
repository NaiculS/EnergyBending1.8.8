package me.NaiculS.EnergyBending;

import com.projectkorra.projectkorra.ProjectKorra;
import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.AvatarAbility;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.permissions.PermissionDefault;

public class EnergybendAbility extends AvatarAbility implements AddonAbility {
    public static EnergybendAbility ability;
    public static ConcurrentHashMap<UUID, String> playerelements = new ConcurrentHashMap();
    private Location location = player.getEyeLocation();

    public EnergybendAbility(final Player player) {
        super(player);
        start();
    }

    @Override
    public long getCooldown() {
        return 20000;
    }

    @Override
    public Location getLocation(){
        return location;
    }

    @Override
    public String getDescription() {
        return "Crouch (default: shift) and hold with this ability bound, then right click on a player and you will remove/restore their bending! This ability has a 20 second cooldown and can only be used in the Avatar State. This ability cannot be used upon a target in the Avatar State!";
    }

    @Override
    public String getInstructions(){
        return "Crouch (default: shift) and hold with this ability bound, then right click on a player and you will remove/restore their bending!";
    }

    @Override
    public String getName(){
        return "Energybending";
    }

    @Override
    public void progress(){
        if (!bPlayer.canBendIgnoreBindsCooldowns(this)) {
            remove();
            return;
        }
        
    }

    @Override
    public String getAuthor() {
        return "AlexTheCoder, updated to 1.8.8 by NaiculS";
    }

    @Override
    public String getVersion() {
        return "v3.0";
    }

    @Override
    public boolean isSneakAbility() {
        return true;
    }

    @Override
    public boolean isHarmlessAbility() {
        return false;
    }

    @Override
    public void remove(){
        super.remove();
        bPlayer.addCooldown(this);
    }

    @Override
    public void load() {
        ProjectKorra.plugin.getLogger().info("Enabling Addon Ability " + this.getName() + " by " + this.getAuthor());
        ProjectKorra.plugin.getServer().getPluginManager().registerEvents(new EBListener(), ProjectKorra.plugin);
        ProjectKorra.plugin.getServer().getPluginManager().addPermission((new EBPermissions()).energybendPerm);
        ProjectKorra.plugin.getServer().getPluginManager().getPermission("bending.ability.Energybending").setDefault(PermissionDefault.OP);
    }

    @Override
    public void stop() {
        ProjectKorra.plugin.getLogger().info("Disabled " + this.getName());
        PlayerInteractEntityEvent.getHandlerList().unregister(new EBListener());
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
        super.remove();
    }
}
