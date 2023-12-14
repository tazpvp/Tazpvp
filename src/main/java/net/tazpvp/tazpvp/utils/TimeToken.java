package net.tazpvp.tazpvp.utils;

import lombok.Getter;

import java.util.Arrays;
import java.util.Locale;

public class TimeToken {
    @Getter
    private final String untokenized;
    @Getter
    private final long unixTimestamp;

    private final String[] conversionText = {"d", "h", "m"};
    private final int[] conversionMultiplier = {24 * 60 * 60 * 1000, 60 * 60 * 1000, 60 * 1000};

    public TimeToken(String untokenized) {
        this.untokenized = untokenized.toLowerCase(Locale.ROOT);

        if (getUntokenized().equals("perm") || getUntokenized().equals("permanent")) {
            unixTimestamp = 0L;
            return;
        }

        final int time = Integer.parseInt(getUntokenized().replaceAll("[^0-9]", ""));
        final String period = getUntokenized().replaceAll("[^A-Za-z]", "");

        final long currentTimestamp = System.currentTimeMillis();
        unixTimestamp = currentTimestamp + ((long) time * conversionMultiplier[Arrays.asList(conversionText).indexOf(period)]);
    }
}
