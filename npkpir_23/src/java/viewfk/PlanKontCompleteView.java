/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.PlanKontFKBean;
import comparator.Kontocomparator;
import dao.KontoDAOfk;
import dao.KontopozycjaZapisDAO;
import dao.MiejscePrzychodowDAO;
import dao.UkladBRDAO;
import entityfk.Konto;
import entityfk.KontopozycjaZapis;
import entityfk.MiejscePrzychodow;
import entityfk.UkladBR;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.inject.Named;

import javax.faces.view.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import msg.Msg;import view.WpisView;
/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class PlanKontCompleteView implements javax.faces.convert.Converter, Serializable {
    private static final long serialVersionUID = 1L;
    private List<Konto> listakontOstatniaAnalitykaklienta;
    @Inject
    private Konto noweKonto;
    @Inject
    private KontoDAOfk kontoDAOfk;
    @Inject
    private KontopozycjaZapisDAO kontopozycjaZapisDAO;
    @Inject
    private UkladBRDAO ukladBRDAO;
    @Inject
    private MiejscePrzychodowDAO miejscePrzychodowDAO;
    @Inject
    private WpisView wpisView;
    private String elementslownika_nazwapelna;
    private String elementslownika_nazwaskrocona;
    private String elementslownika_numerkonta;
    private List<Konto> konta;

    public PlanKontCompleteView() {
         ////E.m(this);
    }
    
    
  @PostConstruct
  public void init() { //E.m(this);
      listakontOstatniaAnalitykaklienta = kontoDAOfk.findKontaOstAlitykaRO(wpisView);
      if (listakontOstatniaAnalitykaklienta!=null) {
        Collections.sort(listakontOstatniaAnalitykaklienta, new Kontocomparator());
      }
      konta = kontoDAOfk.findWszystkieKontaPodatnikaRO(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
      if (konta!=null) {
        Collections.sort(konta, new Kontocomparator());
      }
  }
    
    public List<Konto> complete(String qr) {
        if (qr != null) {
            String query = null;
            List<Konto> results = Collections.synchronizedList(new ArrayList<>());
            if (listakontOstatniaAnalitykaklienta != null) {
                String nazwa = null;
                if (qr.trim().matches("^(.*\\s+.*)+$") && qr.length() > 6) {
                    String[] pola = qr.split(" ");
                    if (pola.length > 1) {
                        query = pola[0];
                        nazwa = pola[1];
                    } else {
                        query = qr;
                    }
                } else {
                    query = qr.trim();
                }
                try {
                    String q = query.substring(0, 1);
                    int i = Integer.parseInt(q);
                    if (query.length() == 4 && !query.contains("-")) {
                        //wstawia - do ciagu konta
                        query = query.substring(0, 3) + "-" + query.substring(3, 4);
                    }
                    if (query.length() == 5 && !query.contains("-")) {
                        //wstawia - do ciagu konta
                        query = query.substring(0, 3) + "-" + query.substring(3, 5);
                    }
                    String[] ql = {query};
                    results = listakontOstatniaAnalitykaklienta.stream().filter((p)->(p.getPelnynumer().startsWith(ql[0]) && !p.isNiewidoczne())).collect(Collectors.toList()); 
                    //rozwiazanie dla rozrachunkow szukanie po nazwie kontrahenta
                    if (nazwa != null && nazwa.length() > 2) {
                        for (Iterator<Konto> it = results.iterator(); it.hasNext();) {
                            Konto r = it.next();
                            if (!r.getNazwapelna().toLowerCase().contains(nazwa.toLowerCase()) && !r.isNiewidoczne()) {
                                it.remove();
                            }
                        }
                    }
                } catch (NumberFormatException e) {
                    String[] ql = {query.toLowerCase()};
                    results = listakontOstatniaAnalitykaklienta.stream().filter((p)->(p.getNazwapelna().toLowerCase().contains(ql[0]) && !p.isNiewidoczne())).collect(Collectors.toList()); 
                } catch (Exception e) {
                    E.e(e);
                }
            }
            Collections.sort(results, new Kontocomparator());
            if (results.isEmpty()) {
                Konto p = new Konto();
                p.setNazwapelna("dodaj konto");
                p.setPelnynumer(query);
                results.add(p);
                p = new Konto();
                p.setNazwapelna("dodaj kontrahenta");
                p.setPelnynumer(query);
                results.add(p);
                p = new Konto();
                p.setNazwapelna("dodaj el.słownika");
                p.setPelnynumer(query);
                results.add(p);

            }
            return results;
        }
        return null;
    }
    
    public void dodajanalityczneWpis() {
        String nrmacierzystego = PlanKontFKBean.modyfikujnranalityczne(noweKonto.getPelnynumer());
        Konto kontomacierzyste = PlanKontFKBean.wyszukajmacierzyste(wpisView, kontoDAOfk, nrmacierzystego);
        if (kontomacierzyste != null && kontomacierzyste.getIdslownika() == 0) {
            int wynikdodaniakonta = 1;
            List<Konto> wykazkont = kontoDAOfk.findWszystkieKontaPodatnikaBezSlownik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            wynikdodaniakonta = PlanKontFKBean.dodajanalityczne(wpisView.getPodatnikObiekt(), wykazkont, noweKonto, kontomacierzyste, kontoDAOfk, wpisView.getRokWpisu());
            if (wynikdodaniakonta == 0) {
                try {
                    UkladBR wybranyuklad = ukladBRDAO.findukladBRPodatnikRokAktywny(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
                    KontopozycjaZapis kpo = kontopozycjaZapisDAO.findByKonto(kontomacierzyste, wybranyuklad);
                    if (kpo.getSyntetykaanalityka().equals("analityka")) {
                        Msg.msg("w", "Konto przyporządkowane z poziomu analityki!");
                    } else {
                        PlanKontFKBean.naniesPrzyporzadkowaniePojedynczeKonto(kpo, noweKonto, kontopozycjaZapisDAO, kontoDAOfk, "syntetyka", wybranyuklad);
                    }
                } catch (Exception e) {
                    E.e(e);
                }
            }
            if (wynikdodaniakonta == 0) {
                PlanKontFKBean.zablokujKontoMacierzysteNieSlownik(kontomacierzyste, kontoDAOfk);
//                if (czyoddacdowzorca == true) {
//                    wykazkontwzor = kontoDAOfk.findWszystkieKontaWzorcowy(wpisView);
//                } else {
//                    //  MEGAZOR
//                    wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
//                }
                noweKonto = new Konto();
                //PlanKontFKBean.odswiezroot(r, kontoDAOfk, wpisView);
                Msg.msg("i", "Dodaje konto analityczne", "formX:messages");
            } else {
                noweKonto = new Konto();
                Msg.msg("e", "Konto analityczne o takim numerze juz istnieje!", "formX:messages");
            }
            init();
        } else {
            Msg.msg("e", "Niewłaściwy numer konta lub próba zmiany konta słownikowego. Nie dodano nowej analityki");
        }
    }
    
     public void dodajSlownikWpis() {
        String nrmacierzystego = PlanKontFKBean.modyfikujnrslownik(elementslownika_numerkonta);
        Konto kontomacierzyste = PlanKontFKBean.wyszukajmacierzyste(wpisView, kontoDAOfk, nrmacierzystego);
        List<Konto> potomne = PlanKontFKBean.pobierzpotomne(kontomacierzyste, kontoDAOfk);
        Collections.sort(potomne, new Kontocomparator());
        if (potomne != null && potomne.size() > 0) {
            Konto slownik = potomne.get(0);
            String nazwaslownika = slownik.getNazwapelna();
            if (nazwaslownika.equals("Słownik miejsca przychodów")) {
                MiejscePrzychodow mp = new MiejscePrzychodow();
                mp.setOpismiejsca(elementslownika_nazwapelna);
                mp.setOpisskrocony(elementslownika_nazwaskrocona);
                mp.setAktywny(true);
                mp.uzupelnij(wpisView.getPodatnikObiekt(), pobierzkolejnynumerMP());
                mp.setRok(wpisView.getRokWpisu());
                miejscePrzychodowDAO.create(mp);
                if (kontomacierzyste != null) {
                    int wynikdodaniakonta = 0;
                    List<Konto> wykazkont = kontoDAOfk.findWszystkieKontaPodatnikaBezSlownik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
                    wynikdodaniakonta = PlanKontFKBean.aktualizujslownikMiejscaPrzychodow(wykazkont, miejscePrzychodowDAO, mp, kontoDAOfk, wpisView, kontopozycjaZapisDAO, ukladBRDAO);
                    if (wynikdodaniakonta == 0) {
                        noweKonto = new Konto();
                        //PlanKontFKBean.odswiezroot(r, kontoDAOfk, wpisView);
                        Msg.msg("i", "Dodaje konto słownikowe", "formX:messages");
                    } else {
                        noweKonto = new Konto();
                        Msg.msg("e", "Konto słownikowe o takim numerze juz istnieje!", "formX:messages");
                    }
                    elementslownika_nazwapelna = null;
                    elementslownika_nazwaskrocona = null;
                    elementslownika_numerkonta = null;
                    init();
                } else {
                    Msg.msg("e", "Niewłaściwy numer konta lub próba zmiany konta słownikowego. Nie dodano nowej analityki");
                }
            }
        }
    }
     
    private String pobierzkolejnynumerMP() {
        List miejsca = miejscePrzychodowDAO.findMiejscaPodatnik(wpisView.getPodatnikObiekt());
        int max = 0;
        for (Iterator<MiejscePrzychodow> it = miejsca.iterator(); it.hasNext();) {
            MiejscePrzychodow m = it.next();
            int nr = Integer.parseInt(m.getNrkonta());
            if (max < nr) {
                max = nr;
            }
        }
        String zwrot = String.valueOf(max+1);
        return zwrot;
    }
    
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String sub) {
            try {
                int submittedValue = Integer.parseInt(sub);
                for (Konto p : konta) {
                    if (p.getId()== submittedValue) {
                        return p;
                    }
                }

            } catch (Exception exception) {

            }
        return null;
    }
  
    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {  
        if (value == null || value.equals("")) {  
            return "";  
        } else {
            String zwrot = ((Konto) value).getId()!=null ? String.valueOf(((Konto) value).getId()):null;
            return zwrot;  
        }  
    }  

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public List<Konto> getListakontOstatniaAnalitykaklienta() {
        return listakontOstatniaAnalitykaklienta;
    }

    public void setListakontOstatniaAnalitykaklienta(List<Konto> listakontOstatniaAnalitykaklienta) {
        this.listakontOstatniaAnalitykaklienta = listakontOstatniaAnalitykaklienta;
    }

      
    public static void main(String[] args) {
        String s = "201-2-4";
    }

    public String getElementslownika_nazwapelna() {
        return elementslownika_nazwapelna;
    }

    public void setElementslownika_nazwapelna(String elementslownika_nazwapelna) {
        this.elementslownika_nazwapelna = elementslownika_nazwapelna;
    }

    public String getElementslownika_nazwaskrocona() {
        return elementslownika_nazwaskrocona;
    }

    public void setElementslownika_nazwaskrocona(String elementslownika_nazwaskrocona) {
        this.elementslownika_nazwaskrocona = elementslownika_nazwaskrocona;
    }

    public String getElementslownika_numerkonta() {
        return elementslownika_numerkonta;
    }

    public void setElementslownika_numerkonta(String elementslownika_numerkonta) {
        this.elementslownika_numerkonta = elementslownika_numerkonta;
    }

    public Konto getNoweKonto() {
        return noweKonto;
    }

    public void setNoweKonto(Konto noweKonto) {
        this.noweKonto = noweKonto;
    }
    
    
    
}
