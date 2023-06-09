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
import net.tazpvp.tazpvp.commands.admin.hide.HideCommand;
import net.tazpvp.tazpvp.commands.admin.kit.KitCommand;
import net.tazpvp.tazpvp.commands.admin.npc.NpcCommand;
import net.tazpvp.tazpvp.commands.admin.stats.StatCommand;
import net.tazpvp.tazpvp.commands.gameplay.DailyCommand;
import net.tazpvp.tazpvp.commands.moderation.RestoreCommand;
import net.tazpvp.tazpvp.commands.moderation.ban.BanCommand;
import net.tazpvp.tazpvp.commands.gameplay.spawn.SpawnCommand;
import net.tazpvp.tazpvp.commands.network.DiscordCommand;
import net.tazpvp.tazpvp.events.Event;
import net.tazpvp.tazpvp.listeners.*;
import net.tazpvp.tazpvp.npc.shops.*;
import net.tazpvp.tazpvp.talents.talent.*;
import net.tazpvp.tazpvp.utils.ConfigUtil;
import net.tazpvp.tazpvp.utils.crate.CrateManager;
import net.tazpvp.tazpvp.utils.functions.CombatTagFunctions;
import net.tazpvp.tazpvp.utils.observer.Observer;
import net.tazpvp.tazpvp.utils.passive.Alerts;
import net.tazpvp.tazpvp.utils.passive.Generator;
import net.tazpvp.tazpvp.utils.passive.Holograms;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import world.ntdi.nrcore.utils.command.CommandCL;
import world.ntdi.nrcore.utils.command.simple.NRCommand;
import world.ntdi.nrcore.utils.region.Cuboid;
import world.ntdi.postglam.connection.Database;

import java.util.*;
import java.util.logging.Logger;

/*
    A plugin for tazpvp beacuse we love tazpvp! <3 I love tazpvp <3 <3 <3 Rownxo smells like tazpvp stinky tazpvp
 */
public final class Tazpvp extends JavaPlugin {
    @Getter
    private static List<Observer> observers = new ArrayList<>();
    private List<NPC> npcs = new LinkedList<>();
    public static List<UUID> playerList = new ArrayList<>();
    public static List<String> events = new ArrayList<>();
    public static Event event;
    public static String prefix = "tazpvp.";

    @Getter
    private static ConfigUtil parkourUtil;

    @Getter
    public static Cuboid spawnRegion;

    @Getter
    private static Database database;

    private static final Logger log = Logger.getLogger("Minecraft");
    @Getter
    private static CrateManager crateManager;

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

//        getLogger().info(NRCore.config.FROM_MESSAGE);

        registerEvents();
        registerCommands();
        Generator.generate();
        Holograms.holograms();
        Alerts.alert();
        events.add("FFA");
        registerObservable();
        spawnNpcs();
        CombatTagFunctions.initCombatTag();

        parkourUtil = new ConfigUtil("parkour.yml", this);

        spawnRegion = new Cuboid(
                new Location(Bukkit.getWorld("arena"), 25, 137, -31),
                new Location(Bukkit.getWorld("arena"), -24, 93, 25)
        );

        connectDatabase(
                getConfig().getString("sql-host"),
                getConfig().getInt("sql-port"),
                getConfig().getString("sql-user"),
                getConfig().getString("sql-password")
        );

        crateManager = new CrateManager();
    }

    private static void connectDatabase(String host, int port, String user, String password) {
        database = new Database(host, port, user, password);
        database.connect();
    }

    public static void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void onDisable() {

        log.info(String.format("[%s] Disabled Version %s", getDescription().getName(), getDescription().getVersion()));

        despawnNpcs();

        getCrateManager().shutdown();
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
        new Zorgin();

        new Revenge();
        new Moist();
        new Agile();
        new Architect();
        new Blessed();
        new Cannibal();
        new Excavator();
        new Hunter();
        new Resilient();
    }

    public void registerCommands() {
        register(
                new KitCommand(),
                new NpcCommand(),
                new BanCommand(),
                new RestoreCommand(),
                new SpawnCommand(),
                new HideCommand(),
                new DiscordCommand(),
                new StatCommand(),
                new DailyCommand()
        );
    }

    private void register(NRCommand... commands) {
        for (NRCommand command : commands) {
            CommandCL.register(command, "tazpvp");
        }
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new Damage(), this);
        getServer().getPluginManager().registerEvents(new Join(), this);
        getServer().getPluginManager().registerEvents(new Leave(), this);
        getServer().getPluginManager().registerEvents(new InventoryClick(), this);
        getServer().getPluginManager().registerEvents(new Break(), this);
        getServer().getPluginManager().registerEvents(new Move(), this);
        getServer().getPluginManager().registerEvents(new Place(), this);
        getServer().getPluginManager().registerEvents(new Chat(), this);
        getServer().getPluginManager().registerEvents(new Exp(), this);
        getServer().getPluginManager().registerEvents(new Interact(), this);
        getServer().getPluginManager().registerEvents(new Shoot(), this);
    }

    private void spawnNpcs() {
        npcs.add(new Maxim());
        npcs.add(new Lorenzo());
        npcs.add(new Bub());
        npcs.add(new Caesar());
    }

    private void despawnNpcs() {
        npcs.forEach(NPC::remove);
        npcs.clear();
    }

}
