package org.ancienttimes

import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import org.ancienttimes.item.SimpleItem

enum class Items(val item: Item) {
    CLOTH(SimpleItem("cloth", creativeTab = CreativeTabs.MATERIALS)),
    ;
}
