import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.apple.eawt.AppEvent.AppForegroundEvent;
import com.apple.eawt.AppEvent.AppReOpenedEvent;
import com.apple.eawt.AppForegroundListener;
import com.apple.eawt.AppReOpenedListener;
import com.apple.eawt.Application;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamImageTransformer;
import com.github.sarxos.webcam.WebcamPanel;

import pen.Pen;


public class AirPen implements WebcamImageTransformer {

	private static final JHFilterBlob GRAY = new JHFilterBlob();
	private DrawingFrame drawing;
	private long lastFrame = System.currentTimeMillis();
	
	public static final Dimension Resolution = new Dimension (176, 144);
	private static final float sensitivity = 0.5f;

	public AirPen() {
		
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		
		Pen.registerPens();
		
		Webcam webcam = Webcam.getDefault();
		//Dimension dim = WebcamResolution.VGA.getSize();
		//[176x144] [320x240] [640x480]
		webcam.setViewSize(Resolution);
		webcam.setImageTransformer(this);
		webcam.open();

		JFrame window = new JFrame("Live View");

		WebcamPanel panel = new WebcamPanel(webcam) {
			private static final long serialVersionUID = 1L;
			
			@Override
			  public void paint(Graphics g)
			  {
			    super.paint(g);
			    
			    if (0 != -1) {
			    	g.setColor(new Color (.1f, .1f, .1f));
			    	g.fillOval(Resolution.width - 21, Resolution.height - 21, 12, 12);
			    	g.setColor(new Color (1, .5f, .5f));
			    	g.fillOval(Resolution.width - 20, Resolution.height - 20, 10, 10);
			    }
			  }
		};
		panel.setFPSDisplayed(true);
		panel.setFillArea(true);

		window.add(panel);
		window.pack();
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//JFrame window2 = new JFrame("Drawing");

		//drawing = new ImagePanel (new BufferedImage (316, 315, BufferedImage.TYPE_INT_RGB));
		//drawing.setPreferredSize(new Dimension(315, 315));
		
		//window2.add(drawing);
		//window2.pack();
		//window2.setVisible(true);
		//window2.setLocationRelativeTo(null);
		//window2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		drawing = new DrawingFrame ();
		
		Application app = Application.getApplication();
	    app.addAppEventListener(new AppForegroundListener() {

	        @Override
	        public void appMovedToBackground(AppForegroundEvent arg0)
	        {
	        	drawing.window.setVisible(false);
	        }

	        @Override
	        public void appRaisedToForeground(AppForegroundEvent arg0)
	        {
	            drawing.window.setVisible(true);
	        }

	    });

	    app.addAppEventListener(new AppReOpenedListener() {
	        @Override
	        public void appReOpened(AppReOpenedEvent arg0)
	        {
	            drawing.window.setVisible(true);
	        }
	    });
	    
	    JMenuBar menuBar = new JMenuBar();
	    
	    JMenu menu = new JMenu("File");
	    menu.setMnemonic(KeyEvent.VK_F);
	    menu.getAccessibleContext().setAccessibleDescription("Show file options");
	    menuBar.add(menu);
	    
	    JMenuItem menuItem = new JMenuItem("Clear Drawing");
	    menuItem.setMnemonic(KeyEvent.VK_C);
	    menu.add(menuItem);
	    menuItem.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                drawing.clear ();
            }
        } );
	    
	    window.setJMenuBar(menuBar);
	}

	
	
	@Override
	public BufferedImage transform(BufferedImage image) {
		if (System.currentTimeMillis() >= lastFrame + 50) {
			for (int i=0; i<Pen.pens.size(); i++) {
				Pen p = Pen.pens.get(i);
				
				if (p.last_x != -1 && p.x != -1) {
					lastFrame = System.currentTimeMillis();
					drawing.drawLine(new Line ((int)Math.floor(p.last_x * (float)drawing.window.getWidth()), (int)Math.floor(p.last_y * (float)drawing.window.getHeight()), (int)Math.floor(p.x * (float)drawing.window.getWidth()), (int)Math.floor(p.y * (float)drawing.window.getHeight()), p.stroke, p.color));
				}
				
				p.last_x = p.x;
				p.last_y = p.y;
			}
		}
		
		for (int i=0; i<Pen.pens.size(); i++) {
			Pen p = Pen.pens.get(i);
			p.x = -1;
			p.y = -1;
		}
		
		return GRAY.filter(image, null);
	}

	public static void main(String[] args) {
		new AirPen();
	}

}
