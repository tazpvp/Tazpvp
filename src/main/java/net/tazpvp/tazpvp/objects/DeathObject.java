package net.tazpvp.tazpvp.objects;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.data.LooseData;
import net.tazpvp.tazpvp.data.entity.GuildEntity;
import net.tazpvp.tazpvp.data.entity.KitEntity;
import net.tazpvp.tazpvp.data.entity.PlayerStatEntity;
import net.tazpvp.tazpvp.data.implementations.KitServiceImpl;
import net.tazpvp.tazpvp.data.services.GuildService;
import net.tazpvp.tazpvp.data.services.KitService;
import net.tazpvp.tazpvp.data.services.PlayerStatService;
import net.tazpvp.tazpvp.enums.ItemEnum;
import net.tazpvp.tazpvp.game.booster.ActiveBoosterManager;
import net.tazpvp.tazpvp.game.booster.BoosterBonus;
import net.tazpvp.tazpvp.game.booster.BoosterTypes;
import net.tazpvp.tazpvp.enums.CC;
import net.tazpvp.tazpvp.helpers.CombatTagHelper;
import net.tazpvp.tazpvp.helpers.PlayerHelper;
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
    private final Random r = new Random();
    private final PlayerStatEntity killerStatEntity;
    private final PlayerStatEntity victimStatEntity;
    private final PlayerStatService playerStatService;
    private final GuildEntity victimGuild;
    private final GuildEntity killerGuild;
    private final GuildService guildService;
    private final PlayerWrapper killerWrapper;
    private final PlayerWrapper victimWrapper;

    public DeathObject(UUID victim, @Nullable UUID killer) {
        this.playerStatService = Tazpvp.getInstance().getPlayerStatService();
        this.guildService = Tazpvp.getInstance().getGuildService();
        this.victim = victim;
        this.pVictim = Bukkit.getPlayer(victim);
        this.victimStatEntity = playerStatService.getOrDefault(victim);
        if (pVictim != null) {
            this.location = pVictim.getLocation();
        } else {
            this.location = null;
        }
        if (killer == null) {
            final UUID currentKiller = CombatTagHelper.getLastAttacker(victim);
            if (currentKiller != null) {
                CombatObject.tags.get(victim).getAttackers().clear();
                this.killer = currentKiller;
                this.killerWrapper = PlayerWrapper.getPlayer(currentKiller);
                this.killerStatEntity = playerStatService.getOrDefault(currentKiller);
                this.killerGuild = Tazpvp.getInstance().getGuildService().getGuildByPlayer(currentKiller);
            } else {
                this.killer = null;
                this.killerWrapper = null;
                this.killerStatEntity = null;
                this.killerGuild = null;
            }
        } else {
            this.killerStatEntity = playerStatService.getOrDefault(killer);
            this.killerGuild = Tazpvp.getInstance().getGuildService().getGuildByPlayer(killer);
            this.killer = killer;
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
        CombatObject.tags.get(pVictim.getUniqueId()).endCombat(null, false);
    }

    public void dropItems() {
        if (killer == victim) return;

        if (victimGuild != null && killerGuild != null) {
            if (victimGuild == killerGuild) return;
        }

        int chance = r.nextInt(10);

        if (chance <= 4) {
            World world = location.getWorld();

            if (world != null) {
                if (killerWrapper.getTalentEntity().isNecromancer()) {
                    world.dropItemNaturally(location.add(0, 2, 0), ItemEnum.getRandomDrop().getItem(2));
                }
                world.dropItemNaturally(location.add(0, 1, 0), ItemEnum.getRandomDrop().getItem(2));
            }
        }
    }

    public void dropHead() {
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

        if (killer == null) {
            if (CombatTagHelper.getLastAttacker(victim) != null) {
                UUID assistKillerId = CombatTagHelper.getLastAttacker(victim);
                if (assistKillerId != null) {
                    Player assistKiller = Bukkit.getPlayer(assistKillerId);
                    if (assistKiller != null) {
                        camLoc = assistKiller.getLocation();
                    }
                }
            }
        } else {
            camLoc = pKiller.getLocation();
        }

        spectateObject = new SpectateObject(camLoc);
        pVictim.teleport(spectateObject.getResult());
        spectateObject.faceLocation(pVictim);
        pVictim.setGameMode(GameMode.SPECTATOR);
        pVictim.playSound(pVictim.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
        pVictim.sendTitle(CC.RED + "" + CC.BOLD + "YOU DIED", CC.GOLD + "Respawning...", 5, 50, 5);
        victimWrapper.setRespawning(true);
        new BukkitRunnable() {
            public void run() {
                pVictim.setGameMode(GameMode.SURVIVAL);
                pVictim.teleport(NRCore.config.spawn);
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
        CombatObject tag = CombatObject.tags.get(victim);
        if (!tag.getAttackers().isEmpty()) {
            for (UUID uuid : tag.getAttackers()) {
                if (uuid != killer && uuid != null) {
                    Player assister = Bukkit.getPlayer(uuid);
                    if (assister == null) continue;

                    int finalXp = 5;
                    int finalCoins = 5;

                    assister.sendMessage(
                            CC.DARK_GRAY + "Assist kill:" + CC.GRAY + " (" + pVictim.getName() + ") " +
                                    CC.DARK_AQUA + "Exp: " + CC.AQUA + finalXp +
                                    CC.GOLD + " Coins: " + CC.YELLOW + finalCoins
                    );
                    PlayerStatEntity aStatEntity = playerStatService.getOrDefault(uuid);
                    aStatEntity.setCoins(aStatEntity.getCoins() + finalCoins);
                    aStatEntity.setXp(aStatEntity.getXp() + finalXp);
                    aStatEntity.setMMR(aStatEntity.getMMR() + 5);

                    playerStatService.save(aStatEntity);
                    PlayerHelper.levelUp(uuid);
                }
            }
        }

        updateVictimStats();
        if (victimGuild != null && killerGuild != null) {
            if (victimGuild == killerGuild) return;
        }
        updateKillerStats();
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
            int XP_OTHER_BUFF = otherBuffs(killerStatEntity, XP);
            int COIN_OTHER_BUFF =  otherBuffs(killerStatEntity, COINS);

            final int bountyReward = LooseData.getKs(victim) * 10;

            int finalXp = (int) XP_NETWORK_BUFF.result() + XP_OTHER_BUFF;
            int finalCoins = (int) COIN_NETWORK_BUFF.result() + COIN_OTHER_BUFF + bountyReward;

            killerStatEntity.setKills(killerStatEntity.getKills() + 1);
            LooseData.addKs(killer);

            killerStatEntity.setCoins(killerStatEntity.getCoins() + finalCoins);
            killerStatEntity.setXp(killerStatEntity.getXp() + finalXp);
            killerStatEntity.setMMR(killerStatEntity.getMMR() + 15);
            playerStatService.save(killerStatEntity);

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

        victimStatEntity.setDeaths(victimStatEntity.getDeaths() + 1);
        if (victimStatEntity.getMMR() >= 10) {
            victimStatEntity.setDeaths(victimStatEntity.getMMR() - 10);
        } else {
            victimStatEntity.setDeaths(0);
        }
        LooseData.resetKs(victim);

        Tazpvp.getInstance().getPlayerNameTagService().setTagRank(pVictim);

        playerStatService.save(victimStatEntity);
        guildService.saveGuild(victimGuild);
    }

    private int otherBuffs(PlayerStatEntity playerStatEntity, int stat) {
        int finalStat = 0;

        if (playerStatEntity.getPrestige() > 0) {
            finalStat = (playerStatEntity.getPrestige() * stat);
        }

        return finalStat;
    }
}
