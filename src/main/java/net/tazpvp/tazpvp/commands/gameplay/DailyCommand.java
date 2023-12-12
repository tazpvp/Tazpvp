package net.tazpvp.tazpvp.commands.gameplay;

import lombok.NonNull;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.utils.crate.KeyFactory;
import net.tazpvp.tazpvp.utils.data.DataTypes;
import net.tazpvp.tazpvp.utils.data.PersistentData;
import net.tazpvp.tazpvp.utils.enums.CC;
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
            if (p.isOp()) {
                p.getInventory().addItem(KeyFactory.getFactory().createDailyKey());
            }  else if (Tazpvp.getCrateManager().canClaimDaily(p)) {
                p.getInventory().addItem(KeyFactory.getFactory().createDailyKey());
                p.sendMessage(CC.GREEN + "Claimed " + CC.GOLD + "DAILY " + CC.GREEN + "crate key!");
                p.playSound(p.getLocation(), Sound.ITEM_BOTTLE_FILL_DRAGONBREATH, 1F, 1F);

                PersistentData.set(p, DataTypes.DAILYCRATEUNIX, System.currentTimeMillis());
            } else {
                p.sendMessage(CC.RED + "Please wait until you can claim your crate key again.");
                p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_HURT, 1F, 1F);
            }
        }
        return true;
    }
}
