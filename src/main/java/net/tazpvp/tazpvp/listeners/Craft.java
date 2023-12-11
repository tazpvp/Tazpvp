package net.tazpvp.tazpvp.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

public class Craft implements Listener {

    @EventHandler
    private void onCraft(CraftItemEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (p.getGameMode() != GameMode.CREATIVE) {
            e.setCancelled(true);
        }

    }
}
