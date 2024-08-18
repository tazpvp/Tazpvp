package net.tazpvp.tazpvp.game.achievements;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.data.entity.AchievementEntity;
import net.tazpvp.tazpvp.data.entity.UserAchievementEntity;
import net.tazpvp.tazpvp.data.services.UserAchievementService;
import net.tazpvp.tazpvp.enums.StatEnum;
import net.tazpvp.tazpvp.helpers.ChatHelper;
import net.tazpvp.tazpvp.utils.observer.Observable;
import org.bukkit.entity.Player;

public class Error extends Observable {
    private final UserAchievementService userAchievementService = Tazpvp.getInstance().getUserAchievementService();
    @Override
    public void death(Player victim, Player killer) {
        final UserAchievementEntity userAchievementEntity =  userAchievementService.getUserAchievementEntity(killer.getUniqueId());
        final AchievementEntity achievementEntity = userAchievementEntity.getError();

        if (!achievementEntity.isCompleted()) {
            if (StatEnum.DEATHS.getInt(victim.getUniqueId()) >= 100) {
                achievementEntity.setCompleted(true);
                userAchievementEntity.setError(achievementEntity);
                userAchievementService.saveUserAchievementEntity(userAchievementEntity);
                ChatHelper.achievement(killer, "Error");
            }
        }
    }
}
