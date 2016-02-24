/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.FakturaPodatnikRozliczeniecomparator;
import comparator.Kliencicomparator;
import dao.FakturaDAO;
import dao.FakturaRozrachunkiDAO;
import dao.FakturadodelementyDAO;
import data.Data;
import embeddable.FakturaPodatnikRozliczenie;
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
    private Klienci szukanyklient;
    private List<FakturaPodatnikRozliczenie> nowepozycje;
    private List<FakturaPodatnikRozliczenie> archiwum;
    private List<FakturaPodatnikRozliczenie> saldanierozliczone;
    private double sumasaldnierozliczonych;
    private List<FakturaPodatnikRozliczenie> selectedrozliczenia;
    @Inject
    private FakturaRozrachunki selected;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private FakturaDAO fakturaDAO;
    @Inject
    private FakturadodelementyDAO fakturadodelementyDAO;
    @Inject
    private FakturaRozrachunkiDAO fakturaRozrachunkiDAO;
    private int aktywnytab;
    private UISelectOne selectOneUI;
    private boolean tylkoprzeterminowane;

    public FakturaRozrachunkiAnalizaView() {
        
    }

    @PostConstruct
    public void init() {
        klienci = new ArrayList<>();
        nowepozycje = new ArrayList<>();
        archiwum = new ArrayList<>();
        saldanierozliczone = new ArrayList<>();
        klienci.addAll(pobierzkontrahentow());
        Collections.sort(klienci, new Kliencicomparator());
        if (klienci != null) {
            for (Iterator<Klienci> it = klienci.iterator(); it.hasNext();) {
                if (it.next() == null) {
                    it.remove();
                }
            }
        }
        pobierzwszystko(wpisView.getMiesiacWpisu());
    }
    
    public void pobierzwszystko(String mc) {
        nowepozycje = pobierzelementy(mc, false);
        archiwum = pobierzelementy(mc, true);
        sortujsumuj(nowepozycje);
        sortujsumuj(archiwum);
    }
    
    public List<FakturaPodatnikRozliczenie> pobierzelementy(String mc, boolean nowe0archiwum) {
        List<FakturaPodatnikRozliczenie> pozycje = null;
        if (szukanyklient != null) {
            List<FakturaRozrachunki> platnosci = fakturaRozrachunkiDAO.findByPodatnikKontrahent(wpisView, szukanyklient);
            List<Faktura> faktury = fakturaDAO.findbyKontrahentNipRok(szukanyklient.getNip(), wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
            pozycje = stworztabele(platnosci, faktury, nowe0archiwum);
            int i = 1;
            for (FakturaPodatnikRozliczenie p : pozycje) {
                p.setLp(i++);
            }
        }
        return pozycje;
    }
    
     
    private Collection<? extends Klienci> pobierzkontrahentow() {
        Collection p = fakturaDAO.findKontrahentFaktury(wpisView.getPodatnikObiekt());
        for (Iterator<Klienci> it = p.iterator(); it.hasNext();) {
            if (it.next() == null) {
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
            results.add(new Klienci("nowy klient", "nowy klient", "0123456789", "11-111", "miejscowosc", "ulica", "1", "1", "ewidencja", "kolumna"));
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
        if (platnosci != null) {
            for (FakturaRozrachunki p : platnosci) {
                if (p.isNowy0archiwum1() == nowe0archiwum1) {
                    l.add(new FakturaPodatnikRozliczenie(p));
                }
            }
        }
        if (faktury != null) {
            for (Faktura r : faktury) {
                if (r.isNowy0archiwum1() == nowe0archiwum1) {
                    if (tylkoprzeterminowane == false)  {
                        l.add(new FakturaPodatnikRozliczenie(r));
                    } else if (!r.getMc().equals(wpisView.getMiesiacWpisu())){
                        l.add(new FakturaPodatnikRozliczenie(r));
                    }
                }
            }
        }
        Collections.sort(l, new FakturaPodatnikRozliczeniecomparator());
        return l;
    }
    
    public void zestawieniezbiorcze() {
        saldanierozliczone = new ArrayList<>();
        int i = 1;
        sumasaldnierozliczonych = 0.0;
        for (Iterator<Klienci> it =  klienci.iterator(); it.hasNext();) {
            szukanyklient = it.next();
            pobierzwszystko(wpisView.getMiesiacWpisu());
            if (nowepozycje.size() > 0) {
                FakturaPodatnikRozliczenie r = nowepozycje.get(nowepozycje.size()-1);
                if (r.getSaldo() == 674.0) {
                    System.out.println("");
                }
                if (r.getSaldo() != 0.0) {
                    r.setLp(i++);
                    if (tylkoprzeterminowane == false) {
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

    public void pobierzszczegoly(FakturaPodatnikRozliczenie p) {
        if (p.isFaktura0rozliczenie1()) {
            szukanyklient = p.getRozliczenie().getKontrahent();
        } else {
            szukanyklient = p.getFaktura().getKontrahent();
        }
        pobierzwszystko(wpisView.getMiesiacWpisu());
        selectOneUI.setValue(szukanyklient);
        aktywnytab = 2;
    }
    
    public void korygujsaldo(FakturaPodatnikRozliczenie p) {
        double saldo = p.getSaldo();
        FakturaRozrachunki f = new FakturaRozrachunki();
        f.setData(Data.aktualnyDzien());
        if (p.isFaktura0rozliczenie1()) {
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
        zestawieniezbiorcze();
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
    
    public void mailKlienci(FakturaPodatnikRozliczenie p) {
        if (szukanyklient != null && !nowepozycje.isEmpty()) {
            System.out.println(p.toString());
            FakturaRozrachunki r = p.getRozliczenie();
            Faktura f = p.getFaktura();
            double saldo = p.getSaldo();
            if (saldo > 0) {
                obetnijliste(p);
                PdfFaktRozrach.drukujKlienciSilent(szukanyklient, nowepozycje, archiwum, wpisView);
                Fakturadodelementy stopka = fakturadodelementyDAO.findFaktStopkaPodatnik(wpisView.getPodatnikWpisu());
                MailFaktRozrach.rozrachunek(szukanyklient, wpisView, fakturaDAO, saldo, stopka.getTrescelementu());
                if (r != null) {
                    r.setDataupomnienia(new Date());
                    fakturaRozrachunkiDAO.edit(r);
                } else {
                    f.setDataupomnienia(new Date());
                    fakturaDAO.edit(f);
                }
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
            PdfFaktRozrach.drukujKlienciZbiorcze(saldanierozliczone, wpisView);
        } catch (Exception e) {
            Msg.msg("e", "Wystąpił błąd. Wydruk nieudany");
            E.e(e);
        }
    }
    
    
//<editor-fold defaultstate="collapsed" desc="comment">
    public List<Klienci> getKlienci() {
        return klienci;
    }
    
    public void setKlienci(List<Klienci> klienci) {
        this.klienci = klienci;
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


    public UISelectOne getSelectOneUI() {
        return selectOneUI;
    }

    public void setSelectOneUI(UISelectOne selectOneUI) {
        this.selectOneUI = selectOneUI;
    }
    
  
   
//</editor-fold>
    
    
}
