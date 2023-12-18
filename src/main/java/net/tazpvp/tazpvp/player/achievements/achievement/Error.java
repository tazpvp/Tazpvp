package net.tazpvp.tazpvp.player.achievements.achievement;

import net.tazpvp.tazpvp.data.DataTypes;
import net.tazpvp.tazpvp.data.PersistentData;
import net.tazpvp.tazpvp.data.entity.AchievementEntity;
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

        if (!achievementEntity.isCompleted()) {
            if (PersistentData.getInt(victim.getUniqueId(), DataTypes.DEATHS) >= 100) {
                achievementEntity.setCompleted(true);
                pw.setUserAchievementEntity(userAchievementEntity);
                ChatFunctions.achievement(killer, "Error");
            }
        }
    }
}
