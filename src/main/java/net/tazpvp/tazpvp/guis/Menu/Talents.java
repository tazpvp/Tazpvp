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

package net.tazpvp.tazpvp.guis.Menu;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.utils.data.PersistentData;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.functions.PlayerFunctions;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import world.ntdi.nrcore.utils.gui.Button;
import world.ntdi.nrcore.utils.gui.GUI;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;
import world.ntdi.nrcore.utils.item.builders.PotionBuilder;

public class Talents extends GUI {

    int shardCount;

    public Talents(Player p) {
        super("Talents", 4);
        addItems(p);
        open(p);
    }

    private void addItems(Player p) {
        net.tazpvp.tazpvp.talents.Talents talents = PersistentData.getTalents(p.getUniqueId());

        fill(0, 4*9, ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE, 1).name(" ").build());

        setButton(p, 10, Material.NETHERITE_SWORD, 8, "Revenge", "Set the player who killed you on fire.", talents.is("Revenge"));
        setButton(p, 11, Material.WATER_BUCKET, 9, "Moist", "You can no longer be set on fire.", talents.is("Moist"));
        setButton(p, 12, Material.SHIELD, 12, "Resilient", "Gain 2 absorption hearts on kill.", talents.is("Resilient"));
        setButton(p, 13, Material.GOLDEN_PICKAXE,8, "Excavator", "Mining gives you experience.", talents.is("Excavator"));
        setButton(p, 14, Material.CRAFTING_TABLE,6, "Architect", "A chance to reclaim the block you placed.", talents.is("Architect"));
        setButton(p, 15, Material.BOW,8, "Hunter", "A chance to reclaim the arrow you shot.", talents.is("Hunter"));
        setButton(p, 16, Material.ROTTEN_FLESH,9, "Cannibal", "Replenish your hunger on kill.", talents.is("Cannibal"));

        setButton(p, 19, Material.FEATHER,14, "Agile", "Gain a speed boost on kill.", talents.is("Agile"));
        setButton(p, 20, Material.SHEARS,11, "Harvester", "Better chance that players drop heads.", talents.is("Harvester"));
        setButton(p, 21, Material.NETHERITE_HOE,15, "Necromancer", "Gain more from player coffins.", talents.is("Necromancer"));
        setButton(p, 22, Material.GOLDEN_APPLE,20, "Blessed", "A chance of getting a golden apple from a kill.", talents.is("Blessed"));
        setButton(p, 23, Material.ELYTRA,6, "Glide", "The launch pad pushes you further.", talents.is("Glide"));
        setButton(p, 24, Material.EXPERIENCE_BOTTLE,9, "Proficient", "Gain experience from duels.", talents.is("Proficient"));
        setButton(p, 25, PotionBuilder.of(PotionBuilder.PotionType.SPLASH).setColor(Color.PURPLE).build().getType(),10, "Medic", "Heal nearby guild mates on kill.", talents.is("Medic"));
    }

    private void setButton(Player p, int slot, Material mat, int cost, String name, String lore, boolean completed) {

        String complete = completed ? CC.GREEN + "Active" : CC.RED + "Inactive";

        addButton(Button.create(ItemBuilder.of(mat, 1)
                .name(CC.AQUA +  "" + CC.BOLD +name)
                .lore(CC.DARK_AQUA + lore, " ",CC.GRAY + "Cost: " + cost + " Shards", " ", complete)
                .flag(ItemFlag.HIDE_POTION_EFFECTS).flag(ItemFlag.HIDE_ATTRIBUTES)
                .build(), (e) -> {

            if (!completed) {

                shardCount = PlayerFunctions.countShards(p);

                if (shardCount >= cost) {

                    PlayerFunctions.takeShards(p, cost);

                    net.tazpvp.tazpvp.talents.Talents talents = PersistentData.getTalents(p);

                    talents.set(name, true); // TODO: Add handlers incase of errors that may occur when changing invalid name, for now this should work tho so shrug.

                    PersistentData.setTalents(p, talents);
                    p.closeInventory();
                    p.sendTitle("New Talent",  name, 10, 20, 10);
                    p.playSound(p.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, 1, 1);

                    Tazpvp.getObservers().forEach(observer -> observer.talent(p));
                } else {
                    p.sendMessage("You do not have enough shards.");
                }
            } else {
                p.sendMessage("You already own this talent");
            }

        }), slot);
    }
}
