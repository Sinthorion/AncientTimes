package org.ancienttimes

import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.item.Item
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

const val MODID = "ancienttimes"

@Mod(modid=MODID, name="Ancient Times", version="0.1.0", modLanguageAdapter="net.shadowfacts.forgelin.KotlinAdapter")
object AncientTimesMod
@Mod.EventBusSubscriber
object AncientTimesModEventHandler {

    @JvmStatic
    @SubscribeEvent
    fun onItemRegister(event: RegistryEvent.Register<Item>) {

        Items.values().forEach {
            val item = it.item
            // getUnlocalizedName actually returns "item."+unlocalizedName
            val name = item.unlocalizedName.split('.', limit = 2).last()
            val resourceLocation = ModelResourceLocation("$MODID:$name", "inventory")
            item.registryName = resourceLocation
            event.registry.register(item)
            ModelLoader.setCustomModelResourceLocation(item, 0, resourceLocation)
        }
    }
}
