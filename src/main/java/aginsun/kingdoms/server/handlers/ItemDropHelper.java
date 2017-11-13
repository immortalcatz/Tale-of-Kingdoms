package aginsun.kingdoms.server.handlers;

import aginsun.kingdoms.server.TaleOfKingdoms;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.*;
import net.minecraft.item.Item;

import java.util.Random;

public final class ItemDropHelper
{
    private static Random random = new Random();
    public static boolean isHostileEntity(EntityLivingBase entityLiving)
    {
        return entityLiving instanceof EntityBlaze || entityLiving instanceof EntityCaveSpider || entityLiving instanceof EntityCreeper || entityLiving instanceof EntityDragon || entityLiving instanceof EntityEnderman || entityLiving instanceof EntityGhast || entityLiving instanceof EntityMagmaCube || entityLiving instanceof EntityPigZombie || entityLiving instanceof EntitySilverfish || entityLiving instanceof EntitySkeleton || entityLiving instanceof EntitySpider || entityLiving instanceof EntityWitch || entityLiving instanceof EntityWither || entityLiving instanceof EntityZombie;
    }

    public static void dropCoins(EntityLivingBase entityLiving)
    {
        if(isHostileEntity(entityLiving) && !entityLiving.worldObj.isRemote)
        {
            for (int i = 0; i < random.nextInt(25); i++)
            {
                dropItem(TaleOfKingdoms.proxy.coins, 1, entityLiving);
            }
        }
    }

    private static void dropItem(Item item, int meta, EntityLivingBase livingBase)
    {
        livingBase.dropItem(item, meta);
    }
}