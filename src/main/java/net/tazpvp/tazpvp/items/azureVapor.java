package net.tazpvp.tazpvp.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import world.ntdi.nrcore.utils.item.custom.CustomItem;

public class azureVapor extends CustomItem {
    /*
        Azure vapor? More like azuremines, haha get it bc rownox
        failed at making a server named azuremines but still wanted
        to use the word Azure because it's a cool word, I agree with
        this as azure is enjoyable to look at and read, but reminds
        me of Azure cloud services from microsoft which are unreliable
        and quite frankly, shit.
     */
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
