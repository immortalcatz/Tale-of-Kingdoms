package aginsun.kingdoms.server.handlers.schematic;

import aginsun.kingdoms.api.blocks.FakeBlock;
import aginsun.kingdoms.api.entities.FakeEntity;
import aginsun.kingdoms.server.TaleOfKingdoms;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public final class Schematic
{
    private ArrayList blockList = new ArrayList(), entityList = new ArrayList();
    private byte[] blocksArray, dataArray;
    private short height, length, width;
    public int x, y, z, speed;

    public Schematic(String s)
    {
        InputStream
                entityData = TaleOfKingdoms.class.getResourceAsStream(s + ".dat"),
                schematicData = TaleOfKingdoms.class.getResourceAsStream(s + ".schematic");

        NBTTagCompound
                nbtSchematic = null, nbtEntities = null;

        try
        {
            nbtSchematic = CompressedStreamTools.readCompressed(schematicData);
            nbtEntities = CompressedStreamTools.readCompressed(entityData);
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }

        this.writeSchematic(nbtSchematic);

        if (s.contains("Tier4"))
        {
            this.writeEntitiesTier4(nbtEntities);
        }
        else if (s.contains("Tier3"))
        {
            this.writeEntitiesTier3(nbtEntities);
        }
        else
        {
            this.writeEntities(nbtEntities);
        }
    }

    private void writeSchematic(NBTTagCompound nbt)
    {
        this.height = nbt.getShort("Height");
        this.length = nbt.getShort("Length");
        this.width = nbt.getShort("Width");
        this.blocksArray = nbt.getByteArray("Blocks");
        this.dataArray = nbt.getByteArray("Data");

        for (int y = 0; y < this.height; ++y)
        {
            for (int x = 0; x < this.width; ++x)
            {
                for (int z = 0; z < this.length; ++z)
                {
                    int index = y * this.width * this.length + z * this.width + x;
                    this.blockList.add(new FakeBlock(this.blocksArray[index], this.dataArray[index], x, y, z));
                }
            }
        }
    }

    private void writeEntities(NBTTagCompound nbt)
    {
        NBTTagList list = nbt.getTagList("Entities", 10);

        for (int i = 0; i < list.tagCount(); ++i)
        {
            NBTTagCompound nbt1 = list.getCompoundTagAt(i);
            String s = nbt1.getString("EntityName");
            double posX = nbt1.getDouble("posX");
            double posY = nbt1.getDouble("posY");
            double posZ = nbt1.getDouble("posZ");
            this.entityList.add(new FakeEntity(s, posX, posY, posZ));
        }
    }

    private void writeEntitiesTier4(NBTTagCompound nbt)
    {
        NBTTagList list = nbt.getTagList("Entities", 10);

        for (int i = 0; i < list.tagCount(); ++i)
        {
            NBTTagCompound nbt1 = list.getCompoundTagAt(i);
            String s = nbt1.getString("EntityName");
            double posX = nbt1.getDouble("posX");
            double posY = nbt1.getDouble("posY");
            double posZ = nbt1.getDouble("posZ");
            this.entityList.add(new FakeEntity(s, posX + 10.0D, posY, posZ + 5.0D));
        }
    }

    private void writeEntitiesTier3(NBTTagCompound nbt)
    {
        NBTTagList list = nbt.getTagList("Entities", 10);

        for (int i = 0; i < list.tagCount(); ++i)
        {
            NBTTagCompound nbt1 = list.getCompoundTagAt(i);
            String s = nbt1.getString("EntityName");
            double posX = nbt1.getDouble("posX");
            double posY = nbt1.getDouble("posY");
            double posZ = nbt1.getDouble("posZ");
            this.entityList.add(new FakeEntity(s, posX - 10.0D, posY, posZ - 5.0D));
        }
    }

    public ArrayList getBlockList()
    {
        return this.blockList;
    }

    public ArrayList getEntityList()
    {
        return this.entityList;
    }

    public void setBlockList(ArrayList blockList)
    {
        this.blockList = blockList;
    }

    public Schematic setPosition(int posX, int posY, int posZ)
    {
        this.x = posX;
        this.y = posY;
        this.z = posZ;
        return this;
    }

    public Schematic setSpeed(int i)
    {
        this.speed = i;
        return this;
    }
}