import java.awt.Graphics2D;
import java.util.ArrayList;
import java.awt.image.BufferedImage;

public abstract class Personnage{

	private int posx, posy;
    private int sens;
    private int vitesse;
    private int largeur;
    private int hauteur;
    private int itImage;
    private int numImage;
    private String nom;
    private String nomImage;
    private StrategiePeinture peinture;
    private BufferedImage[][] tilesPerso; 
    
	public Personnage(int posx, int posy, int sens, String nom, String nomImage){
		this.posx = posx;
		this.posy = posy;
        this.nom = nom;
        this.nomImage = nomImage;
        this.sens = sens;
        this.vitesse = 3;
        this.peinture = new StrategiePeinturePersonnage(this);
        this.itImage = 1;
        this.numImage = 1;
        tilesPerso = StockImage.get().getImagesPersonnage(Constant.DOSSIER_SPRITES+Constant.DOSSIER_PERSOS+nomImage+".png");
        hauteur = tilesPerso[0][0].getHeight(null);
        largeur = tilesPerso[0][0].getWidth(null);
	}
	
	public BufferedImage getTilesPerso(int iteration, int sens){
		return tilesPerso[iteration][sens];
	}

    public String getNom() {
        return nom;
    }
    
    public String getNomImage() {
        return nomImage;
    }
    
    public int getLargeur(){
        return largeur;
    }
    
    public int getHauteur(){
        return hauteur;
    }
    
    public int getVitesse(){
        return vitesse;
    }
    
    public int getPosx(){
        return posx;
    }
    
    public int getPosy(){
        return posy;
    }
    
    public int getSens(){
        return sens;
    }
    
    public void setSens(int sens){
        this.sens = sens;
    }
    
    public void setPosx(int x){
        this.posx = x;
    }
    
    public void setPosy(int y){
        this.posy = y;
    }
    
    public void setVitesse(int v){
		vitesse = v;
	}
    
	public void deplacer(int px, int py){
        posx = px;
        posy = py;
        bouger();
	}
    
    
    public void paint(Graphics2D g2){
        peinture.paint(g2);
    }
    
    public void bouger(){
        itImage = (itImage == 9) ? itImage = 2 : itImage + 1;
        numImage = itImage/2;
    }
    
    public int getNumImage(){
        return numImage;
    }
}
