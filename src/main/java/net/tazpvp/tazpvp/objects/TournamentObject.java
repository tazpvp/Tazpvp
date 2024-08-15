package net.tazpvp.tazpvp.objects;

import lombok.Getter;
import lombok.Setter;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.enums.CC;
import net.tazpvp.tazpvp.helpers.PlayerHelper;
import net.tazpvp.tazpvp.wrappers.PlayerWrapper;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import world.ntdi.nrcore.NRCore;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class TournamentObject {

    public static TournamentObject activeTournament;

    private final List<PartyObject> participantList;
    private final int teamSizeCap;
    @Setter
    private String status;
    @Setter
    private int timeUntilBegin;

    public TournamentObject(int teamSizeCap) {
        activeTournament = this;

        this.teamSizeCap = teamSizeCap;
        this.status = "Initializing...";
        participantList = new ArrayList<>();
        timeUntilBegin = 10;

        initialize();
    }

    private void initialize() {
        //teleport players
        new BukkitRunnable() {
            @Override
            public void run() {
                begin();
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

    private void begin() {
        PartyObject.inviteList.clear();
        kitPlayers();
        handleTeleports();
    }

    public void addParticipant(PartyObject team) {
        if (team.getMembers().size() != teamSizeCap) {
            team.sendAll(CC.RED + "You cannot join this event. You are only allowed " + teamSizeCap + " total party members for this event.");
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

    public void end() {
        for (PartyObject team : participantList) {
            for (Player p : team.getOnlineMembers()) {
                PlayerWrapper pw = PlayerWrapper.getPlayer(p);
                pw.setParty(null);

            }
        }
    }

    public abstract void handleTeleports();
    public abstract void kitPlayers();

    public static void send(Player p, String msg) {
        p.sendMessage(CC.DARK_PURPLE + "Tournament ❯ " + CC.LIGHT_PURPLE + msg);
    }
}
