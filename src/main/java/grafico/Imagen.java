package grafico;

import java.awt.image.BufferedImage;

public class Imagen {
	
	private BufferedImage recorte;
	
	/**
	 * @param recorte
	 * send image to be cropped
	 */
	
	public Imagen(BufferedImage recorte) {
		this.recorte = recorte;
	}
	
	/**
	 * @param width
	 * 		get width of subimage
	 * @param height
	 * 		get height of subimage
	 * @param x
	 * 		x position of subimage
	 * @param y
	 * 		y position of subimage
	 * @return
	 * 		returns a BufferedImage to calling class
	 */
	
	public BufferedImage recortar(int width, int height, int x, int y) {
		return recorte.getSubimage(x, y, width, height);
	}

}
