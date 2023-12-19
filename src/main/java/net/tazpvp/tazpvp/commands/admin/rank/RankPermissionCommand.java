package net.tazpvp.tazpvp.commands.admin.rank;

import lombok.NonNull;
import net.tazpvp.tazpvp.data.entity.GameRankEntity;
import net.tazpvp.tazpvp.data.entity.PermissionEntity;
import net.tazpvp.tazpvp.data.implementations.GameRankServiceImpl;
import net.tazpvp.tazpvp.data.implementations.PermissionServiceImpl;
import net.tazpvp.tazpvp.data.services.GameRankService;
import net.tazpvp.tazpvp.data.services.PermissionService;
import net.tazpvp.tazpvp.utils.enums.CC;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

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
        } else {
            gameRankService.addPermissionToGameRank(gameRankEntity, permission);
            sender.sendMessage("Added permission");
        }

        return true;
    }
}
