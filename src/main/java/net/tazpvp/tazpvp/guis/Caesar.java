package net.tazpvp.tazpvp.guis;

import net.tazpvp.tazpvp.guis.guild.GuildBrowser;
import net.tazpvp.tazpvp.utils.enums.CC;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import world.ntdi.nrcore.utils.gui.Button;
import world.ntdi.nrcore.utils.gui.GUI;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

public class Caesar extends GUI {
    public Caesar(Player p) {
        super("Caesar", 4);
        addItems(p);
        open(p);
    }

    private void addItems(Player p) {
        fill(0, 3*9, ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE, 1).name(" ").build());

        addButton(Button.create(ItemBuilder.of(Material.BOOK, 1).name(CC.GREEN + "" + CC.BOLD + "Achievements").lore(CC.GRAY + "Check your achievements").build(), (e) -> {
            new Achievements(p);
        }), 10);

        update();
    }
}
