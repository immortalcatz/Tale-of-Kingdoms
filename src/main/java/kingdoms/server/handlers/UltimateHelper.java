package kingdoms.server.handlers;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
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

    public void spawnEntity(World world, String name, double x, double y, double z, float yaw)
    {
        spawnEntity(world, name, x, y, z, yaw, 0);
    }

    public void spawnEntity(World world, String name, double x, double y, double z, float yaw, float pitch)
    {
        if (!world.isRemote)
        {
            EntityLivingBase entity = (EntityLivingBase) getEntity("taleofkingdoms." + name, world);
            entity.setLocationAndAngles(x, y, z, yaw, pitch);
            world.spawnEntityInWorld(entity);
        }
    }

    public String gold(String text)
    {
        StringBuilder output = new StringBuilder();

        int bound = text.length();

        for (int i = 0; i < bound; i++)
        {
            if ((i + Minecraft.getSystemTime() / 40) % 88 == 0)
            {
                output.append(ChatFormatting.WHITE).append(ChatFormatting.BOLD).append(text.substring(i, i + 1));
            }
            else if ((i + Minecraft.getSystemTime() / 40) % 88 == 1)
            {
                output.append(ChatFormatting.YELLOW).append(ChatFormatting.BOLD).append(text.substring(i, i + 1));
            }
            else if ((i + Minecraft.getSystemTime() / 40) % 88 == 87)
            {
                output.append(ChatFormatting.YELLOW).append(ChatFormatting.BOLD).append(text.substring(i, i + 1));
            }
            else {
                output.append(ChatFormatting.GOLD).append(ChatFormatting.BOLD).append(text.substring(i, i + 1));
            }
        }

        return output.toString();
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