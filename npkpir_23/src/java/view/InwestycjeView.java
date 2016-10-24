/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DokDAO;
import dao.InwestycjeDAO;
import embeddable.Roki;
import entity.Dok;
import entity.Inwestycje;
import entity.Inwestycje.Sumazalata;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import pdf.PdfInwestycja;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class InwestycjeView implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Inwestycje> inwestycjerozpoczete;
    private List<Inwestycje> inwestycjezakonczone;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private InwestycjeDAO inwestycjeDAO;
    @Inject
    private DokDAO dokDAO;
    @Inject
    private Inwestycje selected;
    @Inject
    private Inwestycje wybrany;
    private String mczakonczenia;
    private String rokzakonczenia;

    @PostConstruct
    private void init() {
        inwestycjerozpoczete = inwestycjeDAO.findInwestycje(wpisView.getPodatnikWpisu(), false);
        inwestycjezakonczone = inwestycjeDAO.findInwestycje(wpisView.getPodatnikWpisu(), true);
        try {
            if (inwestycjerozpoczete != null) {
                for (Inwestycje p : inwestycjerozpoczete) {
                    aktualizujwartosci(p);
                }
            }
        } catch (Exception e1) {
            E.e(e1);
        }
        mczakonczenia = wpisView.getMiesiacWpisu();
        rokzakonczenia = String.valueOf(wpisView.getRokWpisu());
      }

    public void dodaj() {
        try {
            selected.setPodatnik(wpisView.getPodatnikWpisu());
            selected.setSymbol(wpisView.getRokWpisu() + "/" + selected.getSkrot());
            inwestycjeDAO.dodaj(selected);
            selected.setOpis("");
            selected.setSkrot("");
            Msg.msg("i", "Dodałem nową inwestycję", "form:messages");
            inwestycjerozpoczete.add(selected);
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd. Nie dodałem nowej inwestycji", "form:messages");
        }
    }

    public void usun() {
        try {
            if (!wybrany.getDoklist().isEmpty()) {
                Msg.msg("e", "Inwestycja zawiera dokumenty! Usuń je najpierw", "form:messages");
                throw new Exception();
            } else {
                inwestycjeDAO.destroy(wybrany);
                inwestycjerozpoczete.remove(wybrany);
                Msg.msg("i", "Usunąłem wybrną inwestycję", "form:messages");
            }
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd. Nie usunąłem wkazanej inwestycji", "form:messages");
        }
    }

    public void zamknijinwestycje(Inwestycje wybrany) {
        try {
            wybrany.setZakonczona(Boolean.TRUE);
            wybrany.setMczakonczenia(mczakonczenia);
            wybrany.setRokzakonczenia(rokzakonczenia);
            inwestycjeDAO.edit(wybrany);
            inwestycjerozpoczete.remove(wybrany);
            inwestycjezakonczone.add(wybrany);
            Msg.msg("i", "Zamknąłem wybrną inwestycję", "form:messages");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd. Nie zamknąłem wkazanej inwestycji", "form:messages");
        }
    }

    public void otworzinwestycje(Inwestycje wybrany) {
        try {
            wybrany.setZakonczona(Boolean.FALSE);
            wybrany.setMczakonczenia(null);
            wybrany.setMczakonczenia("");
            wybrany.setRokzakonczenia("");
            inwestycjeDAO.edit(wybrany);
            inwestycjezakonczone.remove(wybrany);
            inwestycjerozpoczete.add(wybrany);
            Msg.msg("i", "Otworzyłem ponownie wybrną inwestycję", "form:messages");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd. Nie można było ponownie otworzyć inwestycji", "form:messages");
        }
    }
//usuwa tez rachunek z bazy danych!!!
//    public void usunrachunek(Inwestycje inwestycja, Dok dok) {
//        try {
//            inwestycja.getDoklist().remove(dok);
//            inwestycjeDAO.edit(inwestycja);
//            inwestycjerozpoczete = inwestycjeDAO.findInwestycje(wpisView.getPodatnikWpisu(), false);
//            inwestycjesymbole = new ArrayList<>();
//            if (inwestycjerozpoczete != null) {
//                for (Inwestycje p : inwestycjerozpoczete) {
//                    aktualizujwartosci(p);
//                }
//            }
//            Msg.msg("i", "Usunąłem rachunek z inwestycji " + dok.toString(), "form:messages");
//        } catch (Exception e) {
//            E.e(e);
//            Msg.msg("e", "Wystąpił błąd. Nie można było usunąć rachunku " + dok.toString(), "form:messages");
//        }
//    }

  

    public void drukujInwestycje(Inwestycje wybrany) {
        try {
            PdfInwestycja.drukujinwestycje(wybrany, wpisView);
        } catch (Exception e) {
            E.e(e);

        }
    }

    public void wybranoinwestycje() {
        Msg.msg("i", "Wybrano inwestycję " + wybrany.getOpis(), "form:messages");
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    //<editor-fold defaultstate="collapsed" desc="comment">
    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public List<Inwestycje> getInwestycjerozpoczete() {
        return inwestycjerozpoczete;
    }

    public void setInwestycjerozpoczete(List<Inwestycje> inwestycjerozpoczete) {
        this.inwestycjerozpoczete = inwestycjerozpoczete;
    }

    public Inwestycje getSelected() {
        return selected;
    }

    public void setSelected(Inwestycje selected) {
        this.selected = selected;
    }

    public Inwestycje getWybrany() {
        return wybrany;
    }

    public void setWybrany(Inwestycje wybrany) {
        this.wybrany = wybrany;
    }

    public List<Inwestycje> getInwestycjezakonczone() {
        return inwestycjezakonczone;
    }

    public void setInwestycjezakonczone(List<Inwestycje> inwestycjezakonczone) {
        this.inwestycjezakonczone = inwestycjezakonczone;
    }

    public String getMczakonczenia() {
        return mczakonczenia;
    }

    public void setMczakonczenia(String mczakonczenia) {
        this.mczakonczenia = mczakonczenia;
    }

    public String getRokzakonczenia() {
        return rokzakonczenia;
    }

    public void setRokzakonczenia(String rokzakonczenia) {
        this.rokzakonczenia = rokzakonczenia;
    }

    //</editor-fold>
    private void aktualizujwartosci(Inwestycje p) {
        Integer rokbiezacy = Integer.parseInt(p.getRokrozpoczecia());
        List<String> lata = new ArrayList<>();
        for (Integer r : Roki.getRokiListS()) {
            if (r >= rokbiezacy) {
                lata.add(String.valueOf(r));
            }
        }
        for (Inwestycje s : inwestycjerozpoczete) {
            s.setSumazalata(new ArrayList<Sumazalata>());
            inwestycjeDAO.edit(s);
            List<Inwestycje.Sumazalata> suma = s.getSumazalata();
            for (String t : lata) {
                suma.add(s.new Sumazalata(t));
            }
            double total = 0.0;
            for (Inwestycje.Sumazalata o : suma) {
                String rok = o.getRok();
                for (Dok u : s.getDoklist()) {
                    if (u.getPkpirR().equals(rok)) {
                        o.setKwota(o.getKwota() + u.getNetto());
                        total += u.getNetto();
                    }
                }
            }
            s.setTotal(total);
            inwestycjeDAO.edit(s);
        }

    }
}
