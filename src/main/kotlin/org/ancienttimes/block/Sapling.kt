package org.ancienttimes.block

import net.minecraft.block.*
import net.minecraft.block.material.Material
import net.minecraft.block.properties.IProperty
import net.minecraft.block.properties.PropertyInteger
import net.minecraft.block.state.BlockFaceShape
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.util.BlockRenderLayer
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraft.world.gen.feature.*
import net.minecraftforge.common.EnumPlantType
import net.minecraftforge.common.IPlantable
import net.minecraftforge.event.terraingen.TerrainGen
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.util.*

class Sapling: SimpleBlock, IPlantable, IGrowable {

    companion object StateProperties {
        val STAGE = PropertyInteger.create("stage", 0, 1)
    }

    protected val SAPLING_AABB = AxisAlignedBB(0.09999999403953552, 0.0, 0.09999999403953552, 0.8999999761581421, 0.800000011920929, 0.8999999761581421)

    private var generator: WorldGenerator

    constructor(name: String,
                generator: WorldGenerator
    ): super(name,
            creativeTab = CreativeTabs.DECORATIONS,
            material = Material.PLANTS,
            mapColor = Material.PLANTS.materialMapColor
    ) {
        this.setTickRandomly(true)
        this.generator = generator
        this.defaultState = this.blockState.baseState.withProperty(STAGE, Integer.valueOf(0)!!)
    }

    override fun getBoundingBox(state: IBlockState, source: IBlockAccess, pos: BlockPos): AxisAlignedBB {
        return SAPLING_AABB
    }

    override fun getCollisionBoundingBox(blockState: IBlockState, worldIn: IBlockAccess, pos: BlockPos): AxisAlignedBB? {
        return Block.NULL_AABB
    }

    override fun canPlaceBlockAt(world: World, pos: BlockPos): Boolean {
        val soil = world.getBlockState(pos.down())
        return super.canPlaceBlockAt(world, pos) && soil.block.canSustainPlant(soil, world, pos.down(), net.minecraft.util.EnumFacing.UP, this)
    }

    override fun updateTick(worldIn: World, pos: BlockPos, state: IBlockState, rand: Random) {
        if (!worldIn.isRemote) {
            super.updateTick(worldIn, pos, state, rand)

            if (!worldIn.isAreaLoaded(pos, 1)) return  // Forge: prevent loading unloaded chunks when checking neighbor's light
            if (worldIn.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0) {
                this.grow(worldIn, pos, state, rand)
            }
        }
    }

    fun grow(worldIn: World, pos: BlockPos, state: IBlockState, rand: Random) {
        if ((state.getValue(STAGE) as Int).toInt() == 0) {
            worldIn.setBlockState(pos, state.cycleProperty(STAGE), 4)
        } else {
            this.generateTree(worldIn, pos, state, rand)
        }
    }

    fun generateTree(world: World, pos: BlockPos, state: IBlockState, random: Random) {
        if (!TerrainGen.saplingGrowTree(world, random, pos)) {
            return
        }

        val airState = Blocks.AIR.defaultState

        world.setBlockState(pos, airState, 4)

        if (!generator.generate(world, random, pos)) {
            world.setBlockState(pos, state, 4)
        }
    }

    /**
     * Whether this IGrowable can grow
     */
    override fun canGrow(worldIn: World, pos: BlockPos, state: IBlockState, isClient: Boolean): Boolean {
        return true
    }

    override fun canUseBonemeal(worldIn: World, rand: Random, pos: BlockPos, state: IBlockState): Boolean {
        return worldIn.rand.nextFloat().toDouble() < 0.45
    }

    override fun grow(worldIn: World, rand: Random, pos: BlockPos, state: IBlockState) {
        this.grow(worldIn, pos, state, rand)
    }

    override fun getPlantType(world: IBlockAccess?, pos: BlockPos): EnumPlantType {
        return EnumPlantType.Plains
    }

    override fun getPlant(world: IBlockAccess, pos: BlockPos): IBlockState {
        val state = world.getBlockState(pos)
        return if (state.block == this) state else defaultState
    }

    override fun getItem(world: World, pos: BlockPos, state: IBlockState): ItemStack {
        return super.getItem(world, pos, state)
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    override fun getStateFromMeta(meta: Int): IBlockState {
        return this.defaultState.withProperty(STAGE, meta)
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    override fun getMetaFromState(state: IBlockState): Int {
        return  state.getValue(STAGE)
    }

    override fun createBlockState(): BlockStateContainer {
        return BlockStateContainer(this, *arrayOf<IProperty<*>>(STAGE))
    }

    override fun getBlockFaceShape(worldIn: IBlockAccess, state: IBlockState, pos: BlockPos, face: EnumFacing): BlockFaceShape {
        return BlockFaceShape.UNDEFINED
    }

    @SideOnly(Side.CLIENT)
    override fun getBlockLayer(): BlockRenderLayer {
        return BlockRenderLayer.CUTOUT
    }

    override fun isOpaqueCube(state: IBlockState): Boolean {
        return false
    }

    override fun isFullCube(state: IBlockState): Boolean {
        return false
    }
}