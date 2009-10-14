import java.awt.Graphics2D;
import java.awt.Image;

public class StrategiePeinturePersonnage implements StrategiePeinture {

    protected StockImage stockage;
    private Personnage p;
    
    public StrategiePeinturePersonnage(Personnage p){
        this.stockage = StockImage.get();
        this.p = p;
    }
    
    public void paint(Graphics2D g2){
        Image tmp = p.getTilesPerso(p.getNumImage()-1,p.getSens());
        g2.drawImage(tmp,p.getPosx(),p.getPosy(),tmp.getWidth(null),tmp.getHeight(null),null);
    }
}
