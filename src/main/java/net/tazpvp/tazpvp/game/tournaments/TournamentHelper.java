package net.tazpvp.tazpvp.game.tournaments;

import net.tazpvp.tazpvp.objects.PartyObject;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class TournamentHelper {
    public static Tournament currentTournament;

    public static boolean isInTournament(Player p) {
        for (PartyObject party : currentTournament.getParticipants()) {
            for (OfflinePlayer op : party.getMembers()) {
                if (op.getUniqueId().equals(p.getUniqueId())) {
                    return true;
                }
            }
        }
        return false;
    }
}
