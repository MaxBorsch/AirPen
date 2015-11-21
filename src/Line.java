import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;

public class Line {
	public int x = 0;
	public int y = 0;
	public int x2 = 0;
	public int y2 = 0;
	public Stroke stroke;
	public Color color;
	
	public Line (int x, int y, int x2, int y2, Stroke stroke, Color color) {
		this.x = x;
		this.y = y;
		this.x2 = x2;
		this.y2 = y2;
		this.stroke = stroke;
		this.color = color;
	}
	
	public Line (int x, int y, int x2, int y2) {
		this.x = x;
		this.y = y;
		this.x2 = x2;
		this.y2 = y2;
		this.stroke = new BasicStroke (5f);
		this.color = new Color (1, .5f, .5f);
	}
}
