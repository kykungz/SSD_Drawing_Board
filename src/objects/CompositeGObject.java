package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class CompositeGObject extends GObject {

	private List<GObject> gObjects;

	public CompositeGObject() {
		super(0, 0, 0, 0);
		gObjects = new ArrayList<GObject>();
	}

	public void add(GObject gObject) {
		this.gObjects.add(gObject);
		this.recalculateRegion();
	}

	public void remove(GObject gObject) {
		this.gObjects.remove(gObject);
	}

	@Override
	public void move(int dX, int dY) {
		for (GObject shape : this.gObjects) {
			shape.move(dX, dY);
		}
		super.move(dX, dY);
	}

	public void recalculateRegion() {
		GObject leftMost = new Rect(99999, 0, 0, 0, Color.WHITE);
		GObject rightMost = new Rect(0, 0, 0, 0, Color.WHITE);
		GObject topMost = new Rect(0, 99999, 0, 0, Color.WHITE);
		GObject bottomMost = new Rect(0, 0, 0, 0, Color.WHITE);

		for (GObject shape : this.gObjects) {
			if (shape.x < leftMost.x) {
				leftMost = shape;
			}
			if (shape.y < topMost.y) {
				topMost = shape;
			}
			if (shape.x + shape.width > rightMost.x + rightMost.width) {
				rightMost = shape;
			}
			if (shape.y + shape.height > bottomMost.y + bottomMost.height) {
				bottomMost = shape;
			}
		}

		this.x = leftMost.x;
		this.y = topMost.y;
		this.width = (rightMost.x + rightMost.width) - leftMost.x;
		this.height = (bottomMost.y + bottomMost.height) - topMost.y;
	}

	@Override
	public void paintObject(Graphics g) {
		for (GObject shape : this.gObjects) {
			shape.paintObject(g);
		}
	}

	@Override
	public void paintLabel(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawString("Composite", x, y + height + 10);
	}

}
