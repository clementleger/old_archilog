/**
 * Classe définissant les constantes utilisées
 */
public class Constant{
    
    //Définition des répertoires
    public final static String DOSSIER_SPRITES = "sprites/";
    public final static String DOSSIER_MAPS = "maps/";
    public final static String DOSSIER_PERSOS = "persos/";
    public final static String DOSSIER_OBJETS = "objets/";
    public final static String DOSSIER_MENU = "menu/";
    public final static String DOSSIER_HUD = "hud/";
    public final static String RAYONS	= "rayons.png";
    
    public final static String CONTINUE_STRING = "Appuyez sur Entrée pour continuer";
    
    //Définition des sens
    public final static int BAS = 0;
    public final static int GAUCHE = 1;
    public final static int DROITE = 2;
    public final static int HAUT = 3;
    
    public static String sensToString(int c){
        switch(c){
            case Constant.BAS : return "bas";
            case Constant.HAUT : return "haut";
            case Constant.GAUCHE : return "gauche";
            case Constant.DROITE : return "droite";
            default : return "bas";
        }
    }   
    
    public static int stringToSens(String sens){
        if(sens.equals("bas"))
			return Constant.BAS;
		else if(sens.equals("haut"))
			return Constant.HAUT;
		else if(sens.equals("droite"))
			return Constant.DROITE;
		else if(sens.equals("gauche"))
			return Constant.GAUCHE;
		else
			return Constant.BAS;
    }
}
