import java.util.ArrayList;
import java.awt.image.BufferedImage;

public class MapConcrete implements Map{
    
    private String nom;
    private String description;
    private int hauteur;
    private int largeur;
    private int tailleCase;
    private Case caseDebut;
    private int sensDebut;
    private ObjetTerrain[][] objetsTerrain; //objet bloquants et teleportation
    private ArrayList<PersonnageInteractif> listePersos;
    private ArrayList<ObjetRamassable> listeObjetsRamassables;
    
    public MapConcrete(String nom, String description, int tailleCase, Case caseDebut, int sensDebut){
        this.nom = nom;
        this.description = description;
        this.tailleCase = tailleCase;//taille d'une case en pixel
        BufferedImage imageBackground = StockImage.get().getImage(Constant.DOSSIER_SPRITES + Constant.DOSSIER_MAPS +nom+"/bg.gif");
		hauteur = imageBackground.getHeight(null);
		largeur = imageBackground.getWidth(null);
		imageBackground = null;
        objetsTerrain = new ObjetTerrain[largeur/tailleCase][hauteur/tailleCase];
        listePersos = new ArrayList<PersonnageInteractif>();
        listeObjetsRamassables = new ArrayList<ObjetRamassable>();
        this.caseDebut = caseDebut;
        this.sensDebut = sensDebut;
    }
    
    public void setHauteur( int h ){
        this.hauteur = h;
    }
    
    public void setLargeur( int l ){
        this.largeur = l;
    } 
       
    public int getHauteur(){
        return hauteur;
    }
    
    public int getLargeur(){
        return largeur;
    }
    
    public int getHauteurEnCase(){
        return getHauteur()/getTailleCase();
    }
    
    public int getLargeurEnCase(){
        return getLargeur()/getTailleCase();
    }
    
    /**
     * Retourne la case correspondante au coordonnées x, y en pixel
     * @param x x en pixel
     * @param y y en pixel
     * @return la case de la map correspond à ces coordonnées
     */
    public Case getCaseMap(int x, int y){
        return new Case(x/getTailleCase(), y/getTailleCase()); 
    }

    public Case getCaseDebut(){
        return caseDebut;
    }
    
    public int getSensDebut(){
        return sensDebut;
    }
    
    public int getTailleCase(){
        return tailleCase;
    }

    public String getNom(){
        return nom;
    }
    
    public String getDescription(){
        return description;
    }
    
    public PersonnageInteractif getPersonnageInteraction(){
        for(PersonnageInteractif pa : getListePersos()){
            if (pa.getInteraction())
                return pa;
        }
        return null;
    }
    
    public ObjetTerrain getObjetTerrain(Case c){
        int x = c.getX();
        int y = c.getY();
        if ( x >= 0 && x < largeur/tailleCase && y >= 0 && y < hauteur/tailleCase )
            return objetsTerrain[x][y];
        return null;
    }

    public ObjetRamassable getObjetRamassable(String nom) {                
        for(ObjetRamassable o : listeObjetsRamassables) {
            if(o.getNom().equals(nom)) {
                return o;
            }
        }
        return null;
    }
    
    public void setObjetTerrain(ObjetTerrain o, Case c){
        objetsTerrain[c.getX()][c.getY()] = o;
    }
    
    public ObjetTerrain[][] getObjetsTerrain(){
        return objetsTerrain;
    }
    
    public void ajouterObjetRamassable(ObjetRamassable o){
        listeObjetsRamassables.add(o);
    }
    
    public void supprimerObjetRamassable(ObjetRamassable o){
        listeObjetsRamassables.remove(o);
    }
    
    public void setCaseDebut(Case c){
        caseDebut = c;
    }
    
    public void setSensDebut(int sensDebut){
        this.sensDebut = sensDebut;
    }

    public ArrayList<PersonnageInteractif> getListePersos() {
        return listePersos;
    }
    
    public ArrayList<ObjetRamassable> getListeObjetsRamassables() {
        return listeObjetsRamassables;
    }

    public void ajouterPersonnage(PersonnageInteractif p) {
        listePersos.add(p);
    }

    public void supprimerPersonnage(PersonnageInteractif p) {
        listePersos.remove(p);
    }
}
