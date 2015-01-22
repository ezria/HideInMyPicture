package Interface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import Objet.Image;
import Traitement.SteganoTraitement;
public class Stenasy {
	
	private JFrame frmHimphideIn;
	private BufferedImage image;
	private JTextPane editorPaneTexte,editorPaneCle;
	private JButton btnCache,btnLireInfo;
	private JRadioButton rdbtnCouleur,rdbtnNiveauDeGris;
	private File fileOpened;
	private JMenuItem mntmSauvergarder,mntmSauvegarderSous,mntmCredit, mntmOuvrir ;
	private JLabel lblNomImage;
	private Panneau panneau;
	private JTextPane textPanInfo;
	private JMenu mnFichier;
	private JScrollPane scrollPane_1;
	private JScrollPane scrollPane_2;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Stenasy window = new Stenasy();
					window.frmHimphideIn.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Stenasy() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmHimphideIn = new JFrame();
		frmHimphideIn.setTitle("HIMP (Hide in my picture)");
		frmHimphideIn.setBounds(100, 100, 788, 626);
		frmHimphideIn.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmHimphideIn.setResizable(false);
		JMenuBar menuBar = new JMenuBar();
		frmHimphideIn.setJMenuBar(menuBar);
		
		mnFichier = new JMenu("Fichier");
		menuBar.add(mnFichier);
		mnFichier.setMnemonic(8);
		
		mntmOuvrir = new JMenuItem("Ouvrir");
		mntmOuvrir.setMnemonic('O');
		mntmOuvrir.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		mntmOuvrir.addActionListener(new openImage());
		mnFichier.add(mntmOuvrir);
		
		mntmSauvergarder = new JMenuItem("Sauvegarder");
		mntmSauvergarder.addActionListener(new Save());
		mntmSauvergarder.setMnemonic('S');
		mntmSauvergarder.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		mnFichier.add(mntmSauvergarder);
		
		mntmSauvegarderSous = new JMenuItem("Sauvegarder Sous");
		mntmSauvegarderSous.setMnemonic('U');
		mntmSauvegarderSous.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_U, ActionEvent.CTRL_MASK));
		mnFichier.add(mntmSauvegarderSous);
		
		JMenu mnInfo = new JMenu("Info");
		mnInfo.setMnemonic('I');
		menuBar.add(mnInfo);
		
		mntmCredit = new JMenuItem("A propos...");
		mnInfo.add(mntmCredit);
		mntmCredit.addActionListener(new InfoAction());
		mntmSauvegarderSous.addActionListener(new SaveUnder());
		frmHimphideIn.getContentPane().setLayout(null);
		
		JPanel panelGauche = new JPanel();
		panelGauche.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		panelGauche.setBounds(0, 0, 225, 553);
		frmHimphideIn.getContentPane().add(panelGauche);
		panelGauche.setLayout(null);
		
		JLabel lblTexteCacher = new JLabel("Texte \u00E0 cacher :");
		lblTexteCacher.setBounds(10, 11, 205, 14);
		panelGauche.add(lblTexteCacher);
		
		JLabel lblClDeCryptage = new JLabel("Cl\u00E9 de cryptage :");
		lblClDeCryptage.setBounds(10, 155, 205, 14);
		panelGauche.add(lblClDeCryptage);
		
		JLabel lblTypeDimage = new JLabel("Type d'image :");
		lblTypeDimage.setBounds(10, 263, 205, 14);
		panelGauche.add(lblTypeDimage);
		ButtonGroup typeImageGroup = new ButtonGroup();
		
		rdbtnNiveauDeGris = new JRadioButton("Niveau de gris");
		rdbtnNiveauDeGris.setHorizontalAlignment(SwingConstants.LEFT);
		rdbtnNiveauDeGris.setMnemonic('g');
		rdbtnNiveauDeGris.setBounds(20, 284, 109, 23);
		typeImageGroup.add(rdbtnNiveauDeGris);
		panelGauche.add(rdbtnNiveauDeGris);
		
		rdbtnCouleur = new JRadioButton("Couleur");
		rdbtnCouleur.setMnemonic('c');
		rdbtnCouleur.setBounds(20, 310, 109, 23);
		rdbtnCouleur.setSelected(true);
		typeImageGroup.add(rdbtnCouleur);
		panelGauche.add(rdbtnCouleur);
		
		btnCache = new JButton("Cacher l'information");
		btnCache.setBounds(10, 340, 205, 23);
		panelGauche.add(btnCache);
		btnCache.addActionListener(new WritePicture());
		
		btnLireInfo = new JButton("Lire l'information");
		btnLireInfo.setBounds(10, 374, 205, 23);
		btnLireInfo.addActionListener(new ReadPicture());
		panelGauche.add(btnLireInfo);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 408, 205, 134);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		panelGauche.add(scrollPane);
		
		textPanInfo = new JTextPane();
		textPanInfo.setEditable(false);
		textPanInfo.setBackground(SystemColor.menu);
		scrollPane.setViewportView(textPanInfo);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(20, 28, 195, 116);
		panelGauche.add(scrollPane_1);
		
		editorPaneTexte = new JTextPane();
		scrollPane_1.setViewportView(editorPaneTexte);
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		editorPaneTexte.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
		
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(20, 180, 195, 66);
		scrollPane_2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		panelGauche.add(scrollPane_2);
		
		editorPaneCle = new JTextPane();
		scrollPane_2.setViewportView(editorPaneCle);
		editorPaneCle.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
		enabledButton(false);
		JPanel panelDroite = new JPanel();
		panelDroite.setBounds(224, 0, 548, 553);
		frmHimphideIn.getContentPane().add(panelDroite);
		panelDroite.setLayout(new BorderLayout(0, 0));
		
		panneau = new Panneau();
		panelDroite.add(panneau, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 551, 772, 26);
		frmHimphideIn.getContentPane().add(panel);
		
		lblNomImage = new JLabel("");
		panel.add(lblNomImage);
		
	}
	private void enabledButton(boolean bool){
		btnLireInfo.setEnabled(bool);
		btnCache.setEnabled(bool);
		mntmSauvergarder.setEnabled(bool);
		mntmSauvegarderSous.setEnabled(bool);
		editorPaneCle.setText("");
		editorPaneTexte.setText("");
	}
	private void enabledAllButton(boolean bool){
		btnLireInfo.setEnabled(bool);
		btnCache.setEnabled(bool);
		mntmSauvergarder.setEnabled(bool);
		mntmSauvegarderSous.setEnabled(bool);
		mntmOuvrir.setEnabled(bool);
		rdbtnNiveauDeGris.setEnabled(bool);
		rdbtnCouleur.setEnabled(bool);
		editorPaneCle.setEnabled(bool);
		editorPaneTexte.setEnabled(bool);
	}
	private void setImage(File fichier){
		try {
			image = new Image(ImageIO.read(fichier)).getBuffImage();
			enabledButton(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			enabledButton(false);
		}
		panneau.setMonImage(image);
	}
	class openImage implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			fileOpened = DialogBox.openDialog((fileOpened==null)?null:fileOpened, "Veuillez selectionner l'image à traiter.");
			setImage(fileOpened);
			enabledButton(true);
			lblNomImage.setText(fileOpened.getAbsolutePath());
			textPanInfo.setText("");
		}
	}
	
	class WritePicture implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String texte = editorPaneTexte.getText();
			
			enabledAllButton(false);
			
			if(rdbtnNiveauDeGris.isSelected()&&(texte.length()*8)+16<=image.getWidth()*image.getHeight()){
				image=SteganoTraitement.writeOnHeightBitColorsPicture(image, texte, editorPaneCle.getText());
				panneau.setMonImage(image);
				textPanInfo.setText("Traitement terminé");
				
			}
			else if(rdbtnCouleur.isSelected()&&((texte.length()*8)/3)+16<=image.getWidth()*image.getHeight()){
				image=SteganoTraitement.writeOnTwentyFourBitColorsPicture(image, texte, editorPaneCle.getText());
				panneau.setMonImage(image);
				textPanInfo.setText("Traitement terminé");
			}
			else 
				textPanInfo.setText("Votre texte est trop long pour être inscrit dans votre image.");
			enabledAllButton(true);
		}
	}
	class ReadPicture implements ActionListener{
		@Override
		
		public void actionPerformed(ActionEvent e) {
			enabledAllButton(false);
			editorPaneTexte.setText("");
			if(rdbtnNiveauDeGris.isSelected())
				textPanInfo.setText("Votre message est: \n\r" + SteganoTraitement.readOnHeightBitColorsPicture(image, editorPaneCle.getText()));

			else if(rdbtnCouleur.isSelected())
			
			enabledAllButton(true);
		}
	}
	class Save implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String format = (fileOpened.getName().substring(fileOpened.getName().indexOf('.')+1, fileOpened.getName().length()).toUpperCase()=="JPG")?"JPEG":fileOpened.getName().substring(fileOpened.getName().indexOf('.')+1, fileOpened.getName().length()).toUpperCase();
			SteganoTraitement.saveFile(fileOpened, image, format);
			setImage(fileOpened);
			enabledButton(true);
			textPanInfo.setText("Image sauvegardée sur le fichier "+fileOpened.getPath());
		}
	}
	class SaveUnder implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			File file= DialogBox.saveDialog((fileOpened==null)?null:fileOpened, "Veuillez selectionner le lieu de sauvegarde de votre fichier.");
			String format="";
			if(file.getName().lastIndexOf(".")==-1){
				format= fileOpened.getName().substring(fileOpened.getName().indexOf('.')+1, fileOpened.getName().length()).toUpperCase();
				file=new File(file.getAbsolutePath()+"."+format.toLowerCase());
			}
			else
				format=file.getName().substring(file.getName().lastIndexOf(".")+1, file.getName().length()).toUpperCase();
			SteganoTraitement.saveFile(file, image, format);
			fileOpened=file;
			setImage(fileOpened);
			enabledButton(true);
			lblNomImage.setText(fileOpened.getAbsolutePath());	
			textPanInfo.setText("Image sauvegardée sur le fichier "+fileOpened.getPath());

		}
	}
	class  InfoAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			new AProposFrame();
			textPanInfo.setText("");
		}
		
	}
}
