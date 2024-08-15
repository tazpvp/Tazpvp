package net.tazpvp.tazpvp.listeners;

import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class EntitySpawnListener implements Listener {
    @EventHandler
    public void onTNTSpawn(EntitySpawnEvent e) {
        if (e.getEntity() instanceof TNTPrimed tnt) {
            tnt.setFuseTicks(30);
        }
    }
}
