package org.ancienttimes.item

import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemTool
import net.minecraftforge.oredict.OreDictionary

class AxeItem: ItemTool {

    constructor(name: String,
                creativeTab: CreativeTabs? = null,
                maxStackSize: Int = 64,
                oreDictionaryName: String? = null,
                material: ToolMaterial
    ) : super(2.0f, -3.0f, material, Companion.EFFECTIVE_ON) {
        this.unlocalizedName = name
        this.creativeTab = creativeTab
        this.maxStackSize = maxStackSize
        if (oreDictionaryName != null) {
            OreDictionary.registerOre(oreDictionaryName, this)
        }
    }

    override fun getDestroySpeed(itemStack: ItemStack, block: IBlockState): Float {
        val material = block.material
        return if (material !== Material.WOOD && material !== Material.PLANTS && material !== Material.VINE)
            super.getDestroySpeed(itemStack, block)
        else this.efficiency
    }

    companion object {
        val EFFECTIVE_ON: Set<Block> = setOf(
                Blocks.LOG, Blocks.LOG2, Blocks.PLANKS,
                Blocks.BOOKSHELF, Blocks.CHEST, Blocks.LADDER,
                Blocks.PUMPKIN, Blocks.LIT_PUMPKIN, Blocks.MELON_BLOCK,
                Blocks.WOODEN_BUTTON, Blocks.WOODEN_PRESSURE_PLATE
        )
    }
}
