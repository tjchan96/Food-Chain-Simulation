package entities;

import java.awt.geom.Point2D;
import main.World;

/**
 *
 * @author TJ
 */
public class Autotroph extends Entity
{
    public Autotroph(World world, Point2D.Double spawn)
    {
        super(world, spawn);
    }

    @Override
    public void run()
    {
        energyObtainmentRate = world.getPhotosyntheticRate() * (Math.random() + 1);
        super.run();
    }
}