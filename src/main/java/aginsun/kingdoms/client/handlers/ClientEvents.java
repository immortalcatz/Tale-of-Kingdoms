package aginsun.kingdoms.client.handlers;

import aginsun.kingdoms.server.TaleOfKingdoms;
import aginsun.kingdoms.server.handlers.resources.EconomyHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;

import static aginsun.kingdoms.server.handlers.GuiHandler.GUI_CONQUEST;

public final class ClientEvents
{
    private final KeyBinding key = new KeyBinding(I18n.format("keyBinding.conquest"), 21, "Tale Of Kingdoms");

    public ClientEvents()
    {
        ClientRegistry.registerKeyBinding(key);
        MinecraftForge.EVENT_BUS.register(new MF());
        FMLCommonHandler.instance().bus().register(new FML());
    }

    public class MF
    {
        @SubscribeEvent
        public void onRender(GuiScreenEvent.DrawScreenEvent.Post e)
        {
            final Minecraft mc = Minecraft.getMinecraft();
            final ScaledResolution scaled = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);

            if (e.gui instanceof GuiInventory)
            {
                e.gui.drawString(mc.fontRenderer, I18n.format("gui.goldTotal") + " " + EconomyHandler.INSTANCE.getGoldTotal(), scaled.getScaledWidth() / 2 - 2, scaled.getScaledHeight() / 2 - 17, 16763904);
            }
        }
    }

    public class FML
    {
        @SubscribeEvent
        public void onInput(InputEvent.KeyInputEvent e)
        {
            if (Keyboard.isKeyDown(key.getKeyCode()))
            {
                EntityPlayer player = Minecraft.getMinecraft().thePlayer;
                if (Minecraft.getMinecraft().currentScreen == null)
                {
                    player.openGui(TaleOfKingdoms.instance, GUI_CONQUEST, player.worldObj, 0, 0, 0);
                }
            }
        }
    }
}