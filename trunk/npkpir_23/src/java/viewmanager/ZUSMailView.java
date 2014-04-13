/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewmanager;

import dao.PodatnikDAO;
import embeddable.Mce;
import entity.Podatnik;
import entity.Zusmail;
import entity.ZusmailPK;
import entity.Zusstawki;
import entity.ZusstawkiPK;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import org.joda.time.DateTime;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class ZUSMailView implements Serializable {
    
    private static String rok;
    private static String mc;
    private static List<Zusmail> wykazprzygotowanychmaili;
    private static Map<String, Zusstawki> stawkipodatnicy;
    @Inject
    private PodatnikDAO podatnikDAO;
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;

    public ZUSMailView() {
        wykazprzygotowanychmaili = new ArrayList<>();
        stawkipodatnicy = new HashMap<>();
    }
    
    @PostConstruct
    private void init() {
        rok = String.valueOf((new DateTime()).getYear());
        mc = Mce.getMapamcy().get((new DateTime()).getMonthOfYear());
        pobierzstawki();
        przygotujmaile();
    }
    
    private void pobierzstawki() {
        stawkipodatnicy.clear();
        List<Podatnik> podatnicy = podatnikDAO.findAll();
        ZusstawkiPK zusstawkiPK = new ZusstawkiPK(rok, mc);
        for (Podatnik p : podatnicy) {
            List<Zusstawki> zusstawki = p.getZusparametr();
            if (zusstawki != null) {
                for (Zusstawki r : zusstawki) {
                    if (r.getZusstawkiPK().equals(zusstawkiPK)) {
                        stawkipodatnicy.put(p.getNazwapelna(), r);
                    }
                }
            }
        }
    }
    
    public void przygotujmaile() {
        wykazprzygotowanychmaili.clear();
        if (!stawkipodatnicy.isEmpty()) {
            Set<String> pobranipodatnicy = stawkipodatnicy.keySet();
            for (String p : pobranipodatnicy) {
                Zusstawki zusstawki = stawkipodatnicy.get(p);
                ZusmailPK zusmailPK = new ZusmailPK(p, rok, mc);
                Zusmail zusmail = new Zusmail(zusmailPK);
                zusmail.setZus51ch(zusstawki.getZus51ch());
                zusmail.setZus51bch(zusstawki.getZus51bch());
                zusmail.setZus52(zusstawki.getZus52());
                zusmail.setZus52odl(zusstawki.getZus52odl());
                zusmail.setZus53(zusstawki.getZus53());
                zusmail.setPit4(zusstawki.getPit4());
                zusmail.setTytul(String.format("Taxman - zestawienie kwot ZUS/PIT4 za %s/%s", rok, mc));
                zusmail.setTresc("Trescmaila");
                zusmail.setAdresmail((podatnikDAO.find(p)).getEmail());
                zusmail.setWysylajacy(wpisView.getWprowadzil().getLogin());
                if (!wykazprzygotowanychmaili.contains(zusmail)) {
                    wykazprzygotowanychmaili.add(zusmail);
                } else {
                    Msg.msg("duplitak");
                }
            }
        }
    }
    
    
    public void wiadomosczmiana(String cozmieniono) {
        Msg.msg(String.format("Dokonano zmiany: %s", cozmieniono));
        pobierzstawki();
        przygotujmaile();
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public List<Zusmail> getWykazprzygotowanychmaili() {
        return wykazprzygotowanychmaili;
    }

    public void setWykazprzygotowanychmaili(List<Zusmail> wykazprzygotowanychmaili) {
        this.wykazprzygotowanychmaili = wykazprzygotowanychmaili;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    
    
    
}
