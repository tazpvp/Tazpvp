package net.tazpvp.tazpvp.game.events;

import lombok.Getter;
import net.tazpvp.tazpvp.wrappers.PlayerWrapper;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class TeamObject {

    private final UUID leader;
    private final List<UUID> members;

    public TeamObject(UUID leader) {
        this.leader = leader;
        members = new ArrayList<>();
        members.add(leader);
        PlayerWrapper playerWrapper = PlayerWrapper.getPlayer(leader);
        playerWrapper.setEventTeam(this);
    }

    private void addMember(Player p) {
        members.add(p.getUniqueId());
        PlayerWrapper playerWrapper = PlayerWrapper.getPlayer(p);
        playerWrapper.setEventTeam(this);
        p.sendMessage("You have joined " + getMember(leader).getName() + "'s event team.");
    }

    private void invitePlayer(Player target) {
        target.sendMessage(getMember(leader).getName() + " has invited you to their event team.");
    }

    public void sendAll(String message) {
        for (Player p : getOnlineMembers()) {
            p.sendMessage(message);
        }
    }

    public List<Player> getOnlineMembers() {
        List<Player> online = new ArrayList<>();
        for (OfflinePlayer p : getMembers()) {
            if (p.isOnline()) {
                online.add(p.getPlayer());
            }
        }
        return online;
    }

    public boolean checkMembersOnline() {
        for (OfflinePlayer p : getMembers()) {
            if (p == null) return false;
            if (!p.isOnline()) return false;
        }
        return true;
    }

    public List<OfflinePlayer> getMembers() {
        List<OfflinePlayer> offlineMembers = new ArrayList<>();
        for (UUID id: members) {
            OfflinePlayer p = Bukkit.getOfflinePlayer(id);
            offlineMembers.add(p);
        }
        return offlineMembers;
    }

    public OfflinePlayer getMember(UUID id) {
        return Bukkit.getPlayer(id);
    }

    private void disband() {
        members.clear();
    }
}
