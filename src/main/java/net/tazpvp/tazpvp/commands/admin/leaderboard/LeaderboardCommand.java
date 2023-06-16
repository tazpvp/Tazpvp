package net.tazpvp.tazpvp.commands.admin.leaderboard;

import lombok.NonNull;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.leaderboard.Leaderboard;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

import java.util.*;

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
                for (Leaderboard.leaderboardEnum leaderboardEnum : Leaderboard.leaderboardEnum.values()) {
                    if (type.equalsIgnoreCase(leaderboardEnum.getType())) {
                        int count = 1;
                        TreeMap<Integer, UUID> sortedMap = new TreeMap<>(Collections.reverseOrder());
                        leaderboardEnum.getLeaderboard().getSortedPlacement().forEach((uuid, placement) -> sortedMap.put(placement.getPoints(), uuid));
                        p.sendMessage( CC.DARK_AQUA + "" + CC.BOLD + leaderboardEnum.getType() + " Leaderboard");
                        for (Map.Entry<Integer, UUID> entry : sortedMap.entrySet()) {
                            Leaderboard.Placement placement = leaderboardEnum.getLeaderboard().getSortedPlacement().get(entry.getValue());
                            p.sendMessage(count + ". " + CC.GRAY + Bukkit.getOfflinePlayer(entry.getValue()).getName() + " " + CC.GOLD + placement.getPoints());
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
