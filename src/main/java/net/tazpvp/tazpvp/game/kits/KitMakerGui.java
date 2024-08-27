package net.tazpvp.tazpvp.game.kits;

import net.tazpvp.tazpvp.data.entity.KitEntity;
import net.tazpvp.tazpvp.data.services.KitService;
import net.tazpvp.tazpvp.enums.CC;
import net.tazpvp.tazpvp.services.KitMakerService;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import world.ntdi.nrcore.utils.gui.Button;
import world.ntdi.nrcore.utils.gui.GUI;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

import java.util.List;

public class KitMakerGui {
    private GUI gui;
    private final KitMakerService kitMakerService;
    private final KitService kitService;
    private final Player player;
    private final int size;

    public KitMakerGui(KitMakerService kitMakerService, KitService kitService, Player player) {
        this.kitMakerService = kitMakerService;
        this.player = player;
        this.kitService = kitService;
        this.size = (int) Math.ceil(kitMakerService.getValidItems().size() + 1 / 9.0);
        this.gui = new GUI("Kit Maker", size);
        addItems();
        gui.open(player);
        gui.setOnDestroy(() -> {
            ItemStack[] contents = player.getInventory().getContents();
            ItemStack[] sanitizedContents = kitMakerService.sanitize(contents);
            String serializedInventory = kitMakerService.serializeInventory(sanitizedContents);
            KitEntity kitEntity = kitService.getOrDefault(player.getUniqueId());
            kitEntity.setSerial(serializedInventory);
            kitService.saveKitEntity(kitEntity);
            player.sendMessage("Kit saved!");
        });
    }

    private void addItems() {
        gui.fill(0, size, ItemBuilder.of(Material.GRAY_STAINED_GLASS_PANE).name(" ").build());

        List<ItemStack> items = kitMakerService.getValidItems();

        for (int i = 0; i < items.size(); i++) {
            gui.addButton(createButton(items.get(i)), i);
        }

        gui.addButton(Button.create(ItemBuilder.of(Material.BARRIER)
                .name(CC.RED.toString() + CC.BOLD + "Clear Cursor")
                .lore(CC.GRAY + "Shift+Click to clear inventory.").build(), ((inventoryClickEvent, button) -> {
                    if (inventoryClickEvent.isShiftClick()) {
                        inventoryClickEvent.getWhoClicked().getInventory().clear();
                    } else {
                        inventoryClickEvent.getWhoClicked().setItemOnCursor(new ItemStack(Material.AIR));
                    }
        })), size - 1);
    }

    private Button createButton(ItemStack itemStack) {
        return Button.create(itemStack, ((inventoryClickEvent, button) -> {
            inventoryClickEvent.getWhoClicked().setItemOnCursor(button.getItem());
        }));
    }
}
