package kingdoms.server.commands

import kingdoms.server.PlayerProvider
import kingdoms.server.handlers.resources.ResourcesHandler
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.server.MinecraftServer
import net.minecraft.util.ChatComponentText
import net.minecraft.util.ChatComponentTranslation

class CheatCodes: CommandBase()
{
    override fun getCommandName(): String
    {
        return "tok"
    }

    override fun processCommand(sender: ICommandSender, args: Array<String>)
    {
        if (sender is EntityPlayer)
        {
            val player: EntityPlayer = sender
            val provider = PlayerProvider.get(player)

            if (MinecraftServer.getServer().configurationManager.func_152596_g(player.gameProfile))
            {
                when (args[0])
                {
                    "glory" -> {
                        provider.addGlory(10000)
                        player.addChatMessage(ChatComponentTranslation("command.tok.worthy"))
                    }
                    "gold" -> {
                        provider.addGold(10000)
                        player.addChatMessage(ChatComponentText("Gold added!"))
                    }
                    "res" -> {
                        ResourcesHandler.INSTANCE.addcobbleResource(10000)
                        ResourcesHandler.INSTANCE.addwoodResource(10000)
                        sender.addChatMessage(ChatComponentText("Resources added!"))
                    }
                    "help" -> {
                        player.addChatMessage(ChatComponentText("===================================="))
                        player.addChatMessage(ChatComponentText("/tok worthy - 10.000 Worthy"))
                        player.addChatMessage(ChatComponentText("/tok gold - 10.000 Gold coins"))
                        player.addChatMessage(ChatComponentText("/tok res - 10.000 cobble and wood res"))
                        player.addChatMessage(ChatComponentText("===================================="))
                    }
                }
            }
        }
    }

    override fun getCommandUsage(sender: ICommandSender): String
    {
        return "Use \'/tok help\' for help"
    }
}