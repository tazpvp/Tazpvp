/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2022, n-tdi
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package net.tazpvp.tazpvp.game.achievements;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.data.entity.AchievementEntity;
import net.tazpvp.tazpvp.data.entity.TalentEntity;
import net.tazpvp.tazpvp.data.entity.UserAchievementEntity;
import net.tazpvp.tazpvp.data.services.UserAchievementService;
import net.tazpvp.tazpvp.helpers.ChatHelper;
import net.tazpvp.tazpvp.utils.observer.Observable;
import net.tazpvp.tazpvp.wrappers.PlayerWrapper;
import org.bukkit.entity.Player;

public class Adept extends Observable {
    private final UserAchievementService userAchievementService = Tazpvp.getInstance().getUserAchievementService();
    @Override
    public void talent(Player p) {
        final PlayerWrapper pw = PlayerWrapper.getPlayer(p);
        final UserAchievementEntity userAchievementEntity = userAchievementService.getUserAchievementEntity(p.getUniqueId());
        final AchievementEntity achievementEntity = userAchievementEntity.getAdept();

        if (!achievementEntity.isCompleted()) {
            TalentEntity talentEntity = pw.getTalentEntity();

            if (!talentEntity.isAgile()) return;
            if (!talentEntity.isHarvester()) return;
            if (!talentEntity.isArchitect()) return;
            if (!talentEntity.isCannibal()) return;
            if (!talentEntity.isExcavator()) return;
            if (!talentEntity.isBlessed()) return;
            if (!talentEntity.isMoist()) return;
            if (!talentEntity.isMedic()) return;
            if (!talentEntity.isProficient()) return;
            if (!talentEntity.isNecromancer()) return;
            if (!talentEntity.isResilient()) return;
            if (!talentEntity.isGlide()) return;
            if (!talentEntity.isRevenge()) return;

            achievementEntity.setCompleted(true);
            userAchievementEntity.setAdept(achievementEntity);
            userAchievementService.saveUserAchievementEntity(userAchievementEntity);

            ChatHelper.achievement(p, "Adept");
        }
    }
}
