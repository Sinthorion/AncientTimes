package org.ancienttimes.block

import net.minecraft.block.SoundType
import net.minecraft.block.material.MapColor
import net.minecraft.block.material.Material
import net.minecraft.block.properties.PropertyEnum
import net.minecraft.block.state.BlockFaceShape
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.EntityLivingBase
import net.minecraft.item.ItemSlab
import net.minecraft.util.EnumFacing
import net.minecraft.util.IStringSerializable
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.common.ForgeModContainer
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import org.ancienttimes.item.itemblock.SlabItem
import java.util.*

open class Slab : SimpleBlock {

    enum class Half(val side: EnumFacing?, val align: AxisAlignedBB): IStringSerializable {
        BOTTOM(EnumFacing.DOWN, AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.5, 1.0)),
        TOP(EnumFacing.UP, AxisAlignedBB(0.0, 0.5, 0.0, 1.0, 1.0, 1.0)),
        NORTH(EnumFacing.NORTH, AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 0.5)),
        SOUTH(EnumFacing.SOUTH, AxisAlignedBB(0.0, 0.0, 0.5, 1.0, 1.0, 1.0)),
        WEST(EnumFacing.WEST, AxisAlignedBB(0.0, 0.0, 0.0, 0.5, 1.0, 1.0)),
        EAST(EnumFacing.EAST, AxisAlignedBB(0.5, 0.0, 0.0, 1.0, 1.0, 1.0)),
        FULL(null, AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0)),
        ;

        override fun getName(): String {
            return this.name.toLowerCase()
        }

        companion object {
            fun forFacing(side: EnumFacing): Half {
                return Half.values()[side.ordinal]
            }
        }
    }

    companion object StateProperties {
        val HALF = PropertyEnum.create("half", Half::class.java)
    }

    constructor(name: String,
                creativeTab: CreativeTabs = CreativeTabs.BUILDING_BLOCKS,
                material: Material = Material.ROCK,
                mapColor: MapColor = material.materialMapColor,
                hardness: Float = 0.5f,
                resistance: Float = hardness * 5f,
                harvestTool: String = "",
                harvestLevel: Int = 0,
                soundType: SoundType = SoundType.STONE,
                oreDictionaryName: String? = null

    ) : super(name,
        creativeTab, material, mapColor, hardness, resistance, harvestTool, harvestLevel, soundType, oreDictionaryName,
        itemBlockClass = SlabItem::class
    ) {
        this.defaultState = this.blockState.baseState.withProperty(HALF, Half.BOTTOM)
    }

    override fun getStateFromMeta(meta: Int): IBlockState {
        return this.defaultState.withProperty(HALF, Half.values()[meta])
    }

    override fun getMetaFromState(state: IBlockState): Int {
        return state.getValue(HALF).ordinal
    }

    override fun createBlockState(): BlockStateContainer {
        return BlockStateContainer(this, HALF)
    }

    fun isDouble(state: IBlockState) : Boolean {
        return state.getValue(HALF) == Half.FULL
    }

    override fun canSilkHarvest(): Boolean {
        return false
    }

    override fun getBoundingBox(state: IBlockState, source: IBlockAccess, pos: BlockPos): AxisAlignedBB {
        return state.getValue(HALF).align
    }

    override fun isTopSolid(state: IBlockState): Boolean {
        return this.isDouble(state) || state.getValue<Half>(HALF) == Half.TOP
    }

    override fun getBlockFaceShape(worldIn: IBlockAccess, state: IBlockState, pos: BlockPos, face: EnumFacing): BlockFaceShape {
        return if (this.isDouble(state) || state.getValue(HALF).side == face) {
            BlockFaceShape.SOLID
        } else {
            BlockFaceShape.UNDEFINED
        }
    }

    override fun isOpaqueCube(state: IBlockState): Boolean {
        return this.isDouble(state)
    }

    override fun doesSideBlockRendering(state: IBlockState, world: IBlockAccess, pos: BlockPos, face: EnumFacing): Boolean {
        if (ForgeModContainer.disableStairSlabCulling)
            return super.doesSideBlockRendering(state, world, pos, face)

        val fullSide = state.getValue(HALF).side
        return fullSide == null || fullSide == face
    }

    override fun getStateForPlacement(world: World, pos: BlockPos, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float, meta: Int, placer: EntityLivingBase): IBlockState {
        val placedState = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer)

        val state = world.getBlockState(pos)
        if (state.block is Slab && state.getValue(HALF) != Half.FULL) {
            return placedState.withProperty(HALF, Half.FULL)
        }

        return if (this.isDouble(placedState)) {
            placedState
        } else {
            placedState.withProperty(HALF, Half.forFacing(facing.opposite))
        }
    }

    override fun quantityDropped(state: IBlockState, fortune: Int, random: Random): Int {
        return if (this.isDouble(state)) 2 else 1
    }

    override fun isFullCube(state: IBlockState): Boolean {
        return this.isDouble(state)
    }



    @SideOnly(Side.CLIENT)
    override fun shouldSideBeRendered(state: IBlockState, world: IBlockAccess, pos: BlockPos, side: EnumFacing): Boolean {
        if (this.isDouble(state)) {
            return super.shouldSideBeRendered(state, world, pos, side)
        }

        val openSide = state.getValue(HALF).side!!.opposite
        if (side == openSide) {
            return true
        }

        return super.shouldSideBeRendered(state, world, pos, side)
    }
}