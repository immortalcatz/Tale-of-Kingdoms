package kingdoms.server.handlers.resources;

public final class HunterHandler
{
    private boolean hunter = false;
    public static final HunterHandler INSTANCE = new HunterHandler();

    public boolean getHunter()
    {
        return this.hunter;
    }
    public void setHunter(boolean status)
    {
        this.hunter = status;
    }
}