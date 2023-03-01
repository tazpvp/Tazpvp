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

package net.tazpvp.tazpvp.utils.objects;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.utils.data.DataTypes;
import net.tazpvp.tazpvp.utils.data.PersistentData;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.functions.PlayerFunctions;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
import world.ntdi.nrcore.utils.config.ConfigUtils;
import world.ntdi.nrcore.utils.gui.Button;
import world.ntdi.nrcore.utils.gui.GUI;
import world.ntdi.nrcore.utils.holograms.Hologram;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;
import world.ntdi.nrcore.utils.item.builders.SkullBuilder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class Death {

    private final Player victim;
    private final Player killer;
    private final Location deathLocation;
    private final Random r = new Random();

    public Death(final Player victim, @Nullable final Player killer) {
        this.victim = victim;
        this.deathLocation = victim.getLocation();
        this.killer = killer;
        Tazpvp.getObservers().forEach(observer -> observer.death(victim, killer));
    }

    /**
     * Creates a small chance of a chest spawning in the location of the death with enchantments inside.
     */
    public void coffin() {
        if (killer == victim) return;

        Location loc = victim.getLocation();
        Material chest = new ItemStack(Material.CHEST).getType();
        int chance = new Random().nextInt(100);

        if (chance < 50) {
            Block block = loc.getBlock();
            block.setType(chest);

            Chest coffin = (Chest) block.getState();
            Inventory inv = coffin.getInventory();
            GUI gui = new GUI(inv);

            Enchantment ench = coffinEnchant();
            int lvl = coffinEnchantLevel();

            ItemStack enchantment = ItemBuilder.of(Material.ENCHANTED_BOOK, 1).enchantment(ench, lvl).build();

            Hologram hologram = new Hologram(new String[]{"&6" + ench.getKey().getKey() + " &c" + lvl}, loc.getBlock().getLocation().add(0.5, 0, 0.5).subtract(0, 0.5, 0), false);

            gui.addButton(Button.create(enchantment, (e) -> {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        gui.setReturnsItems(true);
                        e.getWhoClicked().closeInventory();
                        e.getWhoClicked().getInventory().addItem(enchantment);
                        gui.destroy();
                        block.setType(Material.AIR);
                        hologram.deleteHologram();
                    }
                }.runTaskLater(Tazpvp.getInstance(), 1);
            }), 13);

            gui.update();


            new BukkitRunnable() {
                @Override
                public void run() {
                    hologram.deleteHologram();
                    block.setType(Material.AIR);
                }
            }.runTaskLater(Tazpvp.getInstance(), 20 * 10);
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

        return list.get(r.nextInt(list.size()));
    }

    public int coffinEnchantLevel() {
        return r.nextInt(2) + 1;
    }

    /**
     * Run functionality of dropping the victim's head at their death location
     */
    public void dropHead() {
        if (skullDropChange()) {
            World w = deathLocation.getWorld();
            w.dropItemNaturally(deathLocation.add(0, 1, 0), makeSkull(victim));
        }
    }

    /**
     * 25% chance of returning true
     * @return true or false
     */
    private boolean skullDropChange() {
        Random r = new Random();
        return r.nextInt(4) == 3;
    }

    /**
     * Make a skull ItemStack with a given player
     * @param p The Player you want to make the skull of
     * @return the ItemStack with the Player's skull
     */
    private ItemStack makeSkull(@Nonnull final Player p) {
        ItemStack stack = SkullBuilder.of(1, p.getName()).setHeadTexture(p).build();
        return stack;
    }

    public void rewards() {
        if (killer != victim) {
            final int xp = 15;
            final int coins = 26;

            sendActionbar(xp, coins);
            PersistentData.add(killer, DataTypes.COINS, coins);
            PersistentData.add(killer, DataTypes.XP, xp);
        }
    }

    @SuppressWarnings("all")
    private void sendActionbar(final int xp, final int coins) {
        killer.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(CC.AQUA + "EXP: " + xp + CC.GOLD + " Coins: " + coins));
    }
}
