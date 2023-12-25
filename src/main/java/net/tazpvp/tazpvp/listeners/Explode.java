package net.tazpvp.tazpvp.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.util.Vector;

public class Explode implements Listener {
    @EventHandler
    public void onTNTExplode(EntityExplodeEvent e) {
        if (e.getEntity() instanceof org.bukkit.entity.TNTPrimed) {
            e.blockList().clear();

            for (Entity entity : e.getEntity().getNearbyEntities(4, 4, 4)) {
                if (entity instanceof Player p) {
                    p.setVelocity(p.getLocation().toVector().subtract(e.getLocation().toVector()).normalize().multiply(2));
                } else {
                    entity.setVelocity(new Vector(0, 0, 0));
                }
            }
        }

        if (e.getEntity().getType() == EntityType.ENDER_CRYSTAL) {
            e.setCancelled(true);
        }
    }
}
