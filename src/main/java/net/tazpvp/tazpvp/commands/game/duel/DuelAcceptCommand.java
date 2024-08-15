package net.tazpvp.tazpvp.commands.game.duel;

import lombok.NonNull;
import net.tazpvp.tazpvp.commands.admin.tazload.TazloadCommand;
import net.tazpvp.tazpvp.objects.DuelObject;
import net.tazpvp.tazpvp.helpers.CombatTagHelper;
import net.tazpvp.tazpvp.wrappers.PlayerWrapper;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

import java.util.UUID;

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

        PlayerWrapper pw = PlayerWrapper.getPlayer(p);

        if (pw.getDuel() != null) {
            p.sendMessage(DuelObject.prefix + "You cannot use this command while dueling.");
            return true;
        }

        if (CombatTagHelper.isInCombat(p.getUniqueId())) {
            p.sendMessage( DuelObject.prefix + "You cannot use this command while in combat.");
            return true;
        }

        if (TazloadCommand.tazloading) {
            p.sendMessage(DuelObject.prefix + "This feature is disabled while the server is reloading.");
            return true;
        }

        if (args.length < 1) {
            p.sendMessage(DuelObject.prefix + "Incorrect Usage: /duel accept <player>");
            return true;
        }

        if (pw.getDuelRequests().isEmpty()) {
            p.sendMessage(DuelObject.prefix + "No one sent you a duel request.");
            return true;
        }

        Player senderPlayer = Bukkit.getPlayer(args[0]);
        if (senderPlayer == null) {
            p.sendMessage(DuelObject.prefix + "Invalid Player.");
            return true;
        }
        UUID senderID = senderPlayer.getUniqueId();

        for (UUID id : pw.getDuelRequests().keySet()) {
            if (id.equals(senderID)) {
                DuelObject duel = pw.getDuelRequests().get(id);
                if (duel == null) {
                    pw.getDuelRequests().remove(id);
                    return true;
                }

                senderPlayer.sendMessage(DuelObject.prefix + p.getName() + " has accepted your duel request. You will teleport to the duel arena shortly.");
                p.sendMessage(DuelObject.prefix + "You have accepted " + senderPlayer.getName() + "'s duel request. You will teleport to the duel arena shortly.");

                pw.getDuelRequests().clear();
                PlayerWrapper senderWrapper = PlayerWrapper.getPlayer(senderID);
                senderWrapper.getDuelRequests().clear();

                DuelObject.activeDuels.add(duel);
                duel.initialize();
            }
        }

        return true;
    }
}
