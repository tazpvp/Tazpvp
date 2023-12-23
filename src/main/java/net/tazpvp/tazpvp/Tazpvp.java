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
import net.tazpvp.tazpvp.commands.admin.rank.RankCommand;
import net.tazpvp.tazpvp.commands.gameplay.duel.DuelCommand;
import net.tazpvp.tazpvp.commands.gameplay.guild.GuildCommand;
import net.tazpvp.tazpvp.commands.gameplay.leaderboard.BaltopCommand;
import net.tazpvp.tazpvp.commands.moderation.StaffChatCommand;
import net.tazpvp.tazpvp.player.achievements.achievement.*;
import net.tazpvp.tazpvp.commands.admin.BroadcastCommand;
import net.tazpvp.tazpvp.commands.admin.KeyallCommand;
import net.tazpvp.tazpvp.commands.admin.ResetStatsCommand;
import net.tazpvp.tazpvp.commands.admin.booster.BoosterCommand;
import net.tazpvp.tazpvp.commands.admin.edit.EditCommand;
import net.tazpvp.tazpvp.commands.admin.hide.HideCommand;
import net.tazpvp.tazpvp.commands.admin.kit.KitCommand;
import net.tazpvp.tazpvp.commands.admin.npc.NpcCommand;
import net.tazpvp.tazpvp.commands.admin.stats.StatCommand;
import net.tazpvp.tazpvp.commands.admin.tazload.TazloadCommand;
import net.tazpvp.tazpvp.commands.admin.teleportWorld.TeleportWorldCommand;
import net.tazpvp.tazpvp.commands.moderation.vanish.VanishCommand;
import net.tazpvp.tazpvp.commands.network.DailyCommand;
import net.tazpvp.tazpvp.commands.gameplay.report.ReportCommand;
import net.tazpvp.tazpvp.commands.gameplay.event.EventCommand;
import net.tazpvp.tazpvp.commands.network.HelpCommand;
import net.tazpvp.tazpvp.commands.gameplay.leaderboard.LeaderboardCommand;
import net.tazpvp.tazpvp.commands.gameplay.loadout.LoadoutCommand;
import net.tazpvp.tazpvp.commands.moderation.ban.UnbanCommand;
import net.tazpvp.tazpvp.commands.moderation.mute.MuteCommand;
import net.tazpvp.tazpvp.commands.moderation.ReportViewCommand;
import net.tazpvp.tazpvp.commands.moderation.RestoreCommand;
import net.tazpvp.tazpvp.commands.moderation.mute.UnmuteCommand;
import net.tazpvp.tazpvp.commands.moderation.ban.BanCommand;
import net.tazpvp.tazpvp.commands.network.*;
import net.tazpvp.tazpvp.commands.network.SpawnCommand;
import net.tazpvp.tazpvp.utils.discord.bot.BotThread;
import net.tazpvp.tazpvp.game.items.enchants.EnchantUtil;
import net.tazpvp.tazpvp.game.events.Event;
import net.tazpvp.tazpvp.game.items.UsableItem;
import net.tazpvp.tazpvp.listeners.*;
import net.tazpvp.tazpvp.npc.shops.*;
import net.tazpvp.tazpvp.player.talents.talent.*;
import net.tazpvp.tazpvp.utils.ConfigUtil;
import net.tazpvp.tazpvp.game.crates.CrateManager;
import net.tazpvp.tazpvp.data.database.PostgresqlDatabase;
import net.tazpvp.tazpvp.data.entity.*;
import net.tazpvp.tazpvp.data.implementations.*;
import net.tazpvp.tazpvp.utils.functions.AfkFunctions;
import net.tazpvp.tazpvp.utils.functions.CombatTagFunctions;
import net.tazpvp.tazpvp.utils.leaderboard.spawnable.SpawnableLeaderboardManager;
import net.tazpvp.tazpvp.game.bosses.BossManager;
import net.tazpvp.tazpvp.game.bosses.zorg.Zorg;
import net.tazpvp.tazpvp.utils.observer.Observer;
import net.tazpvp.tazpvp.utils.passive.Alerts;
import net.tazpvp.tazpvp.utils.passive.Generator;
import net.tazpvp.tazpvp.utils.passive.Holograms;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import world.ntdi.nrcore.utils.command.CommandCL;
import world.ntdi.nrcore.utils.command.simple.NRCommand;
import world.ntdi.nrcore.utils.region.Cuboid;
import world.ntdi.postglam.connection.Database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/*
    A plugin for tazpvp beacuse we love tazpvp! <3 I love tazpvp <3 <3 <3 Rownxo smells like tazpvp stinky tazpvp
 */
public final class Tazpvp extends JavaPlugin {
    @Getter
    private static List<Observer> observers = new ArrayList<>();
    @Getter
    private List<NPC> npcs = new ArrayList<>();

    public static String prefix = "tazpvp.";

    @Getter
    private static ConfigUtil parkourUtil;

    @Getter
    public static Cuboid spawnRegion;
    @Getter
    public static Cuboid afkRegion;

    @Getter
    private static Database database;
    @Getter
    private static PostgresqlDatabase postgresqlDatabase;

    private static final Logger log = Logger.getLogger("Minecraft");
    @Getter
    private static CrateManager crateManager;
    @Getter
    private static SpawnableLeaderboardManager spawnableLeaderboardManager;
    @Getter
    private static BotThread botThread;

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        registerEvents();
        registerCommands();
        Generator.generate();
        Holograms.holograms();
        Alerts.alert();
        Event.eventTypes.add("FFA");
        registerObservable();
        EnchantUtil.register();
        spawnNpcs();
        CombatTagFunctions.initCombatTag();
        AfkFunctions.setup();
        UsableItem.registerCustomItems();

        parkourUtil = new ConfigUtil("parkour.yml", this);

        spawnRegion = new Cuboid(
                new Location(Bukkit.getWorld("arena"), 25, 137, -31),
                new Location(Bukkit.getWorld("arena"), -24, 93, 25)
        );
        afkRegion = new Cuboid(
                new Location(Bukkit.getWorld("arena"), 16, 98, 9),
                new Location(Bukkit.getWorld("arena"), 11, 95, 4)
        );

        try {
            connectDatabase(
                    getConfig().getString("sql-host"),
                    getConfig().getInt("sql-port"),
                    getConfig().getString("sql-user"),
                    getConfig().getString("sql-password")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        crateManager = new CrateManager();

        botThread = new BotThread(getConfig().getString("bot-token"));
        botThread.start();

        spawnableLeaderboardManager = new SpawnableLeaderboardManager(this);
    }

    private static void connectDatabase(String host, int port, String user, String password) throws SQLException {
        database = new Database(host, port, user, password);
        database.connect();
        postgresqlDatabase = new PostgresqlDatabase("jdbc:postgresql://" + host + ":" + port + "/postgres", user, password);

        new RankServiceImpl().createTableIfNotExists(postgresqlDatabase, RankEntity.class);
        new PunishmentServiceImpl().createTableIfNotExists(postgresqlDatabase, PunishmentEntity.class);
        new KitServiceImpl().createTableIfNotExists(postgresqlDatabase, KitEntity.class);
        new UserAchievementServiceImpl().createTableIfNotExists(postgresqlDatabase, UserAchievementEntity.class);
        new AchievementServiceImpl().createTableIfNotExists(postgresqlDatabase, AchievementEntity.class);
        new TalentServiceImpl().createTableIfNotExists(postgresqlDatabase, TalentEntity.class);
        new ExpirationRankServiceImpl().createTableIfNotExists(postgresqlDatabase, ExpirationRankEntity.class);
        new GameRankServiceImpl().createTableIfNotExists(postgresqlDatabase, GameRankEntity.class);
        new PermissionServiceImpl().createTableIfNotExists(postgresqlDatabase, PermissionEntity.class);
        new UserRankServiceImpl().createTableIfNotExists(postgresqlDatabase, UserRankEntity.class);
    }

    public static void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void onDisable() {

        despawnNpcs();
        BossManager.despawnBoss();
        Holograms.removeHolograms();

        postgresqlDatabase.close();
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
        new Harvester();
        new Artisan();
        new Harvester();
        new Speedrunner();
//        new Error();

        new Revenge();
        new Moist();
        new Agile();
        new Architect();
        new Blessed();
        new Cannibal();
        new Excavator();
        new Hunter();
        new Resilient();
        new Proficient();
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
                new DailyCommand(),
                new DuelCommand(),
                new ReportCommand(),
                new TazloadCommand(),
                new ReportCommand(),
                new GuildCommand(),
                new ReportViewCommand(),
                new ReportCommand(),
                new HelpCommand(),
                new MuteCommand(),
                new LeaderboardCommand(),
                new ApplyCommand(),
                new AdCommand(),
                new TeleportWorldCommand(),
                new EditCommand(),
                new PlaytimeCommand(),
                new EventCommand(),
                new UnmuteCommand(),
                new BoosterCommand(),
                new LoadoutCommand(),
                new StoreCommand(),
                new UnbanCommand(),
                new VanishCommand(),
                new KeyallCommand(),
                new ResetStatsCommand(),
                new BroadcastCommand(),
                new StaffChatCommand(),
                new RankCommand(),
                new BaltopCommand()
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
        getServer().getPluginManager().registerEvents(new ProjectileLaunch(this), this);
        getServer().getPluginManager().registerEvents(new Death(), this);
        getServer().getPluginManager().registerEvents(new CommandSend(), this);
        getServer().getPluginManager().registerEvents(new Craft(), this);
        getServer().getPluginManager().registerEvents(new Explode(), this);
        getServer().getPluginManager().registerEvents(new EntitySpawn(), this);
        getServer().getPluginManager().registerEvents(new Burn(), this);
    }

    private void spawnNpcs() {
        npcs.add(new Maxim());
        npcs.add(new Lorenzo());
        npcs.add(new Bub());
        npcs.add(new Caesar());

        new BukkitRunnable() {
            @Override
            public void run() {
                BossManager.addBoss(new Zorg(new Location(Bukkit.getWorld("arena"), -12, 65, 210)));

                BossManager.spawnBoss();
                BossManager.setupRunnable(Tazpvp.getInstance());
            }
        }.runTaskLater(this, 20*30);
    }

    private void despawnNpcs() {
        npcs.forEach(NPC::remove);
        npcs.clear();
    }
}
