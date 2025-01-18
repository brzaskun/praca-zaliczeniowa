/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pluginkadry;

import comparator.Wierszfakturybazacomparator;
import dao.FakturaDAO;
import dao.FakturywystokresoweDAO;
import dao.WierszfakturybazaDAO;
import data.Data;
import entity.Faktura;
import entity.Fakturywystokresowe;
import entity.Wierszfakturybaza;
import java.io.Serializable;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.ws.WebServiceRef;
import msg.Msg;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class WsKadryFakturaPozycjaView implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @WebServiceRef(wsdlLocation = "http://localhost:8080/kadry/WsKadryFakturaPozycja")
    private WsKadryFakturaPozycja_Service wsKadryFakturaPozycja_Service;
    
    @Inject
    private FakturywystokresoweDAO fakturywystokresoweDAO;
    @Inject
    private FakturaDAO fakturaDAO;
    @Inject
    private WierszfakturybazaDAO wierszfakturybazaDAO;
    private String rok;
    private String mc;
    @Inject
    private WpisView wpisView;
    private List<WierszFaktury> listawierszfaktury;
    private List<Wierszfakturybaza> wierzfakturybazalist;
    private List<Fakturywystokresowe> fakturyokresowe;
    
    
    @PostConstruct
    public void inita() {
        rok = wpisView.getRokWpisuSt();
        mc = wpisView.getMiesiacWpisu();
        String[] okrespoprzeni = Data.poprzedniOkres(mc, rok);
        mc = okrespoprzeni[0];
        rok = okrespoprzeni[1];
        listawierszfaktury = new ArrayList<>();
    }
    
    public void init() {
        try {
            listawierszfaktury = new ArrayList<>();
            fakturyokresowe = fakturywystokresoweDAO.findPodatnikBiezace(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
            wsKadryFakturaPozycja_Service = new WsKadryFakturaPozycja_Service();
            WsKadryFakturaPozycja wsKadryFakturaPozycjaPort = wsKadryFakturaPozycja_Service.getWsKadryFakturaPozycjaPort();
            String hello = wsKadryFakturaPozycjaPort.hello("lolo");
            System.out.println("wsKadryFakturaPozycjaPort: "+hello);
            if (fakturyokresowe.isEmpty()==false) {
                for (Fakturywystokresowe f : fakturyokresowe) {
                    List<WierszFaktury> wiersze = wsKadryFakturaPozycjaPort.kadryfakturapozycjamcrok(f.getDokument().getKontrahent().getNip(), rok, mc);
                    if (wiersze.isEmpty()==false) {
                        f.setRecznaedycja(true);
                        
                        listawierszfaktury.addAll(wiersze);
                        //System.out.println("odp: "+wiersze.get(0).nip+" nazwa: "+wiersze.get(0).nazwa+ " "+wiersze.size());
                    } else {
                        System.out.println("odebrałem pusta baze");
                    }
                }
                fakturywystokresoweDAO.editList(fakturyokresowe);
            }
            wierzfakturybazalist = wierszfakturybazaDAO.findbyRokMc(rok, mc);
            for (WierszFaktury w : listawierszfaktury) {
                Wierszfakturybaza odnaleziony = null;
                for (Wierszfakturybaza wb :wierzfakturybazalist) {
                    //bierzemy tylko te ktore kadrowe zamknely
                    if (wb.getNip().equals(w.getNip())&&wb.getOpis().equals(w.getOpis())&&w.isZamkniety()) {
                        odnaleziony= wb;
                        if (w.getIlosc()!=wb.getIlosc()||w.getKwota()!=wb.getKwota()) {
                            wb.setWymagakorekty(true);
                            wb.setNowailosc(w.getIlosc());
                            wb.setNowakwota(w.getKwota());
                            wb.setDatazamkniecia(w.getData());
                            wierszfakturybazaDAO.edit(wb);
                        } else {
                            wb.setDatazamkniecia(w.getData());
                            wb.setWymagakorekty(false);
                            wierszfakturybazaDAO.edit(wb);
                        }
                    }
                }
                if (odnaleziony==null&&w.isZamkniety()) {
                    odnaleziony = new Wierszfakturybaza(w);
                    odnaleziony.setDatazamkniecia(w.getData());
                    wierszfakturybazaDAO.create(odnaleziony);
                    wierzfakturybazalist.add(odnaleziony);
                }
            }
            Collections.sort(wierzfakturybazalist, new Wierszfakturybazacomparator());
            wierzfakturybazalist = wierzfakturybazalist.stream()
            .sorted(Comparator.comparing(
                w -> w.getNazwa() != null ? w.getNazwa().toLowerCase(new Locale("pl", "PL")) : "",
                Collator.getInstance(new Locale("pl", "PL"))))
            .collect(Collectors.toList());

            System.out.println("a");
        } catch (Exception ex) {
            Logger.getLogger(WsKadryFakturaPozycjaView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
 public void weryfikujfaktury() {
    if (wierzfakturybazalist == null || fakturyokresowe == null) {
        Msg.msg("e", "Brak wymaganych danych do weryfikacji");
        return;
    }
    
    List<Faktura> fakturyZBazy = fakturaDAO.findFakturyByRokMcWystawca(
            wpisView.getRokWpisuSt(), 
            wpisView.getMiesiacWpisu(), 
            wpisView.getPodatnikObiekt()
    );
    
    if (fakturyZBazy == null) {
        Msg.msg("e", "Nie ma pozycji w miesiącu");
        return;
    }
    
    fakturyZBazy = fakturyZBazy.stream()
            .filter(item -> item.isWygenerowanaautomatycznie())
            .collect(Collectors.toList());
    
    if (fakturyZBazy.isEmpty()) {
        Msg.msg("e", "Nie ma pozycji w miesiącu");
        return;
    }
    
    // Mapa dla szybszego wyszukiwania faktur po NIP
    Map<String, Faktura> mapaFaktur = fakturyZBazy.stream()
            .collect(Collectors.toMap(f -> f.getKontrahent().getNip(), f -> f, (f1, f2) -> f1));
    
    boolean zmiany = false;
    
    for (Fakturywystokresowe f : fakturyokresowe) {
        Faktura fakturaWystawiona = mapaFaktur.get(f.getDokument().getWystawca().getNip());
        if (fakturaWystawiona != null) {
            for (Wierszfakturybaza w : wierzfakturybazalist) {
                if (w.getDatafaktury() == null && fakturaWystawiona.getKontrahent() != null && 
                        w.getNip().equals(fakturaWystawiona.getKontrahent().getNip())) {
                    w.setDatafaktury(fakturaWystawiona.getDatasporzadzenia());
                    w.setNaniesiony(true);
                    zmiany = true;
                    break;
                }
            }
        }
    }
    
    if (zmiany) {
        wierszfakturybazaDAO.editList(wierzfakturybazalist);
        Msg.msg("Lista faktur zweryfikowana");
    } else {
        Msg.msg("Brak zmian w danych");
    }
}

    
    public void usun() {
        if (listawierszfaktury!=null&&listawierszfaktury.isEmpty()==false) {
            wierszfakturybazaDAO.removeList(wierzfakturybazalist);
            wierzfakturybazalist = new ArrayList<>();
            Msg.msg("Usunięto wiersze z miesiąca");
        }
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

    public List<WierszFaktury> getListawierszfaktury() {
        return listawierszfaktury;
    }

    public void setListawierszfaktury(List<WierszFaktury> listawierszfaktury) {
        this.listawierszfaktury = listawierszfaktury;
    }

    public List<Wierszfakturybaza> getWierzfakturybazalist() {
        return wierzfakturybazalist;
    }

    public void setWierzfakturybazalist(List<Wierszfakturybaza> wierzfakturybazalist) {
        this.wierzfakturybazalist = wierzfakturybazalist;
    }
    
    
    
}
