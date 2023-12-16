package net.tazpvp.tazpvp.listeners;

import net.tazpvp.tazpvp.Tazpvp;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;

public class Burn implements Listener {

    @EventHandler
    private void onBurn(EntityCombustEvent e) {
        if (e.getEntity() instanceof Player p) {
            Tazpvp.getObservers().forEach(observer -> observer.burn(p, e));
        }
    }
}
