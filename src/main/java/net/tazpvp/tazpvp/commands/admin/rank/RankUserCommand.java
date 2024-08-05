package net.tazpvp.tazpvp.commands.admin.rank;

import lombok.NonNull;
import net.tazpvp.tazpvp.data.entity.GameRankEntity;
import net.tazpvp.tazpvp.data.entity.UserRankEntity;
import net.tazpvp.tazpvp.data.implementations.GameRankServiceImpl;
import net.tazpvp.tazpvp.data.implementations.UserRankServiceImpl;
import net.tazpvp.tazpvp.data.services.GameRankService;
import net.tazpvp.tazpvp.data.services.UserRankService;
import net.tazpvp.tazpvp.enums.CC;
import net.tazpvp.tazpvp.utils.TimeToken;
import net.tazpvp.tazpvp.wrappers.PlayerWrapper;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import world.ntdi.nrcore.utils.command.simple.Completer;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

import java.util.List;
import java.util.UUID;

public class RankUserCommand extends NRCommand {
    public RankUserCommand() {
        super(new Label("user", "tazpvp.rank"));
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {
        if (!sender.hasPermission(this.getLabel().getPermission())) {
            sender.sendMessage(CC.RED + "You do not have permission to run this command.");
            return true;
        }

        if (args.length < 3) {
            sendIncorrectUsage(sender);
            return true;
        }

        final GameRankService gameRankService = new GameRankServiceImpl();
        final UserRankService userRankService = new UserRankServiceImpl();

        final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
        final UUID uuid = offlinePlayer.getUniqueId();

        final String addOrRemove = args[1];

        final GameRankEntity gameRankEntity = gameRankService.getGameRankFromName(args[2]);
        if (gameRankEntity == null) {
            sendIncorrectUsage(sender, "Couldn't find rank");
            return true;
        }

        final UserRankEntity userRankEntity = userRankService.getOrDefault(uuid);

        if (addOrRemove.equalsIgnoreCase("remove")) {
            if (!userRankService.hasRank(userRankEntity, gameRankEntity.getName())) {
                sendIncorrectUsage(sender, "Does not have rank " + gameRankEntity.getName());
                return true;
            }

            userRankService.removeExpiringRank(userRankEntity, gameRankEntity);
            sender.sendMessage("Successfully Removed Rank");
        } else if (addOrRemove.equalsIgnoreCase("add")) {
            String time;
            if (args.length < 4) {
                time = "perm";
            } else {
                time = args[3];
            }

            final long expiration = new TimeToken(time).getUnixTimestamp();
            final long expirationWithCurrentTime = (expiration == 0L ? 0 : System.currentTimeMillis() + expiration);

            userRankService.addExpiringRank(userRankEntity, gameRankEntity, expirationWithCurrentTime);
            sender.sendMessage("Successfully Added Rank");
        } else {
            sendIncorrectUsage(sender, "Couldn't find sub-argument " + addOrRemove);
        }

        if (offlinePlayer.isOnline()) {
            PlayerWrapper.getPlayer(uuid).refreshRankEntity();
        }

        return true;
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        if (args.length < 2) {
            return Completer.onlinePlayers(args[0]);
        } else if (args.length < 3) {
            return List.of("add", "remove");
        } else if (args.length < 4) {
            return new GameRankServiceImpl().getAllGameRanks();
        } else if (args.length < 5) {
            if (args[1].equalsIgnoreCase("add")) {
                return List.of("perm", "1d", "30d", "1h");
            }
        }
        return super.complete(sender, args);
    }
}
