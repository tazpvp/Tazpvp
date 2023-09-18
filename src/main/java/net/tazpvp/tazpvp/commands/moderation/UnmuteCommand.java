package net.tazpvp.tazpvp.commands.moderation;

import lombok.NonNull;
import net.tazpvp.tazpvp.utils.data.PunishmentData;
import net.tazpvp.tazpvp.utils.functions.PunishmentFunctions;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import world.ntdi.nrcore.utils.command.simple.Completer;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

import java.util.List;

public class UnmuteCommand extends NRCommand {
    public UnmuteCommand() {
        super(new Label("unmute", "tazpvp.unmute"));
    }

    @Override
    public boolean execute(@NonNull final CommandSender sender, @NotNull final @NonNull String[] args) {
        if (!sender.hasPermission(getLabel().getPermission())) {
            sendNoPermission(sender);
            return true;
        }

        if (args.length < 1) {
            sendIncorrectUsage(sender);
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            sendIncorrectUsage(sender);
            return false;
        }

        if (!PunishmentData.isMuted(target.getUniqueId())) {
            sendIncorrectUsage(sender, target.getName() + " is not muted!");
            return false;
        }

        PunishmentFunctions.unmute(target);

        sender.sendMessage("Unmuted " + target.getName());

        return true;
    }

    @Override
    public List<String> complete(final CommandSender sender, final String[] args) {
        return Completer.onlinePlayers(args[0]);
    }
}
