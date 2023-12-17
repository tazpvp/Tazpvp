package net.tazpvp.tazpvp.talents.talent;

import net.tazpvp.tazpvp.utils.data.PersistentData;
import net.tazpvp.tazpvp.utils.data.entity.TalentEntity;
import net.tazpvp.tazpvp.utils.observer.Observable;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import org.bukkit.entity.Player;

public class Revenge extends Observable {
    @Override
    public void death(Player victim, Player killer) {
        final PlayerWrapper killerWrapper = PlayerWrapper.getPlayer(killer);
        final TalentEntity killerTalentEntity = killerWrapper.getTalentEntity();
        final PlayerWrapper victimWrapper = PlayerWrapper.getPlayer(killer);
        final TalentEntity victimTalentEntity = victimWrapper.getTalentEntity();

        if (victim != killer) {
            if (killerTalentEntity.isRevenge()) {
                if (!victimTalentEntity.isMoist()) {
                    killer.setFireTicks(20 * 3);
                }
            }
        }
    }
}
