package net.tazpvp.tazpvp.helpers;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.enums.CC;
import net.tazpvp.tazpvp.objects.DuelObject;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class DuelHelper {

    public static DuelObject isSpectating(UUID id) {
        for (DuelObject duel : DuelObject.activeDuels) {
            for (UUID spectatorID : duel.getSpectators()) {
                if (id.equals(spectatorID)) {
                    return duel;
                }
            }
        }
        return null;
    }

    public static void send(Player p, String msg) {
        p.sendMessage(CC.DARK_AQUA + "Duel ❯ " + CC.AQUA + msg);
        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1,1);
    }

    public static void sendAll(List<UUID> duelers, String msg) {
        for (UUID uuid1 : duelers) {
            final Player dueler = Bukkit.getPlayer(uuid1);
            if (dueler != null) {
                send(dueler, msg);
            }
        }
    }

    public static void sendTitleAll(List<UUID> duelers, String msg, String sub) {
        for (UUID uuid1 : duelers) {
            final Player dueler = Bukkit.getPlayer(uuid1);
            if (dueler != null) {
                dueler.sendTitle(msg, sub, 5, 10, 5);
            }
        }
    }
}
