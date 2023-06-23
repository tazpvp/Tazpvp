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

package net.tazpvp.tazpvp.guis.cosmetic;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.utils.Profanity;
import net.tazpvp.tazpvp.utils.data.PlayerRankData;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.*;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.gui.Button;
import world.ntdi.nrcore.utils.gui.GUI;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CosmeticMenu extends GUI {

    private final List<ParticleSelectionContainer> deathParticles = new ArrayList<>(Arrays.asList(
            new ParticleSelectionContainer(Material.REDSTONE, Particle.REDSTONE, CC.RED + "Redstone", "Forge from the stone of Asia."),
            new ParticleSelectionContainer(Material.WATER_BUCKET, Particle.DRIP_WATER, CC.BLUE + "Wet", "Someone needed to cool off."),
            new ParticleSelectionContainer(Material.TNT, Particle.EXPLOSION_NORMAL, CC.DARK_RED + "Creeper?", "Aww man."),
            new ParticleSelectionContainer(Material.FIREWORK_ROCKET, Particle.FIREWORKS_SPARK, CC.GREEN + "Rocket", "Go out with a bang!"),
            new ParticleSelectionContainer(Material.RED_CANDLE, Particle.HEART, CC.RED + "Heart", "Give a little love."),
            new ParticleSelectionContainer(Material.SCULK_SHRIEKER, Particle.SONIC_BOOM, CC.AQUA + "Sonic Boom", "Break the sound barrier."),
            new ParticleSelectionContainer(Material.BLAZE_POWDER, Particle.VILLAGER_ANGRY, CC.LIGHT_PURPLE + "Anger", "Someone woke up on the wrong side of the bed.")
    ));

    private final List<ParticleSelectionContainer> arrowParticles = new ArrayList<>(Arrays.asList(
            new ParticleSelectionContainer(Material.FIRE, Particle.FLAME, CC.YELLOW + "Flame", "Feel the burn."),
            new ParticleSelectionContainer(Material.DRAGON_BREATH, Particle.DRAGON_BREATH, CC.DARK_PURPLE + "Dragon Breath", "Made in China."),
            new ParticleSelectionContainer(Material.TOTEM_OF_UNDYING, Particle.TOTEM, CC.GREEN + "Totem", "Lucky charms?"),
            new ParticleSelectionContainer(Material.NOTE_BLOCK, Particle.NOTE, CC.AQUA + "Note", "The sound of arrows."),
            new ParticleSelectionContainer(Material.CAMPFIRE, Particle.SMOKE_NORMAL, "Smoke", "I.. thought those were banned.. on planes.."),
            new ParticleSelectionContainer(Material.TIPPED_ARROW, Particle.SPELL, CC.BLUE + "Magic", "Do you believe?"),
            new ParticleSelectionContainer(Material.ENCHANTING_TABLE, Particle.ENCHANTMENT_TABLE, CC.YELLOW + "Enchant", "Enchantingly ugly.")
    ));

    public CosmeticMenu(Player p) {
        super(Bukkit.createInventory(null, 3 * 9, CC.GREEN + "Cosmetics"));
        addItems(p);
        this.open(p);
    }

    private void addItems(Player p) {
        fill(0, 3*9, ItemBuilder.of(Material.GRAY_STAINED_GLASS_PANE).name("").build());

        addButton(Button.create(ItemBuilder.of(Material.BEETROOT).name(CC.RED + "Death Particles").build(), e -> {
            updateParticle(p, deathParticles, PlayerRankData.ParticleMaterial.DEATH);
        }), 10);

        addButton(Button.create(ItemBuilder.of(Material.ARROW).name(CC.BLUE + "Arrow Particles").build(), e -> {
            updateParticle(p, arrowParticles, PlayerRankData.ParticleMaterial.ARROW);
        }), 13);

        addButton(Button.create(ItemBuilder.of(Material.NAME_TAG).name(CC.GOLD + "Custom Prefix").build(), e -> {
            p.closeInventory();
            setCustomPrefix(p);
        }), 16);

        update();
    }

    private void updateParticle(Player p, List<ParticleSelectionContainer> particles, PlayerRankData.ParticleMaterial particleMaterial) {
        p.closeInventory();
        new MaterialSelectionGui(p, particles, ((player, particleSelectionContainer) -> PlayerRankData.setMaterial(player.getUniqueId(), particleMaterial, particleSelectionContainer.particle())));
    }

    private void setCustomPrefix(Player p) {
        new AnvilGUI.Builder()
                .onComplete((player, text) -> {
                    if (text.startsWith(">")) {
                        text = text.replaceFirst(">", "");
                    }

                    if (!(text.length() < 8)) {
                        p.sendMessage("too long fatass");
                        return AnvilGUI.Response.close();
                    }

                    if (Profanity.sayNoNo(p, text)) return AnvilGUI.Response.close();

                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                    PlayerWrapper.getPlayer(p).setCustomPrefix(text);
                    p.sendMessage(CC.GREEN + "Set custom prefix to: " + CC.YELLOW + text);

                    return AnvilGUI.Response.close();
                })
                .onClose(player -> {
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                })
                .text(">")
                .itemLeft(ItemBuilder.of(Material.NAME_TAG).name(ChatColor.GREEN + "Custom Prefix < 7").build())
                .title(ChatColor.YELLOW + "Custom Prefix < 7:")
                .plugin(Tazpvp.getInstance())
                .open(p);
    }

}
