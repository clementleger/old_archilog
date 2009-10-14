public class PersonnageFixe extends PersonnageInteractif {
    
    public PersonnageFixe(int posx, int posy, String nom, String nomImage, int sens, String messageAvant){
       super(posx, posy, sens, nom, nomImage, messageAvant);
    }
    
    public String getType(){
		return "fixe";
	}
} 
