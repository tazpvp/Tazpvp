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

package net.tazpvp.tazpvp.npc.npcs;

import net.tazpvp.tazpvp.npc.NPC;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class Bub extends NPC {
    /**
     * Generate a villager NPC
     *
     * @param NAME       Name of the villager
     * @param SPAWN      Spawn of the villager
     * @param PROFESSION Profession of the villager, see {@code Villager.Profession}
     * @param TYPE       Type of the villager, see {@code Villager.Type}
     * @param SOUND
     */
    public Bub(@Nonnull String NAME, @Nonnull Location SPAWN, @Nonnull Villager.Profession PROFESSION, @Nonnull Villager.Type TYPE, @Nonnull Sound SOUND) {
        super(NAME, SPAWN, PROFESSION, TYPE, SOUND);
    }

    @Override
    public void interact(@Nonnull PlayerInteractAtEntityEvent e, @Nonnull Player p) {
        sellHead(p);
    }

    public static void sellHead(Player p) {
        ItemStack item = p.getInventory().getItemInMainHand();
        if (item.equals(Material.PLAYER_HEAD)) {
            int num = item.getAmount();
            item.setType(Material.AIR);
            p.getInventory().addItem(new ItemStack(Material.AMETHYST_SHARD, num));
        }
    }
}