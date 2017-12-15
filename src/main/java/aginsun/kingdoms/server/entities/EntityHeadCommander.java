package aginsun.kingdoms.server.entities;

import aginsun.kingdoms.api.entities.EntityNPC;
import aginsun.kingdoms.server.TaleOfKingdoms;
import aginsun.kingdoms.server.handlers.UltimateHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.potion.Potion;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.List;

import static aginsun.kingdoms.server.handlers.GuiHandler.GUI_HEADCOMMANDER;

public final class EntityHeadCommander extends EntityNPC
{
    public boolean follow;
    private boolean checkPlayer;
    public boolean createdMarker;
    public EntityDefendMarker defend;
    protected int attackStrength;
    public boolean isSwinging;
    public int field_110158_av;

    public EntityHeadCommander(World world)
    {
        super(world, new ItemStack(Items.iron_sword, 1), 50.0F);
        this.follow = false;
        this.checkPlayer = true;
        this.createdMarker = false;
        this.isImmuneToFire = false;
        this.attackStrength = 10;
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        PathEntity pathentity1;

        if (this.follow)
        {
            List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(30, 30, 30));

            for (Entity entity : list)
            {
                if (entity instanceof EntityPlayer)
                {
                    EntityPlayer player = (EntityPlayer) entity;

                    if (player != null)
                    {
                        if (player.getDistanceToEntity(this) > 5.0F && player.getDistanceToEntity(this) < 18.0F)
                        {
                            pathentity1 = this.worldObj.getPathEntityToEntity(this, player, 16.0F, true, false, false, true);
                        }
                        else
                        {
                            pathentity1 = null;
                        }

                        this.setPathToEntity(pathentity1);
                    }
                }
            }
        }
        else
        {
            if (!this.createdMarker)
            {
                this.defend = (EntityDefendMarker) UltimateHelper.INSTANCE.getEntity("DefendMark", this.worldObj);
                this.defend.setLocationAndAngles(this.posX, this.posY, this.posZ, 0.0F, 0.0F);
                this.worldObj.spawnEntityInWorld(this.defend);
                this.createdMarker = true;
            }

            if (this.createdMarker && this.defend != null)
            {
                if (this.defend.getDistanceToEntity(this) > 5.0F && this.defend.getDistanceToEntity(this) < 40.0F)
                {
                    pathentity1 = this.worldObj.getPathEntityToEntity(this, this.defend, 40.0F, true, false, false, true);
                }
                else
                {
                    pathentity1 = null;
                }

                this.setPathToEntity(pathentity1);
            }
        }
    }

    @Override
    public boolean interact(EntityPlayer player)
    {
        if (!this.worldObj.isRemote)
        {
            player.addChatMessage(new ChatComponentText("Knight Commander: I will lead your troops to battle!"));
        }
        player.openGui(TaleOfKingdoms.instance, GUI_HEADCOMMANDER, worldObj, 0, 0, 0);
        return true;
    }

    @Override
    protected void updateEntityActionState()
    {
        super.updateEntityActionState();
        byte i = 6;
        if (this.isSwinging)
        {
            ++this.field_110158_av;
            if (this.field_110158_av >= i)
            {
                this.field_110158_av = 0;
                this.isSwinging = false;
            }
        }
        else
        {
            this.field_110158_av = 0;
        }

        this.swingProgress = (float) this.field_110158_av / (float) i;

        if (this.checkPlayer)
        {
            List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(30, 30, 30));

            for (Entity entity : list)
            {
                if (entity instanceof EntityPlayer)
                {
                    EntityPlayer player = (EntityPlayer) entity;

                    if (player.getDistanceSqToEntity(this) <= 64.0D)
                    {
                        this.follow = true;
                    }
                }

                if (this.canEntityBeSeen(entity) && (entity instanceof EntityMob || entity instanceof EntityReficulSoldier || entity instanceof EntityReficulGuardian || entity instanceof EntityReficulMage))
                {
                    this.entityToAttack = entity;
                }
            }
        }

        this.checkPlayer = false;
    }

    @Override
    protected void attackEntity(Entity entity, float f)
    {
        if (this.attackTime <= 0 && f < 2.0F && entity.boundingBox.maxY > this.boundingBox.minY && entity.boundingBox.minY < this.boundingBox.maxY)
        {
            this.attackTime = 20;
            this.swingItem();
            this.attackEntityAsMob(entity);
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity entity)
    {
        int i = this.attackStrength;

        if (this.isPotionActive(Potion.damageBoost))
        {
            i += 3 << this.getActivePotionEffect(Potion.damageBoost).getAmplifier();
        }

        if (this.isPotionActive(Potion.weakness))
        {
            i -= 2 << this.getActivePotionEffect(Potion.weakness).getAmplifier();
        }
        return entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float) i);
    }

    @Override
    public void swingItem()
    {
        if (!this.isSwinging || this.field_110158_av < 0)
        {
            this.field_110158_av = -1;
            this.isSwinging = true;
        }
    }

    public boolean attackEntityFrom(DamageSource damagesource, int i)
    {
        if (this.worldObj.rand.nextInt(3) != 0)
        {
            boolean flag = true;
            Entity entity = damagesource.getSourceOfDamage();

            if (entity instanceof EntityDefendBandit || entity instanceof EntityDefendKnight || entity instanceof EntityDefendPaladin || entity instanceof EntityDefendWarrior || entity instanceof EntityDefendArcher || entity instanceof EntityHired || entity instanceof EntityPlayer)
            {
                flag = false;
            }

            if (flag)
            {
                super.attackEntityFrom(damagesource, (float)i);
            }
        }
        return true;
    }
}