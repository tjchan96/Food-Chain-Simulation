package entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import main.World;

/**
 *
 * @author tj
 */
public class Entity
{
    protected static int ENTITY_ID_COUNTER = 0;
    protected ArrayList<Point2D.Double> obstacles = new ArrayList<Point2D.Double>();
    protected ArrayList<Integer> foodTypes = new ArrayList<Integer>();
    protected World world = null;
    protected Entity target = null;
    protected Rectangle boundingBox = new Rectangle();
    protected Point2D.Double targetPoint = null;
    protected Point2D.Double previousPoint = new Point2D.Double();
    protected Point2D.Double point = new Point2D.Double();
    protected Point2D.Double screenPoint = new Point2D.Double();
    protected Color color1 = null;
    protected Color color2 = null;
    protected Color color3 = null;
    protected Color color4 = null;
    protected Color color5 = null;
    protected double moveAwayDirection = 0;
    protected double direction = 0;
    protected double speed = 0;
    protected double energy = 0;
    protected double energyUsageRate = 0;
    protected double energyObtainmentRate = 0;
    protected double energyNeededToDivide = 0;
    protected double startingEnergy = 0;
    protected double sightRange = 0;
    protected double digestionTime = 0;
    protected double digestionCounter = 0;
    protected int entityID = 0;
    protected int type = 0;
    protected int widthMax = 0;
    protected int heightMax = 0;
    protected int widthMin = 0;
    protected int heightMin = 0;
    protected int width = 0;
    protected int height = 0;
    protected int screenZoomedWidth = 0;
    protected int screenZoomedHeight = 0;
    protected boolean shouldDie = false;

    public Entity(World world, Point2D.Double spawn)
    {
        this.world = world;
        point.setLocation(spawn);
        previousPoint.setLocation(spawn);
        direction = Math.random() * 360.0;
        energy = startingEnergy;
        entityID = ENTITY_ID_COUNTER;
        ENTITY_ID_COUNTER++;
    }

    public void paintSelf(Graphics2D g)
    {
        AffineTransform nonTransformed = g.getTransform();
        
        screenPoint.setLocation(world.getScreenPoint(point));
        width = (int) ((widthMax - widthMin) * (energy / energyNeededToDivide) + widthMin);
        height = (int) ((heightMax - heightMin) * (energy / energyNeededToDivide) + heightMin);
        screenZoomedWidth = (int) world.getScreenZoomedDimensions(width);
        screenZoomedHeight = (int) world.getScreenZoomedDimensions(height);
        
        g.rotate(Math.toRadians(direction + 90), screenPoint.x, screenPoint.y);
        g.setColor(color1);
        g.fillOval((int) (screenPoint.x - screenZoomedWidth / 2), (int) (screenPoint.y - screenZoomedHeight / 2), screenZoomedWidth, screenZoomedHeight);
        boundingBox.setBounds((int) (screenPoint.x - screenZoomedWidth / 2), (int) (screenPoint.y - screenZoomedHeight / 2), screenZoomedWidth, screenZoomedHeight);

        g.setTransform(nonTransformed);
    }

    public void run()
    {
        energy -= energyUsageRate;
        chooseMove();
        move();
        if (shouldAct())
        {
            act();
        }
        divide();
        die();
    }

    protected void chooseMove()
    {
        target = null;
        for (int i = 0; i < foodTypes.size(); i++)
        {
            if (target == null)
            {
                target = world.getNearestOfTypeInRange(foodTypes.get(foodTypes.size() - i - 1), (int) sightRange, this);
            }
        }
        if (target != null && digestionCounter > digestionTime)
        {
            targetPoint = target.point;
            calculateDirectionToTarget();
        } else
        {
            direction += (int) Math.round(Math.random() * 40.0 - 20.0);
        }
        obstacles = world.checkIntersections(this);
    }

    protected void move()
    {
        for (int i = 0; i < obstacles.size(); i++)
        {
            moveAway(obstacles.get(i));
        }
        moveAwayFromEdges();
        obstacles.clear();
        point.x += speed * Math.cos(Math.toRadians(direction));
        point.y += speed * Math.sin(Math.toRadians(direction));
    }

    protected boolean shouldAct()
    {
        return true;
    }

    protected void act()
    {
        energy += energyObtainmentRate * (Math.random() + 0.5);
    }

    protected void divide()
    {
        if (energy > energyNeededToDivide)
        {
            energy = startingEnergy;
            world.addEntity(point, type);
        }
    }

    protected void die()
    {
        if (energy < 0)
        {
            shouldDie = true;
        }
    }

    private void calculateDirectionToTarget()
    {
        direction = Math.toDegrees(Math.atan2(targetPoint.y - point.y, targetPoint.x - point.x));
    }

    private void moveAway(Point2D.Double point)
    {
        if (point.equals(this.point))
        {
            moveAwayDirection = Math.random() * 360;
        } else
        {
            moveAwayDirection = Math.toDegrees(Math.atan2(point.y - this.point.y, point.x - this.point.x));
        }
        point.x += speed * Math.cos(Math.toRadians(moveAwayDirection));
        point.y += speed * Math.sin(Math.toRadians(moveAwayDirection));
    }

    private void moveAwayFromEdges()
    {
        if (world.getWaterBoundingBox().contains(screenPoint))
        {
            previousPoint.setLocation(point);
        } else
        {
            point.setLocation(previousPoint);
        }
    }

    public Rectangle getBoundingBox()
    {
        return boundingBox;
    }

    public int getEntityID()
    {
        return entityID;
    }

    public Double getPoint()
    {
        return point;
    }

    public Double getScreenPoint()
    {
        return screenPoint;
    }

    public double getEnergy()
    {
        return energy;
    }

    public int getType()
    {
        return type;
    }

    public boolean isShouldDie()
    {
        return shouldDie;
    }
}