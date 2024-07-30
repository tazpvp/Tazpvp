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

package net.tazpvp.tazpvp.game.crates;

import lombok.Getter;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.data.entity.PlayerStatEntity;
import net.tazpvp.tazpvp.data.services.PlayerStatService;
import net.tazpvp.tazpvp.utils.functions.ChatFunctions;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import world.ntdi.nrcore.utils.item.builders.EnchantmentBookBuilder;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

import java.util.ArrayList;
import java.util.List;

public class CrateManager {

    @Getter
    private List<Crate> crates;

    public CrateManager() {
        this.crates = new ArrayList<>();

        getCrates().add(new Crate(
                new Location(Bukkit.getWorld("arena"), -16, 99, 7), ChatFunctions.gradient("#03fc39", "Common Crate", true), "common",
                    createItem("Azure Vapor", "Extinguish flames.", Material.BLUE_ORCHID, 1),
                    createItem("Sticky Web", "Slow down your enemies.", Material.COBWEB, 5),
                    createItem("Ink Splash", "Blind your enemies.", Material.INK_SAC, 3),
                    createItem("Lighter", "Set things afire.", Material.FLINT_AND_STEEL,  1),
                    createItem("Exp Bottle", "Mend your armor.", Material.EXPERIENCE_BOTTLE, 1),
                    createItem("Hatchet", "Break wooden blocks.", Material.GOLDEN_AXE, 1),
                    createItem("Shears", "Break wool blocks.", Material.SHEARS, 1),
                    createItem("Arrows", "Projectiles.", Material.ARROW, 5),
                    createItem("Steak", "We have the meats.", Material.COOKED_BEEF, 5),
                    createItem("Gold Carrot", "Good nutrition.", Material.GOLDEN_CARROT, 5),
                    createItem("Gold Apple", "Only for rich people.", Material.GOLDEN_APPLE, 1),
                    createItem("Spectral Arrow", "Highlight targets.", Material.SPECTRAL_ARROW, 1),
                    createItem("Crossbow", "Stronger than the bow.", Material.CROSSBOW, 1)
                ));
        getCrates().add(new Crate(
                new Location(Bukkit.getWorld("arena"), -15, 99, 5), ChatFunctions.gradient("#039dfc", "Rare Crate", true), "rare",
                createItem(Enchantment.MENDING),
                createItem(Enchantment.SHARPNESS),
                createItem(Enchantment.POWER),
                createItem(Enchantment.UNBREAKING),
                createItem(Enchantment.PROTECTION),
                createItem(Enchantment.PUNCH),
                createItem(Enchantment.KNOCKBACK),
                createItem(Enchantment.FLAME),
                createItem(Enchantment.FIRE_ASPECT)
        ));
        getCrates().add(new Crate(
                new Location(Bukkit.getWorld("arena"), -13, 99, 4), ChatFunctions.gradient("#db3bff", "Mythic Crate", true), "mythic",
                createItem("Shard", "Valuable gem.", Material.AMETHYST_SHARD,1),
                createItem("Shard", "Valuable gem.", Material.AMETHYST_SHARD,2),
                createItem("Shard", "Valuable gem.", Material.AMETHYST_SHARD,3),
                createItem("Shard", "Valuable gem.", Material.AMETHYST_SHARD,4)
        ));
    }

    public ItemStack createItem(String name, String description, Material material, int amount) {
        String name2 = ChatFunctions.gradient("#db3bff", name, true);
        return ItemBuilder.of(material).amount(amount).name(name2).build();
    }

    public ItemStack createItem(Enchantment enchantment) {
        return new EnchantmentBookBuilder().enchantment(enchantment, 1).build();
    }

    public boolean canClaimDaily(OfflinePlayer p) {
        PlayerStatService playerStatService = Tazpvp.getInstance().getPlayerStatService();
        PlayerStatEntity playerStatEntity = playerStatService.getOrDefault(p.getUniqueId());

        long timeNow = System.currentTimeMillis();
        long timeSinceLastDaily = playerStatEntity.getLastClaim();

        return timeNow - timeSinceLastDaily > 24 * 60 * 60 * 1000;
    }
}
