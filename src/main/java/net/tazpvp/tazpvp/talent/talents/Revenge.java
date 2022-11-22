package net.tazpvp.tazpvp.talent.talents;

import net.tazpvp.tazpvp.talent.Observable;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Revenge extends Observable {
    @Override
    public void death(Player victim, Player killer) {
        killer.setFireTicks(20 * 3);
    }

    @Override
    public void mine(Player p, Material material) {

    }

    @Override
    public void launch(Player p) {

    }
}
