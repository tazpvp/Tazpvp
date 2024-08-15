package net.tazpvp.tazpvp.services;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;
import world.ntdi.nrcore.utils.item.builders.PotionBuilder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class KitMakerServiceImpl implements KitMakerService {
    private final List<ItemStack> validItems;

    @SuppressWarnings("all")
    public KitMakerServiceImpl() {
        this.validItems = List.of(
                ItemBuilder.of(Material.GLOWSTONE).amount(64).build(),
                ItemBuilder.of(Material.ENDER_PEARL).amount(16).build(),
                ItemBuilder.of(Material.TOTEM_OF_UNDYING).amount(1).build(),
                ItemBuilder.of(Material.GOLDEN_APPLE).amount(64).build(),
                ItemBuilder.of(Material.EXPERIENCE_BOTTLE).amount(64).build(),
                ItemBuilder.of(Material.OBSIDIAN).amount(64).build(),
                ItemBuilder.of(Material.END_CRYSTAL).amount(64).build(),
                ItemBuilder.of(Material.RESPAWN_ANCHOR).amount(64).build(),
                PotionBuilder.of(PotionBuilder.PotionType.SPLASH).setPotionData(new PotionData(PotionType.SWIFTNESS)).amount(1).build(),
                PotionBuilder.of(PotionBuilder.PotionType.SPLASH).setPotionData(new PotionData(PotionType.REGENERATION)).amount(1).build(),
                ItemBuilder.of(Material.TIPPED_ARROW).amount(64).build(),
                ItemBuilder.of(Material.CROSSBOW).amount(1).build()
        );
    }

    @Override
    public String serializeInventory(ItemStack[] inventory) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            // Write the size of the inventory
            dataOutput.writeInt(inventory.length);

            // Save every element in the list
            for (ItemStack itemStack : inventory) {
                dataOutput.writeObject(itemStack);
            }

            // Serialize that array
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }

    @Override
    public ItemStack[] deserializeInventory(String data) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            ItemStack[] items = new ItemStack[dataInput.readInt()];

            // Read the serialized inventory
            for (int i = 0; i < items.length; i++) {
                items[i] = (ItemStack) dataInput.readObject();
            }

            dataInput.close();
            return items;
        } catch (ClassNotFoundException | IOException e) {
            Bukkit.getLogger().severe("Ruh roh deserializing inventory");
        }
        return new ItemStack[0];
    }

    @Override
    public List<ItemStack> getValidItems() {
        return validItems;
    }
}
