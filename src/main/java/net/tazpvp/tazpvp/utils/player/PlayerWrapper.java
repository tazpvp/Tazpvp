package net.tazpvp.tazpvp.utils.player;

import lombok.Getter;
import lombok.Setter;
import net.tazpvp.tazpvp.Tazpvp;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
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
     * List of all players the player wrapper owner is hidden from
     */
    @Getter
    private List<UUID> hiddenFrom;

    /**
     * Should only take UUID, all other values should not have to persist.
     * @param uuid UUID.
     */
    public PlayerWrapper(UUID uuid) {
        this.uuid = uuid;
        this.launching = false;
        this.respawning = false;
        this.canRestore = false;
        this.hiddenFrom = new ArrayList<>();
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    /**
     * Hide the player from ALL other players.
     */
    public void hidePlayer() {
        Bukkit.getOnlinePlayers().forEach(this::hidePlayer);
    }

    /**
     * Hide the target from the owner.
     * @param target The player to hide from the owner
     */
    public void hidePlayer(Player target) {
        Player owner = getPlayer();
        hiddenFrom.add(target.getUniqueId());
        owner.hidePlayer(Tazpvp.getInstance(), target);
    }

    public void hideFromPlayer(Player target) {
        PlayerWrapper.getPlayer(target).hidePlayer(getPlayer());
    }

    public void hideFromPlayer() {
        Bukkit.getOnlinePlayers().forEach(player -> PlayerWrapper.getPlayer(player).hidePlayer(getPlayer()));
    }

    /**
     * Hide the player from ALL other players.
     */
    public void showPlayer() {
        getHiddenFrom().forEach(uuid -> {
            Player target = Bukkit.getPlayer(uuid);
            if (target != null) {
                showPlayer(target);
            }
        });
    }

    /**
     * Show the target from the owner.
     * @param target The player to hide from the owner
     */
    public void showPlayer(Player target) {
        Player owner = getPlayer();
        hiddenFrom.remove(target.getUniqueId());
        owner.showPlayer(Tazpvp.getInstance(), target);
    }

    public void showFromPlayer(Player target) {
        PlayerWrapper.getPlayer(target).showPlayer(getPlayer());
    }

    public void showFromPlayer() {
        getHiddenFrom().forEach(uuid -> {
            Player target = Bukkit.getPlayer(uuid);
            if (target != null ){
                PlayerWrapper.getPlayer(target).showPlayer(getPlayer());
            }
        });
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