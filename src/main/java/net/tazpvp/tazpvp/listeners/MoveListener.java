package net.tazpvp.tazpvp.listeners;

import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.data.entity.TalentEntity;
import net.tazpvp.tazpvp.data.implementations.PunishmentServiceImpl;
import net.tazpvp.tazpvp.data.services.PunishmentService;
import net.tazpvp.tazpvp.enums.CC;
import net.tazpvp.tazpvp.game.npc.characters.NPC;
import net.tazpvp.tazpvp.helpers.AfkHelper;
import net.tazpvp.tazpvp.helpers.ParkourHelper;
import net.tazpvp.tazpvp.objects.DeathObject;
import net.tazpvp.tazpvp.wrappers.PlayerWrapper;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import world.ntdi.nrcore.NRCore;

public class MoveListener implements Listener {
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        PlayerWrapper pw = PlayerWrapper.getPlayer(p);
        Location playerLocation = p.getLocation();

        Location launchpadRaidus = new Location(Bukkit.getWorld("arena"), 0, 100, NRCore.config.spawn.getZ() + 29);
        Block b = new Location(p.getWorld(), playerLocation.getX(), playerLocation.getY() - 1, playerLocation.getZ()).getBlock();

        if (pw == null) return;

        if (pw.getDuel() != null) {
            if (pw.getDuel().isStarting()) {
                e.setCancelled(true);
                return;
            }
        }

        final PunishmentService punishmentService = new PunishmentServiceImpl();
        final PunishmentService.PunishmentType punishmentType = punishmentService.getPunishment(p.getUniqueId());

        if (punishmentType == PunishmentService.PunishmentType.BANNED) {
            if (punishmentService.getTimeRemaining(p.getUniqueId()) > 0) {
                e.setCancelled(true);
                if (p.getGameMode() != GameMode.SPECTATOR) {
                    p.setGameMode(GameMode.SPECTATOR);
                }
            }
        }

        if (p.getWorld().equals(Bukkit.getWorld("parkour"))) {
            if (b.getType() == Material.WATER && p.getGameMode() != GameMode.SURVIVAL) {
                ParkourHelper.getCheckpoint(p);
            }
        } else if (p.getWorld().getName().equals("arena")) {

            AfkHelper.setAfk(p);

            if (p.getLocation().distance(launchpadRaidus) < 5) {
                if (p.getGameMode().equals(GameMode.SURVIVAL)){
                    Launchpad(p);
                    pw.setTimeOfLaunch(System.currentTimeMillis());
                }
                return;
            }

            if (p.getGameMode() == GameMode.SURVIVAL) {
                if (p.getLocation().getY() < 78 && p.getLocation().getZ() < 133) {
                    new DeathObject(p.getUniqueId(), null);
                    return;
                }
                if (p.getLocation().getY() < 64) {
                    new DeathObject(p.getUniqueId(), null);
                    return;
                }
            }

            pw.setReceivedDialogue(null);
            for (NPC npc : Tazpvp.getInstance().getNpcs()) {
                if (npc.withinRange(playerLocation)) {
                    pw.setReceivedDialogue(npc);
                    break;
                }
            }
            if (pw.getReceivedDialogue() != null && !pw.isNpcDialogue()) {
                p.sendMessage(pw.getReceivedDialogue().getDialogues().getRandomDialogue());
                pw.setNpcDialogue(true);
            } else if (pw.getReceivedDialogue() == null) {
                pw.setNpcDialogue(false);
            }

            if (p.hasMetadata("spawnTeleport")) {
                p.sendMessage(CC.RED + "Teleportation cancelled. You moved.");
                p.removeMetadata("spawnTeleport", Tazpvp.getInstance());
            }
        }
    }

    private void Launchpad(Player p) {
        PlayerWrapper pw = PlayerWrapper.getPlayer(p);
        if (!pw.isLaunching()) {
            pw.setLaunching(true);
            new BukkitRunnable() {
                @Override
                public void run() {
                    pw.setLaunching(false);
                }
            }.runTaskLater(Tazpvp.getInstance(), 20);
            p.playSound(p.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1, 1);

            final TalentEntity talentEntity = pw.getTalentEntity();
            if (talentEntity.isGlide()) {
                p.setVelocity(new Vector(0, 1.7, 8));
            } else {
                p.setVelocity(new Vector(0, 1.2, 3));
            }
            Tazpvp.getObservers().forEach(o -> o.launch(p));
        }
    }
}
