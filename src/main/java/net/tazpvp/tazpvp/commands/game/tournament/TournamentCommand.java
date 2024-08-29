package net.tazpvp.tazpvp.commands.game.tournament;

import net.tazpvp.tazpvp.commands.game.tournament.sub.TournamentCreate;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

public class TournamentCommand extends NRCommand {

    public TournamentCommand() {
        super(new Label("tournament", "tazpvp.tournaments", "event", "events", "tourney"));

        addSubcommand(new TournamentCreate());
    }
}
