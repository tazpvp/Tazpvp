package net.tazpvp.tazpvp.commands.admin.rank;

import lombok.NonNull;
import net.tazpvp.tazpvp.data.entity.UserRankEntity;
import net.tazpvp.tazpvp.data.implementations.UserRankServiceImpl;
import net.tazpvp.tazpvp.data.services.UserRankService;
import net.tazpvp.tazpvp.wrappers.PlayerWrapper;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

public class RankResetCommand extends NRCommand {
    public RankResetCommand() {
        super(new Label("reset", "tazpvp.rank"));
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {
        if (!sender.hasPermission(getLabel().getPermission())) {
            sendIncorrectUsage(sender);
            return true;
        }

        if (args.length < 1) {
            sendIncorrectUsage(sender);
            return true;
        }

        final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);

        final UserRankService userRankService = new UserRankServiceImpl();

        final UserRankEntity userRankEntity = userRankService.getUserRankEntity(offlinePlayer.getUniqueId());
        userRankService.resetAllRanks(userRankEntity);

        if (offlinePlayer.isOnline()) {
            PlayerWrapper.getPlayer(offlinePlayer.getUniqueId()).refreshRankEntity();
            ((Player) offlinePlayer).kickPlayer("Refreshing Rank Setup");
        }

        return true;
    }
}
