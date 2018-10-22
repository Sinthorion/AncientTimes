package org.ancienttimes.worldgen.generator

import net.minecraft.block.state.IBlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraft.world.biome.Biome
import net.minecraft.world.chunk.IChunkProvider
import net.minecraft.world.gen.IChunkGenerator
import net.minecraft.world.gen.feature.WorldGenMinable
import net.minecraft.world.gen.feature.WorldGenerator
import net.minecraftforge.fml.common.IWorldGenerator
import org.ancienttimes.AncientBlocks
import org.ancienttimes.worldgen.feature.FigTree
import java.util.*

object Trees: IWorldGenerator {

    private var random: Random? = null
    private var world: World? = null
    private var chunkX: Int = 0
    private var chunkZ: Int = 0

    override fun generate(random: Random, chunkX: Int, chunkZ: Int, world: World, chunkGenerator: IChunkGenerator, chunkProvider: IChunkProvider?) {
        this.random = random
        this.chunkX = chunkX
        this.chunkZ = chunkZ
        this.world = world

        generateTrees(FigTree(false), occurence = 0.2f)
    }

    private fun generateTrees(
            generator: WorldGenerator,
            occurence: Float = 15f,
            minHeight: Int = 64, maxHeight: Int = 128,
            requiredBiome: Biome? = null
    ) {
        assert(0 < minHeight && minHeight < maxHeight && maxHeight < 256)
        val random = random!!
        val world = world!!
        val heightdiff = maxHeight - minHeight

        var total = 2 * random.nextFloat()
        while (total <= occurence) {
            val x = random.nextInt(16) + chunkX * 16 + 8
            val z = random.nextInt(16) + chunkZ * 16 + 8
            val y = world.getHeight(x, z)
            val pos = BlockPos(x, y, z)

            val biome = world.getBiome(pos)
            if (requiredBiome == null || biome == requiredBiome) {
                generator.generate(world, random, pos)
            }

            total += 2 * random.nextFloat()
        }
    }
}