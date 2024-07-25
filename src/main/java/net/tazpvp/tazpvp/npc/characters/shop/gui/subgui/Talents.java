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
import net.tazpvp.tazpvp.data.DataTypes;
import net.tazpvp.tazpvp.data.PersistentData;
import net.tazpvp.tazpvp.data.entity.TalentEntity;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import world.ntdi.nrcore.utils.gui.Button;
import world.ntdi.nrcore.utils.gui.GUI;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;
import world.ntdi.nrcore.utils.item.builders.PotionBuilder;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class Talents extends GUI {

    String prefix = CC.DARK_AQUA + "[Lorenzo] " + CC.AQUA;
    PlayerWrapper pw;

    public Talents(Player p) {
        super("Talents", 4);
        pw = PlayerWrapper.getPlayer(p);
        addItems(p);
        open(p);
    }

    private void addItems(Player p) {
        fill(0, 4*9, ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE, 1).name(" ").build());

        setButton(p, 10, Material.NETHERITE_SWORD, 8000, "Revenge", "Set the player who killed you on fire.", TalentEntity::isRevenge, (talentEntity -> talentEntity.setRevenge(true)));
        setButton(p, 11, Material.WATER_BUCKET, 9000, "Moist", "You can no longer be set on fire.", TalentEntity::isMoist, (talentEntity -> talentEntity.setMoist(true)));
        setButton(p, 12, Material.SHIELD, 12000, "Resilient", "Gain 2 absorption hearts on kill.", TalentEntity::isResilient, (talentEntity -> talentEntity.setResilient(true)));
        setButton(p, 13, Material.GOLDEN_PICKAXE,8000, "Excavator", "Mining gives you experience.", TalentEntity::isExcavator, (talentEntity -> talentEntity.setExcavator(true)));
        setButton(p, 14, Material.CRAFTING_TABLE,6000, "Architect", "A chance to reclaim the block you placed.", TalentEntity::isArchitect, (talentEntity -> talentEntity.setArchitect(true)));
        setButton(p, 15, Material.BOW,8000, "Hunter", "A chance to reclaim the arrow you shot.", TalentEntity::isHunter, (talentEntity -> talentEntity.setHunter(true)));
        setButton(p, 16, Material.ROTTEN_FLESH,9000, "Cannibal", "Replenish your hunger on kill.", TalentEntity::isCannibal, (talentEntity -> talentEntity.setCannibal(true)));

        setButton(p, 19, Material.FEATHER,14000, "Agile", "Gain a speed boost on kill.", TalentEntity::isAgile, (talentEntity -> talentEntity.setAgile(true)));
        setButton(p, 20, Material.SHEARS,11000, "Harvester", "Better chance that players drop heads.", TalentEntity::isHarvester, (talentEntity -> talentEntity.setHarvester(true)));
        setButton(p, 21, Material.NETHERITE_HOE,15000, "Necromancer", "Double the items that drop from kills.", TalentEntity::isNecromancer, (talentEntity -> talentEntity.setNecromancer(true)));
        setButton(p, 22, Material.GOLDEN_APPLE,20000, "Blessed", "A chance of getting a golden apple from a kill.", TalentEntity::isBlessed, (talentEntity -> talentEntity.setBlessed(true)));
        setButton(p, 23, Material.ELYTRA,6000, "Glide", "The launch pad pushes you further.", TalentEntity::isGlide, (talentEntity -> talentEntity.setGlide(true)));
        setButton(p, 24, Material.EXPERIENCE_BOTTLE,9000, "Proficient", "Gain experience from duels.", TalentEntity::isProficient, (talentEntity -> talentEntity.setProficient(true)));
        ItemStack potion = PotionBuilder.of(PotionBuilder.PotionType.SPLASH).setColor(Color.PURPLE).build();
        setButton(p, 25, potion.getType(),10, "Medic", "Heal nearby guild mates on kill.", TalentEntity::isMedic, (talentEntity -> talentEntity.setMedic(true)));
    }

    private void setButton(Player p, int slot, Material mat, int cost, String name, String lore, Predicate<TalentEntity> hasTalentPredicate, Consumer<TalentEntity> talentEntityConsumer) {
        TalentEntity talentEntity = pw.getTalentEntity();
        boolean active = hasTalentPredicate.test(talentEntity);

        String complete = active ? CC.GREEN + "Active" : CC.RED + "Inactive";

        addButton(Button.create(ItemBuilder.of(mat, 1)
                .name(CC.AQUA +  "" + CC.BOLD +name)
                .lore(CC.DARK_AQUA + lore, " ",CC.GRAY + "Cost: " + cost + " Coins", " ", complete)
                .flag(ItemFlag.HIDE_ATTRIBUTES)
                .build(), (e) -> {

            if (!active) {
                if (PersistentData.getInt(p.getUniqueId(), DataTypes.COINS) >= cost) {

                    talentEntityConsumer.accept(talentEntity);
                    pw.setTalentEntity(talentEntity);

                    PersistentData.remove(p.getUniqueId(), DataTypes.COINS, cost);
                    p.closeInventory();
                    p.sendTitle(CC.AQUA + "" + CC.BOLD + "New Talent",  CC.DARK_AQUA + name, 10, 20, 10);
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

        }), slot);
    }
}
