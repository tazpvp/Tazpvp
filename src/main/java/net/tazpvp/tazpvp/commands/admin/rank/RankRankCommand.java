package net.tazpvp.tazpvp.commands.admin.rank;

import lombok.NonNull;
import net.tazpvp.tazpvp.data.implementations.GameRankServiceImpl;
import net.tazpvp.tazpvp.data.services.GameRankService;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.functions.ChatFunctions;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

public class RankRankCommand extends NRCommand {
    public RankRankCommand() {
        super(new Label("rank", "tazpvp.rank"));
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {

        if (!(sender instanceof Player p)) {
            return true;
        }

        if (!p.hasPermission(this.getLabel().getPermission())) {
            p.sendMessage(CC.RED + "You do not have permission to run this command.");
            return true;
        }

        final String name = args[0];
        final String prefix = args[1];
        final String hex = args[2];
        final int hierarchy = Integer.parseInt(args[3]);

        final String hexPrefix = ChatFunctions.gradient(hex, prefix, true);

        final GameRankService gameRankService = new GameRankServiceImpl();

        gameRankService.createGameRank(name, hexPrefix, hierarchy);

        return true;
    }
}
