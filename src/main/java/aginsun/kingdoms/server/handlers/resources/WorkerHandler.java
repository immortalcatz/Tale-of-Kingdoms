package aginsun.kingdoms.server.handlers.resources;

public final class WorkerHandler
{
    private int lumberMembers, quarryMembers;
    private static final WorkerHandler instance = new WorkerHandler();

    public static WorkerHandler getInstance()
    {
        return instance;
    }

    public int getLumberMembers()
    {
        return this.lumberMembers;
    }

    public void addLumberMember()
    {
        ++this.lumberMembers;
    }

    public void removeLumberMember()
    {
        --this.lumberMembers;
    }

    public void setLumberMembers(int i)
    {
        this.lumberMembers = i;
    }

    public int getQuarryMembers()
    {
        return this.quarryMembers;
    }

    public void addQuarryMember()
    {
        ++this.quarryMembers;
    }

    public void removeQuarryMember()
    {
        --this.quarryMembers;
    }

    public void setQuarryMembers(int i)
    {
        this.quarryMembers = i;
    }
}