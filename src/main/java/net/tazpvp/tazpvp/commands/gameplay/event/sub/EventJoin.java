package net.tazpvp.tazpvp.commands.gameplay.event.sub;

import lombok.NonNull;
import net.tazpvp.tazpvp.objects.PartyObject;
import net.tazpvp.tazpvp.objects.TournamentObject;
import net.tazpvp.tazpvp.wrappers.PlayerWrapper;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

public class EventJoin extends NRCommand {

    public EventJoin() {
        super(new Label("join", null));
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {

        if (!(sender instanceof Player p)) return true;

        TournamentObject tournament = TournamentObject.activeTournament;
        if (tournament != null) {
            int sizeCap = tournament.getTeamSizeCap();
            PlayerWrapper pw = PlayerWrapper.getPlayer(p);
            if (sizeCap > 1) {
                if (pw.getParty() != null) {
                    tournament.addParticipant(pw.getParty());
                } else {
                    p.sendMessage("You require a team of " + sizeCap + " to join this event.");
                }
            } else {
                if (pw.getParty() == null) {
                    pw.setParty(new PartyObject(p.getUniqueId()));
                }
                tournament.addParticipant(pw.getParty());
            }
        }

        return super.execute(sender, args);
    }
}
