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

package net.tazpvp.tazpvp.game.npc.characters.guildmaster.gui;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.data.entity.GuildEntity;
import net.tazpvp.tazpvp.data.services.GuildService;
import net.tazpvp.tazpvp.enums.CC;
import net.tazpvp.tazpvp.enums.StatEnum;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import world.ntdi.nrcore.utils.gui.Button;
import world.ntdi.nrcore.utils.gui.GUI;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class GuildMenu extends GUI {

    private final GuildEntity guildEntity;
    private final GuildService guildService;
    private static final int ROWS = 3;

    public GuildMenu(Player p, GuildService guildService) {
        super("Guild Browser", ROWS);
        this.guildService = guildService;
        this.guildEntity = guildService.getGuildByPlayer(p.getUniqueId());
        addItems(p);
        open(p);
    }

    private void addItems(Player p) {
        clear();
        fill(0, ROWS * 9, ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE).name(" ").build());

        Button playerGuild;

        if (guildService.getGuildByPlayer(p.getUniqueId()) != null) {
            String[] lore = {
                    " ",
                    CC.DARK_GREEN + "Kills: " + CC.GREEN + guildEntity.getKills(),
                    CC.DARK_GREEN + "Deaths: " + CC.GREEN + guildEntity.getDeaths(),
                    CC.DARK_GREEN + "KDR: " + CC.GREEN + (guildEntity.getKills() / guildEntity.getDeaths()),
                    " ",
                    CC.GOLD + "Click to edit guild."
            };

            Material guildIcon = Material.getMaterial(guildEntity.getIcon());
            playerGuild = Button.create(ItemBuilder.of(guildIcon == null ? Material.OAK_SIGN : guildIcon)
                    .name(CC.GREEN + "" + CC.BOLD + guildEntity.getName()).lore(lore).glow(true).build(), (_) ->
            {
                p.closeInventory();
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        new GuildEdit(p, guildService);
                    }
                }.runTaskLater(Tazpvp.getInstance(), 2L);
            });
        } else {
            playerGuild = Button.create(ItemBuilder.of(Material.MINECART)
                    .name(CC.GREEN + "" + CC.BOLD + "Create Guild")
                    .lore(CC.DARK_GREEN + "Click to buy a guild.", " ", CC.GRAY + "Cost: $6,000")
                    .glow(true)
            .build(), (_) -> {
                p.closeInventory();
                if (StatEnum.COINS.getInt(p.getUniqueId()) >= 6000) {
                    nameGuild(p);
                } else {
                    p.sendMessage("You don't have enough money");
                }
            });
        }

        Button guildList = Button.create(ItemBuilder.of(Material.LECTERN)
                .name(CC.GREEN + "" + CC.BOLD + "View Guilds")
                .lore(CC.DARK_GREEN + "A list of all the top guilds.")
                .glow(true)
                .build(), (_) -> {
            p.closeInventory();
            new GuildList(p, guildService);
        });

        addButton(guildList, 12);
        addButton(playerGuild, 14);

        update();
    }

    private void nameGuild(Player p) {
        new AnvilGUI.Builder()
            .onClick((slot, stateSnapshot) -> {
                if (slot != AnvilGUI.Slot.OUTPUT) {
                    return Collections.emptyList();
                }

                String text = stateSnapshot.getText();

                if (text.startsWith(">")) {
                    text = text.replaceFirst(">", "").replaceAll(" ", "");
                }
                StatEnum.COINS.remove(p.getUniqueId(), 6000);
                p.playSound(p.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_PLACE, 1, 1);

                createGuild(text, p.getUniqueId());
                p.sendMessage("You created a guild! " + guildEntity.getName());

                guildService.saveGuild(guildEntity);
                return List.of(AnvilGUI.ResponseAction.close());
            })
            .text(">")
            .itemLeft(ItemBuilder.of(Material.NAME_TAG).build())
            .title("Name your guild")
            .plugin(Tazpvp.getInstance())
            .open(p);

    }

    private void createGuild(String name, UUID id) {
        guildService.createGuild(name, id);
    }
}
