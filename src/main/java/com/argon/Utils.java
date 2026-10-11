package com.argon;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public final class Utils {

    // Prevent instantiation
    private Utils() {}

    public static boolean isAir(Level world, BlockPos pos) {
        return world.getBlockState(pos).isAir();
    }

    public static BlockState blockAbove(Level world, BlockPos pos, int n) {
        return world.getBlockState(pos.above(n));
    }

    /** Squared distance — no sqrt, safe for comparisons */
    public static double distSq(double x1, double y1, double z1,
                                double x2, double y2, double z2) {
        double dx = x1 - x2, dy = y1 - y2, dz = z1 - z2;
        return dx * dx + dy * dy + dz * dz;
    }

    /** Range check using squared distance — no sqrt */
    public static boolean withinRange(double x1, double y1, double z1,
                                      double x2, double y2, double z2,
                                      double range) {
        return distSq(x1, y1, z1, x2, y2, z2) <= range * range;
    }

    /** Safe division — returns 0 instead of NaN/Infinity */
    public static double safeDiv(double a, double b) {
        return b != 0.0 ? a / b : 0.0;
    }

    /** Clamp without Math.min/max overhead (inlined by JIT, but explicit) */
    public static double clamp(double v, double min, double max) {
        return v < min ? min : (v > max ? max : v);
    }

    /** String constant cache — avoids repeated string allocation for common keys */
    public static final String KEY_ITEMS    = "items";
    public static final String KEY_BLOCKS   = "blocks";
    public static final String KEY_ENTITIES = "entities";
    public static final String KEY_RECIPES  = "recipes";
}