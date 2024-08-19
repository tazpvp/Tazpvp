package net.tazpvp.tazpvp.game.tournaments;

import net.tazpvp.tazpvp.objects.PartyObject;

import java.util.List;

public class Match {

    private List<PartyObject> teams;


    public Match(List<PartyObject> participants) {

        this.teams = participants;
    }


    public void begin() {
        for (PartyObject partyObject : teams) {
            if (partyObject == null) {
                end("One of the teams is absent.");
            }
        }
    }

    public void end(String msg) {

    }
}
