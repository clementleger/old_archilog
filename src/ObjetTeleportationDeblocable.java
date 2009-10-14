import java.util.ArrayList;

public class ObjetTeleportationDeblocable extends ObjetTeleportation {

    private ArrayList<String> nomObjetsDebloquants;
    private String message;
    private boolean estDebloque;

    public ObjetTeleportationDeblocable(String message, String mapSuivante, Case caseArrivee, int sensArrivee) {
        super(mapSuivante, caseArrivee, sensArrivee);
        this.message = message;
        this.nomObjetsDebloquants = new ArrayList<String>();
        estDebloque = false;
    }    
    
    public String getType(){
		return "teleportationdeblocable";
	}

    public boolean getEstDebloque() {
        return estDebloque;
    }

    public void setEstDebloque(boolean debloc) {
        estDebloque = debloc;
    }
    public void ajouterObjet(String s) {
        nomObjetsDebloquants.add(s);
    }

    public ArrayList<String> getListeNomObjetsDebloquants() {
        return nomObjetsDebloquants;
    }

    public String getMessage() {
        return message;
    }

    public boolean peutEtreTeleporte(ArrayList<String>  sacADos) {
        if(sacADos.size() < nomObjetsDebloquants.size()) {
            return false;
        }
        else {
            for(String nomObjet : nomObjetsDebloquants) {
                if(!sacADos.contains(nomObjet)) {
                    return false;
                }
            }
        }
        return true;
    }
}
