package inicio;
import java.io.File;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import grafico.Recursos;
import grafico.CargarImagen;
import grafico.Imagen;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuBar;
import javax.swing.JRadioButtonMenuItem;
import estado.EstadoJuego;
import estado.Estado;
import teclado.KeyManager;
import sonido.Sonido;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

//@SuppressWarnings({ "unused", "serial" })
public class Juego extends Canvas implements Runnable {
	// declare constants
	public static final String TITLE  = " Street Fighter UNAP";
	public static final int    WIDTH  = 256;
	public static final int    HEIGHT = 224;
	public static final int    SCALE  = 2;
	// tick variables
	public boolean running = false;
	public int tickCount = 0;
	
	// graphics
	private Graphics g;
	
	// states
	private Estado menuState;
	private Estado gameState;

	// input
	private KeyManager keyManager;
	
	// init jFrame
	private JFrame frame;
	
	// init scanner, and random
	private Scanner sc;
	private Random rand;
	
	// int map
	private int map = 0;
	private final Sonido musicaFondo = new Sonido(System.getProperty("user.dir")+"\\src\\main\\java\\sonido\\fondo1.wav");
        private final Sonido roundInicio = new Sonido(System.getProperty("user.dir")+"\\src\\main\\java\\sonido\\round-1.wav");
        /*JMenuBar barraMenu;
        public JMenu opcion1;
        public JMenu opcion2;
        JRadioButtonMenuItem d1;
        JRadioButtonMenuItem d2;
        JRadioButtonMenuItem d3;
        JMenuItem d4;
        */
        ButtonGroup  grupoRadioFondo;
	public Juego() {
		
            		// init frame properties
		frame = new JFrame(TITLE);
		frame.setSize(WIDTH * SCALE, HEIGHT * SCALE);		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		frame.add(this, BorderLayout.CENTER);
		
		frame.setUndecorated(false);
		frame.setAlwaysOnTop(true);
		frame.setResizable(true);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		setFocusable(false);
				
		// user input
		keyManager = new KeyManager();
		frame.addKeyListener(keyManager);
		
		// pack everything
		frame.pack();
                
		
               /* d1 = new JRadioButtonMenuItem("Ryo");
                d2 = new JRadioButtonMenuItem("Domo");
                d3 = new JRadioButtonMenuItem("Ken");
                d4 = new JRadioButtonMenuItem("A Jugar");
                d4.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                       
                            System.out.print("cki");
                           
                            
                        
                         }
                 });
                grupoRadioFondo = new ButtonGroup();
                grupoRadioFondo.add(d1);
                grupoRadioFondo.add(d2);
                grupoRadioFondo.add(d3);
                barraMenu =new JMenuBar();
                opcion1 = new JMenu("Escenarios");
                opcion2 = new JMenu("Inicio");
                

                opcion1.add(d1);
                opcion1.add(d2);
                opcion1.add(d3);
                barraMenu.add(opcion1);
                barraMenu.add(opcion2);
                frame.add(barraMenu, BorderLayout.NORTH);
                */
		
	}

 
	/**
	 * @see ex_1.png in project dir for explanation of game updating
	 * @see ex_2.png [...] 							of game state managing
	 */

	public synchronized void start() throws IOException {
		
                
		// the program is running...
		running = true;		
		
		// pre-load assets
		Recursos.init();
			
		// states
		gameState = new EstadoJuego(this);
		Estado.setState(gameState);
		

		int choice = 0;
		musicaFondo.play();
                musicaFondo.loop();

                
		map=6;
                roundInicio.play();
		// thread this class
		new Thread(this).start();

	}
	
	public synchronized void stop() {
		// if program is stopped, running is false
		running = false;
	}
	
	/**
	 * Minecraft: Notch's game loop
	 * @link https://stackoverflow.com/questions/18283199/java-main-game-loop
	 */
	
	public void run() {		
		// init vars
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000.0 / 60.0;
		
		int ticks = 0;
		int frames = 0;
		
		long lastTimer = System.currentTimeMillis();
		double delta = 0;
		
		// while the program is running....
		while (running) {
			
			// get the current system time
			long now = System.nanoTime();
			// find delta by taking difference between now and last
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			
			// can render each frame...
			boolean canRender = true;
			
			// if ratio is greater than one, meaning...
			while (delta >= 1) {
				/* if the current time - last / n, where n
				   can be any real number is greater than 1
			    update the game...*/
				ticks++;
				tick();
				delta--;
				canRender = true;
			}
			
			// sleep program so that not to many frames are produced (reduce lag)
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			// if can render...
			if (canRender) {
				// increment frames and render
				frames++;
				render();

			}
			
			// if one second has passed...
			if (System.currentTimeMillis() - lastTimer > 1000) {
				// increment last timer, output frames to user
				lastTimer += 1000;
				System.out.println(ticks + " ticks, " + frames + " frames");
				frames = 0;
				ticks = 0;
			}						
		}		
	}
	
	public void tick() {
		// update keyboard input
		keyManager.tick();
		
		// if current state exist, then update the game
		if (Estado.getState() != null) {
			
			// increment tick count and get state of program
			tickCount++;
			Estado.getState().tick();

		}
	}
	
	
	public void render() {		
		BufferStrategy bs = getBufferStrategy();
		
		// create a double buffering strategy
		if (bs == null) {
			createBufferStrategy(2);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		// create temp white rect that fills screen
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, getWidth(), getHeight());
		
		/* ALL DRAWING HERE */
		
		// init maps
		ImageIcon ryuStage    = new ImageIcon("ryu_stage.png");
                ImageIcon playaStage    = new ImageIcon("1.gif");
                ImageIcon tormentaStage    = new ImageIcon("picture.gif");
		ImageIcon mainStage   = new ImageIcon("main_stage.gif");
		ImageIcon forestStage = new ImageIcon("forest_stage.gif");
                ImageIcon playitaStage    = new ImageIcon("decors-jeu-combat-124.gif");
                ImageIcon decorStage    = new ImageIcon("decors-jeu-combat-088.gif");
                
		//aqui
                g.drawImage(ryuStage.getImage(), -212, 30, ryuStage.getIconWidth() * Juego.SCALE, ryuStage.getIconHeight() * Juego.SCALE,null);
		
		// decision making: if player chooses a map (as def'd in start() method), draw that map 
		if (map == 1)
			g.drawImage(forestStage.getImage(), -900, -220, forestStage.getIconWidth() * 2, forestStage.getIconHeight() * 2,null);

		if (map == 2)
			g.drawImage(mainStage.getImage(), -67, -67, null);
	
		if (map == 3)
			g.drawImage(ryuStage.getImage(), -212, 30, ryuStage.getIconWidth() * Juego.SCALE, ryuStage.getIconHeight() * Juego.SCALE,null);
                if (map == 4)
			g.drawImage(playaStage.getImage(), 0, 0, playaStage.getIconWidth() * Juego.SCALE, playaStage.getIconHeight() * Juego.SCALE,null);
		if (map == 5){
                    g.drawImage (tormentaStage.getImage(), 0,0, tormentaStage.getIconWidth(),tormentaStage.getIconHeight()+35, null);
                    //System.out.println("----->"+tormentaStage.getIconHeight()+35);
                    
                           }
                if (map == 6){
                    g.drawImage (playitaStage.getImage(), 0,0, playitaStage.getIconWidth(),playitaStage.getIconHeight(), null);
                    //System.out.println("----->"+tormentaStage.getIconHeight()+35);
                    
                           }
                if (map == 7){
                    g.drawImage (decorStage.getImage(), 0,0, decorStage.getIconWidth(),decorStage.getIconHeight(), null);
                    //System.out.println("----->"+tormentaStage.getIconHeight()+35);
                    
                           }
                
                
			//g.drawImage(tormentaStage.getImage(), -67, -67, null);
		
		// if current state exist, then render		
		if (Estado.getState() != null) {		
			tickCount++;
			Estado.getState().render(g);	
		}
					
		/* ALL DRAWING HERE */
		
		g.dispose();
		bs.show();
	}	
	
	public static void main(String[] args) throws IOException {
		Juego game = new Juego();
                 game.start();
	}
	
	// GETTERS AND SETTERS
	
	/**
	 * @description 
	 * 	   gets key presses of user
	*/
	public KeyManager getKeyManager() {
		return keyManager;
	}
	
	/**
	 * @description 
	 * 	   gets current game state
	*/
	public Estado getEstadoJuego() {
		return gameState;
	}
}
