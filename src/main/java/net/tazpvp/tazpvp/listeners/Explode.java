package net.tazpvp.tazpvp.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class Explode implements Listener {
    @EventHandler
    public void onTNTExplode(EntityExplodeEvent e) {
        if (e.getEntity() instanceof org.bukkit.entity.TNTPrimed) {
            e.blockList().clear();
        }
    }
}
