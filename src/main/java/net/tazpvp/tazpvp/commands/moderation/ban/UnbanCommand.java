package net.tazpvp.tazpvp.commands.moderation.ban;

import lombok.NonNull;
import net.tazpvp.tazpvp.utils.data.PunishmentService;
import net.tazpvp.tazpvp.utils.data.PunishmentServiceImpl;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;
import world.ntdi.nrcore.utils.command.simple.builder.LabelBuilder;

import java.util.List;

public class UnbanCommand extends NRCommand {
    public UnbanCommand() {
        super(LabelBuilder.of("unban", "tazpvp.unban").build().make());
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

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

        final PunishmentService punishmentService = new PunishmentServiceImpl();

        if (punishmentService.getPunishment(target.getUniqueId()) != PunishmentService.PunishmentType.BANNED) {
            sendIncorrectUsage(sender, target.getName() + " is not banned!");
            return false;
        }
        punishmentService.unpunish(target.getUniqueId());

        sender.sendMessage("Unbanned " + target.getName());

        return true;
    }

    @Override
    public List<String> complete(final CommandSender sender, final String[] args) {
        return List.of("<username>");
    }
}
