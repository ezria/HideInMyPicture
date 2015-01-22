package Objet;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.util.ArrayList;

public class Image {
	private BufferedImage buffImage;
	private Raster r;
	private ColorModel cm;
	private ArrayList<Pixel> pixels = null;
	
	public Image (BufferedImage buffImage){
		this.buffImage = buffImage;
		r = this.buffImage.getData();
		System.out.println(buffImage.getRGB(0, 0));
		cm = this.buffImage.getColorModel(); 
	}

	public BufferedImage getBuffImage() {
		return buffImage;
	}

	public void setBuffImage(BufferedImage buffImage) {
		this.buffImage = buffImage;
	}

	public Raster getR() {
		return r;
	}

	public void setR(Raster r) {
		this.r = r;
	}

	public ColorModel getCm() {
		return cm;
	}

	public void setCm(ColorModel cm) {
		this.cm = cm;
	}
	
	public ArrayList<Pixel> getPixels(){
		if (pixels == null){
			pixels = new ArrayList<Pixel>();
			for (int x = 0 ; x < r.getWidth() ; x++){ 
				for (int y = 0 ; y < r.getHeight() ; y++){ 
					Object objCouleur   = r.getDataElements(x, y, null);
					pixels.add(new Pixel(x, y, new Couleur(cm.getRed(objCouleur), cm.getGreen(objCouleur), cm.getBlue(objCouleur))));
				}	
			}
		}
		return pixels;
		
	}
	public void setPixels(ArrayList<Pixel> pixels){
		this.pixels=pixels;
		for (int x = 0 ; x < r.getWidth() ; x++){ 
			for (int y = 0 ; y < r.getHeight() ; y++){ 
				
				buffImage.setRGB(x, y, this.pixels.get(y*r.getWidth()+x).getCouleurPixel().convertRGBIntoInt());
				System.out.print(this.pixels.get(y*r.getWidth()+x).getCouleurPixel().convertRGBIntoInt());
				System.out.print(buffImage.getRGB(x, y));
			}	
		}
	}
}
