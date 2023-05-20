package net.tazpvp.tazpvp.utils.player;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.WeakHashMap;

/**
 * Wrapper for the player object which contains valuable methods exclusive to tazpvp
 */
public class PlayerWrapper {
    @Getter
    private final UUID uuid;
    @Getter @Setter
    private boolean launching;
    @Getter @Setter
    private boolean respawning;
    @Getter @Setter
    private boolean canRestore;

    /**
     * Should only take UUID, all other values should not have to persist.
     * @param uuid UUID.
     */
    public PlayerWrapper(UUID uuid) {
        this.uuid = uuid;
        this.launching = false;
        this.respawning = false;
        this.canRestore = false;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    private static final WeakHashMap<UUID, PlayerWrapper> playerMap = new WeakHashMap<>();
    public static void addPlayer(Player p) {
        playerMap.put(p.getUniqueId(), new PlayerWrapper(p.getUniqueId()));
    }
    public static void addPlayer(UUID uuid) {
        playerMap.put(uuid, new PlayerWrapper(uuid));
    }
    public static void removePlayer(Player p) {
        playerMap.remove(p.getUniqueId(), new PlayerWrapper(p.getUniqueId()));
    }
    public static void removePlayer(UUID uuid) {
        playerMap.remove(uuid, new PlayerWrapper(uuid));
    }
    public static PlayerWrapper getPlayer(Player p) {
        return playerMap.get(p.getUniqueId());
    }
    public static PlayerWrapper getPlayer(UUID uuid) {
        return playerMap.get(uuid);
    }
}