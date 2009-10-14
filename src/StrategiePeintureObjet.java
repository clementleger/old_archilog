import java.awt.Graphics2D;
import java.awt.Image;

public class StrategiePeintureObjet implements StrategiePeinture {

    protected StockImage stockage;
    private ObjetRamassable o;
    
    public StrategiePeintureObjet(ObjetRamassable o){
        this.stockage = StockImage.get();
        this.o = o;
    }
    
    public void paint(Graphics2D g2){
        g2.drawImage(o.getImage(),o.getX(),o.getY(),o.getImage().getWidth(null),o.getImage().getHeight(null),null);
    }
}
