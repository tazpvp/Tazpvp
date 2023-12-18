package net.tazpvp.tazpvp.commands.admin.permissions;

import lombok.NonNull;
import net.tazpvp.tazpvp.data.entity.GameRankEntity;
import net.tazpvp.tazpvp.data.entity.UserRankEntity;
import net.tazpvp.tazpvp.data.implementations.GameRankServiceImpl;
import net.tazpvp.tazpvp.data.implementations.UserRankServiceImpl;
import net.tazpvp.tazpvp.data.services.GameRankService;
import net.tazpvp.tazpvp.data.services.UserRankService;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;
import world.ntdi.nrcore.utils.command.simple.builder.LabelBuilder;

public class TempPermissionsCommand extends NRCommand {
    public TempPermissionsCommand() {
        super(LabelBuilder.of("tempperm", "is.op").build().make());
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {
        if (sender.isOp()) {
            final UserRankService userRankService = new UserRankServiceImpl();
            final GameRankService gameRankService = new GameRankServiceImpl();

            Player p = (Player) sender;

            UserRankEntity userRankEntity = userRankService.getOrDefault(p.getUniqueId());

            System.out.println(userRankEntity);
        }
        return true;
    }
}
