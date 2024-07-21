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
import net.tazpvp.tazpvp.data.DataTypes;
import net.tazpvp.tazpvp.data.LooseData;
import net.tazpvp.tazpvp.data.PersistentData;
import net.tazpvp.tazpvp.game.booster.ActiveBoosterManager;
import net.tazpvp.tazpvp.game.booster.BoosterBonus;
import net.tazpvp.tazpvp.game.booster.BoosterTypes;
import net.tazpvp.tazpvp.game.guilds.GuildUtils;
import net.tazpvp.tazpvp.game.items.StaticItems;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.functions.CombatTagFunctions;
import net.tazpvp.tazpvp.utils.player.PlayerInventoryStorage;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import world.ntdi.nrcore.NRCore;
import world.ntdi.nrcore.utils.item.builders.EnchantmentBookBuilder;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;
import world.ntdi.nrcore.utils.item.builders.SkullBuilder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Death {

    private final UUID victim;
    private final UUID killer;
    private final Player pVictim;
    private Player pKiller;
    private final Location location;
    private final Random r = new Random();

    public Death(UUID victim, @Nullable UUID killer) {
        this.victim = victim;
        this.killer = killer;
        this.pVictim = Bukkit.getPlayer(victim);
        this.location = pVictim.getLocation();
        if (killer != null) {
            this.pKiller = Bukkit.getPlayer(killer);
            Tazpvp.getObservers().forEach(observer -> observer.death(pVictim, pKiller));
        }
    }

    public void dropItems() {
        if (killer == victim) return;

        if (GuildUtils.isInGuild(pVictim) && GuildUtils.isInGuild(pKiller)) {
            if (GuildUtils.getGuildPlayerIn(pVictim) == GuildUtils.getGuildPlayerIn(pKiller)) return;
        }

        int chance = r.nextInt(10);

        if (chance <= 4) {
            World world = location.getWorld();

            PlayerWrapper killerWrapper = PlayerWrapper.getPlayer(killer);
            if (killerWrapper.getTalentEntity().isNecromancer()) {
                world.dropItemNaturally(location.add(0, 2, 0), deathItem());
            }
            world.dropItemNaturally(location.add(0, 1, 0), deathItem());
        }

    }

    public void dropHead() {
        if (pVictim.getName().startsWith(".")) {
            return;
        }

        PlayerWrapper killerWrapper = PlayerWrapper.getPlayer(killer);
        if (killerWrapper.getTalentEntity().isHarvester()) {
            if (r.nextInt(0, 100) > 1) return;
        } else {
            if (r.nextInt(0, 200) > 1) return;
        }
        World w = location.getWorld();
        ItemStack skull = makeSkull(pVictim);
        if (skull == null || w == null) return;
        w.dropItemNaturally(location.add(0, 1, 0), skull);
    }

    private ItemStack makeSkull(@Nonnull final Player p) {
        return SkullBuilder.of(1, p.getName()).setHeadTexture(p).build();
    }

    public void playParticle() {
        PlayerWrapper playerWrapper = PlayerWrapper.getPlayer(victim);

        if (playerWrapper.getRank().getHierarchy() >= 1) {
            final String particle = playerWrapper.getUserRankEntity().getDeathParticle();

            if (particle != null) {
                location.getWorld().spawnParticle(Particle.valueOf(particle), location, 6);
            }
        }
    }

    public void respawn() {
        EulerAngleSpectating eulerAngleSpectating;
        Location camLoc = location;

        if (killer == null) {
            if (CombatTagFunctions.getLastAttacker(victim) != null) {
                Player assistKiller = Bukkit.getPlayer(CombatTagFunctions.getLastAttacker(victim));
                if (assistKiller != null) {
                    camLoc = assistKiller.getLocation();
                }
            }
        } else {
            camLoc = pKiller.getLocation();
        }

        eulerAngleSpectating = new EulerAngleSpectating(camLoc);
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
        CombatTag tag = CombatTag.tags.get(victim);
        if (!tag.getAttackers().isEmpty()) {
            for (UUID uuid : tag.getAttackers()) {
                if (uuid != killer && uuid != null) {
                    Player assister = Bukkit.getPlayer(uuid);
                    if (assister == null) continue;

                    final BoosterBonus assistBonus = ActiveBoosterManager.getInstance().calculateBonus(5, List.of(BoosterTypes.XP, BoosterTypes.MEGA));
                    final BoosterBonus coinBonus = ActiveBoosterManager.getInstance().calculateBonus(5, List.of(BoosterTypes.COINS, BoosterTypes.MEGA));

                    final int assistXP = (int) assistBonus.result();
                    final int assistCoins = (int) coinBonus.result();

                    assister.sendMessage(
                            CC.DARK_GRAY + "Assist kill:" + CC.GRAY + " (" + pVictim.getName() + ") " +
                                    CC.DARK_AQUA + "Exp: " + CC.AQUA +  assistXP + " " + CC.DARK_AQUA + assistBonus.prettyPercentMultiplier() +
                                    CC.GOLD + " Coins: " + CC.YELLOW +  assistCoins + " " + CC.GOLD + coinBonus.prettyPercentMultiplier()
                    );
                    PersistentData.add(assister, DataTypes.COINS, assistCoins);
                    PersistentData.add(assister, DataTypes.XP, assistXP);
                }
            }
        }
        if (killer != null) {
            if (killer == victim) return;

            if (GuildUtils.isInGuild(pVictim) && GuildUtils.isInGuild(pKiller)) {
                if (GuildUtils.getGuildPlayerIn(pVictim) == GuildUtils.getGuildPlayerIn(pKiller)) return;
            }

            final BoosterBonus xpBonus = ActiveBoosterManager.getInstance().calculateBonus(15, List.of(BoosterTypes.XP, BoosterTypes.MEGA));
            final BoosterBonus coinBonus = ActiveBoosterManager.getInstance().calculateBonus(26, List.of(BoosterTypes.COINS, BoosterTypes.MEGA));

            final int xp = (int) xpBonus.result();
            final int coins = (int) coinBonus.result();
            final int bounty = LooseData.getKs(victim) * 10;

            pKiller.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(
                    CC.DARK_AQUA + CC.BOLD.toString() +  "Exp: " + CC.AQUA + CC.BOLD + xp + " " + CC.DARK_AQUA + xpBonus.prettyPercentMultiplier() +
                            CC.GOLD + CC.BOLD + " Coins: " + CC.YELLOW + CC.BOLD + coins + " " + CC.GOLD + coinBonus.prettyPercentMultiplier()
            ));
            if (bounty > 0) {
                pKiller.sendMessage(CC.YELLOW + "You collected " + pVictim.getName() + "'s " + CC.GOLD + "$" + bounty + CC.YELLOW + " bounty.");
            }
            PersistentData.add(killer, DataTypes.COINS, coins + bounty);
            PersistentData.add(killer, DataTypes.XP, xp);
        }
    }

    public void storeInventory() {
        PlayerInventoryStorage.updateStorage(victim, killer);
    }

    public static ItemStack deathItem() {
        Random r = new Random();

        List<Enchantment> enchants = List.of(
                Enchantment.SHARPNESS,
                Enchantment.POWER,
                Enchantment.PROTECTION,
                Enchantment.MENDING,
                Enchantment.FLAME,
                Enchantment.FIRE_ASPECT,
                Enchantment.UNBREAKING
        );

        List<ItemStack> items = Arrays.asList(
                ItemBuilder.of(Material.AMETHYST_SHARD, 2).name(StaticItems.SHARD.getName()).build(),
                new ItemStack(Material.GOLDEN_APPLE, 2),
                new ItemStack(Material.COOKED_BEEF, 10)
        );

        int randomItemChance = r.nextInt(10);

        if (randomItemChance < 7) {
            Enchantment enchant = enchants.get(r.nextInt(enchants.size()));
            return new EnchantmentBookBuilder().enchantment(enchant, 1).build();
        } else {
            return items.get(r.nextInt(items.size()));
        }
    }
}