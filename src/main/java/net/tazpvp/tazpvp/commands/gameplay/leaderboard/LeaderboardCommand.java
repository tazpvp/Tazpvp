package net.tazpvp.tazpvp.commands.gameplay.leaderboard;

import lombok.NonNull;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.leaderboard.Leaderboard;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class LeaderboardCommand extends NRCommand {
    public LeaderboardCommand() {
        super(new Label("leaderboard", null, "lb"));
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NonNull String[] args) {
        if (!(sender instanceof Player p)) {
            sendNoPermission(sender);
            return true;
        }

        if (args.length >= 2) {
            String request = args[0];
            String type = args[1];

            if (!List.of("view").contains(request)) {
                sendIncorrectUsage(sender);
                return true;
            }

            if (request.equalsIgnoreCase("view")) {
                for (Leaderboard.LeaderboardEnum leaderboardEnum : Leaderboard.LeaderboardEnum.values()) {
                    if (type.equalsIgnoreCase(leaderboardEnum.getType())) {
                        int count = 1;
                        Map<UUID, Integer> sortedMap = leaderboardEnum.getLeaderboard().getSortedPlacement();

                        p.sendMessage( CC.DARK_AQUA + "" + CC.BOLD + leaderboardEnum.getType() + " Leaderboard");

                        for (Map.Entry<UUID, Integer> entry : sortedMap.entrySet()) {
                            p.sendMessage(count + ". " + CC.GRAY + Bukkit.getOfflinePlayer(entry.getKey()).getName() + " " + CC.GOLD + entry.getValue());
                            count++;
                        }
                        return true;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return List.of("view");
        } else if (args.length == 2) {
            return List.of("coins", "deaths", "kills", "levels");
        } else {
            return List.of();
        }
    }
}
