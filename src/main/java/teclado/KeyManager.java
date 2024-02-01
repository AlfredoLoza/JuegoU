package teclado;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {
	private boolean[] keys;
	public boolean arriba, abajo, izquierda, derecha;
	public boolean arriba1, abajo1, izquierda1, derecha1;
	
	public boolean G, H, B, N;
	public boolean N1, N2, N4, N5;

	
	public KeyManager() {
		keys = new boolean[256];
	}
	
	public void tick() {		
		// movement P1
		arriba    = keys[KeyEvent.VK_W];
		abajo  = keys[KeyEvent.VK_S];
		izquierda  = keys[KeyEvent.VK_A];
		derecha = keys[KeyEvent.VK_D];
		
		// attack P1
		G = keys[KeyEvent.VK_G];
		H = keys[KeyEvent.VK_H];
		B = keys[KeyEvent.VK_B];
		N = keys[KeyEvent.VK_N];
		
		// movement P2
		arriba1    = keys[KeyEvent.VK_UP];
		abajo1  = keys[KeyEvent.VK_DOWN];
		izquierda1  = keys[KeyEvent.VK_LEFT];
		derecha1 = keys[KeyEvent.VK_RIGHT];
		
		// attack P2
		N4 = keys[KeyEvent.VK_NUMPAD4];
		N5 = keys[KeyEvent.VK_NUMPAD5];
		N1 = keys[KeyEvent.VK_NUMPAD1];
		N2 = keys[KeyEvent.VK_NUMPAD2];
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
}
