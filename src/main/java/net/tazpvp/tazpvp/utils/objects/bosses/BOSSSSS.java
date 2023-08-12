package net.tazpvp.tazpvp.utils.objects.bosses;

import lombok.Getter;
import net.tazpvp.tazpvp.utils.objects.bosses.attacks.Attack;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class BOSSSSS {
    @Getter
    private final Entity boss;
    private final List<Attack> attacks;
    private final Random random;

    protected BOSSSSS(Entity boss) {
        this.boss = boss;
        this.attacks = new ArrayList<>();
        this.random = new Random();
    }

    protected void addAttack(Attack attack) {
        attacks.add(attack);
    }

    protected final Attack getRandomAttack() {
        return attacks.get(random.nextInt(attacks.size()));
    }

    protected abstract void setup();
}
