package net.tazpvp.tazpvp.game.booster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ActiveBoosterManager {
    private static ActiveBoosterManager instance;

    public static ActiveBoosterManager getInstance() {
        if (instance == null) {
            instance = new ActiveBoosterManager();
        }
        return instance;
    }

    private final Map<BoosterTypes, Long> timeLeft;

    private ActiveBoosterManager() {
        this.timeLeft = new HashMap<>();

        for (final BoosterTypes boosterType : BoosterTypes.values()) {
            this.timeLeft.put(boosterType, System.currentTimeMillis());
        }
    }

    public void addTimeToBooster(final BoosterTypes boosterType, final TimeUnit timeUnit, final long amount) {
        if (!timeLeft.containsKey(boosterType)) {
            timeLeft.put(boosterType, System.currentTimeMillis());
        }

        final long millisToAdd = timeUnit.toMillis(amount);

        timeLeft.put(boosterType, getTimeLeft(boosterType) + millisToAdd);
    }

    public long getTimeLeft(final BoosterTypes boosterType) {
        final long currentTime = System.currentTimeMillis();
        final long storedTime = timeLeft.get(boosterType);

        return Math.max(currentTime, storedTime);
    }

    public boolean isActive(final BoosterTypes boosterType) {
        return timeLeft.get(boosterType) > System.currentTimeMillis();
    }

    public BoosterBonus calculateBonus(final double base, final List<BoosterTypes> boostersToCalculateWith) {
        double result = base;
        final List<Double> multipliers = new ArrayList<>();

        for (final BoosterTypes boosterType : boostersToCalculateWith) {
            if (isActive(boosterType)) {
                result *= boosterType.getMultiplier();
                multipliers.add(boosterType.getMultiplier() - 1);
            }
        }

        return new BoosterBonus(base, multipliers, result);
    }
}
