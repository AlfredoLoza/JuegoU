package personaje;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import grafico.Animacion;
import grafico.Recursos;
import inicio.Juego;
import sonido.Sonido;
import estado.EstadoJuego;
import estado.Estado;

public class Ken extends Persona {

	private int health;
	private int velX, velY;
	private Juego juego;

	// STATES

	// basic movement
	private final int IDLING      = 0;
	private final int PARRYING_R  = 1; 
	private final int PARRYING_L  = 2;
	private final int CROUCHING   = 3;
	private final int JUMPING        = 9;
	private final int FRONT_FLIPPING = 10;
	private final int BACK_FLIPPING  = 11;

	// ground attacks
	private final int ATTACKING_N4 = 4;
	private final int ATTACKING_N5 = 5;
	private final int ATTACKING_N1 = 6;
	private final int ATTACKING_N2 = 7;

	// crouch attacks
	private final int ATTACKING_C_N4 = 8;

	// air attacks: A for air
	private final int ATTACKING_A_N4  = 12;
	private final int ATTACKING_A_N5  = 13;
	private final int ATTACKING_A_N1  = 14;	

	// hurting anims
	private final int HURTING = 15;

	private final int DUMMY = 19;

	// platformer
	private final int GRAV = 1;
	private final int TERMINAL_VELOCITY = 2;
	private final int JUMP_SPEED = -10;

	private boolean[] anims = new boolean[20];

	// movement animations
	private Animacion inactivo;
	private Animacion parar_f, parar_b;
	private Animacion agacharse;
	private Animacion saltar, salto_adelante, salto_atras;

	// attack animations
	private Animacion ataque_N4, ataque_N5, ataque_N1, ataque_N2;

	// crouch attack animations
	private Animacion ataque_C_N4;

	// air attacks animation
	private Animacion ataque_A_N4, ataque_A_N5, ataque_A_N1;

	// ground hurt
	private Animacion agachado_G;

	// cooldowns
	private boolean hurting;
	private long lastTimer;

	// random generator
	Random rand;
        private final Sonido golpe = new Sonido("C:/Users/Matrix/Documents/NetBeansProjects/JuegoUnap/src/main/java/sonido/golpe.wav");

	public Ken(Juego juego, float x, float y) {
		super(x, y);
		// initialise game in constuctor to access vars
		this.juego = juego;

		rand = new Random();

		health = 100;
		hurting = false;

		// movement anims
		inactivo        = new Animacion(100, Recursos.esperando1);
		parar_f     = new Animacion(100, Recursos.parado_f1);
		parar_b     = new Animacion(100, Recursos.parado_b1);
		agacharse      = new Animacion(100, Recursos.agacharse1);
		saltar 	    = new Animacion(85, Recursos.saltar1);
		salto_adelante  = new Animacion(120, Recursos.voltereta_atras1);
		salto_atras   = new Animacion(120, Recursos.salto_mortal1);

		// ground attack anims
		ataque_N4   = new Animacion(75, Recursos.punio1);
		ataque_N5   = new Animacion(100, Recursos.golpe_rapido1);
		ataque_N1   = new Animacion(75, Recursos.patada_arriba1);
		ataque_N2   = new Animacion(100, Recursos.patada_baja1);

		// crouch attack
		ataque_C_N4 = new Animacion(100, Recursos.golpe_agachado1);

		// air attacks
		ataque_A_N4 = new Animacion(100, Recursos.punio_aereo1);
		ataque_A_N5 = new Animacion(100, Recursos.punio_abajo1);
		ataque_A_N1 = new Animacion(100, Recursos.patada_aerea1);		

		// hurting anim
		agachado_G   = new Animacion(100, Recursos.soportar_golpe_atras1);

	}

	@Override
	public void tick() {

		// tick mvmt
		inactivo.tick();
		agacharse.tick();

		// update anims
		parar_b.tick();		
		parar_f.tick();	

		if (anims[ATTACKING_N4])
			ataque_N4.tick();

		if (anims[ATTACKING_N5])
			ataque_N5.tick();

		if (anims[ATTACKING_N1])
			ataque_N1.tick();

		if (anims[ATTACKING_N2])
			ataque_N2.tick();

		if (anims[ATTACKING_C_N4])
			ataque_C_N4.tick();

		if (anims[ATTACKING_A_N4])
			ataque_A_N4.tick();

		if (anims[ATTACKING_A_N5])
			ataque_A_N5.tick();

		if (anims[ATTACKING_A_N1])
			ataque_A_N1.tick();

		if (y == 280){
			// if press left, move left
			if (juego.getKeyManager().izquierda1 && !juego.getKeyManager().arriba1) {
				velX = -2;
                                
                               // reset and init true to state
				handleAnims(PARRYING_L);
			}
			// if press right, move forward
			else if (juego.getKeyManager().derecha1 && !juego.getKeyManager().arriba1) {
				velX = 2;
                            
				// reset and init true to state
				handleAnims(PARRYING_R);
			} 
			//if pressing only up
			else if (juego.getKeyManager().arriba1 && !juego.getKeyManager().derecha1 && !juego.getKeyManager().izquierda1 && !anims[JUMPING]) {
				// jump
                                
				velY = JUMP_SPEED - 2;
				y-=1;
				saltar.index = 0;
			}
			// if pressing up, right
			else if (juego.getKeyManager().arriba1 && juego.getKeyManager().derecha1 && !juego.getKeyManager().izquierda1 && !anims[FRONT_FLIPPING]) {
				// jump diagonally to the right
                               
				velY = JUMP_SPEED;
				velX = 2;
				y-=1;
				salto_adelante.index = 0;				
			}
			// if pressing up, left
			else if (juego.getKeyManager().arriba1 && !juego.getKeyManager().derecha1 && juego.getKeyManager().izquierda1 && !anims[BACK_FLIPPING]) {
				// jump diagonally to the left
                                 
				velY = JUMP_SPEED;
				velX = -2;
				y-=1;
				salto_atras.index = 0;	
			}
			// if press down, crouch
			else if (!juego.getKeyManager().derecha1 && !juego.getKeyManager().izquierda1 && juego.getKeyManager().abajo1){
                                 
				// set hor, vertical speed to 0
				velX = 0;
				velY = 0;
                               			
				handleAnims(CROUCHING);

				if (juego.getKeyManager().N4) {
					velX = 0;
					velY = 0;

					anims[CROUCHING] = false;

					// reset and init true to state
					if (checkIfRunning()) {
						handleAnims(ATTACKING_C_N4);
					}

				}

				resetAnim(ataque_C_N4, ATTACKING_C_N4);

			}
			else if (juego.getKeyManager().N4 && !anims[CROUCHING]){
				// set hor, vertical speed to 0
                                
				velX = 0;
				velY = 0;

				// reset and init true to state
				if (checkIfRunning())
					handleAnims(ATTACKING_N4);
			} else if (juego.getKeyManager().N5 && !anims[CROUCHING]){
				// set hor, vertical speed to 0
                               
				velX = 0;
				velY = 0;

				// reset punch 2
				if (checkIfRunning())
					handleAnims(ATTACKING_N5);
			} else if (juego.getKeyManager().N1 && !anims[CROUCHING]){
				// set hor, vertical speed to 0
                                 System.out.println("set hor, vertical speed to 000");
                                 
                                golpe.play();
				velX = 0;
				velY = 0;

				// reset punch
				if (checkIfRunning())
					handleAnims(ATTACKING_N1);
			} else if (juego.getKeyManager().N2 && !anims[CROUCHING]){
				// set hor, vertical speed to 0
                                 System.out.println("set hor, vertical speed to 00000");
				velX = 0;
				velY = 0;

				// reset 
				if (checkIfRunning())
					handleAnims(ATTACKING_N2);
			} else {

				velX = 0;
				velY = 0;

				anims[CROUCHING] = false;
				anims[PARRYING_L] = false;
				anims[PARRYING_R] = false;
				anims[JUMPING] = false;
				anims[FRONT_FLIPPING] = false;
				anims[BACK_FLIPPING] = false;

				if (anims[DUMMY]) {
					handleAnims(IDLING);
				}	

			}
		}// otherwise, player is in air
		else {

			anims[PARRYING_R] = false;
			anims[PARRYING_L] = false;

			// update frames
			saltar.tick();
			salto_adelante.tick();
			salto_atras.tick();

			// if moving left
			if (velX < 0) {
				// back flip
				handleAirAttacks(salto_atras, BACK_FLIPPING);
				// if moving right
			} else if (velX > 0) {
				// front flip
				handleAirAttacks(salto_adelante, FRONT_FLIPPING);
				// otherwise
			} else {
				// jump vertically
				handleAirAttacks(saltar, JUMPING);
			}
		}

		// if attacking, stop moving
		if (anims[ATTACKING_N4] || anims[ATTACKING_N5] || anims[ATTACKING_N1] || anims[ATTACKING_N2]) {
			velX = 0;
			velY = 0;
		}

		// reset ground attack
		resetAnim(ataque_N4, ATTACKING_N4);
		resetAnim(ataque_N5, ATTACKING_N5);
		resetAnim(ataque_N1, ATTACKING_N1);
		resetAnim(ataque_N2, ATTACKING_N2);

		// reset air attacks
		resetAnim(ataque_A_N4, ATTACKING_A_N4);
		resetAnim(ataque_A_N5, ATTACKING_A_N5);
		resetAnim(ataque_A_N1, ATTACKING_A_N1);

		// if on the floor...
		if (y == 280) {
			// if attacking...
			if (anims[ATTACKING_A_N4] || anims[ATTACKING_A_N5] || anims[ATTACKING_A_N1]) {
				anims[ATTACKING_A_N4] = false;
				anims[ATTACKING_A_N5] = false;
				anims[ATTACKING_A_N1] = false;
			}
		}

		collisions();		        	

		// if hurting.. tick anims
		if (hurting) {	
			agachado_G.tick();

			// if 400ms has passed, then the anim is complete
			if (System.currentTimeMillis() - lastTimer > 400) {
				// set anim to false, hurting to false, and inc timer
				anims[HURTING] = false;
				hurting = false;
				lastTimer += 400;
			}
		}

		// increment x by horizontal speed
		x += velX;

		// if in air, fall
		if (y < 280)
			fall();

		// if player clips through floor, set y to floor
		if (y > 280)
			y = 280;


		// check edges of screen
		checkWalls();

	}
	
	public void checkWalls(){
		
		// if x < 0...
		if (x < 0){
			// do not go past left edge
			x = 0;
		}

		// if x > 0
		if (x > Juego.WIDTH * 2){
			// do not go past right edge
			x = Juego.WIDTH * 2;
		}
	}

	public void fall(){

		// update y speed
		velY += GRAV;

		// if greater than max speed, just equal max speed
		if (velY > TERMINAL_VELOCITY){
			velY = TERMINAL_VELOCITY;
		} 

		// if moving left...		
		if (velX < 0) {
			// backflip
			if (salto_atras.getFrame() >= 2 && salto_atras.getFrame() <= 3) {
				velY = 0;
			}
		}

		// if moving right...
		if (velX > 0) {
			// front flip
			if (salto_adelante.getFrame() >= 2 && salto_adelante.getFrame() <= 3) {
				velY = 0;
			}
		}

		// if horizontally still...
		if (velX == 0) {
			// jump anims
			if (saltar.getFrame() >= 3 && saltar.getFrame() <= 4) {
				velY = 0;
			}
		}

		// update y by y-speed
		y += velY;


	}

	public void collisions() {

		if (juego.getEstadoJuego().getRyuAttackBounds().intersects(getHitBounds())) {

			if (!hurting) {
                             System.out.println("hurtin");
				hurting = true;
				lastTimer = System.currentTimeMillis();
				handleAnims(HURTING);
				health-=2;
			}

			try {
				TimeUnit.MILLISECONDS.sleep(30);
                                System.out.println("pausa");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} 

	}

	@Override
	public void render(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;	

		// when hit, vibrate randomly
		if (hurting) {
			int k = rand.nextInt(3);	
			g2d.translate(-k, k);
		}

		// draw shadow

		g.setColor(new Color(0,0,0, 125));
		g.fillOval((int) x - 61, 188 * Juego.SCALE, 64, 16);

		// drawing ken

		if(anims[PARRYING_R])
			g.drawImage(getCurrentAnimFrame(), (int) (x + 9), (int) (y - 3), -getCurrentAnimFrame().getWidth(), getCurrentAnimFrame().getHeight(),null);	

		else if (anims[PARRYING_L])
			g.drawImage(getCurrentAnimFrame(), (int) (x + 4), (int) y, -getCurrentAnimFrame().getWidth(), getCurrentAnimFrame().getHeight(),null);	

		else if (anims[CROUCHING])
			g.drawImage(getCurrentAnimFrame(), (int) x, (int) y + 36, -getCurrentAnimFrame().getWidth(), getCurrentAnimFrame().getHeight(),null);	

		else if (anims[JUMPING])
			g.drawImage(getCurrentAnimFrame(), (int) x - 3, (int) y - 20, -getCurrentAnimFrame().getWidth(), getCurrentAnimFrame().getHeight(), null);

		else if (anims[BACK_FLIPPING])
			g.drawImage(getCurrentAnimFrame(), (int) x - 15, (int) y - 15, -getCurrentAnimFrame().getWidth(), getCurrentAnimFrame().getHeight(), null);

		else if (anims[FRONT_FLIPPING])
			g.drawImage(getCurrentAnimFrame(), (int) x - 15, (int) y - 15, -getCurrentAnimFrame().getWidth(), getCurrentAnimFrame().getHeight(),null);

		else if (anims[ATTACKING_N4])
			g.drawImage(getCurrentAnimFrame(), (int) x, (int) (y + 3), -getCurrentAnimFrame().getWidth(), getCurrentAnimFrame().getHeight(), null);

		else if (anims[ATTACKING_N5])
			g.drawImage(getCurrentAnimFrame(), (int) x, (int) (y + 3), -getCurrentAnimFrame().getWidth(), getCurrentAnimFrame().getHeight(), null);

		else if (anims[ATTACKING_N1])
			g.drawImage(getCurrentAnimFrame(), (int) x, (int) (y - 3), -getCurrentAnimFrame().getWidth(), getCurrentAnimFrame().getHeight(), null);

		else if (anims[ATTACKING_N2])
			g.drawImage(getCurrentAnimFrame(), (int) x, (int) (y - 4), -getCurrentAnimFrame().getWidth(), getCurrentAnimFrame().getHeight(), null);

		else if (anims[ATTACKING_C_N4])
			g.drawImage(getCurrentAnimFrame(), (int) x, (int) y + 37, -getCurrentAnimFrame().getWidth(), getCurrentAnimFrame().getHeight(), null);	

		else if (anims[HURTING])
			g.drawImage(getCurrentAnimFrame(), (int) x + 20, (int) y + 2, -getCurrentAnimFrame().getWidth(), getCurrentAnimFrame().getHeight(), null);	

		else if (anims[ATTACKING_A_N4])
			g.drawImage(getCurrentAnimFrame(), (int) x, (int) y - 10, -getCurrentAnimFrame().getWidth(), getCurrentAnimFrame().getHeight(), null);

		else if (anims[ATTACKING_A_N5])
			g.drawImage(getCurrentAnimFrame(), (int) x, (int) y - 10, -getCurrentAnimFrame().getWidth(), getCurrentAnimFrame().getHeight(), null);

		else if (anims[ATTACKING_A_N1])
			g.drawImage(getCurrentAnimFrame(), (int) x - 4, (int) y - 5, -getCurrentAnimFrame().getWidth(), getCurrentAnimFrame().getHeight(), null);

		else 
			g.drawImage(getCurrentAnimFrame(), (int) x, (int) y, -getCurrentAnimFrame().getWidth(), getCurrentAnimFrame().getHeight(), null);	


		// cuadro de Ken para ver colision

		/*g.setColor(Color.WHITE);
		g.drawRect(getHitBounds().x, getHitBounds().y, getHitBounds().width, getHitBounds().height);

		g.setColor(Color.RED);
		g.drawRect(getAttackBounds().x, getAttackBounds().y, getAttackBounds().width, getAttackBounds().height);*/
		
	}

	private BufferedImage getCurrentAnimFrame() {

		// get animation of each frame
		
		if (anims[PARRYING_R]) 
			return parar_b.getCurrentFrame();

		else if (anims[PARRYING_L])  
			return parar_f.getCurrentFrame();

		else if (anims[CROUCHING])   
			return agacharse.getCurrentFrame();

		else if (anims[JUMPING]) 
			return saltar.getCurrentFrame();

		else if (anims[FRONT_FLIPPING]) 
			return salto_adelante.getCurrentFrame();

		else if (anims[BACK_FLIPPING]) 
			return salto_atras.getCurrentFrame();

		else if (anims[ATTACKING_N4]) 
			return ataque_N4.getCurrentFrame();

		else if (anims[ATTACKING_N5]) 
			return ataque_N5.getCurrentFrame();

		else if (anims[ATTACKING_N1]) 
			return ataque_N1.getCurrentFrame();

		else if (anims[ATTACKING_N2]) 
			return ataque_N2.getCurrentFrame();

		else if (anims[ATTACKING_C_N4]) 
			return ataque_C_N4.getCurrentFrame();

		else if (anims[ATTACKING_A_N4])
			return ataque_A_N4.getCurrentFrame();

		else if (anims[ATTACKING_A_N5]) 
			return ataque_A_N5.getCurrentFrame();

		else if (anims[ATTACKING_A_N1]) 
			return ataque_A_N1.getCurrentFrame();

		else if (anims[HURTING]) 
			return agachado_G.getCurrentFrame();

		else                         
			return inactivo.getCurrentFrame();

	}

	public Rectangle getHitBounds() {
		// get attack hit boxes for each attack
		if (anims[CROUCHING])
			return new Rectangle((int) x - 60, (int) y + 30, 60, 80);
		else if (anims[ATTACKING_C_N4])
			return new Rectangle((int) x - 60, (int) y + 30, 60, 80);


		return new Rectangle((int) x - 60, (int) y, 60, 110);	
	}

	public Rectangle getAttackBounds() {

		// draw rectangles for each attack
		if (anims[ATTACKING_N4] && ataque_N4.index >= 3 && ataque_N4.index <= 4)
			return new Rectangle((int) x - 110, (int) y + 10, 60, 30);

		if (anims[ATTACKING_N5] && ataque_N5.index >= 1 && ataque_N5.index <= 2)
			return new Rectangle((int) x - 100, (int) y + 10, 60, 30);

		if (anims[ATTACKING_N1] && ataque_N1.index >= 3 && ataque_N1.index <= 6)
			return new Rectangle((int) x - 140, (int) y + 30, 90, 50);

		if (anims[ATTACKING_N2] && ataque_N2.index >= 1 && ataque_N2.index <= 3)
			return new Rectangle((int) x - 120, (int) y , 70, 50);
		
		if (anims[ATTACKING_A_N4] && ataque_A_N4.index >= 1 && ataque_A_N4.index <= 3)
			return new Rectangle((int) x - 80, (int) y + 30, 50, 30);
		
		if (anims[ATTACKING_A_N5] && ataque_A_N5.index >= 1 && ataque_A_N5.index <= 3)
			return new Rectangle((int) x - 80, (int) y + 20 , 70, 30);
		
		if (anims[ATTACKING_A_N1] && ataque_A_N1.index >= 1 && ataque_A_N1.index <= 3)
			return new Rectangle((int) x - 120, (int) y + 20, 70, 50);

		if (anims[ATTACKING_C_N4] && ataque_C_N4.index >= 0 && ataque_C_N4.index <= 1)
			return new Rectangle((int) x - 90, (int) y + 40, 60, 30);


		return new Rectangle((int) x - 60, (int) y, 0, 0);
	}

	public void handleAnims(int unchanged){

		// make all anims false...
		for (int i = 0; i < 19; i++) {
			anims[i] = false;
		}

		// except active anim
		anims[unchanged] = true;

	}

	public void handleAirAttacks(Animacion anim, int index) {

		// if g, h, b while in air... set all anims to false except called anim
		if (juego.getKeyManager().N4) {
			handleAnims(ATTACKING_A_N4);
		} else if (juego.getKeyManager().N5) {
			handleAnims(ATTACKING_A_N5);
		} else if (juego.getKeyManager().N1) {
			handleAnims(ATTACKING_A_N1);
		} else if (checkIfRunning()){
			// update anim and set all to false
			anim.tick();
			handleAnims(index);
		}

	}

	public boolean checkIfRunning() {

		// counter
		int i = 0;

		// traverse through anims
		for (boolean b : anims) {
			// if false, increment counter
			if (b == false)
				i++;
		}
		// if entire array is false, return false
		if (i == 19) {
			return false;
		}

		// otherwise, true
		return true;
	}

	public void resetAnim(Animacion anim, int frame) {
		if (anim.hasPlayedOnce()) {			
			anim.setPlayed();
			anims[frame] = false;
		}

	}

	public int getVida() {
		return health;
	}

	public int getKenX() {
		return (int) x;
	}
}
