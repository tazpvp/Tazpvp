package net.tazpvp.tazpvp.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodListener implements Listener {

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
//        e.setCancelled(true);
//        e.getEntity().setFoodLevel(20);
    }
}
