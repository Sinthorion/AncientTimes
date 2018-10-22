package org.ancienttimes

import net.minecraftforge.fml.common.registry.GameRegistry
import org.ancienttimes.worldgen.generator.*

object WorldGenFeatures {

    fun register() {
        GameRegistry.registerWorldGenerator(Ores, 0)
        GameRegistry.registerWorldGenerator(Trees, 0)
    }
}