package org.ancienttimes

import net.minecraft.block.Block
import net.minecraft.creativetab.CreativeTabs
import org.ancienttimes.item.SimpleBlock

enum class Blocks(val block: Block) {
    COPPER_ORE(SimpleBlock("copper_ore", creativeTab = CreativeTabs.BUILDING_BLOCKS)),
}