package net.tazpvp.tazpvp.listeners;

import net.tazpvp.tazpvp.bosses.BossManager;
import net.tazpvp.tazpvp.bosses.zorg.attacks.SummonUndeadAttack;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.UUID;

public class Death implements Listener {
    @EventHandler
    public void onDeath(final EntityDeathEvent event) {
        final Entity entity = event.getEntity();

        final UUID uuid = entity.getUniqueId();

        event.setDroppedExp(0);
        event.getDrops().clear();

        if (BossManager.getSpawnedBoss() == null) {
            return;
        }

        final UUID bossUUID = BossManager.getSpawnedBoss().getBoss().getUniqueId();

        if (uuid.equals(bossUUID)) {
            BossManager.bossDied();
            if (!SummonUndeadAttack.undeadList.isEmpty()) {
                for (Zombie z : SummonUndeadAttack.undeadList) {
                    SummonUndeadAttack.undeadList.clear();
                    z.setHealth(0);
                }
            }
        }
    }
}
