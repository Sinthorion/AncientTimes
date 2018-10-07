package org.ancienttimes

import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.oredict.OreDictionary

const val MODID = "ancienttimes"

@Mod(modid=MODID, name="Ancient Times", version="0.1.0", acceptedMinecraftVersions = "[1.12]",
        modLanguageAdapter="net.shadowfacts.forgelin.KotlinAdapter")
object AncientTimesMod {

    @Mod.EventHandler
    fun onPostInit(event: FMLInitializationEvent) {
        GameRegistry.addSmelting(Blocks.COPPER_ORE.block, ItemStack(Items.COPPER.item), 0.6f)
        GameRegistry.addSmelting(Blocks.TIN_ORE.block, ItemStack(Items.TIN.item), 0.8f)

        OreDictionary.registerOre("copper", Items.COPPER.item)

        ToolMaterials.Init.registerRepairItems()
    }
}

@Mod.EventBusSubscriber
object AncientTimesModEventHandler {

    @JvmStatic
    @SubscribeEvent
    fun onBlockRegister(event: RegistryEvent.Register<Block>) {

        Blocks.values().forEach {it.register(event.registry) }
    }

    @JvmStatic
    @SubscribeEvent
    fun onItemRegister(event: RegistryEvent.Register<Item>) {

        println("init items")
        Items.values().forEach { it.register(event.registry) }

        Blocks.values().forEach { it.registerItem(event.registry)}
    }
}
