package main;

import entities.LargeFish;
import entities.PhytoPlankton;
import entities.SmallFish;
import entities.ZooPlankton;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 *
 * @author tj
 */
public class Main implements ActionListener
{
    public static final int FPS = 50;
    public static final int SPS = 50;
    public static final int SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    public static final int SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
    private static Main main = null;
    private Timer fpsTimer = new Timer(1000 / FPS, this);
    private Timer spsTimer = new Timer(1000 / SPS, this);
    private World world = null;
    private Painter painter = null;
    private JFrame screen = new JFrame("Pond Simulation");
    private Point2D.Double spawnPoint = new Point2D.Double(7 * World.SIZE_FACTOR, 5 * World.SIZE_FACTOR);
    private boolean onWindows = true;

    public static void main(String[] args)
    {
        main = new Main();
    }

    public Main()
    {
        world = new World(this);
        painter = new Painter(this);
        
        screen.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        screen.add(painter);
        screen.setVisible(true);
        
        String os = System.getProperty("os.name");
        onWindows = os.startsWith("Windows");
        
        fpsTimer.start();
        spsTimer.start();
        
        for (int i = 0; i < 200; i++)
        {
            world.addEntity(new PhytoPlankton(world, spawnPoint));
        }
        for (int i = 0; i < 100; i++)
        {
            world.addEntity(new ZooPlankton(world, spawnPoint));
        }
        for (int i = 0; i < 10; i++)
        {
            world.addEntity(new SmallFish(world, spawnPoint));
        }
        for (int i = 0; i < 1; i++)
        {
            world.addEntity(new LargeFish(world, spawnPoint));
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae)
    {
        if (ae.getSource().equals(fpsTimer))
        {
            painter.repaint();
        }
        if (ae.getSource().equals(spsTimer))
        {
            world.run();
        }
    }

    public World getWorld()
    {
        return world;
    }

    public boolean isOnWindows()
    {
        return onWindows;
    }

    public Double getSpawnPoint()
    {
        return spawnPoint;
    }
}