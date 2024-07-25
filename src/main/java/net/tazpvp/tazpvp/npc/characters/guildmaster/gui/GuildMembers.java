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

import net.tazpvp.tazpvp.data.entity.GuildEntity;
import net.tazpvp.tazpvp.data.services.GuildService;
import net.tazpvp.tazpvp.utils.enums.CC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import world.ntdi.nrcore.utils.gui.Button;
import world.ntdi.nrcore.utils.gui.GUI;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;
import world.ntdi.nrcore.utils.item.builders.SkullBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GuildMembers extends GUI {

    private final GuildService guildService;

    public GuildMembers(Player p, GuildService guildService) {
        super("Guild Members", 4);
        this.guildService = guildService;
        addItems(p, guildService.getGuildByPlayer(p.getUniqueId()));
        open(p);
    }

    private void addItems(Player p, GuildEntity g) {
        fill(0, 4*9, ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE).name(" ").build());

        Button owner = Button.createBasic(SkullBuilder.of()
                .setHeadTexture(g.getOwner())
                .name(Bukkit.getOfflinePlayer(g.getOwner()).getName())
                .build());

        addButton(owner, 4);

        List<OfflinePlayer> members = new ArrayList<>();
        for (UUID uuid : guildService.getAllMembers(g)) {
            if (uuid.equals(p.getUniqueId())) continue;
            members.add(Bukkit.getOfflinePlayer(uuid));
        }
        allOthers(members, g, p);

        update();
    }

    private void allOthers(List<OfflinePlayer> members, GuildEntity g, Player viewer) {
        int index = 10;
        for (OfflinePlayer p : members) {
            ChatColor nameColor = guildService.getOfficers(g).contains(p.getUniqueId()) ? ChatColor.GREEN : ChatColor.GRAY;
            //rank based colors

            String lore = guildService.getMemberEntity(g, viewer.getUniqueId()).isOfficer() ? ChatColor.RED + "Click me to edit!" : "";
            String rank;

            if (viewer.getUniqueId()== g.getOwner()) {
                rank = "Leader";
            } else if (guildService.getMemberEntity(g, viewer.getUniqueId()).isOfficer()) {
                rank = "Officer";
            } else {
                rank = "Member";
            }
            ItemStack plrItem = SkullBuilder.of().setHeadTexture(p).name(nameColor + p.getName()).lore(nameColor + rank, "", lore).build();
            Button item = Button.createBasic(plrItem);

            addButton(item, index);

            if (index == 16 || index == 25) {
                index += 2;
            }
            index++;
        }

        if (members.isEmpty()) {
            addButton(
                Button.createBasic(ItemBuilder.of(Material.OAK_SIGN)
                        .name(CC.DARK_GREEN + "Guild Empty")
                        .lore(CC.GRAY + "Invite players with" + CC.GREEN + " /guild invite")
                        .build()),
                22);
        }

        update();
    }
}
