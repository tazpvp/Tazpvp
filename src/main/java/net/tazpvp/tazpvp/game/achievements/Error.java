package net.tazpvp.tazpvp.game.achievements;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.data.entity.AchievementEntity;
import net.tazpvp.tazpvp.data.entity.PlayerStatEntity;
import net.tazpvp.tazpvp.data.entity.UserAchievementEntity;
import net.tazpvp.tazpvp.utils.functions.ChatFunctions;
import net.tazpvp.tazpvp.utils.observer.Observable;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import org.bukkit.entity.Player;

public class Error extends Observable {
    @Override
    public void death(Player victim, Player killer) {
        final PlayerWrapper pw = PlayerWrapper.getPlayer(killer);
        final UserAchievementEntity userAchievementEntity = pw.getUserAchievementEntity();
        final AchievementEntity achievementEntity = userAchievementEntity.getErrorAchievementEntity();
        final PlayerStatEntity victimStatEntity = Tazpvp.getInstance().getPlayerStatService().getOrDefault(victim.getUniqueId());

        if (!achievementEntity.isCompleted()) {
            if (victimStatEntity.getDeaths() >= 100) {
                achievementEntity.setCompleted(true);
                userAchievementEntity.setErrorAchievementEntity(achievementEntity);
                pw.setUserAchievementEntity(userAchievementEntity);
                ChatFunctions.achievement(killer, "Error");
            }
        }
    }
}
