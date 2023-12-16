package net.tazpvp.tazpvp.commands.admin;
import lombok.NonNull;
import net.tazpvp.tazpvp.utils.functions.ChatFunctions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

public class BroadcastCommand extends NRCommand {
    public BroadcastCommand() {
        super(new Label("broadcast", "tazpvp.broadcast"));
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {

        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /broadcast <message>");
            return true;
        }

        String message = String.join(" ", args);
        ChatFunctions.announce(ChatColor.GREEN + "[Broadcast] " + ChatColor.WHITE + message, Sound.BLOCK_NOTE_BLOCK_PLING);
        return true;


    }
}
