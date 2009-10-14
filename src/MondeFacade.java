import java.util.HashMap;
import javax.swing.JFrame;
import java.awt.Dimension;

public class MondeFacade {

        private MonteurFenetre fen;
        private SujetMonde sujetMonde;
        private VueMonde vueMonde; 
        private VueMenu vueMenu; 
        private boolean lancer;

        
    public MondeFacade() {
		lancer = false;
        fen = new MonteurFenetre("Projet d'architecture Logicielle - M1 STIC - By Titi Team",740,560);
		vueMenu = new VueMenu(this);
		fen.ajoutCentre(vueMenu);
    }
    
    public void nouveauJeu(){
		if(! estLancer() ){
			MondeConcret monde = new MondeConcret();
			ParserXML p = new ParserXML(monde, "Jeu.xml");
			p.genererMap();
			vueMonde = new VueMonde(monde);
			fen.ajoutCentre(vueMonde);
			VueMessage vueMessage = new VueMessage(monde);
			fen.ajoutHaut(vueMessage);
			VueSacADos vueSacADos = new VueSacADos();
			fen.ajoutBas(vueSacADos);
			SujetMonde sujetMonde = new SujetMonde(monde);
			sujetMonde.ajoutObservateurMonde(vueMonde);
			sujetMonde.ajoutObservateurMessage(vueMessage);
			sujetMonde.ajoutObservateurSacADos(vueSacADos);
			
			vueMonde.addKeyListener(new CommandeClavier(sujetMonde));
			vueMonde.requestFocusInWindow();
			
			lancer = true;
			fen.retourneFenetre().validate();
		}
	}
	
	public boolean estLancer(){
		return lancer;
	}

    public void lancerJeu() {
        fen.retourneFenetre(); 
        vueMenu.requestFocusInWindow();
    }
}
