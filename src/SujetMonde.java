import java.util.ArrayList;
import java.util.Timer;

public class SujetMonde implements Monde {

	private Monde monde;
    private ArrayList<Observateur> observateursMonde;
    private ArrayList<ObservateurMessage> observateursMessage;
    private ArrayList<ObservateurSacADos> observateursSacADos;
    private String dernierNomMap;
    private Timer timer;
    
	public SujetMonde(Monde m){
		monde = m;
        observateursMonde = new ArrayList<Observateur>();
        observateursMessage = new ArrayList<ObservateurMessage>();
        observateursSacADos = new ArrayList<ObservateurSacADos>();
		dernierNomMap = "";
        bougerListePerso();
	}

    public void bougerListePerso(){
        timer = new Timer();
        timer.schedule(new BougePersonnageAmbulant(this), 0, 150);
    }

    public void notifieObsMonde(){
        for(Observateur o : observateursMonde)
            o.miseAJour();
    }

    public Monde getMondeConcret() {
        return monde;
    }
    
    public void notifieObsMessage(){
        for(ObservateurMessage o : observateursMessage)
            o.miseAJour();
    }

    public void notifieObsSacADos(){
        for(ObservateurSacADos o : observateursSacADos)
            o.miseAJour(monde.getPersonnage().getSacADos());
    }
    
    public void ajoutObservateurMonde(Observateur o){
        observateursMonde.add(o);
    }  
      
    public void ajoutObservateurMessage(ObservateurMessage o){
        observateursMessage.add(o);
    }

    public void ajoutObservateurSacADos(ObservateurSacADos o){
        observateursSacADos.add(o);
    }
    

    public PersonnagePrincipal getPersonnage() {
        return monde.getPersonnage();
    }

     public void deplacerPersonnage(int sens){
        monde.deplacerPersonnage(sens);
        notifieObsMonde();
		notifieObsMessage();
       // notifieObsSacADos(); //appellee quand on change de map pr enlever du sac a dos les objets ramassable deblocable
     }

    public Map getMapCourante(){
	    return monde.getMapCourante();
	}
    
    public void setCaseCourante(Case caseCourante){
        monde.setCaseCourante(caseCourante);
    }

    public void ramasser(){
        monde.ramasser();
        notifieObsMonde();
        notifieObsSacADos();
    }
    
    public void parler(){
        monde.parler();
        notifieObsSacADos();
    }
    
	public String getMessageAction(){
		return monde.getMessageAction();
	}
    
	public String getConversation(){
		return monde.getConversation();
	}
	
	public void setConversation(String conv){
		monde.setConversation(conv);
		notifieObsMonde();
	}
    public boolean getJeuTermine() {
        return monde.getJeuTermine();
    }
	
	public boolean enConversation(){
		return monde.enConversation();
	}
}
