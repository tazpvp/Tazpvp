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

package net.tazpvp.tazpvp.utils.observer;

import net.tazpvp.tazpvp.Tazpvp;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityCombustEvent;

public class Observable implements Observer {
    /**
     * Register the observer to the list
     */
    public Observable() {
        Tazpvp.registerObserver(this);
    }

    @Override
    public void death(Player victim, Player killer) {}
    @Override
    public void mine(Player p, Material material) {}
    @Override
    public void launch(Player p) {}
    @Override
    public void talent(Player p) {}
    @Override
    public void gui(Player p, String name) {}
    @Override
    public void rebirth(Player p) {}
    @Override
    public void duel(Player p) {}
    @Override
    public void event(Player p) {}
    @Override
    public void enchant(Player p) {}
    @Override
    public void chat(Player p, String chat) {}
    @Override
    public void burn(Player p, EntityCombustEvent event) {}
    @Override
    public void place(Player p, Block b) {}
    @Override
    public void shoot(Player shooter) {}
    @Override
    public void kill_zorg(Player shooter) {}
    @Override
    public void open_coffin(Player p) {}
    @Override
    public void duel_end(Player winner, Player loser) {}
}
