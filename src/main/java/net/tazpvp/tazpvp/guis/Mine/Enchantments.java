package net.tazpvp.tazpvp.guis.Mine;

import net.tazpvp.tazpvp.utils.enums.CC;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
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

        Button autoSmelt = Button.create(ItemBuilder.of(Material.ENCHANTED_BOOK, 1).name(CC.GREEN + "" + CC.BOLD + "Enchant Pickaxe").lore(CC.GRAY + "Check out the custom pickaxe enchantments.").build(), (e) -> {
            p.closeInventory();
        });

        Button efficiency = Button.create(ItemBuilder.of(Material.ENCHANTED_BOOK, 1).name(CC.GREEN + "" + CC.BOLD + "Enchant Pickaxe").lore(CC.GRAY + "Check out the custom pickaxe enchantments.").build(), (e) -> {
            p.closeInventory();
        });

        Button doubleOres = Button.create(ItemBuilder.of(Material.ENCHANTED_BOOK, 1).name(CC.GREEN + "" + CC.BOLD + "Enchant Pickaxe").lore(CC.GRAY + "Check out the custom pickaxe enchantments.").build(), (e) -> {
            p.closeInventory();
        });

        addButton(autoSmelt, 11);
        addButton(efficiency, 11);
        addButton(doubleOres, 11);

        update();
    }
}
