package net.tazpvp.tazpvp.objects;

import lombok.Getter;
import net.tazpvp.tazpvp.enums.CC;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.UUID;
import java.util.WeakHashMap;

public class CombatObject {

    private final Player p;
    @Getter
    private final LinkedList<UUID> attackers;
    @Getter
    private float countdown;
    @Getter
    private final BossBar bar;
    public final static WeakHashMap<UUID, CombatObject> tags = new WeakHashMap<>();

    public CombatObject(UUID id) {
        this.attackers = new LinkedList<>();
        this.countdown = 0;
        this.bar = Bukkit.createBossBar("Combat Tag: 15s", BarColor.RED, BarStyle.SOLID);
        this.p = Bukkit.getPlayer(id);
        initialize();
    }

    private void initialize() {
        bar.setProgress(1);
        if (p != null) {
            bar.addPlayer(p);
        }
        bar.setVisible(false);
    }

    private void updateBar() {
        bar.setProgress(countdown / 15);
        bar.setTitle("Combat Tag: " + (int) countdown + "s");
    }

    public void countDown() {
        if (countdown > 0) {
            --countdown;
            updateBar();
            if (countdown <= 0) {
                endCombat(null, true);
            }
        }
    }

    public void setTimer(@Nullable UUID opponent) {
        boolean inCombat = false;

        if (opponent != null && !attackers.contains(opponent)) {
            attackers.add(opponent);
            Player pOpponent = Bukkit.getPlayer(opponent);
            if (pOpponent != null) {
                p.sendMessage(CC.RED + "You are now in combat with " + CC.BOLD + pOpponent.getName());
                inCombat = true;
            }
        }
        if (countdown <= 0) {
            if (!inCombat) {
                p.sendMessage(CC.RED + "You are now in combat.");
            }
            bar.setVisible(true);
        }
        countdown = 15;
        updateBar();
    }

    public void endCombat(@Nullable UUID attacker, boolean msg) {
        if (attacker == null) {
            countdown = 0;
            bar.setVisible(false);
            attackers.clear();
            if (msg) {
                p.sendMessage(CC.GREEN +"You are no longer in combat.");
            }
        } else {
            attackers.remove(attacker);
        }
    }
}
