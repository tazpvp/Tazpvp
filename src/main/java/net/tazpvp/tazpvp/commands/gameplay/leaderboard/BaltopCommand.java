package net.tazpvp.tazpvp.commands.gameplay.leaderboard;

import lombok.NonNull;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.leaderboard.Leaderboard;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

import java.util.Map;
import java.util.UUID;

public class BaltopCommand extends NRCommand{
    public BaltopCommand() {
        super(new Label("baltop", null, "topbal"));
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {
        if (!(sender instanceof Player p)) {
            sendNoPermission(sender);
            return true;
        }

        for (Leaderboard.LeaderboardEnum leaderboardEnum : Leaderboard.LeaderboardEnum.values()) {
            if (leaderboardEnum.getType().equalsIgnoreCase("coins")) {
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
        return true;
    }
}
