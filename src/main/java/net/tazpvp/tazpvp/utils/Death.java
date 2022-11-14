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

package net.tazpvp.tazpvp.utils;

import me.rownox.nrcore.utils.item.builders.ItemBuilder;
import net.tazpvp.tazpvp.utils.extra.CC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class Death {

    final String prefix = CC.GRAY + "[" + CC.DARK_RED + "☠" + CC.GRAY + "] " + CC.DARK_GRAY;
    private final Player victim;
    private final Player killer;

    public Death(final Player victim, @Nullable final Player killer) {
        this.victim = victim;
        this.killer = killer;
    }

    /**
     * Replenishes the maximum health of the player and removes potion effects.
     */

    public void heal() {
        victim.setHealth(20); //TODO: reference
        killer.setHealth(killer.getHealth() + 5);

        for (PotionEffect effect : victim.getActivePotionEffects()) {
            victim.removePotionEffect(effect.getType());
        }
    }

    /**
     * Creates a small chance of a chest spawning in the location of the death with enchantments inside.
     */
    public void coffin() {
        Location loc = victim.getLocation();
        Material chest = new ItemStack(Material.CHEST).getType();
        int chance = new Random().nextInt(100);

        if (chance < 50) {
            Block block = loc.getBlock();
            block.setType(chest);

            Chest coffin = (Chest) block.getState();
            Inventory inv = coffin.getInventory();

            ItemStack enchantment = ItemBuilder.of(Material.ENCHANTED_BOOK, 1).enchantment(coffinEnchant(), coffinEnchantLevel()).build();

            inv.setItem(13, enchantment);
        }
    }

    /**
     * List of all possible enchantments that appear in the coffin.
     * @return Returns a random enchantment out of the list.
     */

    public Enchantment coffinEnchant() {
        List<Enchantment> list = List.of(
            Enchantment.DAMAGE_ALL,
            Enchantment.ARROW_DAMAGE,
            Enchantment.PROTECTION_ENVIRONMENTAL
        );

        return list.get(new Random().nextInt(list.size()));
    }

    public int coffinEnchantLevel() {
        return new Random().nextInt(3 - 1) + 1;
    }

    /**
     * Send personal and broadcast public kill messages.
     * @param p The user that the message is sent to.
     */

    public void killMessage(final Player p) {
        final String who = (p == killer) ? "You" : CC.GRAY + killer.getName();
        final String what = CC.DARK_GRAY + " killed " + CC.GRAY + victim.getName();
        String msg = prefix + who + what;

        p.sendMessage(msg);
    }

    /**
     * Used to send personal death messages or broadcasting suicide messages.
     * @param p The user that the message is sent to.
     */

    public void deathMessage(final Player p) {
        final String who = (p == victim) ? "You" : CC.GRAY + victim.getName();
        String msg = prefix + who + " died.";

        p.sendMessage(msg);
    }

    /**
     * Broadcast a death message for either a suicide or kill.
     * @param which Suicide or kill?
     */

    public void MessageAll(String which) {
        for (Player op : Bukkit.getOnlinePlayers()) {
            if (which.equals("kill")) {
                killMessage(op);
            } else if (which.equals("death")) {
                deathMessage(op);
            }
        }
    }
}
