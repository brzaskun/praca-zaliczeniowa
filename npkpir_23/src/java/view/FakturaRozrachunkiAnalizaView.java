/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansMail.SMTPBean;
import comparator.FakturaPodatnikRozliczeniecomparator;
import comparator.Kliencicomparator;
import dao.FakturaDAO;
import dao.FakturaRozrachunkiDAO;
import dao.FakturadodelementyDAO;
import dao.KlienciDAO;
import dao.SMTPSettingsDAO;
import data.Data;
import embeddable.FakturaPodatnikRozliczenie;
import embeddable.Mce;
import entity.Faktura;
import entity.FakturaRozrachunki;
import entity.Fakturadodelementy;
import entity.Klienci;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UISelectOne;
import javax.inject.Inject;
import mail.MailFaktRozrach;
import msg.Msg;
import pdf.PdfFaktRozrach;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class FakturaRozrachunkiAnalizaView  implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private List<Klienci> klienci;
    private List<Klienci> klienciaktywowanie;
    private Klienci szukanyklient;
    private List<FakturaPodatnikRozliczenie> nowepozycje;
    private List<FakturaPodatnikRozliczenie> archiwum;
    private List<FakturaPodatnikRozliczenie> saldanierozliczone;
    private List<FakturaPodatnikRozliczenie> saldanierozliczoneselected;
    private double sumasaldnierozliczonych;
    private List<FakturaPodatnikRozliczenie> selectedrozliczenia;
    @Inject
    private FakturaRozrachunki selected;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private FakturaDAO fakturaDAO;
    @Inject
    private KlienciDAO klienciDAO;
    @Inject
    private FakturadodelementyDAO fakturadodelementyDAO;
    @Inject
    private FakturaRozrachunkiDAO fakturaRozrachunkiDAO;
    @Inject
    private SMTPSettingsDAO sMTPSettingsDAO;
    private int aktywnytab;
    private UISelectOne selectOneUI;
    private boolean pokaznadplaty;
    private boolean tylkoprzeterminowane;
    public FakturaRozrachunkiAnalizaView() {
        
    }

    @PostConstruct
    public void init() {
        klienci = new ArrayList<>();
        klienciaktywowanie = new ArrayList<>();
        nowepozycje = new ArrayList<>();
        archiwum = new ArrayList<>();
        saldanierozliczone = new ArrayList<>();
        klienci.addAll(pobierzkontrahentow());
        Collections.sort(klienci, new Kliencicomparator());
        klienciaktywowanie.addAll(pobierzkontrahentowaktywowanie());
        Collections.sort(klienciaktywowanie, new Kliencicomparator());
    }
    
    public void pobierzwszystkoKlienta() {
        String mc = wpisView.getMiesiacWpisu();
        nowepozycje = pobierzelementy(mc, false, szukanyklient);
        archiwum = pobierzelementy(mc, true, szukanyklient);
        sortujsumuj(nowepozycje);
        sortujsumuj(archiwum);
        Msg.msg("Pobrano dane kontrahenta");
    }
    
    public void pobierzwszystko(String mc, Klienci klient) {
        nowepozycje = pobierzelementy(mc, false, klient);
        archiwum = pobierzelementy(mc, true, klient);
        sortujsumuj(nowepozycje);
        sortujsumuj(archiwum);
    }
    
    public List<FakturaPodatnikRozliczenie> pobierzelementy(String mc, boolean nowe0archiwum, Klienci klient) {
        List<FakturaPodatnikRozliczenie> pozycje = null;
        if (klient != null) {
            List<FakturaRozrachunki> platnosci = fakturaRozrachunkiDAO.findByPodatnikKontrahentRok(wpisView, klient);
            //problem jest zeby nie brac wczesniejszych niz 2016 wiec BO sie robi
            List<Faktura> faktury = fakturaDAO.findbyKontrahentNipRok(klient.getNip(), wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
            pozycje = stworztabele(platnosci, faktury, nowe0archiwum);
            usuninnemiesiace(wpisView.getRokWpisuSt(), mc, pozycje);
            int i = 1;
            for (FakturaPodatnikRozliczenie p : pozycje) {
                p.setLp(i++);
            }
        }
        return pozycje;
    }
    
    private Collection<? extends Klienci> pobierzkontrahentowaktywowanie() {
        Collection p = fakturaDAO.findKontrahentFaktury(wpisView.getPodatnikObiekt());
        for (Iterator<Klienci> it = p.iterator(); it.hasNext();) {
            Klienci k = it.next();
            if (k == null) {
                it.remove();
            }
        }
        return p;
    }
     
    private Collection<? extends Klienci> pobierzkontrahentow() {
        Collection p = fakturaDAO.findKontrahentFaktury(wpisView.getPodatnikObiekt());
        for (Iterator<Klienci> it = p.iterator(); it.hasNext();) {
            Klienci k = it.next();
            if (k == null) {
                it.remove();
            } else if (k.isAktywnydlafaktrozrachunki() == false) {
                it.remove();
            }
        }
        return p;
    }
    
    public List<Klienci> completeKL(String query) {
        List<Klienci> results = new ArrayList<>();
        if (query.length() > 1) {
            Pattern pattern = Pattern.compile("[A-Z]{2}\\d+");
            Matcher m = pattern.matcher(query.toUpperCase());
            boolean czynipzagraniczny = m.matches();
            if (czynipzagraniczny) {
                for (Klienci p : klienci) {
                    if (p.getNip().startsWith(query.toUpperCase())) {
                            results.add(p);
                    }
                }
            } else {
                try {
                    String q = query.substring(0, 1);
                    int i = Integer.parseInt(q);
                    for (Klienci p : klienci) {
                        if (p.getNip().startsWith(query)) {
                            results.add(p);
                        }
                    }
                } catch (NumberFormatException e) {
                    for (Klienci p : klienci) {
                        if (p.getNpelna().toLowerCase().contains(query.toLowerCase())) {
                            results.add(p);
                        }
                    }
                }
            }
            results.add(new Klienci("nowy klient", "nowy klient", "0123456789", "11-111", "miejscowosc", "ulica", "1", "1"));
        }
        return results;
    }
    
    public void rozlicz(FakturaPodatnikRozliczenie p, boolean nowy0archiwum1) {
        if (selectedrozliczenia != null && selectedrozliczenia.size() > 0) {
            for (FakturaPodatnikRozliczenie r : selectedrozliczenia) {
                rozliczJednaPozycja(r, nowy0archiwum1);
            }
            selectedrozliczenia = null;
        } else {
            rozliczJednaPozycja(p, nowy0archiwum1);
        }
        sortujsumuj(nowepozycje);
        sortujsumuj(archiwum);
    }
    
    private void sortujsumuj(List<FakturaPodatnikRozliczenie> pozycje) {
        if (pozycje != null) {
            Collections.sort(pozycje, new FakturaPodatnikRozliczeniecomparator());
            obliczsaldo(pozycje);
        }
    }
    
    private void obliczsaldo(List<FakturaPodatnikRozliczenie> nowepozycje) {
        double saldo = 0.0;
        for (FakturaPodatnikRozliczenie p : nowepozycje) {
            if (p.isFaktura0rozliczenie1()) {
                saldo -= p.getKwota();
            } else {
                saldo += p.getKwota();
            }
            p.setSaldo(saldo);
        }
    }
    
    public void rozliczJednaPozycja(FakturaPodatnikRozliczenie p, boolean nowy0archiwum1) {
        try {
            archiwizuj(p, nowy0archiwum1);
            if (nowy0archiwum1) {
                nowepozycje.remove(p);
                archiwum.add(p);
            } else {
                nowepozycje.add(p);
                archiwum.remove(p);
            }
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    private void archiwizuj(FakturaPodatnikRozliczenie p, boolean nowy0archiwum1) {
        if (p.isFaktura0rozliczenie1() == false) {
            p.getFaktura().setNowy0archiwum1(nowy0archiwum1);
            fakturaDAO.edit(p.getFaktura());
        } else {
            p.getRozliczenie().setNowy0archiwum1(nowy0archiwum1);
            fakturaRozrachunkiDAO.edit(p.getRozliczenie());
        }
    }
    
    private List<FakturaPodatnikRozliczenie> stworztabele(List<FakturaRozrachunki> platnosci, List<Faktura> faktury, boolean nowe0archiwum1) {
        List<FakturaPodatnikRozliczenie> l = new ArrayList<>();
        FakturaPodatnikRozliczenie ostatniaplatnosc = null;
        if (platnosci != null) {
            for (FakturaRozrachunki p : platnosci) {
                if (p.isNowy0archiwum1() == nowe0archiwum1) {
                    ostatniaplatnosc = new FakturaPodatnikRozliczenie(p);
                    l.add(ostatniaplatnosc);
                }
            }
        }
        if (faktury != null) {
            for (Faktura r : faktury) {
                if (tylkoprzeterminowane && r.isNowy0archiwum1() == false && !r.getMc().equals(wpisView.getMiesiacWpisu())) {
                    l.add(new FakturaPodatnikRozliczenie(r));
                } else if (!tylkoprzeterminowane && r.isNowy0archiwum1() == nowe0archiwum1) {
                    l.add(new FakturaPodatnikRozliczenie(r));
                }
            }
        }
        Collections.sort(l, new FakturaPodatnikRozliczeniecomparator());
        if (!l.isEmpty() && ostatniaplatnosc != null) {
            FakturaPodatnikRozliczenie o = l.get(l.size()-1);
            o.setOstatniaplatnoscdata(ostatniaplatnosc.getData());
            o.setOstatniaplatnosckwota(ostatniaplatnosc.getKwota());
        }
        return l;
    }
    
    public void zestawieniezbiorcze() {
        saldanierozliczone = new ArrayList<>();
        int i = 1;
        sumasaldnierozliczonych = 0.0;
        for (Iterator<Klienci> it =  klienci.iterator(); it.hasNext();) {
            szukanyklient = it.next();
            pobierzwszystko(wpisView.getMiesiacWpisu(), szukanyklient);
            if (nowepozycje.size() > 0) {
                FakturaPodatnikRozliczenie r = nowepozycje.get(nowepozycje.size()-1);
                if (r.getSaldo() != 0.0) {
                    if (r.getMc().equals(wpisView.getMiesiacWpisu())) {
                        if (r.getDataupomnienia()!=null || r.getDatatelefon()!=null) {
                            r.setColor("blue");
                        } else {
                            r.setColor("initial");
                        }
                    }
                    r.setLp(i++);
                    if (pokaznadplaty == true) {
                        saldanierozliczone.add(r);
                        sumasaldnierozliczonych += r.getSaldo();
                    } else if (r.getSaldo() > 0.0) {
                        saldanierozliczone.add(r);
                        sumasaldnierozliczonych += r.getSaldo();
                    }
                }
            } else {
                it.remove();
            }
        }
    }
    
    public void sumujwybrane() {
        sumasaldnierozliczonych = 0.0;
        if (saldanierozliczoneselected != null) {
            for (FakturaPodatnikRozliczenie p : saldanierozliczoneselected) {
                sumasaldnierozliczonych += p.getSaldo();
            }
        }
    }

    public void pobierzszczegoly(FakturaPodatnikRozliczenie p) {
        if (p.isFaktura0rozliczenie1()) {
            szukanyklient = p.getRozliczenie().getKontrahent();
        } else {
            szukanyklient = p.getFaktura().getKontrahent();
        }
        pobierzwszystko(wpisView.getMiesiacWpisu(), szukanyklient);
        selectOneUI.setValue(szukanyklient);
        aktywnytab = 2;
    }
    
    public void zaksiegujjakoBO (FakturaPodatnikRozliczenie p) {
        double saldo = p.getSaldo();
        FakturaRozrachunki f = new FakturaRozrachunki();
        f.setData(wpisView.getRokNastepnySt()+"-01-01");
        if (p.isFaktura0rozliczenie1()) {
            FakturaRozrachunki fr = p.getRozliczenie();
            szukanyklient = fr.getKontrahent();
            fr.setPrzeniesionosaldo(true);
            fakturaRozrachunkiDAO.edit(fr);
        } else {
            Faktura fa = p.getFaktura();
            szukanyklient = fa.getKontrahent();
            fa.setPrzeniesionosaldo(true);
            fakturaDAO.edit(fa);
        }
        f.setKontrahent(szukanyklient);
        f.setKwota(saldo);
        f.setRok(wpisView.getRokNastepnySt());
        f.setMc("01");
        f.setWystawca(wpisView.getPodatnikObiekt());
        f.setWprowadzil(wpisView.getWprowadzil());
        f.setZaplata0korekta1(true);
        f.setRodzajdokumentu("ka");
        String nr = "bo/"+wpisView.getPodatnikWpisu().substring(0,1)+"/"+wpisView.getMiesiacWpisu();
        f.setNrdokumentu(nr);
        f.setPrzeniesionosaldo(true);
        selectOneUI.setValue(szukanyklient);
        fakturaRozrachunkiDAO.dodaj(f);
        aktywnytab = 3;
        Msg.msg("Zaksięgowano w bo");
    }
    
    public void korygujsaldo(FakturaPodatnikRozliczenie p) {
        double saldo = p.getSaldo();
        FakturaRozrachunki f = new FakturaRozrachunki();
        f.setData(Data.aktualnaData());
        if (p.getRozliczenie()!= null) {
            szukanyklient = p.getRozliczenie().getKontrahent();
        } else {
            szukanyklient = p.getFaktura().getKontrahent();
        }
        f.setKontrahent(szukanyklient);
        f.setKwota(-saldo);
        f.setRok(wpisView.getRokWpisuSt());
        f.setMc(wpisView.getMiesiacWpisu());
        f.setWystawca(wpisView.getPodatnikObiekt());
        f.setWprowadzil(wpisView.getWprowadzil());
        f.setZaplata0korekta1(true);
        f.setRodzajdokumentu("ka");
        String nr = "ka/"+wpisView.getPodatnikWpisu().substring(0,1)+"/"+wpisView.getMiesiacWpisu();
        f.setNrdokumentu(nr);
        selectOneUI.setValue(szukanyklient);
        fakturaRozrachunkiDAO.dodaj(f);
        saldanierozliczone.remove(p);
        aktywnytab = 3;
    }
    
    public void drukujKlienci() {
        try {
            PdfFaktRozrach.drukujKlienci(szukanyklient, nowepozycje, archiwum, wpisView);
        } catch (Exception e) {
            Msg.msg("e", "Wystąpił błąd. Wydruk nieudany");
            E.e(e);
        }
    }
    
    public void mailKlienci() {
        if (szukanyklient != null && !nowepozycje.isEmpty()) {
            FakturaPodatnikRozliczenie p = nowepozycje.get(nowepozycje.size()-1);
            FakturaRozrachunki r = p.getRozliczenie();
            Faktura f = p.getFaktura();
            double saldo = p.getSaldo();
            if (saldo > 0) {
                obetnijliste(p);
                PdfFaktRozrach.drukujKlienciSilent(szukanyklient, nowepozycje, archiwum, wpisView);
                Fakturadodelementy stopka = fakturadodelementyDAO.findFaktStopkaPodatnik(wpisView.getPodatnikWpisu());
                MailFaktRozrach.rozrachunek(szukanyklient, wpisView, fakturaDAO, saldo, stopka.getTrescelementu(), SMTPBean.pobierzSMTP(sMTPSettingsDAO, wpisView.getWprowadzil()), sMTPSettingsDAO.findSprawaByDef());
                if (r != null) {
                    r.setDataupomnienia(new Date());
                    p.setDataupomnienia(new Date());
                    fakturaRozrachunkiDAO.edit(r);
                } else {
                    f.setDataupomnienia(new Date());
                    p.setDataupomnienia(new Date());
                    fakturaDAO.edit(f);
                }
                Msg.msg("Wysłano upomnienie do klienta");
            } else {
                Msg.msg("e", "Saldo zerowe, nie ma po co wysyłac maila");
            }
        }
    }
    
    public void telefonKlienci() {
        if (szukanyklient != null && !nowepozycje.isEmpty()) {
            FakturaPodatnikRozliczenie p = nowepozycje.get(nowepozycje.size()-1);
            FakturaRozrachunki r = p.getRozliczenie();
            Faktura f = p.getFaktura();
            double saldo = p.getSaldo();
            if (saldo > 0) {
                if (r != null) {
                    r.setDatatelefon(new Date());
                    p.setDatatelefon(new Date());
                    fakturaRozrachunkiDAO.edit(r);
                } else {
                    f.setDatatelefon(new Date());
                    p.setDatatelefon(new Date());
                    fakturaDAO.edit(f);
                }
                Msg.msg("Naniesiono informacje o rozmowie z klientem");
            } else {
                Msg.msg("e", "Saldo zerowe, nie ma po co dzwonić");
            }
        }
    }
    
    private void obetnijliste(FakturaPodatnikRozliczenie p) {
        for (Iterator<FakturaPodatnikRozliczenie> it = nowepozycje.iterator(); it.hasNext();) {
            if (it.next().getLp() > p.getLp()) {
                it.remove();
            }
        }
    }
    
    public void drukujKlienciZbiorcze() {
        try {
            if (saldanierozliczoneselected != null && saldanierozliczoneselected.size() > 0){
                PdfFaktRozrach.drukujKlienciZbiorcze(saldanierozliczoneselected, wpisView);
            } else {
                PdfFaktRozrach.drukujKlienciZbiorcze(saldanierozliczone, wpisView);
            }
        } catch (Exception e) {
            Msg.msg("e", "Wystąpił błąd. Wydruk nieudany");
            E.e(e);
        }
    }
    
    public void aktywacjakontrahenta(Klienci k) {
        if (k.isAktywnydlafaktrozrachunki()) {
            k.setAktywnydlafaktrozrachunki(false);
        } else {
            k.setAktywnydlafaktrozrachunki(true);
        }
        klienciDAO.edit(k);
        Msg.msg("Zmieniono stan aktywacji klienta");
    }
    
//<editor-fold defaultstate="collapsed" desc="comment">
    public List<Klienci> getKlienci() {
        return klienci;
    }
    
    public void setKlienci(List<Klienci> klienci) {
        this.klienci = klienci;
    }

    public boolean isPokaznadplaty() {
        return pokaznadplaty;
    }

    public void setPokaznadplaty(boolean pokaznadplaty) {
        this.pokaznadplaty = pokaznadplaty;
    }

    public Klienci getSzukanyklient() {
        return szukanyklient;
    }

    public void setSzukanyklient(Klienci szukanyklient) {
        this.szukanyklient = szukanyklient;
    }

    public double getSumasaldnierozliczonych() {
        return sumasaldnierozliczonych;
    }

    public void setSumasaldnierozliczonych(double sumasaldnierozliczonych) {
        this.sumasaldnierozliczonych = sumasaldnierozliczonych;
    }

    public FakturaRozrachunki getSelected() {
        return selected;
    }

    public void setSelected(FakturaRozrachunki selected) {
        this.selected = selected;
    }
    
    public WpisView getWpisView() {
        return wpisView;
    }
    
    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public List<FakturaPodatnikRozliczenie> getSelectedrozliczenia() {
        return selectedrozliczenia;
    }

    public void setSelectedrozliczenia(List<FakturaPodatnikRozliczenie> selectedrozliczenia) {
        this.selectedrozliczenia = selectedrozliczenia;
    }

    public List<FakturaPodatnikRozliczenie> getArchiwum() {
        return archiwum;
    }

    public void setArchiwum(List<FakturaPodatnikRozliczenie> archiwum) {
        this.archiwum = archiwum;
    }

    public List<FakturaPodatnikRozliczenie> getSaldanierozliczone() {
        return saldanierozliczone;
    }

    public void setSaldanierozliczone(List<FakturaPodatnikRozliczenie> saldanierozliczone) {
        this.saldanierozliczone = saldanierozliczone;
    }

    public int getAktywnytab() {
        return aktywnytab;
    }

    public void setAktywnytab(int aktywnytab) {
        this.aktywnytab = aktywnytab;
    }
    
    public List<FakturaPodatnikRozliczenie> getNowepozycje() {
        return nowepozycje;
    }

    public void setNowepozycje(List<FakturaPodatnikRozliczenie> nowepozycje) {
        this.nowepozycje = nowepozycje;
    }

    public boolean isTylkoprzeterminowane() {
        return tylkoprzeterminowane;
    }

    public void setTylkoprzeterminowane(boolean tylkoprzeterminowane) {
        this.tylkoprzeterminowane = tylkoprzeterminowane;
    }

    public List<FakturaPodatnikRozliczenie> getSaldanierozliczoneselected() {
        return saldanierozliczoneselected;
    }

    public void setSaldanierozliczoneselected(List<FakturaPodatnikRozliczenie> saldanierozliczoneselected) {
        this.saldanierozliczoneselected = saldanierozliczoneselected;
    }

    public List<Klienci> getKlienciaktywowanie() {
        return klienciaktywowanie;
    }

    public void setKlienciaktywowanie(List<Klienci> klienciaktywowanie) {
        this.klienciaktywowanie = klienciaktywowanie;
    }


    public UISelectOne getSelectOneUI() {
        return selectOneUI;
    }

    public void setSelectOneUI(UISelectOne selectOneUI) {
        this.selectOneUI = selectOneUI;
    }
    
  
   
//</editor-fold>

    private void usuninnemiesiace(String rokWpisuSt, String mc, List<FakturaPodatnikRozliczenie> pozycje) {
        for (Iterator<FakturaPodatnikRozliczenie> it = pozycje.iterator();it.hasNext();) {
            FakturaPodatnikRozliczenie f = it.next();
            if (rokWpisuSt.equals(f.getRok())) {
                int granica = Mce.getMiesiacToNumber().get(mc);
                int mcpozycji = Mce.getMiesiacToNumber().get(f.getMc());
                if (mcpozycji > granica) {
                    it.remove();
                }
            }
        }
    }

   
    
}
