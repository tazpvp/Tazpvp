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
 *
 */

package net.tazpvp.tazpvp;

import lombok.Getter;
import net.tazpvp.tazpvp.achievements.achievement.*;
import net.tazpvp.tazpvp.commands.*;
import net.tazpvp.tazpvp.duels.Duel;
import net.tazpvp.tazpvp.events.continuous.Generator;
import net.tazpvp.tazpvp.listeners.*;
import net.tazpvp.tazpvp.npc.NPC;
import net.tazpvp.tazpvp.npc.npcs.Bub;
import net.tazpvp.tazpvp.npc.npcs.Lorenzo;
import net.tazpvp.tazpvp.npc.npcs.Maxim;
import net.tazpvp.tazpvp.talents.talent.Moist;
import net.tazpvp.tazpvp.talents.talent.Revenge;
import net.tazpvp.tazpvp.utils.functions.CombatFunctions;
import net.tazpvp.tazpvp.utils.objects.AssistKill;
import net.tazpvp.tazpvp.utils.observer.Observer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

/*
    A plugin for tazpvp beacuse we love tazpvp! <3 I love tazpvp <3 <3 <3 Rownxo smells like tazpvp stinky tazpvp
 */
public final class Tazpvp extends JavaPlugin {
    @Getter
    private static List<Observer> observers = new ArrayList<>();
    public static List<String> events = new ArrayList<>();
    public static String eventKey;
    public static List<UUID> playerList = new ArrayList<>();

    public static String prefix = "tazpvp.";

    public static WeakHashMap<UUID, AssistKill> combatAssist = new WeakHashMap<>();
    public static WeakHashMap<UUID, Duel> duels = new WeakHashMap<>();

    private List<NPC> npcs = new LinkedList<>();

    @Override
    public void onEnable() {

        registerEvents();
        registerCommands();

        new Generator();

        events.add("FFA");

        registerObservable();

        spawnNpcs();

        new BukkitRunnable() {
            @Override
            public void run() {
                CombatFunctions.check();
            }
        }.runTaskTimerAsynchronously(this, 16L, 16L);
    }

    public static void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void onDisable() {

        despawnNpcs();
    }

    public static Tazpvp getInstance() {
        return (Tazpvp) Bukkit.getPluginManager().getPlugin("Tazpvp");
    }

    public void registerObservable() {
        new Adept();
        new Bowling();
        new Charm();
        new Craftsman();
        new Gamble();
        new Gladiator();
        new Legend();
        new Merchant();
        new Superior();

        new Revenge();
        new Moist();
    }

    public void registerCommands() {
        new EventCommandFunction();
        new DuelCommandFunction();
        new NpcCommandFunction();
        new StatCommandFunction();
        new GuildCommandFunction();
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new Damage(), this);
        getServer().getPluginManager().registerEvents(new Join(), this);
        getServer().getPluginManager().registerEvents(new Leave(), this);
        getServer().getPluginManager().registerEvents(new InventoryClick(), this);
        getServer().getPluginManager().registerEvents(new BlockBreak(), this);
        getServer().getPluginManager().registerEvents(new ItemDrop(), this);
        getServer().getPluginManager().registerEvents(new Move(), this);
        getServer().getPluginManager().registerEvents(new Place(), this);
    }

    private void spawnNpcs() {
        npcs.add(new Maxim());
        npcs.add(new Lorenzo());
        npcs.add(new Bub());
    }

    private void despawnNpcs() {
        npcs.forEach(NPC::remove);
        npcs.clear();
    }

}
