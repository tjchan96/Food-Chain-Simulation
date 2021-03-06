package entities;

import java.awt.geom.Point2D;
import main.World;

/**
 *
 * @author tj
 */
public class EggHeterotroph extends Heterotroph
{
    protected int layEggCounter = 0;
    protected int layEggDelayTime = 0;
    
    public EggHeterotroph(World world, Point2D.Double spawn)
    {
        super(world, spawn);
    }

    @Override
    protected boolean shouldAct()
    {
        if (energy > energyNeededToDivide)
        {
            return false;
        }
        else
        {
            return super.shouldAct();
        }
    }

    @Override
    protected void divide()
    {
        if (energy > energyNeededToDivide && layEggCounter > layEggDelayTime)
        {
            layEggCounter = 0;
            world.addEntity(new Egg(world, point, width, type, color1));
        } else
        {
            layEggCounter++;
        }
    }
}
