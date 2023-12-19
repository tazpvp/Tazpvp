package net.tazpvp.tazpvp.commands.admin.rank;

import lombok.NonNull;
import net.tazpvp.tazpvp.data.entity.GameRankEntity;
import net.tazpvp.tazpvp.data.implementations.GameRankServiceImpl;
import net.tazpvp.tazpvp.data.services.GameRankService;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.functions.ChatFunctions;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

public class RankCommand extends NRCommand {
    public RankCommand() {
        super(new Label("rank", "tazpvp.rank"));
        addSubcommand(new RankPermissionCommand());
        addSubcommand(new RankRankCommand());
    }
}
