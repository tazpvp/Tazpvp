package net.tazpvp.tazpvp.game.bosses.zorg;

import net.tazpvp.tazpvp.game.bosses.CustomBoss;
import net.tazpvp.tazpvp.game.bosses.zorg.attacks.SonicBoomAttack;
import net.tazpvp.tazpvp.game.bosses.zorg.attacks.SummonUndeadAttack;
import net.tazpvp.tazpvp.helpers.ChatFunctions;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

public class Zorg extends CustomBoss {

    public Zorg(final Location location) {
        super(location.getWorld().spawnEntity(location, EntityType.WITHER_SKELETON), location);
        addAttack(new SonicBoomAttack());
        addAttack(new SummonUndeadAttack());

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

        getBossAs().setAI(true);
        getBossAs().setAware(true);
        getBossAs().setCustomNameVisible(true);
    }

    @Override
    protected void spawn() {
        WitherSkeleton skeleton = (WitherSkeleton) getSpawnLocation().getWorld().spawnEntity(getSpawnLocation(), EntityType.WITHER_SKELETON);
        skeleton.setRemoveWhenFarAway(false);
        super.boss = skeleton;
        setup();
    }

    protected WitherSkeleton getBossAs() {
        return (WitherSkeleton) getBoss();
    }

    @Override
    protected void despawn() {
        getBossAs().remove();
    }
}
