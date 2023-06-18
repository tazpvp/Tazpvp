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
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.gui.Button;
import world.ntdi.nrcore.utils.gui.GUI;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

public class CosmeticMenu extends GUI {

    public CosmeticMenu(Player p) {
        super(Bukkit.createInventory(null, 3 * 9, CC.GREEN + "Cosmetics"));
        addItems(p);
        this.open(p);
    }

    private void addItems(Player p) {
        fill(0, 3*9, ItemBuilder.of(Material.GRAY_STAINED_GLASS_PANE).name("").build());

        addButton(Button.create(ItemBuilder.of(Material.BEETROOT).name(CC.RED + "Death Particles").build(), e -> {
            // death particles goowy
        }), 10);

        addButton(Button.create(ItemBuilder.of(Material.ARROW).name(CC.BLUE + "Arrow Particles").build(), e -> {
            // Arrow Particle goowy
        }), 13);

        addButton(Button.create(ItemBuilder.of(Material.NAME_TAG).name(CC.GOLD + "Custom Prefix").build(), e -> {
            p.closeInventory();
            setCustomPrefix(p);
        }), 16);

        update();
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
