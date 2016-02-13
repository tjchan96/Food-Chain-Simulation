package main;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.*;
import java.awt.image.BufferedImage;

/**
 *
 * @author tj
 */
public class Painter extends Canvas implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener
{
    public Main main = null;
    private BufferedImage offscreen = null;

    public Painter(Main main)
    {
        this.main = main;
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
        this.addKeyListener(this);
    }

    @Override
    public void paint(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        main.getWorld().paintSelf(g2);
    }

    @Override
    public void update(Graphics g)
    {
        if (main.isOnWindows())
        {
            Graphics offgc;
            Rectangle box = g.getClipBounds();

            offscreen = new BufferedImage(box.width, box.height, BufferedImage.TYPE_INT_RGB);
            offgc = offscreen.getGraphics();
            offgc.setColor(getBackground());
            offgc.fillRect(0, 0, box.width, box.height);
            offgc.setColor(getForeground());
            offgc.translate(-box.x, -box.y);
            paint(offgc);
            g.drawImage(offscreen, box.x, box.y, this);
        } else
        {
            super.update(g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        main.getWorld().getViewer().setMouseClicked(true);
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        main.getWorld().getViewer().setMouseClicked(false);
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
        main.getWorld().getViewer().setMousePoint(e.getPoint());
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        main.getWorld().getViewer().setMousePoint(e.getPoint());
    }
    
    @Override
    public void mouseWheelMoved(MouseWheelEvent e)
    {
        if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL)
        {
            main.getWorld().getViewer().setMouseWheelRotation(e.getWheelRotation());
        }
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_W)
        {
            main.getWorld().getViewer().setwKeyPressed(true);
        }
        if (e.getKeyCode() == KeyEvent.VK_S)
        {
            main.getWorld().getViewer().setsKeyPressed(true);
        }
        if (e.getKeyCode() == KeyEvent.VK_A)
        {
            main.getWorld().getViewer().setaKeyPressed(true);
        }
        if (e.getKeyCode() == KeyEvent.VK_D)
        {
            main.getWorld().getViewer().setdKeyPressed(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_W)
        {
            main.getWorld().getViewer().setwKeyPressed(false);
        }
        if (e.getKeyCode() == KeyEvent.VK_S)
        {
            main.getWorld().getViewer().setsKeyPressed(false);
        }
        if (e.getKeyCode() == KeyEvent.VK_A)
        {
            main.getWorld().getViewer().setaKeyPressed(false);
        }
        if (e.getKeyCode() == KeyEvent.VK_D)
        {
            main.getWorld().getViewer().setdKeyPressed(false);
        }
    }
}
