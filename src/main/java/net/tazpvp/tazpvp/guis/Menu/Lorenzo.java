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

package net.tazpvp.tazpvp.guis.Menu;

import net.tazpvp.tazpvp.guis.Menu.cosmetic.PremiumMenu;
import net.tazpvp.tazpvp.guis.Menu.guild.GuildBrowser;
import net.tazpvp.tazpvp.utils.enums.CC;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.gui.Button;
import world.ntdi.nrcore.utils.gui.GUI;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

public class Lorenzo extends GUI {

    public Lorenzo(Player p) {
        super("Lorenzo", 3);
        addItems(p);
        open(p);
    }

    private void addItems(Player p) {
        fill(0, 3*9, ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE, 1).name(" ").build());

        addButton(Button.create(ItemBuilder.of(Material.BOOK, 1).name(CC.GREEN + "" + CC.BOLD + "Achievements").lore(CC.GRAY + "Complete these for rewards").build(), (e) -> {
            new Achievements(p);
        }), 10);

        addButton(Button.create(ItemBuilder.of(Material.LECTERN, 1).name(CC.GREEN + "" + CC.BOLD + "Talents").lore(CC.GRAY + "PvP and PvE perks" + CC.GRAY + "for the arena").build(), (e) -> {
            new Talents(p);
        }), 12);

        addButton(Button.create(ItemBuilder.of(Material.TOTEM_OF_UNDYING, 1).name(CC.GREEN + "" + CC.BOLD + "Guilds").lore(CC.GRAY + "Create guilds and", CC.GRAY + "compete with rivals").build(), (e) -> {
            p.sendMessage(CC.RED + "Currently out of order.");
//            new GuildBrowser(p);
        }), 14);

        addButton(Button.create(ItemBuilder.of(Material.FIRE_CHARGE, 1).name(CC.GREEN + "" + CC.BOLD + "Premium").lore(CC.GRAY + "All of the premium", CC.GRAY + "and cosmetic features").build(), (e) -> {
            new PremiumMenu(p);
        }), 16);

        update();
    }
}
