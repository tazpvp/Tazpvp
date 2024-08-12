package net.tazpvp.tazpvp.helpers;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.game.duels.Duel;
import net.tazpvp.tazpvp.wrappers.PlayerWrapper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DuelHelper {

    public static void handlePlayerInteractions(Player p, boolean dueling) {
        Duel currentDuel = Duel.getDuel();
        if (dueling) {
            for (Player outsider : Bukkit.getOnlinePlayers()) {
                if (!currentDuel.getDuelers().contains(outsider.getUniqueId())) {
                    for (UUID duelerID : currentDuel.getDuelers()) {
                        Player dueler = Bukkit.getPlayer(duelerID);
                        if (dueler == null) continue;
                        dueler.hidePlayer(Tazpvp.getInstance(), outsider);
                    }
                }
            }
        }
    }
}
