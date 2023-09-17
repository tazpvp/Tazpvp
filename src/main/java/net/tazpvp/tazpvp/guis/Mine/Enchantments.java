package net.tazpvp.tazpvp.guis.Mine;

import net.tazpvp.tazpvp.utils.enums.CC;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.gui.Button;
import world.ntdi.nrcore.utils.gui.GUI;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

public class Enchantments extends GUI {
    public Enchantments(Player p) {
        super("Pickaxe Enchantments", 3);
        addItems(p);
        open(p);
    }

    private void addItems(Player p) {
        fill(0, 27, ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE, 1).name(" ").build());

        Button autoSmelt = Button.create(ItemBuilder.of(Material.ENCHANTED_BOOK, 1).name(CC.GREEN + "" + CC.BOLD + "Auto Smelt").lore(CC.GRAY + "Automatically refine ores.").build(), (e) -> {
            p.closeInventory();
        });

        Button efficiency = Button.create(ItemBuilder.of(Material.ENCHANTED_BOOK, 1).name(CC.GREEN + "" + CC.BOLD + "Efficiency").lore(CC.GRAY + "Increase the speed of mining.").build(), (e) -> {
            p.closeInventory();
        });

        Button doubleOres = Button.create(ItemBuilder.of(Material.ENCHANTED_BOOK, 1).name(CC.GREEN + "" + CC.BOLD + "Double Ores").lore(CC.GRAY + "Duplicate the ores you mine.").build(), (e) -> {
            p.closeInventory();
        });

        addButton(autoSmelt, 11);
        addButton(efficiency, 13);
        addButton(doubleOres, 15);

        update();
    }
}
