package net.tazpvp.tazpvp.commands.admin.permissions;

import lombok.NonNull;
import net.tazpvp.tazpvp.data.Rank;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.functions.ChatFunctions;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
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
        super(new Label("permissions", "tazpvp.rank", "perms"));
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {
        if (!sender.hasPermission(getLabel().getPermission())) {
            sendNoPermission(sender);
            return true;
        }

        String incorrectUsage = CC.RED + "Incorrect Usage:\n" +
                CC.YELLOW + "/permissions <user> <rank|prefix> <set|reset> <rankType|newPrefix> <hex color-code> <bold?>";

        if (args.length < 4) {
            if (!args[2].equalsIgnoreCase("reset")) {
                sender.sendMessage(incorrectUsage);
                return true;
            }
        }

        OfflinePlayer offlineTarget = Bukkit.getOfflinePlayer(args[0]);

        if (offlineTarget.isOnline()) {
            Player target = Bukkit.getPlayer(args[0]);

            PlayerWrapper targetWrapper = PlayerWrapper.getPlayer(target);
            String type = args[1];
            String setOrReset = args[2];
            String updatedName = null;
            if (args.length > 3 && setOrReset.equalsIgnoreCase("set")) {
                updatedName = args[3];
                if (args.length > 5) {
                    String colorCode = args[4];
                    boolean isBold = Boolean.parseBoolean(args[5]);
                    if (colorCode.charAt(0) == '#' && colorCode.length() == 7) {
                        updatedName = ChatFunctions.gradient(colorCode, updatedName, isBold);
                    } else {
                        sender.sendMessage(incorrectUsage);
                    }
                }
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
                    targetWrapper.setCustomPrefix(null);
                }
                return true;
            } else {
                sender.sendMessage(incorrectUsage);
                return true;
            }

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
        } else if (args.length == 5) {
            return List.of("<hex color>");
        } else if (args.length == 6) {
            return List.of("true", "false");
        }
        return List.of();
    }
}
