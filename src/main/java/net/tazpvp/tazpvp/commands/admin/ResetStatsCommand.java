package net.tazpvp.tazpvp.commands.admin;

import lombok.NonNull;
import net.tazpvp.tazpvp.utils.data.PersistentData;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

import java.util.List;
import java.util.UUID;

public class ResetStatsCommand extends NRCommand {
    public ResetStatsCommand() {
        super(new Label("resetstats", "tazpvp.resetstats"));
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {
        if (!sender.hasPermission(getLabel().getPermission())) {
            sendNoPermission(sender);
            return true;
        }
        return true;
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        return super.complete(sender, args);
    }
}
