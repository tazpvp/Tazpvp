package net.tazpvp.tazpvp.commands.moderation;

import lombok.NonNull;
import net.tazpvp.tazpvp.enums.CC;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import world.ntdi.nrcore.utils.ChatUtils;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

public class StaffChatCommand extends NRCommand {

    public StaffChatCommand() {
        super(new Label("staffchat", "tazpvp.staffchat", "sc"));
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(CC.RED + "Only players can use this command.");
            return true;
        }

        // Check for the "staffchat" permission
        if (!player.hasPermission("tazpvp.staffchat")) {
            player.sendMessage(CC.RED + "You don't have permission to use staff chat.");
            return true;
        }

        PlayerWrapper pw = PlayerWrapper.getPlayer(player);

        if (args.length > 0) {
            // Send the message to staff chat directly
            final String message = ChatUtils.builder(args, 0);

            for (Player plr : Bukkit.getOnlinePlayers()) {
                if (plr.hasPermission("tazpvp.staffchat")) {
                    plr.sendMessage(CC.LIGHT_PURPLE.toString() + CC.BOLD + "[Staff] " + CC.getLastColors(pw.getRankPrefix()) + pw.getRankPrefix() + " " + player.getName() + CC.WHITE + " " + message);
                }
            }
        } else {
            // Toggle the staff chat state
            pw.setStaffChatActive(!pw.isStaffChatActive());

            // Notify the player about the staff chat state
            player.sendMessage(CC.GREEN + "Staff chat " +
                    (pw.isStaffChatActive() ? "enabled" : "disabled"));
        }
        return true;
    }
}
