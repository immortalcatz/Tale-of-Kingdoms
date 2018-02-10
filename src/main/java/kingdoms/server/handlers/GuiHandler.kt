package kingdoms.server.handlers

import cpw.mods.fml.common.network.IGuiHandler
import cpw.mods.fml.common.network.NetworkRegistry
import kingdoms.client.gui.*
import kingdoms.server.PlayerProvider
import kingdoms.server.TaleOfKingdoms
import kingdoms.server.entities.*
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World

class GuiHandler: IGuiHandler
{
    init
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(TaleOfKingdoms.instance, this)
    }

    override fun getServerGuiElement(id: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Any?
    {
        val entity = world.getEntityByID(id)

        return when (entity)
        {
            is EntitySantaClaus -> null
            else -> null
        }
    }

    override fun getClientGuiElement(id: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Any?
    {
        val entity = world.getEntityByID(id)

        return when (entity)
        {
            is EntityBankerKeeper -> GuiBank(player, world)
            is EntityBuilderKeeper -> GuiBuildScreen(player, world, entity)
            is EntityFisher -> GuiFisher(player, world)
            is EntityFarmerKeeper -> GuiFoodKeeper(player, world)
            is EntityFoodKeeper, is EntityForgeKeeper, is EntityShopKeeper, is EntityWeaponKeeper -> GuiShopList(player, world, PlayerProvider.get(player).stacks)
            is EntityHunterKeeper -> GuiHunter(player, world)
            is EntityInnKeeper -> GuiInnMenu(player, world)
            is EntityLibraryKeeper -> GuiLibrary(player, world, entity)
            is EntityLumber -> GuiLumber(player, world)
            is EntityMageKeeper -> GuiMageHall(player, world)
            is EntityPriestKeeper -> GuiPriest(player, world)
            is EntityQuarry -> GuiQuarry(player, world)
            is EntityHeadCommander -> GuiReinforcementPool(player, world)
            is EntityStockKeeper -> GuiStockList(player, world)
            is EntityTavernKeeper -> GuiTavernGame(player, world)
            is EntityBarracksKeeper -> GuiWardenMenu(player, world)
            is EntityWorkerMember -> GuiWorker(player, world, entity)
            else -> null
        }
    }
}