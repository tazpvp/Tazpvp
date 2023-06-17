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

package net.tazpvp.tazpvp.utils.crate;

import lombok.Getter;
import net.tazpvp.tazpvp.utils.enums.CC;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import world.ntdi.nrcore.utils.holograms.Hologram;
import world.ntdi.postglam.data.Tuple;

import java.util.Random;

public class Crate {
    @Getter
    private final Location location;
    @Getter
    private final Hologram hologram;
    @Getter
    private final Material block;
    @Getter
    private final String type;
    @Getter
    private final Material[] crateDrops;

    public Crate(Location location, String hologramText, String type, Material... crateDrops) {
        this.location = location;
        Location hologramLocation = new Location(location.getWorld(), location.getX() + 0.5, location.getY(), location.getZ() + 0.5);
        this.hologram = new Hologram(hologramText, hologramLocation, false);
        this.type = type;
        this.block = getLocation().getBlock().getType();
        getLocation().getBlock().setType(Material.BEACON);
        this.crateDrops = crateDrops;
    }

    public void acceptClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (isCrate(e)) {
            if (hasKey(p)) { // Only runs if the player interacts with a crate and has the proper key
                getLocation().getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, getLocation(), 1);
                removeOne(p);
                p.playSound(p.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, 1F, 1F);
                Tuple<String, ItemStack> randomShopItem = randomShopItem();
                p.sendMessage(CC.GREEN + "You got " + randomShopItem.getA() + "!");
                p.getInventory().addItem(randomShopItem.getB());
            } else { // YEET the player backwards
                p.setVelocity(p.getLocation().getDirection().multiply(-1));
                p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1F, 1F);
                p.sendMessage(CC.RED + "No crate key!");
            }
        }
    }

    private boolean isCrate(PlayerInteractEvent e) {
        return (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getLocation().equals(getLocation()));
    }

    private boolean hasKey(Player p) {
        if (p.getInventory().getItemInMainHand().getType() == Material.TRIPWIRE_HOOK) {
            ItemStack i = p.getInventory().getItemInMainHand();
            if (i.getItemMeta() != null) {
                return KeyFactory.getFactory().isCrateKey(i, getType());
            }
        }
        return false;
    }

    /*
        Remove one item from the player's inventory, needed because can't set amount of an Item to 0 so must set to AIR if amount is 1
     */
    private void removeOne(Player p) {
        ItemStack i = p.getInventory().getItemInMainHand();
        if (i.getAmount() > 1) {
            i.setAmount(i.getAmount() - 1);
            p.getInventory().setItemInMainHand(i);
        } else {
            p.getInventory().remove(i);
        }
    }

    private Tuple<String, ItemStack> randomShopItem() {
        Random r = new Random();
        Material material = crateDrops[r.nextInt(crateDrops.length)];
        ItemStack i = new ItemStack(material);
        String name = i.getType().name().replaceAll("_", "");
        return new Tuple<>(name, i);
    }
}
