package org.ancienttimes

import net.minecraft.init.SoundEvents
import net.minecraft.item.ItemArmor
import net.minecraft.item.ItemStack
import net.minecraft.util.SoundEvent
import net.minecraftforge.common.util.EnumHelper

enum class ArmorMaterials {

//    LEATHER("leather", 5, intArrayOf(1, 2, 3, 1), 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f),
//    CHAIN("chainmail", 15, intArrayOf(1, 4, 5, 2), 12, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 0.0f),
//    IRON("iron", 15, intArrayOf(2, 5, 6, 2), 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0f),
//    GOLD("gold", 7, intArrayOf(1, 3, 5, 2), 25, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0.0f),
//    DIAMOND("diamond", 33, intArrayOf(3, 6, 8, 3), 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0f);

    BRONZE("BRONZE", "bronze", 15, intArrayOf(2, 4, 5, 2), 10, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0f),
    ;

    var material: ItemArmor.ArmorMaterial

    constructor(name: String, textureName: String, durability: Int, reductionAmounts: IntArray, enchantability: Int, soundOnEquip: SoundEvent, toughness: Float) {
        this.material = EnumHelper.addArmorMaterial(
                name, textureName, durability, reductionAmounts, enchantability, soundOnEquip, toughness
        )!!
    }

    companion object Init {
        public fun registerRepairItems() {
            BRONZE.material.setRepairItem(ItemStack(AncientItems.BRONZE.item))
        }
    }

}
//    WOOD(0, 59, 2.0f, 0.0f, 15),
//    STONE(1, 131, 4.0f, 1.0f, 5),
//    IRON(2, 250, 6.0f, 2.0f, 14),
//    DIAMOND(3, 1561, 8.0f, 3.0f, 10),
//    GOLD(0, 32, 12.0f, 0.0f, 22);
