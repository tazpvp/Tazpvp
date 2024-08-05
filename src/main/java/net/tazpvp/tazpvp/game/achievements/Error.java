package net.tazpvp.tazpvp.game.achievements;

import net.tazpvp.tazpvp.data.entity.AchievementEntity;
import net.tazpvp.tazpvp.data.entity.UserAchievementEntity;
import net.tazpvp.tazpvp.enums.StatEnum;
import net.tazpvp.tazpvp.helpers.ChatHelper;
import net.tazpvp.tazpvp.utils.observer.Observable;
import net.tazpvp.tazpvp.wrappers.PlayerWrapper;
import org.bukkit.entity.Player;

public class Error extends Observable {
    @Override
    public void death(Player victim, Player killer) {
        final PlayerWrapper pw = PlayerWrapper.getPlayer(killer);
        final UserAchievementEntity userAchievementEntity = pw.getUserAchievementEntity();
        final AchievementEntity achievementEntity = userAchievementEntity.getErrorAchievementEntity();

        if (!achievementEntity.isCompleted()) {
            if (StatEnum.DEATHS.getInt(victim.getUniqueId()) >= 100) {
                achievementEntity.setCompleted(true);
                userAchievementEntity.setErrorAchievementEntity(achievementEntity);
                pw.setUserAchievementEntity(userAchievementEntity);
                ChatHelper.achievement(killer, "Error");
            }
        }
    }
}
