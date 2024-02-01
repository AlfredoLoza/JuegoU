package estado;

import java.awt.Graphics;
import java.awt.Rectangle;
import inicio.Juego;


public abstract class Estado {

	// init State var
	private static Estado currState = null;
	
	// setter for State
	public static void setState(Estado state) {
		currState = state;
	}
	
	// getter for State
	public static Estado getState() {
		return currState;
	}
	
	// init juego var
	protected Juego juego;
	
	// constructor
	public Estado(Juego juego) {
		this.juego = juego;
	}
	
	// nethods that all extended classes will share...
	public abstract void tick();
	
	public abstract void render(Graphics g);
	
	public abstract void music();
		
	// hitbox methods
	public abstract Rectangle getRyuHitBounds();	
	public abstract Rectangle getRyuAttackBounds();	
	public abstract Rectangle getKenHitBounds();	
	public abstract Rectangle getKenAttackBounds();	
	public abstract int getRyuX();	
	public abstract int getKenX();	
}
