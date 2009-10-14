public class PersonnageAmbulant extends PersonnageInteractif {

    private int fromX, toX, fromY, toY, vitesse;
    
    public PersonnageAmbulant(int posx, int posy, String nom,String nomImage, int toX,int toY, int vitesse, String messageAvant){
        super(posx, posy, Constant.BAS, nom, nomImage, messageAvant);
        this.fromX = posx;
        this.fromY = posy;
        this.toX = toX;
        this.toY = toY;
        this.vitesse = vitesse;
    }
    
    public int getToX() {
        return toX;
    }

    public int getToY() {
        return toY;
    }
    
    public int getFromX() {
        return fromX;
    }

    public int getFromY() {
        return fromY;
    }

    public int getVitesse() {
        return vitesse;
    }
    
    public String getType(){
		return "ambulant";
	}
} 
