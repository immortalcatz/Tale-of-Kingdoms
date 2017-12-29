package kingdoms.server.entities;

import kingdoms.api.entities.EntityNPC;
import kingdoms.server.handlers.UltimateHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public final class EntityDefendArcher extends EntityNPC
{
    private int level = 0;
    private int attackStrength;
    private EntityDefendMarker defend;
    private boolean follow = false, checkPlayer = true, createdMarker = false;

    public EntityDefendArcher(World world)
    {
        super(world, new ItemStack(Items.bow), 40.0F);
        this.isImmuneToFire = false;
        this.attackStrength = 10;
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(30, 30, 30));

        for (Entity entity : list)
        {
            if (entity instanceof EntityPlayer)
            {
                EntityPlayer player = (EntityPlayer) entity;
                PathEntity pathEntity;

                if (this.follow)
                {
                    if (player != null)
                    {
                        if (player.getDistanceToEntity(this) > 5.0F && player.getDistanceToEntity(this) < 18.0F)
                        {
                            pathEntity = this.worldObj.getPathEntityToEntity(this, player, 16.0F, true, false, false, true);
                        }
                        else
                        {
                            pathEntity = null;
                        }
                        this.setPathToEntity(pathEntity);
                    }
                }
                else
                {
                    if (!this.createdMarker)
                    {
                        this.defend = (EntityDefendMarker) UltimateHelper.INSTANCE.getEntity("DefendMark", worldObj);
                        this.defend.setLocationAndAngles(this.posX, this.posY, this.posZ, 0.0F, 0.0F);
                        this.worldObj.spawnEntityInWorld(this.defend);
                        this.createdMarker = true;
                    }

                    if (this.createdMarker && this.defend != null)
                    {
                        if (this.defend.getDistanceToEntity(this) > 5.0F && this.defend.getDistanceToEntity(this) < 40.0F)
                        {
                            pathEntity = this.worldObj.getPathEntityToEntity(this, this.defend, 40.0F, true, false, false, true);
                        }
                        else
                        {
                            pathEntity = null;
                        }
                        this.setPathToEntity(pathEntity);
                    }
                }
            }
        }
    }

    @Override
    public boolean interact(EntityPlayer player)
    {
        if (!worldObj.isRemote)
        {
            if (!this.follow)
            {
                this.follow = true;

                player.addChatMessage(new ChatComponentText("Archer: I will follow you."));

                this.defend.setDead();
                this.createdMarker = false;
            }
            else
            {
                this.follow = false;
                player.addChatMessage(new ChatComponentText("Archer:I will guard this area."));
            }
        }
        return true;
    }

    public void upgrade()
    {
        if (!this.worldObj.isRemote)
        {
            EntityLiving entityliving = (EntityLiving) UltimateHelper.INSTANCE.getEntity("DefendBandit", this.worldObj);
            entityliving.setLocationAndAngles(this.posX, this.posY, this.posZ, 0.0F, 0.0F);
            this.worldObj.spawnEntityInWorld(entityliving);
            this.setDead();
        }
    }

    @Override
    protected void updateEntityActionState()
    {
        super.updateEntityActionState();

        if (this.checkPlayer)
        {
            List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(30, 30, 30));

            for (Entity entity : list)
            {
                if (entity instanceof EntityPlayer)
                {
                    EntityPlayer player = (EntityPlayer) entity;

                    if(player.getDistanceSqToEntity(this) <= 64.0D)
                    {
                        this.follow = true;
                    }
                }

                if (this.entityToAttack == null && !this.hasPath())
                {
                    if (this.canEntityBeSeen(entity) && (entity instanceof EntityMob || entity instanceof EntityReficulSoldier || entity instanceof EntityReficulGuardian || entity instanceof EntityReficulMage))
                    {
                        this.entityToAttack = entity;
                    }
                }
            }
        }

        this.checkPlayer = false;
    }

    @Override
    protected void attackEntity(Entity entity, float f)
    {
        if (f < 10.0F)
        {
            double d = entity.posX - this.posX, d1 = entity.posZ - this.posZ;

            if (this.attackTime == 0)
            {
                EntityArrow entityarrow = new EntityArrow(this.worldObj, this, 1.0F);
                double d2 = entity.posY + (double) entity.getEyeHeight() - 0.699999988079071D - entityarrow.posY;
                float f1 = MathHelper.sqrt_double(d * d + d1 * d1) * 0.2F;
                this.worldObj.playSoundAtEntity(this, "random.bow", 1.0F, 1.0F / (this.rand.nextFloat() * 0.4F + 0.8F));
                this.worldObj.spawnEntityInWorld(entityarrow);
                entityarrow.setThrowableHeading(d, d2 + (double)f1, d1, 1.6F, 12.0F);
                this.attackTime = 30;
                ++this.level;

                if (this.level > 12)
                    this.upgrade();
            }

            this.rotationYaw = (float)(Math.atan2(d1, d) * 180.0D / 3.1415927410125732D) - 90.0F;
            this.hasAttacked = true;
        }
    }

    public boolean attackEntityFrom(DamageSource damagesource, int i)
    {
        boolean flag = true;
        Entity entity = damagesource.getSourceOfDamage();

        if (entity instanceof EntityDefendBandit || entity instanceof EntityDefendKnight || entity instanceof EntityDefendMage || entity instanceof EntityDefendPaladin || entity instanceof EntityDefendWarrior || entity instanceof EntityDefendArcher || entity instanceof EntityHired || entity instanceof EntityPlayer)
            flag = false;

        if (flag)
            super.attackEntityFrom(damagesource, (float) i);
        return true;
    }

    public void onDeath(DamageSource damagesource) {}
}