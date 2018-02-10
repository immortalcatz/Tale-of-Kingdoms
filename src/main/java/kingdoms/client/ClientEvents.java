package kingdoms.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import kingdoms.client.gui.GuiStartConquest;
import kingdoms.server.PlayerProvider;
import kingdoms.server.handlers.UltimateHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;

final class ClientEvents
{
    private final Minecraft mc = Minecraft.getMinecraft();
    private final KeyBinding key = new KeyBinding(I18n.format("keyBinding.conquest"), 21, "Tale Of Kingdoms");

    ClientEvents()
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
            final ScaledResolution scaled = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);

            if (e.gui instanceof GuiInventory)
                e.gui.drawString(mc.fontRenderer, I18n.format("gui.goldTotal") + " " + PlayerProvider.get(mc.thePlayer).getGoldTotal(), scaled.getScaledWidth() / 2 - 2, scaled.getScaledHeight() / 2 - 17, 16763904);
        }

        @SubscribeEvent
        public void onGui(RenderGameOverlayEvent e)
        {
            if (!e.isCancelable() && e.type == RenderGameOverlayEvent.ElementType.TEXT)
                mc.fontRenderer.drawString(UltimateHelper.INSTANCE.gold(I18n.format("gui.goldTotal") + " " + PlayerProvider.get(mc.thePlayer).getGoldTotal()), 0, 0, 0xFFFFFF);
        }
    }

    public class FML
    {
        @SubscribeEvent
        public void onInput(InputEvent.KeyInputEvent e)
        {
            if (Keyboard.isKeyDown(key.getKeyCode()))
            {
                EntityPlayer player = mc.thePlayer;

                if (mc.currentScreen == null)
                    mc.displayGuiScreen(new GuiStartConquest(player, mc.theWorld));
            }
        }
    }
}