package org.ancienttimes

import net.minecraft.block.Block
import net.minecraft.block.SoundType
import net.minecraft.block.material.MapColor
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.registries.IForgeRegistry
import org.ancienttimes.block.*
import org.ancienttimes.worldgen.feature.FigTree

enum class AncientBlocks(val block: SimpleBlock) {
    BRONZE_BLOCK(MetalStorage("bronze_block", hardness = 5f, resistance = 10f)),
    COPPER_ORE(Ore("copper_ore", harvestLevel = 1)),
    COPPER_BLOCK(MetalStorage("copper_block", hardness = 4f, resistance = 9f)),
    TIN_ORE(Ore("tin_ore", harvestLevel = 1)),
    TIN_BLOCK(MetalStorage("tin_block", hardness = 3f, resistance = 10f)),
    FIG_SAPLING(Sapling("fig_sapling", generator = FigTree(true))),
    FIG_LOG(Log("fig_log")),
    FIG_LEAVES(Leaves("fig_leaves", sapling = FIG_SAPLING.block)),
    FIG_PLANKS(Planks("fig_planks")),
    FIG_WOOD_SLAB(WoodSlab("fig_slab")),
    ;

    fun register(registry: IForgeRegistry<Block>) {
        val name = block.unlocalizedName.split('.', limit = 2).last()
        block.registryName = ResourceLocation("$MODID:$name")
        registry.register(block)
    }

    fun registerItem(registry: IForgeRegistry<Item>) {
        val itemBlockClass = this.block.itemBlockClass
        val item = itemBlockClass.constructors.first().call(block) as Item
        // getUnlocalizedName actually returns "item."+unlocalizedName
        val name = item.unlocalizedName.split('.', limit = 2).last()
        item.registryName = ResourceLocation("$MODID:$name")
        registry.register(item)
        ModelLoader.setCustomModelResourceLocation(item, 0, ModelResourceLocation("$MODID:$name"))
    }
}