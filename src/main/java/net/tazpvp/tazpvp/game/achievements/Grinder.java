package net.tazpvp.tazpvp.game.achievements;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.data.LooseData;
import net.tazpvp.tazpvp.data.entity.AchievementEntity;
import net.tazpvp.tazpvp.data.entity.UserAchievementEntity;
import net.tazpvp.tazpvp.data.services.UserAchievementService;
import net.tazpvp.tazpvp.helpers.ChatHelper;
import net.tazpvp.tazpvp.utils.observer.Observable;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Grinder extends Observable {
    private final UserAchievementService userAchievementService = Tazpvp.getInstance().getUserAchievementService();
    @Override
    public void mine(Player p, Material material) {
        final UserAchievementEntity userAchievementEntity =  userAchievementService.getUserAchievementEntity(p.getUniqueId());
        final AchievementEntity achievementEntity = userAchievementEntity.getGrinder();

        if (!achievementEntity.isCompleted()) {
            if (LooseData.getMineCount(p.getUniqueId()) >= 100) {
                achievementEntity.setCompleted(true);
                userAchievementEntity.setGrinder(achievementEntity);
                userAchievementService.saveUserAchievementEntity(userAchievementEntity);
                ChatHelper.achievement(p, "Grinder");
            }
        }
    }
}
