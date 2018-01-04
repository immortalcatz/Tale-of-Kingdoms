package kingdoms.server.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import kingdoms.server.handlers.UltimateHelper;
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

import java.util.Arrays;
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
        Arrays.stream(EntitiesType.values()).map(type -> new ItemStack(item, 1, type.ordinal())).forEachOrdered(list::add);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int color)
    {
        return (color == 0) ? EntitiesType.getByID(stack.getItemDamage()).getBase() : EntitiesType.getByID(stack.getItemDamage()).getSpots();
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player,
                             World world, int x, int y, int z, int par7, float par8,
                             float par9, float par10)
    {
        if (world.isRemote)
        {
            return true;
        }
        else
        {
            Block block = world.getBlock(x, y, z);
            x += Facing.offsetsXForSide[par7];
            y += Facing.offsetsYForSide[par7];
            z += Facing.offsetsZForSide[par7];
            double d0 = 0.0D;

            if (par7 == 1 && block.getRenderType() == 11)
                d0 = 0.5D;

            Entity entity = spawnEntity(stack, world, x + 0.5D, y + d0, z + 0.5D);

            if (entity != null)
            {
                if (entity instanceof EntityLivingBase && stack.hasDisplayName())
                    ((EntityLiving) entity).setCustomNameTag(stack.getDisplayName());

                if (!player.capabilities.isCreativeMode)
                    --stack.stackSize;
            }

            return true;
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        if (world.isRemote)
        {
            return stack;
        }
        else
        {
            MovingObjectPosition movingobjectposition =
                    getMovingObjectPositionFromPlayer(world, player, true);

            if (movingobjectposition == null)
            {
                return stack;
            }
            else
            {
                if (movingobjectposition.typeOfHit == MovingObjectPosition
                        .MovingObjectType.BLOCK)
                {
                    int x = movingobjectposition.blockX;
                    int y = movingobjectposition.blockY;
                    int z = movingobjectposition.blockZ;

                    if (!world.canMineBlock(player, x, y, z))
                        return stack;

                    if (!player.canPlayerEdit(x, y, z, movingobjectposition.sideHit, stack))
                        return stack;

                    if (world.getBlock(x, y, z) instanceof BlockLiquid)
                    {
                        Entity entity = spawnEntity(stack, world, x, y, z);

                        if (entity != null)
                        {
                            if (entity instanceof EntityLivingBase && stack.hasDisplayName())
                                ((EntityLiving) entity).setCustomNameTag(stack.getDisplayName());

                            if (!player.capabilities.isCreativeMode)
                                --stack.stackSize;
                        }
                    }
                }

                return stack;
            }
        }
    }

    private Entity spawnEntity(ItemStack stack, World world, double x, double y, double z)
    {
        if (!world.isRemote)
        {
            String nameFull = EntitiesType.getByID(stack.getItemDamage()).getName();

            if (EntityList.stringToClassMapping.containsKey("taleofkingdoms." + nameFull))
            {
                EntityLiving entity = (EntityLiving) UltimateHelper.INSTANCE.getEntity(nameFull, world);
                entity.setLocationAndAngles(x, y, z, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0F), 0.0F);
                world.spawnEntityInWorld(entity);
                entity.onSpawnWithEgg(null);
                entity.playLivingSound();
                return entity;
            }
        }
        return null;
    }
}