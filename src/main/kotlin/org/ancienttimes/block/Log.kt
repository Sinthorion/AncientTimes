package org.ancienttimes.block

import net.minecraft.block.BlockLog
import net.minecraft.block.BlockPlanks
import net.minecraft.block.SoundType
import net.minecraft.block.material.MapColor
import net.minecraft.block.material.Material
import net.minecraft.block.properties.PropertyBool
import net.minecraft.block.properties.PropertyEnum
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
import net.minecraftforge.oredict.OreDictionary
import java.util.*

class Log: SimpleBlock {

    companion object StateProperties {
        val FACING = BlockLog.LOG_AXIS
    }

    constructor(name: String,
                oreDictionaryName: String? = null
    ): super(name,
            creativeTab = CreativeTabs.BUILDING_BLOCKS,
            material = Material.WOOD,
            hardness = 2.0f,
            harvestTool = "axe",
            harvestLevel = 0,
            soundType = SoundType.PLANT,
            oreDictionaryName = oreDictionaryName
    ) {
        defaultState = this.blockState.baseState.withProperty(StateProperties.FACING, BlockLog.EnumAxis.Y)
    }

    @SideOnly(Side.CLIENT)
    override fun shouldSideBeRendered(state: IBlockState, world: IBlockAccess, pos: BlockPos, side: EnumFacing): Boolean {
        return Blocks.LEAVES.shouldSideBeRendered(state, world, pos, side)
    }

    override fun canSustainLeaves(state: IBlockState, world: IBlockAccess, pos: BlockPos): Boolean {
        return true
    }

    override fun createBlockState(): BlockStateContainer {
        return BlockStateContainer(this, StateProperties.FACING)
    }

    override fun getStateFromMeta(meta: Int): IBlockState {
        return this.defaultState.withProperty(StateProperties.FACING, BlockLog.EnumAxis.values()[meta % 4])

    }

    override fun getMetaFromState(state: IBlockState): Int {
        return state.getValue(StateProperties.FACING).ordinal
    }
}