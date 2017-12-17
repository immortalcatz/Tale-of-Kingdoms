package aginsun.kingdoms.client;

import aginsun.kingdoms.server.TaleOfKingdoms;
import aginsun.kingdoms.server.handlers.UltimateHelper;
import aginsun.kingdoms.server.handlers.resources.EconomyHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.input.Keyboard;

import static aginsun.kingdoms.server.handlers.GuiHandler.GUI_CONQUEST;

@Mod.EventBusSubscriber(Side.CLIENT)
public final class ClientEvents
{
    private final KeyBinding key = new KeyBinding(I18n.format("keyBinding.conquest"), 21, "Tale Of Kingdoms");

    public ClientEvents()
    {
        ClientRegistry.registerKeyBinding(key);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onRender(GuiScreenEvent.DrawScreenEvent.Post e)
    {
        final Minecraft mc = Minecraft.getMinecraft();
        final ScaledResolution scaled = new ScaledResolution(mc);

        if (e.getGui() instanceof GuiInventory)
        {
            e.getGui().drawString(mc.fontRenderer, I18n.format("gui.goldTotal") + " " + EconomyHandler.INSTANCE.getGoldTotal(), scaled.getScaledWidth() / 2 - 2, scaled.getScaledHeight() / 2 - 17, 16763904);
        }
    }

    @SubscribeEvent
    public void onGui(RenderGameOverlayEvent e)
    {
        if (!e.isCancelable() && e.getType() == RenderGameOverlayEvent.ElementType.TEXT)
        {
            Minecraft.getMinecraft().fontRenderer.drawString(UltimateHelper.INSTANCE.randbow("Hello, World!"), 0, 0, 0xFFFFFF);
        }
    }

    @SubscribeEvent
    public void onInput(InputEvent.KeyInputEvent e)
    {
        if (Keyboard.isKeyDown(key.getKeyCode()))
        {
            EntityPlayer player = Minecraft.getMinecraft().player;

            if (Minecraft.getMinecraft().currentScreen == null)
                player.openGui(TaleOfKingdoms.instance, GUI_CONQUEST, player.world, 0, 0, 0);
        }
    }
}