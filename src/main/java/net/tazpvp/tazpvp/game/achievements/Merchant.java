package net.tazpvp.tazpvp.game.achievements;

import net.tazpvp.tazpvp.data.entity.AchievementEntity;
import net.tazpvp.tazpvp.data.entity.UserAchievementEntity;
import net.tazpvp.tazpvp.helpers.ChatHelper;
import net.tazpvp.tazpvp.utils.observer.Observable;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import org.bukkit.entity.Player;

public class Merchant extends Observable {
    @Override
    public void gui(Player p, String name) {
        final PlayerWrapper pw = PlayerWrapper.getPlayer(p);
        final UserAchievementEntity userAchievementEntity = pw.getUserAchievementEntity();
        final AchievementEntity achievementEntity = userAchievementEntity.getMerchantAchievementEntity();

        if (!achievementEntity.isCompleted()) {
            if (name.equalsIgnoreCase("caesar")) {
                achievementEntity.setCompleted(true);
                userAchievementEntity.setMerchantAchievementEntity(achievementEntity);
                pw.setUserAchievementEntity(userAchievementEntity);
                ChatHelper.achievement(p, "Merchant");
            }
        }
    }
}
