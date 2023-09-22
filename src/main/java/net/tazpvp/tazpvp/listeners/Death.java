package net.tazpvp.tazpvp.listeners;

import net.tazpvp.tazpvp.utils.objects.bosses.BossManager;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.UUID;

public class Death implements Listener {
    @EventHandler
    public void onDeath(final EntityDeathEvent event) {
        final Entity entity = event.getEntity();

        final UUID uuid = entity.getUniqueId();

        if (BossManager.getSpawnedBoss() == null) {
            return;
        }

        final UUID bossUUID = BossManager.getSpawnedBoss().getBoss().getUniqueId();

        if (uuid.equals(bossUUID)) {
            BossManager.bossDied();
        }
    }
}
