package net.tazpvp.tazpvp.commands.gameplay.event.gui;

import net.tazpvp.tazpvp.enums.CC;
import net.tazpvp.tazpvp.game.events.EventObject;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import world.ntdi.nrcore.utils.gui.Button;
import world.ntdi.nrcore.utils.gui.GUI;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

import java.util.List;

public class EventGui extends GUI {

    private final List<EventObject> eventObjects = EventObject.activeEvents;
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

    private void addEvents() {
        int startIndex = currentPage * ITEMS_PER_PAGE;
        int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, eventObjects.size());

        for (int i = startIndex; i < endIndex; i++) {
            EventObject eventObject = eventObjects.get(i);
            ItemStack item;
            if (eventObject.getTimeUntilBegin() <= 0) {
                item = ItemBuilder.of(Material.CAKE).name("EVENT").lore(eventObject.getStatus()).glow(true).build();
            } else {
                item = ItemBuilder.of(Material.CAKE).name("EVENT").lore(eventObject.getStatus(), CC.GREEN + "Click to join.").glow(true).build();
            }
            int slot = (i - startIndex) % ITEMS_PER_PAGE + 10;
            if (slot % 9 == 8) slot += 2;
            addButton(Button.createBasic(item), slot);
        }
    }
}
