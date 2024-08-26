/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import dao.PodatnikDAO;
import dao.UberprufungDAO;
import entity.Podatnik;
import entity.Uberprufung;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import msg.Msg;

@Named
@RequestScoped
public class UberprufungView implements Serializable {
    private static final long serialVersionUID = 1L;

    @EJB
    private UberprufungDAO uberprufungDAO;

    @EJB
    private PodatnikDAO podatnikDAO;

    private List<Uberprufung> uberprufungList;
    private Uberprufung newUberprufung;
    private List<Podatnik> podatnikList;

    @PostConstruct
    public void init() {
        uberprufungList = uberprufungDAO.findAll();
        newUberprufung = new Uberprufung();
        podatnikList = podatnikDAO.findAll();
    }

    public List<Uberprufung> getUberprufungList() {
        return uberprufungList;
    }

    public Uberprufung getNewUberprufung() {
        return newUberprufung;
    }

    public void setNewUberprufung(Uberprufung newUberprufung) {
        this.newUberprufung = newUberprufung;
    }

    public List<Podatnik> getPodatnikList() {
        return podatnikList;
    }

    public void setPodatnikList(List<Podatnik> podatnikList) {
        this.podatnikList = podatnikList;
    }

    public void createUberprufung() {
        try {
            uberprufungDAO.create(newUberprufung);
            uberprufungList.add(newUberprufung);
            newUberprufung = new Uberprufung(); // Reset formularza
            Msg.msg("Dodano Uberprufung");
        } catch (Exception e) {
            Msg.msg("e", "Błąd podczas dodawania Uberprufung: " + e.getMessage());
        }
    }

    public void deleteUberprufung(Uberprufung uberprufung) {
        try {
            uberprufungDAO.remove(uberprufung);
            uberprufungList.remove(uberprufung);
            Msg.msg("Usunięto Uberprufung");
        } catch (Exception e) {
            Msg.msg("e", "Błąd podczas usuwania Uberprufung: " + e.getMessage());
        }
    }
}
