package kingdoms.server.commands;

import kingdoms.server.PlayerProvider;
import kingdoms.server.handlers.resources.ResourcesHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;

public final class CommandTOK extends CommandBase
{
    @Override
    public String getCommandName()
    {
        return "tok";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args)
    {
        if (sender instanceof EntityPlayer)
        {
            if (MinecraftServer.getServer().getConfigurationManager().func_152596_g(((EntityPlayer) sender).getGameProfile()))
            {
                switch (args[0])
                {
                    case "glory":
                        PlayerProvider.get((EntityPlayer) sender).addGlory(10000);
                        sender.addChatMessage(new ChatComponentTranslation("command.tok.worthy"));
                        break;
                    case "gold":
                        PlayerProvider.get((EntityPlayer) sender).addGold(10000);
                        sender.addChatMessage(new ChatComponentText("Gold added!"));
                        break;
                    case "res":
                        ResourcesHandler.INSTANCE.addcobbleResource(10000);
                        ResourcesHandler.INSTANCE.addwoodResource(10000);
                        break;
                    case "help":
                        sender.addChatMessage(new ChatComponentText("===================================="));
                        sender.addChatMessage(new ChatComponentText("/tok worthy - 10.000 Worthy"));
                        sender.addChatMessage(new ChatComponentText("/tok gold - 10.000 Gold coins"));
                        sender.addChatMessage(new ChatComponentText("===================================="));
                        break;
                }
            }
        }
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "Use \'/tok help\' for help";
    }
}