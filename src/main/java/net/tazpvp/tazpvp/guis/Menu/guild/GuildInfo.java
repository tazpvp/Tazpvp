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

import net.tazpvp.tazpvp.game.guilds.Guild;
import net.tazpvp.tazpvp.utils.enums.CC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.gui.Button;
import world.ntdi.nrcore.utils.gui.GUI;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;
import world.ntdi.nrcore.utils.item.builders.SkullBuilder;

public class GuildInfo extends GUI {

    public GuildInfo(Player p, Guild g) {
        super("Guild Info", 3);
        addItems(p, g);
        open(p);
    }

    private void addItems(Player p, Guild g) {
        String[] lore = {
                " ",
                CC.DARK_GREEN + "Kills: " + CC.GREEN + g.getKills(),
                CC.DARK_GREEN + "Deaths: " + CC.GREEN + g.getDeaths(),
                CC.DARK_GREEN + "KDR: " + CC.GREEN + g.getKDR(),
                " ",
                CC.GOLD + "Click to edit"
        };

        OfflinePlayer leader = Bukkit.getOfflinePlayer(g.getGuildLeader());

        fill(0, 3*9, ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE).name(" ").build());

        addButton(Button.create(ItemBuilder.of(Material.ENCHANTING_TABLE, 1)
                .name(CC.GREEN + g.getName()).lore(lore).build(), (e) -> {
            new GuildEdit(p, g);
        }), 11);

        addButton(Button.createBasic(SkullBuilder.of().setHeadTexture(g.getGuildLeader())
                .name(CC.GREEN + "Guild Master").lore(" ", CC.DARK_GREEN + leader.getName()).build()), 13);

        addButton(Button.create(ItemBuilder.of(Material.WRITABLE_BOOK, 1)
                .name(CC.GREEN + "Members").lore(" ", CC.DARK_GREEN + "View guild members.").build(), (e) -> {
            new GuildMembers(p, g);
        }), 15);

        update();
    }
}
