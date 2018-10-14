package org.ancienttimes

import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.inventory.EntityEquipmentSlot
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.registries.IForgeRegistry
import org.ancienttimes.item.*

enum class Items(val item: Item) {
    BRONZE(SimpleItem("bronze", creativeTab = CreativeTabs.MATERIALS)),
    BRONZE_NUGGET(SimpleItem("bronze_nugget", creativeTab = CreativeTabs.MATERIALS)),
    BRONZE_AXE(AxeItem("bronze_axe", material = ToolMaterials.BRONZE.material, creativeTab = CreativeTabs.TOOLS)),
    BRONZE_HOE(HoeItem("bronze_hoe", material = ToolMaterials.BRONZE.material, creativeTab = CreativeTabs.TOOLS)),
    BRONZE_PICKAXE(PickaxeItem("bronze_pickaxe", creativeTab = CreativeTabs.TOOLS,
            material = ToolMaterials.BRONZE.material)),
    BRONZE_SHOVEL(ShovelItem("bronze_shovel", creativeTab = CreativeTabs.TOOLS,
            material = ToolMaterials.BRONZE.material)),
    BRONZE_SWORD(SwordItem("bronze_sword",
            material = ToolMaterials.BRONZE.material, creativeTab = CreativeTabs.COMBAT)),
    BRONZE_HEAD(ArmorItem("bronze_head", creativeTab = CreativeTabs.COMBAT,
            material = ArmorMaterials.BRONZE.material, renderIndex = 0, slot = EntityEquipmentSlot.HEAD)),
    BRONZE_CHEST(ArmorItem("bronze_chest", creativeTab = CreativeTabs.COMBAT,
            material = ArmorMaterials.BRONZE.material, renderIndex = 0, slot = EntityEquipmentSlot.CHEST)),
    BRONZE_LEGS(ArmorItem("bronze_legs", creativeTab = CreativeTabs.COMBAT,
            material = ArmorMaterials.BRONZE.material, renderIndex = 0, slot = EntityEquipmentSlot.LEGS)),
    BRONZE_FEET(ArmorItem("bronze_feet", creativeTab = CreativeTabs.COMBAT,
            material = ArmorMaterials.BRONZE.material, renderIndex = 0, slot = EntityEquipmentSlot.FEET)),
    CLOTH(SimpleItem("cloth", creativeTab = CreativeTabs.MATERIALS)),
    COPPER(SimpleItem("copper", creativeTab = CreativeTabs.MATERIALS)),
    COPPER_NUGGET(SimpleItem("copper_nugget", creativeTab = CreativeTabs.MATERIALS)),
    COPPER_AXE(AxeItem("copper_axe", creativeTab = CreativeTabs.TOOLS,
            material = ToolMaterials.COPPER.material)),
    COPPER_HOE(HoeItem("copper_hoe", creativeTab = CreativeTabs.TOOLS,
            material = ToolMaterials.COPPER.material)),
    COPPER_PICKAXE(PickaxeItem("copper_pickaxe", creativeTab = CreativeTabs.TOOLS,
            material = ToolMaterials.COPPER.material)),
    COPPER_SHOVEL(ShovelItem("copper_shovel", creativeTab = CreativeTabs.TOOLS,
            material = ToolMaterials.COPPER.material)),
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
