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

package net.tazpvp.tazpvp.game.npc.characters.achievements.gui;

import net.tazpvp.tazpvp.data.entity.AchievementEntity;
import net.tazpvp.tazpvp.data.entity.UserAchievementEntity;
import net.tazpvp.tazpvp.enums.CC;
import net.tazpvp.tazpvp.enums.StaticItems;
import net.tazpvp.tazpvp.game.crates.KeyFactory;
import net.tazpvp.tazpvp.wrappers.PlayerWrapper;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.gui.Button;
import world.ntdi.nrcore.utils.gui.GUI;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

import java.util.function.BiConsumer;

public class Achievements extends GUI {

    String prefix = CC.DARK_AQUA + "[Lorenzo] " + CC.AQUA;

    public Achievements(Player p) {
        super("Achievements", 5);
        addItems(p);
        open(p);
    }

    private void addItems(Player p) {
        final PlayerWrapper pw = PlayerWrapper.getPlayer(p);
        final UserAchievementEntity UAE = pw.getUserAchievementEntity();

        fill(0, 5*9, ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE, 1).name(" ").build());

        setButton(p,  10, "Adept", "Learn every talent.", UAE, UAE.getAdeptAchievementEntity(), UserAchievementEntity::setAdeptAchievementEntity,  StaticItems.MYTHIC_KEY.getName(), "mythic");
        setButton(p,  11, "Bowling", "Get a kill streak of 50.", UAE, UAE.getBowlingAchievementEntity(), UserAchievementEntity::setBowlingAchievementEntity, StaticItems.RARE_KEY.getName(), "rare");
        setButton(p,  12, "Charm", "Chat 100 times before leaving.", UAE, UAE.getCharmAchievementEntity(), UserAchievementEntity::setCharmAchievementEntity, StaticItems.MYTHIC_KEY.getName(), "mythic");
        setButton(p,  13, "Craftsman", "Combine your sword with an enchantment.", UAE, UAE.getCraftsmanAchievementEntity(), UserAchievementEntity::setCraftsmanAchievementEntity, StaticItems.RARE_KEY.getName(), "rare");
        setButton(p,  14, "Gamble", "Kill a player while at low health.", UAE, UAE.getGambleAchievementEntity(), UserAchievementEntity::setGambleAchievementEntity, StaticItems.MYTHIC_KEY.getName(), "mythic");
        setButton(p,  15, "Gladiator", "Win 35 duels.", UAE, UAE.getGladiatorAchievementEntity(), UserAchievementEntity::setGladiatorAchievementEntity, StaticItems.MYTHIC_KEY.getName(), "mythic");
        setButton(p,  16, "Legend", "Rebirth your character.", UAE, UAE.getLegendAchievementEntity(), UserAchievementEntity::setLegendAchievementEntity, StaticItems.RARE_KEY.getName(), "rare");

        setButton(p,  19, "Merchant", "Trade with Caesar at the mines.", UAE, UAE.getMerchantAchievementEntity(), UserAchievementEntity::setMerchantAchievementEntity, StaticItems.RARE_KEY.getName(), "rare");
        setButton(p,  20, "Superior", "Win an event.", UAE, UAE.getSuperiorAchievementEntity(), UserAchievementEntity::setSuperiorAchievementEntity, StaticItems.MYTHIC_KEY.getName(), "mythic");
        setButton(p,  21, "Zorgin", "Kill Zorg in the mines.", UAE, UAE.getZorginAchievementEntity(), UserAchievementEntity::setZorginAchievementEntity, StaticItems.RARE_KEY.getName(), "rare");
        setButton(p,  22, "Grinder", "Mine 100 ores.", UAE, UAE.getGrinderAchievementEntity(), UserAchievementEntity::setGrinderAchievementEntity, StaticItems.RARE_KEY.getName(), "rare");
        setButton(p,  23, "Artisan", "Place every type of wood plank.", UAE, UAE.getArtisanAchievementEntity(), UserAchievementEntity::setArtisanAchievementEntity, StaticItems.RARE_KEY.getName(), "rare");
        setButton(p,  24, "Harvester", "Collect a player coffin.", UAE, UAE.getHarvesterAchievementEntity(), UserAchievementEntity::setHarvesterAchievementEntity, StaticItems.RARE_KEY.getName(), "rare");
        setButton(p,  25, "Speedrunner", "Get a kill within 30 seconds of launch.", UAE, UAE.getSpeedrunnerAchievementEntity(), UserAchievementEntity::setSpeedrunnerAchievementEntity, StaticItems.RARE_KEY.getName(), "rare");

        setButton(p,  28, "Error", "Die 500 times.", UAE, UAE.getErrorAchievementEntity(), UserAchievementEntity::setErrorAchievementEntity, StaticItems.MYTHIC_KEY.getName(), "mythic");
        setButton(p,  29, "Skilled", "Get level 100 without talents.", UAE, UAE.getSkilledAchievementEntity(), UserAchievementEntity::setSkilledAchievementEntity, StaticItems.MYTHIC_KEY.getName(), "mythic");
        update();
    }

    private void setButton(Player p, int slot, String name, String lore,
                           UserAchievementEntity userEntity, AchievementEntity entity,
                           BiConsumer<UserAchievementEntity, AchievementEntity> setUserAchievementEntityAchievementEntityBiConsumer,
                           String keyName, String keyType) {
        boolean completed = entity.isCompleted();
        String complete = completed ? CC.GREEN + "Complete" : CC.RED + "Incomplete";
        Material mat = completed ? Material.ENCHANTED_BOOK : Material.BOOK;

        boolean collected = entity.isCollected();

        if (!completed) {
            addButton(Button.createBasic(ItemBuilder.of(mat, 1)
                    .name(CC.RED + "" + CC.BOLD + name)
                    .lore(CC.GRAY + lore, " ", CC.GRAY + "Reward: " + keyName, " ", complete)
                    .build()), slot);
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
                PlayerWrapper pw = PlayerWrapper.getPlayer(p);

                p.sendMessage(prefix + "You collected your reward!");
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);

                entity.setCollected(true);
                setUserAchievementEntityAchievementEntityBiConsumer.accept(userEntity, entity);
                pw.setUserAchievementEntity(userEntity);

                p.getInventory().addItem(KeyFactory.getFactory().createKey(keyType, keyName));
                p.closeInventory();
                new Achievements(p);
            }), slot);
        } else {
            addButton(Button.createBasic(ItemBuilder.of(mat, 1)
                    .name(CC.RED + "" + CC.BOLD + name)
                    .lore(CC.GRAY + lore, " ", complete)
                    .build()), slot);
        }

    }
}
