package kingdoms.server.handlers.schematic;

import kingdoms.api.blocks.FakeBlock;
import kingdoms.api.entities.FakeEntity;
import kingdoms.server.TaleOfKingdoms;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Schematic
{
    public int x, y, z, speed;
    private List<FakeBlock> blocks = new ArrayList<>();
    private List<FakeEntity> entities = new ArrayList<>();

    public Schematic(String s)
    {
        InputStream
                entityData = TaleOfKingdoms.class.getResourceAsStream(s + ".dat"),
                schematicData = TaleOfKingdoms.class.getResourceAsStream(s + ".schematic");

        NBTTagCompound schematic = null, entities = null;

        try
        {
            schematic = CompressedStreamTools.readCompressed(schematicData);
            entities = CompressedStreamTools.readCompressed(entityData);
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }

        this.writeSchematic(Objects.requireNonNull(schematic));

        if (s.contains("Tier4"))
        {
            this.writeEntitiesTier4(Objects.requireNonNull(entities));
        }
        else if (s.contains("Tier3"))
        {
            this.writeEntitiesTier3(Objects.requireNonNull(entities));
        }
        else
        {
            this.writeEntities(Objects.requireNonNull(entities));
        }
    }

    private void writeSchematic(NBTTagCompound compound)
    {
        short
                height = compound.getShort("Height"),
                length = compound.getShort("Length"),
                width = compound.getShort("Width");
        byte[]
                blocksArray = compound.getByteArray("Blocks"),
                dataArray = compound.getByteArray("Data");

        for (int y = 0; y < height; ++y)
        {
            for (int x = 0; x < width; ++x)
            {
                for (int z = 0; z < length; ++z)
                {
                    int index = y * width * length + z * width + x;
                    this.blocks.add(new FakeBlock(blocksArray[index], dataArray[index], x, y, z));
                }
            }
        }
    }

    private void writeEntities(NBTTagCompound compound)
    {
        NBTTagList list = compound.getTagList("Entities", 10);

        int bound = list.tagCount();

        for (int i = 0; i < bound; i++)
        {
            NBTTagCompound nbt = list.getCompoundTagAt(i);
            String s = nbt.getString("EntityName");
            double posX = nbt.getDouble("posX");
            double posY = nbt.getDouble("posY");
            double posZ = nbt.getDouble("posZ");
            this.entities.add(new FakeEntity(s, posX, posY, posZ));
        }
    }

    private void writeEntitiesTier4(NBTTagCompound compound)
    {
        NBTTagList list = compound.getTagList("Entities", 10);

        int bound = list.tagCount();

        for (int i = 0; i < bound; i++)
        {
            NBTTagCompound nbt = list.getCompoundTagAt(i);
            String s = nbt.getString("EntityName");
            double posX = nbt.getDouble("posX");
            double posY = nbt.getDouble("posY");
            double posZ = nbt.getDouble("posZ");
            this.entities.add(new FakeEntity(s, posX + 10.0D, posY, posZ + 5.0D));
        }
    }

    private void writeEntitiesTier3(NBTTagCompound compound)
    {
        NBTTagList list = compound.getTagList("Entities", 10);

        int bound = list.tagCount();

        for (int i = 0; i < bound; i++)
        {
            NBTTagCompound nbt = list.getCompoundTagAt(i);
            String s = nbt.getString("EntityName");
            double posX = nbt.getDouble("posX");
            double posY = nbt.getDouble("posY");
            double posZ = nbt.getDouble("posZ");
            this.entities.add(new FakeEntity(s, posX - 10.0D, posY, posZ - 5.0D));
        }
    }

    public List<FakeBlock> getBlockList()
    {
        return this.blocks;
    }
    public List<FakeEntity> getEntityList()
    {
        return this.entities;
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