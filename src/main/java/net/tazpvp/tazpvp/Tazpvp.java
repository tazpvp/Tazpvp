/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2022, n-tdi
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

package net.tazpvp.tazpvp;

import lombok.Getter;
import net.tazpvp.tazpvp.commands.EventCommandFunction;
import net.tazpvp.tazpvp.listeners.Damage;
import net.tazpvp.tazpvp.listeners.Join;
import net.tazpvp.tazpvp.listeners.Leave;
import net.tazpvp.tazpvp.utils.observer.Observable;
import net.tazpvp.tazpvp.utils.observer.Observer;
import net.tazpvp.tazpvp.utils.functions.CombatFunctions;
import net.tazpvp.tazpvp.utils.objects.AssistKill;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/*
    A plugin for tazpvp beacuse we love tazpvp! <3 I love tazpvp <3 <3 <3 Rownxo smells like tazpvp stinky tazpvp
 */
public final class Tazpvp extends JavaPlugin {
    @Getter
    private static List<Observer> observers = new ArrayList<>();
    public static List<String> events = new ArrayList<>();
    public static String eventKey;
    public static List<UUID> playerList = new ArrayList<>();

    public static String prefix = "tazpvp.";

    public static WeakHashMap<UUID, AssistKill> combatAssist = new WeakHashMap<>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new Damage(), this);
        getServer().getPluginManager().registerEvents(new Join(), this);
        getServer().getPluginManager().registerEvents(new Leave(), this);
        new EventCommandFunction();

        events.add("FFA");

        registerTalents();

        new BukkitRunnable() {
            @Override
            public void run() {
                CombatFunctions.check();
            }
        }.runTaskTimerAsynchronously(this, 16L, 16L);
    }

    public static void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void onDisable() {}

    public static Tazpvp getInstance() {
        return (Tazpvp) Bukkit.getPluginManager().getPlugin("Tazpvp");
    }

    /**
     * Implementation of {@code getExtendingClasses} for {@code Observable} extendations.
     */
    public void registerTalents() {
        for (Class<? extends Observable> observable : Tazpvp.getExtendingClasses(this, Observable.class)) {
            try {
                observable.getConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Gets all non-abstract, non-interface classes which extend a certain class within a plugin @ Redlib
     *
     * @param plugin The plugin
     * @param clazz  The class
     * @param <T>    The type of the class
     * @return The list of matching classes
     */
    public static <T> List<Class<? extends T>> getExtendingClasses(Plugin plugin, Class<T> clazz) {
        List<Class<? extends T>> list = new ArrayList<>();
        try {
            ClassLoader loader = plugin.getClass().getClassLoader();
            JarFile file = new JarFile(new File(plugin.getClass().getProtectionDomain().getCodeSource().getLocation().toURI()));
            Enumeration<JarEntry> entries = file.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (entry.isDirectory()) {
                    continue;
                }
                String name = entry.getName();
                if (!name.endsWith(".class")) {
                    continue;
                }
                name = name.substring(0, name.length() - 6).replace("/", ".");
                Class<?> c;
                try {
                    c = Class.forName(name, true, loader);
                } catch (ClassNotFoundException | NoClassDefFoundError ex) {
                    continue;
                }
                if (!clazz.isAssignableFrom(c) || Modifier.isAbstract(c.getModifiers()) || c.isInterface()) {
                    continue;
                }
                list.add((Class<? extends T>) c);
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return list;
    }
}
