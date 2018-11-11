package org.ancienttimes.item

import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraftforge.oredict.OreDictionary

class SimpleItem: Item, OreDictSubject {
    override val oreDictName: String?

    constructor(name: String,
                creativeTab: CreativeTabs? = null,
                maxStackSize: Int = 64,
                oreDictionaryName: String? = null
    ) {
        this.unlocalizedName = name
        this.creativeTab = creativeTab
        this.maxStackSize = maxStackSize
        this.oreDictName = oreDictionaryName
    }
}