/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zbiorczy;

/**
 *
 * @author Osito
 */
public class Raport {
    private int id;
    private Sprawa sprawa;
    private String dataraportu;
    private String sporzadzajacy;
    private boolean doreczony;

    public Raport() {
    }

    public Raport(int id, Sprawa sprawa, String dataraportu, String sporzadzajacy, boolean doreczony) {
        this.id = id;
        this.sprawa = sprawa;
        this.dataraportu = dataraportu;
        this.sporzadzajacy = sporzadzajacy;
        this.doreczony = doreczony;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Sprawa getSprawa() {
        return sprawa;
    }

    public void setSprawa(Sprawa sprawa) {
        this.sprawa = sprawa;
    }

    public String getDataraportu() {
        return dataraportu;
    }

    public void setDataraportu(String dataraportu) {
        this.dataraportu = dataraportu;
    }

    public String getSporzadzajacy() {
        return sporzadzajacy;
    }

    public void setSporzadzajacy(String sporzadzajacy) {
        this.sporzadzajacy = sporzadzajacy;
    }

    public boolean isDoreczony() {
        return doreczony;
    }

    public void setDoreczony(boolean doreczony) {
        this.doreczony = doreczony;
    }

    
    
    
}
