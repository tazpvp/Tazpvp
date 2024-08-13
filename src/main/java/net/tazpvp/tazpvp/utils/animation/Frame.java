package net.tazpvp.tazpvp.utils.animation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

@Getter
@AllArgsConstructor
public class Frame {
    private final Vector3f translation;
    private final AxisAngle4f leftRotation;
    private final Vector3f scale;
    private final AxisAngle4f rightRotation;

    public Transformation toTransformation() {
        return new Transformation(translation, leftRotation, scale, rightRotation);
    }
}
