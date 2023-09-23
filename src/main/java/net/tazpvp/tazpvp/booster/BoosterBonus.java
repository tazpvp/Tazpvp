package net.tazpvp.tazpvp.booster;

import java.util.List;

public record BoosterBonus(double base, List<Double> percentMultipliers, double result) {
    public String prettyPercentMultiplier() {
        final StringBuilder stringBuilder = new StringBuilder();
        for (final double multi : percentMultipliers) {
            stringBuilder.append("+").append((int) (multi * 100)).append("%").append(" ");
        }

        return "(" + stringBuilder.toString().trim() + ")";
    }
}
