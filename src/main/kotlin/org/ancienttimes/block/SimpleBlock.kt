package org.ancienttimes.block

import net.minecraft.block.Block
import net.minecraft.block.SoundType
import net.minecraft.block.material.MapColor
import net.minecraft.block.material.Material
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.ItemBlock
import net.minecraftforge.oredict.OreDictionary
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

open class SimpleBlock : Block {

    var itemBlockClass: KClass<out ItemBlock>

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
        if (oreDictionaryName != null) {
            OreDictionary.registerOre(oreDictionaryName, this)
        }
    }
}