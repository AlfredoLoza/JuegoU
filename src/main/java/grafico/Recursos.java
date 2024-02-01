package grafico;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Recursos {

	
	//  SPRITE RYU ============================================
		
	// movimientos basicos
	public static BufferedImage[] 	esperando         = new BufferedImage[6], 
					parado_f         = new BufferedImage[8], 
					parado_b         = new BufferedImage[8],
					agachase         = new BufferedImage[1];

	// golpes a nivel del piso
	public static BufferedImage[] 	punio           = new BufferedImage[6], 
					golpe_rapido    = new BufferedImage[3],
					golpe_agachado  = new BufferedImage[3],
					ataque_agachado = new BufferedImage[8],
					corte_superior  = new BufferedImage[8];

	// patadas en el piso
	public static BufferedImage[]   patada_baja        = new BufferedImage[5],
					patada_arriba      = new BufferedImage[9];

	// ataques aereos
	public static BufferedImage[]   punio_aereo       = new BufferedImage[6],
					patada_aerea        = new BufferedImage[5],
					punio_abajo      = new BufferedImage[4];
	// saltos
	public static BufferedImage[]   voltereta_atras       = new BufferedImage[8],
					salto_mortal      = new BufferedImage[8],
					saltar            = new BufferedImage[11];

	// Herido
	public static BufferedImage[]   golpe_abajo      = new BufferedImage[2],
					golpe_abajo_atras = new BufferedImage[4],
					soportar_golpe       = new BufferedImage[2],
					soportar_golpe_atras  = new BufferedImage[4],
					caerse_atras       = new BufferedImage[4],
					recuperarse         = new BufferedImage[5];
	// misc.
	public static BufferedImage[]   ganar             = new BufferedImage[10],
					morir            = new BufferedImage[1];
	

	// ============================================ SPRITE SHEETS: KEN ============================================

	
	// movimientos basicoa ken
	public static BufferedImage[] 	esperando1            = new BufferedImage[6], 
					parado_f1         = new BufferedImage[8], 
					parado_b1         = new BufferedImage[8],
					agacharse1          = new BufferedImage[1];


// ground attack
	public static BufferedImage[] 	punio1           = new BufferedImage[8], 
                                        golpe_rapido1     = new BufferedImage[4],
                                        golpe_agachado1    = new BufferedImage[3],
                                        ataque_agachado1   = new BufferedImage[5],
                                        corte_superior1        = new BufferedImage[8];

	// ground kick
	public static BufferedImage[]   patada_baja1        = new BufferedImage[5],
                                        patada_arriba1      = new BufferedImage[10];

	// air attack
 
        
	public static BufferedImage[]   punio_aereo1       = new BufferedImage[7],
                                        patada_aerea1        = new BufferedImage[6],
                                        punio_abajo1      = new BufferedImage[4];
	// jumps
	public static BufferedImage[]   voltereta_atras1       = new BufferedImage[8],
                                        salto_mortal1      = new BufferedImage[8],
                                        saltar1            = new BufferedImage[11];

	// hurt
	public static BufferedImage[]   golpe_abajo1      = new BufferedImage[2],
                                        golpe_abajo_atras1 = new BufferedImage[4],
                                        soportar_golpe1       = new BufferedImage[2],
                                        soportar_golpe_atras1  = new BufferedImage[4],
                                        caerse_atras1       = new BufferedImage[4],
                                        recuperarse1         = new BufferedImage[5];
	// misc.
	public static BufferedImage[]   ganar1             = new BufferedImage[10],
                                        morir1            = new BufferedImage[1];
	

	
	/**
	 * @description 
	 * 		pre-loads sprite sheet and crops the image as it stores into BufferedImage array
	 */

	public static void init() throws IOException {

		// ============================================ SPRITE SHEETS: RYU ============================================
               // BufferedImage bi = ImageIO.read(new File("textures\\ryu\\crouch.png"));
                //System.out.println(bi);
               
                Imagen ss_agachado          = new Imagen(CargarImagen.loadImage("\\textures\\ryu\\agachado.png"));
		Imagen ss_golpe_agachado    = new Imagen(CargarImagen.loadImage("\\textures\\ryu\\golpe_agachado.png"));
		Imagen ss_golpeado_atras  = new Imagen(CargarImagen.loadImage("\\textures\\ryu\\golpeado_atras.png"));
		Imagen ss_esparando            = new Imagen(CargarImagen.loadImage("\\textures\\ryu\\esparando.png"));
		Imagen ss_patada_baja        = new Imagen(CargarImagen.loadImage("\\textures\\ryu\\patada_baja.png"));
		Imagen ss_parar_atras      = new Imagen(CargarImagen.loadImage("\\textures\\ryu\\parar_atras.png"));
		Imagen ss_parar_adelante     = new Imagen(CargarImagen.loadImage("\\textures\\ryu\\parar_adelante.png"));
		Imagen ss_puniete           = new Imagen(CargarImagen.loadImage("\\textures\\ryu\\puniete.png"));
		Imagen ss_puniete_rapido     = new Imagen(CargarImagen.loadImage("\\textures\\ryu\\puniete_rapido.png"));
		Imagen ss_patada_giratoria      = new Imagen(CargarImagen.loadImage("\\textures\\ryu\\patada_giratoria.png"));
		Imagen ss_salto	           = new Imagen(CargarImagen.loadImage("\\textures\\ryu\\salto.png"));
		Imagen ss_salto_mortal_adelante      = new Imagen(CargarImagen.loadImage("\\textures\\ryu\\salto_mortal_adelante.png"));
		Imagen ss_salto_mortal_atras       = new Imagen(CargarImagen.loadImage("\\textures\\ryu\\salto_mortal_atras.png"));
		Imagen ss_puniete_volador       = new Imagen(CargarImagen.loadImage("\\textures\\ryu\\puniete_volador.png"));
		Imagen ss_patada_voladora        = new Imagen(CargarImagen.loadImage("\\textures\\ryu\\patada_voladora.png"));
		Imagen ss_puniete_abajo      = new Imagen(CargarImagen.loadImage("\\textures\\ryu\\puniete_abajo.png"));

		// ============================================ SPRITE CROPPING ============================================

		// loops through frame slices and stores in array

		// basic movement:
		for (int i = 0; i < 6; i++) 
                {	
                    esperando[i] = ss_esparando.recortar(57, 106, 57 * i, 0);
                    /*try {
                        ImageIO.write(idle[i], "png", new File("E:\\img\\foto"+i+".jpg"));
                     } catch (IOException e) {
                        System.out.println("Error de escritura");
                     }
                */
                
                         // JOptionPane.showMessageDialog(null, "Hello World");
                }

		for (int i = 0; i < 8; i++) {
                    parado_f[i] = ss_parar_adelante.recortar(70, 110, 70 * i, 0);
                    try {
                        ImageIO.write(parado_f[i], "png", new File("E:\\img\\parar"+i+".jpg"));
                     } catch (IOException e) {
                        System.out.println("Error de escritura");
                     }
                }
			

		for (int i = 0; i < 8; i++) 
			parado_b[i] = ss_parar_atras.recortar(70, 108, 70 * i, 0);

		agachase[0] = ss_agachado.recortar(54, 73, 0, 0);
		saltar[0] = ss_salto.recortar(70, 154, 0, 0);
		saltar[1] = ss_salto.recortar(70, 154, 0, 0);
		saltar[2] = ss_salto.recortar(70, 154, 0, 0);
		
		for (int i = 3; i < 11; i++) 
			saltar[i] = ss_salto.recortar(70, 154, 70 * (i - 2), 0);
		
		
		for (int i = 0; i < 8; i++) 
			salto_mortal[i] = ss_salto_mortal_adelante.recortar(88, 129, 88 * i, 0);
		
		for (int i = 0; i < 8; i++) 
			voltereta_atras[i] = ss_salto_mortal_atras.recortar(88, 129, 88 * i, 0);
		
		// ground attack:
		for (int i = 0; i < 6; i++) 
			punio[i] = ss_puniete.recortar(101, 102, 101 * i, 0);

		for (int i = 0; i < 3; i++) 
			golpe_rapido[i] = ss_puniete_rapido.recortar(94, 102, 94 * i, 0);

		// crouch attack	
		for (int i = 0; i < 3; i++) 
			golpe_agachado[i] = ss_golpe_agachado.recortar(86, 72, 86 * i, 0);	
				
		// air attacks				
		for (int i = 0; i < 6; i++) 
			punio_aereo[i] = ss_puniete_volador.recortar(83, 95, 83 * i, 0);
		
		for (int i = 0; i < 4; i++) 
			punio_abajo[i] = ss_puniete_abajo.recortar(75, 90, 75 * i, 0);
		
		for (int i = 0; i < 5; i++) 
			patada_aerea[i] = ss_patada_voladora.recortar(99, 94, 99 * i, 0);
				
		// ground kick
		for (int i = 0; i < 5; i++) 
			patada_baja[i] = ss_patada_baja.recortar(115, 111, 115 * i, 0);

		for (int i = 0; i < 9; i++) 
			patada_arriba[i] = ss_patada_giratoria.recortar(110, 111, 110 * i, 0);

		// hurting anims
  		for (int i = 0; i < 4; i++) 
			soportar_golpe_atras[i] = ss_golpeado_atras.recortar(77, 104, 77 * i, 0);

		// ============================================ SPRITE SHEETS: KEN ============================================

                Imagen ss_agachado1          = new Imagen(CargarImagen.loadImage("\\textures\\ken\\agachado.png"));
		Imagen ss_golpe_agachado1    = new Imagen(CargarImagen.loadImage("\\textures\\ken\\golpe_agachado.png"));;
		Imagen ss_golpeado_atras1  = new Imagen(CargarImagen.loadImage("\\textures\\ken\\golpeado_atras.png"));
		Imagen ss_esparando1            = new Imagen(CargarImagen.loadImage("\\textures\\ken\\esperando.png"));
		Imagen ss_patada_baja1        = new Imagen(CargarImagen.loadImage("\\textures\\ken\\patada_baja.png"));
		Imagen ss_parar_atras1      = new Imagen(CargarImagen.loadImage("\\textures\\ken\\parar_atras.png"));
		Imagen ss_parar_adelante1     = new Imagen(CargarImagen.loadImage("\\textures\\ken\\parar_adelante.png"));
		Imagen ss_puniete1           = new Imagen(CargarImagen.loadImage("\\textures\\ken\\puniete.png"));
		Imagen ss_puniete_rapido1     = new Imagen(CargarImagen.loadImage("\\textures\\ken\\puniete_rapido.png"));
		Imagen ss_patada_giratoria1      = new Imagen(CargarImagen.loadImage("\\textures\\ken\\patada_giratoria.png"));
		Imagen ss_salto1		= new Imagen(CargarImagen.loadImage("\\textures\\ken\\salto.png"));
		Imagen ss_salto_mortal_adelante1      = new Imagen(CargarImagen.loadImage("\\textures\\ken\\salto_mortal_adelante.png"));
		Imagen ss_salto_mortal_atras1       = new Imagen(CargarImagen.loadImage("\\textures\\ken\\salto_mortal_atras.png"));
		Imagen ss_puniete_volador1       = new Imagen(CargarImagen.loadImage("\\textures\\ken\\puniete_volador.png"));
		Imagen ss_patada_voladora1        = new Imagen(CargarImagen.loadImage("\\textures\\ken\\patada_voladora.png"));
		Imagen ss_puniete_abajo1      = new Imagen(CargarImagen.loadImage("\\textures\\ken\\puniete_abajo.png"));

		// ============================================ SPRITE CROPPING ============================================

		// loops through frame slices and stores in array ...

		// basic movement:

		for (int i = 0; i < 6; i++) 
			esperando1[i] = ss_esparando1.recortar(57, 106, 57 * i, 0);

		for (int i = 0; i < 8; i++) 
			parado_f1[i] = ss_parar_adelante1.recortar(70, 110, 70 * i, 0);

		for (int i = 0; i < 8; i++) 
			parado_b1[i] = ss_parar_atras1.recortar(70, 110, 70 * i, 0);

		agacharse1[0] = ss_agachado1.recortar(54, 73, 0, 0);

		// aerial moves 
		
		saltar1[0] = ss_salto1.recortar(61, 124, 0, 0);
		saltar1[1] = ss_salto1.recortar(61, 124, 0, 0);
		saltar1[2] = ss_salto1.recortar(61, 124, 0, 0);
		
		for (int i = 3; i < 11; i++) 
			saltar1[i] = ss_salto1.recortar(61, 124, 61 * (i - 2), 0);
		
	
		for (int i = 0; i < 8; i++) 
			salto_mortal1[i] = ss_salto_mortal_adelante1.recortar(83, 125, 83 * i, 0);
		
		for (int i = 0; i < 8; i++) 
			voltereta_atras1[i] = ss_salto_mortal_atras1.recortar(83, 125, 83 * i, 0);
		
		// ground attacks

		for (int i = 0; i < 8; i++) 
			punio1[i] = ss_puniete1.recortar(103, 103, 103 * i, 0);

		for (int i = 0; i < 4; i++) 
			golpe_rapido1[i] = ss_puniete_rapido1.recortar(95, 102, 95 * i, 0);

		for (int i = 0; i < 3; i++) 
			golpe_agachado1[i] = ss_golpe_agachado1.recortar(89, 72, 89 * i, 0);

		// ground kick

		for (int i = 0; i < 5; i++) 
			patada_baja1[i] = ss_patada_baja1.recortar(118, 105, 118 * i, 0);

		for (int i = 0; i < 10; i++) 
			patada_arriba1[i] = ss_patada_giratoria1.recortar(135, 108, 135 * i, 0);
		
		// air attacks				
		for (int i = 0; i < 7; i++) 
			punio_aereo1[i] = ss_puniete_volador1.recortar(84, 95, 84 * i, 0);
				
		for (int i = 0; i < 4; i++) 
			punio_abajo1[i] = ss_puniete_abajo1.recortar(68, 88, 68 * i, 0);
				
		for (int i = 0; i < 6; i++) 
			patada_aerea1[i] = ss_patada_voladora1.recortar(106, 83, 106 * i, 0);

		// hurt
		
		for (int i = 0; i < 4; i++) 
			soportar_golpe_atras1[i] = ss_golpeado_atras1.recortar(79, 104, 79 * i, 0);

	}
}
