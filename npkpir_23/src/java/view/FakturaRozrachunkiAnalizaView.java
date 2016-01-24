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
import embeddable.FakturaPodatnikRozliczenie;
import entity.Faktura;
import entity.FakturaRozrachunki;
import entity.Klienci;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
import org.primefaces.context.RequestContext;

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
    private List<FakturaPodatnikRozliczenie> selectedrozliczenia;
    @Inject
    private FakturaRozrachunki selected;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private FakturaDAO fakturaDAO;
    @Inject
    private FakturaRozrachunkiDAO fakturaRozrachunkiDAO;
    private int aktywnytab;
    private UISelectOne selectOneUI;

    public FakturaRozrachunkiAnalizaView() {
        klienci = new ArrayList<>();
        nowepozycje = new ArrayList<>();
        archiwum = new ArrayList<>();
        saldanierozliczone = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
        klienci.addAll(pobierzkontrahentow());
        Collections.sort(klienci, new Kliencicomparator());
        if (klienci != null) {
            for (Iterator<Klienci> it = klienci.iterator(); it.hasNext();) {
                if (it.next() == null) {
                    it.remove();
                }
            }
        }
        pobierzpozycje(wpisView.getMiesiacWpisu());
        zestawieniezbiorcze();
    }
    
    public void pobierzpozycje(String mc) {
        pobierzfakturynowe(mc);
        pobierzfakturarchiwum(mc);
        sortujsumuj();
    }
    
    public void pobierzfakturynowe(String mc) {
        if (szukanyklient != null) {
            List<FakturaRozrachunki> platnosci = fakturaRozrachunkiDAO.findByPodatnikKontrahent(wpisView, szukanyklient);
            List<Faktura> faktury = fakturaDAO.findbyKontrahentNipRok(szukanyklient.getNip(), wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
            nowepozycje = stworztabele(platnosci, faktury, false);
            int i = 1;
            for (FakturaPodatnikRozliczenie p : nowepozycje) {
                p.setLp(i++);
            }
        }
    }
    
    public void pobierzfakturarchiwum(String mc) {
        if (szukanyklient != null) {
            List<FakturaRozrachunki> platnosci = fakturaRozrachunkiDAO.findByPodatnikKontrahent(wpisView, szukanyklient);
            List<Faktura> faktury = fakturaDAO.findbyKontrahentNipRok(szukanyklient.getNip(), wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
            archiwum = stworztabele(platnosci, faktury, true);
            int i = 1;
            for (FakturaPodatnikRozliczenie p : archiwum) {
                p.setLp(i++);
            }
        }
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
        sortujsumuj();
    }
    
    private void sortujsumuj() {
        Collections.sort(nowepozycje, new FakturaPodatnikRozliczeniecomparator());
        obliczsaldo(nowepozycje);
        Collections.sort(archiwum, new FakturaPodatnikRozliczeniecomparator());
        obliczsaldo(archiwum);
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
                    l.add(new FakturaPodatnikRozliczenie(r));
                }
            }
        }
        Collections.sort(l, new FakturaPodatnikRozliczeniecomparator());
        return l;
    }
    
    private void zestawieniezbiorcze() {
        int i = 1;
        for (Klienci p : klienci) {
            szukanyklient = p;
            pobierzpozycje(wpisView.getMiesiacWpisu());
            if (nowepozycje.size() > 0) {
                FakturaPodatnikRozliczenie r = nowepozycje.get(nowepozycje.size()-1);
                if (r.getSaldo() != 0.0) {
                    r.setLp(i++);
                    saldanierozliczone.add(r);
                }
            }
        }
    }

    public void pobierzszczegoly(FakturaPodatnikRozliczenie p) {
        if (p.isFaktura0rozliczenie1()) {
            szukanyklient = p.getRozliczenie().getKontrahent();
        } else {
            szukanyklient = p.getFaktura().getKontrahent();
        }
        pobierzpozycje(wpisView.getMiesiacWpisu());
        selectOneUI.setValue(szukanyklient);
        aktywnytab = 2;
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


    public UISelectOne getSelectOneUI() {
        return selectOneUI;
    }

    public void setSelectOneUI(UISelectOne selectOneUI) {
        this.selectOneUI = selectOneUI;
    }
    
  
   
//</editor-fold>
    
    
}
