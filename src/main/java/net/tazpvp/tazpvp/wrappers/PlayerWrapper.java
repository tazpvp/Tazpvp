package net.tazpvp.tazpvp.wrappers;

import lombok.Getter;
import lombok.Setter;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.commands.gameplay.report.utils.ReportDebounce;
import net.tazpvp.tazpvp.commands.gameplay.report.utils.ReportLogger;
import net.tazpvp.tazpvp.data.entity.*;
import net.tazpvp.tazpvp.data.implementations.TalentServiceImpl;
import net.tazpvp.tazpvp.data.implementations.UserAchievementServiceImpl;
import net.tazpvp.tazpvp.data.implementations.UserRankServiceImpl;
import net.tazpvp.tazpvp.data.services.GuildService;
import net.tazpvp.tazpvp.data.services.TalentService;
import net.tazpvp.tazpvp.data.services.UserAchievementService;
import net.tazpvp.tazpvp.data.services.UserRankService;
import net.tazpvp.tazpvp.game.duels.Duel;
import net.tazpvp.tazpvp.game.events.TeamObject;
import net.tazpvp.tazpvp.game.npc.characters.NPC;
import net.tazpvp.tazpvp.utils.PlayerNameTag;
import net.tazpvp.tazpvp.enums.CC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.scoreboard.Team;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Wrapper for the player object which contains valuable methods exclusive to tazpvp
 */
@Getter
public class PlayerWrapper {
    private final UUID uuid;
    private final UserRankService userRankService;
    private final GuildService guildService;
    private final List<ReportDebounce> reportDebouncesList;
    private final List<ReportLogger> reportLoggerList;
    private PermissionAttachment permissionAttachment;
    private UserRankEntity userRankEntity; // After you set a value here make sure to call refresh()
    private UserAchievementEntity userAchievementEntity;
    private TalentEntity talentEntity;
    private final ConcurrentHashMap<UUID, Duel> duelRequests;

    @Setter private boolean launching;
    @Setter private boolean respawning;
    @Setter private boolean canRestore;
    @Setter private Duel duel;
    @Setter private NPC receivedDialogue;
    @Setter private boolean npcDialogue;
    @Setter private boolean isAfk;
    @Setter private int killCount;
    @Setter private long timeOfLaunch;
    @Setter private long timeSinceAfk;
    @Setter private List<Material> blocksPlaced;
    @Setter private String lastMessageSent;
    @Setter private Duel spectating;
    @Setter private boolean vanished;
    @Setter private boolean staffChatActive;
    @Setter private TeamObject eventTeam;


    public PlayerWrapper(UUID uuid) {
        this.uuid = uuid;
        this.launching = false;
        this.respawning = false;
        this.canRestore = false;
        this.duel = null;
        this.reportDebouncesList = new ArrayList<>();
        this.reportLoggerList = new ArrayList<>();
        this.receivedDialogue = null;
        this.npcDialogue = false;
        this.killCount = 0;
        this.timeOfLaunch = 0;
        this.blocksPlaced = new ArrayList<>();
        this.lastMessageSent = "";
        this.spectating = null;
        this.staffChatActive = false;

        this.userRankService = new UserRankServiceImpl();
        this.userRankEntity = userRankService.getOrDefault(getUuid());

        final UserAchievementService userAchievementService = new UserAchievementServiceImpl();
        this.userAchievementEntity = userAchievementService.getOrDefault(getUuid());

        final TalentService talentService = new TalentServiceImpl();
        this.talentEntity = talentService.getOrDefault(getUuid());
        this.guildService = Tazpvp.getInstance().getGuildService();
        this.duelRequests = new ConcurrentHashMap<>();

        refreshPermissions();
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public String getGuildTag() {
        GuildEntity playerGuild = guildService.getGuildByPlayer(getUuid());
        if (playerGuild != null) {
            if (playerGuild.getTag() != null) {
                return CC.YELLOW + " [" + playerGuild.getTag().toUpperCase() + "]";
            }
        }
        return "";
    }

    public GameRankEntity getRank() {
        return this.userRankService.getHighestRank(this.userRankEntity);
    }

    public String getRankPrefix() {
        if (!getRank().getPrefix().isEmpty()) {
            return getRank().getPrefix();
        }
        return getCustomPrefix();
    }

    @Nullable
    public String getCustomPrefix() {
        return getRank().getPrefix();
    }

    public void setCustomPrefix(String prefix) {
        userRankEntity.setCustomPrefix(prefix);
        setRankEntity();
    }

    public void hidePlayer() {
        Bukkit.getOnlinePlayers().forEach(this::hidePlayer);
    }

    public void hidePlayer(Player target) {
        Player owner = getPlayer();
        owner.hidePlayer(Tazpvp.getInstance(), target);
    }

    public void hideFromPlayer(Player target) {
        PlayerWrapper.getPlayer(target).hidePlayer(getPlayer());
    }

    public void hideFromPlayer() {
        Bukkit.getOnlinePlayers().forEach(player -> PlayerWrapper.getPlayer(player).hidePlayer(getPlayer()));
    }

    public void showPlayer() {
        Bukkit.getOnlinePlayers().forEach(target -> {
            if (!target.canSee(getPlayer())) {
                showPlayer(target);
            }
        });
    }

    public void showPlayer(Player target) {
        Player owner = getPlayer();
        owner.showPlayer(Tazpvp.getInstance(), target);
    }

    public void showFromPlayer(Player target) {
        PlayerWrapper.getPlayer(target).showPlayer(getPlayer());
    }

    public void showFromPlayer() {
        Bukkit.getOnlinePlayers().forEach(uuid -> {
            PlayerWrapper wrapper = PlayerWrapper.getPlayer(uuid);
            wrapper.showPlayer(getPlayer());
        });
    }

    public void reportPlayer(Player target, String reason) {
        this.reportDebouncesList.add(new ReportDebounce(target.getUniqueId(), System.currentTimeMillis()));
        PlayerWrapper.getPlayer(target).addReport(getUuid(), reason);
    }

    private void addReport(UUID reportee, String reason) {
        this.reportLoggerList.add(new ReportLogger(reportee, reason, System.currentTimeMillis()));
    }

    private void refreshPermissions() {
        if (Bukkit.getPlayer(getUuid()) == null) {
            return;
        }

        if (this.permissionAttachment != null) {
            this.permissionAttachment.remove();
        }
        this.permissionAttachment = getPlayer().addAttachment(Tazpvp.getInstance());
//        getRank().getPermissions().forEach(perm -> this.permissionAttachment.setPermission(perm, true));
        this.userRankService.getPermissions(getUserRankEntity())
                .forEach(perm -> this.permissionAttachment.setPermission(perm, true));

        this.permissionAttachment.getPermissible().recalculatePermissions();
    }


    public void setRankEntity() {
        this.userRankService.saveUserRankEntity(userRankEntity);
        refreshRankEntity();
    }

    public void refreshRankEntity() {
        this.userRankEntity = this.userRankService.getUserRankEntity(getUuid());
    }

    public void setUserAchievementEntity(final UserAchievementEntity achievementEntity) {
        final UserAchievementService userAchievementService = new UserAchievementServiceImpl();
        userAchievementService.saveUserAchievementEntity(achievementEntity);

        this.userAchievementEntity = achievementEntity;
    }

    public void setTalentEntity(final TalentEntity talentEntity) {
        final TalentService talentService = new TalentServiceImpl();
        talentService.saveTalentEntity(talentEntity);

        this.talentEntity = talentEntity;
    }

    public void refreshNametag() {
        Bukkit.getOnlinePlayers().forEach(plr -> new PlayerNameTag().initializePlayerNameTag(plr));
    }

    private static final WeakHashMap<UUID, PlayerWrapper> playerMap = new WeakHashMap<>();
    public static void addPlayer(Player p) {
        playerMap.put(p.getUniqueId(), new PlayerWrapper(p.getUniqueId()));
    }
    public static void addPlayer(UUID uuid) {
        playerMap.put(uuid, new PlayerWrapper(uuid));
    }
    public static void removePlayer(Player p) {
        playerMap.remove(p.getUniqueId(), new PlayerWrapper(p.getUniqueId()));
    }
    public static void removePlayer(UUID uuid) {
        playerMap.remove(uuid, new PlayerWrapper(uuid));
    }
    public static PlayerWrapper getPlayer(Player p) {
        return getPlayer(p.getUniqueId());
    }
    public static PlayerWrapper getPlayer(UUID uuid) {
        if (!playerMap.containsKey(uuid)) {
            addPlayer(uuid);
        }

        return playerMap.get(uuid);
    }
}