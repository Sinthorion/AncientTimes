package org.ancienttimes.worldgen.generator

import net.minecraft.block.state.IBlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraft.world.biome.Biome
import net.minecraft.world.chunk.IChunkProvider
import net.minecraft.world.gen.IChunkGenerator
import net.minecraft.world.gen.feature.WorldGenMinable
import net.minecraftforge.fml.common.IWorldGenerator
import org.ancienttimes.AncientBlocks
import java.util.*

object Ores: IWorldGenerator {

    private var random: Random? = null
    private var world: World? = null
    private var chunkX: Int = 0
    private var chunkZ: Int = 0

    override fun generate(random: Random, chunkX: Int, chunkZ: Int, world: World, chunkGenerator: IChunkGenerator, chunkProvider: IChunkProvider?) {
        Ores.random = random
        Ores.chunkX = chunkX
        Ores.chunkZ = chunkZ
        Ores.world = world

        generateOre(AncientBlocks.TIN_ORE.block.defaultState, occurence = 2f, maxHeight = 96)
        generateOre(AncientBlocks.COPPER_ORE.block.defaultState, occurence = 20f, maxHeight = 96)
    }

    private fun generateOre(
            ore: IBlockState,
            occurence: Float = 1f, size: Int = 10,
            minHeight: Int = 1, maxHeight: Int = 128,
            requiredBiome: Biome? = null
    ) {
        assert(0 < minHeight && minHeight < maxHeight && maxHeight < 256)
        val random = random!!
        val world = world!!

        val generator = WorldGenMinable(ore, size)
        val heightdiff = maxHeight - minHeight

        var total = 2 * random.nextFloat()
        while (total <= occurence) {
            val x = random.nextInt(16) + chunkX * 16
            val y = minHeight + random.nextInt(heightdiff)
            val z = random.nextInt(16) + chunkZ * 16
            val pos = BlockPos(x, y, z)

            val biome = world.getBiome(pos)
            if (requiredBiome == null || biome == requiredBiome) {
                generator.generate(world, random, pos)
            }

            total += 2 * random.nextFloat()
        }
    }
}