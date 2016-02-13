package entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import main.World;

/**
 *
 * @author TJ
 */
public class Egg extends Entity
{
    protected int typeOriginal = 0;
    
    public Egg(World world, Point2D.Double spawn, int radius, int typeOriginal, Color color1)
    {
        super(world, spawn);
        type = World.TYPE_EGG;
        this.color1 = new Color(141, 179, 198);
        color2 = color1;
        widthMax = radius;
        heightMax = radius;
        widthMin = radius;
        heightMin = radius;
        speed = 0;
        sightRange = 0;
        energyUsageRate = 0;
        energyObtainmentRate = 5;
        energyNeededToDivide = 1000;
        digestionTime = 0;
        startingEnergy = 500;
        energy = startingEnergy;
        this.typeOriginal = typeOriginal;
    }
    
    @Override
    public void paintSelf(Graphics2D g)
    {
        super.paintSelf(g);
        
        AffineTransform nonTransformed = g.getTransform();

        g.rotate(Math.toRadians(direction + 90), screenPoint.x, screenPoint.y);
        g.setColor(color2);
        g.fillOval((int) (screenPoint.x - screenZoomedWidth / 4), (int) (screenPoint.y - screenZoomedHeight / 4), screenZoomedWidth / 2, screenZoomedHeight / 2);

        g.setTransform(nonTransformed);
    }
    
    @Override
    protected void divide()
    {
        if (energy > energyNeededToDivide)
        {
            shouldDie = true;
            world.addEntity(point, typeOriginal);
        }
    }
}