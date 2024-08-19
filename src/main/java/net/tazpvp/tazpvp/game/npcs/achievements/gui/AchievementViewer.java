/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2023, n-tdi
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

package net.tazpvp.tazpvp.game.npcs.achievements.gui;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.data.entity.AchievementEntity;
import net.tazpvp.tazpvp.data.entity.UserAchievementEntity;
import net.tazpvp.tazpvp.data.services.UserAchievementService;
import net.tazpvp.tazpvp.enums.CC;
import net.tazpvp.tazpvp.enums.StaticItems;
import net.tazpvp.tazpvp.game.crates.KeyFactory;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.gui.Button;
import world.ntdi.nrcore.utils.gui.GUI;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class AchievementViewer extends GUI {

    private final UserAchievementService userAchievementService = Tazpvp.getInstance().getUserAchievementService();
    private int slotNum;
    private int count;
    private final Player p;
    private final UserAchievementEntity userAchievementEntity;

    public AchievementViewer(Player p) {
        super("Achievements", 5);
        this.p = p;
        this.userAchievementEntity = userAchievementService.getOrDefault(p.getUniqueId());
        addItems();
        open(p);
    }

    private void addItems() {
        slotNum = 10;
        count = 1;

        fill(0, 5*9, ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE, 1).name(" ").build());

        setButton("Adept", "Learn every talent.", UserAchievementEntity::getAdept, UserAchievementEntity::setAdept,  StaticItems.MYTHIC_KEY.getName(), "mythic");
        setButton("Bowling", "Get a kill streak of 50.", UserAchievementEntity::getBowling, UserAchievementEntity::setBowling, StaticItems.RARE_KEY.getName(), "rare");
        setButton("Charm", "Chat 50 times before leaving.", UserAchievementEntity::getCharm, UserAchievementEntity::setCharm, StaticItems.MYTHIC_KEY.getName(), "mythic");
        setButton("Craftsman", "Combine your sword with an enchantment.", UserAchievementEntity::getCraftsman, UserAchievementEntity::setCraftsman, StaticItems.RARE_KEY.getName(), "rare");
        setButton("Gamble", "Kill a player while at low health.", UserAchievementEntity::getGamble, UserAchievementEntity::setGamble, StaticItems.MYTHIC_KEY.getName(), "mythic");
        setButton("Gladiator", "Win 35 duels.", UserAchievementEntity::getGladiator, UserAchievementEntity::setGladiator, StaticItems.MYTHIC_KEY.getName(), "mythic");
        setButton("Legend", "Rebirth your character.", UserAchievementEntity::getLegend, UserAchievementEntity::setLegend, StaticItems.RARE_KEY.getName(), "rare");

        setButton("Merchant", "Trade with Caesar at the mines.", UserAchievementEntity::getMerchant, UserAchievementEntity::setMerchant, StaticItems.RARE_KEY.getName(), "rare");
        setButton("Superior", "Win an event.", UserAchievementEntity::getSuperior, UserAchievementEntity::setSuperior, StaticItems.MYTHIC_KEY.getName(), "mythic");
        setButton("Artisan", "Place every type of wood plank.", UserAchievementEntity::getArtisan, UserAchievementEntity::setArtisan, StaticItems.RARE_KEY.getName(), "rare");
        setButton("Speedrunner", "Get a kill within 30 seconds of launch.", UserAchievementEntity::getSpeedrunner, UserAchievementEntity::setSpeedrunner, StaticItems.RARE_KEY.getName(), "rare");
        setButton("Error", "Die 500 times.", UserAchievementEntity::getError, UserAchievementEntity::setError, StaticItems.MYTHIC_KEY.getName(), "mythic");
        setButton("Skilled", "Get level 100 without talents.", UserAchievementEntity::getSkilled, UserAchievementEntity::setSkilled, StaticItems.MYTHIC_KEY.getName(), "mythic");
        update();
    }

    private void setButton(String name, String lore,
                           Function<UserAchievementEntity, AchievementEntity> getter,
                           BiConsumer<UserAchievementEntity, AchievementEntity> setter,
                           String keyName, String keyType) {
        final AchievementEntity achievementEntity = getter.apply(userAchievementEntity);
        boolean completed = achievementEntity.isCompleted();
        String complete = completed ? CC.GREEN + "Complete" : CC.RED + "Incomplete";
        Material mat = completed ? Material.ENCHANTED_BOOK : Material.BOOK;

        boolean collected = achievementEntity.isCollected();

        if (!completed) {
            addButton(Button.createBasic(ItemBuilder.of(mat, 1)
                    .name(CC.RED + "" + CC.BOLD + name)
                    .lore(CC.GRAY + lore, " ", CC.GRAY + "Reward: " + keyName, " ", complete)
                    .build()), slotNum);
        } else if (!collected) {
            addButton(Button.create(ItemBuilder.of(mat, 1)
                                    .name(CC.RED + "" + CC.BOLD + name)
                                    .lore(
                                            CC.GRAY + lore,
                                            " ",
                                            CC.GRAY + "Reward: " + keyName,
                                            CC.YELLOW + "(Click to claim reward)",
                                            " ",
                                            complete
                                    ).build(), (e) -> {

                p.sendMessage("You collected your reward!");
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);

                achievementEntity.setCollected(true);
                setter.accept(userAchievementEntity, achievementEntity);
//                userAchievementService.saveUserAchievementEntity(userAchievementEntity);
                Tazpvp.getInstance().getAchievementService().saveAchievementEntity(achievementEntity);

                p.getInventory().addItem(KeyFactory.getFactory().createKey(keyType, keyName));
                p.closeInventory();
                new AchievementViewer(p);
            }), slotNum);
            calcSlot();
        } else {
            addButton(Button.createBasic(ItemBuilder.of(mat, 1)
                    .name(CC.RED + "" + CC.BOLD + name)
                    .lore(CC.GRAY + lore, " ", complete)
                    .build()), slotNum);
        }
        calcSlot();
    }

    public void calcSlot() {
        if (count % 7 == 0) {
            slotNum += 2;
            count = 0;
        }
        slotNum ++;
        count++;
    }
}
