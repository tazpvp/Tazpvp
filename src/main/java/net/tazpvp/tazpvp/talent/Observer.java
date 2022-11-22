package net.tazpvp.tazpvp.talent;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public interface Observer {
    /**
     * Death function that gets called when the player dies
     * @param victim The dead player
     * @param killer The player who killed the dead player
     */
    void death(Player victim, Player killer);

    /**
     * Mine function that gets called when the player mines
     * @param p The player who mined
     * @param material The material of the block that was mined
     */
    void mine(Player p, Material material); // TODO: Add events to implement to

    /**
     * Launch function that gets called when a player uses a launch pad to go to the main map
     * @param p The player who used the launch pad
     */
    void launch(Player p); // TODO: Add events to implement to

}
