package net.tazpvp.tazpvp.commands.gameplay.leaderboard;

import lombok.NonNull;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.data.entity.PlayerStatEntity;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.leaderboard.LeaderboardEnum;
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
            String type = args[0];

            for (LeaderboardEnum leaderboardEnum : LeaderboardEnum.values()) {
                if (type.equalsIgnoreCase(leaderboardEnum.getColumnName())) {
                    int count = 1;
                    List<PlayerStatEntity> playerStatEntities = Tazpvp.getInstance().getPlayerStatService()
                            .getTop10Most(leaderboardEnum.getColumnName());

                    p.sendMessage( CC.DARK_AQUA + "" + CC.BOLD + "COINS Leaderboard");

                    for (PlayerStatEntity playerStatEntity : playerStatEntities) {
                        p.sendMessage(count + ". " + CC.GRAY + Bukkit.getOfflinePlayer(playerStatEntity.getUuid()).getName() + " " + CC.GOLD + leaderboardEnum.getStatEntityIntegerFunction().apply(playerStatEntity));
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
            return List.of("Coins", "Deaths", "Kills", "Levels");
        }
        return List.of();
    }
}
