package net.tazpvp.tazpvp.objects;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.data.LooseData;
import net.tazpvp.tazpvp.data.entity.GuildEntity;
import net.tazpvp.tazpvp.data.entity.KitEntity;
import net.tazpvp.tazpvp.data.implementations.KitServiceImpl;
import net.tazpvp.tazpvp.data.services.GuildService;
import net.tazpvp.tazpvp.data.services.KitService;
import net.tazpvp.tazpvp.enums.CC;
import net.tazpvp.tazpvp.enums.ItemEnum;
import net.tazpvp.tazpvp.enums.ScoreboardEnum;
import net.tazpvp.tazpvp.enums.StatEnum;
import net.tazpvp.tazpvp.game.booster.ActiveBoosterManager;
import net.tazpvp.tazpvp.game.booster.BoosterBonus;
import net.tazpvp.tazpvp.game.booster.BoosterTypes;
import net.tazpvp.tazpvp.helpers.PlayerHelper;
import net.tazpvp.tazpvp.helpers.ScoreboardHelper;
import net.tazpvp.tazpvp.helpers.SerializableInventory;
import net.tazpvp.tazpvp.wrappers.PlayerWrapper;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import world.ntdi.nrcore.NRCore;
import world.ntdi.nrcore.utils.item.builders.SkullBuilder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class DeathObject {

    private final UUID victim;
    private final UUID killer;
    private final Player pVictim;
    private Player pKiller;
    private final Location location;

    private final GuildService guildService;
    private final GuildEntity victimGuild;
    private final GuildEntity killerGuild;
    private final PlayerWrapper killerWrapper;
    private final PlayerWrapper victimWrapper;

    public DeathObject(UUID victim, @Nullable UUID killer) {
        this.guildService = Tazpvp.getInstance().getGuildService();
        this.victim = victim;
        this.pVictim = Bukkit.getPlayer(victim);
        if (pVictim != null) {
            this.location = pVictim.getLocation();
        } else {
            this.location = null;
        }
        this.killer = killer;
        if (killer == null) {
            this.killerWrapper = null;
            this.killerGuild = null;
        } else {
            this.killerGuild = Tazpvp.getInstance().getGuildService().getGuildByPlayer(killer);
            this.pKiller = Bukkit.getPlayer(killer);
            this.killerWrapper = PlayerWrapper.getPlayer(killer);
            Tazpvp.getObservers().forEach(observer -> observer.death(pVictim, pKiller));
        }
        this.victimWrapper = PlayerWrapper.getPlayer(victim);
        this.victimGuild = Tazpvp.getInstance().getGuildService().getGuildByPlayer(victim);

        initialize();
    }

    private void initialize() {

        if (victimWrapper.getDuel() != null) {
            victimWrapper.getDuel().end(victim);
            return;
        }

        if (killer != null) {
            if (!killer.equals(victim)) {
                playEffects();
                dropItems();
                dropHead();
                PlayerInventoryStorage.updateStorage(victim, killer);
                CombatObject.tags.get(killer).endCombat(victim, false);
                Tazpvp.getObservers().forEach(observer -> observer.death(pVictim, pKiller));
            }
        }

        deathMessage();
        respawn();
        updateStats();
        pVictim.getInventory().clear();

        final KitService kitService = new KitServiceImpl();
        final KitEntity kitEntity = kitService.getOrDefault(pVictim.getUniqueId());

        final String kitSerial = kitEntity.getSerial();
        if (kitSerial == null || kitSerial.isEmpty()) {
            PlayerHelper.kitPlayer(pVictim);
        } else {
            SerializableInventory serializableInventory = SerializableInventory.convertFromString(kitSerial);
            serializableInventory.addItems(pVictim.getInventory(), PlayerHelper.getKitItems(pVictim));

            PlayerHelper.armorPlayer(pVictim);
        }

        PlayerHelper.resetHealth(pVictim);
        PlayerHelper.feedPlr(pVictim);
    }

    public void dropItems() {
        if (killer == victim) return;

        if (victimGuild != null && killerGuild != null) {
            if (victimGuild == killerGuild) return;
        }

        World world = location.getWorld();

        if (world != null) {
            if (killerWrapper.getTalentEntity().isNecromancer()) {
                world.dropItemNaturally(location.add(0, 2, 0), ItemEnum.getRandomDrop().getItem(1));
            }
            world.dropItemNaturally(location.add(0, 1, 0), ItemEnum.getRandomDrop().getItem(1));
        }
        pKiller.getInventory().addItem(ItemEnum.GOLDEN_APPLE.getItem(1));
    }

    public void dropHead() {
        final Random r = new Random();

        if (pVictim.getName().startsWith(".")) {
            return;
        }

        if (killerWrapper.getTalentEntity().isHarvester()) {
            if (r.nextInt(0, 100) > 1) return;
        } else {
            if (r.nextInt(0, 200) > 1) return;
        }
        World w = location.getWorld();
        ItemStack skull = makeSkull(pVictim);
        if (skull == null || w == null) return;
        w.dropItemNaturally(location.add(0, 1, 0), skull);
    }

    private ItemStack makeSkull(@Nonnull final Player p) {
        return SkullBuilder.of(1, p.getName()).setHeadTexture(p).build();
    }

    public void playEffects() {

        if (victimWrapper.getRank().getHierarchy() >= 1) {
            final String particle = victimWrapper.getUserRankEntity().getDeathParticle();

            if (particle != null && location.getWorld() != null) {
                location.getWorld().spawnParticle(Particle.valueOf(particle), location, 6);
            }
        }

        if (pKiller != null) {
            pKiller.playSound(pKiller.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
        }
    }

    public void respawn() {
        SpectateObject spectateObject;
        Location camLoc = location;

        if (killer != null) {
            camLoc = pKiller.getLocation();
        }

        spectateObject = new SpectateObject(camLoc);
        PlayerHelper.teleport(pVictim, spectateObject.getResult());
        spectateObject.faceLocation(pVictim);
        pVictim.setGameMode(GameMode.SPECTATOR);
        pVictim.playSound(pVictim.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
        pVictim.sendTitle(CC.RED + "" + CC.BOLD + "YOU DIED", CC.GOLD + "Respawning...", 5, 50, 5);
        victimWrapper.setRespawning(true);
        new BukkitRunnable() {
            public void run() {
                pVictim.setGameMode(GameMode.SURVIVAL);
                PlayerHelper.teleport(pVictim, NRCore.config.spawn);
                victimWrapper.setRespawning(false);
            }
        }.runTaskLater(Tazpvp.getInstance(), 20 * 3);
    }

    public void deathMessage() {
        final String prefix = CC.GRAY + "[" + CC.DARK_RED + "☠" + CC.GRAY + "] " + CC.DARK_GRAY;

        for (Player op : Bukkit.getOnlinePlayers()) {
            if (victim == killer || killer == null) {
                final String who = (op == pVictim) ? "You" : CC.GRAY + pVictim.getName();
                String msg = prefix + who + CC.DARK_GRAY + " died.";

                op.sendMessage(msg);
            } else {
                final String who = (op == pKiller) ? "You" : CC.GRAY + pKiller.getName();
                final String died = (op == pVictim) ? "you" : CC.GRAY + pVictim.getName();
                String msg = prefix + who + CC.DARK_GRAY + " killed " + died;

                op.sendMessage(msg);
            }
        }
    }

    public void updateStats() {
        updateVictimStats();

        if (victimGuild != null && killerGuild != null) {
            if (victimGuild == killerGuild) return;
        }
        updateKillerStats();

        CombatObject tag = CombatObject.tags.get(victim);
        tag.getAttackers().remove(killer);

        for (UUID uuid : tag.getAttackers()) {
            Player assistant = Bukkit.getPlayer(uuid);
            if (assistant == null) continue;

            int finalXp = 5;
            int finalCoins = 5;

            assistant.sendMessage(
                    CC.DARK_GRAY + "Assist kill:" + CC.GRAY + pVictim.getName() +
                            CC.DARK_AQUA + "Exp: " + CC.AQUA + finalXp + CC.GOLD + " Coins: " + CC.YELLOW + finalCoins
            );

            StatEnum.COINS.add(uuid, finalCoins);
            StatEnum.XP.add(uuid, finalXp);
            StatEnum.MMR.add(uuid, 5);
            PlayerHelper.levelUp(uuid);
        }
        CombatObject.tags.get(victim).endCombat(null, false);
    }

    private void updateKillerStats() {
        if (killer != null) {
            if (killer == victim) return;

            int XP = 15;
            int COINS = 25;
            final BoosterBonus XP_NETWORK_BUFF = ActiveBoosterManager.getInstance()
                    .calculateBonus(XP, List.of(BoosterTypes.XP, BoosterTypes.MEGA));
            final BoosterBonus COIN_NETWORK_BUFF = ActiveBoosterManager.getInstance()
                    .calculateBonus(COINS, List.of(BoosterTypes.COINS, BoosterTypes.MEGA));
            int XP_OTHER_BUFF = otherBuffs(killer, XP);
            int COIN_OTHER_BUFF =  otherBuffs(killer, COINS);

            final int bountyReward = LooseData.getKs(victim) * 10;

            int finalXp = (int) XP_NETWORK_BUFF.result() + XP_OTHER_BUFF;
            int finalCoins = (int) COIN_NETWORK_BUFF.result() + COIN_OTHER_BUFF + bountyReward;

            StatEnum.KILLS.add(killer, 1);
            LooseData.addKs(killer);
            StatEnum.COINS.add(killer, finalCoins);
            StatEnum.XP.add(killer, finalXp);
            StatEnum.MMR.add(killer, 15);

            int kills = StatEnum.KILLS.getInt(killer);
            int deaths = StatEnum.DEATHS.getInt(killer);

            ScoreboardHelper.updateSuffix(pKiller, ScoreboardEnum.KDR, LooseData.kdrFormula(kills, deaths) + "");

            if ((LooseData.getKs(killer) % 5) == 0) {
                Bukkit.broadcastMessage(
                        CC.GOLD + pKiller.getName() + CC.YELLOW + " has a kill streak of " +
                                CC.GOLD + LooseData.getKs(killer) + "\n" +
                                CC.GOLD + "Bounty: " + CC.YELLOW + "$" + (LooseData.getKs(killer) * 10)
                );
            }

            if (killerGuild != null) {
                killerGuild.setKills(killerGuild.getKills() + 1);
                guildService.saveGuild(killerGuild);
            }

            pKiller.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(
                    CC.DARK_AQUA.toString() + CC.BOLD + "Exp: " + CC.AQUA + CC.BOLD + XP + " " + CC.DARK_AQUA + XP_NETWORK_BUFF.prettyPercentMultiplier() +
                    CC.GOLD + CC.BOLD + " Coins: " + CC.YELLOW + CC.BOLD + COINS + " " + CC.GOLD + COIN_NETWORK_BUFF.prettyPercentMultiplier()
            ));
            if (bountyReward > 0) {
                pKiller.sendMessage(CC.YELLOW + "You collected " + pVictim.getName() + "'s " + CC.GOLD + "$" + bountyReward + CC.YELLOW + " bounty.");
            }

            Tazpvp.getInstance().getPlayerNameTagService().setTagRank(pKiller);
            PlayerHelper.levelUp(killer);
        }
    }

    private void updateVictimStats() {
        if (victimGuild != null) {
            victimGuild.setDeaths(victimGuild.getDeaths() + 1);
        }

        StatEnum.DEATHS.add(victim, 1);
        if (StatEnum.MMR.getInt(victim) >= 10) {
            StatEnum.MMR.remove(victim, 10);
        } else {
            StatEnum.MMR.set(victim, 0);
        }
        LooseData.resetKs(victim);

        Tazpvp.getInstance().getPlayerNameTagService().setTagRank(pVictim);
        guildService.saveGuild(victimGuild);
    }

    private int otherBuffs(UUID id, int stat) {
        int finalStat = 0;

        if (StatEnum.PRESTIGE.getInt(id) > 0) {
            finalStat = (StatEnum.PRESTIGE.getInt(id) * stat);
        }

        return finalStat;
    }
}
