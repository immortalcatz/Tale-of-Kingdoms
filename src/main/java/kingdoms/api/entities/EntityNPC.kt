package kingdoms.api.entities

import kingdoms.server.WorldProvider
import net.minecraft.entity.EntityCreature
import net.minecraft.entity.SharedMonsterAttributes
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.world.World

abstract class EntityNPC(world: World, private val defaultHeldItem: ItemStack?, health: Float): EntityCreature(world)
{
    protected val worldProvider: WorldProvider

    init
    {
        this.health = health
        this.worldProvider = WorldProvider.get(world)
    }

    override fun applyEntityAttributes()
    {
        super.applyEntityAttributes()
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).baseValue = 0.5
    }

    override fun getHeldItem(): ItemStack?
    {
        return this.defaultHeldItem
    }

    override fun canDespawn(): Boolean
    {
        return false
    }

    override fun canBePushed(): Boolean
    {
        return true
    }

    override fun isMovementCeased(): Boolean
    {
        return false
    }

    open fun canInteractWith(player: EntityPlayer): Boolean
    {
        return !this.isDead || player.getDistanceSqToEntity(this) <= 64.0
    }
}