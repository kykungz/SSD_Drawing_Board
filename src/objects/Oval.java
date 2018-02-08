package objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Oval extends GObject {

	private Color color;

	public Oval(int x, int y, int width, int height, Color color) {
		super(x, y, width, height);
		this.color = color;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	@Override
	public void paintObject(Graphics g) {
		g.setColor(color);
		g.fillOval(x, y, width, height);
		System.out.printf("painting: %d %d %d %d\n", x, y, width, height);
	}

	@Override
	public void paintLabel(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawString("Oval", x, y + height + 10);
	}

}
