package Objet;


public class Pixel {
	private int x, y;
	private Couleur couleurPixel;
	
	public Pixel() {
		super();
	}
	
	public Pixel(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public Pixel(int x, int y, Couleur couleurPixel) {
		super();
		this.x = x;
		this.y = y;
		this.couleurPixel = couleurPixel;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public Couleur getCouleurPixel() {
		return couleurPixel;
	}
	
	public void setCouleurPixel(Couleur couleurPixel) {
		this.couleurPixel = couleurPixel;
	}
	
}
