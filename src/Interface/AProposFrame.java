package Interface;

import java.awt.SystemColor;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class AProposFrame extends JFrame {

	private JPanel contentPane;
	public AProposFrame() {
		setResizable(false);
		setTitle("à propos de ce logiciel");
		setVisible(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 370);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextPane txtpnCeLogicielA = new JTextPane();
		txtpnCeLogicielA.setEditable(false);
		txtpnCeLogicielA.setBackground(SystemColor.control);
		txtpnCeLogicielA.setText("Ce logiciel a été conçu dans le cadre d'un projet donné par Monsieur Collette, membre de centre de recherche de l'école d'ingénieurs ESAIP de Saint-Barthélemy-d'Anjou. \r\nCe logiciel permet de cacher une information dans une image et ensuite de la récupérer. \r\nIl permet également de chiffrer et déchiffrer le message inscrit par clé à l'aide de l'algorithme de Vigenère appliqué à la table ASCII. \r\n\r\nLes images chiffrées doivent avoir des couleurs inscrites sur 8, 24 ou 32 bits et doivent avoir un format non compressé comme par exemple le BMP8 ou 24 ainsi que le PNG 8 ou 24. \r\n\r\nLes images avec couleurs encodées sur 8 bits devront être en niveau de gris pour conserver la discrétion du message. \r\n\r\nCe logiciel n'a aucune licence d'utilisation et est totalement libre de réutilisation. \r\n\r\nCréé par Corentin Chevallier, avec l'aide d\"Alexandre Bergère pour l'algorithme de Vigenère.");
		txtpnCeLogicielA.setBounds(10, 10, 430, 350);
		contentPane.add(txtpnCeLogicielA);
	}
}
