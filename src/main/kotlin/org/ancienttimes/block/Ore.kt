package org.ancienttimes.block

import net.minecraft.block.SoundType
import net.minecraft.block.material.MapColor
import net.minecraft.block.material.Material
import net.minecraft.creativetab.CreativeTabs
import net.minecraftforge.event.entity.player.PlayerEvent

class Ore(name: String,
          harvestLevel: Int
) : SimpleBlock(name,
    creativeTab = CreativeTabs.BUILDING_BLOCKS,
    hardness = 3f, resistance = 5f,
    harvestTool = "pickaxe", harvestLevel = harvestLevel,
    soundType = SoundType.STONE, mapColor = MapColor.STONE,
    material = Material.ROCK,
    oreDictionaryName = "ore" + name.split("_").first().capitalize()
)