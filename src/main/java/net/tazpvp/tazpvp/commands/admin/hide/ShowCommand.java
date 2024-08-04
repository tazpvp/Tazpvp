package net.tazpvp.tazpvp.commands.admin.hide;

import lombok.NonNull;
import net.tazpvp.tazpvp.wrappers.PlayerWrapper;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.command.simple.Completer;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

import java.util.List;

public class ShowCommand extends NRCommand {
    public ShowCommand() {
        super(new Label("show", "tazpvp.hide"));
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NonNull String[] args) {
        if (!(sender instanceof Player p)) {
            sendIncorrectUsage(sender);
            return true;
        }

        if (args.length > 0) {
            final String target = args[0];
            final Player targetPlayer = Bukkit.getPlayer(target);
            if (targetPlayer != null) {
                PlayerWrapper wrapper = PlayerWrapper.getPlayer(targetPlayer);
                wrapper.showFromPlayer();
                wrapper.showPlayer();
            }
        } else {
            PlayerWrapper wrapper = PlayerWrapper.getPlayer(p);
            wrapper.showPlayer();
            wrapper.showFromPlayer();
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
