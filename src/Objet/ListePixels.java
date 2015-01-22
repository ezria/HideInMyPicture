package Objet;

import java.util.ArrayList;
import java.util.HashMap;

public class ListePixels {


	private HashMap<Integer,HashMap<Integer, Pixel>> hashPixels;
	private ArrayList<Pixel>pixels;
	
	
	public ListePixels() {
		hashPixels = new HashMap<Integer,HashMap<Integer, Pixel>>();
		this.pixels = new ArrayList<Pixel>();
	}
	public ListePixels(ArrayList<Pixel> pixels) {
		super();
		hashPixels = new HashMap<Integer,HashMap<Integer, Pixel>>();
		this.pixels = new ArrayList<Pixel>();
		for (Pixel p : pixels){
			ajoutePixel(p);
		}
	}

	public void ajoutePixel(Pixel pixel){
		if (hashPixels.get(pixel.getX()) ==  null)
			hashPixels.put(pixel.getX(), new HashMap<Integer, Pixel>());
		
		hashPixels.get(pixel.getX()).put(pixel.getY(), pixel);
		pixels.add(pixel);
	}
	
	public Pixel recuperePixel(int x, int y){
		Pixel p = null;	

		if (hashPixels.get(x) != null && hashPixels.get(x).get(y) != null && pixels.contains(hashPixels.get(x).get(y))){
			p = hashPixels.get(x).get(y);
		}
		return p;
	}

	public Pixel recuperePixel(int i){
			return pixels.get(i);
}

	

	public void supprimePixel(int x, int y){
		if (pixels.contains(recuperePixel(x, y))){
			pixels.remove(pixels.indexOf(recuperePixel(x, y)));
			hashPixels.get(x).remove(y);
			if (hashPixels.get(x).size() == 0){
				hashPixels.remove(x);
			}
		}
	}

	public void supprimePixel(int i){
		pixels.remove(i);
	}

	public int taille(){
		return pixels.size();	
	}
	public HashMap<Integer,HashMap<Integer, Pixel>> getHashPixels() {
		return hashPixels;
	}
	public void setHashPixels(HashMap<Integer,HashMap<Integer, Pixel>> hashPixels) {
		this.hashPixels = hashPixels;
	}
	public ArrayList<Pixel> getPixels() {
		return pixels;
	}
	public void setPixels(ArrayList<Pixel> pixels) {
		this.pixels = pixels;
	}


}
