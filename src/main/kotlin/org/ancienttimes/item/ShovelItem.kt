package org.ancienttimes.item

import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.ItemSpade
import net.minecraftforge.oredict.OreDictionary

class ShovelItem: ItemSpade {
    constructor(name: String,
                creativeTab: CreativeTabs? = CreativeTabs.TOOLS,
                maxStackSize: Int = 64,
                oreDictionaryName: String? = null,
                material: ToolMaterial
    ) : super(material) {
        this.unlocalizedName = name
        this.creativeTab = creativeTab
        this.maxStackSize = maxStackSize
        if (oreDictionaryName != null) {
            OreDictionary.registerOre(oreDictionaryName, this)
        }
    }
}
