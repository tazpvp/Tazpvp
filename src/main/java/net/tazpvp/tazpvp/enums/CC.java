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

package net.tazpvp.tazpvp.enums;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import org.bukkit.ChatColor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.regex.Pattern;

public enum CC {
    BLACK('0', 0x00) {
        @Nonnull
        @Override
        public net.md_5.bungee.api.ChatColor asBungee() {
            return net.md_5.bungee.api.ChatColor.BLACK;
        }
    },
    DARK_BLUE('1', 0x1) {
        @Nonnull
        @Override
        public net.md_5.bungee.api.ChatColor asBungee() {
            return net.md_5.bungee.api.ChatColor.DARK_BLUE;
        }
    },
    DARK_GREEN('2', 0x2) {
        @Nonnull
        @Override
        public net.md_5.bungee.api.ChatColor asBungee() {
            return net.md_5.bungee.api.ChatColor.DARK_GREEN;
        }
    },
    DARK_AQUA('3', 0x3) {
        @Nonnull
        @Override
        public net.md_5.bungee.api.ChatColor asBungee() {
            return net.md_5.bungee.api.ChatColor.DARK_AQUA;
        }
    },
    DARK_RED('4', 0x4) {
        @Nonnull
        @Override
        public net.md_5.bungee.api.ChatColor asBungee() {
            return net.md_5.bungee.api.ChatColor.DARK_RED;
        }
    },
    DARK_PURPLE('5', 0x5) {
        @Nonnull
        @Override
        public net.md_5.bungee.api.ChatColor asBungee() {
            return net.md_5.bungee.api.ChatColor.DARK_PURPLE;
        }
    },
    GOLD('6', 0x6) {
        @Nonnull
        @Override
        public net.md_5.bungee.api.ChatColor asBungee() {
            return net.md_5.bungee.api.ChatColor.GOLD;
        }
    },
    GRAY('7', 0x7) {
        @Nonnull
        @Override
        public net.md_5.bungee.api.ChatColor asBungee() {
            return net.md_5.bungee.api.ChatColor.GRAY;
        }
    },
    DARK_GRAY('8', 0x8) {
        @Nonnull
        @Override
        public net.md_5.bungee.api.ChatColor asBungee() {
            return net.md_5.bungee.api.ChatColor.DARK_GRAY;
        }
    },
    BLUE('9', 0x9) {
        @Nonnull
        @Override
        public net.md_5.bungee.api.ChatColor asBungee() {
            return net.md_5.bungee.api.ChatColor.BLUE;
        }
    },
    GREEN('a', 0xA) {
        @Nonnull
        @Override
        public net.md_5.bungee.api.ChatColor asBungee() {
            return net.md_5.bungee.api.ChatColor.GREEN;
        }
    },
    AQUA('b', 0xB) {
        @Nonnull
        @Override
        public net.md_5.bungee.api.ChatColor asBungee() {
            return net.md_5.bungee.api.ChatColor.AQUA;
        }
    },
    RED('c', 0xC) {
        @Nonnull
        @Override
        public net.md_5.bungee.api.ChatColor asBungee() {
            return net.md_5.bungee.api.ChatColor.RED;
        }
    },
    LIGHT_PURPLE('d', 0xD) {
        @Nonnull
        @Override
        public net.md_5.bungee.api.ChatColor asBungee() {
            return net.md_5.bungee.api.ChatColor.LIGHT_PURPLE;
        }
    },
    YELLOW('e', 0xE) {
        @Nonnull
        @Override
        public net.md_5.bungee.api.ChatColor asBungee() {
            return net.md_5.bungee.api.ChatColor.YELLOW;
        }
    },
    WHITE('f', 0xF) {
        @Nonnull
        @Override
        public net.md_5.bungee.api.ChatColor asBungee() {
            return net.md_5.bungee.api.ChatColor.WHITE;
        }
    },
    MAGIC('k', 0x10, true) {
        @Nonnull
        @Override
        public net.md_5.bungee.api.ChatColor asBungee() {
            return net.md_5.bungee.api.ChatColor.MAGIC;
        }
    },
    BOLD('l', 0x11, true) {
        @Nonnull
        @Override
        public net.md_5.bungee.api.ChatColor asBungee() {
            return net.md_5.bungee.api.ChatColor.BOLD;
        }
    },
    STRIKETHROUGH('m', 0x12, true) {
        @Nonnull
        @Override
        public net.md_5.bungee.api.ChatColor asBungee() {
            return net.md_5.bungee.api.ChatColor.STRIKETHROUGH;
        }
    },
    UNDERLINE('n', 0x13, true) {
        @Nonnull
        @Override
        public net.md_5.bungee.api.ChatColor asBungee() {
            return net.md_5.bungee.api.ChatColor.UNDERLINE;
        }
    },
    ITALIC('o', 0x14, true) {
        @Nonnull
        @Override
        public net.md_5.bungee.api.ChatColor asBungee() {
            return net.md_5.bungee.api.ChatColor.ITALIC;
        }
    },
    RESET('r', 0x15) {
        @Nonnull
        @Override
        public net.md_5.bungee.api.ChatColor asBungee() {
            return net.md_5.bungee.api.ChatColor.RESET;
        }
    };

    public static final char COLOR_CHAR = '\u00A7';
    private static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)" + String.valueOf(COLOR_CHAR) + "[0-9A-FK-ORX]");

    private final int intCode;
    private final char code;
    private final boolean isFormat;
    private final String toString;
    private static final Map<Integer, CC> BY_ID = Maps.newHashMap();
    private static final Map<Character, CC> BY_CHAR = Maps.newHashMap();

    private CC(char code, int intCode) {
        this(code, intCode, false);
    }

    private CC(char code, int intCode, boolean isFormat) {
        this.code = code;
        this.intCode = intCode;
        this.isFormat = isFormat;
        this.toString = new String(new char[] {COLOR_CHAR, code});
    }

    @Nonnull
    public net.md_5.bungee.api.ChatColor asBungee() {
        return net.md_5.bungee.api.ChatColor.RESET;
    };


    public char getChar() {
        return code;
    }

    @Nonnull
    @Override
    public String toString() {
        return toString;
    }

    public boolean isFormat() {
        return isFormat;
    }

    public boolean isColor() {
        return !isFormat && this != RESET;
    }

    @Nullable
    public static CC getByChar(char code) {
        return BY_CHAR.get(code);
    }

    @Nullable
    public static CC getByChar(@Nonnull String code) {
        Preconditions.checkArgument(code != null, "Code cannot be null");
        Preconditions.checkArgument(code.length() > 0, "Code must have at least one char");

        return BY_CHAR.get(code.charAt(0));
    }

    @Nullable
    public static String stripColor(@Nullable final String input) {
        if (input == null) {
            return null;
        }

        return STRIP_COLOR_PATTERN.matcher(input).replaceAll("");
    }


    @Nonnull
    public static String translateAlternateColorCodes(char altColorChar, @Nonnull String textToTranslate) {
        Preconditions.checkArgument(textToTranslate != null, "Cannot translate null text");

        char[] b = textToTranslate.toCharArray();
        for (int i = 0; i < b.length - 1; i++) {
            if (b[i] == altColorChar && "0123456789AaBbCcDdEeFfKkLlMmNnOoRrXx".indexOf(b[i + 1]) > -1) {
                b[i] = ChatColor.COLOR_CHAR;
                b[i + 1] = Character.toLowerCase(b[i + 1]);
            }
        }
        return new String(b);
    }

    @Nonnull
    public static String trans(@Nonnull String textToTranslate) {
        return translateAlternateColorCodes('&', textToTranslate);
    }

    @Nonnull
    public static String getLastColors(@Nonnull String input) {
        Preconditions.checkArgument(input != null, "Cannot get last colors from null text");

        String result = "";
        int length = input.length();

        // Search backwards from the end as it is faster
        for (int index = length - 1; index > -1; index--) {
            char section = input.charAt(index);
            if (section == COLOR_CHAR && index < length - 1) {
                char c = input.charAt(index + 1);
                CC color = getByChar(c);

                if (color != null) {
                    result = color.toString() + result;

                    // Once we find a color or reset we can stop searching
                    if (color.isColor() || color.equals(RESET)) {
                        break;
                    }
                }
            }
        }

        return result;
    }

    static {
        for (CC color : values()) {
            BY_ID.put(color.intCode, color);
            BY_CHAR.put(color.code, color);
        }
    }
}
