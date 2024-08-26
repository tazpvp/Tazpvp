package net.tazpvp.tazpvp.commands.game.tournament.sub;

import lombok.NonNull;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.game.npcs.NPC;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

public class TournamentCreate extends NRCommand {

    public TournamentCreate() {
        super(new Label("create", "tazpvp.tournaments.create"));
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {

        if (args.length >= 2) {

        }

        NPC tournamentsNpc = Tazpvp.getInstance().getNpcs().get(3);
        tournamentsNpc.refreshName("");

        return false;
    }
}
