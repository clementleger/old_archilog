public class StrategiePersonnagePorteur implements StrategiePersonnage {

    private String messageApresInteraction;
    private ObjetTransmettable objTrans;
    boolean objetDejaDonne;
    private PersonnageInteractif perso;

    public StrategiePersonnagePorteur(PersonnageInteractif p, String messApres, ObjetTransmettable obj) {
        messageApresInteraction = messApres;
        objTrans = obj;
        objetDejaDonne = false;
        perso = p;
    }

    public ObjetTransmettable interaction() {
        if(!objetDejaDonne) {
            objetDejaDonne = true;
            perso.setMessageInteraction(messageApresInteraction);             
            return objTrans; 
        }
        else {
            return null;
        }
    }
}
