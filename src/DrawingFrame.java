import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.sun.jna.platform.WindowUtils;


public class DrawingFrame {
	
	Window window;
	BufferedImage image;
	Graphics2D graphics;
	ArrayList<Line> lines = new ArrayList<Line> ();

	@SuppressWarnings("serial")
	public DrawingFrame () {
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		image = new BufferedImage (size.width, size.height, BufferedImage.TYPE_INT_ARGB);
		graphics = image.createGraphics();
				
		window=new Window(null)
		{

		  @Override
		  public void paint(Graphics g)
		  {
		    g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
		  }
		  
		  @Override
		  public void update(Graphics g)
		  {
		    paint(g);
		  }
		};
	    
		window.addMouseListener(new MouseListener () {

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				clear ();
				window.setVisible(false);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
			
		});
		
		window.setAlwaysOnTop(true);
		window.setBounds(window.getGraphicsConfiguration().getBounds());
		window.setBackground(new Color(0, true));
		System.setProperty("sun.java2d.noddraw", "true");
        WindowUtils.setWindowTransparent(window, true);
        WindowUtils.setWindowAlpha(window, 1.0f);
		window.setVisible(true);
	}
	
	public void drawLine (Line line) {
		lines.add(line);
		graphics.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setColor(line.color);
		graphics.setStroke(line.stroke);
		graphics.drawLine(line.x, line.y, line.x2, line.y2);
		window.repaint();
	}

	public void clear() {
		lines = new ArrayList<Line> ();
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		image = new BufferedImage (size.width, size.height, BufferedImage.TYPE_INT_ARGB);
		graphics = image.createGraphics();
		window.repaint();
		//graphics.setBackground(new Color(255, 255, 255, 0));
		//graphics.clearRect(0, 0, size.width, size.height);
		//window.repaint();
	}
}
