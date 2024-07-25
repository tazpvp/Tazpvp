package net.tazpvp.tazpvp.utils;

import org.bukkit.entity.Player;

public interface PlayerNameTagService {
    void updatePlayerNameTag(Player player, String NameTag, String chatName);
    String getPlayerNameTag(Player player);

    void initializePlayer(Player player);
    void removePlayer(Player player);

    void recalibratePlayer(Player player);
}
