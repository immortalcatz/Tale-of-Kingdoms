package kingdoms.server.entities;

import kingdoms.api.entities.EntityNPC;
import kingdoms.server.PlayerProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

public final class EntitySantaClaus extends EntityNPC
{
    private int timer = 6000;
    private Calendar calendar;
    private List<Item> stacks = new ArrayList<>();

    public EntitySantaClaus(World world)
    {
        super(world, new ItemStack(Item.getItemFromBlock(Blocks.snow)), 40.0F);
        calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        stacks.add(Items.golden_apple);
        stacks.add(Items.experience_bottle);
        stacks.add(Items.milk_bucket);
        stacks.add(Items.cookie);
        stacks.add(Items.mushroom_stew);
        stacks.add(Items.painting);
        stacks.add(Items.saddle);
        stacks.add(Items.nether_star);
        stacks.add(Items.diamond_horse_armor);
        stacks.add(Items.golden_horse_armor);
        stacks.add(Items.iron_horse_armor);
        stacks.add(Items.compass);
        stacks.add(Items.clock);
        stacks.add(Items.diamond);
        stacks.add(Items.emerald);
        stacks.add(Items.iron_ingot);
        stacks.add(Items.gold_ingot);
        stacks.add(Items.record_wait);
    }

    @Override
    public boolean canBePushed()
    {
        return false;
    }

    @Override
    protected boolean interact(EntityPlayer player)
    {
        if (!worldObj.isRemote)
        {
            if (this.canInteractWith(player))
                player.addChatMessage(!PlayerProvider.get(player).badKid ? calendar.get(Calendar.MONTH) + 1 == 1 && calendar.get(Calendar.DATE) == Calendar.SUNDAY ? new ChatComponentTranslation("npc.santa.new.year") : new ChatComponentTranslation("npc.santa.dialog") : new ChatComponentTranslation("npc.santa.badKid"));
        }
        return true;
    }

    @Override
    public void onDeath(DamageSource damageSource)
    {
        if (!worldObj.isRemote)
        {
            if (damageSource.getSourceOfDamage() instanceof EntityPlayer)
            {
                EntityPlayer player = (EntityPlayer) damageSource.getEntity();
                player.addChatMessage(new ChatComponentTranslation("npc.santa.badKid.dialog"));
                player.dropItem(Items.coal, worldObj.rand.nextInt(32));

                PlayerProvider.get(player).badKid = true;

                int bound = rand.nextInt(5);

                for (int i = 1; i < bound; i++)
                {
                    EntitySnowman golem = new EntitySnowman(worldObj);
                    golem.setLocationAndAngles(this.posX, this.posY, this.posZ, 0.0F, 0.0F);
                    golem.setAttackTarget(player);
                    worldObj.spawnEntityInWorld(golem);
                }
            }
        }
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if (!worldObj.isRemote)
        {
            int posX, posY, posZ;

            --timer;

            for (int l = 0; l < 4; ++l)
            {
                posX = MathHelper.floor_double(this.posX + (double)((float)(l % 2 * 2 - 1) * 0.25F));
                posY = MathHelper.floor_double(this.posY);
                posZ = MathHelper.floor_double(this.posZ + (double)((float)(l / 2 % 2 * 2 - 1) * 0.25F));

                if (this.worldObj.getBlock(posX, posY, posZ).getMaterial() == Material.air && Blocks.snow_layer.canPlaceBlockAt(this.worldObj, posX, posY, posZ))
                    this.worldObj.setBlock(posX, posY, posZ, Blocks.snow_layer);
            }

            List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(30, 30, 30));

            for (Entity entity : list)
            {
                if (entity instanceof EntityPlayer)
                {
                    EntityPlayer player = (EntityPlayer) entity;

                    if (player.getDistanceToEntity(this) > 2F && player.getDistanceToEntity(this) < 8F)
                    {
                        if (timer <= 0)
                        {
                            if (!PlayerProvider.get(player).badKid)
                            {
                                this.dropItem(Items.cookie, worldObj.rand.nextInt(25));
                                this.worldObj.setBlock((int) this.posX - 1, (int) this.posY, (int) this.posZ - 1, Blocks.cake);

                                if (worldObj.rand.nextInt(4) == 2)
                                {
                                    this.worldObj.setBlock((int) this.posX + 1, (int) this.posY, (int) this.posZ + 1, Blocks.chest);

                                    TileEntity te = worldObj.getTileEntity((int) this.posX + 1, (int) this.posY, (int) this.posZ + 1);

                                    if (te instanceof TileEntityChest)
                                    {
                                        IntStream.range(worldObj.rand.nextInt(25), worldObj.rand.nextInt(27)).forEach(i -> {
                                            Item item = stacks.get(i) == null ? Item.getItemFromBlock(Blocks.air) : stacks.get(i);
                                            ((TileEntityChest) te).setInventorySlotContents(i, new ItemStack(item, i));
                                        });
                                    }
                                }
                            }
                            else
                            {
                                this.dropItem(Items.coal, worldObj.rand.nextInt(25));

                                EntitySnowman golem = new EntitySnowman(worldObj);
                                golem.setPosition(this.posX, this.posY, this.posZ);
                                golem.setAttackTarget(player);
                                worldObj.spawnEntityInWorld(golem);
                            }
                            timer = 6000;
                        }

                        if (timer < 0)
                        {
                            timer = 6000;
                        }
                    }
                }
            }
        }
    }
}