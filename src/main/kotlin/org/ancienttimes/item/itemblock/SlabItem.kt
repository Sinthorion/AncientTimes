package org.ancienttimes.item.itemblock

import net.minecraft.advancements.CriteriaTriggers
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.ItemBlock
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.SoundCategory
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import org.ancienttimes.block.SimpleBlock
import org.ancienttimes.block.Slab

class SlabItem(block: SimpleBlock) : ItemBlock(block) {

    override fun onItemUse(player: EntityPlayer, world: World, pos: BlockPos, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult {
        val itemstack = player.getHeldItem(hand)

        if (itemstack.isEmpty || !player.canPlayerEdit(pos.offset(facing), facing, itemstack)) {
            return EnumActionResult.FAIL
        }

        val oldstate = world.getBlockState(pos)

        if (oldstate.block === this.block && facing.opposite == oldstate.getValue(Slab.HALF).side) {
            val newstate = this.block.defaultState.withProperty(Slab.HALF, Slab.Half.FULL)

            val collisionBox = newstate.getCollisionBoundingBox(world, pos)!!.offset(pos)


            if (!world.checkNoEntityCollision(collisionBox) || !world.setBlockState(pos, newstate, 1 or 2 or 8)) {
                return EnumActionResult.FAIL
            }
            val soundtype = this.block.getSoundType(newstate, world, pos, player)
            world.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0f) / 2.0f, soundtype.getPitch() * 0.8f)
            itemstack.shrink(1)
            if (player is EntityPlayerMP) {
                CriteriaTriggers.PLACED_BLOCK.trigger(player, pos, itemstack)
            }
            return EnumActionResult.SUCCESS
        }

        return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ)
    }

    @SideOnly(Side.CLIENT)
    override fun canPlaceBlockOnSide(world: World, pos: BlockPos, side: EnumFacing, player: EntityPlayer, stack: ItemStack): Boolean {
        val state = world.getBlockState(pos)

        if (state.block === this.block && (side.opposite == state.getValue(Slab.HALF).side)) {
            return true
        }

        return super.canPlaceBlockOnSide(world, pos, side, player, stack)
    }
}