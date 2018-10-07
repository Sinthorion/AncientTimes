package org.ancienttimes

import net.minecraft.block.Block
import net.minecraft.block.SoundType
import net.minecraft.block.material.MapColor
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.registries.IForgeRegistry
import org.ancienttimes.block.SimpleBlock

enum class Blocks(val block: Block) {
    BRONZE_BLOCK(SimpleBlock("bronze_block",
            creativeTab = CreativeTabs.BUILDING_BLOCKS,
            hardness = 4f, resistance = 9f,
            harvestTool = "pickaxe", harvestLevel = 1,
            soundType = SoundType.METAL, mapColor = MapColor.IRON
    )),
    COPPER_ORE(SimpleBlock("copper_ore",
            creativeTab = CreativeTabs.BUILDING_BLOCKS,
            hardness = 3f, resistance = 5f,
            harvestTool = "pickaxe", harvestLevel = 1,
            soundType = SoundType.STONE, mapColor = MapColor.STONE
    )),
    COPPER_BLOCK(SimpleBlock("copper_block",
            creativeTab = CreativeTabs.BUILDING_BLOCKS,
            hardness = 4f, resistance = 9f,
            harvestTool = "pickaxe", harvestLevel = 1,
            soundType = SoundType.METAL, mapColor = MapColor.IRON
    )),
    TIN_ORE(SimpleBlock("tin_ore",
            creativeTab = CreativeTabs.BUILDING_BLOCKS,
            hardness = 3f, resistance = 5f,
            harvestTool = "pickaxe", harvestLevel = 1,
            soundType = SoundType.STONE, mapColor = MapColor.STONE
    )),
    TIN_BLOCK(SimpleBlock("tin_block",
            creativeTab = CreativeTabs.BUILDING_BLOCKS,
            hardness = 2f, resistance = 4f,
            harvestTool = "pickaxe", harvestLevel = 1,
            soundType = SoundType.METAL, mapColor = MapColor.IRON
    )),
    ;

    fun register(registry: IForgeRegistry<Block>) {
        val name = block.unlocalizedName.split('.', limit = 2).last()
        block.registryName = ResourceLocation("$MODID:$name")
        registry.register(block)
    }

    fun registerItem(registry: IForgeRegistry<Item>) {

        val item = ItemBlock(this.block)
        // getUnlocalizedName actually returns "item."+unlocalizedName
        val name = item.unlocalizedName.split('.', limit = 2).last()
        item.registryName = ResourceLocation("$MODID:$name")
        registry.register(item)
        ModelLoader.setCustomModelResourceLocation(item, 0, ModelResourceLocation("$MODID:$name"))
    }
}