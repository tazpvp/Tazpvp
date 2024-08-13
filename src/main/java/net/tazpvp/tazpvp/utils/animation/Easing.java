package net.tazpvp.tazpvp.utils.animation;

public class Easing {
    public static float easeInOut(final float t) {
        if (t < 0.5) {
            return 4 * t * t * t;
        } else {
            float f = (t - 1);
            return 1 + 4 * f * f * f;
        }
    }
}
