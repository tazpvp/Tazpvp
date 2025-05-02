package net.tazpvp.tazpvp.game.achievements;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.data.entity.AchievementEntity;
import net.tazpvp.tazpvp.data.entity.UserAchievementEntity;
import net.tazpvp.tazpvp.data.services.AchievementService;
import net.tazpvp.tazpvp.data.services.UserAchievementService;
import net.tazpvp.tazpvp.helpers.ChatHelper;
import net.tazpvp.tazpvp.utils.observer.Observable;
import net.tazpvp.tazpvp.wrappers.PlayerWrapper;
import org.bukkit.entity.Player;

public class Speedrunner extends Observable {
    private final UserAchievementService userAchievementService = Tazpvp.getInstance().getUserAchievementService();
    private final AchievementService achievementService = Tazpvp.getInstance().getAchievementService();
    @Override
    public void death(Player victim, Player killer) {
        final PlayerWrapper pw = PlayerWrapper.getPlayer(killer);
        final UserAchievementEntity userAchievementEntity =  userAchievementService.getOrDefault(killer.getUniqueId());
        final AchievementEntity achievementEntity = userAchievementEntity.getSpeedrunner();

        if (!achievementEntity.isCompleted()) {
            if (System.currentTimeMillis() - pw.getTimeOfLaunch() <= 30 * 1000) {
                if (pw.getKillCount() >= 10) {
                    achievementEntity.setCompleted(true);
                    achievementService.saveAchievementEntity(achievementEntity);
                    ChatHelper.achievement(killer, "Speedrunner");
                } else {
                    pw.setKillCount(pw.getKillCount() + 1);
                }
            }
        }
    }
}
