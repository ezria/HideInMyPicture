package Interface;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Panneau extends JPanel{
	private BufferedImage monImage;
	private Image imageRedim;
	public Panneau(){
		super();
	}
	
	public Panneau (BufferedImage monImage){
		super();
		this.monImage = monImage;
		Graphics2D g = monImage.createGraphics();
		g.drawImage(monImage, 0, 0,monImage.getWidth(),monImage.getHeight(), null); 
	}
	
	public BufferedImage getMonImage() {

		return monImage;
	}

	public void setMonImage(BufferedImage monImage) {
		this.monImage = monImage;
		resizeImage();
		this.repaint();
	}
	

	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if(monImage != null)
			resizeImage();
			g.drawImage(imageRedim, 0, 0, null); 	
	} 
	
	private void resizeImage(){
		
		int hauteurPanel, hauteurImage, largeurPanel, largeurImage;
		hauteurPanel = this.getHeight();
		largeurPanel = this.getWidth();
		hauteurImage = monImage.getHeight();
		largeurImage=monImage.getWidth();
		float ratioDiminution = (float)largeurPanel/(float)largeurImage;
		if (ratioDiminution>((float)hauteurPanel/(float)hauteurImage))
			ratioDiminution = (float)hauteurPanel/(float)hauteurImage;		
		imageRedim = monImage.getScaledInstance((int)(largeurImage*(ratioDiminution)), (int)(hauteurImage*(ratioDiminution)), Image.SCALE_FAST);

		
		
		
		/*if (largIm > largPan){
			largIm=largPan;
			hautIm=(int)(largPan/ratioIm);
			imageRedim = monImage.getScaledInstance(largPan,(int)(largPan/ratioIm),Image.SCALE_SMOOTH);
		}
		if (hautIm > hautPan){
			largIm=(int)(hautPan*ratioIm);
			hautIm=hautPan;
			imageRedim = monImage.getScaledInstance((int)(hautPan/ratioIm),hautPan,Image.SCALE_SMOOTH);
		}*/
		
	}
}
