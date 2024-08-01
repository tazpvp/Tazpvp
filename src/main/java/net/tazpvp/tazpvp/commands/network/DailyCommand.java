package net.tazpvp.tazpvp.commands.network;

import lombok.NonNull;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.data.entity.PlayerStatEntity;
import net.tazpvp.tazpvp.data.services.PlayerStatService;
import net.tazpvp.tazpvp.game.crates.KeyFactory;
import net.tazpvp.tazpvp.enums.CC;
import net.tazpvp.tazpvp.helpers.ChatHelper;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

public class DailyCommand extends NRCommand {
    public DailyCommand() {
        super(new Label("daily", null));
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NonNull String[] args) {
        if (sender instanceof Player p) {
            PlayerStatService playerStatService = Tazpvp.getInstance().getPlayerStatService();
            PlayerStatEntity playerStatEntity = playerStatService.getOrDefault(p.getUniqueId());

            long currentTime = System.currentTimeMillis();
            long lastClaimTime = playerStatEntity.getLastClaim();

            if (p.isOp()) {
                p.getInventory().addItem(KeyFactory.getFactory().createCommonKey());
                p.getInventory().addItem(KeyFactory.getFactory().createRareKey());
                p.getInventory().addItem(KeyFactory.getFactory().createMythicKey());
            } else if (Tazpvp.getCrateManager().canClaimDaily(p)) {
                p.getInventory().addItem(KeyFactory.getFactory().createCommonKey());
                p.sendMessage(CC.GREEN + "You claimed your daily " + ChatHelper.gradient("#03fc39", "Common Key", true));
                p.playSound(p.getLocation(), Sound.ITEM_BOTTLE_FILL_DRAGONBREATH, 1F, 1F);

                playerStatEntity.setLastClaim(currentTime);
                playerStatService.save(playerStatEntity);

            } else {
                long timeDifference = (24 * 60 * 60 * 1000) - (currentTime - lastClaimTime);

                if (timeDifference > 0) {
                    long hoursLeft = timeDifference / (60 * 60 * 1000);
                    p.sendMessage(CC.RED + "Please wait " + hoursLeft + " hours until you can claim your crate key again.");
                    p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_HURT, 1F, 1F);
                } else {
                    p.sendMessage(CC.RED + "Please wait until you can claim your crate key again.");
                    p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_HURT, 1F, 1F);
                }
            }
        }
        return true;
    }
}
