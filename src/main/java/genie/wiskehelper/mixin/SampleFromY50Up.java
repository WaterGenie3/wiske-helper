package genie.wiskehelper.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Heightmap;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;

/**
 * This mixin sample the y coordinate from 50 to heightmap + 1
 * instead of world bottom to heightmap + 1.
 * 
 * For heightmap < 50, it just forces the y on the heightmap + 1 only. 
 */
@Mixin(SpawnHelper.class)
public abstract class SampleFromY50Up {

    @Inject(
        method="getRandomPosInChunkSection",
        at=@At("HEAD"),
        cancellable=true
    )
    private static void getRandomPosWithForce50Y(World world, WorldChunk chunk, CallbackInfoReturnable<BlockPos> cir) {
        ChunkPos chunkPos = chunk.getPos();
        int x = chunkPos.getStartX() + world.random.nextInt(16);
        int z = chunkPos.getStartZ() + world.random.nextInt(16);
        int y = chunk.sampleHeightmap(Heightmap.Type.WORLD_SURFACE, x, z) + 1;
        // Use top-most y until > 50.
        if (y <= 50) {
            cir.setReturnValue(new BlockPos(x, y, z));
        } else {
            int sampleY = MathHelper.nextBetween(world.random, 50, y);
            cir.setReturnValue(new BlockPos(x, sampleY, z));
        }
    }

}
