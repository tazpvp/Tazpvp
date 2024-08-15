package net.tazpvp.tazpvp.commands.admin;

import lombok.NonNull;
import net.tazpvp.tazpvp.helpers.ChatHelper;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

public class BroadcastCommand extends NRCommand {
    public BroadcastCommand() {
        super(new Label("broadcast", "tazpvp.broadcast"));
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {

        if (!sender.hasPermission(getLabel().getPermission())) {
            sendNoPermission(sender);
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /broadcast <message>");
            return true;
        }

        String message = String.join(" ", args);
        ChatHelper.announce(ChatColor.GREEN + "[Broadcast] " + ChatColor.WHITE + message, Sound.BLOCK_NOTE_BLOCK_PLING);
        return true;


    }
}
