package aginsun.kingdoms.server.handlers.resources;

public final class ResourcesHandler
{
    private int woodPool, cobblePool, woodResource, cobbleResource;
    public static final ResourcesHandler INSTANCE = new ResourcesHandler();

    public int getWoodPool()
    {
        return this.woodPool;
    }
    public int getCobblePool()
    {
        return this.cobblePool;
    }

    public void setWoodPool(int count)
    {
        this.woodPool = count;
    }
    public void setCobblePool(int count)
    {
        this.cobblePool = count;
    }

    public void addWoodPool(int count)
    {
        this.woodPool += count;
    }
    public void addCobblePool(int count)
    {
        this.cobblePool += count;
    }

    public void decreaseWoodPool(int count)
    {
        if (woodPool > 0)
            this.woodPool -= count;
    }

    public void decreaseCobblePool(int count)
    {
        if (cobblePool > 0)
            this.cobblePool -= count;
    }

    public void setwoodResource(int count)
    {
        this.woodResource = count;
    }
    public void setcobbleResource(int count)
    {
        this.cobbleResource = count;
    }

    public int getwoodResource()
    {
        return this.woodResource;
    }
    public int getcobbleResource()
    {
        return this.cobbleResource;
    }

    public void addwoodResource(int count)
    {
        if (woodResource <= 320)
            this.woodResource += count;
    }

    public void addcobbleResource(int count)
    {
        if (cobbleResource <= 320)
            this.cobbleResource += count;
    }

    public void decreasewoodResource(int count)
    {
        if (woodResource > 0)
            this.woodResource -= count;
    }

    public void decreasecobbleResource(int count)
    {
        if (cobbleResource > 0)
            this.cobbleResource -= count;
    }
}