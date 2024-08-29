package net.tazpvp.tazpvp.game.tournaments;

import org.bukkit.Location;

import java.util.HashMap;
import java.util.List;

public class Bracket {

    public final List<Match> matches;
    public final HashMap<Integer, List<Location>> spawnLocations = new HashMap<>();

    public Bracket(List<Match> matches) {
        this.matches = matches;
        initLocations();
    }

    public void beginMatches() {
        for (Match match : matches) {
            match.begin();
        }
    }

    public void endMatches() {
        for (Match match : matches) {
            match.end("Match ended");
        }
    }

    private void initLocations() {
        int x = 0;
        int y = 100;
        int z = 0;
        int i = 0;
        for (Match match : matches) {
            match.locations.add(new Location(Tournament.world, x + (100 * i), y, z));
            match.locations.add(new Location(Tournament.world, x + (100 * i), y, z + 50));
            i++;
        }
    }
}
