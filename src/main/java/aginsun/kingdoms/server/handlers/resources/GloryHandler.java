package aginsun.kingdoms.server.handlers.resources;

public final class GloryHandler
{
    private int glory;
    public static final GloryHandler INSTANCE = new GloryHandler();

    public int getGlory()
    {
        return glory;
    }

    public void setGlory(int glory)
    {
        this.glory = glory;
    }

    public void addGlory(int glory)
    {
        this.glory += glory;
    }

    public void decreaseGlory(int glory)
    {
        this.glory -= glory;
    }
}