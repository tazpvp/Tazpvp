package net.tazpvp.tazpvp.game.kits;

import net.tazpvp.tazpvp.data.entity.KitEntity;
import net.tazpvp.tazpvp.data.services.KitService;
import net.tazpvp.tazpvp.services.KitMakerService;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import world.ntdi.nrcore.utils.gui.Button;
import world.ntdi.nrcore.utils.gui.GUI;

import java.util.List;

public class KitMakerGui {
    private GUI gui;
    private final KitMakerService kitMakerService;
    private final KitService kitService;
    private final Player player;

    public KitMakerGui(KitMakerService kitMakerService, KitService kitService, Player player) {
        this.kitMakerService = kitMakerService;
        this.player = player;
        this.kitService = kitService;
        this.gui = new GUI("Kit Maker", (int) Math.ceil(kitMakerService.getValidItems().size() / 9.0));
        addItems();
        gui.open(player);
        gui.setOnDestroy(() -> {
            ItemStack[] contents = player.getInventory().getContents();
            String serializedInventory = kitMakerService.serializeInventory(contents);
            KitEntity kitEntity = kitService.getOrDefault(player.getUniqueId());
            kitEntity.setSerial(serializedInventory);
            kitService.saveKitEntity(kitEntity);
            player.sendMessage("Kit saved!");
        });
    }

    private void addItems() {
        gui.fill(0, 6*9, GUI.FILLER);

        List<ItemStack> items = kitMakerService.getValidItems();

        for (int i = 0; i < items.size(); i++) {
            gui.addButton(createButton(items.get(i)), i);
        }
    }

    private Button createButton(ItemStack itemStack) {
        return Button.create(itemStack, ((inventoryClickEvent, button) -> {
            inventoryClickEvent.getWhoClicked().setItemOnCursor(button.getItem());
        }));
    }
}
