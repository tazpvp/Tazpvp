package net.tazpvp.tazpvp.commands.admin.rank;

import lombok.NonNull;
import net.tazpvp.tazpvp.data.entity.GameRankEntity;
import net.tazpvp.tazpvp.data.entity.PermissionEntity;
import net.tazpvp.tazpvp.data.implementations.GameRankServiceImpl;
import net.tazpvp.tazpvp.data.implementations.PermissionServiceImpl;
import net.tazpvp.tazpvp.data.services.GameRankService;
import net.tazpvp.tazpvp.data.services.PermissionService;
import net.tazpvp.tazpvp.enums.CC;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

import java.util.ArrayList;
import java.util.List;

public class RankPermissionCommand extends NRCommand {
    public RankPermissionCommand() {
        super(new Label("permission", "tazpvp.rank"));
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {
        if (!sender.hasPermission(this.getLabel().getPermission())) {
            sender.sendMessage(CC.RED + "You do not have permission to run this command.");
            return true;
        }

        final String targetRank = args[0];
        final String addOrRemove = args[1];
        final String permission = args[2];

        final GameRankService gameRankService = new GameRankServiceImpl();
        final PermissionService permissionService = new PermissionServiceImpl();

        final GameRankEntity gameRankEntity = gameRankService.getGameRankFromName(targetRank);

        if (gameRankEntity == null) {
            sendIncorrectUsage(sender, "No game rank found");
            return true;
        }

        if (addOrRemove.equals("remove")) {
            final PermissionEntity permissionEntity = permissionService.findByPermission(gameRankEntity, permission);

            if (permissionEntity == null) {
                sendIncorrectUsage(sender, "No permission found");
                return true;
            }

            gameRankService.removePermissionFromGameRank(gameRankEntity, permission);
            sender.sendMessage("Removed permission");
        } else {
            gameRankService.addPermissionToGameRank(gameRankEntity, permission);
            sender.sendMessage("Added permission");
        }

        return true;
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        if (args.length < 2) {
            return new GameRankServiceImpl().getAllGameRanks();
        } else if (args.length < 3) {
            return List.of("add", "remove");
        } else if (args.length < 4) {
            if (args[1].equals("remove")) {
                return List.of("todo add permissions");
            }
        }

        return new ArrayList<>();
    }
}
