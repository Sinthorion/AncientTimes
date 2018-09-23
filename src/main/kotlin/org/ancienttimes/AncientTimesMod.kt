package org.ancienttimes

import net.minecraft.block.Block
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.registry.GameRegistry

const val MODID = "ancienttimes"

@Mod(modid=MODID, name="Ancient Times", version="0.1.0", acceptedMinecraftVersions = "[1.12]",
        modLanguageAdapter="net.shadowfacts.forgelin.KotlinAdapter")
object AncientTimesMod {
    @Mod.EventHandler
    fun onPostInit(event: FMLInitializationEvent) {
        GameRegistry.addSmelting(Blocks.COPPER_ORE.block, ItemStack(Items.COPPER.item), 0.6f)
    }
}

@Mod.EventBusSubscriber
object AncientTimesModEventHandler {

    @JvmStatic
    @SubscribeEvent
    fun onBlockRegister(event: RegistryEvent.Register<Block>) {

        Blocks.values().forEach {
            val block = it.block
            val name = block.unlocalizedName.split('.', limit = 2).last()
            block.registryName = ResourceLocation("$MODID:$name")
            event.registry.register(block)
        }
    }

    @JvmStatic
    @SubscribeEvent
    fun onItemRegister(event: RegistryEvent.Register<Item>) {

        Items.values().forEach {
            val item = it.item
            // getUnlocalizedName actually returns "item."+unlocalizedName
            val name = item.unlocalizedName.split('.', limit = 2).last()
            item.registryName = ResourceLocation("$MODID:$name")
            event.registry.register(item)
            ModelLoader.setCustomModelResourceLocation(item, 0, ModelResourceLocation("$MODID:$name", "inventory"))
        }

        Blocks.values().forEach {
            val item = ItemBlock(it.block)
            // getUnlocalizedName actually returns "item."+unlocalizedName
            val name = item.unlocalizedName.split('.', limit = 2).last()
            item.registryName = ResourceLocation("$MODID:$name")
            event.registry.register(item)
            ModelLoader.setCustomModelResourceLocation(item, 0, ModelResourceLocation("$MODID:$name"))
        }
    }
}
