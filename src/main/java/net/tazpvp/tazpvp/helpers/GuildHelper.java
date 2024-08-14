package net.tazpvp.tazpvp.helpers;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.data.entity.GuildEntity;
import net.tazpvp.tazpvp.data.services.GuildService;
import net.tazpvp.tazpvp.enums.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;
import java.util.WeakHashMap;

public class GuildHelper {

    private static final GuildService guildService = Tazpvp.getInstance().getGuildService();
    public static WeakHashMap<UUID, GuildEntity> inviteList = new WeakHashMap<>();

    public static void invitePlayer(UUID target, GuildEntity guildEntity) {
        inviteList.put(target, guildEntity);
        Player pTarget = Bukkit.getPlayer(target);
        if (pTarget != null) {
            pTarget.sendMessage(CC.YELLOW + "You have been invited to a guild: " + CC.GOLD + guildEntity.getName());
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!guildService.isInGuild(target, guildEntity)) {
                    if (pTarget != null) {
                        pTarget.sendMessage(CC.YELLOW + "The guild invite to " + guildEntity.getName() + " has expired.");
                    }
                    inviteList.remove(target);
                }
            }
        }.runTaskLater(Tazpvp.getInstance(), 20*30);
    }
}
