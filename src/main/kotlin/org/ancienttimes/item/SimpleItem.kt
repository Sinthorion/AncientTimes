package org.ancienttimes.item

import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item

class SimpleItem: Item {
    constructor(name: String, creativeTab: CreativeTabs? = null, maxStackSize: Int = 64) {
        this.unlocalizedName = name
        this.creativeTab = creativeTab
        this.maxStackSize = maxStackSize
    }
}