package net.tazpvp.tazpvp.utils.objects;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

public class AssistKill {
    private Queue<UUID> attackers = new LinkedList<>();
    private int countdown = 0; // sets to 15 - should get from config - cough rownox cough -

    public void clearAll() {
        attackers.clear();
        countdown = 0;
    }

    public Queue<UUID> getAttackers() {
        return this.attackers;
    }

    public void addAttacker(@Nonnull final UUID uuid) {
        attackers.remove(uuid);
        attackers.add(uuid);
    }

    public int getCountdown() {
        return countdown;
    }

    public void decreaseCountdown() {
        if (countdown != 0) {
            this.countdown = countdown--;
        }
    }

    public boolean overCheck() {
        return (countdown >= 0);
    }
}
