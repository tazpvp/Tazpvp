package net.tazpvp.tazpvp.talents.talent;

import net.tazpvp.tazpvp.utils.observer.Observable;
import org.bukkit.entity.Player;

public class Revenge extends Observable {
    @Override
    public void death(Player victim, Player killer) {
        if (victim != killer) {
            killer.setFireTicks(20 * 3);
        }
    }
}
