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
 *
 */

package net.tazpvp.tazpvp.utils.objects;

import lombok.Getter;
import net.tazpvp.tazpvp.utils.data.PersistentData;
import net.tazpvp.tazpvp.utils.functions.DeathFunctions;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import world.ntdi.nrcore.utils.holograms.Hologram;
import world.ntdi.nrcore.utils.item.builders.EnchantmentBookBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Coffin {
    @Getter
    private final Location location;
    @Getter
    private final Hologram hologram;
    private final Random r = new Random();

    public Coffin(Location location, Hologram hologram) {
        this.location = location;
        this.hologram = hologram;

        location.getBlock().setType(Material.CHEST);

        DeathFunctions.addCoffin(this);
        System.out.println("made coffin");
    }

    public void destroy() {
        location.getBlock().setType(Material.AIR);
        hologram.deleteHologram();
    }

    public void doTheDo(Player p) {
        destroy();

        p.getInventory().addItem(coffinItem());

        Location playerLocation = p.getLocation();
        playerLocation.getWorld().spawnParticle(Particle.SPELL_MOB, playerLocation, 100, 0.5, 1, 0.5, 0.2);
        p.playSound(p.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1, 1);

        if (PersistentData.getTalents(p).is("Necromancer")) {
            p.getInventory().addItem(coffinItem());
        }
    }

    /**
     * List of all possible enchantments that appear in the coffin.
     * @return Returns a random enchantment out of the list.
     */
    private ItemStack coffinItem() {
        Random r = new Random();
        List<PotionType> tippedArrows = Arrays.asList(
                PotionType.SPEED,
                PotionType.SLOWNESS,
                PotionType.INSTANT_HEAL,
                PotionType.INSTANT_DAMAGE,
                PotionType.POISON,
                PotionType.REGEN,
                PotionType.STRENGTH,
                PotionType.WEAKNESS,
                PotionType.LUCK
        );
        List<Enchantment> list = List.of(
                Enchantment.DAMAGE_ALL,
                Enchantment.ARROW_DAMAGE,
                Enchantment.PROTECTION_ENVIRONMENTAL,
                Enchantment.MENDING,
                Enchantment.PROTECTION_FIRE,
                Enchantment.PROTECTION_PROJECTILE,
                Enchantment.ARROW_FIRE,
                Enchantment.FIRE_ASPECT,
                Enchantment.SWEEPING_EDGE,
                Enchantment.KNOCKBACK,
                Enchantment.DURABILITY
        );

        if ((r.nextInt(2) + 1) <= 1) {
            Enchantment enchant = list.get(r.nextInt(list.size()));
            return new EnchantmentBookBuilder().enchantment(enchant, 1).build();
        }

        PotionType potionType = tippedArrows.get(r.nextInt(tippedArrows.size()));

        ItemStack tippedArrow = new ItemStack(Material.TIPPED_ARROW, 10);
        PotionMeta potionMeta = (PotionMeta) tippedArrow.getItemMeta();
        potionMeta.setBasePotionData(new PotionData(potionType));
        tippedArrow.setItemMeta(potionMeta);

        return tippedArrow;
    }
}
