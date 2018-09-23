package org.ancienttimes.block

import net.minecraft.block.Block
import net.minecraft.block.SoundType
import net.minecraft.block.material.MapColor
import net.minecraft.block.material.Material
import net.minecraft.creativetab.CreativeTabs
import net.minecraftforge.oredict.OreDictionary

class SimpleBlock : Block {

    constructor(name: String,
                creativeTab: CreativeTabs? = null,
                material: Material = Material.ROCK,
                mapColor: MapColor = MapColor.STONE,
                hardness: Float = 0.5f,
                resistance: Float = 0.5f,
                harvestTool: String = "",
                harvestLevel: Int = 0,
                soundType: SoundType = SoundType.STONE,
                oreDictionaryName: String? = null

    ) : super(material, mapColor) {
        this.unlocalizedName = name
        if (creativeTab != null) {
            this.setCreativeTab(creativeTab)
        }
        this.blockHardness = hardness
        this.blockResistance = resistance
        this.setHarvestLevel(harvestTool, harvestLevel)
        this.soundType = soundType
        if (oreDictionaryName != null) {
            OreDictionary.registerOre(oreDictionaryName, this)
        }
    }
}