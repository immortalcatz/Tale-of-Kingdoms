package aginsun.kingdoms.server.handlers;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public final class UltimateHelper
{
    public static final UltimateHelper INSTANCE = new UltimateHelper();
    public static final ResourceLocation BACKGROUND = new ResourceLocation("taleofkingdoms", "textures/gui/crafting.png");

    public Entity getEntity(String name, World world)
    {
        return EntityList.createEntityByName("taleofkingdoms." + name, world);
    }

    public void spawnEntity(World world, String name, ChunkCoordinates position)
    {
        if (!world.isRemote)
        {
            EntityLivingBase entity = (EntityLivingBase) getEntity("taleofkingdoms." + name, world);
            entity.setLocationAndAngles((double) position.posX, (double) position.posY, (double) position.posZ, 0.0F, 0.0F);
            world.spawnEntityInWorld(entity);
        }
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