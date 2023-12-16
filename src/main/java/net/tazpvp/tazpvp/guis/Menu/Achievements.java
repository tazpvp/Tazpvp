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

import net.tazpvp.tazpvp.utils.data.PersistentData;
import net.tazpvp.tazpvp.utils.data.entity.AchievementEntity;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.gui.Button;
import world.ntdi.nrcore.utils.gui.GUI;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

public class Achievements extends GUI {

    public Achievements(Player p) {
        super("Achievements", 4);
        addItems(p);
        open(p);
    }

    private void addItems(Player p) {
        final PlayerWrapper playerWrapper = PlayerWrapper.getPlayer(p);
        AchievementEntity achievementEntity = playerWrapper.getAchievementEntity();

        fill(0, 4*9, ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE, 1).name(" ").build());

        setButton(p,  10, "Adept", "Learn every talent.", achievementEntity.isAdept());
        setButton(p,  11, "Bowling", "Get a kill streak of 50.", achievementEntity.isBowling());
        setButton(p,  12, "Charm", "Chat 100 times before leaving.", achievementEntity.isCharm());
        setButton(p,  13, "Craftsman", "Combine your sword with an enchantment.", achievementEntity.isCraftsman());
        setButton(p,  14, "Gamble", "Kill a player while at low health.", achievementEntity.isGamble());
        setButton(p,  15, "Gladiator", "Win 35 duels.", achievementEntity.isGladiator());
        setButton(p,  16, "Legend", "Rebirth your character.", achievementEntity.isLegend());

        setButton(p,  19, "Merchant", "Trade with Caesar at the mines.", achievementEntity.isMerchant());
        setButton(p,  20, "Superior", "Win an event.", achievementEntity.isSuperior());
        setButton(p,  21, "Zorgin", "Kill Zorg in the mines.", achievementEntity.isZorgin());
        setButton(p,  22, "Grinder", "Mine 100 ores.", achievementEntity.isGrinder());
        setButton(p,  23, "Artisan", "Place every type of wood plank.", achievementEntity.isArtisan());
        setButton(p,  24, "Harvester", "Collect a player coffin.", achievementEntity.isHarvester());
        setButton(p,  25, "Speedrunner", "Get a kill within 30 seconds of launch.", achievementEntity.isSpeedrunner());

        setButton(p,  28, "Error", "Die 500 times.", achievementEntity.isError());



        update();
    }

    private void setButton(Player p, int slot, String name, String lore, boolean completed) {

        String complete = completed ? CC.GREEN + "Complete" : CC.RED + "Incomplete";
        Material mat = completed ? Material.ENCHANTED_BOOK : Material.BOOK;

        addButton(Button.createBasic(ItemBuilder.of(mat, 1).name(CC.RED + "" + CC.BOLD + name).lore(CC.GRAY + lore, " ", complete).build()), slot);
    }
}
