import java.util.TimerTask;
/**
 * Classe réalisant le déplacement des personnages ambulants
 */
public class BougePersonnageAmbulant extends TimerTask {

    private SujetMonde sujetMonde;

    public BougePersonnageAmbulant(SujetMonde sujetMonde) {
        this.sujetMonde=sujetMonde;
    }    
    /**
     * Méthode appelée par le timer. Fait bouger les personnages ambulants dans la map
     */
    public void run() {

        int x, y;
        if(!sujetMonde.getMondeConcret().getJeuTermine()) {                    
            for(PersonnageInteractif pi : sujetMonde.getMapCourante().getListePersos()) {
			    //si c'est un personnage ambulant
			    if( pi.getType().equals("ambulant")){
				    PersonnageAmbulant p = (PersonnageAmbulant) pi;
				    //si le personnage principal n'interagit pas avec le personnage ambulant
				    if( !p.getInteraction()){
					    x = 0;
					    y = 0;
					    if(p.getFromX() == p.getToX()) {
						    //On deplace sur Y
						     if(p.getPosy() + p.getVitesse() <= p.getToY() && p.getSens() == Constant.BAS) {
							    y = p.getVitesse();
						    }
						    else {
							    p.setSens(Constant.HAUT);
							    if(p.getPosy() - p.getVitesse() < p.getFromY()) {
								     p.setSens(Constant.BAS);
								     y = 0;
							    } 
							    else {
								    y = -p.getVitesse();
							    }
						    }
					    }
					    else if(p.getFromY() == p.getToY()) {
						    //On deplace sur X
					       
						    if(p.getPosx() + p.getVitesse() <= p.getToX() && p.getSens() == Constant.DROITE) {
							    x = p.getVitesse();
						    }
						    else {
							    p.setSens(Constant.GAUCHE);
							    if(p.getPosx() - p.getVitesse() < p.getFromX()) {
								     p.setSens(Constant.DROITE);
								     x = 0;
							    } 
							    else {
								    x = -p.getVitesse();
							    }
						    }
					    }
					    p.deplacer(p.getPosx()+x,p.getPosy()+y);
				    }   
			    }   
		       sujetMonde.notifieObsMonde();
		    }
        }
	} 
}
