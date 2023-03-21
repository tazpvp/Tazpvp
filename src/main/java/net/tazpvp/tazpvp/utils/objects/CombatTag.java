package net.tazpvp.tazpvp.utils.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.utils.enums.CC;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

public class CombatTag {

    private UUID id;
    @Getter
    private LinkedList<UUID> attackers;
    @Getter
    private float countdown;
    @Getter
    private BossBar bar;

    public CombatTag(UUID id) {
        this.id = id;
        this.attackers = new LinkedList<>();
        this.countdown = 0;
        bar = Bukkit.createBossBar("Combat Tag: 15s", BarColor.RED, BarStyle.SOLID);
        bar.setProgress(1);
        bar.addPlayer(Bukkit.getPlayer(id));
        bar.setVisible(false);
    }

    private void updateBar() {
        bar.setProgress(countdown / 15);
        bar.setTitle("Combat Tag: " + (int) countdown + "s");
    }

    public void countDown() {
        if (countdown > 0) {
            Player p = Bukkit.getPlayer(id);
            --countdown;
            updateBar();
            if (countdown <= 0) {
                endCombat(null, true);
            }
        }
    }

    public void setTimer(@Nullable UUID attacker) {
        if (attacker != null) {
            if (!attackers.contains(attacker)) {
                attackers.add(attacker);

                Bukkit.getPlayer(id).sendMessage(CC.RED + "You are now in combat with " + CC.BOLD + Bukkit.getPlayer(attacker).getName());
            }
        }
        if (countdown <= 0) {
            if (attacker == null) {
                Bukkit.getPlayer(id).sendMessage(CC.RED + "You are now in combat.");
            }
            countdown = 15;
            updateBar();
            bar.setVisible(true);
        } else {
            countdown = 15;
            updateBar();
        }
    }

    public void endCombat(@Nullable UUID attacker, boolean msg) {
        if (attacker == null) {
            Player p = Bukkit.getPlayer(id);
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
