package net.tazpvp.tazpvp.commands.admin.rank;

import lombok.NonNull;
import net.tazpvp.tazpvp.data.entity.UserRankEntity;
import net.tazpvp.tazpvp.data.implementations.UserRankServiceImpl;
import net.tazpvp.tazpvp.data.services.UserRankService;
import net.tazpvp.tazpvp.utils.TimeUtil;
import net.tazpvp.tazpvp.enums.CC;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

public class RankListCommand extends NRCommand {
    public RankListCommand() {
        super(new Label("list", "tazpvp.rank"));
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

        userRankEntity.getRanks().forEach(expirationRankEntity -> {
            final String rankExpires = (expirationRankEntity.getExpirationTimestamp() == 0L
                    ? "Never"
                    : TimeUtil.howLongAgo(expirationRankEntity.getExpirationTimestamp()));

            sender.sendMessage(
                    CC.GOLD + expirationRankEntity.getGameRankEntity().getName() +
                            CC.GRAY + " - " +
                            CC.RED + "Expires: " + rankExpires);
        });

        return true;
    }
}
