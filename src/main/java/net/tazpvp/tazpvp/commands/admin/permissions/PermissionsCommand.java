package net.tazpvp.tazpvp.commands.admin.permissions;

import lombok.NonNull;
import net.tazpvp.tazpvp.utils.data.Rank;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import world.ntdi.nrcore.utils.command.simple.Completer;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

import java.util.ArrayList;
import java.util.List;

public class PermissionsCommand extends NRCommand {
    public PermissionsCommand() {
        super(new Label("permissions", "tazpvp.rank", "perms", "rank"));
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {

        if (args.length < 4) {
            if (!args[2].equalsIgnoreCase("reset")) {
                sendIncorrectUsage(sender, "/"+ super.getLabel() + " <user> <rank|prefix> <set|reset> <rankType|newPrefix>");
                return true;
            }
        }

        Player target = Bukkit.getPlayer(args[0]);
        PlayerWrapper targetWrapper = PlayerWrapper.getPlayer(target);
        String type = args[1];
        String setOrReset = args[2];
        String updatedName = null;
        if (args.length > 3 && setOrReset.equalsIgnoreCase("set")) {
            updatedName = args[3];
        }

        if (type.equalsIgnoreCase("rank")) {
            if (updatedName != null) {
                for (Rank rank : Rank.values()) {
                    if (updatedName.equalsIgnoreCase(rank.getName())) {
                        targetWrapper.setRank(rank);
                        Bukkit.getLogger().info(target.getName() + "'s rank was set to " + targetWrapper.getRank().getName() + " by " + sender.getName());
                        return true;
                    }
                }
            } else if (setOrReset.equalsIgnoreCase("reset")) {
                targetWrapper.setRank(Rank.DEFAULT);
                Bukkit.getLogger().info(target.getName() + "'s rank was set to " + Rank.DEFAULT.getName() + " by " + sender.getName());
                return true;
            }
        } else if (type.equalsIgnoreCase("prefix")) {
            if (updatedName != null) {
                targetWrapper.setCustomPrefix(updatedName);
            } else if (args[2].equalsIgnoreCase("reset")) {
                targetWrapper.removeCustomPrefix();
            }
            return true;
        } else {
            sendIncorrectUsage(sender, "/"+ super.getLabel() + " <user> <rank|prefix> <rankType|newPrefix>");
            return true;
        }

        return true;
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return Completer.onlinePlayers(args[0]);
        } else if (args.length == 2) {
            return List.of("rank", "prefix");
        } else if (args.length == 3) {
            return List.of("reset", "set");
        } else if (args.length == 4) {
            if (args[1].equalsIgnoreCase("rank")) {
                List<String> ranks = new ArrayList<>();
                for (Rank rank : Rank.values()) {
                    ranks.add(rank.name());
                }
                return ranks;
            } else {
                return List.of("<prefix>");
            }
        }
        return List.of();
    }
}
