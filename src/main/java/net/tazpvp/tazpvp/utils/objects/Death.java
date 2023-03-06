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

import lombok.Getter;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.utils.data.DataTypes;
import net.tazpvp.tazpvp.utils.data.PersistentData;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.functions.CombatTagFunctions;
import net.tazpvp.tazpvp.utils.functions.PlayerFunctions;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
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
import java.util.UUID;

public class Death {

    private final Player victim;
    private final UUID victimID;

    private final Entity killer;
    private Player pKiller = null;
    private UUID killerID = null;

    private final Location location;
    private final Random r = new Random();

    public Death(Player victim, @Nullable final Entity killer) {
        this.victim = victim;
        this.victimID = victim.getUniqueId();
        this.killer = killer;
        this.location = victim.getLocation();
        if (killer instanceof Player pKiller) {
            this.killerID = pKiller.getUniqueId();
            this.pKiller = pKiller;
            Tazpvp.getObservers().forEach(observer -> observer.death(victim, pKiller));
        }
    }

    public void coffin() {
        if (killer == victim) return;

        Material chest = new ItemStack(Material.CHEST).getType();
        int chance = new Random().nextInt(100);

        if (chance <= 5) {
            Block block = location.getBlock();
            block.setType(chest);

            Chest coffin = (Chest) block.getState();
            Inventory inv = coffin.getInventory();
            GUI gui = new GUI(inv);

            Enchantment ench = coffinEnchant();
            int lvl = r.nextInt(2) + 1;

            ItemStack enchantment = ItemBuilder.of(Material.ENCHANTED_BOOK, 1).enchantment(ench, lvl).build();
            Hologram hologram = new Hologram(new String[]{"&b&l" + ench.getKey().getKey() + " &6&l" + lvl}, location.getBlock().getLocation().add(0.5, 0, 0.5).subtract(0, 0.5, 0), false);

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

    public void dropHead() {
        if (new Random().nextInt(4) == 3) {
            World w = location.getWorld();
            w.dropItemNaturally(location.add(0, 1, 0), makeSkull(victim));
        }
    }

    private ItemStack makeSkull(@Nonnull final Player p) {
        ItemStack stack = SkullBuilder.of(1, p.getName()).setHeadTexture(p).build();
        return stack;
    }

    public void respawn() {
        victim.setGameMode(GameMode.SPECTATOR);
        victim.playSound(victim.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
        victim.sendTitle(CC.RED + "" + CC.BOLD + "YOU DIED", CC.GOLD + "Respawning...", 5, 50, 5);
        new BukkitRunnable() {
            public void run() {
                victim.setGameMode(GameMode.SURVIVAL);
                victim.teleport(ConfigUtils.spawn);
            }
        }.runTaskLater(Tazpvp.getInstance(), 20*3);
    }

    public void heal() {
        PlayerFunctions.healPlr(victim);
        PlayerFunctions.feedPlr(victim);

        for (PotionEffect effect : victim.getActivePotionEffects()) {
            victim.removePotionEffect(effect.getType());
        }
    }

    public void addHealth(int amount) {
        if ((pKiller.getHealth() + 5) >= PlayerFunctions.getMaxHealth(pKiller)) {
            PlayerFunctions.healPlr(pKiller);
            pKiller.setHealth(PlayerFunctions.getMaxHealth(pKiller));
        } else {
            pKiller.setHealth(pKiller.getHealth() + amount);
        }
    }

    public void deathMessage(boolean pKill) {
        final String prefix = CC.GRAY + "[" + CC.DARK_RED + "☠" + CC.GRAY + "] " + CC.DARK_GRAY;

        for (Player op : Bukkit.getOnlinePlayers()) {
            if (pKill) {
                final String who = (op == killer) ? "You" : CC.GRAY + killer.getName();
                final String died = (op == victim) ? "you" : CC.GRAY + victim.getName();
                String msg = prefix + who + CC.DARK_GRAY + " killed " + died;

                op.sendMessage(msg);
            } else {
                final String who = (op == victim) ? "You" : CC.GRAY + victim.getName();
                String msg = prefix + who + CC.DARK_GRAY + " died.";

                op.sendMessage(msg);
            }
        }
    }

    public void rewards() {
        if (killer != victim) {
            final int xp = 15;
            final int coins = 26;

            pKiller.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(CC.AQUA + "EXP: " + xp + CC.GOLD + " Coins: " + coins));
            PersistentData.add(killerID, DataTypes.COINS, coins);
            PersistentData.add(killerID, DataTypes.XP, xp);

            CombatTag tag = Tazpvp.tags.get(victimID);

            for (UUID id : tag.getAttackers()) {
                if (id != killerID && id != null) {
                    Player assister = Bukkit.getPlayer(id);
                    final int AssistXP = 5;
                    final int AssistCoins = 5;

                    assister.sendMessage("You assisted in killing " + victim.getName() + "+" + AssistXP + " EXP, +" + AssistCoins + " Coins");
                    PersistentData.add(assister, DataTypes.COINS, AssistCoins);
                    PersistentData.add(assister, DataTypes.XP, AssistXP);
                }
            }
        }
    }
}
