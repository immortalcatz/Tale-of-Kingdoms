package aginsun.kingdoms.server.items;

import aginsun.kingdoms.server.handlers.UltimateHelper;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;

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
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        Arrays.stream(EntitiesType.values()).map(type -> new ItemStack(this, 1, type.ordinal())).forEachOrdered(items::add);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int color)
    {
        return (color == 0) ? EntitiesType.getByID(stack.getItemDamage()).getBase() : EntitiesType.getByID(stack.getItemDamage()).getSpots();
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player,
                             World world, int par4, int par5, int par6, int par7, float par8,
                             float par9, float par10)
    {
        if (world.isRemote)
        {
            return true;
        }
        else
        {
            IBlockState block = world.getBlockState(new BlockPos(par4, par5, par6));
            par4 += Facing.offsetsXForSide[par7];
            par5 += Facing.offsetsYForSide[par7];
            par6 += Facing.offsetsZForSide[par7];
            double d0 = 0.0D;

            if (par7 == 1 && block.getRenderType() == 11)
                d0 = 0.5D;

            Entity entity = spawnEntity(stack, world, par4 + 0.5D, par5 + d0, par6 + 0.5D);

            if (entity != null)
            {
                if (entity instanceof EntityLivingBase && stack.hasDisplayName())
                    entity.setCustomNameTag(stack.getDisplayName());

                if (!player.capabilities.isCreativeMode)
                    stack.shrink(1);
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
            RayTraceResult raytraceresult = this.rayTrace(world, player, true);

            if (raytraceresult == null)
            {
                return stack;
            }
            else
            {
                if (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK)
                {
                    BlockPos blockpos = raytraceresult.getBlockPos();

                    int i = raytraceresult.blockX;
                    int j = raytraceresult.blockY;
                    int k = raytraceresult.blockZ;

                    if (!world.canMineBlock(player, i, j, k))
                        return stack;

                    if (!player.canPlayerEdit(i, j, k, raytraceresult.sideHit, stack))
                        return stack;

                    if (world.getBlock(i, j, k) instanceof BlockLiquid)
                    {
                        Entity entity = spawnEntity(stack, world, i, j, k);

                        if (entity != null)
                        {
                            if (entity instanceof EntityLivingBase && stack.hasDisplayName())
                                entity.setCustomNameTag(stack.getDisplayName());

                            if (!player.capabilities.isCreativeMode)
                                stack.shrink(1);
                        }
                    }
                }

                return stack;
            }
        }
    }

    public Entity spawnEntity(ItemStack stack, World world, double parX, double parY, double parZ)
    {
        if (!world.isRemote)
        {
            String nameFull = EntitiesType.getByID(stack.getItemDamage()).getName();

            if (EntityList.stringToClassMapping.containsKey("taleofkingdoms." + nameFull))
            {
                EntityLiving entity = (EntityLiving) UltimateHelper.INSTANCE.getEntity(nameFull, world);
                entity.setLocationAndAngles(parX, parY, parZ, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0F), 0.0F);
                world.spawnEntityInWorld(entity);
                entity.onSpawnWithEgg(null);
                entity.playLivingSound();
                return entity;
            }
        }
        return null;
    }
}