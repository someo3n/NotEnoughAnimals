package io.github.someo3n.notenoughanimals;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.List;
import java.util.Random;

public final class Utility {
    public static Block resolveGround(Block start) {
        Block current = start;

        for (int i = 0; i < 32; i++) {
            Material type = current.getType();

            if (Tag.REPLACEABLE.isTagged(type) || Tag.LEAVES.isTagged(type)) {
                current = current.getRelative(0, -1, 0);
                continue;
            }

            return current;
        }

        return null;
    }

    public static <T> T randomChoice(List<T> list, Random random) {
        return list.get(random.nextInt(list.size()));
    }

    public static boolean hasEquipment(Entity entity) {
        boolean hasEquipment = false;

        if (entity instanceof LivingEntity living && living.getEquipment() != null) {
            hasEquipment = !living.getEquipment().getItemInMainHand().isEmpty() ||
                    !living.getEquipment().getItemInOffHand().isEmpty();
        }

        return hasEquipment;
    }
}
