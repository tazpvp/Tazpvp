package net.tazpvp.tazpvp.game.tournaments;

import net.tazpvp.tazpvp.objects.PartyObject;
import org.bukkit.Location;

import java.util.List;

public class Match {

    private List<PartyObject> teams;
    public List<Location> locations;

    public Match(List<PartyObject> participants) {
        this.teams = participants;
    }

    public void begin() {
        checkValidTeams();

        PartyObject team1 = teams.get(0);
        PartyObject team2 = teams.get(1);

        team1.teleportAll(locations.get(0));
        team2.teleportAll(locations.get(1));
    }

    public void end(String msg) {

    }

    private void checkValidTeams() {
        for (PartyObject partyObject : teams) {
            if (partyObject == null) {
                end("One of the teams is absent.");
            }
        }
    }
}
