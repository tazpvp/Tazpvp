package net.tazpvp.tazpvp.commands.admin.permissions;

import lombok.NonNull;
import net.tazpvp.tazpvp.utils.data.Rank;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

public class PermissionsCommand extends NRCommand {
    public PermissionsCommand() {
        super(new Label("permissions", "tazpvp.rank", "perms", "rank"));
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {

        if (args.length < 3) {
            sendIncorrectUsage(sender, "/"+ super.getLabel() + " <user> <rank|prefix> <rankType|newPrefix>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        PlayerWrapper targetWrapper = PlayerWrapper.getPlayer(target);
        String type = args[1];
        String setTo = args[2];

        if (type.equalsIgnoreCase("rank")) {
            for (Rank rank : Rank.values()) {
                if (setTo.equalsIgnoreCase(rank.getName())) {
                    targetWrapper.setRank(rank);
                    Bukkit.getLogger().info(target.getName() + "'s rank was set to " + targetWrapper.getRank().getName() + " by " + sender.getName());
                    return true;
                }
            }
        } else if (type.equalsIgnoreCase("prefix")) {
            return true;
        } else {
            sendIncorrectUsage(sender, "/"+ super.getLabel() + " <user> <rank|prefix> <rankType|newPrefix>");
            return true;
        }

        return true;
    }
}
