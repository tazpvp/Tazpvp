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

package net.tazpvp.tazpvp.npc.characters;

import lombok.Getter;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.npc.dialogue.Dialogues;
import net.tazpvp.tazpvp.utils.PDCUtil;
import net.tazpvp.tazpvp.enums.CC;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import world.ntdi.postglam.data.Tuple;

import javax.annotation.Nonnull;
import java.util.UUID;

/**
 * A simple utility class for creating Villager NPCs
 */
@Getter
public abstract class NPC implements Listener {
    private final String NAME;
    private final Location SPAWN;
    private final Villager.Profession PROFESSION;
    private final Villager.Type TYPE;
    private final Sound SOUND;
    private final Villager V;
    private final UUID ID;
    private final Dialogues dialogues;


    public NPC(@Nonnull final String NAME, @Nonnull final Location SPAWN, @Nonnull final Villager.Profession PROFESSION, @Nonnull final Villager.Type TYPE, @Nonnull final Sound SOUND, @Nonnull final Dialogues dialogues) {
        this.NAME = NAME;
        this.SPAWN = SPAWN;
        this.PROFESSION = PROFESSION;
        this.TYPE = TYPE;
        this.SOUND = SOUND;
        this.ID = UUID.randomUUID();
        this.dialogues = dialogues;
        this.V = (Villager) SPAWN.getWorld().spawnEntity(SPAWN, EntityType.VILLAGER);

        V.setCustomName(CC.trans(NAME));
        V.setCustomNameVisible(true);
        V.setInvulnerable(true);
        V.setProfession(PROFESSION);
        V.setVillagerType(TYPE);
        V.setGravity(false);
        V.setCollidable(false);
        PDCUtil.setPDC(V, PDCUtil.getNpcKey(), this.ID.toString());

        Tazpvp.getInstance().getServer().getPluginManager().registerEvents(this, Tazpvp.getInstance());
    }

    public void remove() {
        V.remove();
    }

    public abstract void interact(@Nonnull final PlayerInteractAtEntityEvent e, @Nonnull final Player p);

    public boolean withinRange(Location location) {
        int RANGE = 4;
        return getSPAWN().toVector().distance(location.toVector()) <= RANGE;
    }

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent e) {
        if (e.getRightClicked().getType().equals(EntityType.VILLAGER)) {
            Villager v = (Villager) e.getRightClicked();
            Tuple<Boolean, String> pair = PDCUtil.hasPDC(v, PDCUtil.getNpcKey());

            if (pair.getA()) {
                if (pair.getB().equals(ID.toString())) {
                    interact(e, e.getPlayer());
                }
            }
        }
    }
}
