package net.tazpvp.tazpvp.game.events;

import net.tazpvp.tazpvp.enums.CC;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;

public class EventObject {

    private final List<TeamObject> participantList;
    private final int teamSizeCap;

    public EventObject(int teamSizeCap) {
        this.teamSizeCap = teamSizeCap;
        participantList = new ArrayList<>();
    }

    private void addParticipant(TeamObject team) {
        if (team.getMembers().size() != teamSizeCap) {
            for (Player p : team.getMembers()) {
                if (p.isOnline()) {
                    p.sendMessage(CC.RED + "You cannot join this event. You are only allowed " + teamSizeCap + " total team members for this event.");
                }
            }
        } else {
            participantList.add(team);
            for (Player p : team.getMembers()) {
                if (p.isOnline()) {
                    p.sendMessage(CC.GREEN +  "You have entered the event.");
                }
            }
        }
        participantList.add(team);
    }
}
