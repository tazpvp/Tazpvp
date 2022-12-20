package net.tazpvp.tazpvp.guild;

import lombok.Getter;
import net.tazpvp.tazpvp.utils.data.DataTypes;
import net.tazpvp.tazpvp.utils.data.GuildData;
import net.tazpvp.tazpvp.utils.data.PersistentData;
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
    private UUID guild_leader;
    @Getter
    private final List<UUID> guild_generals;
    @Getter
    private final List<UUID> guild_members;
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

    public Guild(String name, UUID guild_leader) {
        this.ID = UUID.randomUUID();
        this.name = name;
        this.guild_leader = guild_leader;
        this.guild_generals = new ArrayList<>();
        this.guild_members = new ArrayList<>();
        this.invited = new ArrayList<>();
        this.kills = 0;
        this.deaths = 0;
        this.show_in_browser = true;
        this.description = "A guild";
        this.icon = Material.OAK_SIGN;

        setPlayersGuild(getGuild_leader());
    }

    public void setPlayersGuild(OfflinePlayer p) {
        setPlayersGuild(p.getUniqueId());
    }
    public void setPlayersGuild(UUID p) {
        PersistentData.setValueS(p, DataTypes.GUILD_ID.getColumnName(), getID().toString());
    }

    public void resetPlayerGuild(UUID uuid) {
        PersistentData.setValueS(uuid, DataTypes.GUILD_ID.getColumnName(), "n");
    }

    public UUID[] getAllMembers() {
        List<UUID> mem = guild_members;
        mem.addAll(guild_generals);
        mem.add(guild_leader);
        return mem.toArray(UUID[]::new);
    }

    public boolean isInGuild(UUID uuid) {
        return Arrays.stream(getAllMembers()).toList().contains(uuid);
    }

    public boolean hasElevatedPerms(UUID uuid) {
        return !guild_members.contains(uuid);
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
            guild_members.add(uuid);
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
        if (getGuild_members().contains(uuid)) {
            getGuild_members().remove(uuid);
            resetPlayerGuild(uuid);
            OfflinePlayer p = Bukkit.getOfflinePlayer(uuid);
            sendAll(p.getName() + " has left the guild!");
        } else if (getGuild_generals().contains(uuid)) {
            getGuild_generals().remove(uuid);
            resetPlayerGuild(uuid);
            OfflinePlayer p = Bukkit.getOfflinePlayer(uuid);
            sendAll(p.getName() + " has left the guild!");
        } else {
            throw new IllegalArgumentException("Cannot kick someone who is not a member or general of a guild (includes owner)");
        }
        GuildData.setGuild(getID(), this);
    }

    public void promoteMember(UUID uuid) {
        if (getGuild_members().contains(uuid)) {
            getGuild_members().remove(uuid);
            getGuild_generals().add(uuid);
            sendAll(Bukkit.getOfflinePlayer(uuid).getName() + " has been promoted to general");
        } else {
            throw new IllegalArgumentException("Cannot promote non member ranked");
        }
        GuildData.setGuild(getID(), this);
    }

    public void demoteMember(UUID uuid) {
        if (getGuild_generals().contains(uuid)) {
            guild_generals.remove(uuid);
            guild_members.add(uuid);
            sendAll(Bukkit.getOfflinePlayer(uuid).getName() + " has been demoted to member");
        } else {
            throw new IllegalArgumentException("Cannot promote non general ranked");
        }
        GuildData.setGuild(getID(), this);
    }

    public void setDescription(UUID uuid, String description) {
        if (getGuild_leader().equals(uuid)) this.description = description;
        GuildData.setGuild(getID(), this);
    }

    public void setTag(UUID uuid, String tag) {
        if (getGuild_leader().equals(uuid)) this.tag = tag;
        GuildData.setGuild(getID(), this);
    }

    public void setIcon(UUID uuid, Material icon) {
        if (getGuild_leader().equals(uuid)) this.icon = icon;
        GuildData.setGuild(getID(), this);
    }

    public void setShow_in_browser(UUID uuid, boolean show_in_browser) {
        if (getGuild_leader().equals(uuid)) this.show_in_browser = show_in_browser;
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
}
