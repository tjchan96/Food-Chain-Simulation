package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

/**
 *
 * @author TJ
 */
public class Viewer
{
    private World world = null;
    private Point2D.Double viewerPoint = new Point2D.Double();
    private Point2D.Double mousePoint = new Point2D.Double();
    private double zoomSpeed = 0.2;
    private double zoomIndex = 1;
    private double zoomIndexPrevious = 4;
    private int lastSelectedEntityID = 0;
    private int panSpeed = 10;
    private int mouseWheelRotation = 0;
    private boolean hasZoomed = false;
    private boolean mouseClicked = false;
    private boolean wKeyPressed = false;
    private boolean sKeyPressed = false;
    private boolean aKeyPressed = false;
    private boolean dKeyPressed = false;

    public Viewer(World world)
    {
        this.world = world;
        viewerPoint = new Point2D.Double(world.main.getSpawnPoint().x - Main.SCREEN_WIDTH / 2, world.main.getSpawnPoint().y - Main.SCREEN_HEIGHT / 2);
    }

    public void run()
    {
        if (wKeyPressed || mousePoint.y < 200)
        {
            viewerPoint.y -= panSpeed;
        }
        if (sKeyPressed || mousePoint.y > Main.SCREEN_HEIGHT - 200)
        {
            viewerPoint.y += panSpeed;
        }
        if (aKeyPressed || mousePoint.x < 200)
        {
            viewerPoint.x -= panSpeed;
        }
        if (dKeyPressed || mousePoint.x > Main.SCREEN_WIDTH - 200)
        {
            viewerPoint.x += panSpeed;
        }

        zoomIndexPrevious = zoomIndex;
//        Point2D.Double preZoomMWP = getMouseWorldPoint();
//        Point2D.Double postZoomMWP = null;

        if (mouseWheelRotation > 0)
        {
            mouseWheelRotation = 0;
            if (zoomIndex >= 0.4)
            {
                zoomIndex -= zoomSpeed;
//                hasZoomed = true;
            }
        }
        if (mouseWheelRotation < 0)
        {
            mouseWheelRotation = 0;
            if (zoomIndex <= 5.0)
            {
                zoomIndex += zoomSpeed;
//                hasZoomed = true;
            }
        }

//        viewerPoint.x = (world.main.getSpawnPoint().x - Main.SCREEN_WIDTH / 2) * (zoomIndex);
//        viewerPoint.y = (world.main.getSpawnPoint().y - Main.SCREEN_HEIGHT / 2) * (zoomIndex);
//        
//        if (hasZoomed)
//        {
//            hasZoomed = false;
//            postZoomMWP = getMouseWorldPoint();
//            viewerPoint.x += (postZoomMWP.x - preZoomMWP.x);
//            viewerPoint.y += (postZoomMWP.y - preZoomMWP.y);
//        }

    }

//    private Point2D.Double getMouseWorldPoint()
//    {
//        Point2D.Double mouseWorldPoint = new Point2D.Double(mousePoint.x + viewerPoint.x, mousePoint.y + viewerPoint.y);
//        mouseWorldPoint.x /= (zoomIndex / 5.0);
//        mouseWorldPoint.y /= (zoomIndex / 5.0);
//        return mouseWorldPoint;
//    }

    public void paintSelf(Graphics2D g)
    {
        for (int i = 0; i < world.getEntities().size(); i++)
        {
            if (world.getEntities().get(i).getBoundingBox().contains(mousePoint))
            {
                lastSelectedEntityID = world.getEntities().get(i).getEntityID();
            }
            if (mouseClicked)
            {
                lastSelectedEntityID = -1;
            }
            if (lastSelectedEntityID == world.getEntities().get(i).getEntityID())
            {
                g.setColor(Color.lightGray);
                g.fillRect((int) world.getEntities().get(i).getScreenPoint().x, (int) world.getEntities().get(i).getScreenPoint().y, 95, 14);
                g.setColor(Color.black);
                g.setFont(new Font("calibri", Font.PLAIN, 12));
                g.drawString("Energy: " + (int) world.getEntities().get(i).getEnergy(), (int) world.getEntities().get(i).getScreenPoint().x + 2, (int) world.getEntities().get(i).getScreenPoint().y + 11);
            }
        }

        g.setColor(Color.lightGray);
        g.fillRect(Main.SCREEN_WIDTH - 200, 100, 155, 14);
        g.fillRect(Main.SCREEN_WIDTH - 200, 200, 155, 14);
        g.fillRect(Main.SCREEN_WIDTH - 200, 300, 155, 14);
        g.fillRect(Main.SCREEN_WIDTH - 200, 400, 155, 14);
        g.setColor(Color.black);
        g.setFont(new Font("calibri", Font.PLAIN, 12));
        g.drawString("Total Phytoplankton: " + (int) world.getPhytoPlanktonCount(), Main.SCREEN_WIDTH - 198, 111);
        g.drawString("Total Zooplankton: " + (int) world.getZooPlanktonCount(), Main.SCREEN_WIDTH - 198, 211);
        g.drawString("Total Small Fish: " + (int) world.getSmallFishCount(), Main.SCREEN_WIDTH - 198, 311);
        g.drawString("Total Large Fish: " + (int) world.getLargeFishCount(), Main.SCREEN_WIDTH - 198, 411);
    }

    public Point2D.Double getViewerPoint()
    {
        return viewerPoint;
    }

    public double getZoomIndex()
    {
        return zoomIndex;
    }

    public Double getMousePoint()
    {
        return mousePoint;
    }

    public void setZoomIndex(double zoomIndex)
    {
        this.zoomIndex = zoomIndex;
    }

    public double getZoomIndexPrevious()
    {
        return zoomIndexPrevious;
    }

    public void setZoomIndexPrevious(double zoomIndexPrevious)
    {
        this.zoomIndexPrevious = zoomIndexPrevious;
    }

    public void setMouseClicked(boolean mouseClicked)
    {
        this.mouseClicked = mouseClicked;
    }

    public void setMousePoint(Point mousePoint)
    {
        this.mousePoint.setLocation(mousePoint);
    }

    public void setMouseWheelRotation(int mouseWheelRotation)
    {
        this.mouseWheelRotation = mouseWheelRotation;
    }

    public void setaKeyPressed(boolean aKeyPressed)
    {
        this.aKeyPressed = aKeyPressed;
    }

    public void setdKeyPressed(boolean dKeyPressed)
    {
        this.dKeyPressed = dKeyPressed;
    }

    public void setsKeyPressed(boolean sKeyPressed)
    {
        this.sKeyPressed = sKeyPressed;
    }

    public void setwKeyPressed(boolean wKeyPressed)
    {
        this.wKeyPressed = wKeyPressed;
    }
}