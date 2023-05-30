package net.tazpvp.tazpvp.item.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import world.ntdi.nrcore.utils.item.custom.CustomItem;

public class azureVapor extends CustomItem {

    public azureVapor() {
        super("Azure Vapor", new String[]{"Extinguish Flames"}, Material.BLUE_ORCHID);
    }

    @Override
    public void onLeftClick(PlayerInteractEvent playerInteractEvent) {
        removeFire(playerInteractEvent.getPlayer());
    }

    @Override
    public void onRightClick(PlayerInteractEvent playerInteractEvent) {
        removeFire(playerInteractEvent.getPlayer());
    }

    private void removeFire(Player p) {
        if (p.getFireTicks() > 0) {
            p.setFireTicks(0);
        }
    }
}
