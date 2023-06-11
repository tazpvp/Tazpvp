package net.tazpvp.tazpvp.commands.gameplay.duel;

import lombok.NonNull;
import net.tazpvp.tazpvp.commands.admin.tazload.TazloadCommand;
import net.tazpvp.tazpvp.duels.Duel;
import net.tazpvp.tazpvp.duels.type.Classic;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.functions.CombatTagFunctions;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.command.simple.Completer;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

import java.util.List;

public class DuelSendCommand extends NRCommand {
    public DuelSendCommand() {
        super(new Label("send", null));
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NonNull String[] args) {
        if (!(sender instanceof Player p)) {
            sendNoPermission(sender);
            return true;
        }

        if (args.length < 2) {
            sendIncorrectUsage(sender, "Usage: /duel send <player> <type>\n" + "Types: \n" + "- Classic");
            return true;
        }

        if (PlayerWrapper.getPlayer(p).isDueling()) {
            p.sendMessage(CC.RED + "You cannot use this command while dueling.");
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

        Player target = Bukkit.getPlayer(args[0]);

        if (target == p) {
            sendIncorrectUsage(sender, "You cannot send a duel to yourself.");
            return true;
        }

        if (target != null) {
            putInDuel(args[1], p, target);
        }
        return true;
    }

    private void putInDuel(String type, Player p, Player target) {
        for (Duel duel : Duel.duels.keySet()) {
            if (Duel.duels.get(duel) == target.getUniqueId()) {
                if (duel.getP1() == p.getUniqueId()) {
                    p.sendMessage("You already sent a duel to this person.");
                    return;
                }
                Duel.duels.remove(duel);
            }
        }
        if (type.equalsIgnoreCase("classic")) {
            Duel.duels.put(new Classic(p.getUniqueId(), target.getUniqueId()), target.getUniqueId());
        } else {
            p.sendMessage("Not a valid type");
            return;
        }
        target.sendMessage(p.getName() + " sent you a duel request.");
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return Completer.onlinePlayers(args[0]);
        } else if (args.length == 2) {
            return List.of("Classic");
        }
        return List.of();
    }
}
