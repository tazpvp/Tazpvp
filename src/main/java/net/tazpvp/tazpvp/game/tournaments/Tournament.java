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
    private Bracket currentBracket;
    private int BRACKET_NUM;

    public Tournament(UUID host) {
        this.host = host;
        this.BRACKET_NUM = 0;
        initialize();
    }

    public void initialize() {
        generateBracket();

        new BukkitRunnable() {
            @Override
            public void run() {
                intermission();
            }
        }.runTaskLater(Tazpvp.getInstance(), 20*60);
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
        currentBracket.currentMatch.end("Match ended");
    }

    public void nextMatch() {
        BRACKET_NUM++;
        currentBracket.currentMatch.begin();
    }

    public void generateBracket() {
        int teamCount = participants.size();
        List<PartyObject> bracketParticipants = participants;
        List<Match> matches = new ArrayList<>();
        if (teamCount % 2 != 0) {
            List<PartyObject> contestants = List.of(
                    bracketParticipants.getFirst(),
                    bracketParticipants.get(1)
            );

            bracketParticipants.remove(contestants.getFirst());
            matches.add(new Match(contestants));
        }
        for (int i = 0 ; i < bracketParticipants.size() ; i++) {
            List<PartyObject> contestants = List.of(
                bracketParticipants.get(i),
                bracketParticipants.get(i+1)
            );
            bracketParticipants.remove(contestants.getFirst());
            bracketParticipants.remove(contestants.get(1));
            matches.add(new Match(contestants));
        }
        brackets = new Bracket(matches);
    }
}
