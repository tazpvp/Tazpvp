package net.tazpvp.tazpvp.talents.talent;

import net.tazpvp.tazpvp.talents.Talents;
import net.tazpvp.tazpvp.utils.data.DataTypes;
import net.tazpvp.tazpvp.utils.data.PersistentData;
import net.tazpvp.tazpvp.utils.observer.Observable;
import org.bukkit.entity.Player;

public class Revenge extends Observable {
    @Override
    public void death(Player victim, Player killer) {
        if (victim != killer) {
            if (PersistentData.getTalents(victim.getUniqueId()).is("Revenge")) {
                killer.setFireTicks(20 * 3);
            }
        }
    }
}
