import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Image;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import javax.swing.JPanel;

import java.util.ArrayList;

public class VueSacADos extends JPanel implements ObservateurSacADos{
   
    private ArrayList<ObjetInteractif> sacADos = new ArrayList<ObjetInteractif>();  
    private StockImage stockage;   

    public VueSacADos(){
        setPreferredSize(new Dimension(400,66));
        stockage = StockImage.get();
    }
    
    
    public void miseAJour(ArrayList<ObjetInteractif> sacADos){
        this.sacADos =sacADos;
        repaint();
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		
        g2.setColor(Color.BLACK);
        g2.fillRect(0,0,this.getWidth(), this.getHeight());
        dessinerSac(g2);
    }
    
    public void dessinerSac(Graphics2D g2){
        Image tmp;
        int largeur = 0;
        FontMetrics fontMetrics = g2.getFontMetrics();
        String message = "Sac a dos";
        int hauteurTxt = fontMetrics.getHeight();
        g2.setColor(Color.WHITE);
        g2.fillRect(0,0,this.getWidth(),2);
        tmp = stockage.getCacheImage(Constant.DOSSIER_SPRITES + Constant.DOSSIER_HUD + "sac.gif");
        g2.drawImage(tmp, 0, 2, tmp.getWidth(null),tmp.getHeight(null),null);
        
        for(ObjetInteractif or: sacADos) {        
            largeur += or.getLargeur() + 15;
        }
        int longueurTxt = fontMetrics.stringWidth(message);
        int posx = this.getWidth()/2 -(largeur-15)/2;
        int dy = this.getHeight()/2 -hauteurTxt/2 ;
            
        for(ObjetInteractif or: sacADos) {        
            tmp = or.getImage();
            g2.drawImage(tmp, posx, dy, tmp.getWidth(null),tmp.getHeight(null),null);
            posx+=tmp.getWidth(null)+15;
        }
        int dx = this.getWidth()/2 - longueurTxt/2;
        g2.translate(dx, dy-hauteurTxt/2);

        g2.setFont(new Font("Serif",Font.PLAIN,16));
        g2.setColor(Color.WHITE);
        g2.drawString(message,0,0);
    }
}
