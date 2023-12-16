package net.tazpvp.tazpvp.achievements.achievement;

import net.tazpvp.tazpvp.achievements.Achievements;
import net.tazpvp.tazpvp.utils.data.DataTypes;
import net.tazpvp.tazpvp.utils.data.PersistentData;
import net.tazpvp.tazpvp.utils.data.entity.AchievementEntity;
import net.tazpvp.tazpvp.utils.functions.ChatFunctions;
import net.tazpvp.tazpvp.utils.observer.Observable;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import org.bukkit.entity.Player;

public class Error extends Observable {
    @Override
    public void death(Player victim, Player killer) {
        final PlayerWrapper playerWrapper = PlayerWrapper.getPlayer(killer);
        final AchievementEntity achievementEntity = playerWrapper.getAchievementEntity();

        if (!achievementEntity.isError()) {
            if (PersistentData.getInt(victim.getUniqueId(), DataTypes.DEATHS) >= 100) {
                achievementEntity.setError(true);
                playerWrapper.setAchievementEntity(achievementEntity);
                ChatFunctions.achievement(killer, "Error");
            }
        }
    }
}
