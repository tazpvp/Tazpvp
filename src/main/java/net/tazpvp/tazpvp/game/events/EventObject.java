package net.tazpvp.tazpvp.game.events;

import lombok.Getter;
import lombok.Setter;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.enums.CC;
import net.tazpvp.tazpvp.helpers.PlayerHelper;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import world.ntdi.nrcore.NRCore;

import java.util.ArrayList;
import java.util.List;

@Getter
public class EventObject {

    public final static List<EventObject> activeEvents = new ArrayList<>();

    private final List<TeamObject> participantList;
    private final int teamSizeCap;
    @Setter
    private String status;
    @Setter
    private int timeUntilBegin;

    public EventObject(int teamSizeCap) {
        this.teamSizeCap = teamSizeCap;
        this.status = "Initializing...";
        participantList = new ArrayList<>();
        activeEvents.add(this);
        timeUntilBegin = 10;
    }

    private void initialize() {
        //teleport players
        new BukkitRunnable() {
            @Override
            public void run() {
                //start event
            }
        }.runTaskLater(Tazpvp.getInstance(), 20L * 60 * timeUntilBegin);

        new BukkitRunnable() {
            @Override
            public void run() {
                setTimeUntilBegin(timeUntilBegin - 1);
                setStatus(timeUntilBegin + " minutes until the event begins.");
            }
        }.runTaskTimer(Tazpvp.getInstance(), 20*30, 20*60);
    }

    private void addParticipant(TeamObject team) {
        if (team.getMembers().size() != teamSizeCap) {
            team.sendAll(CC.RED + "You cannot join this event. You are only allowed " + teamSizeCap + " total team members for this event.");
        } else {
            participantList.add(team);
            List<Player> members = team.getOnlineMembers();
            team.sendAll(CC.GREEN +  "You have entered the event.");
            for (Player p : members) {
                PlayerHelper.teleport(p, NRCore.config.spawn);
            }
        }
        participantList.add(team);
    }
}
