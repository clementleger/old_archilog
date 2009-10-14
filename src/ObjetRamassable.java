import java.awt.Graphics2D;

public class ObjetRamassable extends ObjetInteractif{

    private String description;
    private int x ,y;
    private StrategiePeinture peinture;

    public ObjetRamassable(String nom, String nomImage, int x, int y){
        super(nom, nomImage);
        this.peinture = new StrategiePeintureObjet(this);
        this.x = x;
        this.y = y;
    }    
    
    public String getType(){
		return "ramassable";
	}
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public void paint(Graphics2D g2){
		peinture.paint(g2);
    }

     public boolean equals(Object o) {
	    if(this==o) return true;
	    if(o==null) return false;
	    if(getClass()!=o.getClass()) return false;
	    ObjetRamassable or=(ObjetRamassable) o;
		
	    return getNom().equals(or.getNom());
	}
    
}
