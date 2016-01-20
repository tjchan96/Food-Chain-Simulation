package entities;

import java.awt.geom.Point2D;
import main.World;

/**
 *
 * @author TJ
 */
public class Heterotroph extends Entity
{
    public Heterotroph(World world, Point2D.Double spawn)
    {
        super(world, spawn);
    }

    @Override
    protected boolean shouldAct()
    {
        if (target != null && digestionCounter > digestionTime)
        {
            return target.boundingBox.intersects(boundingBox);
        }
        digestionCounter++;
        return false;
    }

    @Override
    protected void act()
    {
        if (target != null)
        {
            target.energy -= energyObtainmentRate * 2;
            energy += energyObtainmentRate;
            if (target.energy <= 0)
            {
                digestionCounter = 0;
            }
        }
    }
}