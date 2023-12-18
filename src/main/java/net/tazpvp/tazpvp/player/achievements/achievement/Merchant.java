package net.tazpvp.tazpvp.player.achievements.achievement;

import net.tazpvp.tazpvp.data.entity.AchievementEntity;
import net.tazpvp.tazpvp.data.entity.UserAchievementEntity;
import net.tazpvp.tazpvp.utils.functions.ChatFunctions;
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
                pw.setUserAchievementEntity(userAchievementEntity);
                ChatFunctions.achievement(p, "Merchant");
            }
        }
    }
}
