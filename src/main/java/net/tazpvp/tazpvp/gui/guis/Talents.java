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

package net.tazpvp.tazpvp.gui.guis;

import net.tazpvp.tazpvp.utils.data.PersistentData;
import net.tazpvp.tazpvp.utils.enums.CC;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import world.ntdi.nrcore.utils.gui.Button;
import world.ntdi.nrcore.utils.gui.GUI;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

public class Talents extends GUI {

    public Talents(Player p) {
        super("Talents", 4);

        net.tazpvp.tazpvp.talents.Talents TALENT = PersistentData.getTalents(p.getUniqueId());

        setButton(p, 10, 10, "Revenge", "Set the player who killed you on fire.", TALENT.isRevenge());
        setButton(p, 10, 10, "Moist", "You can no longer be set on fire.", TALENT.isRevenge());
        setButton(p, 10, 10, "Resilient", "Gain 2 absorption hearts on kill.", TALENT.isRevenge());
        setButton(p, 10, 10, "Excavator", "Mining gives you experience.", TALENT.isRevenge());
        setButton(p, 10, 10, "Architect", "A chance to reclaim the block you placed.", TALENT.isRevenge());
        setButton(p, 10, 10, "Hunter", "A chance to reclaim the arrow you shot.", TALENT.isRevenge());
        setButton(p, 10, 10, "Cannibal", "Replenish your hunger on kill.", TALENT.isRevenge());

        setButton(p, 10, 10, "Agile", "Gain a speed boost on kill.", TALENT.isRevenge());
        setButton(p, 10, 10, "Harvester", "Better chance that players drop heads.", TALENT.isRevenge());
        setButton(p, 10, 10, "Necromancer", "Gain more from player coffins.", TALENT.isRevenge());
        setButton(p, 10, 10, "Blessed", "A chance of getting a gold apple from a kill.", TALENT.isRevenge());
        setButton(p, 10, 10, "Glider", "The launch pad pushes you further.", TALENT.isRevenge());
        setButton(p, 10, 10, "Proficient", "Gain experience from duels.", TALENT.isRevenge());
        setButton(p, 10, 10, "Medic", "Heal nearby guild mates on kill.", TALENT.isRevenge());

        fill(0, 4*9-1, ItemBuilder.of(Material.BLACK_STAINED_GLASS, 1).name(" ").build());

        open(p);
    }

    private void setButton(Player p, int slot, int cost, String name, String lore, boolean completed) {

        String complete = completed ? CC.GREEN + "Active" : CC.RED + "Inactive";
        Material mat = completed ? Material.ENCHANTED_BOOK : Material.WRITTEN_BOOK;

        addButton(Button.create(ItemBuilder.of(mat, 1).name(CC.AQUA + name).lore(CC.GRAY + lore, " ",CC.DARK_AQUA + "Cost: " + cost, " ", complete).build(), (e) -> {
            p.getInventory().addItem(new ItemStack(mat, 1));
        }), slot);
    }


}
