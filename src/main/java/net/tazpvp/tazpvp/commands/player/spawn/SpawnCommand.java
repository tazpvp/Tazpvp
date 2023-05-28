package net.tazpvp.tazpvp.commands.player.spawn;

import lombok.NonNull;
import net.tazpvp.tazpvp.Tazpvp;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import world.ntdi.nrcore.NRCore;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

public class SpawnCommand extends NRCommand {
    public SpawnCommand() {
        super(new Label("spawn", null));
        setNativeExecutor((sender, args) -> {
            if (!(sender instanceof Player p)) {
                return true;
            }

            if (p.hasPermission("tazpvp.spawn")) {
                p.teleport(NRCore.config.spawn);
            } else {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        p.teleport(NRCore.config.spawn);
                    }
                }.runTaskLater(Tazpvp.getInstance(), 20*5);
            }

            return true;
        });
    }
}
