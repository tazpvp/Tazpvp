package net.tazpvp.tazpvp.commands.game.tournament.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.gui.Button;
import world.ntdi.nrcore.utils.gui.GUI;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

public class EventGui extends GUI {

    private int currentPage;
    private static final int ROWS = 5;
    private static final int ITEMS_PER_PAGE = ROWS * 7;

    public EventGui(Player p) {
        super("Browse Events", ROWS);
        updateGui();
        open(p);
    }

    private void updateGui() {
        fillBorder();
    }

    private void fillBorder() {
        Button borderItem = Button.createBasic(ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE).build());

        for (int i = 0; i < 9; i++) {
            addButton(borderItem, i);
            addButton(borderItem, (ROWS + 1) * 9 - 1 - i);
        }
        for (int i = 1; i < ROWS; i++) {
            addButton(borderItem, i * 9);
            addButton(borderItem, i * 9 + 8);
        }
    }
}
