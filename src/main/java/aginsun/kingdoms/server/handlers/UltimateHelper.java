package aginsun.kingdoms.server.handlers;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.stream.IntStream;

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

    public String randbow(String text)
    {
        StringBuilder output = new StringBuilder();

        IntStream.range(0, text.length()).forEach(i -> {
            if ((i + Minecraft.getSystemTime() / 40) % 88 == 0) {
                output.append(ChatFormatting.WHITE).append(text.substring(i, i + 1));
            } else if ((i + Minecraft.getSystemTime() / 40) % 88 == 1) {
                output.append(ChatFormatting.YELLOW).append(text.substring(i, i + 1));
            } else if ((i + Minecraft.getSystemTime() / 40) % 88 == 87) {
                output.append(ChatFormatting.YELLOW).append(text.substring(i, i + 1));
            } else {
                output.append(ChatFormatting.GOLD).append(text.substring(i, i + 1));
            }
        });

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