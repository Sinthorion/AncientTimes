package org.ancienttimes.worldgen.feature

import net.minecraft.block.BlockSapling
import net.minecraft.block.state.IBlockState
import net.minecraft.init.Blocks
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraft.world.gen.feature.WorldGenAbstractTree
import org.ancienttimes.AncientBlocks
import java.util.*

class FigTree(doBlockNotify: Boolean): WorldGenAbstractTree(doBlockNotify) {

//    val LOG = Blocks.LOG.defaultState.withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.SPRUCE)
//    val LEAF = Blocks.LEAVES.defaultState.withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.BIRCH)
//            .withProperty(BlockOldLeaf.CHECK_DECAY, false)

    val LOG: IBlockState by lazy { AncientBlocks.FIG_LOG.block.defaultState }
    val LEAF: IBlockState by lazy { AncientBlocks.FIG_LEAVES.block.defaultState }

    override fun generate(world: World, random: Random, pos: BlockPos): Boolean {
        val height = 4 + random.nextInt(2)

        // Part 1: check space
        if (pos.y <= 0 || pos.y + 1 + height >= world.height) {
            return false
        }
        for (y in pos.y..(pos.y + 1 + height)) {

            val scanRadius = when {
                y == pos.y -> 0
                y >= pos.y + 1 + height - 2 -> 2
                else -> 1
            }

            for (x in (pos.x - scanRadius)..(pos.x + scanRadius)) {
                for (z in (pos.z - scanRadius)..(pos.z + scanRadius)) {
                    if (!this.isReplaceable(world, BlockPos(x, y, z))) {
                        return false
                    }
                }
            }
        }

        val down = pos.down()
        val groundBlockstate = world.getBlockState(down)
        val isSoil = groundBlockstate.block.canSustainPlant(groundBlockstate, world, down, EnumFacing.UP, Blocks.SAPLING as BlockSapling)
        if (!isSoil) {
            return false
        }

        // Part 2: Build the tree

        groundBlockstate.block.onPlantGrow(groundBlockstate, world, down, pos)

        // leaves
        for (y in (pos.y - 3 + height)..(pos.y + height)) {
            var i = y - pos.y - height
            val leafRadius = 1 - i / 2

            for (x in (pos.x - leafRadius)..(pos.x + leafRadius)) {
                val j = x - pos.x

                for (z in (pos.z - leafRadius)..(pos.z + leafRadius)) {
                    val k = z - pos.z
                    if (Math.abs(j) != leafRadius || Math.abs(k) != leafRadius || random.nextInt(2) != 0 && i != 0) {
                        val blockpos = BlockPos(x, y, z)
                        val state = world.getBlockState(blockpos)
                        if (state.block.isAir(state, world, blockpos)) {
                            this.setBlockAndNotifyAdequately(world, blockpos, LEAF)
                        }
                    }
                }
            }
        }

        // log
        for (y in 0..(height - 1)) {
            val logPos = pos.up(y)
            val state2 = world.getBlockState(logPos)
//            if (state2.block.isAir(state2, world, logPos) || state2.block.isLeaves(state2, world, logPos)) {
            if (state2.block.isAir(state2, world, logPos) || state2 == LEAF) {
                this.setBlockAndNotifyAdequately(world, logPos, LOG)
            }
        }

        return true
    }
}