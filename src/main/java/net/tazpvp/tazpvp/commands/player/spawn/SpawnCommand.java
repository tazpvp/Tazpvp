package net.tazpvp.tazpvp.commands.player.spawn;

import lombok.NonNull;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.functions.CombatTagFunctions;
import net.tazpvp.tazpvp.utils.functions.DeathFunctions;
import net.tazpvp.tazpvp.utils.objects.CombatTag;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import world.ntdi.nrcore.NRCore;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

import java.util.UUID;

public class SpawnCommand extends NRCommand {
    public SpawnCommand() {
        super(new Label("spawn", null));
        setNativeExecutor((sender, args) -> {
            if (!(sender instanceof Player p)) {
                return true;
            }

            if (DeathFunctions.tags.containsKey(p.getUniqueId())) {
                p.sendMessage(CC.RED + "You cannot teleport to spawn while in combat.");
                return true;
            }

            if (p.hasPermission("tazpvp.spawn")) {
                p.teleport(NRCore.config.spawn);
            } else {
                p.sendMessage(CC.DARK_AQUA + "Teleportation to spawn will start in 5 seconds. Please do not move.");
                p.setMetadata("spawnTeleport", new FixedMetadataValue(Tazpvp.getInstance(), true));
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (p.hasMetadata("spawnTeleport")) {
                            p.removeMetadata("spawnTeleport", Tazpvp.getInstance());
                            p.teleport(NRCore.config.spawn);
                        } else {
                            cancel();
                        }
                    }
                }.runTaskLater(Tazpvp.getInstance(), 20 * 5);
            }

            return true;
        });
    }
}
