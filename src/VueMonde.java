import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Font;
import java.awt.Point;
import java.awt.FontMetrics;
import java.awt.AlphaComposite;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPanel;
import java.util.ArrayList;

public class VueMonde extends JPanel implements Observateur{
    private StockImage stockage;
    private MondeConcret monde;
    private int dx , dy;
    
    public VueMonde(MondeConcret monde){
        this.monde = monde;
        stockage = StockImage.get();
    }
    
    public void miseAJour(){
        repaint();
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if( ! isFocusOwner() )
			requestFocusInWindow();
        Graphics2D g2 = (Graphics2D) g;
        if(monde.getJeuTermine()) {
			afficherMessageFin(g2);
        }else {
			dessinerTout(g2);
        }
    }
    
    public void dessinerTout(Graphics2D g2){
		g2.setColor(Color.BLACK);
            g2.fillRect(0,0,this.getWidth(), this.getHeight());
            int posx = monde.getPersonnage().getPosx();
            int posy = monde.getPersonnage().getPosy();
            
            //SCROLLING
            if(posx > this.getWidth()/2 && posx < monde.getMapCourante().getLargeur() - this.getWidth()/2)
                dx = this.getWidth()/2 - posx;
            else if (posx < this.getWidth()/2)
                dx = 0;
            else if (posx > monde.getMapCourante().getLargeur() - this.getWidth()/2)
                dx = this.getWidth() - monde.getMapCourante().getLargeur();
                
            if( posy > this.getHeight()/2 && posy < monde.getMapCourante().getHauteur() - this.getHeight()/2)
                dy = this.getHeight()/2 - posy;
            else if (posy < this.getHeight()/2)
                dy = 0;
            else if (posy > monde.getMapCourante().getHauteur() - this.getHeight()/2)
                dy = this.getHeight() - monde.getMapCourante().getHauteur();
            //FULLSCREEN    
            if(this.getWidth() > monde.getMapCourante().getLargeur())
                dx = this.getWidth()/2 - monde.getMapCourante().getLargeur()/2;
            if (this.getHeight() > monde.getMapCourante().getHauteur())
                dy = 0;
            
                
		    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            g2.translate(dx, dy);
            dessinerMonde(g2);
	}
    
    public void dessinerMonde(Graphics2D g2){
		dessinerFond(g2);
        dessinerObjets(g2);
        //dessin des personnages
        dessinerPersonnages(g2);
        //couche alpha
        dessinerAlpha(g2);
		dessinerMessages(g2);
		
    }
    
    public void dessinerFond(Graphics2D g2){
        //background
        Image tmp = monde.getImageBackground();
        g2.drawImage(tmp,0,0,tmp.getWidth(null),tmp.getHeight(null),null);
	}
	
	public void dessinerAlpha(Graphics2D g2){
		int posx = monde.getPersonnage().getPosx();
		int posy = monde.getPersonnage().getPosy();
		int largeur = monde.getPersonnage().getLargeur();
		int hauteur = monde.getPersonnage().getHauteur();
		BufferedImage tmp = monde.getImageAlpha();
		int [] pix = new int[1];
		//récuperation du pixel du milieu du personnage
			tmp.getRGB(posx+largeur/2, posy+hauteur/2, 1, 1, pix, 0, 1);
			//si on arrive sur une zone de couleur
			if( ((pix[0] >> 24) & 0xFF) != 0x00){
				hauteur += 20;
				largeur += 40;
				posx -= 20;
				posy -= 10;
				if(posx < 0){
					largeur += posx;
					posx = 0;
				}
				if( posx+largeur > monde.getMapCourante().getLargeur()){
					largeur = monde.getMapCourante().getLargeur() - posx;
				}
				if( posy < 0){
					hauteur -= posy;
					posy = 0;
				}
				if( posy + hauteur > monde.getMapCourante().getHauteur()){
					hauteur = monde.getMapCourante().getHauteur() - posy;
				} 
				Point p = new Point(largeur/2, hauteur/2);
				int[] rgbs = new int[largeur*hauteur];
				int[] rgbscopy = new int[largeur*hauteur];
				//on récupere les pixels de la zone
				tmp.getRGB(posx, posy, largeur, hauteur, rgbs, 0, largeur);
				for (int i = 0; i < rgbs.length; i++){
					rgbscopy[i] = rgbs[i];
					int distance = (int) p.distance(new Point(i%largeur, i/largeur));
					if(distance < 20)
						rgbs[i] = rgbs[i] & 0x20ffffff;
					else if( distance < 25)
						rgbs[i] = rgbs[i] & 0x60ffffff;
					else if( distance < 30)
						rgbs[i] = rgbs[i] & 0x90ffffff;
				}
				tmp.setRGB(posx, posy, largeur, hauteur, rgbs, 0, largeur);
				g2.drawImage(tmp,0,0,tmp.getWidth(null),tmp.getHeight(null),null);
				tmp.setRGB(posx, posy, largeur, hauteur, rgbscopy, 0, largeur);
			}else{
				g2.drawImage(tmp,0,0,tmp.getWidth(null),tmp.getHeight(null),null);
			}
	}
	
    public void dessinerObjets(Graphics2D g2){
        for(ObjetRamassable o : monde.getMapCourante().getListeObjetsRamassables()){
            o.paint(g2);
        }
    }
    
    public void dessinerMessages(Graphics2D g2){
		if(monde.afficherAction()){
			FontMetrics fontMetrics = g2.getFontMetrics();
			int longueur = fontMetrics.stringWidth(monde.getMessageAction());
			int hauteur = fontMetrics.getHeight();
			setTransparence(g2, 0.7f);
			RoundRectangle2D rect = 
				new RoundRectangle2D.Float(monde.getPersonnage().getPosx()-longueur/2,
									monde.getPersonnage().getPosy()+monde.getPersonnage().getHauteur(),
									longueur+20,
									hauteur+10,
									15, 15);
			g2.fill(rect);
			setTransparence(g2, 1f );
			g2.setColor(Color.WHITE);
			g2.drawString(monde.getMessageAction(),
				monde.getPersonnage().getPosx()-longueur/2+10,
				monde.getPersonnage().getPosy()+monde.getPersonnage().getHauteur()+hauteur);
		}
		if(monde.enConversation()){
			FontMetrics fontMetrics = g2.getFontMetrics();
			int hauteur = fontMetrics.getHeight();
			Image tmp = stockage.getCacheImage(Constant.DOSSIER_SPRITES + Constant.DOSSIER_HUD + "parchemin.gif");
			g2.drawImage(tmp,this.getWidth()/2-tmp.getWidth(null)/2-dx,
				this.getHeight()/2-tmp.getHeight(null)/2-dy,
				tmp.getWidth(null),
				tmp.getHeight(null),null);
			String conv = monde.getConversation();
			g2.setColor(Color.BLACK);
			g2.setFont(new Font("Serif",Font.BOLD,14));
			int posx = -dx + this.getWidth()/2-tmp.getWidth(null)/2 + 2 * hauteur;
			int posy = -dy + this.getHeight()/2-tmp.getHeight(null)/2 + 2 * hauteur;
			int lastSpace = 0, lastIndex = 0, lines = 0; 
			for (int i = 0; i < conv.length(); i++){
				if(conv.charAt(i) == ' '){
					lastSpace = i+1;
				}	
				if( fontMetrics.stringWidth(conv.substring(lastIndex,i)) >  tmp.getWidth(null)/2 + 20){
					g2.drawString(conv.substring(lastIndex,lastSpace),posx,posy + lines * hauteur+10);
					lastIndex = lastSpace;
					lines++;
				}
			}
			g2.drawString(conv.substring(lastIndex,conv.length()),posx,posy + lines * hauteur+10);
			g2.setFont(new Font("Serif", Font.ITALIC, 14));
			String appuyer = "Appuyez sur Entrée pour continuer";
			g2.drawString(appuyer,
				-dx + this.getWidth()/2-fontMetrics.stringWidth(appuyer)/2 - 20,
				-dy + this.getHeight()/2+tmp.getHeight(null)/2 - hauteur);
		}
	}
    
    
    public void dessinerPersonnages(Graphics2D g2){
		ArrayList<PersonnageInteractif> pa = new ArrayList<PersonnageInteractif>();
        for(PersonnageInteractif p : monde.getMapCourante().getListePersos()){
			if( p.getPosy() < monde.getPersonnage().getPosy() )
				p.paint(g2);
			else
				pa.add(p);
        }
        monde.getPersonnage().paint(g2);
        for(PersonnageInteractif p : pa){
			p.paint(g2);
		}
    }
    
    public void afficherMessageFin(Graphics2D g2){
            g2.setColor(Color.BLACK);
            Image tmp = stockage.getCacheImage(Constant.DOSSIER_SPRITES + monde.getLogoFin());
            g2.fillRect(0,0,this.getWidth(), this.getHeight());
            g2.drawImage(tmp, 30, 50, tmp.getWidth(null),tmp.getHeight(null),null);
            g2.setColor(Color.WHITE);
			FontMetrics fontMetrics = g2.getFontMetrics();
			int longueur = fontMetrics.stringWidth(monde.getMessageFin());
			int hauteur = fontMetrics.getHeight();
			g2.setFont(new Font("Serif",Font.PLAIN,30));
			g2.drawString(monde.getMessageFin(),
			getWidth()/2 - longueur/2,
			getHeight()/2);
	}
    
    public void setTransparence(Graphics2D g2, float value){
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, value ));
	}
}
