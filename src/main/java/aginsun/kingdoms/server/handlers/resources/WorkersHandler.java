package aginsun.kingdoms.server.handlers.resources;

public final class WorkersHandler
{
    private byte lumberMembers, quarryMembers;
    public static final WorkersHandler INSTANCE = new WorkersHandler();

    public byte getLumberMembers()
    {
        return this.lumberMembers;
    }
    public byte getQuarryMembers()
    {
        return this.quarryMembers;
    }

    public void setLumberMembers(byte count)
    {
        this.lumberMembers = count;
    }
    public void setQuarryMembers(byte count)
    {
        this.quarryMembers = count;
    }

    public void addLumberMember(byte count)
    {
        this.lumberMembers += count;
    }
    public void addQuarryMember(byte count)
    {
        this.quarryMembers += count;
    }

    public void removeLumberMember(byte count)
    {
        this.lumberMembers -= count;
    }
    public void removeQuarryMember(byte count)
    {
        this.quarryMembers -= count;
    }
}