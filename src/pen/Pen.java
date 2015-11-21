package pen;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;
import java.util.ArrayList;

public class Pen {
	public String name;
	public Stroke stroke;
	public Color color;
	public float x = -1;
	public float y = -1;
	public float last_x = -1;
	public float last_y = -1;
	
	public DetectorModel model;
	
	public static ArrayList<Pen> pens = new ArrayList<Pen> ();
	
	public Pen (String name, Stroke stroke, Color color, DetectorModel model) {
		this.name = name;
		this.stroke = stroke;
		this.color = color;
		this.model = model;
	}
	
	public static void registerPens () {
		pens.add(new Pen ("Pink Highlighter", new BasicStroke (5f), new Color (1, 0.5f, 0.5f, 0.5f), 
				new DetectorModel (1, 1, new Color (200, 0, 0),  new Color (255, 120, 200))));
		
		pens.add(new Pen ("Yellow Highlighter", new BasicStroke (5f), new Color (245f / 255f, 245f / 255f, 100f / 255f, 0.5f), 
				new DetectorModel (1, 1, new Color (200, 200, 70),  new Color (230, 230, 120))));
	}
}
