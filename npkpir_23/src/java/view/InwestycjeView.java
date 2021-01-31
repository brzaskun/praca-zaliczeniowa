/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansSrodkiTrwale.SrodkiTrwBean;
import dao.DokDAO;
import dao.InwestycjeDAO;
import dao.RodzajedokDAO;
import dao.STRDAO;
import data.Data;
import embeddable.Roki;
import entity.Dok;
import entity.Inwestycje;
import entity.Inwestycje.Sumazalata;
import entity.Klienci;
import entity.KwotaKolumna1;
import entity.Rodzajedok;
import entity.SrodekTrw;
import entity.Srodkikst;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import msg.Msg;import pdf.PdfInwestycja;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class InwestycjeView implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Inwestycje> inwestycjerozpoczete;
    private List<Inwestycje> inwestycjezakonczone;
    @Inject
    private WpisView wpisView;
    @Inject
    private InwestycjeDAO inwestycjeDAO;
    @Inject
    private DokDAO dokDAO;
    @Inject
    protected STRDAO sTRDAO;
    @Inject
    private Inwestycje selected;
    @Inject
    private RodzajedokDAO rodzajedokDAO;
    @Inject
    private Inwestycje wybrany;
    private String mczakonczenia;
    private String rokzakonczenia;
    @Inject
    private Srodkikst srodekkategoria;

    @PostConstruct
    private void init() { //E.m(this);
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
            inwestycjeDAO.create(selected);
            selected.setOpis("");
            selected.setSkrot("");
            Msg.msg("i", "Dodałem nową inwestycję", "form:messages");
            inwestycjerozpoczete.add(selected);
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd. Nie dodałem nowej inwestycji", "form:messages");
        }
    }

    public void usun(Inwestycje wybrana) {
        try {
            if (!wybrana.getDoklist().isEmpty()) {
                Msg.msg("e", "Inwestycja zawiera dokumenty! Usuń je najpierw", "form:messages");
                throw new Exception();
            } else {
                inwestycjeDAO.remove(wybrana);
                inwestycjerozpoczete.remove(wybrana);
                Msg.msg("i", "Usunąłem wybrną inwestycję", "form:messages");
            }
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd. Nie usunąłem wkazanej inwestycji", "form:messages");
        }
    }

    public void zamknijinwestycje(Inwestycje wybranainwestycja) {
        try {
            wybranainwestycja.setZakonczona(Boolean.TRUE);
            wybranainwestycja.setMczakonczenia(mczakonczenia);
            wybranainwestycja.setRokzakonczenia(rokzakonczenia);
            inwestycjeDAO.edit(wybranainwestycja);
            inwestycjerozpoczete.remove(wybranainwestycja);
            inwestycjezakonczone.add(wybranainwestycja);
            Msg.msg("i", "Zamknąłem wybrną inwestycję", "form:messages");
            dodajdokumentOT(wybranainwestycja);
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd. Nie zamknąłem wkazanej inwestycji", "form:messages");
        }
    }
    
    private void dodajdokumentOT(Inwestycje wybranainwestycja) {
        try {
                Dok selDokument = new Dok();
                selDokument.setEwidencjaVAT1(null);
                selDokument.setWprowadzil(wpisView.getUzer().getLogin());
                selDokument.setPkpirM(wpisView.getMiesiacWpisu());
                selDokument.setPkpirR(wpisView.getRokWpisu().toString());
                selDokument.setVatM(wpisView.getMiesiacWpisu());
                selDokument.setVatR(wpisView.getRokWpisu().toString());
                selDokument.setPodatnik(wpisView.getPodatnikObiekt());
                selDokument.setStatus("bufor");
                selDokument.setUsunpozornie(false);
                selDokument.setDataWyst(Data.ostatniDzien(wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu()));
                selDokument.setKontr(new Klienci("", "dowód wewnętrzny"));
                Rodzajedok amodok = rodzajedokDAO.find("OT", wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
                selDokument.setRodzajedok(amodok);
                selDokument.setNrWlDk("OTINW/"+wpisView.getMiesiacWpisu() + "/" + wpisView.getRokWpisu().toString());
                selDokument.setOpis("inwestycja "+wybranainwestycja.getSkrot()+" zakończenie");
                List<KwotaKolumna1> listaX = Collections.synchronizedList(new ArrayList<>());
                KwotaKolumna1 tmpX = new KwotaKolumna1();
                tmpX.setDok(selDokument);
                tmpX.setNetto(wybranainwestycja.getNetto());
                tmpX.setVat(wybranainwestycja.getVAT());
                tmpX.setNazwakolumny("inwestycje");
                listaX.add(tmpX);
                selDokument.setListakwot1(listaX);
                selDokument.setNetto(wybranainwestycja.getTotal());
                selDokument.setRozliczony(true);
                //sprawdzCzyNieDuplikat(selDokument);
                if (selDokument.getNetto() > 0) {
                    wybranainwestycja.setDokOT(selDokument);
                    inwestycjeDAO.edit(wybranainwestycja);
                    String wiadomosc = "Inwestycha zaksięghowana: " + selDokument.getPkpirR() + "/" + selDokument.getPkpirM() + " kwota: " + selDokument.getNetto();
                    Msg.msg("i", wiadomosc);
                    dodajSTR(wybranainwestycja);
                } else {
                    Msg.msg("e", "Wartośc inwestycji to 0.00, nie ma czego skiegować");
                }
            } catch (Exception e) {
                E.e(e);
                Msg.msg("e", "Wystąpił błąd, dokument OT nie zaksięgowany!");
            }
    }
    
    public void dodajSTR(Inwestycje wybranainwestycja) {
        try {
            SrodekTrw selectedSTR = new SrodekTrw();
            selectedSTR.setNetto(wybranainwestycja.getNetto());
            selectedSTR.setVat(wybranainwestycja.getVAT());
            selectedSTR.setDatazak(Data.ostatniDzien(wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu()));
            selectedSTR.setDataprzek(Data.ostatniDzien(wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu()));
            selectedSTR.setUmorzeniezaksiegowane(Boolean.FALSE);
            selectedSTR.setNrwldokzak("OTINW/"+wpisView.getMiesiacWpisu() + "/" + wpisView.getRokWpisu().toString());
            selectedSTR.setZlikwidowany(0);
            selectedSTR.setStawka(Double.valueOf(wybranainwestycja.getSrodkikst().getStawka()));
            selectedSTR.setKst(wybranainwestycja.getSrodkikst().getSymbol());
            selectedSTR.setDatasprzedazy("");
            selectedSTR.setTyp("srodek trw.");
            selectedSTR.setPodatnik(wpisView.getPodatnikWpisu());
            selectedSTR.setNazwa(wybranainwestycja.getOpis());
            SrodkiTrwBean.odpisroczny(selectedSTR);
            SrodkiTrwBean.odpismiesieczny(selectedSTR);
            //oblicza planowane umorzenia
            selectedSTR.setUmorzPlan(SrodkiTrwBean.naliczodpisymczne(selectedSTR));
            selectedSTR.setPlanumorzen(SrodkiTrwBean.generujumorzeniadlasrodka(selectedSTR, wpisView));
            sTRDAO.create(selectedSTR);
            Msg.msg("i", "Środek trwały "+selectedSTR.getNazwa()+" dodany", "formSTR:messages");
        } catch (Exception e) { 
            E.e(e); 
            Msg.msg("e","Nowy srodek nie zachowany ");
        }
    }

    public void otworzinwestycje(Inwestycje wybrany) {
        try {
            wybrany.setZakonczona(Boolean.FALSE);
            wybrany.setMczakonczenia(null);
            wybrany.setMczakonczenia("");
            wybrany.setRokzakonczenia("");
            wybrany.setDokOT(null);
            inwestycjeDAO.edit(wybrany);
            inwestycjezakonczone.remove(wybrany);
            inwestycjerozpoczete.add(wybrany);
            Msg.msg("i", "Otworzyłem ponownie wybrną inwestycję", "form:messages");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd. Nie można było ponownie otworzyć inwestycji", "form:messages");
        }
    }
    public void edytuj(Inwestycje i) {
        try {
            inwestycjeDAO.edit(i);
            Msg.dP();
        } catch (Exception e) {
            Msg.dPe();
        }
    }
    
    
//usuwa tez rachunek z bazy danych!!!
//    public void usunrachunek(Inwestycje inwestycja, Dok dok) {
//        try {
//            inwestycja.getDoklist().remove(dok);
//            inwestycjeDAO.edit(inwestycja);
//            inwestycjerozpoczete = inwestycjeDAO.findInwestycje(wpisView.getPodatnikWpisu(), false);
//            inwestycjesymbole = Collections.synchronizedList(new ArrayList<>());
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

    public Srodkikst getSrodekkategoria() {
        return srodekkategoria;
    }

    public void setSrodekkategoria(Srodkikst srodekkategoria) {
        this.srodekkategoria = srodekkategoria;
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
        List<String> lata = Collections.synchronizedList(new ArrayList<>());
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
