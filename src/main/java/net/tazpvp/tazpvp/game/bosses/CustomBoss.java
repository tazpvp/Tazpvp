package net.tazpvp.tazpvp.game.bosses;

import lombok.Getter;
import net.tazpvp.tazpvp.game.bosses.attacks.Attack;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class CustomBoss {
    @Getter
    protected Entity boss;
    @Getter
    private final Location spawnLocation;
    private final List<Attack> attacks;
    private final Random random;

    protected CustomBoss(final Entity boss, final Location p_spawnLocation) {
        this.boss = boss;
        this.spawnLocation = p_spawnLocation;
        this.attacks = new ArrayList<>();
        this.random = new Random();
    }

    protected void addAttack(Attack attack) {
        attacks.add(attack);
    }

    protected final Attack getRandomAttack() {
        return attacks.get(random.nextInt(attacks.size()));
    }

    protected abstract void spawn();
    protected abstract void setup();
    protected abstract void despawn();
}
