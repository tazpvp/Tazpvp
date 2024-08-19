package net.tazpvp.tazpvp.game.tournaments;

import java.util.List;

public class Bracket {

    public Match currentMatch;
    public final List<Match> matches;

    public Bracket(List<Match> matches) {
        this.matches = matches;
    }
}
