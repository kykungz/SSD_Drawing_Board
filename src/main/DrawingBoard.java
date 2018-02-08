package main;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import objects.*;

public class DrawingBoard extends JPanel {

	private MouseAdapter mouseAdapter;
	private List<GObject> gObjects;
	private GObject target;

	private int gridSize = 10;

	public DrawingBoard() {
		gObjects = new ArrayList<GObject>();
		mouseAdapter = new MAdapter();
		addMouseListener(mouseAdapter);
		addMouseMotionListener(mouseAdapter);
		setPreferredSize(new Dimension(800, 600));
	}

	public void addGObject(GObject gObject) {
		this.gObjects.add(gObject);
		this.repaint();
	}

	public void groupAll() {
		CompositeGObject composite = new CompositeGObject();
		for (GObject shape : gObjects) {
			composite.add(shape);
		}
		this.gObjects.clear();
		this.gObjects.add(composite);
		repaint();

	}

	public void deleteSelected() {
		this.gObjects.remove(target);
		this.repaint();
	}

	public void clear() {
		this.gObjects.clear();
		this.repaint();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		paintBackground(g);
		paintGrids(g);
		paintObjects(g);
	}

	private void paintBackground(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	private void paintGrids(Graphics g) {
		g.setColor(Color.lightGray);
		int gridCountX = getWidth() / gridSize;
		int gridCountY = getHeight() / gridSize;
		for (int i = 0; i < gridCountX; i++) {
			g.drawLine(gridSize * i, 0, gridSize * i, getHeight());
		}
		for (int i = 0; i < gridCountY; i++) {
			g.drawLine(0, gridSize * i, getWidth(), gridSize * i);
		}
	}

	private void paintObjects(Graphics g) {
		for (GObject go : gObjects) {
			go.paint(g);
		}
	}

	class MAdapter extends MouseAdapter {

		private int lastX = 0;
		private int lastY = 0;

		private void deselectAll() {
			if (target != null)
				target.deselected();
		}

		@Override
		public void mousePressed(MouseEvent e) {
			this.deselectAll();
			target = null;
			for (GObject gObject : gObjects) {
				if (gObject.pointerHit(e.getX(), e.getY())) {
					target = gObject;
					target.selected();
					System.out.println(target);
					break;
				}
			}
			this.lastX = e.getX();
			this.lastY = e.getY();
			repaint();
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (target != null) {
				int dX = e.getX() - this.lastX;
				int dY = e.getY() - this.lastY;
				target.move(dX, dY);
				this.lastX = e.getX();
				this.lastY = e.getY();
			}
			repaint();
		}
	}

}