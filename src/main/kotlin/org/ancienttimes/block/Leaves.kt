package org.ancienttimes.block

import net.minecraft.block.Block
import net.minecraft.block.SoundType
import net.minecraft.block.material.MapColor
import net.minecraft.block.material.Material
import net.minecraft.block.properties.PropertyBool
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.init.Blocks
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.BlockRenderLayer
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumParticleTypes
import net.minecraft.util.NonNullList
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.common.IShearable
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.util.*

open class Leaves: SimpleBlock, IShearable {

    companion object StateProperties {
        val DECAYABLE = PropertyBool.create("decayable")
        val CHECK_DECAY = PropertyBool.create("check_decay")
    }

    internal var surroundings: IntArray = IntArray(32768)

    protected var saplingDropChance: Int
    protected var sapling: Block

    constructor(name: String,
                sapling: Block,
                oreDictionaryName: String? = null,
                saplingDropChance: Int = 20
    ): super(name,
            creativeTab = CreativeTabs.DECORATIONS,
            material = Material.LEAVES,
            mapColor = MapColor.FOLIAGE,
            hardness = 0.2f,
            soundType = SoundType.PLANT,
            oreDictionaryName = oreDictionaryName
    ) {
        this.saplingDropChance = saplingDropChance
        this.sapling = sapling

        this.tickRandomly = true
        this.lightOpacity = 1
        defaultState = this.blockState.baseState.withProperty(StateProperties.DECAYABLE, true).withProperty(StateProperties.CHECK_DECAY, true)
    }

    override fun breakBlock(world: World, pos: BlockPos, state: IBlockState) {

        if (world.isAreaLoaded(pos.add(-2, -2, -2), pos.add(2, 2, 2))) {
            for (x in -1..1) {
                for (y in -1..1) {
                    for (z in -1..1) {
                        val blockpos = pos.add(x, y, z)
                        val neighbourState = world.getBlockState(blockpos)

                        if (neighbourState.block.isLeaves(neighbourState, world, blockpos)) {
                            neighbourState.block.beginLeavesDecay(neighbourState, world, blockpos)
                        }
                    }
                }
            }
        }
    }

    override fun updateTick(world: World, pos: BlockPos, state: IBlockState, rand: Random) {
        if (!world.isRemote) {
            if (state.getValue(StateProperties.CHECK_DECAY) && state.getValue(StateProperties.DECAYABLE)) {

                if (!world.isAreaLoaded(pos, 1)) {
                    // Forge: prevent decaying leaves from updating neighbors and loading unloaded chunks
                    return
                }
                if (world.isAreaLoaded(pos, 6)) {
                // Forge: extend range from 5 to 6 to account for neighbor checks in world.markAndNotifyBlock -> world.updateObservingBlocksAt

                    for (x in -4..4) {
                        for (y in -4..4) {
                            for (z in -4..4) {
                                val tmpPos = pos.add(x, y, z)
                                val iblockstate = world.getBlockState(tmpPos)
                                val block = iblockstate.block

                                if (!block.canSustainLeaves(iblockstate, world, tmpPos)) {
                                    if (block.isLeaves(iblockstate, world, tmpPos)) {
                                        this.surroundings[(x + 16) * 1024 + (y + 16) * 32 + z + 16] = -2
                                    } else {
                                        this.surroundings[(x + 16) * 1024 + (y + 16) * 32 + z + 16] = -1
                                    }
                                } else {
                                    this.surroundings[(x + 16) * 1024 + (y + 16) * 32 + z + 16] = 0
                                }
                            }
                        }
                    }

                    for (decayStatus in 1..4) {
                        for (x in -4..4) {
                            for (y in -4..4) {
                                for (z in -4..4) {
                                    if (this.surroundings[(x + 16) * 1024 + (y + 16) * 32 + z + 16] == decayStatus - 1) {
                                        if (this.surroundings[(x + 16 - 1) * 1024 + (y + 16) * 32 + z + 16] == -2) {
                                            this.surroundings[(x + 16 - 1) * 1024 + (y + 16) * 32 + z + 16] = decayStatus
                                        }

                                        if (this.surroundings[(x + 16 + 1) * 1024 + (y + 16) * 32 + z + 16] == -2) {
                                            this.surroundings[(x + 16 + 1) * 1024 + (y + 16) * 32 + z + 16] = decayStatus
                                        }

                                        if (this.surroundings[(x + 16) * 1024 + (y + 16 - 1) * 32 + z + 16] == -2) {
                                            this.surroundings[(x + 16) * 1024 + (y + 16 - 1) * 32 + z + 16] = decayStatus
                                        }

                                        if (this.surroundings[(x + 16) * 1024 + (y + 16 + 1) * 32 + z + 16] == -2) {
                                            this.surroundings[(x + 16) * 1024 + (y + 16 + 1) * 32 + z + 16] = decayStatus
                                        }

                                        if (this.surroundings[(x + 16) * 1024 + (y + 16) * 32 + (z + 16 - 1)] == -2) {
                                            this.surroundings[(x + 16) * 1024 + (y + 16) * 32 + (z + 16 - 1)] = decayStatus
                                        }

                                        if (this.surroundings[(x + 16) * 1024 + (y + 16) * 32 + z + 16 + 1] == -2) {
                                            this.surroundings[(x + 16) * 1024 + (y + 16) * 32 + z + 16 + 1] = decayStatus
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                val decay = this.surroundings[16912]

                if (decay >= 0) {
                    world.setBlockState(pos, state.withProperty(StateProperties.CHECK_DECAY, java.lang.Boolean.valueOf(false)), 4)
                } else {
                    this.destroy(world, pos)
                }
            }
        }
    }

    private fun destroy(worldIn: World, pos: BlockPos) {
        this.dropBlockAsItem(worldIn, pos, worldIn.getBlockState(pos), 0)
        worldIn.setBlockToAir(pos)
    }

    @SideOnly(Side.CLIENT)
    override fun randomDisplayTick(state: IBlockState, world: World, pos: BlockPos, rand: Random) {
        if (world.isRainingAt(pos.up()) && !world.getBlockState(pos.down()).isTopSolid && rand.nextInt(15) == 1) {
            val x = (pos.x.toFloat() + rand.nextFloat()).toDouble()
            val y = pos.y.toDouble() - 0.05
            val z = (pos.z.toFloat() + rand.nextFloat()).toDouble()
            world.spawnParticle(EnumParticleTypes.DRIP_WATER, x, y, z, 0.0, 0.0, 0.0)
        }
    }

    override fun quantityDropped(random: Random): Int {
        return if (random.nextInt(saplingDropChance) == 0) 1 else 0
    }

    override fun getItemDropped(state: IBlockState, rand: Random, fortune: Int): Item {
        println("dropping " + Item.getItemFromBlock(sapling))
        return Item.getItemFromBlock(sapling)
    }

    override fun dropBlockAsItemWithChance(world: World, pos: BlockPos, state: IBlockState, chance: Float, fortune: Int) {
        super.dropBlockAsItemWithChance(world, pos, state, chance, fortune)
    }

    override fun isOpaqueCube(state: IBlockState): Boolean {
        return Blocks.LEAVES.isOpaqueCube(state) // state is never used
    }

    @SideOnly(Side.CLIENT)
    override fun getBlockLayer(): BlockRenderLayer {
        return Blocks.LEAVES.getBlockLayer()
    }

    override fun causesSuffocation(state: IBlockState): Boolean {
        return false
    }

    override fun isShearable(item: ItemStack, world: IBlockAccess, pos: BlockPos): Boolean {
        return true
    }

    override fun onSheared(itemStack: ItemStack, world: IBlockAccess, pos: BlockPos, metavalue: Int): MutableList<ItemStack> {
        return NonNullList.withSize(1, ItemStack(this, 1))
    }

    override fun isLeaves(state: IBlockState, world: IBlockAccess, pos: BlockPos): Boolean {
        return true
    }

    override fun beginLeavesDecay(state: IBlockState, world: World, pos: BlockPos) {
        if (!(state.getValue(StateProperties.CHECK_DECAY) as Boolean)) {
            world.setBlockState(pos, state.withProperty(StateProperties.CHECK_DECAY, true), 4)
        }
    }

    /*override fun getDrops(drops: NonNullList<ItemStack>, world: IBlockAccess, pos: BlockPos, state: IBlockState, fortune: Int) {
        val rand = if (world is World) world.rand else Random()
        var chance = this.saplingDropChance

        if (fortune > 0) {
            chance -= 2 shl fortune
            if (chance < 10) chance = 10
        }

        if (rand.nextInt(chance) == 0) {
            val drop = ItemStack(getItemDropped(state, rand, fortune), 1, damageDropped(state))
            if (!drop.isEmpty)
                drops.add(drop)
        }

        chance = 200
        if (fortune > 0) {
            chance -= 10 shl fortune
            if (chance < 40) chance = 40
        }

        this.captureDrops(true)
        drops.addAll(this.captureDrops(false))
    }*/


    @SideOnly(Side.CLIENT)
    override fun shouldSideBeRendered(state: IBlockState, world: IBlockAccess, pos: BlockPos, side: EnumFacing): Boolean {
        return Blocks.LEAVES.shouldSideBeRendered(state, world, pos, side)
    }

    override fun createBlockState(): BlockStateContainer {
        return BlockStateContainer(this, StateProperties.DECAYABLE, StateProperties.CHECK_DECAY)
    }

    override fun getStateFromMeta(meta: Int): IBlockState {
        return this.defaultState
                .withProperty(StateProperties.DECAYABLE, meta and 1 > 0)
                .withProperty(StateProperties.CHECK_DECAY, meta and 2 > 0)

    }

    override fun getMetaFromState(state: IBlockState): Int {
        var meta = 0
        if (state.getValue(StateProperties.DECAYABLE)) meta += 1
        if (state.getValue(StateProperties.CHECK_DECAY)) meta += 2
        return meta
    }
}