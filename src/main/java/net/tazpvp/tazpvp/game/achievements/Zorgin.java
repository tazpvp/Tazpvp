package net.tazpvp.tazpvp.game.achievements;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.data.entity.AchievementEntity;
import net.tazpvp.tazpvp.data.entity.UserAchievementEntity;
import net.tazpvp.tazpvp.data.services.UserAchievementService;
import net.tazpvp.tazpvp.helpers.ChatHelper;
import net.tazpvp.tazpvp.utils.observer.Observable;
import org.bukkit.entity.Player;

public class Zorgin extends Observable {
    private final UserAchievementService userAchievementService = Tazpvp.getInstance().getUserAchievementService();
    @Override
    public void kill_zorg(Player p) {
        final UserAchievementEntity userAchievementEntity =  userAchievementService.getUserAchievementEntity(p.getUniqueId());
        final AchievementEntity achievementEntity = userAchievementEntity.getZorgin();

        if (!achievementEntity.isCompleted()) {
            achievementEntity.setCompleted(true);
            userAchievementEntity.setZorgin(achievementEntity);
            userAchievementService.saveUserAchievementEntity(userAchievementEntity);
            ChatHelper.achievement(p, "Zorgin");
        }
    }
}
