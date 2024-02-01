package personaje;

import java.awt.Graphics;

public abstract class Entidad {
	// every creature has an x,y
	protected float x, y;

	// constructor
	public Entidad(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	// update
	public abstract void tick();
	
	// draw
	public abstract void render(Graphics g);	
}
