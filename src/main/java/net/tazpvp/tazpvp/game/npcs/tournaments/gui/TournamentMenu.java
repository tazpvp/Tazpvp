package net.tazpvp.tazpvp.game.npcs.tournaments.gui;

import net.tazpvp.tazpvp.enums.CC;
import net.tazpvp.tazpvp.game.tournaments.Tournament;
import net.tazpvp.tazpvp.game.tournaments.TournamentHelper;
import net.tazpvp.tazpvp.wrappers.PlayerWrapper;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.gui.Button;
import world.ntdi.nrcore.utils.gui.GUI;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

public class TournamentMenu extends GUI {

    public TournamentMenu(Player p) {
        super("Tournament Menu", 3);
        addItems(p);
        open(p);
    }

    private void addItems(Player p) {
        fill(0, 3*9, ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE, 1).name(" ").build());

        String state;
        if (TournamentHelper.currentTournament != null) {
            state = TournamentHelper.currentTournament.getState();
        } else {
            state = "No Upcoming Tournaments";
        }

        addButton(Button.create(ItemBuilder.of(Material.ENDER_EYE, 1)
                .name(CC.GREEN + "" + CC.BOLD + "TOURNAMENT")
                .lore(state)
                .glow(true)
                .build(), (_) ->
        {
            Tournament tournament = TournamentHelper.currentTournament;
            if (tournament != null) {
                int sizeCap = tournament.getTeamSizeCap();
                PlayerWrapper pw = PlayerWrapper.getPlayer(p);
                if (pw.getParty() != null) {
                    tournament.addParty(pw.getParty());
                } else {
                    p.sendMessage(Tournament.prefix + "You need to be in a party to join this tournament.");
                    p.sendMessage(Tournament.prefix + "Maximum Party Size: " + sizeCap);
                }
            }
        }), 11);

        update();
    }
}
