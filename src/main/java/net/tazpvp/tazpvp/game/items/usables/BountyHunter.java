package net.tazpvp.tazpvp.game.items.usables;

import net.tazpvp.tazpvp.data.LooseData;
import net.tazpvp.tazpvp.game.items.UsableItem;
import net.tazpvp.tazpvp.enums.CC;
import net.tazpvp.tazpvp.wrappers.PlayerWrapper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class BountyHunter extends UsableItem {
    public BountyHunter() {
        super("Bounty Hunter", new String[]{"Right click to detect bounties."}, Material.COMPASS);
    }

    @Override
    public void onRightClick(Player p, ItemStack item) {
        WeakHashMap<UUID, Integer> bounties = new WeakHashMap<UUID, Integer>();
        for (Player target : Bukkit.getOnlinePlayers()) {
            PlayerWrapper targetWrapper = PlayerWrapper.getPlayer(target);
            if (LooseData.getKs(target.getUniqueId()) > 0) {
                bounties.put(target.getUniqueId(), LooseData.getKs(target.getUniqueId()));
            }
        }

        List<Map.Entry<UUID, Integer>> entries = new ArrayList<>(bounties.entrySet());
        entries.sort(Map.Entry.<UUID, Integer>comparingByValue().reversed());

        if (!entries.isEmpty()) {

            Map.Entry<UUID, Integer> entityWithHighestKs = entries.get(0);
            Player playerHighestKs = Bukkit.getPlayer(entityWithHighestKs.getKey());
            if (playerHighestKs == null) return;

            p.sendMessage(CC.LIGHT_PURPLE + "" + CC.BOLD + "The player with the highest bounty is " + CC.YELLOW + CC.BOLD + playerHighestKs.getName());
        }
    }

    @Override
    public void onLeftClick(Player p, ItemStack item) {}

    @Override
    public void onLeftClick(Player p, ItemStack item, Player target) {}

    @Override
    public void onLeftClick(Player p, ItemStack item, Block b) {}
}
