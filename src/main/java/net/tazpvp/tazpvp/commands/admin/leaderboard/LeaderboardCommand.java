package net.tazpvp.tazpvp.commands.admin.leaderboard;

import lombok.NonNull;
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
            if (args[0].equalsIgnoreCase("view")) {
                if (args[1].equalsIgnoreCase("coins")) {
                    int count = 1;
                    TreeMap<Integer, UUID> sortedMap = new TreeMap<>(Collections.reverseOrder());
                    Leaderboard.CoinsLeaderboard.getSortedPlacement().forEach((uuid, placement) -> sortedMap.put(placement.getPoints(), uuid));
                    for (Map.Entry<Integer, UUID> entry : sortedMap.entrySet()) {
                        Leaderboard.Placement placement = Leaderboard.CoinsLeaderboard.getSortedPlacement().get(entry.getValue());
                        p.sendMessage(count + ". " + Bukkit.getOfflinePlayer(entry.getValue()).getName() + " " + placement.getPoints());
                        count++;
                    }
                    return true;
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
