package net.tazpvp.tazpvp.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.gui.GUI;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

public class MainGui extends GUI {
    public MainGui(Player p) {
        super("Main Menu", 6);

        setItems();

        open(p);
    }

    private void setItems() {
        fill(0, 6 * 9, ItemBuilder.of(Material.GRAY_STAINED_GLASS).name(" ").build());
    }
}
