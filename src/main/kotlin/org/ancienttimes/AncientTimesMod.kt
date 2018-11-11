package org.ancienttimes

import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraftforge.common.crafting.CraftingHelper
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.oredict.OreDictionary
import org.apache.logging.log4j.LogManager
import java.lang.reflect.Field
import java.lang.reflect.Modifier

const val MODID = "ancienttimes"

@Mod(modid=MODID, name="AncientItems Times", version="0.1.0", acceptedMinecraftVersions = "[1.12]",
        modLanguageAdapter="net.shadowfacts.forgelin.KotlinAdapter")
object AncientTimesMod {

    public val logger = LogManager.getLogger(MODID)

    @Mod.EventHandler
    fun onPostInit(event: FMLPostInitializationEvent) {
        GameRegistry.addSmelting(AncientBlocks.COPPER_ORE.block, ItemStack(AncientItems.COPPER.item), 0.6f)
        GameRegistry.addSmelting(AncientBlocks.TIN_ORE.block, ItemStack(AncientItems.TIN.item), 0.8f)

        ToolMaterials.Init.registerRepairItems()

        // TODO: register missing vanilla ores

        WorldGenFeatures.register()

        loadVanillaRecipes()
    }
}

@Mod.EventBusSubscriber
object AncientTimesModEventHandler {

    @JvmStatic
    @SubscribeEvent
    fun onBlockRegister(event: RegistryEvent.Register<Block>) {
        AncientBlocks.values().forEach {it.register(event.registry) }
    }

    @JvmStatic
    @SubscribeEvent
    fun onItemRegister(event: RegistryEvent.Register<Item>) {
        AncientItems.values().forEach { it.register(event.registry) }
        AncientBlocks.values().forEach { it.registerItem(event.registry)}
    }
}
