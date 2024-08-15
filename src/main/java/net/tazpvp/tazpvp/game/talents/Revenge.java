package net.tazpvp.tazpvp.game.talents;

import net.tazpvp.tazpvp.data.entity.TalentEntity;
import net.tazpvp.tazpvp.utils.observer.Observable;
import net.tazpvp.tazpvp.wrappers.PlayerWrapper;
import org.bukkit.entity.Player;

public class Revenge extends Observable {
    @Override
    public void death(Player victim, Player killer) {
        final PlayerWrapper killerWrapper = PlayerWrapper.getPlayer(killer);
        final TalentEntity killerTalentEntity = killerWrapper.getTalentEntity();
        final PlayerWrapper victimWrapper = PlayerWrapper.getPlayer(killer);
        final TalentEntity victimTalentEntity = victimWrapper.getTalentEntity();

        if (victim != killer) {
            if (victimTalentEntity.isRevenge()) {
                if (!killerTalentEntity.isMoist()) {
                    killer.setFireTicks(20 * 3);
                }
            }
        }
    }
}
