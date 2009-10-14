import java.awt.image.BufferedImage;
public class ObjetInteractif extends Objet{
	private int hauteur;
	private int largeur;
	private String nomImage;
	private String nom;
	private BufferedImage image;
	
	public ObjetInteractif(String nom, String nomImage){
		this.nomImage = nomImage;
		this.image = StockImage.get().getImage(Constant.DOSSIER_SPRITES+Constant.DOSSIER_OBJETS+nomImage+".gif");
        this.hauteur = image.getWidth(null);
        this.largeur = image.getHeight(null);
        this.nom = nom;
	}
	
	public String getType(){
		return "interactif";
	}
	
	public int getLargeur(){
        return largeur;
    }
    
    public int getHauteur(){
        return hauteur;
    }
    
    public BufferedImage getImage(){
		return image;
	}
    
    public String getNom(){
        return nom;
    }

    public boolean equals(Object o) {
	    if(this==o) return true;
	    if(o==null) return false;
	    if(getClass()!=o.getClass()) return false;
	    ObjetInteractif oi=(ObjetInteractif) o;
		
	    return getNom().equals(oi.getNom());
	}
}
