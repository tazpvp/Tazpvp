package net.tazpvp.tazpvp.game.tournaments;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.objects.PartyObject;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Tournament {
    private final UUID host;
    private final List<PartyObject> participants = new ArrayList<>();
    private final List<Player> spectators = new ArrayList<>();
    private final List<Match> bracket = new ArrayList<>();
    private final int MATCH_NUMBER;

    public Tournament(UUID host) {
        this.host = host;
        this.MATCH_NUMBER = 0;
        initialize();
    }

    public void initialize() {

    }

    public void intermission() {
        endCurrentMatch();
        new BukkitRunnable() {

            @Override
            public void run() {
                nextMatch();
            }
        }.runTaskLater(Tazpvp.getInstance(), 20*5);
    }

    public void endCurrentMatch() {
        bracket.get(MATCH_NUMBER).end("Match ended");
    }

    public void nextMatch() {
        bracket.get(MATCH_NUMBER).begin();
    }

    public void createBracket() {
        int teamCount = participants.size();
        if (teamCount % 2 != 0) {
        }
    }

}
