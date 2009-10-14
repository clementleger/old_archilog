import java.io.*;
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.filter.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class ParserXML {

    private String nomFichier;
    private Element racine;
    private org.jdom.Document document;
    private MondeConcret monde;
    
    public ParserXML(MondeConcret m, String nom) {
        nomFichier = nom;
        monde = m;
        SAXBuilder sxb = new SAXBuilder();
        try {
          document = sxb.build(getClass().getClassLoader().getResource(nom));
        }
        catch(Exception e) {
            System.out.println(e);
        }
        racine = document.getRootElement();
    }

    public void genererMap() {
        
        String mapDebut = racine.getAttributeValue("mapdebut");
        String messDebut = racine.getAttributeValue("messagedebut");
        String logoFin = racine.getAttributeValue("logofin");
        String musicFin = racine.getAttributeValue("musiquefin");
        String messageFin = racine.getAttributeValue("messagefin");
        String objetGagnant = racine.getAttributeValue("objetgagnant");
        
        {
			Element pers = (Element)(racine.getChildren("personnage")).get(0);
			String nomPers = pers.getAttributeValue("nom");
			String nomImagePers = pers.getAttributeValue("nomimage");

			PersonnagePrincipal p = new PersonnagePrincipal(0, 0, nomPers, nomImagePers);
			monde.setPersonnage(p);
		}

        List listeMaps = racine.getChildren("map");
        Iterator i = listeMaps.iterator();

        while(i.hasNext()) {
            Element mapCourante = (Element)i.next();
            String nom = mapCourante.getAttributeValue("nom");
            String description = mapCourante.getAttributeValue("description");
            Integer tailleCase = Integer.parseInt(mapCourante.getAttributeValue("taillecase"));
            Integer caseDepartX = Integer.parseInt(mapCourante.getAttributeValue("casedepartx"));         
            Integer caseDepartY = Integer.parseInt(mapCourante.getAttributeValue("casedeparty"));
            String sensTmp = mapCourante.getAttributeValue("sensdepart");
            Map map = new MapConcrete(nom, description, tailleCase, new Case(caseDepartX, caseDepartY), Constant.stringToSens(sensTmp));

			/* PERSONNAGES AMBULANTS */
            List persoAmbulant = mapCourante.getChildren("personnage");
            Iterator j = persoAmbulant.iterator();
            while(j.hasNext()){
                Element persoCourant = (Element)j.next();
                String typePersonnage = persoCourant.getAttributeValue("type");
                String nomPers = persoCourant.getAttributeValue("nom");
                String nomImage = persoCourant.getAttributeValue("nomimage");
                Integer posPersX = Integer.parseInt(persoCourant.getAttributeValue("posx"));
                Integer posPersY = Integer.parseInt(persoCourant.getAttributeValue("posy"));               
                String messageAvantObjet = persoCourant.getAttributeValue("message");   
                if( typePersonnage.equals("ambulant")){
					Integer toPersX = Integer.parseInt(persoCourant.getAttributeValue("tox"));
					Integer toPersY = Integer.parseInt(persoCourant.getAttributeValue("toy"));
					Integer vitessePers = Integer.parseInt(persoCourant.getAttributeValue("vitesse"));
					map.ajouterPersonnage(new PersonnageAmbulant(posPersX, posPersY, nomPers, nomImage, toPersX, toPersY, vitessePers, messageAvantObjet));     
				} else if( typePersonnage.equals("fixe")){
					String sens = persoCourant.getAttributeValue("sens");
					map.ajouterPersonnage(new PersonnageFixe(posPersX, posPersY, nomPers, nomImage, Constant.stringToSens(sens), messageAvantObjet));   
				}                                          
            } 


			/* OBJETS*/
            List listeObjets = mapCourante.getChildren("objet");
             j = listeObjets.iterator();
            while(j.hasNext()) {
                Element objetCourant = (Element)j.next();
                String typeObjet = objetCourant.getAttributeValue("type");
                if(typeObjet.equals("bloquant")) {
                    Integer caseX = Integer.parseInt(objetCourant.getAttributeValue("casex")); 
                    Integer caseY = Integer.parseInt(objetCourant.getAttributeValue("casey"));
                    ObjetTerrain o = new ObjetBloquant();
                    map.setObjetTerrain(o, new Case(caseX, caseY));
                }
                else if(typeObjet.equals("teleportation") || typeObjet.equals("teleportationdeblocable")) {
                    Integer caseX = Integer.parseInt(objetCourant.getAttributeValue("casex")); 
                    Integer caseY = Integer.parseInt(objetCourant.getAttributeValue("casey"));
                    String mapSuivante = objetCourant.getAttributeValue("mapsuivante");
                    Integer caseArriveeX = Integer.parseInt(objetCourant.getAttributeValue("casearriveex"));
                    Integer caseArriveeY = Integer.parseInt(objetCourant.getAttributeValue("casearriveey"));
                    sensTmp = objetCourant.getAttributeValue("sensarrivee");
                    if(typeObjet.equals("teleportationdeblocable")) {
                        String message = objetCourant.getAttributeValue("message");
                        String liste = objetCourant.getAttributeValue("listeobjets");
                        ObjetTeleportationDeblocable o = new ObjetTeleportationDeblocable(message, mapSuivante, new Case(caseArriveeX, caseArriveeY), Constant.stringToSens(sensTmp));
                        map.setObjetTerrain(o, new Case(caseX, caseY));
                        Scanner sc = new Scanner(liste).useDelimiter(",");
                        while(sc.hasNext()) {
                                o.ajouterObjet(sc.next());
                        }
                    }
                    else {
                        ObjetTerrain o = new ObjetTeleportation(mapSuivante, new Case(caseArriveeX, caseArriveeY),  Constant.stringToSens(sensTmp)); 
                        map.setObjetTerrain(o, new Case(caseX, caseY));
                    }
                }
                else if(typeObjet.equals("ramassable")) {
                    int x = Integer.parseInt(objetCourant.getAttributeValue("x"));
                    int y = Integer.parseInt(objetCourant.getAttributeValue("y"));
                    String nomObjet = objetCourant.getAttributeValue("nom");
                    String imageObjet = objetCourant.getAttributeValue("image");
                    ObjetRamassable o  = new ObjetRamassable(nomObjet, imageObjet, x, y);
                    map.ajouterObjetRamassable(o);
                }

                else if(typeObjet.equals("transmettable")) {
                    String nomObjet = objetCourant.getAttributeValue("nom");
                    String imageObjet = objetCourant.getAttributeValue("image");
                    String perso = objetCourant.getAttributeValue("personnage");
                    String messageApres = objetCourant.getAttributeValue("messageapresobjet");
                    ObjetTransmettable o  = new ObjetTransmettable(nomObjet, imageObjet);
                    ArrayList<PersonnageInteractif> listePerso = map.getListePersos();
                    for(PersonnageInteractif persA: listePerso) {
                        if(persA.getNom().equals(perso)) {
                            StrategiePersonnagePorteur  strat= new StrategiePersonnagePorteur(persA, messageApres, o); 
                            persA.setStrategie(strat);
                            break;
                        }
                    }
                }
            }
            monde.ajoutMap(nom, map);       
        }

        monde.setMapCourante(mapDebut);
        monde.setNomObjetGagnant(objetGagnant);
        monde.setMusicFin(musicFin);
        monde.setMessageFin(messageFin);
        monde.setLogoFin(logoFin);
        monde.setConversation(messDebut);
    }
}
