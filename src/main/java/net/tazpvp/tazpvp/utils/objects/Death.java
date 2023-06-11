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
import lombok.Setter;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.guild.GuildUtils;
import net.tazpvp.tazpvp.utils.data.DataTypes;
import net.tazpvp.tazpvp.utils.data.LooseData;
import net.tazpvp.tazpvp.utils.data.PersistentData;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.functions.DeathFunctions;
import net.tazpvp.tazpvp.utils.functions.PlayerFunctions;
import net.tazpvp.tazpvp.utils.player.PlayerInventoryStorage;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;
import world.ntdi.nrcore.NRCore;
import world.ntdi.nrcore.utils.gui.Button;
import world.ntdi.nrcore.utils.gui.GUI;
import world.ntdi.nrcore.utils.holograms.Hologram;
import world.ntdi.nrcore.utils.item.builders.EnchantmentBookBuilder;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;
import world.ntdi.nrcore.utils.item.builders.PotionBuilder;
import world.ntdi.nrcore.utils.item.builders.SkullBuilder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Death {

    @Getter @Setter
    private final UUID victim;
    @Getter @Setter
    private final UUID killer;
    private Player pVictim;
    private Player pKiller;
    private final Location location;
    private final Random r = new Random();

    public Death(UUID victim, @Nullable UUID killer) {
        this.victim = victim;
        this.killer = killer;
        this.pVictim = Bukkit.getPlayer(victim);
        this.pKiller = Bukkit.getPlayer(killer);
        this.location = pVictim.getLocation();
        Tazpvp.getObservers().forEach(observer -> observer.death(pVictim, pKiller));
    }

    public void coffin() {
        if (killer == victim) return;

        if (GuildUtils.isInGuild(pVictim) && GuildUtils.isInGuild(pKiller)) {
            if (GuildUtils.getGuildPlayerIn(pVictim) == GuildUtils.getGuildPlayerIn(pKiller)) return;
        }

        int chance = new Random().nextInt(10);

        if (PersistentData.getTalents(killer).is("Necromancer")) {
            if (!(chance <= 2)) return;
        } else if (chance != 1) return;

        Material chest = new ItemStack(Material.CHEST).getType();

        Block block = location.getBlock();
        block.setType(chest);

        Chest coffin = (Chest) block.getState();
        Inventory inv = coffin.getInventory();
        GUI gui = new GUI(inv);

        ItemStack item = coffinItem();

        Hologram hologram = new Hologram(new String[]{"&3&l" + pVictim.getName() + "'s Coffin"}, location.getBlock().getLocation().add(0.5, 0, 0.5).subtract(0, 0.5, 0), false);

        gui.addButton(Button.create(item, (e) -> {
            new BukkitRunnable() {
                @Override
                public void run() {
                    gui.setReturnsItems(true);
                    e.getWhoClicked().closeInventory();
                    e.getWhoClicked().getInventory().addItem(item);
                    gui.destroy();
                    block.setType(Material.AIR);
                    hologram.deleteHologram();
                    if (e.getWhoClicked() instanceof Player p) {
                        p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);

                        Location playerLocation = p.getLocation();
                        playerLocation.getWorld().spawnParticle(Particle.SPELL_MOB, playerLocation, 100, 0.5, 1, 0.5, 0.2);

                        p.playSound(p.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1, 1);
                    }
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

    /**
     * List of all possible enchantments that appear in the coffin.
     * @return Returns a random enchantment out of the list.
     */
    private ItemStack coffinItem() {
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

    public void dropHead() {
        if (PersistentData.getTalents(killer).is("Harvester")) {
            if (new Random().nextInt(4) != 1) return;
        } else {
            if (new Random().nextInt(6) != 1) return;
        }
        World w = location.getWorld();
        w.dropItemNaturally(location.add(0, 1, 0), makeSkull(pVictim));

    }

    private ItemStack makeSkull(@Nonnull final Player p) {
        ItemStack stack = SkullBuilder.of(1, p.getName()).setHeadTexture(p).build();
        return stack;
    }

    public void respawn() {
        EulerAngleSpectating eulerAngleSpectating = new EulerAngleSpectating(location);
        pVictim.teleport(eulerAngleSpectating.getResult());
        eulerAngleSpectating.faceLocation(pVictim);
        pVictim.setGameMode(GameMode.SPECTATOR);
        pVictim.playSound(pVictim.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
        pVictim.sendTitle(CC.RED + "" + CC.BOLD + "YOU DIED", CC.GOLD + "Respawning...", 5, 50, 5);
        PlayerWrapper vp = PlayerWrapper.getPlayer(victim);
        vp.setRespawning(true);
        new BukkitRunnable() {
            public void run() {
                pVictim.setGameMode(GameMode.SURVIVAL);
                pVictim.teleport(NRCore.config.spawn);
                vp.setRespawning(false);
            }
        }.runTaskLater(Tazpvp.getInstance(), 20*3);
    }

    public void deathMessage() {
        final String prefix = CC.GRAY + "[" + CC.DARK_RED + "☠" + CC.GRAY + "] " + CC.DARK_GRAY;

        for (Player op : Bukkit.getOnlinePlayers()) {
            if (victim == killer || killer == null) {
                final String who = (op == pVictim) ? "You" : CC.GRAY + pVictim.getName();
                String msg = prefix + who + CC.DARK_GRAY + " died.";

                op.sendMessage(msg);
            } else {
                final String who = (op == pKiller) ? "You" : CC.GRAY + pKiller.getName();
                final String died = (op == pVictim) ? "you" : CC.GRAY + pVictim.getName();
                String msg = prefix + who + CC.DARK_GRAY + " killed " + died;

                op.sendMessage(msg);
            }
        }
    }

    public void rewards() {
        if (killer == victim) return;

        if (GuildUtils.isInGuild(pVictim) && GuildUtils.isInGuild(pKiller)) {
            if (GuildUtils.getGuildPlayerIn(pVictim) == GuildUtils.getGuildPlayerIn(pKiller)) return;
        }

        final int xp = 15;
        final int coins = 26;
        final int bounty = LooseData.getKs(victim) * 10;

        pKiller.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(CC.DARK_AQUA + "" + CC.BOLD +  "EXP: " + CC.AQUA + "" + CC.BOLD +  xp + CC.GOLD + "" + CC.BOLD + " COINS: " + CC.YELLOW + "" + CC.BOLD +  coins));
        if (bounty > 0) {
            pKiller.sendMessage(CC.YELLOW + "You collected " + pVictim.getName() + "'s " + CC.GOLD + "$" + bounty + CC.YELLOW + " bounty.");
        }
        PersistentData.add(killer, DataTypes.COINS, coins + bounty);
        PersistentData.add(killer, DataTypes.XP, xp);

        CombatTag tag = CombatTag.tags.get(victim);

        for (UUID id : tag.getAttackers()) {
            if (id != killer && id != null) {
                Player assister = Bukkit.getPlayer(id);
                final int AssistXP = 5;
                final int AssistCoins = 5;

                assister.sendMessage(CC.DARK_GRAY + "Assist kill: " + CC.GRAY + pVictim.getName() + ": " + CC.DARK_AQUA +  "EXP: " + CC.AQUA +  AssistXP + CC.GOLD + " COINS: " + CC.YELLOW +  AssistCoins);
                PersistentData.add(assister, DataTypes.COINS, AssistCoins);
                PersistentData.add(assister, DataTypes.XP, AssistXP);
            }
        }
    }

    public void storeInventory() {
        PlayerInventoryStorage.updateStorage(victim, killer);
    }
}