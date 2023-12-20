package net.tazpvp.tazpvp.commands.admin.rank;

import lombok.NonNull;
import net.tazpvp.tazpvp.data.entity.GameRankEntity;
import net.tazpvp.tazpvp.data.implementations.GameRankServiceImpl;
import net.tazpvp.tazpvp.data.implementations.UserRankServiceImpl;
import net.tazpvp.tazpvp.data.services.GameRankService;
import net.tazpvp.tazpvp.data.services.UserRankService;
import net.tazpvp.tazpvp.utils.TimeToken;
import net.tazpvp.tazpvp.utils.enums.CC;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
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

        final String addOrRemoveOrList = args[1];

        final GameRankEntity gameRankEntity = gameRankService.getGameRankFromName(args[2]);
        if (gameRankEntity == null) {
            sendIncorrectUsage(sender, "Couldn't find rank");
            return true;
        }

        if (addOrRemoveOrList.equalsIgnoreCase("remove")) {
            if (userRankService.)
        } else if (addOrRemoveOrList.equalsIgnoreCase("add")) {
            if (args.length < 4) {
                sendIncorrectUsage(sender);
                return true;
            }

            final long expiration = new TimeToken(args[3]).getUnixTimestamp();
            final long expirationWithCurrentTime = System.currentTimeMillis() + expiration;
        } else {

        }





        return true;
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        return super.complete(sender, args);
    }
}
