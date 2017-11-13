package aginsun.kingdoms.server.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

public final class ItemSpawnEgg extends ItemMonsterPlacer
{
    public ItemSpawnEgg()
    {
        this.setTextureName("spawn_egg");
        this.setUnlocalizedName("monsterPlacer");
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack)
    {
        return ("" + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name")).trim() + " " + StatCollector.translateToLocal("entity." + EntitiesType.getByID(stack.getItemDamage()).getName() + ".name");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tabs, List list)
    {
        for (EntitiesType type : EntitiesType.values())
        {
            list.add(new ItemStack(item, 1, type.ordinal()));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int parColorType)
    {
        return (parColorType == 0) ? EntitiesType.getByID(stack.getItemDamage()).getBase() : EntitiesType.getByID(stack.getItemDamage()).getSpots();
    }

    @Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer,
                             World par3World, int par4, int par5, int par6, int par7, float par8,
                             float par9, float par10)
    {
        if (par3World.isRemote)
        {
            return true;
        }
        else
        {
            Block block = par3World.getBlock(par4, par5, par6);
            par4 += Facing.offsetsXForSide[par7];
            par5 += Facing.offsetsYForSide[par7];
            par6 += Facing.offsetsZForSide[par7];
            double d0 = 0.0D;

            if (par7 == 1 && block.getRenderType() == 11)
            {
                d0 = 0.5D;
            }

            Entity entity = spawnEntity(par1ItemStack, par3World, par4 + 0.5D, par5 + d0, par6 + 0.5D);

            if (entity != null)
            {
                if (entity instanceof EntityLivingBase && par1ItemStack.hasDisplayName())
                {
                    ((EntityLiving)entity).setCustomNameTag(par1ItemStack.getDisplayName());
                }

                if (!par2EntityPlayer.capabilities.isCreativeMode)
                {
                    --par1ItemStack.stackSize;
                }
            }

            return true;
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        if (par2World.isRemote)
        {
            return par1ItemStack;
        }
        else
        {
            MovingObjectPosition movingobjectposition =
                    getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer, true);

            if (movingobjectposition == null)
            {
                return par1ItemStack;
            }
            else
            {
                if (movingobjectposition.typeOfHit == MovingObjectPosition
                        .MovingObjectType.BLOCK)
                {
                    int i = movingobjectposition.blockX;
                    int j = movingobjectposition.blockY;
                    int k = movingobjectposition.blockZ;

                    if (!par2World.canMineBlock(par3EntityPlayer, i, j, k))
                    {
                        return par1ItemStack;
                    }

                    if (!par3EntityPlayer.canPlayerEdit(i, j, k, movingobjectposition
                            .sideHit, par1ItemStack))
                    {
                        return par1ItemStack;
                    }

                    if (par2World.getBlock(i, j, k) instanceof BlockLiquid)
                    {
                        Entity entity = spawnEntity(par1ItemStack, par2World, i, j, k);

                        if (entity != null)
                        {
                            if (entity instanceof EntityLivingBase && par1ItemStack
                                    .hasDisplayName())
                            {
                                ((EntityLiving)entity).setCustomNameTag(par1ItemStack
                                        .getDisplayName());
                            }

                            if (!par3EntityPlayer.capabilities.isCreativeMode)
                            {
                                --par1ItemStack.stackSize;
                            }
                        }
                    }
                }

                return par1ItemStack;
            }
        }
    }

    public Entity spawnEntity(ItemStack stack, World parWorld, double parX, double parY, double parZ)
    {
        if (!parWorld.isRemote)
        {
            String nameFull = "taleofkingdoms." + EntitiesType.getByID(stack.getItemDamage()).getName();

            if (EntityList.stringToClassMapping.containsKey(nameFull))
            {
                EntityLiving entity = (EntityLiving) EntityList.createEntityByName(nameFull, parWorld);
                entity.setLocationAndAngles(parX, parY, parZ,
                        MathHelper.wrapAngleTo180_float(parWorld.rand.nextFloat()
                                * 360.0F), 0.0F);
                parWorld.spawnEntityInWorld(entity);
                entity.onSpawnWithEgg(null);
                entity.playLivingSound();
                return entity;
            }
        }
        return null;
    }
}