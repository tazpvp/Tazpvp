package net.tazpvp.tazpvp.game.tournaments;

import lombok.Getter;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.helpers.CombatTagHelper;
import net.tazpvp.tazpvp.objects.DeathObject;
import net.tazpvp.tazpvp.objects.PartyObject;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import world.ntdi.nrcore.utils.world.WorldUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Tournament {
    private final UUID host;
    public final List<PartyObject> participants = new ArrayList<>();
    public final List<Player> spectators = new ArrayList<>();
    private Bracket currentBracket;
    public static final World world = new WorldUtil().cloneWorld("tournamentMap", "tournament_" + UUID.randomUUID());

    public Tournament(UUID host) {
        this.host = host;
        initialize();
    }

    public void initialize() {
        generateBracket();
        preparePlayers();

        new BukkitRunnable() {
            @Override
            public void run() {
                intermission();
            }
        }.runTaskLater(Tazpvp.getInstance(), 20*60);
    }

    public void intermission() {
        currentBracket.endCurrentMatch();
        new BukkitRunnable() {

            @Override
            public void run() {
                currentBracket.nextMatch();
            }
        }.runTaskLater(Tazpvp.getInstance(), 20*5);
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
        currentBracket = new Bracket(matches);
    }

    public void preparePlayers() {
        for (PartyObject party : participants) {
            for (Player op : party.getOnlineMembers()) {
                final UUID lastAttacker = CombatTagHelper.getLastAttacker(op.getUniqueId());
                if (lastAttacker != null) {
                    new DeathObject(op.getUniqueId(), lastAttacker);
                } else {
                    new DeathObject(op.getUniqueId(), null);
                }
            }
        }
    }
}
