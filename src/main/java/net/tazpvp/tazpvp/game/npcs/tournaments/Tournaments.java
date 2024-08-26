package net.tazpvp.tazpvp.game.npcs.tournaments;

import net.tazpvp.tazpvp.game.npcs.NPC;
import net.tazpvp.tazpvp.game.npcs.tournaments.gui.Browser;
import net.tazpvp.tazpvp.helpers.ChatHelper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.jetbrains.annotations.NotNull;

public class Tournaments extends NPC {

    public Tournaments() {
        super(ChatHelper.gradient("#fc6400", "TOURNAMENT", true),
                new Location(Bukkit.getWorld("arena"), -5, 95, 5, -135, 0),
                Villager.Profession.FARMER,
                Villager.Type.SAVANNA,
                Sound.ITEM_GOAT_HORN_SOUND_0);
    }

    @Override
    public void interact(@NotNull PlayerInteractAtEntityEvent e, @NotNull Player p) {
        new Browser();
    }
}
