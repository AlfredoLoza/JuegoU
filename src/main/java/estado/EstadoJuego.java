package estado;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import personaje.Ken;
import personaje.Ryu;
import inicio.Juego;

public class EstadoJuego extends Estado {	
	// init ryu, ken
	private Ryu ryu;
	private Ken ken;
	
	// constructor
	public EstadoJuego(Juego juego) {
		super(juego);
		
		// create instance of ryu, ken 
                /**
                 * ryu = new Ryu(game, 60, 280);
		ken = new Ken(game, 224 * 2,280);
                */
		ryu = new Ryu(juego, 60, 280);
		ken = new Ken(juego, 224 * 2,280);		
	}
	
	@Override
	public void tick() {
		// update hitboxes, attack boxes
		ryu.getLimitesAtaque();
		ryu.getLimitesGolpe();
		
		ken.getAttackBounds();
		ken.getHitBounds();
		
		// update entire instance
		ryu.tick();
		ken.tick();	
	}

	@Override
	public void render(Graphics g) {				
		// get images for ui
                
		ImageIcon healthBar = new ImageIcon("estado.png");
                ImageIcon ryoBar = new ImageIcon("nivel_1.png");
                ImageIcon KenBar = new ImageIcon("nivel_2.png");
		ImageIcon ryuFont = new ImageIcon("ryuFont.png");
		ImageIcon kenFont = new ImageIcon("kenFont.png");

		// print ui for ryu
		g.setColor(Color.red);
		double percentRyu = ryu.getVida() / 100.0;	
		g.fillRect(61, 19, (int) (173 * percentRyu), 11);
		g.drawImage(ryuFont.getImage(), kenFont.getIconWidth() - 35, 40, 48, 16, null);
		g.drawImage(ryoBar.getImage(),  50, 10, (int) (ryoBar.getIconWidth() * 1.2), (int) (ryoBar.getIconHeight() * 1.5) ,null);
		// print ui for ken
		g.setColor(Color.yellow);
		double percentKen  = ken.getVida() / 100.0;	
		g.fillRect(99 + 29 + 144, 19, (int) (173 * percentKen) , 11);
		g.drawImage(kenFont.getImage(), (Juego.WIDTH * Juego.SCALE) - 2 * kenFont.getIconWidth() + kenFont.getIconWidth() / 2 + 32, 40, 48, 16, null);
                g.drawImage(KenBar.getImage(),  260, 10, (int) (KenBar.getIconWidth() * 1.2), (int) (KenBar.getIconHeight() * 1.5) ,null);
		
		// drawn last so that rect and ui could be under
                 
		g.drawImage(healthBar.getImage(),  60, 16, (int) (healthBar.getIconWidth() * 1.2), (int) (healthBar.getIconHeight() * 1.2) ,null);
                

		
		// render instances
		ryu.render(g);
		ken.render(g);	
					
		//g.setColor(Color.BLACK);
		// end game
                
                
                if ((int)ryu.getVida() <= 0) {
			// win screen
                        ImageIcon ganaRuy = new ImageIcon("ganaKen.gif");
                        g.drawImage (ganaRuy.getImage(), 0,0, Juego.WIDTH * 2,Juego.HEIGHT * 2, null);
                        
                        /*
                        ImageIcon ganaKen = new ImageIcon("ganaKen.gif");
                        g.drawImage (ganaKen.getImage(), 0,0, Juego.WIDTH * 2,Juego.HEIGHT * 2, null);
			String kenWin = "KEN GANA EL JUEGO!";
			g.fillRect(0, 0, Juego.WIDTH * 2, Juego.HEIGHT * 2);
			g.setColor(Color.BLUE);
			int width = g.getFontMetrics().stringWidth(kenWin);
			g.drawString(kenWin, Juego.WIDTH - width/2, Juego.HEIGHT);*/
		}
                
		if ((int)ken.getVida()<1) {
                    
			// win screen
                        ImageIcon ganaRuy = new ImageIcon("ganaRyu.gif");
                        g.drawImage (ganaRuy.getImage(), 0,0, Juego.WIDTH * 2,Juego.HEIGHT * 2, null);
			/*String ryuWin = "RYU GANA EL JUEGO!";
			g.fillRect(0, 0, Juego.WIDTH * 2, Juego.HEIGHT * 2);
			g.setColor(Color.BLUE);
			int width = g.getFontMetrics().stringWidth(ryuWin);
			g.drawString(ryuWin, Juego.WIDTH - width/2, Juego.HEIGHT);*/
		} 
                		
	}
	
	// GETTERS AND SETTERS:
	
	public Rectangle getRyuHitBounds() {
		return ryu.getLimitesGolpe();
	}
	
	public Rectangle getRyuAttackBounds() {
		return ryu.getLimitesAtaque();
	}
	
	public Rectangle getKenHitBounds() {
		return ken.getHitBounds();
	}
	
	public Rectangle getKenAttackBounds() {
		return ken.getAttackBounds();
	}
	
	public int getRyuX() {
		return ryu.getRyuX();
	}

	public int getKenX() {
		return ken.getKenX();
	}

    @Override
    public void music() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
