package org.ancienttimes.item

import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.Entity
import net.minecraft.inventory.EntityEquipmentSlot
import net.minecraft.item.ItemArmor
import net.minecraft.item.ItemStack
import net.minecraftforge.oredict.OreDictionary

class ArmorItem : ItemArmor {
    constructor(
            name: String,
            material: ArmorMaterial,
            renderIndex: Int,
            slot: EntityEquipmentSlot,
            creativeTab: CreativeTabs? = null,
            maxStackSize: Int = 64,
            oreDictionaryName: String? = null
    ) : super(material, renderIndex, slot) {
        this.unlocalizedName = name
        this.creativeTab = creativeTab
        this.maxStackSize = maxStackSize
        if (oreDictionaryName != null) {
            OreDictionary.registerOre(oreDictionaryName, this)
        }
    }

    override fun getArmorTexture(itemStack: ItemStack, entity: Entity, slot: EntityEquipmentSlot, texture: String?): String? {
        val layer = if (slot == EntityEquipmentSlot.HEAD || slot == EntityEquipmentSlot.CHEST) {
            1
        } else {
            2
        }
        val name = this.armorMaterial.getName().toLowerCase()
        return String.format("ancienttimes:textures/models/armor/%s_layer_%d.png", name, layer)
    }
}