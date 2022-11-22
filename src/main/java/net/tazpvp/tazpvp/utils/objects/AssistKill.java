package net.tazpvp.tazpvp.utils.objects;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

public class AssistKill {
    /**
     * List of all the attackers
     */
    private Queue<UUID> attackers = new LinkedList<>();
    /**
     * The countdown until out of combat tag
     */
    private int countdown = 0; // sets to 15 - should get from config - cough rownox cough -

    /**
     * Clear both the countdown and attacker list
     */
    public void clearAll() {
        attackers.clear();
        countdown = 0;
    }

    /**
     * Get all the attackers in the list
     * @return {@code Queue<UUID>} of attackers
     */
    public Queue<UUID> getAttackers() {
        return this.attackers;
    }

    /**
     * Add an attacker to the list
     * @param uuid the UUID of the attacker
     */
    public void addAttacker(@Nonnull final UUID uuid) {
        attackers.remove(uuid);
        attackers.add(uuid);
    }

    /**
     * Get the countdown until the player is out of the combat tag
     * @return the int value
     */
    public int getCountdown() {
        return countdown;
    }

    /**
     * decrease the countdown by 1
     */
    public void decreaseCountdown() {
        if (countdown != 0) {
            this.countdown = countdown--;
        }
    }

    /**
     * Check if the countdown is over and the player is no longer in combat
     * @return
     */
    public boolean overCheck() {
        return (countdown >= 0);
    }
}
