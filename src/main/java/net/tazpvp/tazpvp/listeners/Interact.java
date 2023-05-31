package net.tazpvp.tazpvp.listeners;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.utils.crate.Crate;
import net.tazpvp.tazpvp.utils.crate.CrateManager;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class Interact implements Listener {

    @EventHandler
    private void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getClickedBlock().getType() == Material.BEACON) {
                e.setCancelled(true);
                if (Tazpvp.getCrateManager().canClaimDaily(p)) {
                    for (Crate crate : Tazpvp.getCrateManager().getCrates()) {
                        crate.acceptClick(e);
                    }
                }
            }
        }
    }
}
