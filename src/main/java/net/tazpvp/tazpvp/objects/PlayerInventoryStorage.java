package net.tazpvp.tazpvp.objects;

import lombok.Getter;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.data.entity.PlayerStatEntity;
import net.tazpvp.tazpvp.enums.CC;
import net.tazpvp.tazpvp.enums.StatEnum;
import net.tazpvp.tazpvp.wrappers.PlayerWrapper;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;
import java.util.WeakHashMap;

public class PlayerInventoryStorage {
    @Getter
    private final UUID uuid;
    @Getter
    private final UUID lastKillerUUID;
    @Getter
    private final ItemStack[] items;
    @Getter
    private final ItemStack[] armors;
    @Getter
    private final long timestamp;

    public PlayerInventoryStorage(UUID uuid, UUID lastKillerUUID) {
        this.uuid = uuid;
        this.lastKillerUUID = lastKillerUUID;
        this.items = Bukkit.getPlayer(uuid).getInventory().getStorageContents();
        this.armors = Bukkit.getPlayer(uuid).getInventory().getArmorContents();
        this.timestamp = System.currentTimeMillis();
    }

    @Getter
    private static final WeakHashMap<UUID, PlayerInventoryStorage> playerInventoryStorageWeakHashMap = new WeakHashMap<>();

    public static void updateStorage(Player p, UUID lastAttacker) {
        playerInventoryStorageWeakHashMap.put(p.getUniqueId(), new PlayerInventoryStorage(p.getUniqueId(), lastAttacker));
    }
    public static void updateStorage(UUID uuid, UUID lastKillerUUID) {
        updateStorage(Bukkit.getPlayer(uuid), lastKillerUUID);
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

        StatEnum.DEATHS.remove(p.getUniqueId(), 1);

        playerInventoryStorageWeakHashMap.remove(p.getUniqueId());

    }
    public static void restoreStorage(UUID uuid) {
        restoreStorage(Bukkit.getPlayer(uuid));
    }

    public static void checkContainedBanned(UUID bannedUUID) {
        playerInventoryStorageWeakHashMap.forEach((key, value) -> {
            if (value.lastKillerUUID.equals(bannedUUID)) {
                OfflinePlayer op = Bukkit.getOfflinePlayer(key);
                if (op.isOnline()) {
                    Player p = (Player) op;
                    sendOption(p);
                }
            }
        });
    }
    private static void sendOption(Player p) {
        PlayerWrapper playerWrapper = PlayerWrapper.getPlayer(p);
        playerWrapper.setCanRestore(true);
        p.sendMessage(CC.AQUA + "-----------------------------------");
        TextComponent component = new TextComponent(String.format(CC.RED + "\tUh Oh! Looks like someone who recently got banned previously killed you! Click HERE to restore your items."));
        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{new TextComponent(CC.GOLD + "Restore your items.")}));
        component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/restore self")));
        p.spigot().sendMessage(component);
        p.sendMessage(CC.AQUA + "-----------------------------------");
    }

}
