/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.KontoDAOfk;
import dao.PodatnikDAO;
import dao.RemanentDAO;
import embeddable.Parametr;
import entity.Podatnik;
import entity.Remanent;
import entityfk.Konto;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class RemanentView implements Serializable {

    @Inject
    private WpisView wpisView;
    @Inject
    private RemanentDAO remanentDAO;
    @Inject
    private PodatnikDAO podatnikDAO;
    private List<Remanent> remanentypodatnika;
    private Remanent selected;

    public RemanentView() {
        selected = new Remanent();
    }
    
    

    @PostConstruct
    private void init() { //E.m(this);
        try {
            try {
                 remanentypodatnika = remanentDAO.findPodatnik(wpisView.getPodatnikObiekt());
            } catch (Exception e) { E.e(e); 
                    Msg.msg("e", "Nie wprowadzono remanentu! Program nie obliczy PIT-u za grudzien.");
            }
        } catch (Exception ex) {
            Msg.msg("e", "Problem z pobraniem podatnika. Nie mogę obliczyć różnicy remanentu.");
        }
    }
    
    //to jest tranzycja rem
    public void tworzremanent() {
        List<Podatnik> findAllManager = podatnikDAO.findAllManager();
        for (Podatnik pod : findAllManager) {
            List<Remanent> listaremanentow = new ArrayList<>();
            List<Parametr> remanentLista = pod.getRemanent();
            if (remanentLista!=null&&remanentLista.size()>0) {
                for (Parametr par : remanentLista) {
                    try {
                        if (par.getParametr()!=null&&par.getParametr().equals("")==false) {
                            String parametrweryf = par.getParametr().replace(",", ".");
                            double remanentkwota = Double.valueOf(parametrweryf);
                            Remanent remanent = new Remanent(par.getRokOd(), remanentkwota, pod);
                            listaremanentow.add(remanent);
                        }
                    } catch (Exception e) {
                        System.out.println("");
                    }
                }
                remanentDAO.createList(listaremanentow);
            }
            System.out.println(pod.getPrintnazwa()+" rem koniec");
        }
    }
    
     public void dodajremanent() {
        try {
            selected.setPodid(wpisView.getPodatnikObiekt());
            remanentDAO.create(selected);
            remanentypodatnika.add(selected);
            selected = new Remanent();
            Msg.msg("Dodatno parametr remanent do podatnika: "+wpisView.getPrintNazwa());
        } catch (Exception e) {
            Msg.msg("e","Błąd przy dodawaniu remanentu.");
        }
    }

    public void usunremanent(Remanent selected) {
        if (selected!=null) {
            remanentDAO.remove(selected);
            remanentypodatnika.remove(selected);
            Msg.msg("Usunięto remanent");
        }
    }

    public List<Remanent> getRemanentypodatnika() {
        return remanentypodatnika;
    }

    public void setRemanentypodatnika(List<Remanent> remanentypodatnika) {
        this.remanentypodatnika = remanentypodatnika;
    }

    public Remanent getSelected() {
        return selected;
    }

    public void setSelected(Remanent selected) {
        this.selected = selected;
    }

    
   

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }


    
    
}
