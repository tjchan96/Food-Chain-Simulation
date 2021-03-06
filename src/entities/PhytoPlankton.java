package entities;

import java.awt.Color;
import java.awt.geom.Point2D;
import main.World;

/**
 *
 * @author tj
 */
public class PhytoPlankton extends Autotroph
{
    public PhytoPlankton(World world, Point2D.Double spawn)
    {
        super(world, spawn);
        type = World.TYPE_PHYTOPLANKTON;
        color1 = new Color(139, 205, 118);
        widthMax = 4;
        heightMax = 8;
        widthMin = 3;
        heightMin = 6;
        speed = 0.5;
        sightRange = 50;
        energyUsageRate = 0.1;
        energyObtainmentRate = world.getPhotosyntheticRate();
        energyNeededToDivide = 100;
        digestionTime = 1;
        startingEnergy = 10;
        energy = startingEnergy;
    }
}