import java.util.ArrayList;
public interface Map{
    
    public int getHauteur();
    public int getLargeur();
    public void setLargeur( int l );
    public void setHauteur( int h );
    public int getHauteurEnCase();
    public int getLargeurEnCase();
    public int getTailleCase();
    public String getNom();
    public String getDescription();
    public ObjetTerrain getObjetTerrain(Case c);
    public ObjetRamassable getObjetRamassable(String nom);
    public Case getCaseDebut();
    public int getSensDebut();
    public PersonnageInteractif getPersonnageInteraction();
    public void setSensDebut(int sensDebut);
    public Case getCaseMap(int x, int y);
    public ArrayList<PersonnageInteractif> getListePersos();
    public void setCaseDebut(Case c);
    
    public void ajouterPersonnage(PersonnageInteractif p);
    public void supprimerPersonnage(PersonnageInteractif p);
    
    public ArrayList<ObjetRamassable> getListeObjetsRamassables();
    public void setObjetTerrain(ObjetTerrain o, Case c);
    
    public void supprimerObjetRamassable(ObjetRamassable o);
    public void ajouterObjetRamassable(ObjetRamassable o);
    
    public ObjetTerrain[][] getObjetsTerrain();  
}
