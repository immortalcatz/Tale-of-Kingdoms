package kingdoms.server.handlers;

import java.util.ArrayList;
import java.util.List;

public final class Buildings
{
    private List<Boolean> buildingList = new ArrayList<>();
    public static final Buildings INSTANCE = new Buildings();
    public boolean
            createGuild, kingdomCreated, smallhouse1, smallhouse2, largehouse1, well, itemshop, stockmarket, isTier2,
            smallhouse3, smallhouse4, largehouse2, builderhouse, barracks, foodshop, blockshop, isTier3, smallhouse5,
            smallhouse6, smallhouse7, largehouse3, tavern, chapel, library, magehall, isTier4, bridge, castle, colloseum,
            easternTower, fishHut, lightHouse, mill, observerPost, smallhouse8, smallhouse9, smallhouse10, smallhouse11,
            largehouse4, northernTower1, northernTower2, stables, zeppelin;

    public void registerBuildings()
    {
        clear();
        addBuilding(createGuild);
        addBuilding(kingdomCreated);
        addBuilding(smallhouse1);
        addBuilding(smallhouse2);
        addBuilding(largehouse1);
        addBuilding(well);
        addBuilding(itemshop);
        addBuilding(stockmarket);
        addBuilding(isTier2);
        addBuilding(smallhouse3);
        addBuilding(largehouse2);
        addBuilding(builderhouse);
        addBuilding(smallhouse4);
        addBuilding(barracks);
        addBuilding(foodshop);
        addBuilding(blockshop);
        addBuilding(isTier3);
        addBuilding(tavern);
        addBuilding(smallhouse5);
        addBuilding(smallhouse6);
        addBuilding(smallhouse7);
        addBuilding(chapel);
        addBuilding(largehouse3);
        addBuilding(library);
        addBuilding(magehall);
        addBuilding(isTier4);
        addBuilding(bridge);
        addBuilding(castle);
        addBuilding(colloseum);
        addBuilding(easternTower);
        addBuilding(fishHut);
        addBuilding(largehouse4);
        addBuilding(lightHouse);
        addBuilding(mill);
        addBuilding(northernTower1);
        addBuilding(northernTower2);
        addBuilding(observerPost);
        addBuilding(smallhouse8);
        addBuilding(smallhouse9);
        addBuilding(smallhouse10);
        addBuilding(smallhouse11);
        addBuilding(stables);
        addBuilding(zeppelin);
        addBuilding(stables);
        addBuilding(stables);
    }

    public void addBuilding(boolean name)
    {
        buildingList.add(name);
    }

    public void setBuildingTrue(int name)
    {
        buildingList.set(name, true);
    }

    public void setBuildings(List<Boolean> buildings)
    {
        this.buildingList.clear();
        this.buildingList = buildings;
    }

    public boolean getBuilding(int name)
    {
        return getBuildingList().get(name);
    }

    public List<Boolean> getBuildingList()
    {
        return buildingList;
    }

    public void clear()
    {
        buildingList.clear();
    }

    public void setBuildingList(final List<Boolean> buildingList)
    {
        this.buildingList = buildingList;
    }
}