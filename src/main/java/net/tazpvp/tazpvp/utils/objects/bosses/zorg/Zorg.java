package net.tazpvp.tazpvp.utils.objects.bosses.zorg;

import net.tazpvp.tazpvp.utils.functions.ChatFunctions;
import net.tazpvp.tazpvp.utils.objects.bosses.CustomBoss;
import net.tazpvp.tazpvp.utils.objects.bosses.zorg.attacks.SonicBoomAttack;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

import java.util.Random;

public class Zorg extends CustomBoss {
    private final Random random = new Random();

    public Zorg(final Location location) {
        super(location.getWorld().spawnEntity(location, EntityType.WITHER_SKELETON), location);
        getBossAs().setAI(true);
        getBossAs().setAware(true);
        getBossAs().setCustomNameVisible(true);

        addAttack(new SonicBoomAttack());

        setup();
    }

    @Override
    protected void setup() {
        final EntityEquipment equipment = getBossAs().getEquipment();
        assert equipment != null;

        equipment.setHelmet(new ItemStack(Material.DIAMOND_HELMET));
        equipment.setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
        equipment.setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
        equipment.setBoots(new ItemStack(Material.DIAMOND_BOOTS));
        equipment.setItemInMainHand(new ItemBuilder().item(new ItemStack(Material.DIAMOND_SWORD)).enchantment(Enchantment.KNOCKBACK, 2).build());
        getBossAs().setCustomName(ChatFunctions.gradient("#FFADED", "Zorg", true));
    }

    protected WitherSkeleton getBossAs() {
        return (WitherSkeleton) getBoss();
    }

    @Override
    protected void despawn() {
        getBossAs().setHealth(0);
    }
}
