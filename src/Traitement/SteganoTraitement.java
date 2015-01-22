package Traitement;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


/**
 * 
 * @author corentin
 * Toolbox class allowing to make steganography on pictures.
 */
public abstract class SteganoTraitement {
	/**
	 * 
	 * @param image {@link BufferedImage} of 8 bit colors type image
	 * @param chaineAscii {@link String} what you desire to hide.
	 * @param key {@link String} If you want cipher it, you can with the algorithm of 
	 * @return {@link BufferedImage} the new picture totally changed, but you will never see what changed. 
	 */
	public static BufferedImage writeOnHeightBitColorsPicture(BufferedImage image, String chaineAscii, String key){
		//Convertion de la chaine Ascii en binaire, et chiffrement si clé présente auparavant.
		String chaineBinaire = convertAsciiToBinary((key==null||key.length()==0)?chaineAscii:(chiffrement(chaineAscii, key)));

		//Boucle permettant de parcourir caractère par caractère la chaine binaire.
		for (int i =0;i<chaineBinaire.length();i++){
			//Récuperation de la couleur du pixel. 
			//Calcul de la position du pixel:
			//Nous prenons le pixel se trouvant en i. Hors la chaine de caractère peut être plus longue que la largeur en pixel de l'image. 
			//Pour récupérer le bon pixel, nous divisons donc i par la largeur de l'image pour avoir la ligne à parcourir 
			//et récupérons le reste pour savoir le numéro du pixel sur la ligne.
			
			//Récuperation de la couleur
			//La couleur est enregistrer en binaire signé sur JAVA et nous donne un entier négatif avec comme plus petite valeur - 16777216
			//Nous passons cette valeur à 0 afin de simplifier le traitement ensuite.
			//En niveau de Gris les couleurs RGB on les mêmes valeurs. Java considère la couleur sur 24bit même si elle n'est inscrite que sur 8.
			//Donc le bit de poid faible pour le rouge est egale à 2^16, 2^8 pour le vert et 2^0 pour le bleu.
			//Donc si on doit inscrire un 1 sur le dernier bit, il faudra le faire pour le rouge (65536), le vert(256) et le bleu(1). 
			//On ajoutera donc à la couleur 65536+256+1 si on doit inscrire 1 et que le chiffre est impaire. 
			//inversement on supprimera 65536+256+1 si la couleur est paire alors que l'on souhaite inscrire un 0.
			
			int couleur = (image.getRGB(i%image.getWidth(), i/image.getWidth())) + 16777216;
			if(Integer.parseInt(chaineBinaire.charAt(i)+"")!= couleur%2){
				if (chaineBinaire.charAt(i)==0)
					image.setRGB(i%image.getWidth(), i/image.getWidth(), couleur-16843009);
					image.setRGB(i%image.getWidth(), i/image.getWidth(), couleur-16711423);
			}

		}
		//ici on inscrit la chaine alertant le logiciel lors de la lecture que notre message est terminé. Pour cela, nous avons décidé d'ajouter en fin de chaine 
		//la chaine de binaire 0000 0000 1111 1111.
		for (int i = chaineBinaire.length(); i< chaineBinaire.length()+8;i++){
			int couleur = (image.getRGB(i%image.getWidth(), i/image.getWidth())) + 16777216;
			if(couleur%2!=0)
				image.setRGB(i%image.getWidth(), i/image.getWidth(), couleur-16777216);
		}
		for (int i = chaineBinaire.length()+8; i< chaineBinaire.length()+16;i++){
			int couleur = (image.getRGB(i%image.getWidth(), i/image.getWidth())) + 16777216;
			if(couleur%2!=1)
				image.setRGB(i%image.getWidth(), i/image.getWidth(), couleur-16711423);
		}
		return image;
	}
	/**
	 * 
	 * @param image {@link BufferedImage} of 8 bit colors type image
	 * @param key {@link String} If you want decipher it, but be sure it's the good one, otherwise the message will be unreadable.
	 * @return {@link String} Return the hidden message if it exist and the key is the right one.
	 */
	public static String readOnHeightBitColorsPicture(BufferedImage image, String key){
		//creation of the returned variable.
		String chaineBinaire = "";
		int y=0;
		//while the returned variable doesn't contains the end combination or it didn't reach the end of the line of pixels.
		while(!chaineBinaire.contains("0000000011111111")&&y!=image.getHeight()){
			int x=0;
			//while the returned variable doesn't contains the end combination or it didn't reach the end of the row of the line of pixels.
			while(!chaineBinaire.contains("0000000011111111")&&x!=image.getWidth()){
				//for the same reason as in the writing method, we had 256 to the color of the pixel.
				int couleur=image.getRGB(x,y)+256;
				//we get the last bit of the color because it's the bit of parity
				chaineBinaire+=couleur%2+"";
				x++;
			}	
			y++;
		}
		//we deleted the 8 last char of the string because they are the closing combination of the writing method.
		chaineBinaire = chaineBinaire.substring(0, chaineBinaire.length()-16);	
		return (key==null||key.length()==0)?convertBinaryToAscii(chaineBinaire):dechiffrement(convertBinaryToAscii(chaineBinaire),key);
	}
	
	/**
	 * 
	 * @param image {@link BufferedImage} of 24 bit colors type image
	 * @param chaineAscii {@link String} what you desire to hide.
	 * @param key {@link String} If you want cipher it, you can with the algorithm of 
	 * @return {@link BufferedImage} the new picture totally changed, but you will never see what changed. 
	 */
	public static BufferedImage writeOnTwentyFourBitColorsPicture(BufferedImage image, String chaineAscii, String key){
		
		//chaineimagenaire prend la valeur de la chaine transformé en imagenaire si la clé est null 
		//ou sa taille est égale 0 sinon la chaineAscii est chiffré puis transformé en imagenaire
		if(key!=null&&key.length()!= 0){
			chaineAscii=chiffrement(chaineAscii, key);
		}
		String chaineimagenaire = convertAsciiToBinary(chaineAscii);	
		//Boucle d'insription des données dans l'image.
		for (int i =0;i<chaineimagenaire.length();i++){
			
			//Explication pour la couleur: La couleur récuperée est inscrite sur un entier signé. De fait si toute les couleurs sont à zero, en noir donc, le chiffres sera -16777216 et -1 pour le blanc.
			
			//afin de traiter cette donnée, nous la passons en équivalent à un binaire non signé. Nous lui additionnons donc 16777216.
			
			//La couleur rouge étant inscrite en premiere elle est donc inscrite sur les bit 1 à 8. En récuperer l'entier de la couleur/65536, nous récuperons donc la couleur rouge sur 8 bit (compris entre 0 et 255).
			//Le principe est le même pour le vert, nous le récuperons en divisant le reste de la division par 65536 par 256. 
			//Pour le bleue, nous n'avions pas de calcule complexe puisque seule le bit de parité est modifié. 
			
			//Le pixel récupéré, du fait que nous changeons les trois couleurs pour chaque pixel, nous divisons l'index par trois. Pour x, nous récuperons de reste de la divisions de l'index par le nombre de pixel sur une ligne. 
			//Pour y nous divisons l'index par trois que nous redivisons 
			int couleur = image.getRGB((i/3)%image.getWidth(), (i/3)/image.getWidth()) + 16777216;

			//Nous avons désormé la couleur du pixel ainsi que ses coordonnées. Maintenant pour savoir quelle couleur nous devons modifier, nous récuperons le reste de la divisions par trois de l'index. 
			
			switch (i%3){
			//Dans le cas ou le reste vaut 0, nous modifierons la couleur rouge, 1 vert et 2 bleue
			case 0:
				//Nous vérifions si le modulo 2 de la couleur rouge n'est pas égale au caractère de la chaine de caractère. 
				if(Integer.parseInt(chaineimagenaire.charAt(i)+"")!= (couleur/65536)%2){
					//Si il ne l'est pas et que le caractère vaut 0 alors, nous enlevons à la couleur rouge (le calcul est simplifié en dessous en couleur - 16777216 - 65536)
					if (chaineimagenaire.charAt(i)==0)
						image.setRGB((i/3)%image.getWidth(), (i/3)/image.getWidth(), couleur-16842752);
					//Sinon, c'est que le caractère vaut 1 alors, nous ajoutons 1 à la couleur rouge (le calcul est simplifié en dessous en couleur - 16777216 + 65536)
					else
						image.setRGB((i/3)%image.getWidth(), (i/3)/image.getWidth(), couleur-16711680);
				}
				break;
			case 1:
				//de même pour le vert
				if(Integer.parseInt(chaineimagenaire.charAt(i)+"")!= (couleur%65536/256)%2){
					//Si il ne l'est pas et que le caractère vaut 0 alors, nous enlevons à la couleur verte (le calcul est simplifié en dessous en couleur - 16777216 - 256)
					if (Integer.parseInt(chaineimagenaire.charAt(i)+"")==0)
						image.setRGB((i/3)%image.getWidth(), (i/3)/image.getWidth(), couleur-16777472);
					//Sinon, c'est que le caractère vaut 1 alors, nous ajoutons 1 à la couleur verte (le calcul est simplifié en dessous en couleur - 16777216 + 256)
					else
						image.setRGB((i/3)%image.getWidth(), (i/3)/image.getWidth(), couleur-16776960);
				}
				break;
			case 2:
				//ainsi que pour le bleu
				if(Integer.parseInt(chaineimagenaire.charAt(i)+"")!= couleur%2){
					//Si il ne l'est pas et que le caractère vaut 0 alors, nous enlevons à la couleur bleue (le calcul est simplifié en dessous en couleur - 16777216 - 1)
					if (Integer.parseInt(chaineimagenaire.charAt(i)+"")==0)
						image.setRGB((i/3)%image.getWidth(), (i/3)/image.getWidth(), couleur-16777217);
					//Sinon, c'est que le caractère vaut 1 alors, nous ajoutons 1 à la couleur bleue (le calcul est simplifié en dessous en couleur - 16777216 + 1)
					else
						image.setRGB((i/3)%image.getWidth(), (i/3)/image.getWidth(), couleur-16777215);
				}
				break;
			}
		}
		
		//Inscription de la combinaison de fin de message. 
		for (int i = chaineimagenaire.length(); i< chaineimagenaire.length()+8;i++){
			int couleur = image.getRGB((i/3)%image.getWidth(), (i/3)/image.getWidth()) + 16777216;
			switch (i%3){
			case 0:
				if((couleur/65536)%2!=0)
					image.setRGB((i/3)%image.getWidth(), (i/3)/image.getWidth(), couleur-16842752);
				break;
			case 1:
				if((couleur/256)%2!=0)
					image.setRGB((i/3)%image.getWidth(), (i/3)/image.getWidth(), couleur-16777472);
				break;
			case 2:
				if(couleur%2!=0)
					image.setRGB((i/3)%image.getWidth(), (i/3)/image.getWidth(), couleur-16777217);
				break;
			}
		}
		for (int i = chaineimagenaire.length()+8; i< chaineimagenaire.length()+16;i++){
			int couleur = image.getRGB((i/3)%image.getWidth(), (i/3)/image.getWidth()) + 16777216;
			switch (i%3){
			case 0:
				if((couleur/65536)%2!=1)
					image.setRGB((i/3)%image.getWidth(), (i/3)/image.getWidth(), couleur-16711680);
				break;
			case 1:
				if((couleur/256)%2!=1)
					image.setRGB((i/3)%image.getWidth(), (i/3)/image.getWidth(), couleur-16776960);
				break;
			case 2:
				if(couleur%2!=1)
					image.setRGB((i/3)%image.getWidth(), (i/3)/image.getWidth(), couleur-16777215);
				break;
			}
		}
		return image;
	}
	/**
	 * 
	 * @param image {@link BufferedImage} of 24 bit colors type image
	 * @param key {@link String} If you want decipher it, but be sure it's the good one, otherwise the message will be unreadable.
	 * @return {@link String} Return the hidden message if it exist and the key is the right one.
	 */
	public static String readOnTwentyFourBitColorsPicture(BufferedImage image, String key){
		// le principe est le même que pour les images 8bits, juste que nous rajoutons 3 bits par 3 bits puisque nous lisons pour chaque pixels la valeur RGB.
		String chaineBinaire = "";
		int y=0;
		while(!chaineBinaire.contains("0000000011111111")&&y!=image.getHeight()){
			int x=0;
			while(!chaineBinaire.contains("0000000011111111")&&x!=image.getWidth()){
				int couleur=image.getRGB(x,y)+16777216;
				chaineBinaire+=(couleur/65536)%2+""+((couleur%65536)/256)%2+""+couleur%2;
				x++;
			}	
			y++;
		}
		chaineBinaire = chaineBinaire.substring(0, chaineBinaire.length()-16);	
		return (key==null||key.length()==0)?convertBinaryToAscii(chaineBinaire):dechiffrement(convertBinaryToAscii(chaineBinaire),key);
	}
	
	private static String convertAsciiToBinary(String chaineAscii){
		String chaineBinaire="";
		// nous parcourons la chaine de caractère, caractère par caractère.
		for (char c : chaineAscii.toCharArray()){
			//nous initialisons la variable bit à 128 (car une lettre est inscrite sur 256 bit)
			int bit=128;
			//tant que la valeur du bit n'est pas égale à 0, nous continuons la boucle
			while(bit!=0){
				//la valeur inscrite dans la chaine sera le résultat de la division de la valeur entière du caractère par la valeur du bit.
				int val = ((int)c)/bit;
				chaineBinaire+=String.valueOf(val);
				//si la valeur entière du caractère est supérieur à la valeur du bit, on soustrait la valeur du bit à la valeur entière du caractère.
				if(val==1)c-=bit;

				//on divise par 2 la valeur du bit. bit prendra donc les valeurs suivantes (128-64-32-16-8-4-2-1-0)
				bit/=2;
			}
		}
		return chaineBinaire;
	}
	private static String convertBinaryToAscii(String chaineBinaire){
		String chaineAscii="";
		
		//parcours de la chaineBinaire, 8 bit par 8 bit (caractère par caractère).
		for (int i=0; i<chaineBinaire.length()/8; i++){
			//intialisation de la valeur du caractère en entier, et de la variable valant la position du bit.
			int bit=8;
			int valChar=0;
			//tant que bit est différent de 0 
			while(bit!=0){
				//on ajoute à la valeur du caractère la valeur du caractère à la position ((i+1)*8)-bit) 
				//(i+1)*8 permet de récuperer le dernier bit d'un caractère.
				//On supprime à cette valeur le numéro du bit entrain d'être lu par la boucle pour récuperer le bon bit.
				//on multiplie ce bit (qui vaut 1 ou 0) par 2^(bit-1)
				// valeur des bit 
				//			numero du bit	8	7	6	5	4	3	2	1
				//			valeur du bit	128	64	32	16	8	4	2	1
				//valeur en puissance de 2 	2^7	2^6	2^5	2^4	2^3	2^2	2^1	2^0	
				valChar+=Integer.parseInt(chaineBinaire.charAt(((i+1)*8)-bit)+"")*((int)Math.pow(2, --bit));
			}
			chaineAscii+=String.valueOf((char)valChar);
		}
		return chaineAscii;
	}
	public static boolean saveFile(File monFichier, BufferedImage buffImage, String format){
		try {
			ImageIO.write(buffImage, format, monFichier);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}  
		return true;
	}
	public static String chiffrement(String string, String cle){
	
		String stringChiffre="";
		//boucle de parcours de la chaine à chiffrer caractère par caractère 
		for(int i = 0 ; i<string.length(); i++)
			//on récupère la valeur du caractère de la chaine à chiffrer
			//on récupère la valeur du caractère de la clé. une fois que toute la clé a été utilisé pour chiffrer, on repart au premier caractère de la clé.
			//on additione les deux valeurs. Si la valeurs est plus grande que 255, on enlève 255.
			//on transforme le chiffre obtenu en caractère à partir de la table ascii.
			//on ajoute ce caractère à la chaine de retour
			stringChiffre+= (char)(((int)string.charAt(i)+(int)cle.charAt(i%cle.length()))%256);
		return stringChiffre;	
	}
	public static String dechiffrement(String string, String cle){
		String stringChiffre="";
		//Boucle pour parcourir tout les caractère de la chaine
		for(int i = 0 ; i<string.length(); i++){
			//on récupère la valeur du caractère de la chaine à chiffrer
			//on récupère la valeur du caractère de la clé. une fois que toute la clé a été utilisé pour chiffrer, on repart au premier caractère de la clé.
			//on soustrait la valeur du caractère de clé à la valeur du caractère de la chaine chiffrée.
			int valChar =((int)string.charAt(i)-(int)cle.charAt(i%cle.length()));
			//Si cette valeur est inférieur à 0, alors on ajoute 255 à cette valeur.
			if(valChar<0)
				valChar+=256;
			//on convertit cet entier en caractère afin de retrouvé le caractère d'origine si la clé est la bonne
			stringChiffre+= (char)(valChar);
		}
		//on retroune la chaine dechiffrée.
		return stringChiffre;	
	}
	
}
