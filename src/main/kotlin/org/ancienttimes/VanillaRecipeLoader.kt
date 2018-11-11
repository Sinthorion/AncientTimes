package org.ancienttimes

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import net.minecraft.item.crafting.IRecipe
import net.minecraft.util.JsonUtils
import net.minecraft.util.ResourceLocation
import net.minecraftforge.common.crafting.CraftingHelper
import net.minecraftforge.common.crafting.JsonContext
import net.minecraftforge.fml.common.Loader
import net.minecraftforge.fml.common.registry.ForgeRegistries
import net.minecraftforge.registries.ForgeRegistry
import org.apache.commons.io.FilenameUtils
import java.nio.file.FileSystems
import java.nio.file.Files

fun loadVanillaRecipes() {
    val path = "assets/minecraft/recipes"
    val ancientMod = Loader.instance().indexedModList[MODID]!!
    val vanillaMod = Loader.instance().activeModList.first()
    val source = ancientMod.source
    val root = if (source.isFile) {
        FileSystems.newFileSystem(source.toPath(), null).getPath("/$path")
    } else if (source.isDirectory) {
        source.toPath().resolve(path)
    } else {
        return
    }
    if (!Files.exists(root)) {
        return
    }


    val context = JsonContext(vanillaMod.modId)

    // Make Forge believe this code runs from vanilla
    Loader.instance().setActiveModContainer(vanillaMod)

    val gson = GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()

    for (file in Files.walk(root)) {

        val relative = root.relativize(file).toString()
        if ("json" != FilenameUtils.getExtension(file.toString()) || relative.startsWith("_")) {
            continue
        }

        val name = FilenameUtils.removeExtension(relative).replace("\\\\".toRegex(), "/")

        val json = JsonUtils.fromJson(gson, Files.newBufferedReader(file), JsonObject::class.java)
        if (json == null) {
            AncientTimesMod.logger.error("Invalid JSON for recipe {}", name)
            continue
        }
        val recipe: IRecipe
        try {
            recipe = CraftingHelper.getRecipe(json, context)
        } catch (e: JsonSyntaxException) {
            AncientTimesMod.logger.error("Invalid recipe format for recipe {}", name, e)
            continue
        }
        val resourceLocation = ResourceLocation(vanillaMod.modId, name)
        recipe.setRegistryName(resourceLocation)

        if (ForgeRegistries.RECIPES.containsKey(resourceLocation)) {
            (ForgeRegistries.RECIPES as ForgeRegistry).remove(resourceLocation)
        }
        ForgeRegistries.RECIPES.register(recipe)
    }
}