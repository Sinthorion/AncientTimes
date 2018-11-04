package org.ancienttimes

import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.inventory.EntityEquipmentSlot
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.registries.IForgeRegistry
import org.ancienttimes.item.*

enum class AncientItems(val item: Item) {
    BRONZE(SimpleItem("bronze", creativeTab = CreativeTabs.MATERIALS)),
    BRONZE_NUGGET(SimpleItem("bronze_nugget", creativeTab = CreativeTabs.MATERIALS)),
    BRONZE_AXE(AxeItem("bronze_axe", material = ToolMaterials.BRONZE.material, creativeTab = CreativeTabs.TOOLS)),
    BRONZE_HOE(HoeItem("bronze_hoe", material = ToolMaterials.BRONZE.material, creativeTab = CreativeTabs.TOOLS)),
    BRONZE_PICKAXE(PickaxeItem("bronze_pickaxe", material = ToolMaterials.BRONZE.material)),
    BRONZE_SHOVEL(ShovelItem("bronze_shovel", material = ToolMaterials.BRONZE.material)),
    BRONZE_SWORD(SwordItem("bronze_sword", material = ToolMaterials.BRONZE.material)),
    BRONZE_HEAD(ArmorItem("bronze_head", material = ArmorMaterials.BRONZE.material, slot = EntityEquipmentSlot.HEAD)),
    BRONZE_CHEST(ArmorItem("bronze_chest", material = ArmorMaterials.BRONZE.material, slot = EntityEquipmentSlot.CHEST)),
    BRONZE_LEGS(ArmorItem("bronze_legs", material = ArmorMaterials.BRONZE.material, slot = EntityEquipmentSlot.LEGS)),
    BRONZE_FEET(ArmorItem("bronze_feet", material = ArmorMaterials.BRONZE.material, slot = EntityEquipmentSlot.FEET)),
    CLOTH(SimpleItem("cloth", creativeTab = CreativeTabs.MATERIALS)),
    COPPER(SimpleItem("copper", creativeTab = CreativeTabs.MATERIALS)),
    COPPER_NUGGET(SimpleItem("copper_nugget", creativeTab = CreativeTabs.MATERIALS)),
    COPPER_AXE(AxeItem("copper_axe", material = ToolMaterials.COPPER.material)),
    COPPER_HOE(HoeItem("copper_hoe", material = ToolMaterials.COPPER.material)),
    COPPER_PICKAXE(PickaxeItem("copper_pickaxe", material = ToolMaterials.COPPER.material)),
    COPPER_SHOVEL(ShovelItem("copper_shovel", material = ToolMaterials.COPPER.material)),
    TIN(SimpleItem("tin", creativeTab = CreativeTabs.MATERIALS)),
    TIN_NUGGET(SimpleItem("tin_nugget", creativeTab = CreativeTabs.MATERIALS)),
    ;

    fun register(registry: IForgeRegistry<Item>) {
        // getUnlocalizedName actually returns "item."+unlocalizedName
        val name = item.unlocalizedName.split('.', limit = 2).last()
        item.registryName = ResourceLocation("$MODID:$name")
        registry.register(item)
        ModelLoader.setCustomModelResourceLocation(item, 0, ModelResourceLocation("$MODID:$name", "inventory"))
    }
}
