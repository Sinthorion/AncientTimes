package org.ancienttimes

import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.registry.IForgeRegistry
import net.minecraftforge.client.model.ModelLoader


@Mod.EventBusSubscriber
object RegistryHandler {

    fun registerSimpleItem(registry: IForgeRegistry<Item>, name: String, creativeTab: CreativeTabs? = null) {
        val item: Item = object : Item() {
            init {
                this.unlocalizedName = name
                this.creativeTab = creativeTab
            }
        }
        registerItem(registry, name, item)
    }

    fun registerItem(registry: IForgeRegistry<Item>, name: String, item: Item) {
        val resourceLocation = ModelResourceLocation("$MODID:$name", "inventory")
        item.registryName = resourceLocation
        registry.register(item)
        ModelLoader.setCustomModelResourceLocation(item, 0, resourceLocation)
    }

    @JvmStatic
    @SubscribeEvent
    fun onItemRegister(event: RegistryEvent.Register<Item>) {

        registerSimpleItem(event.registry, "cloth", CreativeTabs.MATERIALS)
    }
}