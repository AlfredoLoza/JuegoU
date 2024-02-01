package grafico;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;

public class CargarImagen {

	/**
	 * @param path
	 * 		file location of image
	 * @return
	 * 		returns a BufferedImage
	 */
    
      
	public static BufferedImage loadImage(String path) {
             BufferedImage sprite = null;
		try {
                   // C:\Users\Matrix\Documents\NetBeansProjects\JuegoUnap
                   // System.out.println("C:/Users/Matrix/Documents/NetBeansProjects/Game/"+path);
                   System.out.println("ruta::::::"+System.getProperty("user.dir")+path);
                    //ImageLoader.class.getResource(path)); 
                      //map = ImageIO.read(getClass().getResource("/textures/ken/crouch.png));
                     // sprite = ImageIO.read(new File(    "C:/Users/Matrix/Documents/NetBeansProjects/JuegoUnap/"+path)); //carga ok
                     sprite = ImageIO.read(new File( System.getProperty("user.dir")+path));
                     
                     // System.out.println("fin cargado"+path);
                    //ImageLoader.class.getResource("/textures/ken/crouch.png");
//                    System.out.println(map); // Prints null
                   // sprite = ImageIO.read(ImageLoader.class.getResource(path));
                     
			//return ImageIO.read(ImageLoader.class.getResource("/textures/ken/crouch.png"));
                      //  return ImageIO.read(new File("C:/Users/Matrix/Documents/NetBeansProjects/Game/textures/ken/crouch.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
                System.out.println(sprite); // Prints null
		return sprite;
                 
                  
	}
	
}
