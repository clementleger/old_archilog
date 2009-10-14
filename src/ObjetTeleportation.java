public class ObjetTeleportation extends ObjetTerrain{
	private String mapSuivante;
	private Case caseArrivee;
	private int sensArrivee;
    private String nom;
   
   public ObjetTeleportation(String mapSuivante, Case caseArrivee, int sensArrivee){
       super();
       this.mapSuivante = mapSuivante;
       this.caseArrivee = caseArrivee;
       this.sensArrivee = sensArrivee;
       this.nom=nom;
    }
    
    public String getType(){
		return "teleportation";
	}
	
    public int getSensArrivee(){
        return sensArrivee;
    }
    
    public String getMapSuivante(){
        return mapSuivante;
    }
    
    public Case getCaseArrivee(){
        return caseArrivee;
    }
}
