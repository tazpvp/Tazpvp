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

package net.tazpvp.tazpvp.game.npc.characters.shop.gui.subgui.cosmetic;

import net.tazpvp.tazpvp.enums.CC;
import net.tazpvp.tazpvp.wrappers.PlayerWrapper;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.gui.Button;
import world.ntdi.nrcore.utils.gui.GUI;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

import java.util.List;
import java.util.function.BiConsumer;

public class MaterialSelection extends GUI {

    public MaterialSelection(Player p, List<ParticleSelectionContainer> particleSelectionContainers, BiConsumer<Player, ParticleSelectionContainer> action) {
        super("Particles", 4);

        this.fill(0, 4 * 9, ItemBuilder.of(Material.GRAY_STAINED_GLASS_PANE).name(" ").build());

        if (particleSelectionContainers.size() % 7 != 0) throw new ArrayIndexOutOfBoundsException("Materials not multiple of 7");

        int step = 10;

        for (ParticleSelectionContainer particleSelectionContainer : particleSelectionContainers) {
            addButton(Button.create(ItemBuilder.of(particleSelectionContainer.material()).name(particleSelectionContainer.name()).lore(CC.GRAY + particleSelectionContainer.lore()).build(), e -> {
                PlayerWrapper pw = PlayerWrapper.getPlayer(p);
                if (pw.getRank().getHierarchy() >= 1) {
                    p.sendMessage(CC.GREEN + "[Lorenzo]" + CC.RED + "You require a premium subscription for this feature.");
                    return;
                }
                action.accept((Player) e.getWhoClicked(), particleSelectionContainer);
                p.closeInventory();
            }), step);
            step++;

            if (step % 9 == 8) {
                step += 2;
            }
        }

        update();
        open(p);
    }
}
