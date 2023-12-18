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

package net.tazpvp.tazpvp.guis.Menu.guild;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.game.guilds.Guild;
import net.tazpvp.tazpvp.game.guilds.GuildUtils;
import net.tazpvp.tazpvp.data.DataTypes;
import net.tazpvp.tazpvp.data.GuildData;
import net.tazpvp.tazpvp.data.PersistentData;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import world.ntdi.nrcore.utils.gui.Button;
import world.ntdi.nrcore.utils.gui.GUI;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

import java.util.*;

public class GuildBrowser extends GUI {
    private int pagesNeeded;
    private int numNum;
    public GuildBrowser(Player p) {
        super("Guild Browser", 6);
        this.pagesNeeded = (int) Math.ceil(GuildUtils.getSortedGuilds().size() / (double) (4 * 7));
        listGuilds(p);
        open(p);
    }

    public void listGuilds(Player p) {
        defaults(p);

        int index = 19;
        LinkedHashMap<UUID, Integer> guilds = GuildUtils.getSortedGuilds();

        for (Map.Entry<UUID, Integer> guild : guilds.entrySet()) {
            Guild g = GuildData.getGuild(guild.getKey());
            if (!g.isShow_in_browser()) {
                continue;
            }

            Button guildView = Button.create(ItemBuilder.of(g.getIcon()).name(CC.WHITE + g.getName()).lore(
                    "",
                    CC.WHITE + g.getDescription(),
                    ChatColor.GRAY + "-" + Bukkit.getOfflinePlayer(g.getGuildLeader()).getName(),
                    "",
                    CC.GRAY + "Members: " + CC.WHITE + g.getAllMembers().length,
                    CC.GRAY + "Kills: " + CC.WHITE + g.getKills()
            ).build(), (e) -> {});

            addButton(guildView, index);

            if (index == 34 || index == 25) {
                index += 2;
            }
            index++;
        }
        update();
    }

    private void defaults(Player p) {
        clear();
        fill(0, 6 * 9, ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE).name(" ").build());

        Button playerGuild;
        if (GuildUtils.isInGuild(p)) {
            Guild g = GuildUtils.getGuildPlayerIn(p);
            playerGuild = Button.create(ItemBuilder.of(g.getIcon())
                    .name(CC.GREEN + "" + CC.BOLD + g.getName())
                    .lore(CC.DARK_GREEN + "Click to view and", CC.DARK_GREEN + "edit your guild.")
                    .glow(true)
            .build(), (e) -> {
                p.closeInventory();
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        new GuildInfo(p, g);
                    }
                }.runTaskLater(Tazpvp.getInstance(), 2L);
            });
        } else {
            playerGuild = Button.create(ItemBuilder.of(Material.MINECART)
                    .name(CC.GREEN + "" + CC.BOLD + "Purchase Guild")
                    .lore(CC.DARK_GREEN + "Click to buy a guild.", " ", CC.GRAY + "Cost: $6,000")
                    .glow(true)
            .build(), (e) -> {
                p.closeInventory();
                if (PersistentData.getInt(p, DataTypes.COINS) >= 6000) {
                    nameGuild(p);
                } else {
                    p.sendMessage("You don't have enough money");
                }
            });
        }
        addButton(playerGuild, 13);

        update();
    }

    private static void nameGuild(Player p) {
        new AnvilGUI.Builder()
            .onClick((slot, stateSnapshot) -> {
                if (slot != AnvilGUI.Slot.OUTPUT) {
                    return Collections.emptyList();
                }

                String text = stateSnapshot.getText();

                if (text.startsWith(">")) {
                    text = text.replaceFirst(">", "").replaceAll(" ", "");
                }
                PersistentData.remove(p, DataTypes.COINS, 6000);
                p.playSound(p.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_PLACE, 1, 1);

                createGuild(text, p.getUniqueId());
                p.sendMessage("You created a guild! " + GuildUtils.getGuildPlayerIn(p).getName());
                return Arrays.asList(AnvilGUI.ResponseAction.close());
            })
            .text(">")
            .itemLeft(ItemBuilder.of(Material.NAME_TAG).build())
            .title("Name your guild")
            .plugin(Tazpvp.getInstance())
            .open(p);

    }

    public static void createGuild(String text, UUID id) {
        new Guild(text, id);
    }

}
