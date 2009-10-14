public class ObjetTransmettable extends ObjetInteractif {

    public ObjetTransmettable(String nom, String nomImage){
        super(nom, nomImage);
    }
    
    public String getType(){
		return "transmettable";
	}

     public boolean equals(Object o) {
	    if(this==o) return true;
	    if(o==null) return false;
	    if(getClass()!=o.getClass()) return false;
	    ObjetTransmettable or=(ObjetTransmettable) o;
		
	    return getNom().equals(or.getNom());
	}
}
