package net.tazpvp.tazpvp.commands.admin.teleportWorld;

import lombok.NonNull;
import net.tazpvp.tazpvp.enums.CC;
import net.tazpvp.tazpvp.helpers.PlayerHelper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

import java.util.ArrayList;
import java.util.List;

public class TeleportWorldCommand extends NRCommand {
    public TeleportWorldCommand() {
        super(new Label("teleportworld", "tazpvp.worldtp", "tpworld"));
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {
        if (!(sender instanceof Player p)) {
            sendNoPermission(sender);
            return true;
        }

        if (!p.hasPermission(getLabel().getPermission())) {
            sendNoPermission(sender);
            return false;
        }

        if (args.length < 1) {
            sendIncorrectUsage(sender, "/teleportworld <world>");
            return true;
        }

        final World world = Bukkit.getWorld(args[0]);
        if (world != null) {
            PlayerHelper.teleport(p, new Location(world, 0, 100, 0));
            return true;
        } else {
            p.sendMessage(CC.RED + "That is not a valid world.");
        }

        return false;
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            List<String> worlds = new ArrayList<>();
            for (World world : Bukkit.getWorlds()) {
                worlds.add(world.getName());
            }
            return worlds;
        } else {
            return List.of();
        }
    }
}
