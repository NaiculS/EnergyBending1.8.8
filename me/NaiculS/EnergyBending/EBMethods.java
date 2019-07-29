package NaiculS.EnergyBending;


import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.CoreAbility;
import org.bukkit.entity.Player;

public class EBMethods {
    public EBMethods() {
    }

    public static boolean canEnergybend(Player player) {
        BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);
        CoreAbility ability = CoreAbility.getAbility(EnergybendAbility.class);
        if (!player.isSneaking()) {
            return false;
        } else if (!bPlayer.isAvatarState()) {
            return false;
        } else if (bPlayer.isChiBlocked()) {
            return false;
        } else if (bPlayer.isPermaRemoved()) {
            return false;
        } else if (!bPlayer.canBend(ability)) {
            return false;
        } else if (!bPlayer.getBoundAbilityName().equalsIgnoreCase("EnergyBending")) {
            return false;
        } else if (GeneralMethods.isRegionProtectedFromBuild(player, "Energybending", player.getLocation())) {
            return false;
        } else {
            return !bPlayer.isOnCooldown("Energybending");
        }
    }

    public static boolean canEnergybendPlayer(Player player, Player target) {
        BendingPlayer bTarget = (BendingPlayer) target;
        return !bTarget.isAvatarState();
    }

    public static boolean isPlayerResistant(Player player, Player attacker) {
        return player.getHealth() > attacker.getHealth();
    }

    public static boolean willBenderDie(Player player, Player target) {
        return player.getHealth() < target.getHealth();
    }

    public static void restoreBending(Player player) {
        if (EnergybendAbility.playerelements.containsKey(player.getUniqueId())) {
            String elements = (String)EnergybendAbility.playerelements.get(player.getUniqueId());
            BendingPlayer target = BendingPlayer.getBendingPlayer(player);
            if (elements.contains("a")) {
                target.addElement(Element.AIR);
            }

            if (elements.contains("e")) {
                target.addElement(Element.EARTH);
            }

            if (elements.contains("f")) {
                target.addElement(Element.FIRE);
            }

            if (elements.contains("w")) {
                target.addElement(Element.WATER);
            }

            GeneralMethods.saveElements(target);
        }

    }

    public static void removeBending(Player player) {
        String elements = "";
        BendingPlayer t = BendingPlayer.getBendingPlayer(player);
        if (t.getElements().contains(Element.AIR)) {
            elements = elements + "a";
        }

        if (t.getElements().contains(Element.EARTH)) {
            elements = elements + "e";
        }

        if (t.getElements().contains(Element.WATER)) {
            elements = elements + "w";
        }

        if (t.getElements().contains(Element.FIRE)) {
            elements = elements + "f";
        }

        if (t.getElements().contains(Element.CHI)) {
            t.setElement(Element.CHI);
        } else {
            t.setElement((Element)null);
        }

        GeneralMethods.removeUnusableAbilities(player.getName());
        EnergybendAbility.playerelements.put(player.getUniqueId(), elements);
        GeneralMethods.saveElements(t);
    }
}