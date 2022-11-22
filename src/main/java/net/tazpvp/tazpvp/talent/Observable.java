package net.tazpvp.tazpvp.talent;

import net.tazpvp.tazpvp.Tazpvp;

public abstract class Observable implements Observer {
    public Observable() {
        Tazpvp.registerObserver(this);
    }
}
