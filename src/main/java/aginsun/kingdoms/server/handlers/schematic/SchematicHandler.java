package aginsun.kingdoms.server.handlers.schematic;

import aginsun.kingdoms.api.blocks.FakeBlock;
import aginsun.kingdoms.api.entities.FakeEntity;
import aginsun.kingdoms.server.handlers.Buildings;
import aginsun.kingdoms.server.handlers.UltimateHelper;
import aginsun.kingdoms.server.handlers.resources.GuildHandler;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

public final class SchematicHandler
{
    private int index;
    public static final SchematicHandler INSTANCE = new SchematicHandler();
    private ArrayList torchList = new ArrayList(), buildingList = new ArrayList();

    public void addBuilding(Schematic schematic)
    {
        this.buildingList.add(schematic);
    }

    public ArrayList getBuildingList()
    {
        return this.buildingList;
    }

    public void update(World world)
    {
        if (!this.buildingList.isEmpty())
        {
            Schematic x = (Schematic)this.buildingList.get(0);

            if (x != null)
            {
                ArrayList arrayList = x.getBlockList();
                ArrayList arrayList1 = x.getEntityList();

                if (!arrayList.isEmpty() && !arrayList1.isEmpty())
                {
                    for (int i = 0; i < x.speed; ++i)
                    {
                        if (this.index < arrayList.size())
                        {
                            FakeBlock fakeBlock = (FakeBlock)arrayList.get(this.index);

                            if (fakeBlock != null && world.getBlockState(new BlockPos(x.x + fakeBlock.posX, x.y + fakeBlock.posY, x.z + fakeBlock.posZ)) != fakeBlock.block)
                            {
                                if(fakeBlock.block == Blocks.AIR)
                                    world.setBlockToAir(new BlockPos(x.x + fakeBlock.posX, x.y + fakeBlock.posY, x.z + fakeBlock.posZ));

                                if (fakeBlock.block != Blocks.TORCH && fakeBlock.block != Blocks.OAK_DOOR && fakeBlock.block != Blocks.LADDER && fakeBlock.block != Blocks.TRAPDOOR)
                                {
                                    world.setBlockState(new BlockPos(x.x + fakeBlock.posX, x.y + fakeBlock.posY, x.z + fakeBlock.posZ), fakeBlock.block.getDefaultState());
                                }
                                else
                                    this.torchList.add(fakeBlock);
                            }

                            ++this.index;
                        }
                        else if (this.index < arrayList.size() + arrayList1.size())
                        {
                            FakeEntity var8 = (FakeEntity)arrayList1.get(this.index - arrayList.size());
                            EntityLiving block = (EntityLiving) UltimateHelper.INSTANCE.getEntity(var8.entityName, world);

                            if (block != null)
                            {
                                if (Buildings.INSTANCE.getBuilding(1))
                                {
                                    block.setPosition((double) GuildHandler.INSTANCE.getTownX() + var8.posX, (double) GuildHandler.INSTANCE.getTownY() + var8.posY, (double) GuildHandler.INSTANCE.getTownZ() + var8.posZ);
                                }
                                else
                                {
                                    block.setPosition((double)x.x + var8.posX, (double)x.y + var8.posY + 1.5D, (double)x.z + var8.posZ);
                                }

                                world.spawnEntity(block);
                            }

                            ++this.index;
                        }
                        else
                        {
                            for (Object aTorchList : this.torchList)
                            {
                                FakeBlock var10 = (FakeBlock) aTorchList;
                                world.setBlockState(new BlockPos(x.x + var10.posX, x.y + var10.posY, x.z + var10.posZ), var10.block.getDefaultState());
                            }

                            this.index = 0;

                            if (!this.buildingList.isEmpty())
                                this.buildingList.remove(0);

                            this.torchList.clear();
                        }
                    }
                }
            }
        }
    }

    public float getProgressCurrentBuilding()
    {
        if (this.buildingList.isEmpty())
        {
            return 0.0F;
        }
        else
        {
            ArrayList arrayList = ((Schematic) this.buildingList.get(0)).getBlockList();
            return this.index / (float) arrayList.size() * 100.0F;
        }
    }
}