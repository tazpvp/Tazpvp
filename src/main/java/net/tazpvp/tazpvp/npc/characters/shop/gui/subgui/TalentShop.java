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

package net.tazpvp.tazpvp.npc.characters.shop.gui.subgui;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.data.entity.PlayerStatEntity;
import net.tazpvp.tazpvp.data.entity.TalentEntity;
import net.tazpvp.tazpvp.data.services.PlayerStatService;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.enums.TalentsEnum;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import world.ntdi.nrcore.utils.gui.Button;
import world.ntdi.nrcore.utils.gui.GUI;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class TalentShop extends GUI {

    private int slotNum;
    private int count;
    PlayerStatService playerStatService;
    Player p;

    String prefix = CC.DARK_AQUA + "[Lorenzo] " + CC.AQUA;
    PlayerWrapper pw;

    public TalentShop(Player p, PlayerStatService playerStatService) {
        super("Talents", 4);
        this.playerStatService = playerStatService;
        this.p = p;
        pw = PlayerWrapper.getPlayer(p);
        addItems();
        open(p);
    }

    private void addItems() {
        slotNum = 10;
        count = 1;
        fill(0, 4*9, ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE, 1).name(" ").build());

        setButton(TalentsEnum.REVENGE, TalentEntity::isRevenge, (t -> t.setRevenge(true)));
        setButton(TalentsEnum.MOIST, TalentEntity::isMoist, (t -> t.setMoist(true)));
        setButton(TalentsEnum.RESILIENT, TalentEntity::isResilient, (t -> t.setResilient(true)));
        setButton(TalentsEnum.EXCAVATOR, TalentEntity::isExcavator, (t -> t.setExcavator(true)));
        setButton(TalentsEnum.ARCHITECT, TalentEntity::isArchitect, (t -> t.setArchitect(true)));
        setButton(TalentsEnum.HUNTER, TalentEntity::isHunter, (t -> t.setHunter(true)));
        setButton(TalentsEnum.CANNIBAL, TalentEntity::isCannibal, (t -> t.setCannibal(true)));

        setButton(TalentsEnum.AGILE, TalentEntity::isAgile, (t -> t.setAgile(true)));
        setButton(TalentsEnum.HARVESTER, TalentEntity::isHarvester, (t -> t.setHarvester(true)));
        setButton(TalentsEnum.NECROMANCER, TalentEntity::isNecromancer, (t -> t.setNecromancer(true)));
        setButton(TalentsEnum.BLESSED, TalentEntity::isBlessed, (t -> t.setBlessed(true)));
        setButton(TalentsEnum.GLIDE, TalentEntity::isGlide, (t -> t.setGlide(true)));
        setButton(TalentsEnum.PROFICIENT, TalentEntity::isProficient, (t -> t.setProficient(true)));
        setButton(TalentsEnum.MEDIC, TalentEntity::isMedic, (t -> t.setMedic(true)));
    }

    private void setButton(TalentsEnum talent, Predicate<TalentEntity> hasTalentPredicate, Consumer<TalentEntity> talentEntityConsumer) {
        TalentEntity talentEntity = pw.getTalentEntity();
        boolean active = hasTalentPredicate.test(talentEntity);
        PlayerStatEntity playerStatEntity = playerStatService.getOrDefault(p.getUniqueId());

        String complete = active ? CC.GREEN + "Active" : CC.RED + "Inactive";

        addButton(Button.create(ItemBuilder.of(talent.getMat(), 1)
                .name(CC.AQUA +  "" + CC.BOLD + talent.getName())
                .lore(CC.DARK_AQUA + talent.getLore(), " ",CC.GRAY + "Cost: " + talent.getCost() + " Coins", " ", complete)
                .flag(ItemFlag.HIDE_ATTRIBUTES)
                .build(), (_) -> {

            if (!active) {
                if (playerStatEntity.getCoins() >= talent.getCost()) {

                    talentEntityConsumer.accept(talentEntity);
                    pw.setTalentEntity(talentEntity);

                    playerStatEntity.setCoins(playerStatEntity.getCoins() - talent.getCost());
                    p.closeInventory();
                    p.sendTitle(CC.AQUA + "" + CC.BOLD + "New Talent",  CC.DARK_AQUA + talent.getName(), 10, 20, 10);
                    p.playSound(p.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, 1, 1);

                    Tazpvp.getObservers().forEach(observer -> observer.talent(p));
                } else {
                    p.sendMessage(prefix + "You do not have enough coins.");
                    p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                }
            } else {
                p.sendMessage(prefix + "You already own this talent");
                p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
            }

        }), slotNum);
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
