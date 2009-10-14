public class PersonnageInteractif extends Personnage {

    private boolean interaction;
    private String messageInteraction;
    private StrategiePersonnage strPerso;
    
	public PersonnageInteractif(int posx, int posy, int sens, String nom, String nomImage, String messageAvant){
		super(posx,posy,sens,nom, nomImage);
		interaction = false;
		messageInteraction = messageAvant;
		strPerso = new StrategiePersonnageNonPorteur();
	}
	
	public boolean getInteraction() {
        return interaction;
    }

    public void setInteraction(boolean b) {
        interaction=b;
    }

    public StrategiePersonnage getStrategie() {
        return strPerso;
    }
    
    public void setStrategie(StrategiePersonnage sp) {
        strPerso = sp;
    }


    public String getMessageInteraction() {
        return messageInteraction;
    }
    
    public void setMessageInteraction(String s) {
        messageInteraction = s;
    }
	public String getType(){
			return "interactif";
	}
}
