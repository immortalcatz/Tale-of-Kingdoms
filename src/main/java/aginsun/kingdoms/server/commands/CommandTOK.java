package aginsun.kingdoms.server.commands;

import aginsun.kingdoms.server.handlers.resources.EconomyHandler;
import aginsun.kingdoms.server.handlers.resources.GloryHandler;
import aginsun.kingdoms.server.handlers.resources.ResourcesHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

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
        if(args[0].equals("glory"))
        {
            GloryHandler.INSTANCE.addGlory(10000);
            sender.addChatMessage(new ChatComponentText(I18n.format("command.tok.worthy")));
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

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return null;
    }
}