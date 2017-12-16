package aginsun.kingdoms.server.entities;

import aginsun.kingdoms.api.entities.EntityNPC;
import aginsun.kingdoms.server.handlers.UltimateHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public final class EntityWorkerMember extends EntityNPC
{
   private World world;
   public static ItemStack defaultHeldItem;
   private boolean markerExist;
   public boolean follow;
   private int hit3;
   public int worktype;
   public boolean freeze;
   public boolean isMining;
   public Entity marker2;
   public boolean isSwinging;
   public int field_110158_av;


   public EntityWorkerMember(World world)
   {
      super(world, defaultHeldItem, 30.0F);
      defaultHeldItem = null;
      this.markerExist = false;
      this.follow = true;
      this.hit3 = 0;
      this.worktype = 0;
      this.freeze = false;
      this.isMining = false;
      this.marker2 = null;
      this.world = world;
      this.isImmuneToFire = false;
   }

   protected boolean isMovementCeased() {
      return this.freeze;
   }

   public boolean interact(EntityPlayer player) {
      boolean flag = false;

      List<Entity> entities = this.world.loadedEntityList;

      entities.stream().filter(entity -> entity instanceof EntityMarkerKeeper).forEach(Entity::setDead);

      this.freeze = false;
      if(this.isMining) {
         this.isMining = false;
      }

      int var10;

      if(this.worktype == 1) {
         var10 = (int)this.posY;
         if(this.follow) {
            for(int var11 = (int)this.posX - 5; (double)var11 < this.posX + 5.0D; ++var11) {
               for(int l = 0; l < 10; ++l) {
                  for(int i1 = (int)this.posZ - 5; (double)i1 < this.posZ + 5.0D; ++i1) {
                     if(this.world.getBlock(var11, var10, i1) == Blocks.log && !flag) {
                        for(int entity2 = var11 - 5; entity2 <= var11 + 5; ++entity2) {
                           for(int k1 = var10 - 5; k1 <= var10 + 5; ++k1) {
                              for(int l1 = i1 - 5; l1 <= i1 + 5; ++l1) {
                                 if(this.world.getBlock(entity2, k1, l1) == Block.getBlockById(18)) {
                                    this.world.setBlock(entity2, k1, l1, Blocks.air);
                                 }
                              }
                           }
                        }

                        Entity entity = UltimateHelper.INSTANCE.getEntity("Marker", this.world);
                        entity.setLocationAndAngles((double)var11, (double)var10, (double)i1, 0.0F, 0.0F);
                        if(!this.world.isRemote)
                        {
                           this.world.spawnEntityInWorld(entity);
                        }

                        flag = true;
                        this.markerExist = true;
                     } else {
                        this.markerExist = false;
                     }
                  }
               }
            }
         }

         if(this.follow && flag && this.worktype == 1 && !this.world.isRemote) {
            player.addChatMessage(new ChatComponentText("Worker: Chopping it down sir!"));
         }

         if(this.follow && !flag && this.worktype == 1 && !this.world.isRemote) {
            player.addChatMessage(new ChatComponentText("Worker: Direct me to a tree and I will start cutting!"));
         }
      }

      if(this.worktype == 2 && !this.isMining) {
         if(this.posY < 50.0D && !this.world.isRemote) {
            player.addChatMessage(new ChatComponentText("Worker: Mining the stone sir!"));
            this.createMine();
            this.isMining = true;
         } else if(!this.world.isRemote) {
            player.addChatMessage(new ChatComponentText("Worker: We must go further underground sir!"));
         }
      }
      return true;
   }

   protected void jump() {
      if(this.follow && !this.freeze) {
         this.motionY = 0.41999998688697815D;
         if(this.isSprinting()) {
            float f = this.rotationYaw * 0.01745329F;
            this.motionX -= (double)(MathHelper.sin(f) * 0.2F);
            this.motionZ += (double)(MathHelper.cos(f) * 0.2F);
         }

         this.isAirBorne = true;
      }

   }

   public void swingItem() {
      if(!this.isSwinging || this.field_110158_av < 0) {
         this.field_110158_av = -1;
         this.isSwinging = true;
      }

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
      if(this.worktype == 1) {
         List f = this.world.getEntitiesWithinAABB(EntityMarkerKeeper.class, AxisAlignedBB.getBoundingBox(this.posX, this.posY, this.posZ, this.posX + 1.0D, this.posY + 1.0D, this.posZ + 1.0D).expand(16.0D, 16.0D, 16.0D));
         if(!f.isEmpty()) {
            this.entityToAttack = (Entity)f.get(this.world.rand.nextInt(f.size()));
         } else {
            this.follow = true;
         }

         for(int var8 = (int)this.posX - 3; var8 <= 3; ++var8) {
            for(int k = (int)this.posY - 3; k <= 3; ++k) {
               for(int l = (int)this.posZ - 3; l <= 3; ++l) {
                  if(this.world.getBlock(var8, k, l) == Block.getBlockById(18)) {
                     this.world.setBlock(var8, k, l, Blocks.air);
                  }
               }
            }
         }
      }

      if(this.follow && this.worktype == 2 && this.marker2 != null) {
         this.entityToAttack = this.marker2;
      }

      if(this.entityToAttack != null) {
         float var7 = this.entityToAttack.getDistanceToEntity(this);
         if(this.entityToAttack.isEntityAlive()) {
            this.attackEntity(this.entityToAttack, var7);
         }

         this.faceEntity(this.entityToAttack, 30.0F, 30.0F);
      }

   }

   protected void attackEntity(Entity entity, float f) {
      Object obj;
      if(entity instanceof EntityMarkerKeeper) {
         obj = entity;
      } else {
         obj = entity;
      }

      if(this.attackTime <= 0 && f < 2.0F && ((Entity)obj).boundingBox.maxY + 20.0D > this.boundingBox.minY && ((Entity)obj).boundingBox.minY < this.boundingBox.maxY + 20.0D && this.worktype == 1) {
         this.attackTime = 20;
         this.swingItem();
         this.follow = false;
      }

      if(this.attackTime <= 0 && f < 2.0F && ((Entity)obj).boundingBox.maxY > this.boundingBox.minY && ((Entity)obj).boundingBox.minY < this.boundingBox.maxY && this.worktype == 2) {
         this.attackTime = 20;
         this.swingItem();
         this.freeze = true;
         if (entity instanceof EntityPlayer)
         {
             EntityPlayer player = (EntityPlayer) entity;
             if(player != null && player.getDistanceSqToEntity(this) <= 3000.0D && this.hit3 > 8) {
                 ItemStack itemstack = new ItemStack(Item.getItemById(4), 1, 0);
                 EntityItem entityitem = new EntityItem(this.world, player.posX, player.posY, player.posZ, itemstack);
                 player.joinEntityItemWithWorld(entityitem);
                 this.hit3 = 0;
             }
         }

         ++this.hit3;
      }

   }

   public void onLivingUpdate()
   {
      super.onLivingUpdate();

      List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(30, 30, 30));

      for (Entity entity : list)
      {
         if (entity instanceof EntityPlayer)
         {
            EntityPlayer player = (EntityPlayer) entity;

            PathEntity pathentity;

            if (player.getDistanceToEntity(this) > 3.5F && player.getDistanceToEntity(this) < 25.0F)
            {
               pathentity = worldObj.getPathEntityToEntity(this, player, 16.0F, true, false, false, true);
            }
            else
            {
               pathentity = null;
            }
            this.setPathToEntity(pathentity);
         }
      }
   }

   private void createMine() {
      int i = (int)this.posX + 1;
      int j = (int)this.posY;
      int k = (int)this.posZ - 3;

      int i1;
      int k1;
      for(i1 = 0; i1 < 5; ++i1) {
         for(k1 = 0; k1 < 3; ++k1) {
            for(int l1 = 1; l1 < 5; ++l1) {
               this.world.setBlock(i + i1, j + k1, k + l1, Blocks.air);
            }
         }
      }

      for(i1 = 1; i1 < 5; ++i1) {
         for(k1 = 1; k1 < 7; ++k1) {
            this.world.setBlock(i + i1, j - 2, k + k1, Blocks.stone);
            this.world.setBlock(i + i1, j - 1, k + k1, Blocks.gravel);
         }
      }

      this.world.setBlock(i + 1, j, k + 1, Blocks.stone);
      this.world.setBlock(i + 1, j, k + 2, Blocks.fence);
      this.world.setBlock(i + 1, j, k + 5, Blocks.fence);
      this.world.setBlock(i + 1, j, k + 6, Blocks.cobblestone);
      this.world.setBlock(i + 1, j + 1, k + 1, Blocks.cobblestone);
      this.world.setBlock(i + 1, j + 1, k + 2, Blocks.fence);
      this.world.setBlock(i + 1, j + 1, k + 5, Blocks.fence);
      this.world.setBlock(i + 1, j + 1, k + 6, Blocks.stone);
      this.world.setBlock(i + 1, j + 2, k + 1, Blocks.stone);
      this.world.setBlock(i + 1, j + 2, k + 2, Blocks.planks);
      this.world.setBlock(i + 1, j + 2, k + 3, Blocks.planks);
      this.world.setBlock(i + 1, j + 2, k + 4, Blocks.planks);
      this.world.setBlock(i + 1, j + 2, k + 5, Blocks.planks);
      this.world.setBlock(i + 1, j + 2, k + 6, Blocks.stone);
      this.world.setBlock(i + 1, j + 3, k + 2, Blocks.cobblestone);
      this.world.setBlock(i + 1, j + 3, k + 3, Blocks.cobblestone);
      this.world.setBlock(i + 1, j + 3, k + 4, Blocks.stone);
      this.world.setBlock(i + 1, j + 3, k + 5, Blocks.cobblestone);
      this.world.setBlock(i + 2, j, k + 1, Blocks.cobblestone);
      this.world.setBlock(i + 2, j, k + 2, Blocks.crafting_table);
      this.world.setBlock(i + 2, j, k + 6, Blocks.cobblestone);
      this.world.setBlock(i + 2, j + 1, k + 1, Blocks.stone);
      this.world.setBlock(i + 2, j + 1, k + 6, Blocks.stone);
      this.world.setBlock(i + 2, j + 2, k + 1, Blocks.cobblestone);
      this.world.setBlock(i + 2, j + 2, k + 6, Blocks.cobblestone);
      this.world.setBlock(i + 2, j + 3, k + 2, Blocks.cobblestone);
      this.world.setBlock(i + 2, j + 3, k + 3, Blocks.stone);
      this.world.setBlock(i + 2, j + 3, k + 4, Blocks.cobblestone);
      this.world.setBlock(i + 2, j + 3, k + 5, Blocks.cobblestone);
      this.world.setBlock(i + 3, j, k + 1, Blocks.stone);
      this.world.setBlock(i + 3, j, k + 5, Blocks.cobblestone);
      this.world.setBlock(i + 3, j, k + 6, Blocks.stone);
      this.world.setBlock(i + 3, j + 1, k + 1, Blocks.cobblestone);
      this.world.setBlock(i + 3, j + 1, k + 6, Blocks.cobblestone);
      this.world.setBlock(i + 3, j + 2, k + 1, Blocks.stone);
      this.world.setBlock(i + 3, j + 2, k + 6, Blocks.stone);
      this.world.setBlock(i + 3, j + 3, k + 2, Blocks.stone);
      this.world.setBlock(i + 3, j + 3, k + 3, Blocks.cobblestone);
      this.world.setBlock(i + 3, j + 3, k + 4, Blocks.cobblestone);
      this.world.setBlock(i + 3, j + 3, k + 5, Blocks.stone);
      this.world.setBlock(i + 4, j, k + 1, Blocks.stone);
      this.world.setBlock(i + 4, j, k + 2, Blocks.cobblestone);
      this.world.setBlock(i + 4, j, k + 6, Blocks.stone);
      this.world.setBlock(i + 4, j + 1, k + 1, Blocks.cobblestone);
      this.world.setBlock(i + 4, j + 1, k + 6, Blocks.stone);
      this.world.setBlock(i + 4, j + 2, k + 1, Blocks.stone);
      this.world.setBlock(i + 4, j + 2, k + 6, Blocks.cobblestone);
      this.world.setBlock(i + 4, j + 3, k + 2, Blocks.stone);
      this.world.setBlock(i + 4, j + 3, k + 3, Blocks.stone);
      this.world.setBlock(i + 4, j + 3, k + 4, Blocks.cobblestone);
      this.world.setBlock(i + 4, j + 3, k + 5, Blocks.stone);
      this.world.setBlockMetadataWithNotify(i + 3, j + 2, k + 2, 50, 3);
      this.marker2 = UltimateHelper.INSTANCE.getEntity("Marker2", this.world);
      this.marker2.setLocationAndAngles((double)(i + 4), (double)(j + 1), (double)(k + 3), 0.0F, 0.0F);
      if(!this.world.isRemote) {
         this.world.spawnEntityInWorld(this.marker2);
      }
   }
}