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

package net.tazpvp.tazpvp.utils.crate;

import lombok.Getter;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.utils.enums.CC;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

public class KeyFactory {
    public static KeyFactory getFactory() {
        return new KeyFactory();
    }

    @Getter
    private final NamespacedKey crateKey;

    public ItemStack createDailyKey() {
        ItemStack i = ItemBuilder.of(Material.TRIPWIRE_HOOK, 1, CC.GOLD + "Daily Crate Key").lore(CC.GRAY + "Use this at your nearest daily crate!").build();
        i = setCrateKey(i, "daily");
        return i;
    }

    private ItemStack setCrateKey(ItemStack i, String type) {
        ItemMeta meta = i.getItemMeta();
        meta.getPersistentDataContainer().set(getCrateKey(), PersistentDataType.STRING, type);
        i.setItemMeta(meta);
        return i;
    }

    public boolean isCrateKey(ItemStack i, String type) {
        ItemMeta meta = i.getItemMeta();
        return meta.getPersistentDataContainer().get(getCrateKey(), PersistentDataType.STRING).equals(type);
    }

    private KeyFactory() {
        this.crateKey = new NamespacedKey(Tazpvp.getInstance(), "crate");
    }
}
