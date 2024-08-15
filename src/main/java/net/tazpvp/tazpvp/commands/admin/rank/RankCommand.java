package net.tazpvp.tazpvp.commands.admin.rank;

import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

public class RankCommand extends NRCommand {
    public RankCommand() {
        super(new Label("rank", "tazpvp.rank"));
        addSubcommand(new RankPermissionCommand());
        addSubcommand(new RankRankCommand());
        addSubcommand(new RankUserCommand());
        addSubcommand(new RankResetCommand());
        addSubcommand(new RankListCommand());
    }
}
