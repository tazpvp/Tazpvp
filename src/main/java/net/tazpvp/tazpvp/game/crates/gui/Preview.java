package net.tazpvp.tazpvp.game.crates.gui;

import net.tazpvp.tazpvp.enums.ItemEnum;
import net.tazpvp.tazpvp.game.crates.Crate;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import world.ntdi.nrcore.utils.gui.Button;
import world.ntdi.nrcore.utils.gui.GUI;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

public class Preview extends GUI {
    private final Player player;
    private final Crate crate;

    public Preview(Player player, Crate crate) {
        super("Crate Preview", 6);
        this.player = player;
        this.crate = crate;
        addItems();
    }

    private void addItems() {
        fill(0, 6*9, ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE, 1).name(" ").build());

        int i = 0;
        for (ItemEnum item : crate.getCrateDrops()) {
            addButton(Button.createBasic(item.getItem()), i);
            i++;
        }

        update();

    }
}
