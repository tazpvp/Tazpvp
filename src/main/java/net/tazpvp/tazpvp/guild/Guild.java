package net.tazpvp.tazpvp.guild;

import lombok.Getter;
import net.tazpvp.tazpvp.utils.data.DataTypes;
import net.tazpvp.tazpvp.utils.data.GuildData;
import net.tazpvp.tazpvp.utils.data.PersistentData;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.ChatUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Guild implements Serializable {
    @Getter
    private final UUID ID;
    @Getter
    private UUID guildLeader;
    @Getter
    private final List<UUID> guildGenerals;
    @Getter
    private final List<UUID> guildMembers;
    @Getter
    private final List<UUID> invited;
    @Getter
    private final String name;
    @Getter
    private String description;
    @Getter
    private String tag;
    @Getter
    private Material icon;
    @Getter
    private boolean show_in_browser;
    @Getter
    private int kills, deaths;

    public Guild(String name, UUID guildLeader) {
        this.ID = UUID.randomUUID();
        this.name = name;
        this.guildLeader = guildLeader;
        this.guildGenerals = new ArrayList<>();
        this.guildMembers = new ArrayList<>();
        this.invited = new ArrayList<>();
        this.kills = 0;
        this.deaths = 0;
        this.show_in_browser = true;
        this.description = "A guild";
        this.icon = Material.OAK_SIGN;

        GuildData.initializeGuild(getID(), this);

        setPlayersGuild(getGuildLeader());
    }

    public void setPlayersGuild(OfflinePlayer p) {
        setPlayersGuild(p.getUniqueId());
    }

    public void setPlayersGuild(UUID p) {
        PersistentData.setValueS(p, DataTypes.GUILD_ID.getColumnName(), getID().toString());
    }

    public void resetPlayerGuild(UUID uuid) {
        PersistentData.setValueS(uuid, DataTypes.GUILD_ID.getColumnName(), "n");
        PlayerWrapper.getPlayer(uuid).refreshNametag();
    }

    public UUID[] getAllMembers() {
        List<UUID> mem = guildMembers;
        mem.addAll(guildGenerals);
        mem.add(guildLeader);
        return mem.toArray(UUID[]::new);
    }

    public boolean isInGuild(UUID uuid) {
        return Arrays.stream(getAllMembers()).toList().contains(uuid);
    }

    public boolean hasElevatedPerms(UUID uuid) {
        if (guildLeader.equals(uuid)) return true;
        else return guildGenerals.contains(uuid);
    }

    public void sendAll(String message) {
        for (UUID uuid : getAllMembers()) {
            Player p = Bukkit.getPlayer(uuid);
            if (p != null && p.isOnline()) {
                p.sendMessage(ChatUtils.chat(message));
            }
        }
    }

    public void addMember(UUID uuid) {
        if (getAllMembers().length < 22) {
            guildMembers.add(uuid);
            invited.remove(uuid);
            setPlayersGuild(uuid);
            OfflinePlayer p = Bukkit.getOfflinePlayer(uuid);
            sendAll(p.getName() + " has joined the guild!");
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
        GuildData.setGuild(getID(), this);
    }

    public void removeMember(UUID uuid) {
        if (getGuildMembers().contains(uuid)) {
            getGuildMembers().remove(uuid);
            resetPlayerGuild(uuid);
            OfflinePlayer p = Bukkit.getOfflinePlayer(uuid);
            sendAll(p.getName() + " has left the guild!");
        } else if (getGuildGenerals().contains(uuid)) {
            getGuildGenerals().remove(uuid);
            resetPlayerGuild(uuid);
            OfflinePlayer p = Bukkit.getOfflinePlayer(uuid);
            sendAll(p.getName() + " has left the guild!");
        } else {
            throw new IllegalArgumentException("Cannot kick someone who is not a member or general of a guild (includes owner)");
        }
        GuildData.setGuild(getID(), this);
    }

    public void promoteMember(UUID uuid) {
        if (getGuildMembers().contains(uuid)) {
            getGuildMembers().remove(uuid);
            getGuildGenerals().add(uuid);
            sendAll(Bukkit.getOfflinePlayer(uuid).getName() + " has been promoted to general");
        } else {
            throw new IllegalArgumentException("Cannot promote non member ranked");
        }
        GuildData.setGuild(getID(), this);
    }

    public void demoteMember(UUID uuid) {
        if (getGuildGenerals().contains(uuid)) {
            guildGenerals.remove(uuid);
            guildMembers.add(uuid);
            sendAll(Bukkit.getOfflinePlayer(uuid).getName() + " has been demoted to member");
        } else {
            throw new IllegalArgumentException("Cannot promote non general ranked");
        }
        GuildData.setGuild(getID(), this);
    }

    public void setDescription(UUID uuid, String description) {
        if (getGuildLeader().equals(uuid)) this.description = description;
        GuildData.setGuild(getID(), this);
    }

    public void setTag(UUID uuid, String tag) {
        if (getGuildLeader().equals(uuid)) this.tag = tag;
        PlayerWrapper.getPlayer(uuid).refreshNametag();
        GuildData.setGuild(getID(), this);
    }

    public void setIcon(UUID uuid, Material icon) {
        if (getGuildLeader().equals(uuid)) this.icon = icon;
        GuildData.setGuild(getID(), this);
    }

    public void setShow_in_browser(UUID uuid, boolean show_in_browser) {
        if (getGuildLeader().equals(uuid)) this.show_in_browser = show_in_browser;
        GuildData.setGuild(getID(), this);
    }

    public void addKills(int amt) {
        kills = kills + amt;
        GuildData.setGuild(getID(), this);
    }

    public void addDeaths(int amt) {
        deaths = deaths + amt;
        GuildData.setGuild(getID(), this);

    }

    public int getKDR() {
        if (deaths == 0 || kills == 0) {
            return 0;
        }
        return kills / deaths;
    }

    public void invitePlayer(UUID invited, UUID inviter) {
        if (hasElevatedPerms(inviter)) {
            this.invited.add(invited);
            sendAll(Bukkit.getOfflinePlayer(inviter).getName() + " has invited " + Bukkit.getOfflinePlayer(invited) + " to the guild");
        }
        GuildData.setGuild(getID(), this);
    }

    public boolean isInvited(UUID uuid) {
        return invited.contains(uuid);
    }

    public void acceptInvite(UUID uuid) {
        if (isInvited(uuid)) {
            addMember(uuid);
        } else if (Bukkit.getOfflinePlayer(uuid).isOnline()) {
            Player p = Bukkit.getPlayer(uuid);
            p.sendMessage("You were not invited to guild " + getName());
        }
        GuildData.setGuild(getID(), this);
    }

    public void deleteGuild() {
        for (UUID member : getAllMembers()) {
            resetPlayerGuild(member);
            GuildData.deleteGuild(getID());
        }
    }

    public void transferOwnership(UUID uuid) {
        final UUID previousOwner = getGuildLeader();
        this.guildLeader = uuid;
        this.guildGenerals.add(previousOwner);
        GuildData.setGuild(getID(), this);
    }

    public String getRank(UUID uuid) {
        if (getGuildLeader() == uuid) {
            return "Leader";
        } else if (getGuildGenerals().contains(uuid)) {
            return "General";
        } else {
            return "Member";
        }
    }

}
