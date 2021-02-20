/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DeklaracjevatDAO;
import dao.PitDAO;
import dao.PlatnosciDAO;
import dao.PodatnikDAO;
import dao.RyczDAO;
import dao.UzDAO;
import embeddable.Mce;
import entity.Deklaracjevat;
import entity.Pitpoz;
import entity.Platnosci;
import entity.PlatnosciPK;
import entity.Podatnik;
import entity.Ryczpoz;
import entity.Zusstawki;
import error.E;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class PlatnosciTablicaView implements Serializable {
    private static final long serialVersionUID = 1L;
    List<Platnosci> lista;
    @Inject
    PlatnosciDAO platnosciDAO;
    @Inject
    private WpisView wpisView;
    @Inject
    private PitDAO pitDAO;
    @Inject
    private RyczDAO ryczDAO;
    @Inject
    PodatnikDAO podatnikDAO;
    @Inject
    private DeklaracjevatDAO deklaracjevatDAO;
    @Inject
    private UzDAO uzDAO;
    @Inject
    

    public PlatnosciTablicaView() {
        lista = Collections.synchronizedList(new ArrayList<>());
    }
    

    @PostConstruct
    private void init() { //E.m(this);
        try {
            for (String mc : Mce.getNumberToMiesiac().values()) {
                lista.add(nowezobowiazanie(wpisView.getRokWpisuSt(), mc, wpisView.getPodatnikObiekt()));
            }
        } catch (Exception e) { E.e(e); 
        }
    }

    public Platnosci nowezobowiazanie(String rok, String mc, Podatnik biezacyPodatnik) {
        Platnosci platnosci = new Platnosci();
        List<Zusstawki> listapobrana = biezacyPodatnik.getZusparametr();
        if (listapobrana != null) {
            Iterator it = listapobrana.iterator();
            while (it.hasNext()) {
                Zusstawki zusstawki = (Zusstawki) it.next();
                if (zusstawki.getZusstawkiPK().getRok().equals(rok) && zusstawki.getZusstawkiPK().getMiesiac().equals(mc)) {
                    platnosci.setZus51(zusstawki.getZus51ch());
                    platnosci.setZus52(zusstawki.getZus52());
                    platnosci.setZus53(zusstawki.getZus53());
                    platnosci.setPit4(zusstawki.getPit4());
                    break;
                }
            }
        }
        //pobierz PIT-5
        try {
            if (wpisView.isKsiegaryczalt() == true) {
                Pitpoz pitpoz = pitDAO.find(rok, mc, biezacyPodatnik.getNazwapelna());
                platnosci.setPit5(pitpoz.getNaleznazal().doubleValue());
            } else {
                Ryczpoz ryczpoz = ryczDAO.find(rok, mc, biezacyPodatnik.getNazwapelna());
                platnosci.setPit5(ryczpoz.getNaleznazal().doubleValue());
            }
        } catch (Exception e) { E.e(e); 
            platnosci.setPit5(0.0);
        }
        //pobierz VAT-7
        try {
            Deklaracjevat dekl = new Deklaracjevat();
            try {
                List<Deklaracjevat> deklaracje = deklaracjevatDAO.findDeklaracjewszystkie(rok, mc, biezacyPodatnik.getNazwapelna());
                dekl = deklaracje.get(deklaracje.size() - 1);
                if (dekl.getSelected().getPozycjeszczegolowe().getPoleI58() != 0) {
                    platnosci.setVat(Double.parseDouble(dekl.getSelected().getPozycjeszczegolowe().getPole58()));
                } else {
                    platnosci.setVat(0 - Double.parseDouble(dekl.getSelected().getPozycjeszczegolowe().getPole60()));
                }
            } catch (Exception e) { E.e(e); 
                platnosci.setVat(0 - Double.parseDouble(dekl.getSelected().getPozycjeszczegolowe().getPole60()));
            }
        } catch (Exception e) { E.e(e); 
            platnosci.setVat(0.0);
        }
        try {
            PlatnosciPK platnosciPK = new PlatnosciPK();
            platnosciPK.setMiesiac(mc);
            platnosciPK.setRok(rok);
            platnosciPK.setPodatnik(biezacyPodatnik.getNazwapelna());
            platnosci.setPlatnosciPK(platnosciPK);
        } catch (Exception e) { E.e(e); 
        }
        //platnosci.setVatsuma(platnosci.getVat()+platnosci.getVatods());
        return platnosci;
    }
    
    private void aktualizujGuest(){
      wpisView.naniesDaneDoWpis();
    }
     private void aktualizuj(){
        wpisView.naniesDaneDoWpis();
    }
    
     public void aktualizujTablice() throws IOException {
        aktualizujGuest();
        aktualizuj();
        init();
        //FacesContext.getCurrentInstance().getExternalContext().redirect(strona);
    }

    public List<Platnosci> getLista() {
        return lista;
    }

    public void setLista(List<Platnosci> lista) {
        this.lista = lista;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

}
