package org.ancienttimes.block

import net.minecraft.block.SoundType
import net.minecraft.block.material.MapColor
import net.minecraft.block.material.Material
import net.minecraft.creativetab.CreativeTabs

class Planks : SimpleBlock {

    constructor(name: String) : super(name,
        creativeTab = CreativeTabs.BUILDING_BLOCKS,
        hardness = 2f, resistance = 5f,
        harvestTool = "axe", harvestLevel = 0,
        soundType = SoundType.WOOD, mapColor = MapColor.WOOD,
        material = Material.WOOD,
        oreDictionaryName = "plankWood"
    )
}