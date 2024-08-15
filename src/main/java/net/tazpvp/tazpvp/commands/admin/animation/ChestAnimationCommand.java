package net.tazpvp.tazpvp.commands.admin.animation;

import lombok.NonNull;
import net.tazpvp.tazpvp.utils.animation.Animation;
import net.tazpvp.tazpvp.utils.animation.Frame;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.data.type.Chest;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;
import world.ntdi.nrcore.utils.command.simple.builder.LabelBuilder;

public class ChestAnimationCommand extends NRCommand {

    public ChestAnimationCommand() {
        super(LabelBuilder.of("chestanim", "tazpvp.sexy").build().make());
    }

    @Override
    public boolean execute(@NonNull final CommandSender sender, @NotNull final @NonNull String[] args) {
        if (!(sender instanceof Player p)) {
            return false;
        }

        Animation animation = new Animation();

        BlockDisplay blockDisplay = p.getLocation().getWorld().spawn(p.getLocation(), BlockDisplay.class);
        blockDisplay.setBlock(Material.CHEST.createBlockData());
        blockDisplay.setGlowing(true);
        blockDisplay.setGlowColorOverride(Color.YELLOW);

        animation.addFrame(new Frame(
                new Vector3f(0.0f, 0.0f, 0.0f),
                new AxisAngle4f(0, 0, 1, 0),
                new Vector3f(1.0f, 1.0f, 1.0f),
                new AxisAngle4f(0, 0, 1, 0)
        ));

        animation.addFrame(new Frame(
                new Vector3f(1.0f, 1.0f, 1.0f),
                new AxisAngle4f((float) Math.toRadians(90), 0, 1, 0),
                new Vector3f(2.0f, 2.0f, 2.0f),
                new AxisAngle4f((float) Math.toRadians(90), 0, 1, 0)
        ));

        animation.addFrame(new Frame(
                new Vector3f(5f, 2f, -3f),
                new AxisAngle4f((float) Math.toRadians(12), 1, 1, 0),
                new Vector3f(5f, 1.0f, 5f),
                new AxisAngle4f(0, 0, 1, 1)
        ));

        animation.playAnimation(blockDisplay, 5000, b -> {
            blockDisplay.remove();
        });
        return super.execute(sender, args);
    }
}
