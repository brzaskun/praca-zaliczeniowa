/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewmanager;

import comparator.Podatnikcomparator;
import dao.PodatnikDAO;
import dao.SMTPSettingsDAO;
import dao.ZusmailDAO;
import embeddable.Mce;
import entity.Podatnik;
import entity.Zusmail;
import entity.Zusstawki;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import mail.MaiManager;
import msg.Msg;
import org.joda.time.DateTime;
import org.primefaces.event.SelectEvent;
import view.WpisView;import waluty.Z;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class ZUSMailView implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String rok;
    private String mc;
    private String mcuprzedni;
    private int nrwysylki;
    private List<Integer> numery;
    private List<Zusmail> wykazprzygotowanychmaili;
    private List<Zusmail> wybranemaile;
    private Map<Podatnik, Zusstawki> stawkipodatnicy;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private ZusmailDAO zusmailDAO;
    @Inject
    private SMTPSettingsDAO sMTPSettingsDAO;
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;

    public ZUSMailView() {
        wykazprzygotowanychmaili = Collections.synchronizedList(new ArrayList<>());
        stawkipodatnicy = new ConcurrentHashMap<>();
        wybranemaile = Collections.synchronizedList(new ArrayList<>());
        numery = Collections.synchronizedList(new ArrayList<>());
    }
    
    @PostConstruct
    private void init() {
        rok = String.valueOf((new DateTime()).getYear());
        mc = Mce.getNumberToMiesiac().get((new DateTime()).getMonthOfYear());
        mcuprzedni = Mce.zmniejszmiesiac(rok, mc)[1];
        pobierzstawki();
        przygotujmaile();
        for (int i = 0 ; i < 12 ; i++) {
            numery.add(i);
        }
    }
    
    private void pobierzstawki() {
        stawkipodatnicy.clear();
        List<Podatnik> podatnicy = podatnikDAO.findAll();
        Collections.sort(podatnicy, new Podatnikcomparator());
        for (Podatnik p : podatnicy) {
            if (p.isWysylkazusmail() == true) {
                List<Zusstawki> zusstawki = p.getZusparametr();
                if (zusstawki != null) {
                    for (Zusstawki r : zusstawki) {
                        if (r.getZusstawkiPK().getRok().equals(rok) && r.getZusstawkiPK().getMiesiac().equals(mc)){
                            stawkipodatnicy.put(p, r);
                        }
                    }
                }
            }
        }
    }
    private static final String trescmaila = "<p> Szanowny Podatniku</p> <p> W niniejszym mailu znajdziesz naliczone kwoty zobowiązań z tytułu ZUS I PIT-4</p> "
            + "<p> do zapłaty/przelania w miesiącu <span style=\"color:#008000;\">%s/%s</span></p> "
            + " <table align=\"left\" border=\"1\" cellpadding=\"1\" cellspacing=\"1\" style=\"width: 500px;\"> <caption> zestawienie zobowiązań</caption> <thead> <tr> "
            + "<th scope=\"col\"> lp</th> <th scope=\"col\"> tytuł</th> <th scope=\"col\"> kwota</th> </tr> </thead> <tbody> <tr> <td style=\"text-align: center;\"> 1</td> "
            + "<td> ZUS 51</td> <td style=\"text-align: right;\"> %.2f</td> </tr> <tr> <td style=\"text-align: center;\"> 2</td>"
            + " <td> ZUS 52</td> <td style=\"text-align: right;\"> <span style=\"text-align: right;\">%.2f</span></td> </tr> <tr> <td style=\"text-align: center;\"> 3</td>"
            + " <td> ZUS 53</td> <td style=\"text-align: right;\"> <span style=\"text-align: right;\">%.2f</span></td> </tr> <tr> <td style=\"text-align: center;\"> 4</td> "
            + " <td> Razem ZUS do przelania</td> <td style=\"text-align: right;\"> <span style=\"text-align: right;\">%.2f</span></td> </tr> <tr> <td style=\"text-align: center;\"> 5</td><td>"
            + " PIT-4</td> <td style=\"text-align: right;\"> <span style=\"text-align: right;\">%.2f</span></td> </tr> <td style=\"text-align: center;\"> 5</td> <td>"
            + " PIT-8AR</td> <td style=\"text-align: right;\"> <span style=\"text-align: right;\">%.2f</span></td> </tr> </tbody> </table>"
            + " <p> &nbsp;</p> <p> &nbsp;</p> <p> &nbsp;</p> <p> &nbsp;</p> <p> &nbsp;</p> "
            + "<p> Ważne! Przelew do ZUS od stycznia 2018 robimy jedną kwotą na JEDNO indywidualne konto wskazane przez ZUS.</p>"
            + "<p> Przypominamy o terminach płatności ZUS:</p>"
            + " <p> do <span style=\"color:#008000;\">10-go</span> &nbsp;- dla os&oacute;b niezatrudniających pracownik&oacute;w</p>"
            + " <p> do <span style=\"color:#008000;\">15-go</span> - dla firm z pracownikami</p>"
            + " <p> Termin płatności podatku:</p>"
            + " <p> do <span style=\"color:#006400;\">20-go</span> - PIT-4/PIT-8 od wynagrodzeń pracownik&oacute;w</p>"
            + " <p> &nbsp;</p>";
    
    public void przygotujmaile() {
        wykazprzygotowanychmaili.clear();
        if (!stawkipodatnicy.isEmpty()) {
            List<Podatnik> pobranipodatnicy = Collections.synchronizedList(new ArrayList<>());
            pobranipodatnicy.addAll(stawkipodatnicy.keySet());
            Collections.sort(pobranipodatnicy, new Podatnikcomparator());
            for (Podatnik p : pobranipodatnicy) {
                Zusstawki zusstawki = stawkipodatnicy.get(p);
                if (sprawdzczyniepuste(zusstawki)) {
                    Zusmail zusmail = new Zusmail(p, rok, mc);
                    zusmail.setZus51ch(zusstawki.getZus51ch());
                    zusmail.setZus51bch(zusstawki.getZus51bch());
                    zusmail.setZus52(zusstawki.getZus52());
                    zusmail.setZus52odl(zusstawki.getZus52odl());
                    zusmail.setZus53(zusstawki.getZus53());
                    zusmail.setPit4(zusstawki.getPit4());
                    zusmail.setPit8(zusstawki.getPit8());
                    double zus51 = 0;
                    double zus52 = 0;
                    double zus53 = 0;
                    double pit4 = 0;
                    double pit8 = 0;
                    try {
                        zus51 = zusmail.getZus51ch() != null && zusmail.getZus51ch() != 0.0 ? zusmail.getZus51ch() : zusmail.getZus51bch();
                    } catch (Exception e) {
                    }
                    zus52 = zusmail.getZus52() != null ? zusmail.getZus52() : 0;
                    zus53 = zusmail.getZus53() != null ? zusmail.getZus53() : 0;
                    pit4 = zusmail.getPit4()!= null ? zusmail.getPit4(): 0;
                    pit8 = zusmail.getPit8()!= null ? zusmail.getPit8(): 0;
                    zusmail.setTytul(String.format("Taxman - zestawienie kwot ZUS/PIT4 za %s/%s", rok, mc));
                    double sumazus = Z.z(zus51+zus52+zus53);
                    zusmail.setTresc(String.format(new Locale("pl"),trescmaila, rok, mc, zus51, zus52, zus53, sumazus, pit4, pit8));
                    zusmail.setAdresmail(p.getEmail());
                    zusmail.setWysylajacy(wpisView.getUzer().getLogin());
                    if (!wykazprzygotowanychmaili.contains(zusmail)) {
                        wykazprzygotowanychmaili.add(zusmail);
                    }
                }
            }
            naniesistniejacezapisy();
            usunniezgodne();
        }
    }
     private boolean sprawdzczyniepuste(Zusstawki zusstawki) {
        if (zusstawki.getZus51ch() != null && zusstawki.getZus51ch() != 0.0 ) {
            return true;
        } else if (zusstawki.getZus51bch() != null && zusstawki.getZus51bch() != 0.0) {
            return true;
        } else if (zusstawki.getZus52() != null && zusstawki.getZus52() != 0.0) {
            return true;
        } else if (zusstawki.getZus53() != null && zusstawki.getZus53() != 0.0) {
            return true;
        } else if (zusstawki.getPit4() != null && zusstawki.getPit4() != 0.0) {
            return true;
        } else if (zusstawki.getPit8() != null && zusstawki.getPit8() != 0.0) {
            return true;
        }
        return false;
    }
    private void usunniezgodne() {
        Iterator it = wykazprzygotowanychmaili.iterator();
        while (it.hasNext()) {
            Zusmail p = (Zusmail) it.next();
            if (p.getNrwysylki() != nrwysylki) {
                it.remove();
            }
        }
    }
    private void naniesistniejacezapisy() {
        try {
            List<Zusmail> listazusmailwbazie = zusmailDAO.findZusRokMc(rok,mc);
            if (listazusmailwbazie != null) {
                for (Zusmail p : listazusmailwbazie) {
                    for (Zusmail r : wykazprzygotowanychmaili) {
                        if (r.getPodatnik().equals(p.getPodatnik()) && r.getRok().equals(p.getRok()) && r.getMc().equals(p.getMc())) {
                            r.setDatawysylki(p.getDatawysylki());
                            r.setNrwysylki(p.getNrwysylki());
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            Msg.msg("w", "Nie ma zapisów wysłanych maili w bazie za dany okres");
        }
    }
    
    
    public void dokonajZmianyElementu(String cozmieniono) {
        Msg.msg(String.format("Dokonano zmiany: %s", cozmieniono));
        mcuprzedni = Mce.zmniejszmiesiac(rok, mc)[1];
        pobierzstawki();
        przygotujmaile();
    }
    
    public void wyslijwszystkie() {
        for (Zusmail p : wykazprzygotowanychmaili) {
                wyslijMailZUSSilent(p);
        }
        Msg.msg("Wysłano maile do wszystkich podatników z listy w liczbie: "+wykazprzygotowanychmaili.size());
    }
    public void wyslijwybrane() {
        if (wybranemaile.size() > 0) {
            for (Zusmail p : wybranemaile) {
                    wyslijMailZUSSilent(p);
            }
            Msg.msg("Wysłano maile do wybranych podatników z listy w liczbie: "+wybranemaile.size());
        }
        wybranemaile.clear();
    }
    
    
    public void wyslijMailZUS(Zusmail zusmail) {
        try {
            MaiManager.mailManagerZUS(zusmail.getAdresmail(), zusmail.getTytul(), zusmail.getTresc(), wpisView.getUzer().getEmail(), null, sMTPSettingsDAO.findSprawaByDef());
            usuzpelnijdane(zusmail);
            Msg.msg("i", "Wyslano wiadomość");
        } catch (Exception e) {
            Msg.msg("e", "Blad nie wyslano wiadomosci! " + e.toString());
        }
    }
    
     public void wyslijMailZUSSilent(Zusmail zusmail) {
        try {
            MaiManager.mailManagerZUS(zusmail.getAdresmail(), zusmail.getTytul(), zusmail.getTresc(), wpisView.getUzer().getEmail(), null, sMTPSettingsDAO.findSprawaByDef());
            usuzpelnijdane(zusmail);
        } catch (Exception e) {
            Msg.msg("e", "Blad nie wyslano wiadomosci! " + e.toString());
        }
    }
    
    private void usuzpelnijdane(Zusmail zusmail) {
        try {
            Zusmail zusmaielwyslany = zusmailDAO.findZusmail(zusmail);
            if (zusmaielwyslany != null) {
               zusmaielwyslany.setNrwysylki(zusmaielwyslany.getNrwysylki()+1);
               zusmaielwyslany.setDatawysylki(new Date());
               zusmailDAO.edit(zusmaielwyslany);
               Msg.msg("Edytowano istniejący zusmail");
            } else {
               zusmail.setNrwysylki(1);
               zusmail.setDatawysylki(new Date());
               zusmailDAO.dodaj(zusmail);
               Msg.msg("Zachowano nowy zusmail");
            }
        } catch (Exception e) {
            Msg.msg("e", "Wystąpił błąd. Mail z ZUS nie został wysłany");
        }
    }
    
    public void wybranoWiersz(SelectEvent e) {
        Msg.msg("Wybrano wiersz "+((Zusmail) e.getObject()).getPodatnik().getPrintnazwa());
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

    public int getNrwysylki() {
        return nrwysylki;
    }

    public void setNrwysylki(int nrwysylki) {
        this.nrwysylki = nrwysylki;
    }

   
    public List<Integer> getNumery() {
        return numery;
    }

    public void setNumery(List<Integer> numery) {
        this.numery = numery;
    }

    public List<Zusmail> getWybranemaile() {
        return wybranemaile;
    }

    public void setWybranemaile(List<Zusmail> wybranemaile) {
        this.wybranemaile = wybranemaile;
    }

    public String getMcuprzedni() {
        return mcuprzedni;
    }

    public void setMcuprzedni(String mcuprzedni) {
        this.mcuprzedni = mcuprzedni;
    }

   

    
    
    
    
    
}
