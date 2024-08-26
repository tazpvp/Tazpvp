package net.tazpvp.tazpvp.commands.game.votemute;

import lombok.NonNull;
import net.tazpvp.tazpvp.enums.CC;
import net.tazpvp.tazpvp.helpers.PunishmentHelper;
import net.tazpvp.tazpvp.utils.passive.Afk;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

public class VoteMuteYesCommand extends NRCommand {

    public VoteMuteYesCommand() {
        super(new Label("no", null));
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NonNull String[] args) {

        if (VoteMuteCommand.isVoteMuteActive()) {
            if (sender instanceof Player p) {
                // if total vote count or whatever == online player count, end vote & mute player
                // else add 1 to total vote count

                VoteMuteCommand.setVoteCount(VoteMuteCommand.getVoteCount() + 1);
                Bukkit.broadcastMessage(CC.GREEN + p.getName() + " has voted yes to mute " + VoteMuteCommand.getTarget().getName() + ".");
                if (VoteMuteCommand.getVoteCount() == Bukkit.getOnlinePlayers().size() - Afk.afkPlayers.size()) {
                    PunishmentHelper.mute(VoteMuteCommand.getTarget(), "1h", VoteMuteCommand.getVoteStarter(), "Vote Mute");
                    Bukkit.broadcastMessage(CC.RED + VoteMuteCommand.getTarget().getName() + " has been muted via vote mute.");
                    VoteMuteCommand.setVoteMuteActive(false);
                    VoteMuteCommand.setTarget(null);
                    VoteMuteCommand.setVoteStarter(null);
                    VoteMuteCommand.setVoteCount(0);
                }
            }
        }

        return true;
    }

}
