package kingdoms.server.entities;

import kingdoms.api.entities.EntityNPC;
import kingdoms.server.handlers.UltimateHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
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

public final class EntityDefendWarrior extends EntityNPC
{
   private static ItemStack defaultHeldItem = new ItemStack(Items.iron_sword, 1);
   private int level;
   private boolean follow;
   private boolean checkPlayer;
   private boolean createdMarker;
   private EntityDefendMarker defend;
   protected int attackStrength;
   public boolean isSwinging;
   public int field_110158_av;


   public EntityDefendWarrior(World world) {
      super(world, defaultHeldItem, 40.0F);
      this.level = 0;
      this.follow = false;
      this.checkPlayer = true;
      this.createdMarker = false;
      this.isImmuneToFire = false;
      this.attackStrength = 6;
   }

   protected boolean canDespawn() {
      return false;
   }

   public void upgrade() {
      EntityLiving entityliving = (EntityLiving) UltimateHelper.INSTANCE.getEntity("DefendKnight", this.worldObj);
      entityliving.setLocationAndAngles(this.posX, this.posY, this.posZ, 0.0F, 0.0F);
      if(!this.worldObj.isRemote)
      {
         this.worldObj.spawnEntityInWorld(entityliving);
      }

      this.setDead();
   }

   public boolean canBePushed() {
      return true;
   }

   protected boolean isMovementCeased() {
      return false;
   }

   public void onLivingUpdate() {
      super.onLivingUpdate();
      float f1;
      PathEntity pathentity1;
      if(this.follow)
      {
          List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(30, 30, 30));

          for (Entity entity : list)
          {
              if (entity instanceof EntityPlayer)
              {
                  EntityPlayer player = (EntityPlayer) entity;

                  if (player.getDistanceToEntity(this) > 5.0F && player.getDistanceToEntity(this) < 18.0F) {
                      pathentity1 = this.worldObj.getPathEntityToEntity(this, player, 16.0F, true, false, false, true);
                  } else {
                      pathentity1 = null;
                  }

                  this.setPathToEntity(pathentity1);
              }
          }
      } else {
         if(!this.createdMarker) {
            this.defend = (EntityDefendMarker) UltimateHelper.INSTANCE.getEntity("DefendMark", this.worldObj);
            this.defend.setLocationAndAngles(this.posX, this.posY, this.posZ, 0.0F, 0.0F);
            this.worldObj.spawnEntityInWorld(this.defend);
            this.createdMarker = true;
         }

         if(this.createdMarker && this.defend != null) {
            f1 = this.defend.getDistanceToEntity(this);
            if(f1 > 5.0F && f1 < 40.0F) {
               pathentity1 = this.worldObj.getPathEntityToEntity(this, this.defend, 40.0F, true, false, false, true);
            } else {
               pathentity1 = null;
            }

            this.setPathToEntity(pathentity1);
         }
      }

   }

   public boolean interact(EntityPlayer player) {
      if(!this.follow) {
         this.follow = true;
         if(!this.worldObj.isRemote) {
             player.addChatMessage(new ChatComponentText("Warrior: I will follow you."));
         }

         this.defend.setDead();
         this.createdMarker = false;
      } else {
         this.follow = false;
         if(!this.worldObj.isRemote) {
             player.addChatMessage(new ChatComponentText("Warrior: I will guard this area."));
         }
      }

      return true;
   }

   protected void updateEntityActionState() {
      super.updateEntityActionState();
      byte i = 6;
      if(this.isSwinging) {
         ++this.field_110158_av;
         if(this.field_110158_av >= i) {
            this.field_110158_av = 0;
            this.isSwinging = false;
         }
      } else {
         this.field_110158_av = 0;
      }

      this.swingProgress = (float)this.field_110158_av / (float)i;

      if(this.checkPlayer)
      {
          List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(30, 30, 30));

          for (Entity entity : list)
          {
              if (entity instanceof EntityPlayer)
              {
                  if (entity.getDistanceSqToEntity(this) <= 64.0D)
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

   protected void attackEntity(Entity entity, float f) {
      if(this.attackTime <= 0 && f < 2.0F && entity.boundingBox.maxY > this.boundingBox.minY && entity.boundingBox.minY < this.boundingBox.maxY) {
         this.attackTime = 20;
         this.swingItem();
         this.attackEntityAsMob(entity);
         ++this.level;
         if(this.level > 7) {
            this.upgrade();
         }
      }
   }

   public boolean attackEntityAsMob(Entity entity) {
      int i = this.attackStrength;
      if(this.isPotionActive(Potion.damageBoost)) {
         i += 3 << this.getActivePotionEffect(Potion.damageBoost).getAmplifier();
      }

      if(this.isPotionActive(Potion.weakness)) {
         i -= 2 << this.getActivePotionEffect(Potion.weakness).getAmplifier();
      }

      return entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float)i);
   }

   public ItemStack getHeldItem() {
      return defaultHeldItem;
   }

   public void swingItem() {
      if(!this.isSwinging || this.field_110158_av < 0) {
         this.field_110158_av = -1;
         this.isSwinging = true;
      }

   }

   public boolean attackEntityFrom(DamageSource damagesource, int i) {
      if(this.worldObj.rand.nextInt(3) == 0) {
         boolean flag = true;
         Entity entity = damagesource.getSourceOfDamage();
         if(entity instanceof EntityDefendBandit || entity instanceof EntityDefendKnight || entity instanceof EntityDefendPaladin || entity instanceof EntityDefendWarrior || entity instanceof EntityDefendArcher || entity instanceof EntityHired || entity instanceof EntityPlayer) {
            flag = false;
         }

         if(flag) {
            super.attackEntityFrom(damagesource, (float)i);
         }
      }

      return true;
   }

   public void onDeath(DamageSource damagesource) {}

}
