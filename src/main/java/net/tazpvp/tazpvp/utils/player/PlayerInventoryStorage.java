package net.tazpvp.tazpvp.utils.player;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;
import java.util.WeakHashMap;

public class PlayerInventoryStorage {
    @Getter
    private final UUID uuid;
    @Getter
    private final ItemStack[] items;
    @Getter
    private final ItemStack[] armors;
    @Getter
    private final long timestamp;

    public PlayerInventoryStorage(UUID uuid) {
        this.uuid = uuid;
        this.items = Bukkit.getPlayer(uuid).getInventory().getStorageContents();
        this.armors = Bukkit.getPlayer(uuid).getInventory().getArmorContents();
        this.timestamp = System.currentTimeMillis();
    }

    @Getter
    private static final WeakHashMap<UUID, PlayerInventoryStorage> playerInventoryStorageWeakHashMap = new WeakHashMap<>();
    public static void updateStorage(Player p) {
        playerInventoryStorageWeakHashMap.put(p.getUniqueId(), new PlayerInventoryStorage(p.getUniqueId()));
    }
    public static void updateStorage(UUID uuid) {
        updateStorage(Bukkit.getPlayer(uuid));
    }
    public static PlayerInventoryStorage getStorage(Player p) {
        return playerInventoryStorageWeakHashMap.get(p.getUniqueId());
    }
    public static PlayerInventoryStorage getStorage(UUID uuid) {
        return playerInventoryStorageWeakHashMap.get(uuid);
    }
    public static void restoreStorage(Player p) {
        PlayerInventoryStorage playerInventoryStorage = getStorage(p);

        p.getInventory().clear();
        p.getInventory().setContents(playerInventoryStorage.getItems());
        p.getInventory().setArmorContents(playerInventoryStorage.getArmors());

        playerInventoryStorageWeakHashMap.remove(p.getUniqueId());

    }

    public static void restoreStorage(UUID uuid) {
        restoreStorage(Bukkit.getPlayer(uuid));
    }

}
