package net.tazpvp.tazpvp.helpers;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.data.entity.PlayerStatEntity;
import net.tazpvp.tazpvp.game.crates.KeyFactory;
import net.tazpvp.tazpvp.enums.CC;
import net.tazpvp.tazpvp.utils.player.PlayerWrapper;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class AfkFunctions {

    public static void setup() {
        final Random random = new Random();
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


                            double probability = random.nextDouble() * 100;
                            String keyType;

                            if (probability < 70) {
                                keyType = ChatFunctions.gradient("#03fc39", "Common", true);
                                p.getInventory().addItem(KeyFactory.getFactory().createCommonKey());
                            } else if (probability < 95) {
                                keyType = ChatFunctions.gradient("#039dfc", "Rare", true);
                                p.getInventory().addItem(KeyFactory.getFactory().createRareKey());
                            } else {
                                keyType = ChatFunctions.gradient("#db3bff", "Mythic", true);
                                p.getInventory().addItem(KeyFactory.getFactory().createMythicKey());
                            }

                            p.sendMessage(
                                    ChatFunctions.gradient("#ffc70b", "AFK Rewards: \n", true) +
                                            "\n" +
                                    CC.GRAY + "+ 1 " + keyType + " Key\n" +
                                    CC.GRAY + "+ $100 Coins\n"
                            );

                            PlayerStatEntity playerStatEntity = Tazpvp.getInstance().getPlayerStatService().getOrDefault(p.getUniqueId());
                            playerStatEntity.setCoins(playerStatEntity.getCoins() + 100);
                            p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
                        }
                    }
                }
            }
        }.runTaskTimer(Tazpvp.getInstance(), 0, 20*60*10);
    }

    public static void setAfk(Player p) {
        PlayerWrapper pw = PlayerWrapper.getPlayer(p);
        if (pw == null) return;
        if (Tazpvp.afkRegion.contains(p.getLocation())) {
            if (!pw.isAfk()) {
                pw.setAfk(true);
                pw.setTimeSinceAfk(System.currentTimeMillis());
                p.sendMessage(CC.GREEN + "You are now AFK");
            }
        } else if (pw.isAfk()){
            pw.setAfk(false);
            p.sendMessage(CC.RED + "You are no longer AFK");
        }
    }
}
