/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewmanager;

import comparator.Podatnikcomparator;
import dao.PodatnikDAO;
import dao.ZusmailDAO;
import embeddable.Mce;
import entity.Podatnik;
import entity.Zusmail;
import entity.ZusmailPK;
import entity.Zusstawki;
import entity.ZusstawkiPK;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import mail.MaiManager;
import msg.Msg;
import org.joda.time.DateTime;
import org.primefaces.event.SelectEvent;
import view.WpisView;

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
    private int nrwysylki;
    private List<Integer> numery;
    private List<Zusmail> wykazprzygotowanychmaili;
    private List<Zusmail> wybranemaile;
    private Map<String, Zusstawki> stawkipodatnicy;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private ZusmailDAO zusmailDAO;
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;

    public ZUSMailView() {
        wykazprzygotowanychmaili = new ArrayList<>();
        stawkipodatnicy = new HashMap<>();
        wybranemaile = new ArrayList<>();
        numery = new ArrayList<>();
    }
    
    @PostConstruct
    private void init() {
        rok = String.valueOf((new DateTime()).getYear());
        mc = Mce.getNumberToMiesiac().get((new DateTime()).getMonthOfYear());
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
        ZusstawkiPK zusstawkiPK = new ZusstawkiPK(rok, mc);
        for (Podatnik p : podatnicy) {
            if (p.isWysylkazusmail() == true) {
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
    }
    private static final String trescmaila = "<p> Szanowny Podatniku</p> <p> W niniejszym mailu znajdziesz naliczone kwoty zobowiązań z tytułu ZUS I PIT-4</p> "
            + "<p> do zapłaty w miesiącu <span style=\"color:#008000;\">%s/%s</span></p> "
            + " <table align=\"left\" border=\"1\" cellpadding=\"1\" cellspacing=\"1\" style=\"width: 500px;\"> <caption> zestawienie zobowiązań</caption> <thead> <tr> "
            + "<th scope=\"col\"> lp</th> <th scope=\"col\"> tytuł</th> <th scope=\"col\"> kwota</th> </tr> </thead> <tbody> <tr> <td style=\"text-align: center;\"> 1</td> "
            + "<td> ZUS 51</td> <td style=\"text-align: right;\"> %.2f</td> </tr> <tr> <td style=\"text-align: center;\"> 2</td>"
            + " <td> ZUS 52</td> <td style=\"text-align: right;\"> <span style=\"text-align: right;\">%.2f</span></td> </tr> <tr> <td style=\"text-align: center;\"> 3</td>"
            + " <td> ZUS 53</td> <td style=\"text-align: right;\"> <span style=\"text-align: right;\">%.2f</span></td> </tr> <tr> <td style=\"text-align: center;\"> 4</td> <td>"
            + " PIT-4</td> <td style=\"text-align: right;\"> <span style=\"text-align: right;\">%.2f</span></td> </tr> </tbody> </table>"
            + " <p> &nbsp;</p> <p> &nbsp;</p> <p> &nbsp;</p> <p> &nbsp;</p> <p> &nbsp;</p> "
            + "<p> Przypominamy o terminach płatności ZUS:</p>"
            + " <p> do <span style=\"color:#008000;\">10-go</span> &nbsp;- dla os&oacute;b niezatrudniających pracownik&oacute;w</p>"
            + " <p> do <span style=\"color:#008000;\">15-go</span> - dla firm z pracownikami</p>"
            + " <p> do <span style=\"color:#006400;\">20-go</span> - PIT-4 od wynagrodzeń pracownik&oacute;w</p>"
            + " <p> &nbsp;</p>";
    
    public void przygotujmaile() {
        wykazprzygotowanychmaili.clear();
        if (!stawkipodatnicy.isEmpty()) {
            List<String> pobranipodatnicy = new ArrayList<>();
            pobranipodatnicy.addAll(stawkipodatnicy.keySet());
            Collections.sort(pobranipodatnicy);
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
                double zus51 = 0;
                try {
                    zus51 = zusmail.getZus51ch() != null ? zusmail.getZus51ch() : zusmail.getZus51bch();
                } catch (Exception e) {}
                zusmail.setTytul(String.format("Taxman - zestawienie kwot ZUS/PIT4 za %s/%s", rok, mc));
                zusmail.setTresc(String.format(new Locale("pl"),trescmaila, rok, mc, zus51, zusmail.getZus52(), zusmail.getZus53(), zusmail.getPit4()));
                zusmail.setAdresmail((podatnikDAO.find(p)).getEmail());
                zusmail.setWysylajacy(wpisView.getWprowadzil().getLogin());
                if (!wykazprzygotowanychmaili.contains(zusmail)) {
                    wykazprzygotowanychmaili.add(zusmail);
                } else {
                    Msg.msg("dupliat");
                }
            }
            naniesistniejacezapisy();
            usunniezgodne();
        }
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
                        if (r.getZusmailPK().equals(p.getZusmailPK())) {
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
            MaiManager.mailManagerZUS(zusmail.getAdresmail(), zusmail.getTytul(), zusmail.getTresc(), wpisView.getWprowadzil().getEmail());
            usuzpelnijdane(zusmail);
            Msg.msg("i", "Wyslano wiadomość");
        } catch (Exception e) {
            Msg.msg("e", "Blad nie wyslano wiadomosci! " + e.toString());
        }
    }
    
     public void wyslijMailZUSSilent(Zusmail zusmail) {
        try {
            MaiManager.mailManagerZUS(zusmail.getAdresmail(), zusmail.getTytul(), zusmail.getTresc(), wpisView.getWprowadzil().getEmail());
            usuzpelnijdane(zusmail);
        } catch (Exception e) {
            Msg.msg("e", "Blad nie wyslano wiadomosci! " + e.toString());
        }
    }
    
    private void usuzpelnijdane(Zusmail zusmail) {
        try {
            Zusmail zusmaielwyslany = zusmailDAO.findZusmail(zusmail);
            if (zusmaielwyslany != null) {
               zusmail.setNrwysylki(zusmaielwyslany.getNrwysylki()+1);
               zusmail.setDatawysylki(new Date());
               zusmailDAO.edit(zusmail);
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
        Msg.msg("Wybrano wiersz "+((Zusmail) e.getObject()).getZusmailPK().getPodatnik());
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

    
    
    
    
    
}
