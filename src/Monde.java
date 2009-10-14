import java.util.ArrayList;

public interface Monde{

    public PersonnagePrincipal getPersonnage();
    public void deplacerPersonnage(int sens);
    public Map getMapCourante();
    public void setCaseCourante(Case caseCourante);
    public boolean getJeuTermine();
    public void parler();
    public void ramasser();
    public String getMessageAction();
    public String getConversation();
    public boolean enConversation();
    public void setConversation(String conv);
}
