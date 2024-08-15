package net.tazpvp.tazpvp.game.kits;

import net.tazpvp.tazpvp.data.entity.KitEntity;
import net.tazpvp.tazpvp.data.services.KitService;
import net.tazpvp.tazpvp.services.KitMakerService;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import world.ntdi.nrcore.utils.gui.Button;
import world.ntdi.nrcore.utils.gui.GUI;

public class KitMakerGui {
    private GUI gui;
    private final KitMakerService kitMakerService;
    private final KitService kitService;
    private final Player player;

    public KitMakerGui(KitMakerService kitMakerService, KitService kitService, Player player) {
        this.kitMakerService = kitMakerService;
        this.player = player;
        this.kitService = kitService;
        this.gui = new GUI("Kit Maker", 6);
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

    }

    private Button createButton(ItemStack itemStack) {
        return Button.create(itemStack, ((inventoryClickEvent, button) -> {
            inventoryClickEvent.getWhoClicked().setItemOnCursor(button.getItem());
        }));
    }
}
