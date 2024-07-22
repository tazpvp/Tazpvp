package net.tazpvp.tazpvp.utils.holograms;

import lombok.Getter;
import net.tazpvp.tazpvp.Tazpvp;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Display;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TextDisplay;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Transformation;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

@Getter
public class Hologram {
    public static final NamespacedKey nameKey = new NamespacedKey(Tazpvp.getInstance(), "billboard-name");
    public static final NamespacedKey shouldCycleKey = new NamespacedKey(Tazpvp.getInstance(), "billboard-cycle");
    public static final NamespacedKey textKey = new NamespacedKey(Tazpvp.getInstance(), "billboard-text-list");
    public static final NamespacedKey indexKey = new NamespacedKey(Tazpvp.getInstance(), "billboard-text-index");

    private final String name;
    private final Vector3f scale;
    private final Location location;
    private final boolean shouldCycle;
    private final Display.Billboard billboard;
    private final TextDisplay.TextAlignment alignment;
    private final boolean hasBackground;
    private final String[] texts;
    private TextDisplay display;

    public Hologram(String name, Location location, String... texts) {
        this(name, location, new Vector3f(1, 1, 1), false, TextDisplay.TextAlignment.CENTER, Display.Billboard.CENTER, true, texts);
    }

    public Hologram(String name, Location location, Vector3f scale, String... texts) {
        this(name, location, scale, false, TextDisplay.TextAlignment.CENTER, Display.Billboard.CENTER, true, texts);
    }

    public Hologram(String name, Location location, Vector3f scale, boolean shouldCycle, String... texts) {
        this(name, location, scale, shouldCycle, TextDisplay.TextAlignment.CENTER, Display.Billboard.CENTER, true, texts);
    }

    public Hologram(String name, Location location, Vector3f scale, boolean shouldCycle, TextDisplay.TextAlignment alignment, String... texts) {
        this(name, location, scale, shouldCycle, alignment, Display.Billboard.CENTER, true, texts);
    }

    public Hologram(String name, Location location, Vector3f scale, boolean shouldCycle, TextDisplay.TextAlignment alignment, Display.Billboard billboard, String... texts) {
        this(name, location, scale, shouldCycle, alignment, billboard, true, texts);
    }

    public Hologram(@NotNull String name, @NotNull Location location, @NotNull Vector3f scale, boolean shouldCycle, @NotNull TextDisplay.TextAlignment alignment, @NotNull Display.Billboard billboard, boolean hasBackground, @NotNull String... texts) {
        this.name = name;
        this.location = location;
        this.scale = scale;
        this.shouldCycle = shouldCycle;
        this.alignment = alignment;
        this.billboard = billboard;
        this.hasBackground = hasBackground;
        this.texts = texts;
        spawn();
    }

    private void spawn() {
        display = (TextDisplay) location.getWorld().spawnEntity(location, EntityType.TEXT_DISPLAY);
        display.setText(texts[0]);
        display.setBillboard(billboard);
        display.setAlignment(alignment);
        display.setBrightness(new Display.Brightness(10, 10));

        Transformation oldTransformation = display.getTransformation();
        Transformation newTransformation = new Transformation(oldTransformation.getTranslation(), oldTransformation.getLeftRotation(), scale, oldTransformation.getRightRotation());
        display.setTransformation(newTransformation);

        if (!hasBackground) {
            display.setBackgroundColor(Color.fromARGB(0, 0, 0, 0));
        }

        display.getPersistentDataContainer().set(nameKey, PersistentDataType.STRING, name);
        display.getPersistentDataContainer().set(shouldCycleKey, PersistentDataType.BOOLEAN, shouldCycle);
        display.getPersistentDataContainer().set(textKey, new StringArrayDataType(), texts);
        display.getPersistentDataContainer().set(indexKey, PersistentDataType.INTEGER, 0);
    }

    public void remove() {
        if (display != null) {
            display.remove();
        }
    }
}
