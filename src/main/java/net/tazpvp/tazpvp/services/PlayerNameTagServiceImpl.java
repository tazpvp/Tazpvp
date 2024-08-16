package net.tazpvp.tazpvp.services;

import net.tazpvp.tazpvp.data.LooseData;
import net.tazpvp.tazpvp.enums.CC;
import net.tazpvp.tazpvp.helpers.ChatHelper;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

public class PlayerNameTagServiceImpl implements PlayerNameTagService {
    private final Map<UUID, TextDisplay> uuidItemDisplayMap;
    private final JavaPlugin javaPlugin;

    public PlayerNameTagServiceImpl(JavaPlugin javaPlugin) {
        this.uuidItemDisplayMap = new WeakHashMap<>();
        this.javaPlugin = javaPlugin;
    }

    @Override
    public void updatePlayerNameTag(Player player, String pNameTag, String pChatName) {
        TextDisplay textDisplay = uuidItemDisplayMap.get(player.getUniqueId());

        if (textDisplay != null) {
            textDisplay.setText(pNameTag);
            player.setDisplayName(pChatName);
            player.setPlayerListName(pChatName);
            player.setCustomName(pChatName);
        } else {
            throw new RuntimeException("Player not initialized");
        }
    }

    @Nullable
    @Override
    public String getPlayerNameTag(Player player) {
        TextDisplay textDisplay = uuidItemDisplayMap.get(player.getUniqueId());
        if (textDisplay != null) {
            return textDisplay.getText();
        }
        return null;
    }

    @Override
    public void initializePlayer(Player player) {
        final Location location = player.getLocation();

        final TextDisplay textDisplay = location.getWorld().spawn(location, TextDisplay.class);
        textDisplay.setCustomNameVisible(false);
        textDisplay.setText("\n");

        textDisplay.setBillboard(Display.Billboard.CENTER);
        player.addPassenger(textDisplay);

//        p_player.hideEntity(m_javaPlugin, textDisplay);

        textDisplay.setBackgroundColor(Color.fromARGB(0, 1, 1, 1));

        uuidItemDisplayMap.put(player.getUniqueId(), textDisplay);
    }

    @Override
    public void removePlayer(Player player) {
        TextDisplay textDisplay = uuidItemDisplayMap.get(player.getUniqueId());
        if (textDisplay != null) {
            textDisplay.remove();
        }
    }

    @Override
    public void recalibratePlayer(Player player) {
        TextDisplay textDisplay = uuidItemDisplayMap.get(player.getUniqueId());
        if (textDisplay != null) {
            player.addPassenger(textDisplay);
        }
    }

    @Override
    public void refreshTag(Player player) {
        TextDisplay textDisplay = uuidItemDisplayMap.get(player.getUniqueId());

        if (textDisplay != null) {
            String bounty;
            if (LooseData.getBounty(player.getUniqueId()) > 0) {
                bounty = CC.GREEN + " $" + LooseData.getBounty(player.getUniqueId());
            } else {
                bounty = "";
            }
            textDisplay.setText(
                    player.getName() + "\n" +
                    ChatHelper.getRankingPrefix(player) + bounty + "\n");
        }
    }

    @Override
    public void setNameTagVisibility(Player player, boolean visible) {
        TextDisplay textDisplay = uuidItemDisplayMap.get(player.getUniqueId());
        if (textDisplay != null) {
            textDisplay.setViewRange(visible ? 100 : 0);
        }
    }

    @Override
    public void teleportPlayer(Player player, Location location) {
        TextDisplay textDisplay = uuidItemDisplayMap.get(player.getUniqueId());
        if (textDisplay != null) {
            player.removePassenger(textDisplay);
            textDisplay.teleport(location);
            player.teleport(location);
            player.addPassenger(textDisplay);
        }
    }

    @Override
    public void destroyAllNametags() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            TextDisplay textDisplay = uuidItemDisplayMap.get(p.getUniqueId());
            if (textDisplay != null) {
                textDisplay.remove();
            }
        }
    }
}
