/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpkview;

import beansPodpis.ObslugaPodpisuBean;
import dao.DeklaracjevatDAO;
import dao.JPKVATWersjaDAO;
import dao.JPKvatwersjaEvewidencjaDAO;
import dao.UPODAO;
import daoFK.EVatwpisDedraDAO;
import data.Data;
import embeddable.Mce;
import embeddable.TKodUS;
import entity.DeklSuper;
import entity.DeklaracjaVatSchemaWierszSum;
import entity.Deklaracjevat;
import entity.EVatwpis1;
import entity.EVatwpisSuper;
import entity.Evewidencja;
import entity.JPKSuper;
import entity.JPKVATWersja;
import entity.JPKvatwersjaEvewidencja;
import entity.Podatnik;
import entity.UPO;
import entityfk.EVatwpisDedra;
import entityfk.EVatwpisFK;
import error.E;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.KeyStoreException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import msg.Msg;
import org.primefaces.PrimeFaces;
import pdf.PdfUPO;
import pl.gov.crd.wzor._2020._05._08._9393.JPK;
import pl.gov.crd.wzor._2020._05._08._9393.TKodFormularzaVAT7;
import view.EwidencjaVatView;
 import view.WpisView;
import waluty.Z;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class JPK_VAT2View implements Serializable {

    private static final long serialVersionUID = 1L;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @ManagedProperty(value = "#{ewidencjaVatView}")
    private EwidencjaVatView ewidencjaVatView;
    @Inject
    private TKodUS tKodUS;
    @Inject
    private UPODAO uPODAO;
    @Inject
    private JPKVATWersjaDAO jPKVATWersjaDAO;
    @Inject
    private JPKvatwersjaEvewidencjaDAO jPKvatwersjaEvewidencjaDAO;
    @Inject
    private DeklaracjevatDAO deklaracjevatDAO;
    private List<UPO> lista;
    @Inject
    private UPO selected;
    private boolean nowa0korekta1;
    private boolean pkpir0ksiegi1;
    @Inject
    private EVatwpisDedraDAO eVatwpisDedraDAO;
    List<EVatwpisSuper> bledy;
    private int werjsajpkrecznie;
    private boolean jpkrazemzdeklaracja;
    private String wynikszukaniadeklaracji;
    private Deklaracjevat deklaracjadlajpk;
    private boolean moznawysylacjpk;
    
    public void init() { //E.m(this);
        try {
            lista = uPODAO.findPodatnikRok(wpisView);
            if (lista == null) {
                lista = Collections.synchronizedList(new ArrayList<>());
            } else {
                for (UPO p : lista) {
                    werjsajpkrecznie = 0;
                    if (p.getMiesiac().equals(wpisView.getMiesiacWpisu()) && p.getCode()==200) {
                        nowa0korekta1 = true;
                        werjsajpkrecznie = pobierznumerkorekty();
                        break;
                    }
                }
            }
            pkpir0ksiegi1 = wpisView.isKsiegirachunkowe();
            if (wpisView.getRokWpisu()>2020 || (wpisView.getRokWpisu()==2020 && Integer.parseInt(wpisView.getMiesiacWpisu())>9)) {
                jpkrazemzdeklaracja = true;
                szukajdeklaracji();
            } else {
                jpkrazemzdeklaracja = false;
            }
        } catch (Exception e) {
            
        }
    }
    
    private void szukajdeklaracji() {
        moznawysylacjpk = false;
        List<Deklaracjevat> wyslane = deklaracjevatDAO.findDeklaracjeWyslaneMcJPK(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        int znaleziono = 0;
        for (Deklaracjevat p : wyslane) {
            if (p.getStatus().equals("388")) {
                deklaracjadlajpk = p;
                znaleziono++;
            }
        }
        if (znaleziono==0) {
            if (wpisView.getVatokres()==2) {
                   if (Mce.getMceListKW().contains(wpisView.getMiesiacWpisu())) {
                       wynikszukaniadeklaracji = "Brak przygotowanej deklaracji do wysłania z jpk";
                   }
            } else {
                wynikszukaniadeklaracji = "Brak przygotowanej deklaracji do wysłania z jpk";
            }
        } else if (znaleziono == 1) {
            if (wyslane.get(0).getStatus().equals("399")) {
                wynikszukaniadeklaracji = "Pobrana dekalracja już została wysłana z JPK ale nie pobrano UPO. Nie można wysyłać nowego JPK";
                moznawysylacjpk = false;
            } else {
                wynikszukaniadeklaracji = "Znaleziono deklaracje do wysłania razem z JPK";
                moznawysylacjpk = true;
            }
        }
        if (znaleziono>1) {
            deklaracjadlajpk = null;
            wynikszukaniadeklaracji = "Zachowano więcej niż jedną deklarację dla jpk";
        }
        
    }
    
    
    public void init2() {
        nowa0korekta1 = false;
        if (lista != null) {
            for (UPO p : lista) {
                    if (p.getMiesiac().equals(wpisView.getMiesiacWpisu())) {
                        nowa0korekta1 = true;
                        break;
                    }
            }
        }
    }
    
        
    
    public void przygotujXMLDedra() {
        List<EVatwpisDedra> wiersze =  eVatwpisDedraDAO.findWierszePodatnikMc(wpisView);
        List<EVatwpisSuper> lista = new ArrayList<>(wiersze);
        List<EVatwpisSuper> bledy = weryfikujwiersze(lista);
        if (bledy.size()==0) {
            generujXML(lista, wpisView.getPodatnikObiekt(), nowa0korekta1);
            UPO upo = wysylkaJPK(wpisView.getPodatnikObiekt());
            if (upo != null && upo.getReferenceNumber() != null) {
                this.lista.add(upo);
            }
        } else {
            Msg.msg("Wystąpiły braki w dokumentach (data, numer, kwota). Nie można wygenerować JPK");
        }
    }
    
    public void przygotujXMLDedraDL() {
        List<EVatwpisDedra> wiersze =  eVatwpisDedraDAO.findWierszePodatnikMc(wpisView);
        List<EVatwpisSuper> lista = new ArrayList<>(wiersze);
        List<EVatwpisSuper> bledy = weryfikujwiersze(lista);
        if (bledy.size()==0) {
            String sciezka = generujXML(lista, wpisView.getPodatnikObiekt(), nowa0korekta1);
            String polecenie = "wydrukXML(\""+sciezka+"\")";
            PrimeFaces.current().executeScript(polecenie);
        } else {
            Msg.msg("Wystąpiły braki w dokumentach (data, numer, kwota). Nie można wygenerować JPK");
        }
        
    }
    
    public void przygotujXML() {
        ewidencjaVatView.setPobierzmiesiacdlajpk(true);
        ewidencjaVatView.stworzenieEwidencjiZDokumentow(wpisView.getPodatnikObiekt());
        List<EVatwpisSuper> wiersze = ewidencjaVatView.getListadokvatprzetworzona();
        List<EVatwpisSuper> bledy = weryfikujwiersze(wiersze);
        if (bledy.size()==0) {
            generujXML(wiersze, wpisView.getPodatnikObiekt(), nowa0korekta1);
            UPO upo = wysylkaJPK(wpisView.getPodatnikObiekt());
            if (upo != null && upo.getReferenceNumber() != null) {
                if (this.lista==null) {
                    this.lista = new ArrayList<>();
                }
                this.lista.add(upo);
            } else {
                Msg.msg("e","Wystąpił problem. Nie wysłano JPK");
            }
        } else {
            Msg.msg("Wystąpiły braki w dokumentach (data, numer, kwota). Nie można wygenerować JPK");
        }
    }
    
     public void przygotujXMLDL() {
        ewidencjaVatView.setPobierzmiesiacdlajpk(true);
        ewidencjaVatView.stworzenieEwidencjiZDokumentow(wpisView.getPodatnikObiekt());
        List<EVatwpisSuper> wiersze = ewidencjaVatView.getListadokvatprzetworzona();
        List<EVatwpisSuper> bledy = weryfikujwiersze(wiersze);
        if (bledy.size()==0) {
            String sciezka = generujXML(wiersze, wpisView.getPodatnikObiekt(), nowa0korekta1);
            String polecenie = "wydrukXML(\""+sciezka+"\")";
            PrimeFaces.current().executeScript(polecenie);
        } else {
            Msg.msg("Wystąpiły braki w dokumentach (data, numer, kwota). Nie można wygenerować JPK");
        }
    }
    
     public void przygotujXMLAll(Podatnik podatnik,  List<Podatnik> dowyslania,  List<UPO> jpkzrobione) {
        ewidencjaVatView.setPobierzmiesiacdlajpk(true);
        if (podatnik.getFormaPrawna()==null) {
            ewidencjaVatView.stworzenieEwidencjiZDokumentow(podatnik);
        } else {
            ewidencjaVatView.stworzenieEwidencjiZDokumentowFK(podatnik, null);
        }
        List<EVatwpisSuper> wiersze = ewidencjaVatView.getListadokvatprzetworzona();
        generujXML(wiersze, podatnik, nowa0korekta1);
        UPO upo = wysylkaJPK(podatnik);
        if (upo != null && upo.getReferenceNumber() != null) {
            dowyslania.remove(podatnik);
            jpkzrobione.add(upo);
        }
    }
    
    public void przygotujXMLPodglad() {
        ewidencjaVatView.setPobierzmiesiacdlajpk(true);
        ewidencjaVatView.stworzenieEwidencjiZDokumentow(wpisView.getPodatnikObiekt());
        List<EVatwpisSuper> wiersze = ewidencjaVatView.getListadokvatprzetworzona();
        List<EVatwpisSuper> bledy = weryfikujwiersze(wiersze);
        if (bledy.size()==0) {
            generujXMLPodglad(wiersze, wpisView.getPodatnikObiekt(), nowa0korekta1);
        } else {
            Msg.msg("e","Wystąpiły braki w dokumentach (data, numer, kwota). Nie można wygenerować JPK");
            String data = "brak daty";
            for (EVatwpisSuper p : bledy) {
                if (p instanceof EVatwpis1) {
                    data = ((EVatwpis1) p).getDataWyst() != null ? ((EVatwpis1) p).getDataWyst() : "brak daty";
                } else {
                    data = p.getDokfk().getDatadokumentu() != null ? p.getDokfk().getDatadokumentu() : "brak daty";
                }
                String nr = p.getNrWlDk() != null ? p.getNrWlDk() : "brak numeru";
                Msg.msg("e","Wadliwy dokument: data "+data+" nr "+p.getNrWlDk()+" kwota "+p.getNetto(), "form_dialog_jpk_vat:wiadomoscjpk");
            }
        }
    }
    
    public void przygotujXMLPodgladDedra() {
        List<EVatwpisDedra> wiersze =  eVatwpisDedraDAO.findWierszePodatnikMc(wpisView);
        List<EVatwpisSuper> bledy = weryfikujwierszeDedra(wiersze);
        if (bledy.size()==0) {
            generujXMLPodgladDedra(wiersze, wpisView.getPodatnikObiekt(), nowa0korekta1);
        } else {
            Msg.msg("Wystąpiły braki w dokumentach (data, numer, kwota). Nie można wygenerować JPK");
        }
    }
    
    public void przygotujXMLPodgladAll(Podatnik podatnik) {
        ewidencjaVatView.setPobierzmiesiacdlajpk(true);
        if (podatnik.getFormaPrawna()==null) {
            ewidencjaVatView.stworzenieEwidencjiZDokumentow(podatnik);
        } else {
            ewidencjaVatView.stworzenieEwidencjiZDokumentowFK(podatnik, null);
        }
        List<EVatwpisSuper> wiersze = ewidencjaVatView.getListadokvatprzetworzona();
        generujXMLPodglad(wiersze, podatnik, nowa0korekta1);
    }
    
    public void przygotujXMLFK() {
        ewidencjaVatView.setPobierzmiesiacdlajpk(true);
        ewidencjaVatView.stworzenieEwidencjiZDokumentowFK(wpisView.getPodatnikObiekt(), null);
        List<EVatwpisSuper> wiersze = ewidencjaVatView.getListadokvatprzetworzona();
        List<EVatwpisSuper> bledy = weryfikujwiersze(wiersze);
        if (bledy.size()==0) {
            generujXML(wiersze,wpisView.getPodatnikObiekt(), nowa0korekta1);
            UPO upo = wysylkaJPK(wpisView.getPodatnikObiekt());
//            if (upo != null && upo.getReferenceNumber() != null) {
//                if (this.lista==null) {
//                    this.lista = new ArrayList<>();
//                }
//                this.lista.add(upo);
//            } else {
//                Msg.msg("e","Wystąpił problem. Nie wysłano JPK");
//            }
            System.out.println("");
        } else {
            Msg.msg("Wystąpiły braki w dokumentach (data, numer, kwota). Nie można wygenerować JPK");
        }
    }
    
    public void przygotujXMLFKDL() {
        ewidencjaVatView.setPobierzmiesiacdlajpk(true);
        ewidencjaVatView.stworzenieEwidencjiZDokumentowFK(wpisView.getPodatnikObiekt(), null);
        List<EVatwpisSuper> wiersze = ewidencjaVatView.getListadokvatprzetworzona();
        List<EVatwpisSuper> bledy = weryfikujwiersze(wiersze);
        if (bledy.size()==0) {
            String sciezka = generujXML(wiersze,wpisView.getPodatnikObiekt(), nowa0korekta1);
            String polecenie = "wydrukXML(\""+sciezka+"\")";
            PrimeFaces.current().executeScript(polecenie);
        } else {
            Msg.msg("Wystąpiły braki w dokumentach (data, numer, kwota). Nie można wygenerować JPK");
        }
    }
    
       
    public void przygotujXMLFKPodglad() {
        ewidencjaVatView.setPobierzmiesiacdlajpk(true);
        ewidencjaVatView.stworzenieEwidencjiZDokumentowFK(wpisView.getPodatnikObiekt(), null);
        List<EVatwpisSuper> wiersze = ewidencjaVatView.getListadokvatprzetworzona();
        List<EVatwpisSuper> bledy = weryfikujwiersze(wiersze);
        if (bledy.size()==0) {
            generujXMLPodglad(wiersze, wpisView.getPodatnikObiekt(), nowa0korekta1);
        } else {
            Msg.msg("Wystąpiły braki w dokumentach (data, numer, kwota). Nie można wygenerować JPK");
            String data;
            for (EVatwpisSuper p : bledy) {
                if (p instanceof EVatwpis1) {
                    data = ((EVatwpis1) p).getDataWyst() != null ? ((EVatwpis1) p).getDataWyst() : "brak daty";
                } else {
                    data = p.getDokfk().getDatadokumentu() != null ? p.getDokfk().getDatadokumentu() : "brak daty";
                }
                String nr = p.getNrWlDk() != null ? p.getNrWlDk() : "brak numeru";
                Msg.msg("e","Wadliwy dokument: data "+data+" nr "+nr+" kwota "+p.getNetto());
            }
        }
    }
    
    private UPO wysylkaJPK(Podatnik podatnik) {
        UPO upo = new UPO();
        if (deklaracjadlajpk!=null && deklaracjadlajpk.getId()!=null) {
            upo.setDeklaracja(deklaracjadlajpk);
        } else if (wpisView.getRokWpisu()>2020 || (wpisView.getRokWpisu()==2020 && Integer.valueOf(wpisView.getMiesiacWpisu())>9)){
            return null;
        }
        try {
            boolean moznapodpisac = ObslugaPodpisuBean.moznapodpisacError(wpisView.getPodatnikObiekt().getKartacert(), wpisView.getPodatnikObiekt().getKartapesel());
            if (moznapodpisac) {
                String[] wiadomosc = SzachMatJPK.wysylka(podatnik, wpisView, upo);
                if (!wiadomosc[0].equals("e")) {
                    wiadomosc = zachowajUPO(upo);
                    if (upo.getDeklaracja()!=null) {
                        DeklSuper deklaracja = upo.getDeklaracja();
                        deklaracja.setStatus("399");
                        deklaracja.setIdentyfikator("wysłano z jpk, oczekuje na upo");
                        deklaracja.setOpis(upo.getUpoString());
                        deklaracja.setUpo(upo.getReferenceNumber());
                        deklaracja.setDataupo(upo.getDataupo());
                        deklaracja.setDatazlozenia(upo.getDatajpk());
                        deklaracjevatDAO.edit(deklaracja);
                    }
                    Msg.msg(wiadomosc[0], wiadomosc[1]);
                } else {
                    Msg.msg(wiadomosc[0], wiadomosc[1]);
                }
            } else {
                Msg.msg("e", "Brak karty w czytniku. Nie można wysłać JPK");
            }
        } catch (KeyStoreException ex) {
            Msg.msg("e", "Brak karty w czytniku");
        } catch (IOException ex) {
            Msg.msg("e", "UWAGA! Błędne hasło!");
        } catch (Exception ex) {
            E.e(ex);
        }
        return upo;
    }
    
      
    
    private JPKSuper genJPK(List<EVatwpisSuper> wiersze, Podatnik podatnik, boolean nowa0korekta1) {
        JPKSuper zwrot = null;
        try {
            if (wpisView.getRokWpisu()>2020 || (wpisView.getRokWpisu()==2020 && Integer.parseInt(wpisView.getMiesiacWpisu())>9)) {
                JPKVATWersja jPKVATWersja = jPKVATWersjaDAO.findByName("JPK2020");
                List<JPKvatwersjaEvewidencja> jpkev = jPKvatwersjaEvewidencjaDAO.findJPKEwidencje(jPKVATWersja);
                Map<Evewidencja, JPKvatwersjaEvewidencja> mapa = przetworzjpk(jpkev);
                pl.gov.crd.wzor._2020._05._08._9393.JPK jpk = new JPK();
                if (deklaracjadlajpk!=null) {
                    List<DeklaracjaVatSchemaWierszSum> schemawierszsumarycznylista = deklaracjadlajpk.getSchemawierszsumarycznylista();
                    JPK.Deklaracja.Naglowek naglowek = deklaracja_naglowek();
                    JPK.Deklaracja.PozycjeSzczegolowe pozycje = deklaracja_pozycjeszczegolowe(schemawierszsumarycznylista);
                    JPK.Deklaracja deklaracja = new JPK.Deklaracja();
                    deklaracja.setNaglowek(naglowek);
                    deklaracja.setPozycjeSzczegolowe(pozycje);
                    deklaracja.setPouczenia(BigDecimal.ONE);
                    jpk.setDeklaracja(deklaracja);
                }
                Object[] sprzedaz = utworzWierszeJpkSprzedaz2020(wiersze, mapa);
                List<pl.gov.crd.wzor._2020._05._08._9393.JPK.Ewidencja.SprzedazWiersz> listas = (List<pl.gov.crd.wzor._2020._05._08._9393.JPK.Ewidencja.SprzedazWiersz>) sprzedaz[0];
                pl.gov.crd.wzor._2020._05._08._9393.JPK.Ewidencja.SprzedazCtrl sprzedazCtrl = (pl.gov.crd.wzor._2020._05._08._9393.JPK.Ewidencja.SprzedazCtrl) sprzedaz[1];
                Object[] zakup = utworzwierszjpkZakup2020(wiersze, mapa);
                List<pl.gov.crd.wzor._2020._05._08._9393.JPK.Ewidencja.ZakupWiersz> listaz = (List<pl.gov.crd.wzor._2020._05._08._9393.JPK.Ewidencja.ZakupWiersz>) zakup[0];
                pl.gov.crd.wzor._2020._05._08._9393.JPK.Ewidencja.ZakupCtrl zakupCtrl = (pl.gov.crd.wzor._2020._05._08._9393.JPK.Ewidencja.ZakupCtrl) zakup[1];
                String kodurzedu = tKodUS.getMapaUrzadKod().get(wpisView.getPodatnikObiekt().getUrzadskarbowy());
                jpk.setNaglowek(JPK_VAT2020_Bean.naglowek(wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu(), kodurzedu));
                int cel = werjsajpkrecznie+1;
                jpk.getNaglowek().getCelZlozenia().setValue(Byte.parseByte(String.valueOf(cel)));
                jpk.setPodmiot1(JPK_VAT2020_Bean.podmiot1(podatnik, wpisView.getUzer().getNrtelefonu(), wpisView.getUzer().getEmail()));
                jpk.setEwidencja(new JPK.Ewidencja());
                jpk.getEwidencja().getSprzedazWiersz().addAll(listas);
                jpk.getEwidencja().setSprzedazCtrl(sprzedazCtrl);
                jpk.getEwidencja().getZakupWiersz().addAll(listaz);
                jpk.getEwidencja().setZakupCtrl(zakupCtrl);
                zwrot = jpk;
            } else if (wpisView.getRokWpisu()>2017) {
                JPKVATWersja jPKVATWersja = jPKVATWersjaDAO.findByName("JPK3");
                List<JPKvatwersjaEvewidencja> jpkev = jPKvatwersjaEvewidencjaDAO.findJPKEwidencje(jPKVATWersja);
                Map<Evewidencja, JPKvatwersjaEvewidencja> mapa = przetworzjpk(jpkev);
                jpk201801.JPK jpk = new jpk201801.JPK();
                Object[] sprzedaz = utworzWierszeJpkSprzedaz3(wiersze, mapa);
                List<jpk201801.JPK.SprzedazWiersz> listas = (List<jpk201801.JPK.SprzedazWiersz>) sprzedaz[0];
                jpk201801.JPK.SprzedazCtrl sprzedazCtrl = (jpk201801.JPK.SprzedazCtrl) sprzedaz[1];
                Object[] zakup = utworzwierszjpkZakup3(wiersze, mapa);
                List<jpk201801.JPK.ZakupWiersz> listaz = (List<jpk201801.JPK.ZakupWiersz>) zakup[0];
                jpk201801.JPK.ZakupCtrl zakupCtrl = (jpk201801.JPK.ZakupCtrl) zakup[1];
                jpk.setNaglowek(JPK_VAT3_Bean.naglowek(Data.dzienpierwszy(wpisView), Data.ostatniDzien(wpisView)));
                int cel = werjsajpkrecznie;
                jpk.getNaglowek().setCelZlozenia(cel);
                jpk.setPodmiot1(JPK_VAT3_Bean.podmiot1(podatnik));
                jpk.getSprzedazWiersz().addAll(listas);
                if (sprzedazCtrl != null && sprzedazCtrl.getLiczbaWierszySprzedazy().intValue() > 0) {
                    jpk.setSprzedazCtrl(sprzedazCtrl);
                }
                jpk.getZakupWiersz().addAll(listaz);
                if (zakupCtrl != null && zakupCtrl.getLiczbaWierszyZakupow().intValue() > 0) {
                    jpk.setZakupCtrl(zakupCtrl);
                }
                zwrot = jpk;
            } else {
                JPKVATWersja jPKVATWersja = jPKVATWersjaDAO.findByName("JPK2");
                List<JPKvatwersjaEvewidencja> jpkev = jPKvatwersjaEvewidencjaDAO.findJPKEwidencje(jPKVATWersja);
                Map<Evewidencja, JPKvatwersjaEvewidencja> mapa = przetworzjpk(jpkev);
                jpk201701.JPK jpk = new jpk201701.JPK();
                Object[] sprzedaz = utworzWierszeJpkSprzedaz2(wiersze, mapa);
                List<jpk201701.JPK.SprzedazWiersz> listas = (List<jpk201701.JPK.SprzedazWiersz>) sprzedaz[0];
                jpk201701.JPK.SprzedazCtrl sprzedazCtrl = (jpk201701.JPK.SprzedazCtrl) sprzedaz[1];
                Object[] zakup = utworzwierszjpkZakup2(wiersze, mapa);
                List<jpk201701.JPK.ZakupWiersz> listaz = (List<jpk201701.JPK.ZakupWiersz>) zakup[0];
                jpk201701.JPK.ZakupCtrl zakupCtrl = (jpk201701.JPK.ZakupCtrl) zakup[1];
                jpk.setNaglowek(JPK_VAT2_Bean.naglowek(Data.dzienpierwszy(wpisView), Data.ostatniDzien(wpisView),tKodUS.getMapaUrzadKod().get(wpisView.getPodatnikObiekt().getUrzadskarbowy())));
                byte cel = nowa0korekta1 ? (byte) 2 : (byte) 1;
                jpk.getNaglowek().setCelZlozenia(cel);
                jpk.setPodmiot1(JPK_VAT2_Bean.podmiot1(podatnik));
                jpk.getSprzedazWiersz().addAll(listas);
                if (sprzedazCtrl != null && sprzedazCtrl.getLiczbaWierszySprzedazy().intValue() > 0) {
                    jpk.setSprzedazCtrl(sprzedazCtrl);
                }
                jpk.getZakupWiersz().addAll(listaz);
                if (zakupCtrl != null && zakupCtrl.getLiczbaWierszyZakupow().intValue() > 0) {
                    jpk.setZakupCtrl(zakupCtrl);
                }
                zwrot = jpk;
            }
        } catch(Exception e) {
            Msg.msg("e", "Wystąpił błąd, nie wygenerowano pliku JPK");
            E.e(e);
        }
        return zwrot;
    }
    
    private JPK.Deklaracja.Naglowek.KodFormularzaDekl deklaracja_naglowek_kod() {
        JPK.Deklaracja.Naglowek.KodFormularzaDekl zwrot = new JPK.Deklaracja.Naglowek.KodFormularzaDekl();
        zwrot.setKodSystemowy(zwrot.getKodSystemowy());
        zwrot.setKodPodatku(zwrot.getKodPodatku());
        zwrot.setRodzajZobowiazania(zwrot.getRodzajZobowiazania());
        zwrot.setWersjaSchemy(zwrot.getWersjaSchemy());
        zwrot.setValue(TKodFormularzaVAT7.VAT_7);
        return zwrot;
    }
    
    private JPK.Deklaracja.Naglowek deklaracja_naglowek() {
        JPK.Deklaracja.Naglowek zwrot = new JPK.Deklaracja.Naglowek();
        JPK.Deklaracja.Naglowek.KodFormularzaDekl kod = deklaracja_naglowek_kod();
        zwrot.setKodFormularzaDekl(kod);
        zwrot.setWariantFormularzaDekl(Byte.parseByte("21"));
        return zwrot;
    }
    
    private JPK.Deklaracja.PozycjeSzczegolowe deklaracja_pozycjeszczegolowe(List<DeklaracjaVatSchemaWierszSum> schemawierszsumarycznylista) {
        pl.gov.crd.wzor._2020._05._08._9393.JPK.Deklaracja.PozycjeSzczegolowe pozycjeSzczegolowe = new JPK.Deklaracja.PozycjeSzczegolowe();
        for (DeklaracjaVatSchemaWierszSum p : schemawierszsumarycznylista) {
            try {
                if (p.getNetto1vat2czek3tekst4()==3) {
                    Method method = pl.gov.crd.wzor._2020._05._08._9393.JPK.Deklaracja.PozycjeSzczegolowe.class.getMethod(zwrocpolejpk(p.getPolenetto()),Byte.class);
                    if (p.getDeklaracjaVatWierszSumaryczny().isCzekpole()) {
                        method.invoke(pozycjeSzczegolowe, BigInteger.valueOf(1));
                    } else {
                        method.invoke(pozycjeSzczegolowe, BigInteger.valueOf(0));
                    }
                } else if (p.getNetto1vat2czek3tekst4()==4) {
                    Method method = pl.gov.crd.wzor._2020._05._08._9393.JPK.Deklaracja.PozycjeSzczegolowe.class.getMethod(zwrocpolejpk(p.getPolenetto()),String.class);
                    method.invoke(pozycjeSzczegolowe, p.getDeklaracjaVatWierszSumaryczny().getStringpole());
                } else {
                    Method method = pl.gov.crd.wzor._2020._05._08._9393.JPK.Deklaracja.PozycjeSzczegolowe.class.getMethod(zwrocpolejpk(p.getPolenetto()),BigInteger.class);
                    method.invoke(pozycjeSzczegolowe, BigInteger.valueOf(p.getDeklaracjaVatWierszSumaryczny().getSumanetto()));
                    method = pl.gov.crd.wzor._2020._05._08._9393.JPK.Deklaracja.PozycjeSzczegolowe.class.getMethod(zwrocpolejpk(p.getPolevat()),BigInteger.class);
                    method.invoke(pozycjeSzczegolowe, BigInteger.valueOf(p.getDeklaracjaVatWierszSumaryczny().getSumavat()));
                }
            } catch (Exception e){
                E.e(e);
            }
        }
        return pozycjeSzczegolowe;
    }
    
    private String zwrocpolejpk(String pole) {
        StringBuilder sb = new StringBuilder();
        sb.append("setP");
        sb.append(pole);
        return sb.toString();
    }
    
    public void pobierzwszystkie(List<UPO> jpkzrobione) {
        if (jpkzrobione==null || jpkzrobione.isEmpty()) {
            Msg.msg("e","Lista jest pusta, nie ma czego pobierać");
        } else {
            for (UPO p : jpkzrobione) {
                if (p.getCode()== 120) {
                    pobierzUPO(p);
                }
            }
            Msg.msg("Pobrano potwierdzenia");
        }
    }
    
    
    public void pobierzUPO(UPO selected) {
        try {
            if (selected.getCode()!=200) {
                String[] wiadomosc = SzachMatJPK.pobierzupo(selected.getReferenceNumber(), selected);
                selected.setDataupo(new Date());
                uPODAO.edit(selected);
                if (selected.getDeklaracja()!=null&&selected.getCode()<299) {
                        DeklSuper deklaracja = selected.getDeklaracja();
                        deklaracja.setStatus(String.valueOf(selected.getCode()));
                        deklaracja.setOpis(selected.getDescription());
                        deklaracja.setUpo(selected.getUpoString());
                        deklaracja.setDataupo(selected.getDataupo());
                        deklaracja.setIdentyfikator(selected.getReferenceNumber());
                        deklaracja.setDatazlozenia(selected.getDatajpk());
                        deklaracjevatDAO.edit(deklaracja);
                    }
                Msg.msg(wiadomosc[0], wiadomosc[1]);
            } else {
                Msg.msg("Obecny status wysyłki to 200. Nie pobieram UPO");
            }
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Nie udało się pobrać wiadomości");
        }
    }
    
    public String zachowajJPK(UPO selected) {
        String nazwa = "brak";
        try {
            if (selected.getJpk()!=null) {
                JPKSuper jpk = selected.getJpk();
                JAXBContext context = JAXBContext.newInstance(jpk.getClass());
                Marshaller marshaller = context.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
                String mainfilename = "jpk"+wpisView.getPodatnikObiekt().getNip()+"mcrok"+wpisView.getMiesiacWpisu()+wpisView.getRokWpisuSt()+".xml";
                ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
                String realPath = ctx.getRealPath("/")+"resources\\xml\\";
                FileOutputStream fileStream = new FileOutputStream(new File(realPath+mainfilename));
                OutputStreamWriter writer = new OutputStreamWriter(fileStream, "UTF-8");
                marshaller.marshal(jpk, writer);
                writer.close();
                nazwa = mainfilename;
                Msg.msg("Zachowano JPK");
                String exec = "wydrukJPK('"+mainfilename+"')";
                PrimeFaces.current().executeScript(exec);
            }
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Nie udało się pobrać wiadomości");
        }
        return nazwa;
    }
    
    
    public void usunUPO() {
        try {
            if (selected != null) {
                uPODAO.destroy(selected);
                lista.remove(selected);
                init2();
                Msg.msg("Usunieto wybrany JPK "+selected.getReferenceNumber());
            } else {
                Msg.msg("e", "Nie wybrano JPK");
            }
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Nie udało się usunąć JPK");
        }
    }
    
    public void drukujUPO(UPO item) {
        if (Integer.parseInt(Data.aktualnyRok()) > 2017) {
            PdfUPO.drukuj_JPK3(item, wpisView);
        } else {
            PdfUPO.drukuj_JPK2(item, wpisView);
        }
    }
    
    
    public void generujXMLPodgladDedra(List<EVatwpisDedra> wiersze, Podatnik podatnik, boolean nowa0korekta1) {
        List<EVatwpisSuper> lista = new ArrayList<>(wiersze);
        JPKSuper jpk = genJPK(lista, podatnik, nowa0korekta1);
        try {
            if (jpk instanceof pl.gov.crd.wzor._2020._05._08._9393.JPK) {
                PdfUPO.drukujJPK2020(jpk, wpisView, podatnik);
            } else if (jpk instanceof jpk201801.JPK) {
                PdfUPO.drukujJPK3(jpk, wpisView, podatnik);
            } else {
                PdfUPO.drukujJPK2(jpk, wpisView, podatnik);
            }
        } catch(Exception e) {
            Msg.msg("e", "Wystąpił błąd, nie wygenerowano pliku JPK");
            E.e(e);
        }
    }
    
    public void generujXMLPodglad(List<EVatwpisSuper> wiersze, Podatnik podatnik, boolean nowa0korekta1) {
        JPKSuper jpk = genJPK(wiersze, podatnik, nowa0korekta1);
        try {
            if (jpk instanceof pl.gov.crd.wzor._2020._05._08._9393.JPK) {
                PdfUPO.drukujJPK2020(jpk, wpisView, podatnik);
            } else if (jpk instanceof jpk201801.JPK) {
                PdfUPO.drukujJPK3(jpk, wpisView, podatnik);
            } else {
                PdfUPO.drukujJPK2(jpk, wpisView, podatnik);
            }
        } catch(Exception e) {
            Msg.msg("e", "Wystąpił błąd, nie wygenerowano pliku JPK");
            E.e(e);
        }
    }
    
    
    public String generujXML(List<EVatwpisSuper> wiersze, Podatnik podatnik, boolean nowa0korekta1) {
        JPKSuper jpk = genJPK(wiersze, podatnik, nowa0korekta1);
        String sciezka = null;
        try {
            sciezka = marszajuldoplikuxml(podatnik, jpk);
            Msg.msg("Wygenerowano plik JPK");
        } catch(Exception e) {
            Msg.msg("e", "Wystąpił błąd, nie wygenerowano pliku JPK");
            E.e(e);
        }
        return sciezka;
    }
    
    private String marszajuldoplikuxml(Podatnik podatnik, JPKSuper jpk) {
        String sciezka = null;
        try {
            JAXBContext context = JAXBContext.newInstance(jpk.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            String mainfilename = "jpk"+podatnik.getNip()+"mcrok"+wpisView.getMiesiacWpisu()+wpisView.getRokWpisuSt()+".xml";
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = ctx.getRealPath("/")+"resources\\xml\\";
            FileOutputStream fileStream = new FileOutputStream(new File(realPath+mainfilename));
            OutputStreamWriter writer = new OutputStreamWriter(fileStream, "UTF-8");
            marshaller.marshal(jpk, writer);
            sciezka = mainfilename;
        } catch (Exception ex) {
            E.e(ex);
        }
        return sciezka;
    }
    
    
//public static void main(String[] ar) {
//    FileOutputStream fileStream = null;
//        try {
//            String mainfilename = "jpk8511005008mcrok072017.xml";
//            String plik = "build/web/resources/xml/"+mainfilename;
//            fileStream = new FileOutputStream(new File(plik));
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(JPK_VAT2View.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                fileStream.close();
//            } catch (IOException ex) {
//                Logger.getLogger(JPK_VAT2View.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//}

    private Object[] utworzWierszeJpkSprzedaz2(List wiersze, Map<Evewidencja, JPKvatwersjaEvewidencja> mapa) {
        Object[] zwrot = new Object[2];
        zwrot[0] = Collections.synchronizedList(new ArrayList<>());
        if (wiersze.size() >0) {
            Class c = wiersze.get(0).getClass();
            List<jpk201701.JPK.SprzedazWiersz> lista = Collections.synchronizedList(new ArrayList<>());
            jpk201701.JPK.SprzedazCtrl sprzedazCtrl = new jpk201701.JPK.SprzedazCtrl();
            sprzedazCtrl.setLiczbaWierszySprzedazy(BigInteger.ZERO);
            sprzedazCtrl.setPodatekNalezny(BigDecimal.ZERO);
            if (c.getName().equals("entity.EVatwpis1")) {
                int lp = 1;
                for (Object p : wiersze) {
                    EVatwpis1 wiersz = (EVatwpis1) p;
                    if (!wiersz.getEwidencja().getTypewidencji().equals("z") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                        lista.add(JPK_VAT2_Bean.dodajwierszsprzedazy(wiersz, BigInteger.valueOf(lp++),sprzedazCtrl, mapa.get(wiersz.getEwidencja())));
                    }
                }
            } else if (c.getName().equals("entityfk.EVatwpisDedra")) {
                int lp = 1;
                for (Object p : wiersze) {
                    EVatwpisDedra wiersz = (EVatwpisDedra) p;
                    if (!wiersz.getEwidencja().getTypewidencji().equals("z") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                        lista.add(JPK_VAT2_Bean.dodajwierszsprzedazy(wiersz, BigInteger.valueOf(lp++),sprzedazCtrl, mapa.get(wiersz.getEwidencja())));
                    }
                }
            } else {
                int lp = 1;
                for (Object p : wiersze) {
                    EVatwpisFK wiersz = (EVatwpisFK) p;
                    if (!wiersz.getEwidencja().getTypewidencji().equals("z") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                        lista.add(JPK_VAT2_Bean.dodajwierszsprzedazyFK(wiersz, BigInteger.valueOf(lp++),sprzedazCtrl, mapa.get(wiersz.getEwidencja())));
                    }
                }
            }
            zwrot[0] = lista;
            zwrot[1] = sprzedazCtrl;
        }
        return zwrot;
    }
    
    private Object[] utworzWierszeJpkSprzedaz3(List wiersze,  Map<Evewidencja, JPKvatwersjaEvewidencja> mapa) {
        Object[] zwrot = new Object[2];
        zwrot[0] = Collections.synchronizedList(new ArrayList<>());
        if (wiersze.size() >0) {
            Class c = wiersze.get(0).getClass();
            List<jpk201801.JPK.SprzedazWiersz> lista = Collections.synchronizedList(new ArrayList<>());
            jpk201801.JPK.SprzedazCtrl sprzedazCtrl = new jpk201801.JPK.SprzedazCtrl();
            sprzedazCtrl.setLiczbaWierszySprzedazy(BigInteger.ZERO);
            sprzedazCtrl.setPodatekNalezny(BigDecimal.ZERO);
            if (c.getName().equals("entity.EVatwpis1")) {
                int lp = 1;
                for (Object p : wiersze) {
                    EVatwpis1 wiersz = (EVatwpis1) p;
                    if (!wiersz.getEwidencja().getTypewidencji().equals("z") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                        lista.add(JPK_VAT3_Bean.dodajwierszsprzedazy(wiersz, BigInteger.valueOf(lp++),sprzedazCtrl, mapa.get(wiersz.getEwidencja())));
                    }
                }
            } else if (c.getName().equals("entityfk.EVatwpisDedra")) {
                int lp = 1;
                for (Object p : wiersze) {
                    EVatwpisDedra wiersz = (EVatwpisDedra) p;
                    if (!wiersz.getEwidencja().getTypewidencji().equals("z") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                        lista.add(JPK_VAT3_Bean.dodajwierszsprzedazy(wiersz, BigInteger.valueOf(lp++),sprzedazCtrl, mapa.get(wiersz.getEwidencja())));
                    }
                }
            } else {
                int lp = 1;
                for (Object p : wiersze) {
                    EVatwpisFK wiersz = (EVatwpisFK) p;
                    if (!wiersz.getEwidencja().getTypewidencji().equals("z") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                        lista.add(JPK_VAT3_Bean.dodajwierszsprzedazyFK(wiersz, BigInteger.valueOf(lp++),sprzedazCtrl, mapa.get(wiersz.getEwidencja())));
                    }
                }
            }
            zwrot[0] = lista;
            zwrot[1] = sprzedazCtrl;
        }
        return zwrot;
    }
    
    private Object[] utworzWierszeJpkSprzedaz2020(List wiersze, Map<Evewidencja, JPKvatwersjaEvewidencja> mapa) {
        Object[] zwrot = new Object[2];
        zwrot[0] = Collections.synchronizedList(new ArrayList<>());
        if (wiersze.size() >0) {
            Class c = wiersze.get(0).getClass();
            List<pl.gov.crd.wzor._2020._05._08._9393.JPK.Ewidencja.SprzedazWiersz> lista = Collections.synchronizedList(new ArrayList<>());
            pl.gov.crd.wzor._2020._05._08._9393.JPK.Ewidencja.SprzedazCtrl sprzedazCtrl = new pl.gov.crd.wzor._2020._05._08._9393.JPK.Ewidencja.SprzedazCtrl();
            sprzedazCtrl.setLiczbaWierszySprzedazy(BigInteger.ZERO);
            sprzedazCtrl.setPodatekNalezny(BigDecimal.ZERO);
            if (c.getName().equals("entity.EVatwpis1")) {
                int lp = 1;
                for (Object p : wiersze) {
                    EVatwpis1 wiersz = (EVatwpis1) p;
                    if (!wiersz.getEwidencja().getTypewidencji().equals("z") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                        lista.add(JPK_VAT2020_Bean.dodajwierszsprzedazy(wiersz, BigInteger.valueOf(lp++),sprzedazCtrl, mapa.get(wiersz.getEwidencja())));
                    }
                }
            } else if (c.getName().equals("entityfk.EVatwpisDedra")) {
                int lp = 1;
                for (Object p : wiersze) {
                    EVatwpisDedra wiersz = (EVatwpisDedra) p;
                    if (!wiersz.getEwidencja().getTypewidencji().equals("z") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                        lista.add(JPK_VAT2020_Bean.dodajwierszsprzedazy(wiersz, BigInteger.valueOf(lp++),sprzedazCtrl, mapa.get(wiersz.getEwidencja())));
                    }
                }
            } else {
                int lp = 1;
                for (Object p : wiersze) {
                    EVatwpisFK wiersz = (EVatwpisFK) p;
                    if (!wiersz.getEwidencja().getTypewidencji().equals("z") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                        lista.add(JPK_VAT2020_Bean.dodajwierszsprzedazyFK(wiersz, BigInteger.valueOf(lp++),sprzedazCtrl, mapa.get(wiersz.getEwidencja())));
                    }
                }
            }
            zwrot[0] = lista;
            zwrot[1] = sprzedazCtrl;
        }
        return zwrot;
    }
    
    private Object[] utworzwierszjpkZakup2(List wiersze, Map<Evewidencja, JPKvatwersjaEvewidencja> mapa) {
        Object[] zwrot = new Object[2];
        zwrot[0] = Collections.synchronizedList(new ArrayList<>());
        if (wiersze.size() >0) {
            Class c = wiersze.get(0).getClass();
            List<jpk201701.JPK.ZakupWiersz> lista = Collections.synchronizedList(new ArrayList<>());
            jpk201701.JPK.ZakupCtrl zakupCtrl = new jpk201701.JPK.ZakupCtrl();
            zakupCtrl.setLiczbaWierszyZakupow(BigInteger.ZERO);
            zakupCtrl.setPodatekNaliczony(BigDecimal.ZERO);
            if (c.getName().equals("entity.EVatwpis1")) {
                int lp = 1;
                for (Object p : wiersze) {
                    EVatwpis1 wiersz = (EVatwpis1) p;
                    if (!wiersz.getEwidencja().getTypewidencji().equals("s") && !wiersz.getEwidencja().getTypewidencji().equals("sz") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                        lista.add(JPK_VAT2_Bean.dodajwierszzakupu(wiersz, BigInteger.valueOf(lp++),zakupCtrl, mapa.get(wiersz.getEwidencja())));
                    }
                }
            } else if (c.getName().equals("entityfk.EVatwpisDedra")) {

            } else {
                int lp = 1;
                for (Object p : wiersze) {
                    EVatwpisFK wiersz = (EVatwpisFK) p;
                    if (!wiersz.getEwidencja().getTypewidencji().equals("s") && !wiersz.getEwidencja().getTypewidencji().equals("sz") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                        lista.add(JPK_VAT2_Bean.dodajwierszzakupu(wiersz, BigInteger.valueOf(lp++),zakupCtrl, mapa.get(wiersz.getEwidencja())));
                    }
                }
            }
            zwrot[0] = lista;
            zwrot[1] = zakupCtrl;
        }
        return zwrot;
    }
    
    private Object[] utworzwierszjpkZakup3(List wiersze, Map<Evewidencja, JPKvatwersjaEvewidencja> mapa) {
        Object[] zwrot = new Object[2];
        zwrot[0] = Collections.synchronizedList(new ArrayList<>());
        if (wiersze.size() >0) {
            Class c = wiersze.get(0).getClass();
            List<jpk201801.JPK.ZakupWiersz> lista = Collections.synchronizedList(new ArrayList<>());
            jpk201801.JPK.ZakupCtrl zakupCtrl = new jpk201801.JPK.ZakupCtrl();
            zakupCtrl.setLiczbaWierszyZakupow(BigInteger.ZERO);
            zakupCtrl.setPodatekNaliczony(BigDecimal.ZERO);
            if (c.getName().equals("entity.EVatwpis1")) {
                int lp = 1;
                for (Object p : wiersze) {
                    EVatwpis1 wiersz = (EVatwpis1) p;
                    if (!wiersz.getEwidencja().getTypewidencji().equals("s") && !wiersz.getEwidencja().getTypewidencji().equals("sz") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                        lista.add(JPK_VAT3_Bean.dodajwierszzakupu(wiersz, BigInteger.valueOf(lp++),zakupCtrl, mapa.get(wiersz.getEwidencja())));
                    }
                }
            } else if (c.getName().equals("entityfk.EVatwpisDedra")) {
                int lp = 1;
                for (Object p : wiersze) {
                    EVatwpisDedra wiersz = (EVatwpisDedra) p;
                    if (!wiersz.getEwidencja().getTypewidencji().equals("s") && !wiersz.getEwidencja().getTypewidencji().equals("sz") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                        lista.add(JPK_VAT3_Bean.dodajwierszzakupu(wiersz, BigInteger.valueOf(lp++),zakupCtrl, mapa.get(wiersz.getEwidencja())));
                    }
                }
            } else {
                int lp = 1;
                for (Object p : wiersze) {
                    EVatwpisFK wiersz = (EVatwpisFK) p;
                    if (!wiersz.getEwidencja().getTypewidencji().equals("s") && !wiersz.getEwidencja().getTypewidencji().equals("sz") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                        lista.add(JPK_VAT3_Bean.dodajwierszzakupu(wiersz, BigInteger.valueOf(lp++),zakupCtrl, mapa.get(wiersz.getEwidencja())));
                    }
                }
            }
            zwrot[0] = lista;
            zwrot[1] = zakupCtrl;
        }
        return zwrot;
    }
    
    private Object[] utworzwierszjpkZakup2020(List wiersze, Map<Evewidencja, JPKvatwersjaEvewidencja> mapa) {
        Object[] zwrot = new Object[2];
        zwrot[0] = Collections.synchronizedList(new ArrayList<>());
        if (wiersze.size() >0) {
            Class c = wiersze.get(0).getClass();
            List<pl.gov.crd.wzor._2020._05._08._9393.JPK.Ewidencja.ZakupWiersz> lista = Collections.synchronizedList(new ArrayList<>());
            pl.gov.crd.wzor._2020._05._08._9393.JPK.Ewidencja.ZakupCtrl zakupCtrl = new pl.gov.crd.wzor._2020._05._08._9393.JPK.Ewidencja.ZakupCtrl();
            zakupCtrl.setLiczbaWierszyZakupow(BigInteger.ZERO);
            zakupCtrl.setPodatekNaliczony(BigDecimal.ZERO);
            if (c.getName().equals("entity.EVatwpis1")) {
                int lp = 1;
                for (Object p : wiersze) {
                    EVatwpis1 wiersz = (EVatwpis1) p;
                    if (!wiersz.getEwidencja().getTypewidencji().equals("s") && !wiersz.getEwidencja().getTypewidencji().equals("sz") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                        lista.add(JPK_VAT2020_Bean.dodajwierszzakupu(wiersz, BigInteger.valueOf(lp++),zakupCtrl, mapa.get(wiersz.getEwidencja())));
                    }
                }
            } else if (c.getName().equals("entityfk.EVatwpisDedra")) {
                int lp = 1;
                for (Object p : wiersze) {
                    EVatwpisDedra wiersz = (EVatwpisDedra) p;
                    if (!wiersz.getEwidencja().getTypewidencji().equals("s") && !wiersz.getEwidencja().getTypewidencji().equals("sz") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                        lista.add(JPK_VAT2020_Bean.dodajwierszzakupu(wiersz, BigInteger.valueOf(lp++),zakupCtrl, mapa.get(wiersz.getEwidencja())));
                    }
                }
            } else {
                int lp = 1;
                for (Object p : wiersze) {
                    EVatwpisFK wiersz = (EVatwpisFK) p;
                    if (!wiersz.getEwidencja().getTypewidencji().equals("s") && !wiersz.getEwidencja().getTypewidencji().equals("sz") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                        lista.add(JPK_VAT2020_Bean.dodajwierszzakupu(wiersz, BigInteger.valueOf(lp++),zakupCtrl, mapa.get(wiersz.getEwidencja())));
                    }
                }
            }
            zwrot[0] = lista;
            zwrot[1] = zakupCtrl;
        }
        return zwrot;
    }
    
    private String[] zachowajUPO(UPO upo) {
        String[] zwrot = new String[2];
        zwrot[0] = "i";
        zwrot[1] = "Rozpoczynam zachowanie UPO";
            try {
                JPKSuper jpk = upo.getJpk();
                upo.setWprowadzil(wpisView.getUzer());
                upo.setDataupo(new Date());
                upo.setDatajpk(new Date());
                uPODAO.dodaj(upo);
                zwrot[0] = "i";
                zwrot[1] = "Udane zachowanie UPO";
            } catch (Exception e) {
                zwrot[0] = "e";
                zwrot[1] = "Nieudane zachowanie UPO. Wystąpił błąd. Nie otrzymano upo";
            }
        return zwrot;
    }

    public EwidencjaVatView getEwidencjaVatView() {
        return ewidencjaVatView;
    }

    public void setEwidencjaVatView(EwidencjaVatView ewidencjaVatView) {
        this.ewidencjaVatView = ewidencjaVatView;
    }
 
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public int getWerjsajpkrecznie() {
        return werjsajpkrecznie;
    }

    public void setWerjsajpkrecznie(int werjsajpkrecznie) {
        this.werjsajpkrecznie = werjsajpkrecznie;
    }

    public String getWynikszukaniadeklaracji() {
        return wynikszukaniadeklaracji;
    }

    public void setWynikszukaniadeklaracji(String wynikszukaniadeklaracji) {
        this.wynikszukaniadeklaracji = wynikszukaniadeklaracji;
    }

    public boolean isMoznawysylacjpk() {
        return moznawysylacjpk;
    }

    public void setMoznawysylacjpk(boolean moznawysylacjpk) {
        this.moznawysylacjpk = moznawysylacjpk;
    }

     

    public List<UPO> getLista() {
        return lista;
    }

    public void setLista(List<UPO> lista) {
        this.lista = lista;
    }

    public UPO getSelected() {
        return selected;
    }

    public void setSelected(UPO selected) {
        this.selected = selected;
    }

    public boolean isNowa0korekta1() {
        return nowa0korekta1;
    }

    public void setNowa0korekta1(boolean nowa0korekta1) {
        this.nowa0korekta1 = nowa0korekta1;
    }

    public boolean isJpkrazemzdeklaracja() {
        return jpkrazemzdeklaracja;
    }

    public void setJpkrazemzdeklaracja(boolean jpkrazemzdeklaracja) {
        this.jpkrazemzdeklaracja = jpkrazemzdeklaracja;
    }

    public boolean isPkpir0ksiegi1() {
        return pkpir0ksiegi1;
    }

    public void setPkpir0ksiegi1(boolean pkpir0ksiegi1) {
        this.pkpir0ksiegi1 = pkpir0ksiegi1;
    }

    public List<EVatwpisSuper> getBledy() {
        return bledy;
    }

    public void setBledy(List<EVatwpisSuper> bledy) {
        this.bledy = bledy;
    }

    
    private int pobierznumerkorekty() {
        int numer = 1;
        for (UPO p : lista) {
            if (p.getJpk().getClass().getName().equals("jpk201701.JPK")) {
                numer = 1;
            } else if (p.getJpk().getClass().getName().equals("jpk201801.JPK")) {
                int celzlozenia = ((jpk201801.JPK)p.getJpk()).getNaglowek().getCelZlozenia();
                if (p.getRok().equals(wpisView.getRokWpisuSt()) && p.getMiesiac().equals(wpisView.getMiesiacWpisu()) && celzlozenia > numer && p.getCode()==200) {
                    numer = celzlozenia;
                }
            }
        }
        return numer;
    }

    private List<EVatwpisSuper> weryfikujwiersze(List<EVatwpisSuper> wiersze) {
        List<EVatwpisSuper> zwrot = Collections.synchronizedList(new ArrayList<>());
        for (EVatwpisSuper p : wiersze) {
            if (p instanceof EVatwpisFK) {
                EVatwpisFK pf = (EVatwpisFK) p;
                if (pf.getDataSprz()==null || p.getDataSprz().equals("") || !Data.sprawdzpoprawnoscdaty(p.getDataSprz())) {
                    zwrot.add(p);
                    break;
                }
                if (pf.getDataWyst()==null || pf.getDataWyst().equals("") || !Data.sprawdzpoprawnoscdaty(p.getDataWyst())) {
                    zwrot.add(p);
                    break;
                }
                if (pf.getKontr()==null) {
                    zwrot.add(p);
                    break;
                }
                if (pf.getNrWlDk()==null || pf.getNrWlDk().equals("")) {
                    zwrot.add(p);
                    break;
                }
            }
            if (p instanceof EVatwpis1) {
                EVatwpis1 pf = (EVatwpis1) p;
                if (pf.getDok().getDataWyst()==null || pf.getDok().getDataWyst().equals("") || !Data.sprawdzpoprawnoscdaty(pf.getDok().getDataWyst())) {
                    zwrot.add(p);
                    break;
                }
                if (pf.getDok().getDataSprz()==null || pf.getDok().getDataSprz().equals("") || !Data.sprawdzpoprawnoscdaty(pf.getDok().getDataSprz())) {
                    zwrot.add(p);
                    break;
                }
                if (pf.getDok().getKontr1()==null) {
                    zwrot.add(p);
                    break;
                }
                if (pf.getDok().getNrWlDk()==null || pf.getDok().getNrWlDk().equals("")) {
                    zwrot.add(p);
                    break;
                }
            }
        }
        return zwrot;
    }
    
    private List<EVatwpisSuper> weryfikujwierszeDedra(List<EVatwpisDedra> wiersze) {
        List<EVatwpisSuper> zwrot = Collections.synchronizedList(new ArrayList<>());
        for (EVatwpisDedra p : wiersze) {
            if (p instanceof EVatwpisDedra) {
                EVatwpisDedra pf = p;
                if (pf.getDataoperacji()==null || p.getDataoperacji().equals("") || !Data.sprawdzpoprawnoscdaty(p.getDataoperacji())) {
                    zwrot.add(p);
                    break;
                }
                if (pf.getDatadokumentu()==null || pf.getDatadokumentu().equals("") || !Data.sprawdzpoprawnoscdaty(p.getDatadokumentu())) {
                    zwrot.add(p);
                    break;
                }
                if (pf.getImienazwisko()==null || pf.getImienazwisko().equals("")) {
                    zwrot.add(p);
                    break;
                }
                if (pf.getAdres()==null || pf.getAdres().equals("")) {
                    zwrot.add(p);
                    break;
                }
                if (pf.getFaktura()==null || pf.getFaktura().equals("")) {
                    zwrot.add(p);
                    break;
                }
            }
            
        }
        return zwrot;
    }

    private Map<Evewidencja, JPKvatwersjaEvewidencja> przetworzjpk(List<JPKvatwersjaEvewidencja> jpkev) {
        Map<Evewidencja, JPKvatwersjaEvewidencja> zwrot = new HashMap<>();
        if (jpkev!=null) {
            for (JPKvatwersjaEvewidencja p : jpkev) {
                zwrot.put(p.getEvewidencja(), p);
            }
        }
        return zwrot;
    }

    
    
    
}
