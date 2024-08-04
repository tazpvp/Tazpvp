package net.tazpvp.tazpvp.game.events;

import lombok.Getter;
import net.tazpvp.tazpvp.wrappers.PlayerWrapper;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Getter
public class TeamObject {

    private final Player leader;
    private final List<Player> members;

    public TeamObject(Player leader) {
        this.leader = leader;
        members = new ArrayList<>();
        members.add(leader);
        PlayerWrapper playerWrapper = PlayerWrapper.getPlayer(leader);
        playerWrapper.setEventTeam(this);
    }

    private void addMember(Player p) {
        members.add(p);
        PlayerWrapper playerWrapper = PlayerWrapper.getPlayer(p);
        playerWrapper.setEventTeam(this);
        p.sendMessage("You have joined " + leader.getName() + "'s event team.");
    }

    private void invitePlayer(Player target) {
        target.sendMessage(leader.getName() + " has invited you to their event team.");
    }

    private void disband() {
        members.clear();
    }
}
