package kingdoms.server.entities;

import kingdoms.api.entities.EntityNPC;
import kingdoms.server.PlayerProvider;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import java.util.List;
import java.util.stream.IntStream;

public class EntityReficulGuardian extends EntityNPC
{
   private EntityPlayer player;
   private boolean playerPresence = true;
   protected int attackStrength;


   public EntityReficulGuardian(World world) {
      super(world, new ItemStack(Items.bow, 1), 30.0F);
      this.attackStrength = 7;
      this.isImmuneToFire = true;
   }

   public void onLivingUpdate() {
      super.onLivingUpdate();

      IntStream.range(0, 2).forEach(i -> this.worldObj.spawnParticle("portal", this.posX + (this.worldObj.rand.nextDouble() - 0.5D) * (double) this.width, this.posY + this.worldObj.rand.nextDouble() * (double) this.height - 0.25D, this.posZ + (this.worldObj.rand.nextDouble() - 0.5D) * (double) this.width, (this.worldObj.rand.nextDouble() - 0.5D) * 2.0D, -this.worldObj.rand.nextDouble(), (this.worldObj.rand.nextDouble() - 0.5D) * 2.0D));
   }

   protected boolean teleportToEntity(Entity entity) {
      Vec3 vec3d = Vec3.createVectorHelper(this.posX - entity.posX, this.boundingBox.minY + (double)(this.height / 2.0F) - entity.posY + (double)entity.getEyeHeight(), this.posZ - entity.posZ);
      vec3d = vec3d.normalize();
      double d = 16.0D;
      double d1 = this.posX + (this.worldObj.rand.nextDouble() - 0.5D) * 8.0D - vec3d.xCoord * d;
      double d2 = this.posY + (double)(this.worldObj.rand.nextInt(16) - 8) - vec3d.yCoord * d;
      double d3 = this.posZ + (this.worldObj.rand.nextDouble() - 0.5D) * 8.0D - vec3d.zCoord * d;
      return this.teleportTo(d1, d2, d3);
   }

   protected boolean teleportTo(double d, double d1, double d2) {
      if(this.worldObj.rand.nextInt(10) == 0) {
         double d3 = this.posX;
         double d4 = this.posY;
         double d5 = this.posZ;
         this.posX = d;
         this.posY = d1;
         this.posZ = d2;
         boolean flag = false;
         int i = MathHelper.floor_double(this.posX);
         int j = MathHelper.floor_double(this.posY);
         int k = MathHelper.floor_double(this.posZ);
         Block j1;
         if(this.worldObj.blockExists(i, j, k)) {
            boolean l = false;

            while(!l && j > 0) {
               j1 = this.worldObj.getBlock(i, j - 1, k);
               if(j1 != null && j1.getMaterial().isSolid()) {
                  l = true;
               } else {
                  --this.posY;
                  --j;
               }
            }

            if(l) {
               this.setPosition(this.posX, this.posY, this.posY);
               if(this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).size() == 0 && !this.worldObj.isAnyLiquid(this.boundingBox)) {
                  flag = true;
               }
            }
         }

         if(!flag) {
            this.setPosition(d3, d4, d5);
            return false;
         }

         short var30 = 128;

         /*for(j1 = 0; j1 < var30; ++j1) {
            double d6 = (double)j1 / ((double)var30 - 1.0D);
            float f = (this.field_70146_Z.nextFloat() - 0.5F) * 0.2F;
            float f1 = (this.field_70146_Z.nextFloat() - 0.5F) * 0.2F;
            float f2 = (this.field_70146_Z.nextFloat() - 0.5F) * 0.2F;
            double d7 = d3 + (this.posX - d3) * d6 + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.width * 2.0D;
            double d8 = d4 + (this.posY - d4) * d6 + this.field_70146_Z.nextDouble() * (double)this.height;
            double d9 = d5 + (this.posZ - d5) * d6 + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.width * 2.0D;
            this.field_70170_p.spawnParticle("portal", d7, d8, d9, (double)f, (double)f1, (double)f2);
         }*/
      }

      return true;
   }

   public void onDeath(DamageSource damagesource)
   {
      PlayerProvider.get(player).addGlory(70);
   }

   protected void attackEntity(Entity entity, float f) {
      if(f < 10.0F) {
         double d = entity.posX - this.posX;
         double d1 = entity.posZ - this.posZ;
         if(this.attackTime == 0) {
            EntityArrow entityarrow = new EntityArrow(this.worldObj, this, 1.0F);
            double d2 = entity.posY + (double)entity.getEyeHeight() - 0.699999988079071D - entityarrow.posY;
            float f1 = MathHelper.sqrt_double(d * d + d1 * d1) * 0.2F;
            this.worldObj.playSoundAtEntity(this, "random.bow", 1.0F, 1.0F / (this.worldObj.rand.nextFloat() * 0.4F + 0.8F));
            if(this.worldObj.rand.nextInt(8) != 0) {
               this.worldObj.spawnEntityInWorld(entityarrow);
               entityarrow.setThrowableHeading(d, d2 + (double)f1, d1, 1.6F, 12.0F);
            }

            this.attackTime = 30;
         }

         this.rotationYaw = (float)(Math.atan2(d1, d) * 180.0D / 3.1415927410125732D) - 90.0F;
         this.hasAttacked = true;
      }

   }

   protected boolean isMovementCeased() {
      return this.playerPresence;
   }

   public void knockBack(Entity entity, int i, double d, double d1) {
      if(this.worldObj.rand.nextInt(2) == 0) {
         this.isAirBorne = true;
         float f = MathHelper.sqrt_double(d * d + d1 * d1);
         float f1 = 0.4F;
         this.motionX /= 2.0D;
         this.motionY /= 2.0D;
         this.motionZ /= 2.0D;
         this.motionX -= d / (double)f * (double)f1;
         this.motionY += 0.4000000059604645D;
         this.motionZ -= d1 / (double)f * (double)f1;
         if(this.motionY > 0.4000000059604645D) {
            this.motionY = 0.4000000059604645D;
         }
      }

   }

   protected void updateEntityActionState() {
      super.updateEntityActionState();

      List<Entity> entities = this.worldObj.loadedEntityList;

      entities.stream().filter(entity -> entity instanceof EntityPlayer).forEach(entity -> this.player = (EntityPlayer) entity);

      if(this.player != null) {
         if(this.player.getDistanceSqToEntity(this) <= 220.0D && this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL) {
            this.playerPresence = false;
            if(this.worldObj.rand.nextInt(6) == 0) {
               this.teleportToEntity(this.player);
               if(this.worldObj.rand.nextInt(10) == 0) {
                  IntStream.range(0, 2).forEach(j -> this.worldObj.spawnParticle("largesmoke", this.posX + (this.worldObj.rand.nextDouble() - 0.5D) * (double) this.width, this.posY + this.worldObj.rand.nextDouble() * (double) this.height - 0.25D, this.posZ + (this.worldObj.rand.nextDouble() - 0.5D) * (double) this.width, (this.worldObj.rand.nextDouble() - 0.5D) * 2.0D, -this.worldObj.rand.nextDouble(), (this.worldObj.rand.nextDouble() - 0.5D) * 2.0D));
               }
            }
         } else {
            this.playerPresence = true;
         }
      }

   }

   protected Entity findPlayerToAttack() {
      EntityPlayer entityplayer = this.worldObj.getClosestPlayerToEntity(this, 16.0D);
      return entityplayer != null && this.canEntityBeSeen(entityplayer) && this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL?entityplayer:null;
   }

   public boolean attackEntityFrom(DamageSource damagesource, int i) {
      if(!this.playerPresence && this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL) {
         if(super.attackEntityFrom(damagesource, (float)i)) {
            Entity entity = damagesource.getSourceOfDamage();
            if(this.riddenByEntity != entity && this.ridingEntity != entity) {
               if(entity != this) {
                  this.entityToAttack = entity;
               }

               return true;
            } else {
               return true;
            }
         } else {
            return false;
         }
      } else {
         return true;
      }
   }

}
