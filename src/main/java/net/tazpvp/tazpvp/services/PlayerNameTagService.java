package net.tazpvp.tazpvp.services;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface PlayerNameTagService {
    void updatePlayerNameTag(Player player, String NameTag, String chatName);
    String getPlayerNameTag(Player player);
    void initializePlayer(Player player);
    void removePlayer(Player player);
    void recalibratePlayer(Player player);
    void refreshTag(Player player);
    void setNameTagVisibility(Player player, boolean visible);
    void teleportPlayer(Player player, Location location);
    void destroyAllNametags();
}
