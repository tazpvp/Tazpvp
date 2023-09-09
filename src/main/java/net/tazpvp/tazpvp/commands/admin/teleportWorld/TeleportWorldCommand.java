package net.tazpvp.tazpvp.commands.admin.teleportWorld;

import net.tazpvp.tazpvp.utils.enums.CC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

public class TeleportWorldCommand extends NRCommand {
    public TeleportWorldCommand() {
        super(new Label("teleportworld", "tazpvp.worldtp", "tpworld"));
        setNativeExecutor((sender, args) -> {
            if (!(sender instanceof Player p)) {
                sendNoPermission(sender);
                return true;
            }

            if (args.length < 1) {
                sendIncorrectUsage(sender, "/teleportworld <world>");
                return true;
            }

            World world = Bukkit.getWorld(args[0]);
            if (world != null) {
                p.teleport(new Location(world, 0, 100, 0));
            } else {
                p.sendMessage(CC.RED + "That is not a valid world.");
            }
            return true;
        });
    }
}
