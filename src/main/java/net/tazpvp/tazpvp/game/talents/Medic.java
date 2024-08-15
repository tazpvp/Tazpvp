package net.tazpvp.tazpvp.game.talents;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.data.entity.GuildEntity;
import net.tazpvp.tazpvp.data.entity.TalentEntity;
import net.tazpvp.tazpvp.data.services.GuildService;
import net.tazpvp.tazpvp.enums.CC;
import net.tazpvp.tazpvp.helpers.PlayerHelper;
import net.tazpvp.tazpvp.utils.observer.Observable;
import net.tazpvp.tazpvp.wrappers.PlayerWrapper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Medic extends Observable {
    @Override
    public void death(Player victim, Player killer) {
        final PlayerWrapper killerWrapper = PlayerWrapper.getPlayer(killer);
        final TalentEntity killerTalentEntity = killerWrapper.getTalentEntity();
        final GuildService guildService = Tazpvp.getInstance().getGuildService();
        final GuildEntity killerGuild = guildService.getGuildByPlayer(victim.getUniqueId());

        if (killerTalentEntity.isMedic()) {
            if (killerGuild == null) return;
            final GuildEntity victimGuild = guildService.getGuildByPlayer(victim.getUniqueId());
            if (victimGuild.equals(killerGuild)) return;
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (killer.getLocation().distance(p.getLocation()) <= 5) {
                    if (p != killer) {
                        final GuildEntity pGuild = guildService.getGuildByPlayer(p.getUniqueId());
                        if (pGuild.equals(killerGuild)) {
                            PlayerHelper.addHealth(p, 2);
                            p.sendMessage(CC.DARK_GREEN + "Your guild mate healed you.");
                        }
                    }
                }
            }
        }
    }
}
