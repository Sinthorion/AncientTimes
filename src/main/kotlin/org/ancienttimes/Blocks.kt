package org.ancienttimes

import net.minecraft.block.Block
import net.minecraft.block.SoundType
import net.minecraft.block.material.MapColor
import net.minecraft.creativetab.CreativeTabs
import org.ancienttimes.block.SimpleBlock

enum class Blocks(val block: Block) {
    COPPER_ORE(SimpleBlock("copper_ore",
            creativeTab = CreativeTabs.BUILDING_BLOCKS,
            hardness = 3f, resistance = 5f,
            harvestTool = "pickaxe", harvestLevel = 1,
            soundType = SoundType.STONE, mapColor = MapColor.STONE
    )),
    COPPER_BLOCK(SimpleBlock("copper_block",
            creativeTab = CreativeTabs.BUILDING_BLOCKS,
            hardness = 4f, resistance = 9f,
            harvestTool = "pickaxe", harvestLevel = 1,
            soundType = SoundType.METAL, mapColor = MapColor.YELLOW
    ))
}