package net.tazpvp.tazpvp.game.crates.gui;

import net.tazpvp.tazpvp.enums.ItemEnum;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.gui.Button;
import world.ntdi.nrcore.utils.gui.GUI;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

import java.util.List;

public class Preview extends GUI {
    private final Player player;
    private final List<ItemEnum> crateDrops;

    public Preview(Player player, List<ItemEnum> crateDrops) {
        super("Crate Preview", 6);
        this.player = player;
        this.crateDrops = crateDrops;
        addItems();
        open(player);
    }

    private void addItems() {
        fill(0, 6*9, ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE, 1).name(" ").build());

        int i = 0;
        for (ItemEnum item : crateDrops) {
            addButton(Button.createBasic(item.getItem(1)), i);
            i++;
        }

        update();
    }
}
