package Objet;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;

public class Image {
	private BufferedImage buffImage;
	private Raster r;
	private ColorModel cm;
	
	public Image (BufferedImage buffImage){
		this.buffImage = buffImage;
		r = this.buffImage.getData();
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
}
