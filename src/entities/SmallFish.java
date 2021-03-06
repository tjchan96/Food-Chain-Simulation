package entities;

import java.awt.Color;
import java.awt.geom.Point2D;
import main.World;

/**
 *
 * @author tj
 */
public class SmallFish extends EggHeterotroph
{
    public SmallFish(World world, Point2D.Double spawn)
    {
        super(world, spawn);
        type = World.TYPE_SMALL_FISHY;
        color1 = new Color(105, 141, 159);
        widthMax = 8;
        heightMax = 16;
        widthMin = 5;
        heightMin = 10;
        speed = 0.8;
        sightRange = 200;
        energyUsageRate = 1;
        energyObtainmentRate = 800;
        energyNeededToDivide = 2000;
        digestionTime = 100;
        startingEnergy = 1000;
        energy = startingEnergy;
        foodTypes.add(World.TYPE_ZOOPLANKTON);
        foodTypes.add(World.TYPE_PHYTOPLANKTON);
        layEggDelayTime = 500;
    }
}