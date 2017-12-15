package aginsun.kingdoms.server.commands;

import aginsun.kingdoms.server.PlayerProvider;
import aginsun.kingdoms.server.handlers.resources.EconomyHandler;
import aginsun.kingdoms.server.handlers.resources.ResourcesHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
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
            if(args[0].equals("glory"))
            {
                PlayerProvider.get((EntityPlayer) sender).addGlory(10000, (EntityPlayer) sender);
                sender.addChatMessage(new ChatComponentTranslation("command.tok.worthy"));
            }
            else if (args[0].equals("gold"))
            {
                EconomyHandler.INSTANCE.addGold(10000);
                sender.addChatMessage(new ChatComponentText("Gold added!"));
            }
            else if (args[0].equals("res"))
            {
                ResourcesHandler.INSTANCE.addcobbleResource(10000);
                ResourcesHandler.INSTANCE.addwoodResource(10000);
            }
            else
            {
                sender.addChatMessage(new ChatComponentText("===================================="));
                sender.addChatMessage(new ChatComponentText("/tok worthy - 10.000 Worthy"));
                sender.addChatMessage(new ChatComponentText("/tok gold - 10.000 Gold coins"));
                sender.addChatMessage(new ChatComponentText("===================================="));
            }
        }
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return null;
    }
}