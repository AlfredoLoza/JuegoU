package personaje;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import grafico.Animacion;
import grafico.Recursos;
import inicio.Juego;

public class Ryu extends Persona {

	// Ryu: vars...
	private int vida;
	private int velX, velY;
	private Juego juego;

	// STATES

	// basic movement
	private final int AVANZAR     = 0;
	private final int PARAR_R     = 1; 
	private final int PARAR_L     = 2;
	private final int AGACHAR      = 3;
	private final int SALTAR        = 9;
	private final int SALTO_FRENTE = 10;
	private final int SATO_ATRAS  = 11;

	// ataques terrestres: la letra representa la clave respectiva
	private final int ATAQUE_G    = 4;
	private final int ATAQUE_H    = 5;
	private final int ATAQUE_B    = 6;
	private final int ATAQUE_N    = 7;

	// ataques en cuclillas: C para agacharse
	private final int ATAQUE_C_G  = 8;

	// ataques aéreos: A por aire
	private final int ATAQUE_A_G  = 12;
	private final int ATAQUE_A_H  = 13;
	private final int ATAQUE_A_B  = 14;	

	// animacion de latimado
	private final int HERIDO = 15;
	// dummy var for checks
	private final int DUMMY = 19;

	// platformer
	private final int GRAV = 1; //GRAVEDAD PARA RYU
	private final int TERMINAL_VELOCITY = 2; // VELOCIDAD DE BAJADA
	private final int JUMP_SPEED = -10;//-10  VELOCIDAD DE SALTO

	private boolean[] animacion = new boolean[20];

	// movement animations
	private Animacion esperando;
	private Animacion parar_f, parar_b;
	private Animacion agacharse;
	private Animacion saltar, salto_adelante, salto_atras;

	// ground attack animations
	private Animacion ataque_G, ataque_H, ataque_B, ataque_N;

	// crouch attack animations
	private Animacion ataque_C_G;

	// air attacks animation
	private Animacion ataque_A_G, ataque_A_H, ataque_A_B;

	// ground hurt
	private Animacion agachado_G;

	// cooldowns
	private boolean agachado;
	private long lastTimer;

	// random generator
	Random rand;

	public Ryu(Juego juego, float x, float y) {
		super(x, y);
		// initialise game in constuctor to access vars
		this.juego = juego;

		// instantiate random gen
		rand = new Random();

		// health to 100
		vida = 100;

		// movement
		esperando  = new Animacion(100, Recursos.esperando);
		parar_f    = new Animacion(100, Recursos.parado_f);
		parar_b    = new Animacion(100, Recursos.parado_b);
		agacharse  = new Animacion(100, Recursos.agachase);
		saltar 	   = new Animacion(85, Recursos.saltar);
		salto_adelante = new Animacion(120, Recursos.salto_mortal);
		salto_atras  = new Animacion(120, Recursos.voltereta_atras);

		// ground attacks
		ataque_G   = new Animacion(100, Recursos.punio);
             
		ataque_H   = new Animacion(100, Recursos.golpe_rapido);
		ataque_B   = new Animacion(75, Recursos.patada_arriba);
		ataque_N   = new Animacion(100, Recursos.patada_baja);

		// crouch attack
		ataque_C_G = new Animacion(50, Recursos.golpe_agachado);

		// air attacks
		ataque_A_G = new Animacion(100, Recursos.punio_aereo);
		ataque_A_H = new Animacion(100, Recursos.punio_abajo);
		ataque_A_B = new Animacion(100, Recursos.patada_aerea);		

		// hurting anim
		agachado_G   = new Animacion(100, Recursos.soportar_golpe_atras);
	}

	@Override
	public void tick() {

		// efecto movimiento en la espera
		esperando.tick();
		agacharse.tick();

		// actualizar animaciones
		parar_b.tick();		
		parar_f.tick();	


		// actualizar animaciones de ataques
		if (animacion[ATAQUE_G])
			ataque_G.tick();
               	if (animacion[ATAQUE_H])
			ataque_H.tick();

		if (animacion[ATAQUE_B])
			ataque_B.tick();

		if (animacion[ATAQUE_N])
			ataque_N.tick();

		if (animacion[ATAQUE_C_G])
			ataque_C_G.tick();

		if (animacion[ATAQUE_A_G])
			ataque_A_G.tick();

		if (animacion[ATAQUE_A_H])
			ataque_A_H.tick();

		if (animacion[ATAQUE_A_B])
			ataque_A_B.tick();

		// si esta a nivel del piso	
		if (y == 280) {
			// si presionamos a la izquierda
			if (juego.getKeyManager().izquierda && !juego.getKeyManager().arriba) {
				velX = -2;

				// restablecer e iniciar fiel al estado
				animacionGolpe(PARAR_L);
			}
			// si presiona hacia la derecha, avanza
			else if (juego.getKeyManager().derecha && !juego.getKeyManager().arriba) {
				velX = 2;

				// restablecer e iniciar fiel al estado
				animacionGolpe(PARAR_R);
			} 
			// si presiona solo hacia arriba
			else if (juego.getKeyManager().arriba && !juego.getKeyManager().derecha && !juego.getKeyManager().izquierda && !animacion[SALTAR]) {
				// salto
				velY = JUMP_SPEED - 2;
				y-=1;
				saltar.index = 0;
			}
			// si presiona hacia arriba, a la derecha
			else if (juego.getKeyManager().arriba && juego.getKeyManager().derecha && !juego.getKeyManager().izquierda && !animacion[SALTO_FRENTE]) {
				//saltar en diagonal hacia la derecha
				velY = JUMP_SPEED;
				velX = 2;
				y-=1;
				salto_adelante.index = 0;				
			}
			// si presiona arriba, izquierda
			else if (juego.getKeyManager().arriba && !juego.getKeyManager().derecha && juego.getKeyManager().izquierda && !animacion[SATO_ATRAS]) {
				// saltar en diagonal hacia la izquierda
				velY = JUMP_SPEED;
				velX = -2;
				y-=1;
				salto_atras.index = 0;	
			}
			// si presiona hacia abajo, agáchese
			else if (!juego.getKeyManager().derecha && !juego.getKeyManager().izquierda && juego.getKeyManager().abajo){

				// detenerse
				velX = 0;
				velY = 0;

				// solo activar agacharse
				animacionGolpe(AGACHAR);

				// si presiona g mientras está agachado
				if (juego.getKeyManager().G) {
					velX = 0;
					velY = 0;

					// para perforar marcos, desactivar agacharse
					animacion[AGACHAR] = false;

					//restablecer e iniciar fiel al estado,ataque
					if (comprobarSiFuncionando())
						animacionGolpe(ATAQUE_C_G);

				}

				// eproducir la animación completa una vez y luego reiniciar
				resetAnim(ataque_C_G, ATAQUE_C_G);

			}
			// si g y no agachado
			else if (juego.getKeyManager().G && !animacion[AGACHAR]){
				velX = 0;
				velY = 0;

                                    // restablecer e iniciar fiel al estado, golpear
				if (comprobarSiFuncionando())
					animacionGolpe(ATAQUE_G);
			} else if (juego.getKeyManager().H && !animacion[AGACHAR]){
				velX = 0;
				velY = 0;

				// reinicio y golpe rápido
				if (comprobarSiFuncionando())
					animacionGolpe(ATAQUE_H);
			} else if (juego.getKeyManager().B && !animacion[AGACHAR]){
				velX = 0;
				velY = 0;

				// reiniciar y patear
				if (comprobarSiFuncionando())
					animacionGolpe(ATAQUE_B);
			} else if (juego.getKeyManager().N && !animacion[AGACHAR]){
				velX = 0;
				velY = 0;

				// reinicio y patada rápida
				if (comprobarSiFuncionando())
					animacionGolpe(ATAQUE_N);

				// De lo contrario, inactivo
			} else {

				velX = 0;
				velY = 0;

				// restablecer animaciones que son instantáneas (solo se activan cuando se presionan)
				animacion[AGACHAR] = false;
				animacion[PARAR_L] = false;
				animacion[PARAR_R] = false;
				animacion[SALTAR] = false;
				animacion[SALTO_FRENTE] = false;
				animacion[SATO_ATRAS] = false;

				// if only dummy boolean is active, then idle
				if (animacion[DUMMY]) {
					animacionGolpe(AVANZAR);
				}	

			}
			// De lo contrario, el jugador está en el aire.
		} else {

			animacion[PARAR_R] = false;
			animacion[PARAR_L] = false;

			// actualizar marcos
			saltar.tick();
			salto_adelante.tick();
			salto_atras.tick();

			// si se mueve hacia la izquierda
			if (velX < 0) {
				// voltereta hacia atrás
				ataqueAereos(salto_atras, SATO_ATRAS);
				// si se mueve hacia la derecha
			} else if (velX > 0) {
				// salto mortal hacia delante
				ataqueAereos(salto_adelante, SALTO_FRENTE);
				// sino
			} else {
				// saltar verticalmente
				ataqueAereos(saltar, SALTAR);
			}
		}

		// Si atacas, deja de moverte.
		if (animacion[ATAQUE_G] || animacion[ATAQUE_H] || animacion[ATAQUE_B] || animacion[ATAQUE_N]) {
			velX = 0;
			velY = 0;
		}

		// restablecer ataques terrestres
		resetAnim(ataque_G, ATAQUE_G);
		resetAnim(ataque_H, ATAQUE_H);
		resetAnim(ataque_B, ATAQUE_B);
		resetAnim(ataque_N, ATAQUE_N);

		// restablecer los ataques aéreos
		resetAnim(ataque_A_G, ATAQUE_A_G);
		resetAnim(ataque_A_H, ATAQUE_A_H);
		resetAnim(ataque_A_B, ATAQUE_A_B);

		// si en el suelo..
		if (y == 280) {
			//si ataca..
			if (animacion[ATAQUE_A_G] || animacion[ATAQUE_A_H] || animacion[ATAQUE_A_B]) {
				animacion[ATAQUE_A_G] = false;
				animacion[ATAQUE_A_H] = false;
				animacion[ATAQUE_A_B] = false;
			}
		}

		// collisions
		colision();

		// if hurting...
		if (agachado) {	
			if (System.currentTimeMillis() - lastTimer > 400) {
				animacion[HERIDO] = false;
				agachado = false;
				lastTimer += 400;
			}
		}

		// update horizontal pos.
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

	public void colision() {

		// if rectangle of player collides with enemy rectangle
		if (juego.getEstadoJuego().getKenAttackBounds().intersects(getLimitesGolpe())) {

			// si no esta herido...
			if (!agachado) {
				animacionGolpe(HERIDO);
				lastTimer = System.currentTimeMillis();
				agachado = true;
				vida-=2;//2
			}

			// freeze..

			try {
				TimeUnit.MILLISECONDS.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} 

	}

	@Override
	public void render(Graphics g) {

		// gráficos 2d para transformaciones
		Graphics2D g2d = (Graphics2D) g;	

		// cuando es golpeado,  aleatoriamente
		if (agachado) {
			int k = rand.nextInt(3);	
			g2d.translate(-k, k);
		}


		// dibujar sombra
		g.setColor(new Color(0,0,0, 125));
		g.fillOval((int) x - 4, 188 * Juego.SCALE, 64, 16);

		// dibujo ryu

		if (animacion[PARAR_R])
			g.drawImage(getCurrentAnimFrame(), (int) (x - 9), (int) (y - 3), null);	

		else if (animacion[PARAR_L])
			g.drawImage(getCurrentAnimFrame(), (int) (x - 4), (int) y, null);	

		else if (animacion[AGACHAR])
			g.drawImage(getCurrentAnimFrame(), (int) x, (int) y + 36, null);	

		else if (animacion[SALTAR])
			g.drawImage(getCurrentAnimFrame(), (int) x - 3, (int) y - 20, null);

		else if (animacion[SATO_ATRAS])
			g.drawImage(getCurrentAnimFrame(), (int) x - 15, (int) y - 15, null);

		else if (animacion[SALTO_FRENTE])
			g.drawImage(getCurrentAnimFrame(), (int) x - 15, (int) y - 15, null);

		else if (animacion[ATAQUE_G])
			g.drawImage(getCurrentAnimFrame(), (int) x, (int) (y + 3), null);

		else if (animacion[ATAQUE_H])
			g.drawImage(getCurrentAnimFrame(), (int) x, (int) (y + 3), null);

		else if (animacion[ATAQUE_B])
			g.drawImage(getCurrentAnimFrame(), (int) x, (int) (y - 3), null);

		else if (animacion[ATAQUE_N])
			g.drawImage(getCurrentAnimFrame(), (int) x, (int) (y - 4), null);

		else if (animacion[ATAQUE_C_G])
			g.drawImage(getCurrentAnimFrame(), (int) x, (int) y + 37, null);	

		else if (animacion[ATAQUE_A_G])
			g.drawImage(getCurrentAnimFrame(), (int) x, (int) y - 10, null);

		else if (animacion[ATAQUE_A_H])
			g.drawImage(getCurrentAnimFrame(), (int) x, (int) y - 10, null);

		else if (animacion[ATAQUE_A_B])
			g.drawImage(getCurrentAnimFrame(), (int) x - 4, (int) y - 5, null);

		else if (animacion[HERIDO])
			g.drawImage(getCurrentAnimFrame(), (int) (x - 15), (int) (y + 1), null);	

		else 
			g.drawImage(getCurrentAnimFrame(), (int) x, (int) y, null);	

		// dibujar cuadros para colision

		/*		
                g.setColor(Color.WHITE);
		g.drawRect(getHitBounds().x, getHitBounds().y, getHitBounds().width, getHitBounds().height);

		g.setColor(Color.RED);
		g.drawRect(getAttackBounds().x, getAttackBounds().y, getAttackBounds().width, getAttackBounds().height);
                */

	}

	private BufferedImage getCurrentAnimFrame() {

		if (animacion[PARAR_L]) 
			return parar_b.getCurrentFrame();

		else if (animacion[PARAR_R])
			return parar_f.getCurrentFrame();

		else if (animacion[AGACHAR]) 
			return agacharse.getCurrentFrame();

		else if (animacion[SALTAR]) 
			return saltar.getCurrentFrame();

		else if (animacion[SALTO_FRENTE]) 
			return salto_adelante.getCurrentFrame();

		else if (animacion[SATO_ATRAS]) 
			return salto_atras.getCurrentFrame();

		else if (animacion[ATAQUE_G]) 
			return ataque_G.getCurrentFrame();

		else if (animacion[ATAQUE_H]) 
			return ataque_H.getCurrentFrame();

		else if (animacion[ATAQUE_B]) 
			return ataque_B.getCurrentFrame();

		else if (animacion[ATAQUE_N]) 
			return ataque_N.getCurrentFrame();

		else if (animacion[ATAQUE_C_G]) 
			return ataque_C_G.getCurrentFrame();

		else if (animacion[ATAQUE_A_G]) 
			return ataque_A_G.getCurrentFrame();

		else if (animacion[ATAQUE_A_H]) 
			return ataque_A_H.getCurrentFrame();

		else if (animacion[ATAQUE_A_B]) 
			return ataque_A_B.getCurrentFrame();

		else if (animacion[HERIDO]) 
			return agachado_G.getCurrentFrame();

		else return esperando.getCurrentFrame();

	}

	public Rectangle getLimitesGolpe() {

		if (animacion[AGACHAR])
			return new Rectangle((int) x, (int) y + 30, 60, 80);	
		else if (animacion[ATAQUE_C_G])
			return new Rectangle((int) x, (int) y + 30, 60, 80);	


		return new Rectangle((int) x, (int) y, 60, 110);		
	}

	public Rectangle getLimitesAtaque() {

		// add specialized hitbox for each individual attack

		if (animacion[ATAQUE_G] && ataque_G.index == 2)
			return new Rectangle((int) x + 40, (int) y + 10, 60, 30);

		if (animacion[ATAQUE_H] && ataque_H.index == 2)
			return new Rectangle((int) x + 40, (int) y + 10, 60, 30);

		if (animacion[ATAQUE_B] && ataque_B.index >= 4 && ataque_B.index <= 6)
			return new Rectangle((int) x + 60, (int) y, 60, 50);

		if (animacion[ATAQUE_N] && ataque_N.index >= 3 && ataque_N.index <= 4)
			return new Rectangle((int) x + 60, (int) y + 50, 60, 50);

		if (animacion[ATAQUE_C_G] && ataque_C_G.index >= 0 && ataque_C_G.index <= 1)
			return new Rectangle((int) x + 30, (int) y + 40, 60, 30);

		if (animacion[ATAQUE_A_G] && ataque_A_G.index >= 2 && ataque_A_G.index <= 3)
			return new Rectangle((int) x + 30, (int) y + 20, 60, 50);

		if (animacion[ATAQUE_A_H] && ataque_A_H.index >= 0 && ataque_A_H.index <= 1)
			return new Rectangle((int) x + 30, (int) y + 20, 60, 50);

		if (animacion[ATAQUE_A_B] && ataque_A_B.index >= 2 && ataque_A_B.index <= 3)
			return new Rectangle((int) x + 40, (int) y + 40, 60, 30);

		return new Rectangle((int) x, (int) y, 0, 0);
	}

	public void animacionGolpe(int unchanged){

		// llenar el array de falsos
		for (int i = 0; i < 19; i++) {
			animacion[i] = false;
		}

		// except active anim
		animacion[unchanged] = true;
	}

	public void ataqueAereos(Animacion anim, int index) {

		// si g, h, b mientras está en el aire... establezca todos los anims en falso excepto llamado anim
		if (juego.getKeyManager().G) {
			animacionGolpe(ATAQUE_A_G);
		} else if (juego.getKeyManager().H) {
			animacionGolpe(ATAQUE_A_H);
		} else if (juego.getKeyManager().B) {
			animacionGolpe(ATAQUE_A_B);
		} else if (comprobarSiFuncionando()){
			// update anim and set all to false
			anim.tick();
			animacionGolpe(index);
		}

	}

	public boolean comprobarSiFuncionando() {

		// counter
		int i = 0;

		// traverse through anims
		for (boolean b : animacion) {
			// if false, increment counter
			if (b == false)
				i++;
		}

		// if all anims are false, return false
		if (i == 19) {
			return false;
		}

		// otherwise, true
		return true;
	}

	public void resetAnim(Animacion anim, int frame) {
		// if called anim is played once...
		if (anim.hasPlayedOnce()) {			
			// set anim to false
			anim.setPlayed();
			animacion[frame] = false;
		}

	}

	// GETTERS: 

	// get hp
	public int getVida() {
		return vida;
	}

	// get x	
	public int getRyuX() {
		return (int) x;
	}

}
