package org.ancienttimes.block

import net.minecraft.block.Block
import net.minecraft.block.SoundType
import net.minecraft.block.material.MapColor
import net.minecraft.block.material.Material
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.ItemBlock
import kotlin.reflect.KClass

open class SimpleBlock : Block {

    val itemBlockClass: KClass<out ItemBlock>
    val oreDictionaryName: String?

    constructor(name: String,
                creativeTab: CreativeTabs? = null,
                material: Material = Material.ROCK,
                mapColor: MapColor = material.materialMapColor,
                hardness: Float = 0.5f,
                resistance: Float = hardness * 5f,
                harvestTool: String = "",
                harvestLevel: Int = 0,
                soundType: SoundType = SoundType.STONE,
                oreDictionaryName: String? = null,
                itemBlockClass: KClass<out ItemBlock> = ItemBlock::class

    ) : super(material, mapColor) {
        this.unlocalizedName = name
        if (creativeTab != null) {
            this.setCreativeTab(creativeTab)
        }
        this.blockResistance = resistance
        this.blockHardness = hardness
        this.setHarvestLevel(harvestTool, harvestLevel)
        this.soundType = soundType
        this.itemBlockClass = itemBlockClass
        this.oreDictionaryName = oreDictionaryName
    }
}