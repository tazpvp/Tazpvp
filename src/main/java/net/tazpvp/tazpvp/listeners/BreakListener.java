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

package net.tazpvp.tazpvp.listeners;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.data.LooseData;
import net.tazpvp.tazpvp.helpers.BlockHelper;
import net.tazpvp.tazpvp.objects.OreObject;
import net.tazpvp.tazpvp.objects.PickaxeObject;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BreakListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Block eventBlock = e.getBlock();
        Material blockMaterial = eventBlock.getType();

        if (!p.getGameMode().equals(GameMode.CREATIVE)) {
            for (OreObject ore : BlockHelper.ores) {
                if (blockMaterial.equals(ore.mat())) {
                    e.setCancelled(true);
                    Material tool = BlockHelper.getPickaxe(p).getType();
                    for (PickaxeObject pickaxe : BlockHelper.pickaxes) {
                        if (tool.equals(pickaxe.item().getType())) {
                            if (pickaxe.level() >= ore.level()) {
                                BlockHelper.respawnOre(p, eventBlock, ore);
                                LooseData.setMineCount(p.getUniqueId(), LooseData.getChatCount(p.getUniqueId()) + 1);
                            } else {
                                p.sendMessage("You require at least a " + ore.pickaxe() + " pickaxe to mine this ore.");
                                p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                            }
                        }
                    }
                } else if (!eventBlock.hasMetadata("PlayerPlaced")) {
                    e.setCancelled(true);
                }
            }
        }

        Tazpvp.getObservers().forEach(observer -> observer.mine(p, blockMaterial));
    }
}
