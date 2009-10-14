import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import javax.swing.JOptionPane;
/**
 * Classe de gestion des évènements clavier
 */
public class CommandeClavier extends KeyAdapter {
    private Monde m;
    private String perso;
    private int posx, posy;

    public CommandeClavier(Monde m) {
	    this.m = m;
    }
    
    public void keyPressed(KeyEvent e) {
	    int k = e.getKeyCode();
        
        if (k == KeyEvent.VK_LEFT){
            m.deplacerPersonnage(Constant.GAUCHE);
        }
        else if (k == KeyEvent.VK_RIGHT) {
            m.deplacerPersonnage(Constant.DROITE);
        }
        else if (k == KeyEvent.VK_DOWN) {
             m.deplacerPersonnage(Constant.BAS);
        }
        else if (k == KeyEvent.VK_UP) {
             m.deplacerPersonnage(Constant.HAUT);
        }
        else if (k == KeyEvent.VK_R) {
            m.ramasser();
        }
        else if (k == KeyEvent.VK_P) {
            m.parler();
        }
        else if(k == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
        else if(k == KeyEvent.VK_ENTER) {
            m.setConversation(null);
        }
    }
}
