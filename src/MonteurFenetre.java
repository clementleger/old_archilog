import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;

public class MonteurFenetre{
    private JFrame fenetre;
    private JPanel centre;
    private JPanel haut;
    private JPanel bas;
    private String titre;
    private int hauteur,largeur;

    /**
     * Constructeur par défault du type Monteur Fenetre
     * @param titre le titre de la fenetre
     * @param hauteur la hauteur en pixel de la fenetre
     * @param largeur la largeur en pixel de la fenetre
     */
    public MonteurFenetre(String titre, int largeur, int hauteur){
        this.titre = titre;
        centre = new JPanel();
        centre.setLayout(new BoxLayout(centre, BoxLayout.Y_AXIS));
        haut = new JPanel();
        haut.setLayout(new BoxLayout(haut, BoxLayout.LINE_AXIS));
        bas = new JPanel();
        bas.setLayout(new BoxLayout(bas, BoxLayout.LINE_AXIS));
        this.hauteur = hauteur;
        this.largeur = largeur;
    }
    /**
     * Permet d'ajouter un composant au centre de la fenetre
     * @param j le composant à ajouter au centre de la fenetre
     */
    public void ajoutCentre(JComponent j){
		centre.removeAll();
        centre.add(j);
    }
    
    /**
     * Permet d'ajouter un composant en bas de la fenetre
     * @param j le composant à ajouter en bas de la fenetre
     */ 
    public void ajoutHaut(JComponent j){
		haut.removeAll();
        haut.add(j);
    }

    public void ajoutBas(JComponent j){
		bas.removeAll();
        bas.add(j);
    }
    
    /**
     * permet de retourner la fenetre
     * @return la fenetre créée par l'objet
     */
    public JFrame retourneFenetre(){
	    if (fenetre != null)
	        return fenetre;

        
        fenetre = new JFrame(titre);
	    // operation de fermeture
	    fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    // ajout des panneaux centre et bas
	    Container contentPane = fenetre.getContentPane();
	    contentPane.add(centre,BorderLayout.CENTER);
	    contentPane.add(haut,BorderLayout.NORTH);
        contentPane.add(bas,BorderLayout.SOUTH);
	    //formater et rendre visible
	    fenetre.pack();
	    fenetre.setVisible(true);
        fenetre.setSize(largeur,hauteur);
	    return fenetre;
    }
}
