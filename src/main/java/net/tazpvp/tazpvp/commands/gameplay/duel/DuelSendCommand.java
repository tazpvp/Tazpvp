package net.tazpvp.tazpvp.commands.gameplay.duel;

import lombok.NonNull;
import net.tazpvp.tazpvp.duels.type.Classic;
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
        if (type.equalsIgnoreCase("classic")) {
            new Classic(p.getUniqueId(), target.getUniqueId());
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
