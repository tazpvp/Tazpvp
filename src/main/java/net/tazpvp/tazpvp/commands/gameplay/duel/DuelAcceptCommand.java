package net.tazpvp.tazpvp.commands.gameplay.duel;

import lombok.NonNull;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.commands.admin.tazload.TazloadCommand;
import net.tazpvp.tazpvp.duels.Duel;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.functions.CombatTagFunctions;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;
import world.ntdi.postglam.data.Tuple;

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

        if (PlayerWrapper.getPlayer(p).getDuel() != null) {
            p.sendMessage(CC.RED + "You cannot use this command while dueling.");
            return true;
        }

        if (PlayerWrapper.getPlayer(p).getSpectating() != null) {
            p.sendMessage(CC.RED + "You cannot use this command while spectating.");
            return true;
        }

        if (CombatTagFunctions.isInCombat(p.getUniqueId())) {
            p.sendMessage( CC.RED + "You cannot use this command while in combat.");
            return true;
        }

        if (TazloadCommand.tazloading) {
            p.sendMessage(CC.RED + "This feature is disabled while the server is reloading.");
            return true;
        }

        Tuple<Boolean, Duel> duelPair = requested(p);

        if (duelPair.getA()) {
            Duel duel = duelPair.getB();

            duel.initialize();
            new BukkitRunnable() {
                public void run() {
                    duel.begin();
                }
            }.runTaskLater(Tazpvp.getInstance(), 20*2L);
            duel.getDUELERS().forEach(d -> {
                Bukkit.getPlayer(d).sendMessage(CC.BOLD + "" + CC.GOLD + "The duel will begin shortly.");
            });
        } else {
            p.sendMessage(CC.RED + "No one sent you a duel request");
        }
        return true;
    }

    private Tuple<Boolean, Duel> requested(Player p) {
        for (Duel duel : Duel.duels.keySet()) {
            if (Duel.duels.get(duel) == p.getUniqueId()) {
                return new Tuple<>(true, duel);
            }
        }
        return new Tuple<>(false, null);
    }
}
