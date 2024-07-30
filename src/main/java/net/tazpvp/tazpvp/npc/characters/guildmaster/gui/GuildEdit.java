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
 */

package net.tazpvp.tazpvp.npc.characters.guildmaster.gui;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.data.entity.GuildEntity;
import net.tazpvp.tazpvp.data.entity.PlayerStatEntity;
import net.tazpvp.tazpvp.data.services.GuildService;
import net.tazpvp.tazpvp.data.services.PlayerStatService;
import net.tazpvp.tazpvp.utils.Profanity;
import net.tazpvp.tazpvp.enums.CC;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import world.ntdi.nrcore.utils.gui.Button;
import world.ntdi.nrcore.utils.gui.GUI;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

import java.util.Collections;
import java.util.List;

public class GuildEdit extends GUI {

    private final String NO_RANK = CC.RED + "You do not have premium, visit the store to purchase it.";
    private final String REQ_RANK = CC.DARK_PURPLE + "Requires rank to edit.";

    private final GuildService guildService;
    private final GuildEntity playerGuildEntity;
    private final PlayerStatService playerStatService;
    private final PlayerStatEntity playerStatEntity;

    public GuildEdit(Player p, GuildService guildService, PlayerStatService playerStatService) {
        super("Guild Edit", 3);
        this.guildService = guildService;
        this.playerGuildEntity = guildService.getGuildByPlayer(p.getUniqueId());
        this.playerStatService = playerStatService;
        this.playerStatEntity = playerStatService.getOrDefault(p.getUniqueId());
        addItems(p);
        open(p);
    }

    private void addItems(Player p) {
        fill(0, 3 * 9, ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE).name(" ").build());

        Button guildIcon = Button.create(ItemBuilder.of(Material.getMaterial(playerGuildEntity.getIcon()))
                .name(CC.GREEN + "Edit Guild Icon")
                .lore(REQ_RANK)
                .build(), _ ->
        {
            if (PlayerWrapper.getPlayer(p.getUniqueId()).getRank().getHierarchy() >= 1) {
                new Icon(p, guildService, playerGuildEntity);
            } else {
                p.sendMessage(NO_RANK);
            }
        });

        Button guildDescription = Button.create(ItemBuilder.of(Material.WRITABLE_BOOK)
                .name(CC.GREEN + "Edit Description")
                .lore(CC.YELLOW + "Costs " + CC.GOLD + "6,000 " + CC.YELLOW + "coins.")
                .build(), _ ->
        {
            if (playerStatEntity.getCoins() > 6000) {
                setDescription(p, playerGuildEntity);
            } else {
                p.sendMessage(NO_RANK);
            }
        });

        Button guildTag = Button.create(ItemBuilder.of(Material.NAME_TAG)
                .name(CC.GREEN + "Edit Tag")
                .lore(REQ_RANK).build(), _ ->
        {
            if (PlayerWrapper.getPlayer(p.getUniqueId()).getRank().getHierarchy() >= 1) {
                setTag(p, playerGuildEntity);
            } else {
                p.sendMessage(NO_RANK);
            }
        });

        final String showinbrowserText = (playerGuildEntity.isPrivate() ? "Enabled" : "Disabled");
        Button setShowInBrowser = Button.create(ItemBuilder.of(Material.BELL)
                .name(CC.GREEN + "Show in browser").lore(showinbrowserText).build(), _ ->
        {
            playerGuildEntity.setPrivate(!playerGuildEntity.isPrivate());
            p.closeInventory();
            p.sendMessage(CC.RED + "Showing guild in browser is now: " + CC.GOLD + (playerGuildEntity.isPrivate() ? "Enabled" : "Disabled"));
            new GuildEdit(p, guildService, playerStatService);
        });

        addButton(Button.create(ItemBuilder.of(Material.WRITABLE_BOOK, 1)
                .name(CC.GREEN + "Members").lore(" ", CC.DARK_GREEN + "View guild members.").build(), (_) -> new GuildMembers(p, guildService)), 15);
        addButton(guildIcon, 10);
        addButton(guildDescription, 12);
        addButton(guildTag, 14);
        addButton(setShowInBrowser, 16);

        update();
    }

    public static class Icon extends GUI {

        private final GuildService guildService2;
        private final GuildEntity playerGuildEntity2;

        private final List<Material> icons = List.of(
                Material.OAK_SIGN,
                Material.RAW_GOLD,
                Material.CHORUS_FLOWER,
                Material.ENCHANTING_TABLE,
                Material.BEACON,
                Material.END_PORTAL_FRAME,
                Material.TURTLE_EGG,
                Material.WITHER_ROSE,
                Material.DIRT,
                Material.TARGET,
                Material.DIAMOND,
                Material.EMERALD,
                Material.LAPIS_LAZULI,
                Material.AMETHYST_SHARD,
                Material.GOLDEN_APPLE,
                Material.NETHERITE_CHESTPLATE,
                Material.CARROT_ON_A_STICK,
                Material.LEATHER,
                Material.LAVA_BUCKET,
                Material.ENDER_PEARL,
                Material.ENDER_EYE,
                Material.SLIME_BALL,
                Material.GLOW_INK_SAC,
                Material.PUFFERFISH,
                Material.MAGMA_CREAM,
                Material.BLAZE_POWDER,
                Material.FIRE_CHARGE,
                Material.DIAMOND_HORSE_ARMOR
        );
        public Icon(Player p, GuildService guildService2, GuildEntity playerGuildEntity2) {
            super("Guild Icon Picker", 6);
            this.guildService2 = guildService2;
            this.playerGuildEntity2 = playerGuildEntity2;
            addItems(p, playerGuildEntity2);
            p.closeInventory();
            open(p);
        }

        private void addItems(Player p, GuildEntity g) {
            fill(0, 6 * 9, ItemBuilder.of(Material.GRAY_STAINED_GLASS_PANE).name(" ").build());

            int index = 10;
            for (Material m : icons) {
                Button iconBTN = Button.create(ItemBuilder.of(m).name(ChatColor.YELLOW + m.name()).lore(ChatColor.GREEN + "Click to set icon", ChatColor.DARK_AQUA + "Cost: " + ChatColor.AQUA + "50 credits").build(), (_) -> {
                    g.setIcon(m.toString());
                    p.closeInventory();
                    guildService2.messageAll(playerGuildEntity2, ChatColor.YELLOW + p.getName() + ChatColor.GREEN + " has set the guild icon to " + ChatColor.YELLOW + m.name());
                    p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            new GuildEdit(p, Tazpvp.getInstance().getGuildService(), Tazpvp.getInstance().getPlayerStatService());
                        }
                    }.runTaskLater(Tazpvp.getInstance(), 1);
                });

                addButton(iconBTN, index);

                if (index == 16 || index == 25 || index == 34 || index == 43) {
                    index += 2;
                }
                index++;
            }
            update();
        }
    }

    private void setDescription(Player p, GuildEntity g) {
        new AnvilGUI.Builder()
                .onClick((slot, stateSnapshot) -> {
                    if(slot != AnvilGUI.Slot.OUTPUT) {
                        return Collections.emptyList();
                    }

                    String text = stateSnapshot.getText();
                    final Player player = stateSnapshot.getPlayer();

                    if (text.startsWith(">")) {
                        text = text.replaceFirst(">", "");
                    }

                    if (Profanity.sayNoNo(p, text)) return List.of(AnvilGUI.ResponseAction.close());
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);

                    playerStatEntity.setCoins(playerStatEntity.getCoins() - 6000);
                    g.setDescription(text);

                    return List.of(AnvilGUI.ResponseAction.close());
                })
                .onClose(stateSnapshot -> {
                    stateSnapshot.getPlayer().playSound(stateSnapshot.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                })
                .text(">")
                .itemLeft(ItemBuilder.of(Material.NAME_TAG).name(ChatColor.GREEN + "Guild Description").build())
                .title(ChatColor.YELLOW + "Guild Description:")
                .plugin(Tazpvp.getInstance())
                .open(p);
    }

    private void setTag(Player p, GuildEntity g) {
        new AnvilGUI.Builder()
            .onClick((slot, stateSnapshot) -> {
                if(slot != AnvilGUI.Slot.OUTPUT) {
                    return Collections.emptyList();
                }

                String text = stateSnapshot.getText();
                final Player player = stateSnapshot.getPlayer();

                if (text.startsWith(">")) {
                    text = text.replaceFirst(">", "");
                }

                if (!(text.length() < 8)) {
                    p.sendMessage("too long fatass");
                    return List.of(AnvilGUI.ResponseAction.close());
                }

                if (Profanity.sayNoNo(p, text)) return List.of(AnvilGUI.ResponseAction.close());

                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);

                g.setTag(text);

                return List.of(AnvilGUI.ResponseAction.close());
            })

            .onClose(stateSnapshot -> {
                stateSnapshot.getPlayer().playSound(stateSnapshot.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
            })
            .text(">")
            .itemLeft(ItemBuilder.of(Material.NAME_TAG).name(ChatColor.GREEN + "Guild Tag < 8").build())
            .title(ChatColor.YELLOW + "Guild Tag < 8:")
            .plugin(Tazpvp.getInstance())
            .open(p);
    }
}
