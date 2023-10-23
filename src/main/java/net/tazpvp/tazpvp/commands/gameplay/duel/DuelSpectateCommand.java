package net.tazpvp.tazpvp.commands.gameplay.duel;

import lombok.NonNull;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.commands.admin.tazload.TazloadCommand;
import net.tazpvp.tazpvp.duels.Duel;
import net.tazpvp.tazpvp.events.Event;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.functions.CombatTagFunctions;
import net.tazpvp.tazpvp.utils.objects.CombatTag;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import world.ntdi.nrcore.utils.command.simple.Completer;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

import java.util.List;

public class DuelSpectateCommand extends NRCommand {
    public DuelSpectateCommand() {
        super(new Label("spectate", null));
    }

    @Override
    public boolean execute(@NonNull final CommandSender sender, @NotNull final @NonNull String[] args) {
        if (!(sender instanceof Player p)) {
            sendIncorrectUsage(sender);
            return false;
        }
        final PlayerWrapper playerWrapper = PlayerWrapper.getPlayer(p);

        if (args.length < 1) {
            sendIncorrectUsage(sender);
            return true;
        }

        if (playerWrapper.getDuel() != null || (Event.currentEvent != null && Event.currentEvent.getParticipantList().contains(p.getUniqueId())) ||
                CombatTagFunctions.isInCombat(p.getUniqueId()) || p.getGameMode() != GameMode.SURVIVAL || playerWrapper.getSpectating() != null) {
            sendIncorrectUsage(p, "You cannot use this right now.");
            return true;
        }

        if (TazloadCommand.tazloading) {
            p.sendMessage(CC.RED + "This feature is disabled while the server is reloading.");
            return true;
        }

        final Player duelerTarget = Bukkit.getPlayer(args[0]);

        if (duelerTarget == null) {
            sendIncorrectUsage(sender, "Cannot find player");
            return true;
        }

        final PlayerWrapper duelerTargetWrapper = PlayerWrapper.getPlayer(duelerTarget);

        final Duel duel = duelerTargetWrapper.getDuel();

        if (duel == null) {
            sendIncorrectUsage(sender, "Player is not dueling");
            return true;
        }

        playerWrapper.setSpectating(duel);
        duel.addSpectator(p);

        return true;
    }

    @Override
    public List<String> complete(final CommandSender sender, final String[] args) {
        return Completer.onlinePlayers(args[0]);
    }
}
