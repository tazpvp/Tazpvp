/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2023, n-tdi
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package net.tazpvp.tazpvp.game.crates;

import lombok.Getter;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.enums.CC;
import net.tazpvp.tazpvp.helpers.ChatHelper;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

@Getter
public class KeyFactory {
    private final NamespacedKey crateKey;

    private KeyFactory() {
        this.crateKey = new NamespacedKey(Tazpvp.getInstance(), "crate");
    }

    public static KeyFactory getFactory() {
        return new KeyFactory();
    }

    public ItemStack createCommonKey() {
        return createKey("common", ChatHelper.gradient("#03fc39", "Common Key", true));
    }

    public ItemStack createRareKey() {
        return createKey("rare", ChatHelper.gradient("#039dfc", "Rare Key", true));
    }

    public ItemStack createMythicKey() {
        return createKey("mythic", ChatHelper.gradient("#db3bff", "Mythic Key", true));
    }

    public ItemStack createKey(String type, String name) {
        ItemStack item = ItemBuilder.of(Material.TRIPWIRE_HOOK, 1, name)
                .lore(CC.GRAY + "Right-click the crate.")
                .build();
        return setCrateKey(item, type);
    }

    private ItemStack setCrateKey(ItemStack item, String type) {
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(crateKey, PersistentDataType.STRING, type);
        item.setItemMeta(meta);
        return item;
    }

    public boolean isCrateKey(ItemStack item, String type) {
        ItemMeta meta = item.getItemMeta();
        return meta.getPersistentDataContainer().get(crateKey, PersistentDataType.STRING).equals(type);
    }
}
