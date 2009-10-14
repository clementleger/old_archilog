import javax.swing.JButton;
import javax.swing.event.MouseInputAdapter;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Rectangle2D;
import java.awt.AlphaComposite;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.awt.Font;
import java.awt.FontMetrics;
import java.util.Timer;
import java.util.TimerTask;

public class VueMenu extends JPanel{
	private StockImage stockImage;
	private final MondeFacade mf;
	private int transition;
	private int imageCourante;
	private int imageSuivante;
	private Timer timer;
	private Timer timerFading;
	private boolean enTransition;
	private MyButton pres0, pres1, nouveau;
	private BufferedImage []imagesFond;
	private BufferedImage imageFond;
	
	public VueMenu(MondeFacade mf){
		imageCourante = 0;
		imageSuivante = 0;
		this.mf = mf;
		setLayout(null);
		enTransition = false;
		timer = new Timer();
        timer.schedule(new AnimationFond(), 3000, 5000);
		stockImage = StockImage.get();
		nouveau = new MyButton("Nouveau Jeu","panneau.gif",0,0);
		nouveau.setActionCommand("nouveaujeu");
		pres0 = new MyButton("","presentation0.gif",0,0);
		pres0.setActionCommand("pres0");
		pres1 = new MyButton("","presentation1.gif",0,0);
		pres1.setActionCommand("pres1");
		pres0.addActionListener(new ButtonHandler());
		pres1.addActionListener(new ButtonHandler());
		nouveau.addActionListener(new ButtonHandler());
		add(nouveau);
		addKeyListener(new KeyboardHandler());
		addComponentListener(new PanelHandler());
		imagesFond = new BufferedImage[4];
		for (int i = 0; i < 4; i++)
		{
			imagesFond[i] = stockImage.getImage(Constant.DOSSIER_SPRITES + Constant.DOSSIER_MENU +"image"+i+".png");
		}
		imageFond = stockImage.getCacheImage(Constant.DOSSIER_SPRITES + Constant.DOSSIER_MENU +"fond.png");
		
	}
	
	private class ButtonHandler implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if( e.getActionCommand().equals("nouveaujeu")){
				remove(nouveau);
				add(pres0);
				repaint();
			}
			else if( e.getActionCommand().equals("pres0")){
				remove(pres0);
				add(pres1);
				repaint();
			}
			else if( e.getActionCommand().equals("pres1")){
				nouveauJeu();
			}
		}
	}
	
	private class PanelHandler extends ComponentAdapter{
		public void componentResized(ComponentEvent e){
			nouveau.setLocation(getWidth()/2 - nouveau.getWidth()/2, 20);
			pres0.setLocation(getWidth()/2 - pres0.getWidth()/2, getHeight()/2 - pres0.getHeight()/2);
			pres1.setLocation(getWidth()/2 - pres1.getWidth()/2, getHeight()/2 - pres1.getHeight()/2);
		}
	}
	
	public void nouveauJeu(){
		if(timer != null)
			timer.cancel();
		if(timerFading != null){
			timerFading.cancel();
			enTransition = false;
			imageCourante = imageSuivante;
		}
		removeAll();
		mf.nouveauJeu();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(imageFond,0,0, this.getWidth(), this.getHeight(),null);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
        dessinerImage(g2);
	}
	

	public void dessinerImage(Graphics2D g2){
		Image courante = imagesFond[imageCourante];
		g2.drawImage(courante,this.getWidth()/2 -  courante.getWidth(null)/2 ,
					this.getHeight()/2 - courante.getHeight(null)/2,
					courante.getWidth(null), courante.getHeight(null),null);
		if(enTransition){
			setTransparence(g2,transition*0.1f);
			Image suivante = imagesFond[imageSuivante];
			g2.drawImage(suivante,this.getWidth()/2 -  suivante.getWidth(null)/2 ,
						this.getHeight()/2 - suivante.getHeight(null)/2,
						suivante.getWidth(null), suivante.getHeight(null),null);
		}
		setTransparence(g2,1.0f);
	}
	
		
    public void setTransparence(Graphics2D g2, float value){
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, value ));
	}
	
	private class AnimationFond extends TimerTask{
		public void run(){
			enTransition = true;
			imageSuivante = (imageCourante+1)%4;
			timerFading = new Timer();
			timerFading.schedule(new AnimationFading(),0,100);
		}
	}
	
	private class AnimationFading extends TimerTask{
		public void run(){
			if(transition++ == 10){
				transition = 0;
				enTransition = false;
				imageCourante = imageSuivante;
				timerFading.cancel();
			}
			repaint();
		}
	}
	
	private class KeyboardHandler extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			int k = e.getKeyCode();
			if(k == KeyEvent.VK_ESCAPE)
				System.exit(0);
		}
	}
	
	private class MyButton extends JButton{
		Image image;
		private boolean mouseOver;
		Color color;
		
		public MyButton(String text, String image, int x, int y){
			super(text);
			color = Color.WHITE;
			mouseOver = false;
			this.setOpaque(false);
			this.image = StockImage.get().getImage(Constant.DOSSIER_SPRITES + Constant.DOSSIER_MENU + image);
			this.addMouseListener(new MouseFocusHandler());
			this.setBounds(x, y, this.image.getWidth(null), this.image.getHeight(null));	
		}

		public void paintComponent(Graphics g){
			Graphics g2 = (Graphics) g;
			g2.drawImage(image, 0, 0, getWidth(), getHeight(), null);
			FontMetrics fontMetrics = g2.getFontMetrics();
			int longueur = fontMetrics.stringWidth(getText());
			int hauteur = fontMetrics.getHeight();
			g2.setFont(new Font("Serif", Font.BOLD, 20));
			g2.setColor(color);
			g2.drawString(getText(),getWidth()/2 - longueur/2 - 20,getHeight()/2 + hauteur/2);
		}
		
		public void paintBorder(Graphics g){
		}
		
		private class MouseFocusHandler extends MouseInputAdapter{
			public void mouseEntered(MouseEvent e){
				color = Color.BLACK;
			}
			public void mouseExited(MouseEvent e){
				color = Color.WHITE;
			}
		}
	}
}
