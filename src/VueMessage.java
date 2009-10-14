import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import javax.swing.JPanel;
import java.util.Timer;
import java.util.TimerTask;


public class VueMessage extends JPanel implements ObservateurMessage{
    private Monde monde;
    protected String dernierNomMap;
    private StockImage stockImage;
    private BufferedImage carte;
    
    final static int SIZE = 66;
    
    public VueMessage(Monde monde){
        this.monde = monde;
        this.dernierNomMap = "";
        setPreferredSize(new Dimension(this.getWidth(),SIZE));
        stockImage = StockImage.get();
        carte = stockImage.getCacheImage(Constant.DOSSIER_SPRITES + Constant.DOSSIER_HUD + "carte.gif");
    }
    
    public void miseAJour(){
		if(!dernierNomMap.equals(monde.getMapCourante().getNom())){
			dernierNomMap = monde.getMapCourante().getNom();
			repaint();
		}
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

        
        g2.setColor(Color.BLACK);
        g2.fillRect(0,0,this.getWidth(), this.getHeight());
        g2.setColor(Color.WHITE);
        g2.fillRect(0,SIZE - 2,this.getWidth(), SIZE - 2);

        g2.drawImage(carte, 0, 0, carte.getWidth(null),carte.getHeight(null),null);
        
        g2.setFont(new Font("Serif",Font.PLAIN,20));
        g2.drawString(monde.getMapCourante().getDescription(), 70, 35);
    }
}
