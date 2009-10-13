import java.awt.*;
import javax.swing.*;

public class MonteurFenetre{
    private JFrame fenetre;
    private JPanel centre;
    private int hauteur,largeur;

    public MonteurFenetre(String titre, int hauteur, int largeur){
        fenetre = new JFrame(titre);
        centre = new JPanel();
        this.hauteur = hauteur;
        this.largeur = largeur;
    }

    public void ajout(JComponent j){
        centre.add(j);
    }
    
    public JFrame retourneFenetre(){
	    if (fenetre != null)
	        return fenetre;

	    // operation de fermeture
	    fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    // ajout des panneaux centre et bas
	    Container contentPane = fenetre.getContentPane();
	    contentPane.add(centre,BorderLayout.CENTER);
        
	    // formater et rendre visible
	    fenetre.pack();
        fenetre.setSize(largeur,hauteur);
	    fenetre.setVisible(true);
	    return fenetre;
    }
}
