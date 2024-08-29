package net.tazpvp.tazpvp.commands.game.duel;

import lombok.NonNull;
import net.tazpvp.tazpvp.commands.admin.tazload.TazloadCommand;
import net.tazpvp.tazpvp.enums.CC;
import net.tazpvp.tazpvp.helpers.CombatTagHelper;
import net.tazpvp.tazpvp.helpers.DuelHelper;
import net.tazpvp.tazpvp.objects.DuelObject;
import net.tazpvp.tazpvp.objects.PartyObject;
import net.tazpvp.tazpvp.game.tournaments.old.EventObject;
import net.tazpvp.tazpvp.wrappers.PlayerWrapper;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.command.simple.Completer;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

import java.util.List;

public class SpectateCommand extends NRCommand {

    public SpectateCommand() {
        super(new Label("spectate", null));
    }

    @Override
    public boolean execute(@NonNull final CommandSender sender, @NonNull String[] args) {
        if (!(sender instanceof Player p)) {
            sendIncorrectUsage(sender);
            return false;
        }
        final PlayerWrapper playerWrapper = PlayerWrapper.getPlayer(p);

        if (args.length < 1) {
            sendIncorrectUsage(sender);
            return true;
        }

        if (playerWrapper.getDuel() != null) {
            DuelHelper.send(p, "You are already in a duel.");
            return true;
        }

        if (DuelHelper.isSpectating(p.getUniqueId()) != null) {
            DuelHelper.send(p, "You are already spectating a duel.");
            return true;
        }

        if (EventObject.activeTournament != null) {
            for (PartyObject team : EventObject.activeTournament.getParticipantList()) {
                if (team.getOnlineMembers().contains(p)) {
                    DuelHelper.send(p, "You cannot duelwhile in an event.");
                    return true;
                }
            }
        }

        if (CombatTagHelper.isInCombat(p.getUniqueId())) {
            DuelHelper.send(p, "You cannot duel while in combat.");
            return true;
        }

        if (p.getGameMode() != GameMode.SURVIVAL) {
            DuelHelper.send(p, "You can only duel while in survival mode.");
            return true;
        }

        if (TazloadCommand.tazloading) {
            DuelHelper.send(p,CC.RED + "This feature is disabled while the server is reloading.");
            return true;
        }

        final Player duelerTarget = Bukkit.getPlayer(args[0]);

        if (duelerTarget == null) {
            DuelHelper.send(p, "Cannot find player");
            return true;
        }

        final PlayerWrapper duelerTargetWrapper = PlayerWrapper.getPlayer(duelerTarget);

        final DuelObject duel = duelerTargetWrapper.getDuel();

        if (duel == null) {
            DuelHelper.send(p, "That player is not in a duel.");
            return true;
        }

        duel.getSpectators().add(p.getUniqueId());
        duel.addSpectator(p);

        return true;
    }

    @Override
    public List<String> complete(final CommandSender sender, final String[] args) {
        return Completer.onlinePlayers(args[0]);
    }
}