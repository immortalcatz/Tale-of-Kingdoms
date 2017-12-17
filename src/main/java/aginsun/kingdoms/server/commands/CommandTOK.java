package aginsun.kingdoms.server.commands;

import aginsun.kingdoms.server.PlayerProvider;
import aginsun.kingdoms.server.handlers.resources.EconomyHandler;
import aginsun.kingdoms.server.handlers.resources.ResourcesHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public final class CommandTOK extends CommandBase
{
    @Override
    public String getName()
    {
        return "tok";
    }

    @Override
    public String getUsage(ICommandSender sender)
    {
        return null;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args)
    {
        if (sender instanceof EntityPlayer)
        {
            switch (args[0])
            {
                case "glory":
                    PlayerProvider.get((EntityPlayer) sender).addGlory(10000, (EntityPlayer) sender);
                    sender.sendMessage(new TextComponentString("command.tok.worthy"));
                    break;
                case "gold":
                    EconomyHandler.INSTANCE.addGold(10000);
                    sender.sendMessage(new TextComponentString("Gold added!"));
                    break;
                case "res":
                    ResourcesHandler.INSTANCE.addcobbleResource(10000);
                    ResourcesHandler.INSTANCE.addwoodResource(10000);
                    break;
                default:
                    sender.sendMessage(new TextComponentString("===================================="));
                    sender.sendMessage(new TextComponentString("/tok worthy - 10.000 Worthy"));
                    sender.sendMessage(new TextComponentString("/tok gold - 10.000 Gold coins"));
                    sender.sendMessage(new TextComponentString("===================================="));
                    break;
            }
        }
    }
}