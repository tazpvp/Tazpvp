package net.tazpvp.tazpvp.game.talents;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.data.entity.GuildEntity;
import net.tazpvp.tazpvp.data.entity.TalentEntity;
import net.tazpvp.tazpvp.enums.CC;
import net.tazpvp.tazpvp.utils.functions.PlayerFunctions;
import net.tazpvp.tazpvp.utils.observer.Observable;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Medic extends Observable {
    @Override
    public void death(Player victim, Player killer) {
        final PlayerWrapper killerWrapper = PlayerWrapper.getPlayer(killer);
        final TalentEntity killerTalentEntity = killerWrapper.getTalentEntity();
        final GuildEntity victimGuild = Tazpvp.getInstance().getGuildService().getGuildByPlayer(victim.getUniqueId());
        final GuildEntity killerGuild = Tazpvp.getInstance().getGuildService().getGuildByPlayer(victim.getUniqueId());

        if (killerTalentEntity.isMedic()) {
            if (killerGuild != null) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (victimGuild != null) {
                        if (victimGuild == killerGuild) {
                            if (p.getLocation().distance(victim.getLocation()) <= 5) {
                                if (p != killer) {
                                    PlayerFunctions.addHealth(p, 2);
                                    p.sendMessage(CC.DARK_GREEN + "Your guild mate healed you.");
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
