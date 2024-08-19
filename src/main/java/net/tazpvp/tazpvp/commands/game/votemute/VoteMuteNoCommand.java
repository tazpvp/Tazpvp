package net.tazpvp.tazpvp.commands.game.votemute;

import lombok.NonNull;
import net.tazpvp.tazpvp.enums.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

public class VoteMuteNoCommand extends NRCommand {

    public VoteMuteNoCommand() {
        super(new Label("yes", null));
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NonNull String[] args) {

        if (VoteMuteCommand.isVoteMuteActive()) {
            if (sender instanceof Player player) {
                if (VoteMuteCommand.getTarget() != null) {
                    if (VoteMuteCommand.getTarget().equals(player)) {
                        sender.sendMessage("You cannot vote on the mute when you are a target.");
                        return true;
                    }
                    VoteMuteCommand.setVoteMuteActive(false);
                    VoteMuteCommand.setTarget(null);
                    VoteMuteCommand.setVoteCount(0);
                    Bukkit.broadcastMessage(CC.RED + player.getName() + " has voted no to mute " + VoteMuteCommand.getTarget().getName() + ". " + "The vote has ended.");
                    VoteMuteCommand.setTarget(null);
                } else {
                    sender.sendMessage("There is no target to vote for a mute.");
                }
            } else {
                sender.sendMessage("You must be a player to vote for a mute.");
            }
        } else {
            sender.sendMessage("There is no vote mute active.");
        }

        return true;
    }
}
