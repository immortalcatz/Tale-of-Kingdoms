package aginsun.kingdoms.server.handlers;

import com.mojang.realmsclient.gui.ChatFormatting;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public final class UltimateHelper
{
    public static final UltimateHelper INSTANCE = new UltimateHelper();
    @SideOnly(Side.CLIENT)
    public static final ResourceLocation BACKGROUND = new ResourceLocation("taleofkingdoms", "textures/gui/crafting.png");

    public Entity getEntity(String name, World world)
    {
        return EntityList.createEntityByName("taleofkingdoms." + name, world);
    }

    public String createText(String text)
    {
        return createText(text, ChatFormatting.WHITE);
    }

    public String createText(String text, ChatFormatting formatting)
    {
        return formatting + I18n.format(text);
    }
}