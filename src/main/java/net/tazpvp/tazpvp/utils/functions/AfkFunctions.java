package net.tazpvp.tazpvp.utils.functions;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.utils.crate.KeyFactory;
import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class AfkFunctions {

    public static void setup() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    PlayerWrapper pw = PlayerWrapper.getPlayer(p);
                    if (pw == null) {
                        Bukkit.getLogger().severe("GRAHH JUST WANNA ROCK - NTDI, PLYAER NAME: " + p.getName());
                        continue;
                    }
                    if (pw.isAfk()) {
                        if ((System.currentTimeMillis() - pw.getTimeSinceAfk()) >= 1000 * 60 * 5) {
                            pw.setTimeSinceAfk(System.currentTimeMillis());
                            p.sendMessage(
                                    ChatFunctions.gradient("#ffc70b", "AFK REWARD: ", true) +
                                            ChatFunctions.gradient("#ffc70b", "1x Reward Keys", false)
                            );
                            p.getInventory().addItem(KeyFactory.getFactory().createDailyKey());
                            p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
                        }
                    }
                }
            }
        }.runTaskTimer(Tazpvp.getInstance(), 0, 20*60*5);
    }

    public static void setAfk(Player p) {
        PlayerWrapper pw = PlayerWrapper.getPlayer(p);
        if (Tazpvp.afkRegion.contains(p.getLocation())) {
            if (!pw.isAfk()) {
                pw.setAfk(true);
                pw.setTimeSinceAfk(System.currentTimeMillis());
                p.sendMessage(CC.GREEN + "You are now AFK");
            }
        } else {
            if (pw.isAfk()) {
                pw.setAfk(false);
                p.sendMessage(CC.RED + "You are no longer AFK");
            }
        }
    }
}
