package net.tazpvp.tazpvp.game.events.events;

public interface GameEvent {
    void registerListeners();
    void beginEvent();
    void cleanupListeners();

    
}
