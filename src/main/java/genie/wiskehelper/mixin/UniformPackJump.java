package genie.wiskehelper.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.world.SpawnHelper;
import net.minecraft.util.math.random.Random;

/**
 * The x and z coordinates are modified by:
 *   x += world.random.nextInt(6) - world.random.nextInt(6);
 *   z += world.random.nextInt(6) - world.random.nextInt(6);
 * I.e. triangular distrubtion between -5 and 5.
 * 
 * This mixin redirects the 1st and 3rd calls to world.random.nextInt(11) - 5
 * and voids the 2nd and 4th calls, so it becomes:
 *   x += world.random.nextInt(11) - 5;
 *   z += world.random.nextInt(11) - 5;
 * I.e. uniform distribution between -5 and 5.
 */
@Mixin(SpawnHelper.class)
public abstract class UniformPackJump {

    @Redirect(
        method="spawnEntitiesInChunk(Lnet/minecraft/entity/SpawnGroup;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/world/chunk/Chunk;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/SpawnHelper$Checker;Lnet/minecraft/world/SpawnHelper$Runner;)V",
        at=@At(
            value="INVOKE",
            target="Lnet/minecraft/util/math/random/Random;nextInt(I)I",
            ordinal=0
        )
    )
    private static int uniformXJump(Random random, int bound) {
        return random.nextInt(11) - 5;
    }

    @Redirect(
        method="spawnEntitiesInChunk(Lnet/minecraft/entity/SpawnGroup;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/world/chunk/Chunk;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/SpawnHelper$Checker;Lnet/minecraft/world/SpawnHelper$Runner;)V",
        at=@At(
            value="INVOKE",
            target="Lnet/minecraft/util/math/random/Random;nextInt(I)I",
            ordinal=2
        )
    )
    private static int uniformZJump(Random random, int bound) {
        return random.nextInt(11) - 5;
    }

    @Redirect(
        method="spawnEntitiesInChunk(Lnet/minecraft/entity/SpawnGroup;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/world/chunk/Chunk;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/SpawnHelper$Checker;Lnet/minecraft/world/SpawnHelper$Runner;)V",
        at=@At(
            value="INVOKE",
            target="Lnet/minecraft/util/math/random/Random;nextInt(I)I",
            ordinal=1
        )
    )
    private static int uniformXJumpTriangular(Random random, int bound) {
        return 0;
    }
    @Redirect(
        method="spawnEntitiesInChunk(Lnet/minecraft/entity/SpawnGroup;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/world/chunk/Chunk;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/SpawnHelper$Checker;Lnet/minecraft/world/SpawnHelper$Runner;)V",
        at=@At(
            value="INVOKE",
            target="Lnet/minecraft/util/math/random/Random;nextInt(I)I",
            ordinal=3
        )
    )
    private static int uniformZJumpTriangular(Random random, int bound) {
        return 0;
    }

}
