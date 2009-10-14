import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import javax.imageio.ImageIO;

public class StockImage{

	private static StockImage single = new StockImage();
	private HashMap<String,BufferedImage[][]> imagesPersonnage = new HashMap<String,BufferedImage[][]>();
	private HashMap<String,BufferedImage> imagesCache = new HashMap<String,BufferedImage>();
	
	public static StockImage get() {
		return single;
	}

	public BufferedImage getCacheImage(String ref){
		if( imagesCache.containsKey(ref)){
			return imagesCache.get(ref);
		}
		BufferedImage tmp = getImage(ref);
		imagesCache.put(ref,tmp);
		return tmp;
	}
	
	public BufferedImage getImage(String ref) {
		BufferedImage sourceImage = null;
		
		try {
			URL url = this.getClass().getClassLoader().getResource(ref);
			
			if (url == null) {
				System.err.println("Erreur de chargement d'image ("+ref+")...Exit ! terminé bonsoir");
				System.exit(0);
			}
			sourceImage = ImageIO.read(url);
		} catch (IOException e) {
				e.printStackTrace();
				System.err.println("Erreur de chargement d'image ("+ref+")...Exit ! terminé bonsoir");
				System.exit(0);
		}
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		BufferedImage image = gc.createCompatibleImage(sourceImage.getWidth(),sourceImage.getHeight(),Transparency.TRANSLUCENT);
		
		image.getGraphics().drawImage(sourceImage,0,0,null);
        return image;
	}
	
	public BufferedImage[][] getImagesPersonnage(String perso){
		if (imagesPersonnage.containsKey(perso)){
			return imagesPersonnage.get(perso);
		}
		BufferedImage imagePerso = getImage(perso);
		BufferedImage[][] tiles = new BufferedImage[4][4];
		int largeurTile = imagePerso.getWidth(null)/4;
		int hauteurTile = imagePerso.getHeight(null)/4;
		for (int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				tiles[i][j] = imagePerso.getSubimage(i*largeurTile,j*hauteurTile,largeurTile,hauteurTile);
			}
		}
		imagesPersonnage.put(perso,tiles);
		return tiles;
	}
}
