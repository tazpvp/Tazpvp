package net.tazpvp.tazpvp.commands.admin.kit;

import lombok.NonNull;
import net.tazpvp.tazpvp.helpers.PlayerHelper;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import world.ntdi.nrcore.utils.command.simple.Completer;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

import java.util.List;

public class KitCommand extends NRCommand {

    public KitCommand() {
        super(new Label("kit", "tazpvp.kit"));
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {
        if (!sender.hasPermission(getLabel().getPermission())) {
            sendNoPermission(sender);
            return true;
        }

        if (!(sender instanceof Player p)) {
            sendNoPermission(sender);
            return true;
        }

        if (args.length < 1) {
            PlayerHelper.kitPlayer(p);
        } else {
            if (Bukkit.getPlayer(args[0]) != null) {
                PlayerHelper.kitPlayer(Bukkit.getPlayer(args[0]));
            } else {
                p.sendMessage("Usage: /kit <player>");
            }
        }

        return true;
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return Completer.onlinePlayers(args[0]);
        }
        return List.of();
    }
}
