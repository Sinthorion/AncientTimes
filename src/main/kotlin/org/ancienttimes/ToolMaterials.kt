package org.ancienttimes

import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraftforge.common.util.EnumHelper

enum class ToolMaterials {

    COPPER("COPPER", 1, 160, 4.5f, 1.2f, 10),
    BRONZE("BRONZE", 2, 230, 5.0f, 1.5f, 16),
    ;

    var material: Item.ToolMaterial

    constructor(name: String, harvestLevel: Int, maxUses: Int, efficiency: Float, damage: Float, enchantability: Int) {
        this.material = EnumHelper.addToolMaterial(
                name, harvestLevel, maxUses, efficiency, damage, enchantability
        )!!
    }

    companion object Init {
        public fun registerRepairItems() {
            COPPER.material.setRepairItem(ItemStack(AncientItems.COPPER.item))
            BRONZE.material.setRepairItem(ItemStack(AncientItems.BRONZE.item))
        }
    }

}
//    WOOD(0, 59, 2.0f, 0.0f, 15),
//    STONE(1, 131, 4.0f, 1.0f, 5),
//    IRON(2, 250, 6.0f, 2.0f, 14),
//    DIAMOND(3, 1561, 8.0f, 3.0f, 10),
//    GOLD(0, 32, 12.0f, 0.0f, 22);
