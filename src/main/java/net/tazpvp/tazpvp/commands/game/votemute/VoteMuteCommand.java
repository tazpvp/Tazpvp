package net.tazpvp.tazpvp.commands.game.votemute;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.enums.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

public class VoteMuteCommand extends NRCommand {
    @Getter @Setter public static boolean voteMuteActive = false;
    @Getter @Setter public static long timeOfLastVote = 0;
    @Getter @Setter public static Player target = null;
    @Getter @Setter public static int voteCount = 0;
    @Getter @Setter public static CommandSender voteStarter = null;

    public VoteMuteCommand() {
        super(new Label("votemute", null));

        addSubcommand(new VoteMuteYesCommand());
        addSubcommand(new VoteMuteNoCommand());
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NonNull String[] args) {

        if (!(sender instanceof Player p)) {
            sender.sendMessage("Only players are able to use this command.");
            return true;
        }

        if (args.length > 1) {
            sender.sendMessage(CC.RED + "Too many arguments. (/votemute <player>)");
            return true;

        }

        if (args.length == 0) {
            sender.sendMessage(CC.RED + "Not enough arguments. (/votemute <player>)");
            return true;
        }

        if (voteMuteActive) {
            sender.sendMessage(CC.RED + "A vote is already active.");
            return true;
        }

        if (System.currentTimeMillis() - timeOfLastVote < 300000) {
            sender.sendMessage(CC.RED + "You must wait 5 minutes before starting another vote.");
            return true;
        }

        voteMuteActive = true;
        timeOfLastVote = System.currentTimeMillis();
        target = Bukkit.getPlayer(args[0]);
        voteStarter = sender;
        Bukkit.broadcastMessage(CC.RED + p.getDisplayName() + CC.WHITE + " has started a vote to mute " + CC.RED + target.getDisplayName());

        Component yes = Component.text("[YES]", NamedTextColor.GREEN)
                .clickEvent(ClickEvent.runCommand("/voteMute Yes"))
                .hoverEvent(Component.text("Click to vote yes to mute " + target.getDisplayName(), NamedTextColor.GREEN));
        Component no = Component.text("[NO]", NamedTextColor.RED)
                .clickEvent(ClickEvent.runCommand("/voteMute No"))
                .hoverEvent(Component.text("Click to vote no to mute " + target.getDisplayName(), NamedTextColor.RED));

        Bukkit.broadcastMessage(yes + " " + no);

        Bukkit.getScheduler().runTaskLater(Tazpvp.getInstance(), () -> {
            if (voteMuteActive) {
                voteMuteActive = false;
                Bukkit.broadcastMessage(CC.RED + "The vote to mute " + target.getDisplayName() + " has failed.");
                target = null;
            }
        },2000L);

        return true;
    }


}
