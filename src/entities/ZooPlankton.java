package entities;

import java.awt.Color;
import java.awt.geom.Point2D;
import main.World;

/**
 *
 * @author tj
 */
public class ZooPlankton extends Heterotroph
{
    public ZooPlankton(World world, Point2D.Double spawn)
    {
        super(world, spawn);
        type = World.TYPE_ZOOPLANKTON;
        color1 = new Color(108, 181, 111);
        widthMax = 6;
        heightMax = 12;
        widthMin = 4;
        heightMin = 8;
        speed = 0.7;
        sightRange = 100;
        energyUsageRate = 0.1;
        energyObtainmentRate = 50;
        energyNeededToDivide = 500;
        digestionTime = 50;
        startingEnergy = 200;
        energy = startingEnergy;
        foodTypes.add(World.TYPE_PHYTOPLANKTON);
    }
}