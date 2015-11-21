package pen;
import java.awt.Color;

public class DetectorModel {
	public int x = 0;
	public int y = 0;
	public Color min_color;
	public Color max_color;
	
	public DetectorModel (int x, int y, Color min_color, Color max_color) {
		this.x = x;
		this.y = y;

		this.min_color = min_color;
		this.max_color = max_color;
	}
}
