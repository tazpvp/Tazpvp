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
 */

package net.tazpvp.tazpvp.helpers;

import net.md_5.bungee.api.ChatColor;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.data.entity.PlayerStatEntity;
import net.tazpvp.tazpvp.data.services.PlayerStatService;
import net.tazpvp.tazpvp.enums.CC;
import net.tazpvp.tazpvp.enums.ScoreboardEnum;
import net.tazpvp.tazpvp.wrappers.PlayerWrapper;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatHelper {

    private static PlayerStatService statService = Tazpvp.getInstance().getPlayerStatService();

    public static void announce(String msg, Sound sound) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage(" ", msg, " ");
            p.playSound(p.getLocation(), sound, 1, 1);
        }
    }

    public static void announce(Player p, String msg, Sound sound) {
        p.sendMessage(" ", msg, " ");
        p.playSound(p.getLocation(), sound, 1, 1);
    }

    private static final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");

    public static String hex(String input) {
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            String color = input.substring(matcher.start(), matcher.end());
            input = input.replace(color, ChatColor.of(color) + "");
            matcher = pattern.matcher(input);
        }
        return input;
    }

    public static String gradient(String hex, String text, boolean bold) {
        StringBuilder coloredText = new StringBuilder();
        Matcher matcher = pattern.matcher(hex);
        if (matcher.find()) {
            hex = matcher.group();
        } else {
            return text;
        }
        float[] hsl = hexToHSL(hex);

        int length = text.length();
        float hueStep = -0.01f;

        for (int i = 0; i < length; i++) {
            float hue = hsl[0] + hueStep * i;
            while (hue < 0.0f) {
                hue += 1.0f;
            }
            String color = hslToHex(hue, hsl[1], hsl[2]);
            coloredText.append(hex(color));
            if (bold) {
                coloredText.append(ChatColor.BOLD);
            }
            coloredText.append(text.charAt(i));
        }

        return coloredText.toString();
    }

    private static float[] hexToHSL(String hexColorCode) {
        int red = Integer.parseInt(hexColorCode.substring(1, 3), 16);
        int green = Integer.parseInt(hexColorCode.substring(3, 5), 16);
        int blue = Integer.parseInt(hexColorCode.substring(5, 7), 16);

        float[] hsl = new float[3];
        java.awt.Color.RGBtoHSB(red, green, blue, hsl);

        return hsl;
    }

    private static String hslToHex(float hue, float saturation, float lightness) {
        int rgb = java.awt.Color.HSBtoRGB(hue, saturation, lightness);
        return String.format("#%06X", (rgb & 0xFFFFFF));
    }

    public static String hexColor(String hexCode, String text, boolean bold) {
        hexCode = hexCode.replace("#", "");
        ChatColor color = ChatColor.of("#" + hexCode);
        ChatColor finalColor = bold ? color.BOLD : color;
        String formattedText = finalColor + text + ChatColor.RESET;
        return formattedText;
    }

    public static org.bukkit.ChatColor hexChatColor(String hexCode, boolean bold) {
        hexCode = hexCode.replace("#", "");
        org.bukkit.ChatColor color = org.bukkit.ChatColor.valueOf("#" + hexCode);
        org.bukkit.ChatColor finalColor = bold ? color.BOLD : color;
        return finalColor;
    }

    public static String intToRoman(int num) {
        int[] values = {1000,900,500,400,100,90,50,40,10,9,5,4,1};
        String[] romanLetters = {"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};
        StringBuilder roman = new StringBuilder();
        for(int i=0; i < values.length; i++) {
            while(num >= values[i]) {
                num = num - values[i];
                roman.append(romanLetters[i]);
            }
        }
        return roman.toString();
    }

    public static boolean hasPremium(Player p, @Nullable String prefix) {
        PlayerWrapper pw = PlayerWrapper.getPlayer(p);
        if (pw.getRank().getHierarchy() >= 1) {
            p.sendMessage(Objects.requireNonNullElse(prefix, CC.RED) + "You require a premium subscription for this feature.");
            p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
            return false;
        }
        return true;
    }

    public static void achievement(Player p, String name) {
        p.sendMessage("");
        p.sendMessage(CC.DARK_AQUA + "" + CC.BOLD + "Achievement Unlocked: " + CC.RED + "" + CC.BOLD + name);
        p.sendMessage("");
        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
    }

    public static int getRanking(Player p) {
        PlayerStatEntity statEntity = statService.getOrDefault(p.getUniqueId());

        if (statEntity.getMMR() > 2000) return 5;
        else if (statEntity.getMMR() > 1000) return 4;
        else if (statEntity.getMMR() > 500) return 3;
        else if (statEntity.getMMR() > 100) return 2;
        else return 1;
    }

    public static String getRankingPrefix(Player p) {
        if (getRanking(p) == 5) return scoreboard(p, "✪✪✪✪✪");
        else if (getRanking(p) == 4) return scoreboard(p, "✪✪✪✪");
        else if (getRanking(p) == 3) return scoreboard(p, "✪✪✪");
        else if (getRanking(p) == 2) return scoreboard(p, "✪✪");
        else return scoreboard(p, "✪");
    }

    private static String scoreboard(Player p, String rank) {
        PlayerStatEntity statEntity = statService.getOrDefault(p.getUniqueId());

        ScoreboardHelper.updateSuffix(p, ScoreboardEnum.RANK,  rank + CC.DARK_GRAY + "(" + statEntity.getMMR() + ")");
        return rank;
    }
}
