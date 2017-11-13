package aginsun.kingdoms.server.handlers.resources;

public final class GuildHandler
{
    private int townX, townY, townZ;
    private boolean guildFightEnded = true, guildFightStarted = false;
    public static final GuildHandler INSTANCE = new GuildHandler();

    public int getTownX()
    {
        return townX;
    }
    public int getTownY()
    {
        return townY;
    }
    public int getTownZ()
    {
        return townZ;
    }

    public boolean getGuildFightEnded()
    {
        return guildFightEnded;
    }
    public boolean getGuildFightStarted()
    {
        return guildFightStarted;
    }

    public void setTownX(int townX)
    {
        this.townX = townX;
    }
    public void setTownY(int townY)
    {
        this.townY = townY;
    }
    public void setTownZ(int townZ)
    {
        this.townZ = townZ;
    }

    public void setGuildFightEnded(boolean guildFightEnded)
    {
        this.guildFightEnded = guildFightEnded;
    }
    public void setGuildFightStarted(boolean guildFightStarted)
    {
        this.guildFightStarted = guildFightStarted;
    }
}