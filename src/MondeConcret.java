import java.util.ArrayList;
import java.util.HashMap;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MondeConcret implements Monde{

    private PersonnagePrincipal personnage;
    private Map mapCourante;
    private String messageAction;
    private Music player;
    private Case caseCourante;
   	private HashMap<String,Map> listeMaps;
    private String nomObjetGagnant;
    private String messageFin;
    private boolean jeuTermine;
    private BufferedImage imageBackground;
    private BufferedImage imageAlpha;
    private String musicFin;
    private String logoFin;

	//Constructeur
	public MondeConcret() {
		personnage = null;
		messageAction = null;
		mapCourante = null;
		player = new Music();
		listeMaps = new HashMap<String,Map>();
        jeuTermine = false;
	}

	//Accesseurs
	public PersonnagePrincipal getPersonnage() {
		return personnage;
	}

    public void setMusicFin(String s) {
        musicFin = s;
    }

    public void setLogoFin(String s) {
        logoFin = s;
    }

    public String getLogoFin() {
        return logoFin;
    }

    public Map getMapCourante() {
		return mapCourante;
	}
    
    public String getMessageAction(){
        return messageAction;
    }

    public String getMessageFin() {
        return messageFin;
    }
    
    public BufferedImage getImageBackground(){
		return imageBackground;
	}
    public BufferedImage getImageAlpha(){
		return imageAlpha;
	}

    public boolean getJeuTermine() {
        return jeuTermine;
    }
    public void setNomObjetGagnant(String s) {
        nomObjetGagnant = s;
    }

    public void setMessageFin(String m) {
        messageFin = m;
    }

    public String getNomObjetGagnant() {
        return nomObjetGagnant;
    }

    public void setConversation(String conv){
		getPersonnage().setConversation(conv);
	}
	
	public String getConversation(){
		return getPersonnage().getConversation();
	}
    
    public boolean enConversation(){
		return getPersonnage().enConversation();
	}
    
    public void setMessageAction(String message){
        this.messageAction = message;
    }
    
    public boolean afficherAction(){
		return messageAction != null;
	}
	
	//Mutateurs
    public void deplacerPersonnage(int sens) {
        int posx = getPersonnage().getPosx(), posy = getPersonnage().getPosy();
        int x = 0, y = 0;
        boolean deplace=true, afficherInfo = false;
        int hauteurMap = getMapCourante().getHauteur(), largeurMap = getMapCourante().getLargeur();
        int largeurPerso = getPersonnage().getLargeur(), hauteurPerso = getPersonnage().getHauteur();
        int objx = 0;
        switch(sens){
            case Constant.BAS : 
                y = 1; 
                hauteurMap -= hauteurPerso ;
                objx = posx+largeurPerso/2;
            break;
            case Constant.HAUT : 
                y = -1; 
                objx = posx+largeurPerso/2;
            break;
            case Constant.GAUCHE : 
                x = -1; 
                objx = posx+(largeurPerso/4);
            break;
            case Constant.DROITE : 
                x = 1; 
                largeurMap -= largeurPerso;
                objx = posx+3*(largeurPerso/4);
            break;
        }
        x = x*getPersonnage().getVitesse();
        y = y*getPersonnage().getVitesse();
        if(getPersonnage().getPosx() + x >= 0 && getPersonnage().getPosx() + x < largeurMap){
            posx += x;
        }
        if(getPersonnage().getPosy() + y >= 0 && getPersonnage().getPosy() + y < hauteurMap){
            posy += y;
        }
        //collision ?
        ObjetTerrain o = null;
        int objy = posy+hauteurPerso;
        o = getMapCourante().getObjetTerrain(getMapCourante().getCaseMap(objx,objy));
        getPersonnage().setSens(sens);
        if(o != null) {
            if( o.getType().equals("bloquant")) {
                deplace = false;
            }
            else if( o.getType().equals("teleportationdeblocable")) {
                ObjetTeleportationDeblocable od = (ObjetTeleportationDeblocable) o;
                ArrayList<ObjetInteractif> sacADos = getPersonnage().getSacADos();
                boolean teleportation = true;
                deplace = false;
                if(od.peutEtreTeleporte(getPersonnage().getNomObjetsSacADos())) {
                    getMap(od.getMapSuivante()).setSensDebut(od.getSensArrivee());
                    getMap(od.getMapSuivante()).setCaseDebut(od.getCaseArrivee());
                    setMapCourante(od.getMapSuivante());
                }
                else {
                   setMessageAction(od.getMessage());
                   afficherInfo = true;
                }
            }
            else if( o.getType().equals("teleportation")) {
                ObjetTeleportation porte = (ObjetTeleportation)o;
                deplace = false;
                getMap(porte.getMapSuivante()).setSensDebut(porte.getSensArrivee());
                getMap(porte.getMapSuivante()).setCaseDebut(porte.getCaseArrivee());
                setMapCourante(porte.getMapSuivante());
            }
        }

        //collision avec les personnages ambulants
        int posxshift = posx + largeurPerso/4;
        int posyshift = posy + (hauteurPerso/4);
        largeurPerso = 2*(largeurPerso/4);
        hauteurPerso = 3*(largeurPerso/4);
        for(PersonnageInteractif p : getMapCourante().getListePersos()){
            if(Math.abs(p.getPosx()-posxshift) < (p.getLargeur() + largeurPerso)/2  &&
               Math.abs(p.getPosy()-posyshift) < (p.getHauteur() + hauteurPerso)/2){
                p.setInteraction(true);
                setMessageAction("Appuyez sur P pour parler avec "+p.getNom());
                afficherInfo = true;
            }
            else{
                p.setInteraction(false);
            }
        }
        //rammassage d'objet possible
        for(ObjetRamassable or : getMapCourante().getListeObjetsRamassables()){
            if(Math.abs(or.getX()- posxshift) < (or.getLargeur() + largeurPerso)/2 &&
               Math.abs(or.getY()- posyshift) < (or.getHauteur() + hauteurPerso)/2 ){ 
				setMessageAction("Appuyez sur R pour ramasser "+or.getNom());
                afficherInfo = true;
            }
        }
        if( !afficherInfo ){
			setMessageAction(null);
		}
        if(deplace) {
            getPersonnage().deplacer(posx, posy);
        }
    }

    public void setMapCourante(String nomMap) {
        if ( getMap(nomMap) != null){
            mapCourante = getMap(nomMap);
            //Mise a jour des coordonnées du personnage
            Case debut = mapCourante.getCaseDebut();
            getPersonnage().setPosx(debut.getX()*mapCourante.getTailleCase());
            getPersonnage().setPosy(debut.getY()*mapCourante.getTailleCase());
            getPersonnage().setSens(getMapCourante().getSensDebut());
            if (player.getSeq() != null){
                player.getSeq().stop();
                player.getSeq().close();            
                player = new Music();
            }
            player.run(getMapCourante().getNom());
            chargerImages();
            System.gc();
        }
    }
    
    public Map getMap(String map){
        return listeMaps.get(map);
    }

    public void ajoutMap(String nom, Map map){
        listeMaps.put(nom, map);
    }

    public void setPersonnage(PersonnagePrincipal p) {
        personnage = p;
    }

    public void setCaseCourante(Case caseCourante) {
        this.caseCourante = caseCourante;
    }
    
    public void chargerImages(){
        imageBackground = StockImage.get().getImage(Constant.DOSSIER_SPRITES + Constant.DOSSIER_MAPS +getMapCourante().getNom()+"/bg.gif");
        imageAlpha = StockImage.get().getImage(Constant.DOSSIER_SPRITES + Constant.DOSSIER_MAPS +getMapCourante().getNom()+"/alpha.gif");
		getMapCourante().setHauteur(imageBackground.getHeight(null));
		getMapCourante().setLargeur(imageBackground.getWidth(null));
	}


    public void ramasser(){
        //collision avec les objets
        for(ObjetRamassable o : getMapCourante().getListeObjetsRamassables()){
            if(Math.abs(o.getX()-getPersonnage().getPosx()) < (o.getLargeur() + getPersonnage().getLargeur())/2 &&
               Math.abs(o.getY()-getPersonnage().getPosy()) < (o.getHauteur() + getPersonnage().getHauteur())/2 ){
                if(o.getNom().equals(nomObjetGagnant))  {
                    jeuTermine = true;
                    player.setSonWav("sprites/"+musicFin+".wav");
                    try {
                        player.getSeq().close();
                    }
                    catch (Exception e) {}
                    player.lancer();
                }
                getPersonnage().ajouterObjet((ObjetRamassable) o);
				getMapCourante().supprimerObjetRamassable(o);
				setMessageAction(null);
                return;
            }
        }
    }

    
    public void parler(){
        PersonnageInteractif pa = getMapCourante().getPersonnageInteraction();
        if( pa != null ){
            setConversation(pa.getMessageInteraction());
            ObjetTransmettable ot = pa.getStrategie().interaction();
            if(ot != null ){
				getPersonnage().ajouterObjet(ot);
			}
        }
    }
}
