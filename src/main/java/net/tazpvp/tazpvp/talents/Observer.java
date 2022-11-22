package net.tazpvp.tazpvp.talents;

import org.bukkit.entity.Player;

public interface Observer {
    void death(Player victim, Player killer);
    void mine(Player p); // TODO: Add events to implement to
    void launch(Player p); // TODO: Add events to implement to
}
