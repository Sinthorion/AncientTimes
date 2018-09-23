package org.ancienttimes.item

import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.creativetab.CreativeTabs

class SimpleBlock : Block {

    constructor(name: String,
                creativeTab: CreativeTabs? = null,
                material: Material = Material.ROCK,
                hardness: Float = 3f,
                resistance: Float = 5f,
                harvestTool: String = "pickaxe",
                harvestLevel: Int = 1

    ) : super(material) {
        this.unlocalizedName = name
        if (creativeTab != null) {
            this.setCreativeTab(creativeTab)
        }
        this.blockHardness = hardness
        this.blockResistance = resistance
        this.setHarvestLevel(harvestTool, harvestLevel)
    }
}