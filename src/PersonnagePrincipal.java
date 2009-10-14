import java.awt.Graphics2D;
import java.util.ArrayList;

public class PersonnagePrincipal extends Personnage {
	
	private String conversation;
	private ArrayList<ObjetInteractif> sacADos = new ArrayList<ObjetInteractif>();
    
	public PersonnagePrincipal(int posx, int posy, String nom, String nomImage){
		super(posx, posy, Constant.BAS, nom, nomImage);
		setVitesse(6); //C'est mal mais pas le temps :p
	}
	 
	public void setConversation(String conv){
		this.conversation = conv;
	}
	
	public boolean enConversation(){
		return conversation != null;
	}
		
	public String getConversation(){
		return conversation;
	}
    
    public ArrayList<ObjetInteractif> getSacADos(){
        return sacADos;
    }	
    
    public void ajouterObjet(ObjetInteractif or){
        sacADos.add(or);
    }

    public ArrayList<String> getNomObjetsSacADos() {
        ArrayList<String> liste = new ArrayList<String>();
        for(ObjetInteractif oi : sacADos) {
            liste.add(oi.getNom());
        }
        return liste;
    }
}
