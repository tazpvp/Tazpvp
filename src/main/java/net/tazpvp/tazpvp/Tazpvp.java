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
import lombok.NonNull;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.tazpvp.tazpvp.commands.admin.BroadcastCommand;
import net.tazpvp.tazpvp.commands.admin.KeyallCommand;
import net.tazpvp.tazpvp.commands.admin.ResetStatsCommand;
import net.tazpvp.tazpvp.commands.admin.animation.ChestAnimationCommand;
import net.tazpvp.tazpvp.commands.admin.booster.BoosterCommand;
import net.tazpvp.tazpvp.commands.admin.edit.EditCommand;
import net.tazpvp.tazpvp.commands.admin.hide.HideCommand;
import net.tazpvp.tazpvp.commands.admin.holograms.HologramCommand;
import net.tazpvp.tazpvp.commands.admin.kit.KitCommand;
import net.tazpvp.tazpvp.commands.admin.npc.NpcCommand;
import net.tazpvp.tazpvp.commands.admin.stats.StatCommand;
import net.tazpvp.tazpvp.commands.admin.tazload.TazloadCommand;
import net.tazpvp.tazpvp.commands.admin.teleportWorld.TeleportWorldCommand;
import net.tazpvp.tazpvp.commands.game.duel.DuelCommand;
import net.tazpvp.tazpvp.commands.game.guild.GuildCommand;
import net.tazpvp.tazpvp.commands.game.leaderboard.BaltopCommand;
import net.tazpvp.tazpvp.commands.game.leaderboard.LeaderboardCommand;
import net.tazpvp.tazpvp.commands.game.party.PartyCommand;
import net.tazpvp.tazpvp.commands.game.pay.PayCommand;
import net.tazpvp.tazpvp.commands.game.report.ReportCommand;
import net.tazpvp.tazpvp.commands.game.tournament.TournamentCommand;
import net.tazpvp.tazpvp.commands.game.votemute.VoteMuteCommand;
import net.tazpvp.tazpvp.commands.moderation.ReportViewCommand;
import net.tazpvp.tazpvp.commands.moderation.RestoreCommand;
import net.tazpvp.tazpvp.commands.moderation.StaffChatCommand;
import net.tazpvp.tazpvp.commands.moderation.ban.BanCommand;
import net.tazpvp.tazpvp.commands.moderation.ban.UnbanCommand;
import net.tazpvp.tazpvp.commands.moderation.mute.MuteCommand;
import net.tazpvp.tazpvp.commands.moderation.mute.UnmuteCommand;
import net.tazpvp.tazpvp.commands.moderation.vanish.VanishCommand;
import net.tazpvp.tazpvp.commands.network.*;
import net.tazpvp.tazpvp.data.database.PostgresqlDatabase;
import net.tazpvp.tazpvp.data.entity.*;
import net.tazpvp.tazpvp.data.implementations.*;
import net.tazpvp.tazpvp.data.services.*;
import net.tazpvp.tazpvp.game.achievements.Error;
import net.tazpvp.tazpvp.game.achievements.*;
import net.tazpvp.tazpvp.game.bosses.BossManager;
import net.tazpvp.tazpvp.game.bosses.zorg.Zorg;
import net.tazpvp.tazpvp.game.crates.CrateManager;
import net.tazpvp.tazpvp.game.items.UsableItem;
import net.tazpvp.tazpvp.game.npcs.NPC;
import net.tazpvp.tazpvp.game.npcs.achievements.Achievements;
import net.tazpvp.tazpvp.game.npcs.guilds.Guilds;
import net.tazpvp.tazpvp.game.npcs.shop.Shop;
import net.tazpvp.tazpvp.game.npcs.tournaments.Tournaments;
import net.tazpvp.tazpvp.game.talents.*;
import net.tazpvp.tazpvp.helpers.AfkHelper;
import net.tazpvp.tazpvp.helpers.BlockHelper;
import net.tazpvp.tazpvp.helpers.CombatTagHelper;
import net.tazpvp.tazpvp.helpers.EnchantHelper;
import net.tazpvp.tazpvp.listeners.*;
import net.tazpvp.tazpvp.services.PlayerNameTagService;
import net.tazpvp.tazpvp.services.PlayerNameTagServiceImpl;
import net.tazpvp.tazpvp.utils.ConfigUtil;
import net.tazpvp.tazpvp.utils.discord.bot.BotThread;
import net.tazpvp.tazpvp.utils.holograms.HologramUtil;
import net.tazpvp.tazpvp.utils.leaderboard.spawnable.SpawnableLeaderboardManager;
import net.tazpvp.tazpvp.utils.observer.Observer;
import net.tazpvp.tazpvp.utils.passive.Alerts;
import net.tazpvp.tazpvp.utils.passive.Generator;
import net.tazpvp.tazpvp.utils.passive.Holograms;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import world.ntdi.nrcore.utils.command.CommandCL;
import world.ntdi.nrcore.utils.command.simple.NRCommand;
import world.ntdi.postglam.connection.Database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
    A plugin for tazpvp beacuse we love tazpvp! <3 I love tazpvp <3 <3 <3 Rownxo smells like tazpvp stinky tazpvp
 */
public final class Tazpvp extends JavaPlugin {
    @Getter
    private static final List<Observer> observers = new ArrayList<>();
    @Getter
    private final List<NPC> npcs = new ArrayList<>();
    @Getter
    private static ConfigUtil parkourUtil;
    @Getter
    private static Database database;
    @Getter
    private static PostgresqlDatabase postgresqlDatabase;
    @Getter
    private BukkitAudiences adventure;
    @Getter
    private static CrateManager crateManager;
    @Getter
    private static SpawnableLeaderboardManager spawnableLeaderboardManager;
    @Getter
    private static HologramUtil hologramUtil;
    @Getter
    private static BotThread botThread;
    @Getter
    private PlayerStatService playerStatService;
    @Getter
    private GuildService guildService;
    @Getter
    private GuildMemberService guildMemberService;
    @Getter
    private UserRankService userRankService;
    @Getter
    private PlayerNameTagService playerNameTagService;
    @Getter
    private UserAchievementService userAchievementService;
    @Getter
    private AchievementService achievementService;

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

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

        adventure = BukkitAudiences.create(this);

        registerEvents();
        registerCommands();
        playerStatService = new PlayerStatServiceImpl();
        playerNameTagService = new PlayerNameTagServiceImpl(this);
        Generator.initialize();
        Holograms.initialize();
        Alerts.initialize();
        registerObservable();
        EnchantHelper.register();
        spawnNpcs();
        CombatTagHelper.initCombatTag();
        AfkHelper.initialize();
        UsableItem.registerCustomItems();

        parkourUtil = new ConfigUtil("parkour.yml", this);
        crateManager = new CrateManager();
        botThread = new BotThread(getConfig().getString("bot-token"));
        botThread.start();
        spawnableLeaderboardManager = new SpawnableLeaderboardManager(this);
        hologramUtil = new HologramUtil();
    }

    private void connectDatabase(String host, int port, String user, String password) throws SQLException {
        database = new Database(host, port, user, password);
        database.connect();
        postgresqlDatabase = new PostgresqlDatabase("jdbc:postgresql://" + host + ":" + port + "/postgres", user, password);

        new RankServiceImpl().createTableIfNotExists(postgresqlDatabase, RankEntity.class);
        new PunishmentServiceImpl().createTableIfNotExists(postgresqlDatabase, PunishmentEntity.class);
        new KitServiceImpl().createTableIfNotExists(postgresqlDatabase, KitEntity.class);
        new TalentServiceImpl().createTableIfNotExists(postgresqlDatabase, TalentEntity.class);
        new ExpirationRankServiceImpl().createTableIfNotExists(postgresqlDatabase, ExpirationRankEntity.class);
        new GameRankServiceImpl().createTableIfNotExists(postgresqlDatabase, GameRankEntity.class);
        new PermissionServiceImpl().createTableIfNotExists(postgresqlDatabase, PermissionEntity.class);

        this.guildMemberService = new GuildMemberServiceImpl();
        this.guildService = new GuildServiceImpl(guildMemberService);
        this.playerStatService = new PlayerStatServiceImpl();
        this.userRankService = new UserRankServiceImpl();
        this.playerNameTagService = new PlayerNameTagServiceImpl(this);
        this.achievementService = new AchievementServiceImpl();
        this.userAchievementService = new UserAchievementServiceImpl();

        guildMemberService.createTableIfNotExists(postgresqlDatabase, GuildMemberEntity.class);
        guildService.createTableIfNotExists(postgresqlDatabase, GuildEntity.class);
        playerStatService.createTableIfNotExists(postgresqlDatabase, PlayerStatEntity.class);
        userRankService.createTableIfNotExists(postgresqlDatabase, UserRankEntity.class);
        achievementService.createTableIfNotExists(postgresqlDatabase, UserRankEntity.class);
        userAchievementService.createTableIfNotExists(postgresqlDatabase, UserRankEntity.class);
    }

    public static void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            playerNameTagService.recalibratePlayer(player);
        }

        postgresqlDatabase.close();

        despawnNpcs();
        BossManager.despawnBoss();
        Holograms.removeHolograms();
        playerNameTagService.destroyAllNametags();
        BlockHelper.deleteAllPlayerBlocks();
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
        new Harvester();
        new Artisan();
        new Speedrunner();
        new Error();
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
        new Medic();
    }
    public void registerCommands() {
        register(
                new KitCommand(),
                new NpcCommand(this),
                new BanCommand(),
                new RestoreCommand(),
                new SpawnCommand(),
                new HideCommand(),
                new DiscordCommand(),
                new StatCommand(),
                new DailyCommand(),
                new DuelCommand(),
                new TazloadCommand(),
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
                new TournamentCommand(),
                new UnmuteCommand(),
                new BoosterCommand(),
                new StoreCommand(),
                new UnbanCommand(),
                new VanishCommand(),
                new KeyallCommand(),
                new ResetStatsCommand(),
                new BroadcastCommand(),
                new StaffChatCommand(),
                new BaltopCommand(),
                new PayCommand(),
                new HologramCommand(),
                new ChestAnimationCommand(),
                new PartyCommand(),
                new net.tazpvp.tazpvp.commands.game.kit.KitCommand(),
                new VoteMuteCommand()
        );
    }

    private void register(NRCommand... commands) {
        for (NRCommand command : commands) {
            CommandCL.register(command, "tazpvp");
        }
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new DamageListener(), this);
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new LeaveListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        getServer().getPluginManager().registerEvents(new BreakListener(), this);
        getServer().getPluginManager().registerEvents(new MoveListener(), this);
        getServer().getPluginManager().registerEvents(new PlaceListener(), this);
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(new ExpListener(), this);
        getServer().getPluginManager().registerEvents(new InteractListener(), this);
        getServer().getPluginManager().registerEvents(new ShootListener(), this);
        getServer().getPluginManager().registerEvents(new ProjectileListener(this), this);
        getServer().getPluginManager().registerEvents(new DeathListener(), this);
        getServer().getPluginManager().registerEvents(new CommandListener(), this);
        getServer().getPluginManager().registerEvents(new CraftListener(), this);
        getServer().getPluginManager().registerEvents(new ExplodeListener(), this);
        getServer().getPluginManager().registerEvents(new EntitySpawnListener(), this);
        getServer().getPluginManager().registerEvents(new BurnListener(), this);
        getServer().getPluginManager().registerEvents(new FoodListener(), this);
    }

    private void spawnNpcs() {
        npcs.add(new Shop());
        npcs.add(new Achievements());
        npcs.add(new Guilds(guildService));
        npcs.add(new Tournaments());

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

    public @NonNull BukkitAudiences adventure() {
        if(adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return adventure;
    }
}
