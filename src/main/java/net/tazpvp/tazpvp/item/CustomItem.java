package net.tazpvp.tazpvp.item;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;
import java.util.Random;

public abstract class CustomItem implements Listener {
    @Getter
    private final ItemStack item;
    @Getter
    private final ItemMeta meta;

    public CustomItem(ItemStack item) {
        this.item = item;
        this.meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(ItemIndex.getKey(), PersistentDataType.INTEGER, new Random().nextInt(10000));
        item.setItemMeta(meta);
    }

    public abstract void interact(Player p, PlayerInteractEvent e);

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if (p.getInventory().getItemInMainHand().getType().equals(item.getType())) {
            ItemStack itemInMainHand = p.getInventory().getItemInMainHand();
            ItemMeta itemInMainHandMeta = itemInMainHand.getItemMeta();
            PersistentDataContainer itemInMainHandMetaPersistentDataContainer = itemInMainHandMeta.getPersistentDataContainer();
            PersistentDataContainer itemContainer = meta.getPersistentDataContainer();
            if (itemContainer.has(ItemIndex.getKey(), PersistentDataType.INTEGER) && itemInMainHandMetaPersistentDataContainer.has(ItemIndex.getKey(), PersistentDataType.INTEGER)) {
                if (Objects.equals(itemContainer.get(ItemIndex.getKey(), PersistentDataType.INTEGER), itemInMainHandMetaPersistentDataContainer.get(ItemIndex.getKey(), PersistentDataType.INTEGER))) {
                     interact(p, event);
                }
            }
        }
    }
}
