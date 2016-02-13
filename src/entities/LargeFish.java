package entities;

import java.awt.Color;
import java.awt.geom.Point2D;
import main.World;

/**
 *
 * @author tj
 */
public class LargeFish extends EggHeterotroph
{
    public LargeFish(World world, Point2D.Double spawn)
    {
        super(world, spawn);
        type = World.TYPE_LARGE_FISHY;
        color1 = new Color(9, 116, 169);
        widthMax = 12;
        heightMax = 24;
        widthMin = 7;
        heightMin = 14;
        speed = 1;
        sightRange = 400;
        energyUsageRate = 2;
        energyObtainmentRate = 2000;
        energyNeededToDivide = 8000;
        digestionTime = 100;
        startingEnergy = 2000;
        energy = startingEnergy;
        foodTypes.add(World.TYPE_SMALL_FISHY);
        layEggDelayTime = 1000;
    }
}
