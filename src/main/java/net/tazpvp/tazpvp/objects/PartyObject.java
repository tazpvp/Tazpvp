package net.tazpvp.tazpvp.objects;

import lombok.Getter;
import lombok.Setter;
import net.tazpvp.tazpvp.enums.CC;
import net.tazpvp.tazpvp.wrappers.PlayerWrapper;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.WeakHashMap;

@Getter
public class PartyObject {

    public static final WeakHashMap<UUID, PartyObject> inviteList = new WeakHashMap<>();

    private final UUID leader;
    private final List<UUID> members;

    public PartyObject(UUID leader) {
        this.leader = leader;
        members = new ArrayList<>();
        members.add(leader);
        PlayerWrapper playerWrapper = PlayerWrapper.getPlayer(leader);
        playerWrapper.setParty(this);
    }

    public void addMember(Player p) {
        members.add(p.getUniqueId());
        PlayerWrapper playerWrapper = PlayerWrapper.getPlayer(p);
        playerWrapper.setParty(this);
        send(p, "You have joined " + getMember(leader).getName() + "'s party.");
    }

    public void invitePlayer(Player target) {
        send(target, getMember(leader).getName() + " has invited you to their party. Type /party join <user> to accept.");
        inviteList.put(target.getUniqueId(), this);
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

    public void disband() {
        for (Player p : getOnlineMembers()) {
            PlayerWrapper pw = PlayerWrapper.getPlayer(p);
            pw.setParty(null);
        }
        members.clear();
    }

    public static void send(Player p, String msg) {
        p.sendMessage(CC.BLUE + "Party ❯ " + CC.WHITE + msg);
    }
}
