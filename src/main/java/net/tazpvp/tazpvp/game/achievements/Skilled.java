package net.tazpvp.tazpvp.game.achievements;

import net.tazpvp.tazpvp.data.entity.AchievementEntity;
import net.tazpvp.tazpvp.data.entity.TalentEntity;
import net.tazpvp.tazpvp.data.entity.UserAchievementEntity;
import net.tazpvp.tazpvp.helpers.ChatHelper;
import net.tazpvp.tazpvp.utils.observer.Observable;
import net.tazpvp.tazpvp.wrappers.PlayerWrapper;
import org.bukkit.entity.Player;

public class Skilled extends Observable {

    @Override
    public void death(Player victim, Player killer) {
        final PlayerWrapper pw = PlayerWrapper.getPlayer(killer);
        final UserAchievementEntity userAchievementEntity = pw.getUserAchievementEntity();
        final AchievementEntity achievementEntity = userAchievementEntity.getSkilledAchievementEntity();

        if (!achievementEntity.isCompleted()) {
            TalentEntity talentEntity = pw.getTalentEntity();

            if (talentEntity.isAgile()) return;
            if (talentEntity.isHarvester()) return;
            if (talentEntity.isArchitect()) return;
            if (talentEntity.isCannibal()) return;
            if (talentEntity.isExcavator()) return;
            if (talentEntity.isBlessed()) return;
            if (talentEntity.isMoist()) return;
            if (talentEntity.isMedic()) return;
            if (talentEntity.isProficient()) return;
            if (talentEntity.isNecromancer()) return;
            if (talentEntity.isResilient()) return;
            if (talentEntity.isGlide()) return;
            if (talentEntity.isRevenge()) return;

            achievementEntity.setCompleted(true);
            userAchievementEntity.setSkilledAchievementEntity(achievementEntity);
            pw.setUserAchievementEntity(userAchievementEntity);
            ChatHelper.achievement(killer, "Skilled");
        }
    }
}
