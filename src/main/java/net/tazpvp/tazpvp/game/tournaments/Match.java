package net.tazpvp.tazpvp.game.tournaments;

import net.tazpvp.tazpvp.objects.PartyObject;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class Match {

    private final List<PartyObject> teams;
    public final List<Location> locations;

    public Match(List<PartyObject> participants) {
        this.teams = participants;
        this.locations = new ArrayList<>();
    }

    public void begin() {
        checkValidTeams();

        PartyObject team1 = teams.get(0);
        PartyObject team2 = teams.get(1);

        team1.teleportAll(locations.get(0));
        team2.teleportAll(locations.get(1));
    }

    public void end(String msg) {
        for (PartyObject partyObject : teams) {
            partyObject.sendAll(msg);
        }
    }

    private void checkValidTeams() {
        for (PartyObject partyObject : teams) {
            if (partyObject == null) {
                end("One of the teams is absent.");
            }
        }
    }
}
