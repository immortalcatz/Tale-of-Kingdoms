package kingdoms.server

import cpw.mods.fml.common.event.FMLInitializationEvent
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import cpw.mods.fml.common.event.FMLServerStartingEvent
import cpw.mods.fml.common.registry.GameRegistry
import kingdoms.server.commands.CheatCodes
import kingdoms.server.handlers.*
import kingdoms.server.items.ItemCoin
import kingdoms.server.items.ItemSpawnEgg
import kingdoms.server.world.WorldGen
import net.minecraft.command.CommandHandler
import net.minecraft.item.Item

open class ServerProxy
{
    lateinit var config: Config

    companion object
    {
        val coins: Item = ItemCoin().setUnlocalizedName("Coins")
    }

    open fun pre(e: FMLPreInitializationEvent)
    {
        config = Config(e)
        EntitiesRegister.register()

        GameRegistry.registerItem(coins, "Coins")
        GameRegistry.registerItem(ItemSpawnEgg(), "MonsterPlacer")

        ServerEvents()
        NetworkHandler

        GameRegistry.registerWorldGenerator(WorldGen(), 1000)
    }

    open fun init(e: FMLInitializationEvent)
    {
        GuiHandler()
    }

    open fun starting(e: FMLServerStartingEvent)
    {
        val command = e.server.commandManager as CommandHandler

        command.registerCommand(CheatCodes())
    }
}