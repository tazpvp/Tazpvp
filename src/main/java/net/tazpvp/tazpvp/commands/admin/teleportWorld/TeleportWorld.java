package net.tazpvp.tazpvp.commands.admin.teleportWorld;

import lombok.NonNull;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

public class TeleportWorld extends NRCommand {
    public TeleportWorld() {
        super(new Label("teleportworld", "tazpvp.worldtp", "tpworld"));
        setNativeExecutor((sender, args) -> {
            if (!(sender instanceof Player p)) {
                sendNoPermission(sender);
                return true;
            }

            if (args.length)
        });
    }
}
