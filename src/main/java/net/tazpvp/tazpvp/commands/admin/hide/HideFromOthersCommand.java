package net.tazpvp.tazpvp.commands.admin.hide;

import lombok.NonNull;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.command.simple.Completer;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

import java.util.List;

public class HideFromOthersCommand extends NRCommand {
    public HideFromOthersCommand() {
        super(new Label("other", "tazpvp.hide"));
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NonNull String[] args) {
        if (args.length < 1) {
            sendIncorrectUsage(sender);
            return true;
        }

        if (!(sender instanceof Player p)) {
            sendIncorrectUsage(sender);
            return true;
        }

        final String target = args[0];

        if (target.equalsIgnoreCase( "all")) {
            PlayerWrapper.getPlayer(p).hideFromPlayer();
        } else {
            final Player targetPlayer = Bukkit.getPlayer(target);
            if (targetPlayer != null) {
                PlayerWrapper.getPlayer(p).hideFromPlayer(targetPlayer);
            } else {
                sendIncorrectUsage(sender);
                return true;
            }
        }

        return true;
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            List<String> completion = Completer.onlinePlayers(args[0]);
            completion.add("all");
            return completion;
        }
        return List.of();
    }
}
