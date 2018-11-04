package org.ancienttimes.block

import net.minecraft.block.SoundType
import net.minecraft.block.material.MapColor
import net.minecraft.block.material.Material
import net.minecraft.creativetab.CreativeTabs

class MetalStorage(name: String,
                   hardness: Float,
                   resistance: Float,
                   mapColor: MapColor = MapColor.IRON
) : SimpleBlock(name,
    creativeTab = CreativeTabs.BUILDING_BLOCKS,
    hardness = hardness, resistance = resistance,
    harvestTool = "pickaxe", harvestLevel = 1,
    soundType = SoundType.METAL, mapColor = mapColor,
    material = Material.IRON,
    oreDictionaryName = "block" + name.split("_").first().capitalize()
)