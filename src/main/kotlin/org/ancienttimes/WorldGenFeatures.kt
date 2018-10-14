package org.ancienttimes

import net.minecraftforge.fml.common.registry.GameRegistry
import org.ancienttimes.worldgen.generator.Ore

object WorldGenFeatures {

    fun register() {
        GameRegistry.registerWorldGenerator(Ore, 0)
    }
}