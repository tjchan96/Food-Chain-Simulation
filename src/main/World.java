package main;

import entities.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author tj
 */
public class World
{
    public final static int TYPE_PHYTOPLANKTON = 0;
    public final static int TYPE_ZOOPLANKTON = 1;
    public final static int TYPE_SMALL_FISHY = 2;
    public final static int TYPE_LARGE_FISHY = 3;
    public final static int TYPE_EGG = 4;
    public final static int SIZE_FACTOR = 500;
    public Main main = null;
    private ArrayList<Entity> entitiesToSpawn = new ArrayList<Entity>();
    private ArrayList<Entity> entities = new ArrayList<Entity>();
    private int[] xWaterBoundingBox = null;
    private int[] yWaterBoundingBox = null;
    private Viewer viewer = null;
    private Polygon waterBoundingBox = null;
    private Color waterColor = new Color(156, 226, 255);
    private Color sandColor = new Color(215, 215, 144);
    private int phytoPlanktonCount = 0;
    private int zooPlanktonCount = 0;
    private int smallFishCount = 0;
    private int largeFishCount = 0;

    public World(Main main)
    {
        this.main = main;
        viewer = new Viewer(this);
        moveBackground();
    }

    public void paintSelf(Graphics2D g)
    {
        g.setColor(sandColor);
        g.fillRect(0, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
        g.setColor(waterColor);
        g.fill(waterBoundingBox);
        for (int i = 0; i < entities.size(); i++)
        {
            entities.get(i).paintSelf(g);
        }
        viewer.paintSelf(g);
    }

    public void run()
    {
        moveBackground();
        for (int i = 0; i < entitiesToSpawn.size(); i++)
        {
            entities.add(entitiesToSpawn.get(i));
        }
        entitiesToSpawn.clear();
        for (int i = 0; i < entities.size(); i++)
        {
            entities.get(i).run();
        }
        destroyEntities();
        viewer.run();
    }

    private void moveBackground()
    {
        xWaterBoundingBox = new int[8];

        xWaterBoundingBox[0] = 1;
        xWaterBoundingBox[1] = 2;
        xWaterBoundingBox[2] = 4;
        xWaterBoundingBox[3] = 6;
        xWaterBoundingBox[4] = 6;
        xWaterBoundingBox[5] = 5;
        xWaterBoundingBox[6] = 3;
        xWaterBoundingBox[7] = 1;

        for (int i = 0; i < xWaterBoundingBox.length; i++)
        {
            xWaterBoundingBox[i] = (int) (((xWaterBoundingBox[i] * SIZE_FACTOR) + Main.SCREEN_WIDTH - viewer.getViewerPoint().x));
            xWaterBoundingBox[i] -= Main.SCREEN_WIDTH / 2;
            xWaterBoundingBox[i] *= (viewer.getZoomIndex());
            xWaterBoundingBox[i] += Main.SCREEN_WIDTH / 2;
        }

        yWaterBoundingBox = new int[8];

        yWaterBoundingBox[0] = 2;
        yWaterBoundingBox[1] = 1;
        yWaterBoundingBox[2] = 1;
        yWaterBoundingBox[3] = 2;
        yWaterBoundingBox[4] = 3;
        yWaterBoundingBox[5] = 5;
        yWaterBoundingBox[6] = 5;
        yWaterBoundingBox[7] = 4;

        for (int i = 0; i < yWaterBoundingBox.length; i++)
        {
            yWaterBoundingBox[i] = (int) (((yWaterBoundingBox[i] * SIZE_FACTOR) + Main.SCREEN_HEIGHT - viewer.getViewerPoint().y));
            yWaterBoundingBox[i] -= Main.SCREEN_HEIGHT / 2;
            yWaterBoundingBox[i] *= (viewer.getZoomIndex());
            yWaterBoundingBox[i] += Main.SCREEN_HEIGHT / 2;
        }

        waterBoundingBox = new Polygon(xWaterBoundingBox, yWaterBoundingBox, xWaterBoundingBox.length);

//        screenPoint.setLocation(point));
//        width = (int) ((widthMax - widthMin) * (energy / energyNeededToDivide) + widthMin);
//        height = (int) ((heightMax - heightMin) * (energy / energyNeededToDivide) + heightMin);
//        screenZoomedWidth = (int) world.getScreenZoomedDimensions(width);
//        screenZoomedHeight = (int) world.getScreenZoomedDimensions(height);
//        
//        previousBackgroundScreenPoint.setLocation(backgroundScreenPoint);
//        backgroundScreenPoint.setLocation(getScreenPoint(backgroundPoint));
//        double changeInX = backgroundScreenPoint.x - previousBackgroundScreenPoint.x;
//        double changeInY = backgroundScreenPoint.y - previousBackgroundScreenPoint.y;
//        double changeInZoom = viewer.getZoomIndex() - viewer.getZoomIndexPrevious();
//        for (int i = 0; i < xWaterBoundingBox.length; i++)
//        {
//            xWaterBoundingBox[i] += changeInX;
//            yWaterBoundingBox[i] += changeInY;
//            if (changeInZoom != 0)
//            {
//                xWaterBoundingBox[i] *= viewer.getZoomIndex() / 5.0;
//                yWaterBoundingBox[i] *= viewer.getZoomIndex() / 5.0;
//            }
//        }
//        waterBoundingBox = new Polygon(xWaterBoundingBox, yWaterBoundingBox, xWaterBoundingBox.length);
    }

    private void destroyEntities()
    {
        Iterator<Entity> iter = entities.iterator();
        while (iter.hasNext())
        {
            Entity next = iter.next();
            if (next.isShouldDie())
            {
                if (next.getType() == TYPE_PHYTOPLANKTON)
                {
                    phytoPlanktonCount--;
                }
                if (next.getType() == TYPE_ZOOPLANKTON)
                {
                    zooPlanktonCount--;
                }
                if (next.getType() == TYPE_SMALL_FISHY)
                {
                    smallFishCount--;
                }
                if (next.getType() == TYPE_LARGE_FISHY)
                {
                    largeFishCount--;
                }
                iter.remove();
            }
        }
    }

    public ArrayList<Point2D.Double> checkIntersections(Entity entity)
    {
        ArrayList<Point2D.Double> obstacles = new ArrayList<Point2D.Double>();
        for (int i = 0; i < entities.size(); i++)
        {
            if (!entities.get(i).equals(entity) && entities.get(i).getBoundingBox().intersects(entity.getBoundingBox()))
            {
                obstacles.add(entities.get(i).getPoint());
            }
        }
        return obstacles;
    }

    public void addEntity(Entity entity)
    {
        entitiesToSpawn.add(entity);
        if (entity.getType() == TYPE_PHYTOPLANKTON)
        {
            phytoPlanktonCount++;
        }
        if (entity.getType() == TYPE_ZOOPLANKTON)
        {
            zooPlanktonCount++;
        }
        if (entity.getType() == TYPE_SMALL_FISHY)
        {
            smallFishCount++;
        }
        if (entity.getType() == TYPE_LARGE_FISHY)
        {
            largeFishCount++;
        }
    }

    public void addEntity(Point2D.Double point, int type)
    {
        if (type == TYPE_PHYTOPLANKTON)
        {
            entitiesToSpawn.add(new PhytoPlankton(this, point));
            phytoPlanktonCount++;
        }
        if (type == TYPE_ZOOPLANKTON)
        {
            entitiesToSpawn.add(new ZooPlankton(this, point));
            zooPlanktonCount++;
        }
        if (type == TYPE_SMALL_FISHY)
        {
            entitiesToSpawn.add(new SmallFish(this, point));
            smallFishCount++;
        }
        if (type == TYPE_LARGE_FISHY)
        {
            entitiesToSpawn.add(new LargeFish(this, point));
            largeFishCount++;
        }
    }

    public double getPhotosyntheticRate()
    {
        if (phytoPlanktonCount < 250.0)
        {
            return (1.0);
        } else
        {
            return ((500.0 - phytoPlanktonCount) / 250.0);
        }
    }

    public Entity getNearestOfTypeInRange(int type, int range, Entity entity)
    {
        int minDistance = range;
        Entity minDistanceEntity = null;

        for (int i = 0; i < entities.size(); i++)
        {
            if (!entities.get(i).equals(entity) && entities.get(i).getType() == type && calculateDistance(entities.get(i).getPoint(), entity.getPoint()) < minDistance)
            {
                minDistance = (int) calculateDistance(entities.get(i).getPoint(), entity.getPoint());
                minDistanceEntity = entities.get(i);
            }
        }

        return minDistanceEntity;
    }

    private double calculateDistance(Point2D.Double point1, Point2D.Double point2)
    {
        double xDistance = point1.x - point2.x;
        double yDistance = point1.y - point2.y;
        double distance = Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));
        return distance;
    }

    public Point2D.Double getScreenPoint(Point2D.Double point)
    {
        Point2D.Double screenPoint = new Point2D.Double(point.x - viewer.getViewerPoint().x, point.y - viewer.getViewerPoint().y);
        screenPoint.x -= Main.SCREEN_WIDTH / 2;
        screenPoint.y -= Main.SCREEN_HEIGHT / 2;
        screenPoint.x *= (viewer.getZoomIndex());
        screenPoint.y *= (viewer.getZoomIndex());
        screenPoint.x += Main.SCREEN_WIDTH / 2;
        screenPoint.y += Main.SCREEN_HEIGHT / 2;
        return screenPoint;
    }

    public double getScreenZoomedDimensions(double dimension)
    {
        double screenZoomedDimension = dimension * (viewer.getZoomIndex());
        return screenZoomedDimension;
    }

    public ArrayList<Entity> getEntities()
    {
        return entities;
    }

    public Viewer getViewer()
    {
        return viewer;
    }

    public Polygon getWaterBoundingBox()
    {
        return waterBoundingBox;
    }

    public int getLargeFishCount()
    {
        return largeFishCount;
    }

    public int getPhytoPlanktonCount()
    {
        return phytoPlanktonCount;
    }

    public int getSmallFishCount()
    {
        return smallFishCount;
    }

    public int getZooPlanktonCount()
    {
        return zooPlanktonCount;
    }
}