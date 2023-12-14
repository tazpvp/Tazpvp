package net.tazpvp.tazpvp.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class Explode implements Listener {
    @EventHandler
    public void onTNTExplode(EntityExplodeEvent e) {
        if (e.getEntity() instanceof org.bukkit.entity.TNTPrimed) {
            e.blockList().clear();

            for (Entity entity : e.getEntity().getNearbyEntities(4, 4, 4)) {
                if (entity instanceof Player) {
                    entity.setVelocity(entity.getLocation().toVector().subtract(e.getLocation().toVector()).normalize().multiply(1.5));
                }
            }
        }
    }
}
