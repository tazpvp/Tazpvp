package net.tazpvp.tazpvp.utils.animation;

import net.tazpvp.tazpvp.Tazpvp;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Animation {
    private final List<Frame> frames = new ArrayList<>();
    private int totalFrames;

    public void addFrame(Frame frame) {
        frames.add(frame);
        if (frames.size() > 1) {
            totalFrames = (frames.size() - 1) * 20; // 20 fwames per transition uwu
        }
    }

    public Transformation getCurrentTransformation(float progress) {
        if (frames.size() < 2) {
            throw new IllegalStateException("Need at least two frames for an animation.");
        }
        progress = Easing.easeInOut(progress);

        float frameProgress = progress * (frames.size() - 1);
        int frameIndex = (int) frameProgress;
        Frame startFrame = frames.get(frameIndex);
        Frame endFrame = frames.get(Math.min(frameIndex + 1, frames.size() - 1));

        return interpolate(startFrame, endFrame, frameProgress - frameIndex);
    }

    private Transformation interpolate(Frame start, Frame end, float t) {
        Vector3f translation = new Vector3f();
        start.getTranslation().lerp(end.getTranslation(), t, translation);

        AxisAngle4f leftRotation = new AxisAngle4f();
        interpolateRotation(start.getLeftRotation(), end.getLeftRotation(), t, leftRotation);

        Vector3f scale = new Vector3f();
        start.getScale().lerp(end.getScale(), t, scale);

        AxisAngle4f rightRotation = new AxisAngle4f();
        interpolateRotation(start.getRightRotation(), end.getRightRotation(), t, rightRotation);

        return new Transformation(translation, leftRotation, scale, rightRotation);
    }

    private void interpolateRotation(AxisAngle4f start, AxisAngle4f end, float t, AxisAngle4f result) {
        float angle = start.angle + t * (end.angle - start.angle);
        result.set(angle, start.x + t * (end.x - start.x), start.y + t * (end.y - start.y), start.z + t * (end.z - start.z));
    }

    public void playAnimation(BlockDisplay blockDisplay, long duration, Consumer<BlockDisplay> afterFinish) {
        long startTime = System.currentTimeMillis();

        new BukkitRunnable() {
            @Override
            public void run() {
                float progress = (float) (System.currentTimeMillis() - startTime) / duration;
                if (progress >= 1.0f) {
                    blockDisplay.setTransformation(frames.get(frames.size() - 1).toTransformation());
                    cancel();
                    afterFinish.accept(blockDisplay);
                    return;
                }

                blockDisplay.setTransformation(getCurrentTransformation(progress));
            }
        }.runTaskTimer(Tazpvp.getInstance(), 0L, 1L);
    }
}