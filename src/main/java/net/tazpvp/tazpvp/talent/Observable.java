package net.tazpvp.tazpvp.talent;

import net.tazpvp.tazpvp.Tazpvp;

public abstract class Observable implements Observer {
    /**
     * Register the observer to the list
     */
    public Observable() {
        Tazpvp.registerObserver(this);
    }
}
