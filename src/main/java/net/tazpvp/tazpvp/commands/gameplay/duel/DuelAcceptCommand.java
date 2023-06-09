package net.tazpvp.tazpvp.commands.gameplay.duel;

import lombok.NonNull;
import net.tazpvp.tazpvp.duels.Duel;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.javatuples.Pair;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

import java.util.List;

public class DuelAcceptCommand extends NRCommand {
    public DuelAcceptCommand() {
        super(new Label("accept", null));
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NonNull String[] args) {

        if (!(sender instanceof Player p)) {
            sendNoPermission(sender);
            return true;
        }

        Pair<Boolean, Duel> duelPair = requested(p);

        if (duelPair.getValue0()) {
            Duel duel = duelPair.getValue1();

            duel.begin();
            duel.getDUELERS().forEach(d -> {
                Bukkit.getPlayer(d).sendMessage("Duel Commencing!");
            });
        } else {
            p.sendMessage("No one sent you a duel request");
        }
        return true;
    }

    private Pair<Boolean, Duel> requested(Player p) {
        for (Duel duel : Duel.duels.keySet()) {
            if (Duel.duels.get(duel) == p.getUniqueId()) {
                return Pair.with(true, duel);
            }
        }
        return Pair.with(false, null);
    }
}
