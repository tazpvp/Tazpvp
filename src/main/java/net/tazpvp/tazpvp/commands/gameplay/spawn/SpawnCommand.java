package net.tazpvp.tazpvp.commands.gameplay.spawn;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.functions.CombatTagFunctions;
import net.tazpvp.tazpvp.utils.functions.DeathFunctions;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
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

            if (PlayerWrapper.getPlayer(p).isDueling()) {
                sendIncorrectUsage(sender, CC.RED + "You cannot use this command while dueling.");
                return true;
            }

            if (CombatTagFunctions.isInCombat(p.getUniqueId())) {
                sendIncorrectUsage(sender, CC.RED + "You cannot use this command while in combat.");
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
