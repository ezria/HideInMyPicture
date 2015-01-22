package Objet;

public class Couleur {
	private int r, g, b;
	private int [] rgb;
	
	public Couleur(int r, int g, int b) {
		super();
		this.r = r;
		this.g = g;
		this.b = b;
		this.rgb = new int[3];
		rgb[0] = r;
		rgb[0] = g;
		rgb[0] = b;
	}

	public int getR() {
		return r;
	}
	
	public void setR(int r) {
		this.r = r;
	}
	
	public int getG() {
		return g;
	}
	
	public void setG(int g) {
		this.g = g;
	}
	
	public int getB() {
		return b;
	}
	
	public void setB(int b) {
		this.b = b;
	}
	
	public int[] getRgb() {
		return rgb;
	}
	
	public void setRgb(int[] rgb) {
		this.rgb = rgb;
	}
	
	public boolean equalRgb(int[] rgb) {
		boolean sameRGB = true;
		int i = 0;
		while(i < 3 && sameRGB){		
			if (rgb[i] != this.rgb[i])
				sameRGB = false;
			i++;
		}
		
		return sameRGB;
	}
	public boolean equalRgb(Couleur c) {
		boolean sameRGB = true;
		int i = 0;
		while(i < 3 && sameRGB){		
			if (c.getRgb()[i] != this.rgb[i])
				sameRGB = false;
			i++;
		}
		
		return sameRGB;
	}
	public int convertRGBIntoInt(){
		int resultat= -16777216;
		resultat += r*65536;
		resultat+= g*256;
		resultat+=b;
		return resultat;
	}
	public String toString(){
		return " r = " + r + " g = " + g + " b = " + b; 
 	}
}
