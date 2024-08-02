/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpkview;

import beansPodpis.ObslugaPodpisuBean;
import beansVAT.EwidPoz;
import dao.DeklaracjevatDAO;
import dao.EVatwpisDedraDAO;
import dao.EvewidencjaDAO;
import dao.JPKVATWersjaDAO;
import dao.JPKvatwersjaEvewidencjaDAO;
import dao.KlientJPKDAO;
import dao.SMTPSettingsDAO;
import dao.UPODAO;
import data.Data;
import embeddable.Kwartaly;
import embeddable.Mce;
import embeddable.TKodUS;
import entity.DeklSuper;
import entity.DeklaracjaVatSchemaWierszSum;
import entity.Deklaracjevat;
import entity.EVatwpis1;
import entity.EVatwpisKJPK;
import entity.EVatwpisSuper;
import entity.Evewidencja;
import entity.JPKSuper;
import entity.JPKVATWersja;
import entity.JPKvatwersjaEvewidencja;
import entity.KlientJPK;
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
import java.util.Locale;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import mail.MaiManager;
import msg.Msg;
import org.primefaces.PrimeFaces;
import pdf.PdfUPO;
import view.EwidencjaVatView;
import view.WpisView;
import waluty.Z;
import xml.XMLValid;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class Jpk_VAT2NView implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    @Inject
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
    @Inject
    private KlientJPKDAO klientJPKDAO;
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
    private boolean odnalezionodeklaracje;
    private boolean jpkfk;
    private boolean jpkfkkorekta;
    private boolean jpkpkpir;
    private boolean jpkpkpirkorekta;
    private Evewidencja ewidencjazakupu;
    @Inject
    private EvewidencjaDAO evewidencjaDAO;
    @Inject
    private SMTPSettingsDAO sMTPSettingsDAO;

    
    public void init() { //E.m(this);
        try {
            lista = uPODAO.findPodatnikRok(wpisView);
            if (lista == null) {
                lista = Collections.synchronizedList(new ArrayList<>());
            } else {
                for (UPO p : lista) {
                    werjsajpkrecznie = 1;
                    if (p.getMiesiac().equals(wpisView.getMiesiacWpisu()) && p.getCode()==200) {
                        nowa0korekta1 = true;
                        werjsajpkrecznie = 2;
                        break;
                    }
                }
            }
            pkpir0ksiegi1 = wpisView.isKsiegirachunkowe();
            odnalezionodeklaracje = false;
            if (wpisView.isJpk2020M()||wpisView.isJpk2020M2()) {
                jpkrazemzdeklaracja = true;
                szukajdeklaracji();
            } else if (wpisView.isJpk2020K()||wpisView.isJpk2020K2()){
                if (wpisView.getMiesiacWpisu().equals("03")||wpisView.getMiesiacWpisu().equals("06")||wpisView.getMiesiacWpisu().equals("09")||wpisView.getMiesiacWpisu().equals("12")) {
                    jpkrazemzdeklaracja = true;
                    szukajdeklaracji();
                } else {
                    jpkrazemzdeklaracja = false;
                    szukajdeklaracji();
                }
            }
            jpkfk = false;
            jpkfkkorekta = false;
            jpkpkpir = false;
            jpkpkpirkorekta = false;
            if (!nowa0korekta1 && pkpir0ksiegi1) {
                jpkfk = ustawpokazywanie();
            } else if (nowa0korekta1 && pkpir0ksiegi1) {
                jpkfkkorekta = ustawpokazywanie();
            } else if (!nowa0korekta1 && !pkpir0ksiegi1) {
                jpkpkpir = ustawpokazywanie();
            } else if (nowa0korekta1 && !pkpir0ksiegi1) {
                jpkpkpirkorekta = ustawpokazywanie();    
            }
            ewidencjazakupu = evewidencjaDAO.znajdzponazwie("zakup");
        } catch (Exception e) {
            E.e(e);
        }
    }
     
    private boolean ustawpokazywanie() {
        boolean zwrot = false;
        if (nowa0korekta1==true) {
            zwrot = true;
        } else if (!wpisView.isJpk2020M() && !wpisView.isJpk2020K() && !wpisView.isJpk2020M2() && !wpisView.isJpk2020K2()) {
            zwrot = true;
        } else if ((wpisView.isJpk2020M() && odnalezionodeklaracje)||wpisView.isJpk2020M2() && odnalezionodeklaracje) {
            zwrot = true;
        } else if ((wpisView.isJpk2020K() && odnalezionodeklaracje)||(wpisView.isJpk2020K2() && odnalezionodeklaracje)) {
            if (!wpisView.getMiesiacWpisu().equals("03")&&!wpisView.getMiesiacWpisu().equals("06")&&!wpisView.getMiesiacWpisu().equals("09")&&!wpisView.getMiesiacWpisu().equals("12")) {
                zwrot = false;
            } else {
                zwrot = true;
            }
        } else if (wpisView.isJpk2020K() && !odnalezionodeklaracje || wpisView.isJpk2020K2() && !odnalezionodeklaracje) {
            if (!wpisView.getMiesiacWpisu().equals("03")&&!wpisView.getMiesiacWpisu().equals("06")&&!wpisView.getMiesiacWpisu().equals("09")&&!wpisView.getMiesiacWpisu().equals("12")) {
                zwrot = true;
            } else {
                zwrot = false;
            }
        }
        return zwrot;
    }
    
    
    private void szukajdeklaracji() {
        odnalezionodeklaracje = false;
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
                odnalezionodeklaracje = false;
            } else {
                wynikszukaniadeklaracji = "Znaleziono deklaracje do wysłania razem z JPK";
                odnalezionodeklaracje = true;
            }
        }
        if (znaleziono>1) {
            deklaracjadlajpk = null;
            wynikszukaniadeklaracji = "Zachowano więcej niż jedną deklarację dla jpk";
        }
        
    }
    
   public void pokazdeklaracje() {
       if (selected!=null) {
           deklaracjadlajpk = selected.getDeklaracja();
           Msg.dP();
                   
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
        List<KlientJPK> kliencijpk = klientJPKDAO.findbyKlientRokMc(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        duplikujjpkwnt(kliencijpk);
        if (bledy.size()==0 && (wiersze!=null||kliencijpk!=null)) {
            String[] sciezka = generujXML(kliencijpk, lista, wpisView.getPodatnikObiekt(), nowa0korekta1, werjsajpkrecznie);
            if (sciezka[1]==null) {
                Msg.msg("e","Błąd generowania/walidacji JPK. Wstrzymuje przetwarzanie danych");
            } else if (wiersze==null) {
                Msg.msg("e","Brak zaksięgowanych faktur nie mozna generowac JPK");
            }  else {
                UPO upo = wysylkaJPK(wpisView.getPodatnikObiekt());
                if (upo != null && upo.getReferenceNumber() != null) {
                    this.lista.add(upo);
                }
            }
        } else {
            Msg.msg("Wystąpiły braki w dokumentach (data, numer, kwota). Nie można wygenerować JPK");
        }
    }
    
    public void przygotujXMLDedraDL() {
        List<EVatwpisDedra> wiersze =  eVatwpisDedraDAO.findWierszePodatnikMc(wpisView);
        List<EVatwpisSuper> lista = new ArrayList<>(wiersze);
        List<EVatwpisSuper> bledy = weryfikujwiersze(lista);
        List<KlientJPK> kliencijpk = klientJPKDAO.findbyKlientRokMc(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        duplikujjpkwnt(kliencijpk);
        if (bledy.size()==0 && (wiersze!=null||kliencijpk!=null)) {
            String[] sciezka = generujXML(kliencijpk, lista, wpisView.getPodatnikObiekt(), nowa0korekta1, werjsajpkrecznie);
            String polecenie = "wydrukXML(\""+sciezka[0]+"\")";
            PrimeFaces.current().executeScript(polecenie);
        } else if (wiersze==null) {
            Msg.msg("e","Brak zaksięgowanych faktur nie mozna generowac JPK");
        } else {
            Msg.msg("e","Wystąpiły braki w dokumentach (data, numer, kwota). Nie można wygenerować JPK");
        }
        
    }
    
    public void przygotujXML() {
        ewidencjaVatView.setPobierzmiesiacdlajpk(true);
        ewidencjaVatView.stworzenieEwidencjiZDokumentowJPK(wpisView.getPodatnikObiekt());
        List<EVatwpisSuper> wiersze = ewidencjaVatView.getListadokvatprzetworzona();
        List<EVatwpisSuper> bledy = weryfikujwiersze(wiersze);
        List<KlientJPK> kliencijpk = klientJPKDAO.findbyKlientRokMc(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        duplikujjpkwnt(kliencijpk);
        if (bledy.size()==0 && (wiersze!=null||kliencijpk!=null)) {
            String[] sciezka = generujXML(kliencijpk, wiersze, wpisView.getPodatnikObiekt(), nowa0korekta1, werjsajpkrecznie);
            if (sciezka[1]==null) {
                Msg.msg("e","Błąd generowania/walidacji JPK. Wstrzymuje przetwarzanie danych");
            } else {
                UPO upo = wysylkaJPK(wpisView.getPodatnikObiekt());
                if (upo != null && upo.getReferenceNumber() != null) {
                    if (this.lista==null) {
                        this.lista = new ArrayList<>();
                    }
                    this.lista.add(upo);
                } else {
                    Msg.msg("e","Wystąpił problem. Nie wysłano JPK");
                }
            }
        }  else if (wiersze==null) {
            Msg.msg("e","Brak zaksięgowanych faktur nie mozna generowac JPK");
        } else {
            Msg.msg("e","Wystąpiły braki w dokumentach (data, numer, kwota). Nie można wygenerować JPK");
        }
    }
    
     public void przygotujXMLDL() {
        ewidencjaVatView.setPobierzmiesiacdlajpk(true);
        ewidencjaVatView.stworzenieEwidencjiZDokumentowJPK(wpisView.getPodatnikObiekt());
        List<EVatwpisSuper> wiersze = ewidencjaVatView.getListadokvatprzetworzona();
        List<EVatwpisSuper> bledy = weryfikujwiersze(wiersze);
        List<KlientJPK> kliencijpk = klientJPKDAO.findbyKlientRokMc(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        duplikujjpkwnt(kliencijpk);
        if (bledy.size()==0 && (wiersze!=null||kliencijpk!=null)) {
            String[] sciezka = generujXML(kliencijpk, wiersze, wpisView.getPodatnikObiekt(), nowa0korekta1, werjsajpkrecznie);
            if (sciezka[2]!=null) {
                Msg.msg("e",sciezka[2]);
            } else if (sciezka[0]!=null){
                String polecenie = "wydrukXML(\""+sciezka[0]+"\")";
                PrimeFaces.current().executeScript(polecenie);
            }
        } else if (wiersze==null) {
           Msg.msg("e","Brak zaksięgowanych faktur nie mozna generowac JPK");
        } else {
            Msg.msg("e","Wystąpiły braki w dokumentach (data, numer, kwota). Nie można wygenerować JPK");
        }
    }
    
     public void przygotujXMLAll(Podatnik podatnik,  List<Podatnik> dowyslania,  List<UPO> jpkzrobione) {
        ewidencjaVatView.setPobierzmiesiacdlajpk(true);
        if (podatnik.getFormaPrawna()==null) {
            ewidencjaVatView.stworzenieEwidencjiZDokumentowJPK(podatnik);
        } else {
            ewidencjaVatView.stworzenieEwidencjiZDokumentowFK(podatnik, null);
        }
        List<EVatwpisSuper> wiersze = ewidencjaVatView.getListadokvatprzetworzona();
        List<KlientJPK> kliencijpk = klientJPKDAO.findbyKlientRokMc(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        duplikujjpkwnt(kliencijpk);
        String[] sciezka = generujXML(kliencijpk, wiersze, podatnik, nowa0korekta1, werjsajpkrecznie);
        if (sciezka[1]==null) {
                Msg.msg("e","Błąd generowania/walidacji JPK. Wstrzymuje przetwarzanie danych");
        } else {
            UPO upo = wysylkaJPK(podatnik);
            if (upo != null && upo.getReferenceNumber() != null) {
                dowyslania.remove(podatnik);
                jpkzrobione.add(upo);
            }
        }
    }
    
    public void przygotujXMLPodglad() {
        ewidencjaVatView.setPobierzmiesiacdlajpk(true);
        ewidencjaVatView.stworzenieEwidencjiZDokumentowJPK(wpisView.getPodatnikObiekt());
        List<EVatwpisSuper> wiersze = ewidencjaVatView.getListadokvatprzetworzona();
        List<EVatwpisSuper> bledy = weryfikujwiersze(wiersze);
        List<KlientJPK> kliencijpk = klientJPKDAO.findbyKlientRokMc(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        duplikujjpkwnt(kliencijpk);
        if (bledy.size()==0 && (wiersze!=null||kliencijpk!=null)) {
            generujXMLPodglad(kliencijpk, wiersze, wpisView.getPodatnikObiekt(), nowa0korekta1, werjsajpkrecznie);
        } else if (wiersze==null) {
           Msg.msg("e","Brak zaksięgowanych faktur nie mozna generowac JPK");
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
        List<KlientJPK> kliencijpk = klientJPKDAO.findbyKlientRokMc(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        if (bledy.size()==0 && (wiersze!=null||kliencijpk!=null)) {
            generujXMLPodgladDedra(kliencijpk, wiersze, wpisView.getPodatnikObiekt(), nowa0korekta1, werjsajpkrecznie);
        } else if (wiersze==null) {
            Msg.msg("e","Brak zaksięgowanych faktur nie mozna generowac JPK");
        } else {
            Msg.msg("e","Wystąpiły braki w dokumentach (data, numer, kwota). Nie można wygenerować JPK");
        }
    }
    
    public void przygotujXMLPodgladAll(Podatnik podatnik) {
        ewidencjaVatView.setPobierzmiesiacdlajpk(true);
        if (podatnik.getFormaPrawna()==null) {
            ewidencjaVatView.stworzenieEwidencjiZDokumentow(podatnik, false);
        } else {
            ewidencjaVatView.stworzenieEwidencjiZDokumentowFKJPK(podatnik, null);
        }
        List<EVatwpisSuper> wiersze = ewidencjaVatView.getListadokvatprzetworzona();
        List<KlientJPK> kliencijpk = klientJPKDAO.findbyKlientRokMc(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        generujXMLPodglad(kliencijpk, wiersze, podatnik, nowa0korekta1, werjsajpkrecznie);
    }
    
    public void przygotujXMLFK() {
        ewidencjaVatView.setPobierzmiesiacdlajpk(true);
        ewidencjaVatView.stworzenieEwidencjiZDokumentowFKJPK(wpisView.getPodatnikObiekt(), null);
        List<EVatwpisSuper> wiersze = ewidencjaVatView.getListadokvatprzetworzona();
        List<EVatwpisSuper> bledy = weryfikujwiersze(wiersze);
        List<KlientJPK> kliencijpk = klientJPKDAO.findbyKlientRokMc(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        duplikujjpkwnt(kliencijpk);
        if (bledy.size()==0 && (wiersze!=null||kliencijpk!=null)) {
            String[] sciezka = generujXML(kliencijpk, wiersze,wpisView.getPodatnikObiekt(), nowa0korekta1, werjsajpkrecznie);
            if (sciezka[1]==null) {
                Msg.msg("e","Błąd generowania/walidacji JPK. Wstrzymuje przetwarzanie danych");
            } else {
                UPO upo = wysylkaJPK(wpisView.getPodatnikObiekt());
                if (upo != null && upo.getReferenceNumber() != null) {
                    if (this.lista==null) {
                        this.lista = new ArrayList<>();
                    }
                    this.lista.add(upo);
                } else {
                    Msg.msg("e","Wystąpił problem. Nie wysłano JPK");
                }
            }
        } else if (wiersze==null) {
            Msg.msg("e","Brak zaksięgowanych faktur nie mozna generowac JPK");
        } else {
            Msg.msg("e","Wystąpiły braki w dokumentach (data, numer, kwota). Nie można wygenerować JPK");
        }
    }
    
    public void przygotujXMLFKDL() {
        ewidencjaVatView.setPobierzmiesiacdlajpk(true);
        ewidencjaVatView.stworzenieEwidencjiZDokumentowFKJPK(wpisView.getPodatnikObiekt(), null);
        List<EVatwpisSuper> wiersze = ewidencjaVatView.getListadokvatprzetworzona();
        List<EVatwpisSuper> bledy = weryfikujwiersze(wiersze);
        List<KlientJPK> kliencijpk = klientJPKDAO.findbyKlientRokMc(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        duplikujjpkwnt(kliencijpk);
        if (bledy.size()==0 && (wiersze!=null||kliencijpk!=null)) {
            String[] sciezka = generujXML(kliencijpk, wiersze,wpisView.getPodatnikObiekt(), nowa0korekta1, werjsajpkrecznie);
            String polecenie = "wydrukXML(\""+sciezka[0]+"\")";
            PrimeFaces.current().executeScript(polecenie);
        } else if (wiersze==null) {
            Msg.msg("e","Brak zaksięgowanych faktur nie mozna generowac JPK");
        } else {
            Msg.msg("e","Wystąpiły braki w dokumentach (data, numer, kwota). Nie można wygenerować JPK");
        }
    }
    
       
    public void przygotujXMLFKPodglad() {
        ewidencjaVatView.setPobierzmiesiacdlajpk(true);
        ewidencjaVatView.stworzenieEwidencjiZDokumentowFKJPK(wpisView.getPodatnikObiekt(), null);
        List<EVatwpisSuper> wiersze = ewidencjaVatView.getListadokvatprzetworzona();
        List<EVatwpisSuper> bledy = weryfikujwiersze(wiersze);
        List<KlientJPK> kliencijpk = klientJPKDAO.findbyKlientRokMc(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        duplikujjpkwnt(kliencijpk);
        if (bledy.size()==0 && (wiersze!=null||kliencijpk!=null)) {
            generujXMLPodglad(kliencijpk, wiersze, wpisView.getPodatnikObiekt(), nowa0korekta1, werjsajpkrecznie);
        } else if (wiersze==null) {
            Msg.msg("e","Brak zaksięgowanych faktur nie mozna generowac JPK");
        } else {
            Msg.msg("e","Wystąpiły braki w dokumentach (data, numer, kwota). Nie można wygenerować JPK");
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
        }
        try {
            boolean moznapodpisac = ObslugaPodpisuBean.moznapodpisacError(wpisView.getPodatnikObiekt().getKartacert(), wpisView.getPodatnikObiekt().getKartapesel());
            if (moznapodpisac) {
                System.out.println("podpis");
                String[] wiadomosc = SzachMatJPK.wysylka(podatnik, wpisView, upo);
                if (!wiadomosc[0].equals("e")) {
                    wiadomosc = zachowajUPO(upo);
                    if (upo.getDeklaracja()!=null&& upo.getCode()==120) {
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
    
        private static final String trescmaila = "<p> Dzień dobry</p> <p> Przesyłamy informacje o naliczonych kwotach z tytułu VAT</p> "
            + "<p> dla firmy <span style=\"color:#008000;\">%s</span> NIP %s</p> "
            + "<p> do zapłaty/przelania w miesiącu <span style=\"color:#008000;\">%s/%s</span></p> "
            + " <table align=\"left\" border=\"1\" cellpadding=\"1\" cellspacing=\"1\" style=\"width: 500px;\"> <caption> zestawienie zobowiązań</caption> <thead> <tr> "
            + "<th scope=\"col\"> lp</th> <th scope=\"col\"> tytuł</th> <th scope=\"col\"> kwota</th> </tr> </thead> <tbody> "
             + "<tr><td style=\"text-align: center;\"> 1</td><td><span >VAT należny</span></td> <td style=\"text-align: right;\"><span style=\"text-align: right;font-weight: bold\"> %.2f</span></td> </tr> "
             + "<tr><td style=\"text-align: center;\"> 2</td><td><span >VAT naliczony</span></td> <td style=\"text-align: right;\"><span style=\"text-align: right;font-weight: bold\"> %.2f</span></td> </tr> "
             + "<tr><td style=\"text-align: center;\"> 3</td><td><span  style='font-weight: bold'>do zapłaty</span></td> <td style=\"text-align: right;\"><span style=\"text-align: right;font-weight: bold\"> %.2f</span></td> </tr> "
            + "<tr><td style=\"text-align: center;\"> 4</td><td><span>do zwrotu</span></td> <td style=\"text-align: right;\"><span style=\"text-align: right;font-weight: bold\"> %.2f</span></td> </tr> "
            + "</tbody> </table>"
            + " <p> &nbsp;</p> <p> &nbsp;</p> <p> &nbsp;</p><br/> "
            + "<p> Ważne! Przelew do US jedną kwotą na JEDNO indywidualne konto wskazane przez US.</p>"
            + "<p> Przypominamy o terminie płatności VAT:</p>"
            + " <p> do <span style=\"color:#008000;\">25-go</span> &nbsp; następnego miesiąca za miesiąc poprzedni</p>"
            + " <p> &nbsp;</p>";
    
    public void wyslijVAT(UPO upo) {
        try {
            JPKSuper jpk = upo.getJpk();
            String tytuł = String.format("Taxman - informacja o VAT za %s/%s", wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
            double nalezny = 0.0;
            double naliczony = 0.0;
            double dozaplaty = 0.0;
            double dozwrotu = 0.0;
            boolean wyslac = false;
            if (jpk instanceof pl.gov.crd.wzor._2021._12._27._11149.JPK) {
                pl.gov.crd.wzor._2021._12._27._11149.JPK o = (pl.gov.crd.wzor._2021._12._27._11149.JPK)jpk;
                nalezny = o.getEwidencja().getSprzedazCtrl().getPodatekNalezny().doubleValue();
                naliczony = o.getEwidencja().getZakupCtrl().getPodatekNaliczony().doubleValue();
                if (nalezny>naliczony) {
                    dozaplaty = nalezny-naliczony;
                } else {
                    dozwrotu = naliczony-nalezny;
                }
                wyslac = true;
            } else if (jpk instanceof pl.gov.crd.wzor._2021._12._27._11148.JPK) {
                pl.gov.crd.wzor._2021._12._27._11148.JPK o = (pl.gov.crd.wzor._2021._12._27._11148.JPK)jpk;
                nalezny = o.getEwidencja().getSprzedazCtrl().getPodatekNalezny().doubleValue();
                naliczony = o.getEwidencja().getZakupCtrl().getPodatekNaliczony().doubleValue();
                if (nalezny>naliczony) {
                    dozaplaty = nalezny-naliczony;
                } else {
                    dozwrotu = naliczony-nalezny;
                }
                wyslac = true;
            }
            if (wyslac) {
                String tresc = String.format(new Locale("pl_PL"), trescmaila, wpisView.getPodatnikObiekt().getPrintnazwa(), wpisView.getPodatnikObiekt().getNip(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu(),
                        nalezny, naliczony, dozaplaty,dozwrotu);
                MaiManager.mailManagerZUSPIT(wpisView.getPodatnikObiekt().getEmail(), tytuł, tresc, wpisView.getUzer().getEmail(), null, sMTPSettingsDAO.findSprawaByDef());
                msg.Msg.msg("Wysłano do klienta informacje o podatku");
            } else {
                msg.Msg.msg("e","Nieobsługiwany format JPK");
            }
        } catch (Exception e){
            
        }
    }

    
    private JPKSuper genJPK(List<KlientJPK> kliencijpk, List<EVatwpisSuper> wiersze, Podatnik podatnik, boolean nowa0korekta1, int werjsajpkrecznie1pierowtna2korekta) {
        JPKSuper zwrot = null;
        try {
            if (wpisView.isJpk2020M2()||podatnik.getNip().equals("5263158333")) {
                JPKVATWersja jPKVATWersja = jPKVATWersjaDAO.findByName("JPK2022");
                List<JPKvatwersjaEvewidencja> jpkev = jPKvatwersjaEvewidencjaDAO.findJPKEwidencje(jPKVATWersja);
                Map<Evewidencja, JPKvatwersjaEvewidencja> mapa = przetworzjpk(jpkev);
                pl.gov.crd.wzor._2021._12._27._11148.JPK jpk = new pl.gov.crd.wzor._2021._12._27._11148.JPK();
                if (deklaracjadlajpk!=null) {
                    List<DeklaracjaVatSchemaWierszSum> schemawierszsumarycznylista = deklaracjadlajpk.getSchemawierszsumarycznylista();
                    pl.gov.crd.wzor._2021._12._27._11148.JPK.Deklaracja.Naglowek naglowek = deklaracja_naglowekM2();
                    List<EwidPoz> ewidpozlista = deklaracjadlajpk.getEwidpozlista();
                    pl.gov.crd.wzor._2021._12._27._11148.JPK.Deklaracja.PozycjeSzczegolowe pozycje = deklaracja_pozycjeszczegoloweM2(schemawierszsumarycznylista, ewidpozlista);
                    pl.gov.crd.wzor._2021._12._27._11148.JPK.Deklaracja deklaracja = new pl.gov.crd.wzor._2021._12._27._11148.JPK.Deklaracja();
                    deklaracja.setNaglowek(naglowek);
                    deklaracja.setPozycjeSzczegolowe(pozycje);
                    deklaracja.setPouczenia(BigDecimal.ONE);
                    jpk.setDeklaracja(deklaracja);
                }
                Object[] sprzedaz = utworzWierszeJpkSprzedaz2020M2(kliencijpk, wiersze, mapa);
                List<pl.gov.crd.wzor._2021._12._27._11148.JPK.Ewidencja.SprzedazWiersz> listas = (List<pl.gov.crd.wzor._2021._12._27._11148.JPK.Ewidencja.SprzedazWiersz>) sprzedaz[0];
                pl.gov.crd.wzor._2021._12._27._11148.JPK.Ewidencja.SprzedazCtrl sprzedazCtrl = (pl.gov.crd.wzor._2021._12._27._11148.JPK.Ewidencja.SprzedazCtrl) sprzedaz[1];
                Object[] zakup = utworzwierszjpkZakup2020M2(kliencijpk, wiersze, mapa);
                List<pl.gov.crd.wzor._2021._12._27._11148.JPK.Ewidencja.ZakupWiersz> listaz = (List<pl.gov.crd.wzor._2021._12._27._11148.JPK.Ewidencja.ZakupWiersz>) zakup[0];
                pl.gov.crd.wzor._2021._12._27._11148.JPK.Ewidencja.ZakupCtrl zakupCtrl = (pl.gov.crd.wzor._2021._12._27._11148.JPK.Ewidencja.ZakupCtrl) zakup[1];
                String kodurzedu = tKodUS.getMapaUrzadKod().get(wpisView.getPodatnikObiekt().getUrzadskarbowy());
                jpk.setNaglowek(JPK_VAT2020M2_Bean.naglowek(wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu(), kodurzedu));
                int cel = 1;
                if (nowa0korekta1) {
                    cel = 2;
                }
                if (werjsajpkrecznie1pierowtna2korekta==1) {
                    cel = 1;
                }
                jpk.getNaglowek().getCelZlozenia().setValue(Byte.parseByte(String.valueOf(cel)));
                jpk.setPodmiot1(JPK_VAT2020M2_Bean.podmiot1(podatnik, wpisView.getUzer().getNrtelefonu(), wpisView.getUzer().getEmail()));
                jpk.setEwidencja(new pl.gov.crd.wzor._2021._12._27._11148.JPK.Ewidencja());
                jpk.getEwidencja().getSprzedazWiersz().addAll(listas);
                jpk.getEwidencja().setSprzedazCtrl(sprzedazCtrl);
                jpk.getEwidencja().getZakupWiersz().addAll(listaz);
                jpk.getEwidencja().setZakupCtrl(zakupCtrl);
                zwrot=jpk;
            } else if (wpisView.isJpk2020K2()) {
                JPKVATWersja jPKVATWersja = jPKVATWersjaDAO.findByName("JPK2022");
                List<JPKvatwersjaEvewidencja> jpkev = jPKvatwersjaEvewidencjaDAO.findJPKEwidencje(jPKVATWersja);
                Map<Evewidencja, JPKvatwersjaEvewidencja> mapa = przetworzjpk(jpkev);
                pl.gov.crd.wzor._2021._12._27._11149.JPK jpk = new pl.gov.crd.wzor._2021._12._27._11149.JPK();
                if (deklaracjadlajpk!=null) {
                    List<DeklaracjaVatSchemaWierszSum> schemawierszsumarycznylista = deklaracjadlajpk.getSchemawierszsumarycznylista();
                    pl.gov.crd.wzor._2021._12._27._11149.JPK.Deklaracja.Naglowek naglowek = deklaracja_naglowekK2();
                    List<EwidPoz> ewidpozlista = deklaracjadlajpk.getEwidpozlista();
                    pl.gov.crd.wzor._2021._12._27._11149.JPK.Deklaracja.PozycjeSzczegolowe pozycje = deklaracja_pozycjeszczegoloweK2(schemawierszsumarycznylista, ewidpozlista);
                    pl.gov.crd.wzor._2021._12._27._11149.JPK.Deklaracja deklaracja = new pl.gov.crd.wzor._2021._12._27._11149.JPK.Deklaracja();
                    deklaracja.setNaglowek(naglowek);
                    deklaracja.setPozycjeSzczegolowe(pozycje);
                    deklaracja.setPouczenia(BigDecimal.ONE);
                    jpk.setDeklaracja(deklaracja);
                }
                Object[] sprzedaz = utworzWierszeJpkSprzedaz2020K2(kliencijpk, wiersze, mapa);
                List<pl.gov.crd.wzor._2021._12._27._11149.JPK.Ewidencja.SprzedazWiersz> listas = (List<pl.gov.crd.wzor._2021._12._27._11149.JPK.Ewidencja.SprzedazWiersz>) sprzedaz[0];
                pl.gov.crd.wzor._2021._12._27._11149.JPK.Ewidencja.SprzedazCtrl sprzedazCtrl = (pl.gov.crd.wzor._2021._12._27._11149.JPK.Ewidencja.SprzedazCtrl) sprzedaz[1];
                Object[] zakup = utworzwierszjpkZakup2020K2(kliencijpk, wiersze, mapa);
                List<pl.gov.crd.wzor._2021._12._27._11149.JPK.Ewidencja.ZakupWiersz> listaz = (List<pl.gov.crd.wzor._2021._12._27._11149.JPK.Ewidencja.ZakupWiersz>) zakup[0];
                pl.gov.crd.wzor._2021._12._27._11149.JPK.Ewidencja.ZakupCtrl zakupCtrl = (pl.gov.crd.wzor._2021._12._27._11149.JPK.Ewidencja.ZakupCtrl) zakup[1];
                String kodurzedu = tKodUS.getMapaUrzadKod().get(wpisView.getPodatnikObiekt().getUrzadskarbowy());
                jpk.setNaglowek(JPK_VAT2020K2_Bean.naglowek(wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu(), kodurzedu));
                int cel = 1;
                if (nowa0korekta1) {
                    cel = 2;
                }
                jpk.getNaglowek().getCelZlozenia().setValue(Byte.parseByte(String.valueOf(cel)));
                jpk.setPodmiot1(JPK_VAT2020K2_Bean.podmiot1(podatnik, wpisView.getUzer().getNrtelefonu(), wpisView.getUzer().getEmail()));
                jpk.setEwidencja(new pl.gov.crd.wzor._2021._12._27._11149.JPK.Ewidencja());
                jpk.getEwidencja().getSprzedazWiersz().addAll(listas);
                jpk.getEwidencja().setSprzedazCtrl(sprzedazCtrl);
                jpk.getEwidencja().getZakupWiersz().addAll(listaz);
                jpk.getEwidencja().setZakupCtrl(zakupCtrl);
                zwrot=jpk;
            } else if (wpisView.isJpk2020M()||podatnik.getNip().equals("5263158333")) {
                JPKVATWersja jPKVATWersja = jPKVATWersjaDAO.findByName("JPK2020");
                List<JPKvatwersjaEvewidencja> jpkev = jPKvatwersjaEvewidencjaDAO.findJPKEwidencje(jPKVATWersja);
                Map<Evewidencja, JPKvatwersjaEvewidencja> mapa = przetworzjpk(jpkev);
                pl.gov.crd.wzor._2020._05._08._9393.JPK jpk = new pl.gov.crd.wzor._2020._05._08._9393.JPK();
                if (deklaracjadlajpk!=null) {
                    List<DeklaracjaVatSchemaWierszSum> schemawierszsumarycznylista = deklaracjadlajpk.getSchemawierszsumarycznylista();
                    pl.gov.crd.wzor._2020._05._08._9393.JPK.Deklaracja.Naglowek naglowek = deklaracja_naglowekM();
                    List<EwidPoz> ewidpozlista = deklaracjadlajpk.getEwidpozlista();
                    pl.gov.crd.wzor._2020._05._08._9393.JPK.Deklaracja.PozycjeSzczegolowe pozycje = deklaracja_pozycjeszczegoloweM(schemawierszsumarycznylista, ewidpozlista);
                    pl.gov.crd.wzor._2020._05._08._9393.JPK.Deklaracja deklaracja = new pl.gov.crd.wzor._2020._05._08._9393.JPK.Deklaracja();
                    deklaracja.setNaglowek(naglowek);
                    deklaracja.setPozycjeSzczegolowe(pozycje);
                    deklaracja.setPouczenia(BigDecimal.ONE);
                    jpk.setDeklaracja(deklaracja);
                }
                Object[] sprzedaz = utworzWierszeJpkSprzedaz2020M(kliencijpk, wiersze, mapa);
                List<pl.gov.crd.wzor._2020._05._08._9393.JPK.Ewidencja.SprzedazWiersz> listas = (List<pl.gov.crd.wzor._2020._05._08._9393.JPK.Ewidencja.SprzedazWiersz>) sprzedaz[0];
                pl.gov.crd.wzor._2020._05._08._9393.JPK.Ewidencja.SprzedazCtrl sprzedazCtrl = (pl.gov.crd.wzor._2020._05._08._9393.JPK.Ewidencja.SprzedazCtrl) sprzedaz[1];
                Object[] zakup = utworzwierszjpkZakup2020M(kliencijpk, wiersze, mapa);
                List<pl.gov.crd.wzor._2020._05._08._9393.JPK.Ewidencja.ZakupWiersz> listaz = (List<pl.gov.crd.wzor._2020._05._08._9393.JPK.Ewidencja.ZakupWiersz>) zakup[0];
                pl.gov.crd.wzor._2020._05._08._9393.JPK.Ewidencja.ZakupCtrl zakupCtrl = (pl.gov.crd.wzor._2020._05._08._9393.JPK.Ewidencja.ZakupCtrl) zakup[1];
                String kodurzedu = tKodUS.getMapaUrzadKod().get(wpisView.getPodatnikObiekt().getUrzadskarbowy());
                jpk.setNaglowek(JPK_VAT2020M_Bean.naglowek(wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu(), kodurzedu));
                int cel = 1;
                if (nowa0korekta1) {
                    cel = 2;
                }
                jpk.getNaglowek().getCelZlozenia().setValue(Byte.parseByte(String.valueOf(cel)));
                jpk.setPodmiot1(JPK_VAT2020M_Bean.podmiot1(podatnik, wpisView.getUzer().getNrtelefonu(), wpisView.getUzer().getEmail()));
                jpk.setEwidencja(new pl.gov.crd.wzor._2020._05._08._9393.JPK.Ewidencja());
                jpk.getEwidencja().getSprzedazWiersz().addAll(listas);
                jpk.getEwidencja().setSprzedazCtrl(sprzedazCtrl);
                jpk.getEwidencja().getZakupWiersz().addAll(listaz);
                jpk.getEwidencja().setZakupCtrl(zakupCtrl);
                zwrot=jpk;
            } else if (wpisView.isJpk2020K()) {
                JPKVATWersja jPKVATWersja = jPKVATWersjaDAO.findByName("JPK2020");
                List<JPKvatwersjaEvewidencja> jpkev = jPKvatwersjaEvewidencjaDAO.findJPKEwidencje(jPKVATWersja);
                Map<Evewidencja, JPKvatwersjaEvewidencja> mapa = przetworzjpk(jpkev);
                pl.gov.crd.wzor._2020._05._08._9394.JPK jpk = new pl.gov.crd.wzor._2020._05._08._9394.JPK();
                if (deklaracjadlajpk!=null) {
                    List<DeklaracjaVatSchemaWierszSum> schemawierszsumarycznylista = deklaracjadlajpk.getSchemawierszsumarycznylista();
                    pl.gov.crd.wzor._2020._05._08._9394.JPK.Deklaracja.Naglowek naglowek = deklaracja_naglowekK();
                    List<EwidPoz> ewidpozlista = deklaracjadlajpk.getEwidpozlista();
                    pl.gov.crd.wzor._2020._05._08._9394.JPK.Deklaracja.PozycjeSzczegolowe pozycje = deklaracja_pozycjeszczegoloweK(schemawierszsumarycznylista, ewidpozlista);
                    pl.gov.crd.wzor._2020._05._08._9394.JPK.Deklaracja deklaracja = new pl.gov.crd.wzor._2020._05._08._9394.JPK.Deklaracja();
                    deklaracja.setNaglowek(naglowek);
                    deklaracja.setPozycjeSzczegolowe(pozycje);
                    deklaracja.setPouczenia(BigDecimal.ONE);
                    jpk.setDeklaracja(deklaracja);
                }
                Object[] sprzedaz = utworzWierszeJpkSprzedaz2020K(kliencijpk, wiersze, mapa);
                List<pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.SprzedazWiersz> listas = (List<pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.SprzedazWiersz>) sprzedaz[0];
                pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.SprzedazCtrl sprzedazCtrl = (pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.SprzedazCtrl) sprzedaz[1];
                Object[] zakup = utworzwierszjpkZakup2020K(kliencijpk, wiersze, mapa);
                List<pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.ZakupWiersz> listaz = (List<pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.ZakupWiersz>) zakup[0];
                pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.ZakupCtrl zakupCtrl = (pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.ZakupCtrl) zakup[1];
                String kodurzedu = tKodUS.getMapaUrzadKod().get(wpisView.getPodatnikObiekt().getUrzadskarbowy());
                jpk.setNaglowek(JPK_VAT2020K_Bean.naglowek(wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu(), kodurzedu));
                int cel = 1;
                if (nowa0korekta1) {
                    cel = 2;
                }
                jpk.getNaglowek().getCelZlozenia().setValue(Byte.parseByte(String.valueOf(cel)));
                jpk.setPodmiot1(JPK_VAT2020K_Bean.podmiot1(podatnik, wpisView.getUzer().getNrtelefonu(), wpisView.getUzer().getEmail()));
                jpk.setEwidencja(new pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja());
                jpk.getEwidencja().getSprzedazWiersz().addAll(listas);
                jpk.getEwidencja().setSprzedazCtrl(sprzedazCtrl);
                jpk.getEwidencja().getZakupWiersz().addAll(listaz);
                jpk.getEwidencja().setZakupCtrl(zakupCtrl);
                zwrot=jpk;
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
                if (cel>2) {cel=2;};
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
    
        
    private pl.gov.crd.wzor._2020._05._08._9393.JPK.Deklaracja.Naglowek.KodFormularzaDekl deklaracja_naglowek_kodM() {
        pl.gov.crd.wzor._2020._05._08._9393.JPK.Deklaracja.Naglowek.KodFormularzaDekl zwrot = new pl.gov.crd.wzor._2020._05._08._9393.JPK.Deklaracja.Naglowek.KodFormularzaDekl();
        zwrot.setKodSystemowy(zwrot.getKodSystemowy());
        zwrot.setKodPodatku(zwrot.getKodPodatku());
        zwrot.setRodzajZobowiazania(zwrot.getRodzajZobowiazania());
        zwrot.setWersjaSchemy(zwrot.getWersjaSchemy());
        zwrot.setValue(pl.gov.crd.wzor._2020._05._08._9393.TKodFormularzaVAT7.VAT_7);
        return zwrot;
    }
    private pl.gov.crd.wzor._2021._12._27._11148.JPK.Deklaracja.Naglowek.KodFormularzaDekl deklaracja_naglowek_kodM2() {
        pl.gov.crd.wzor._2021._12._27._11148.JPK.Deklaracja.Naglowek.KodFormularzaDekl zwrot = new pl.gov.crd.wzor._2021._12._27._11148.JPK.Deklaracja.Naglowek.KodFormularzaDekl();
        zwrot.setKodSystemowy(zwrot.getKodSystemowy());
        zwrot.setKodPodatku(zwrot.getKodPodatku());
        zwrot.setRodzajZobowiazania(zwrot.getRodzajZobowiazania());
        zwrot.setWersjaSchemy(zwrot.getWersjaSchemy());
        zwrot.setValue(pl.gov.crd.wzor._2021._12._27._11148.TKodFormularzaVAT7.VAT_7);
        return zwrot;
    }
    
    private pl.gov.crd.wzor._2020._05._08._9393.JPK.Deklaracja.Naglowek deklaracja_naglowekM() {
        pl.gov.crd.wzor._2020._05._08._9393.JPK.Deklaracja.Naglowek zwrot = new pl.gov.crd.wzor._2020._05._08._9393.JPK.Deklaracja.Naglowek();
        pl.gov.crd.wzor._2020._05._08._9393.JPK.Deklaracja.Naglowek.KodFormularzaDekl kod = deklaracja_naglowek_kodM();
        zwrot.setKodFormularzaDekl(kod);
        zwrot.setWariantFormularzaDekl(Byte.parseByte("21"));
        return zwrot;
    }
    
    private pl.gov.crd.wzor._2021._12._27._11148.JPK.Deklaracja.Naglowek deklaracja_naglowekM2() {
        pl.gov.crd.wzor._2021._12._27._11148.JPK.Deklaracja.Naglowek zwrot = new pl.gov.crd.wzor._2021._12._27._11148.JPK.Deklaracja.Naglowek();
        pl.gov.crd.wzor._2021._12._27._11148.JPK.Deklaracja.Naglowek.KodFormularzaDekl kod = deklaracja_naglowek_kodM2();
        zwrot.setKodFormularzaDekl(kod);
        zwrot.setWariantFormularzaDekl(Byte.parseByte("22"));
        return zwrot;
    }
    
    private pl.gov.crd.wzor._2020._05._08._9393.JPK.Deklaracja.PozycjeSzczegolowe deklaracja_pozycjeszczegoloweM(List<DeklaracjaVatSchemaWierszSum> schemawierszsumarycznylista, List<EwidPoz> ewidpozlista) {
        pl.gov.crd.wzor._2020._05._08._9393.JPK.Deklaracja.PozycjeSzczegolowe pozycjeSzczegolowe = new pl.gov.crd.wzor._2020._05._08._9393.JPK.Deklaracja.PozycjeSzczegolowe();
        for (EwidPoz r : ewidpozlista) {
            try {
                Method method = pl.gov.crd.wzor._2020._05._08._9393.JPK.Deklaracja.PozycjeSzczegolowe.class.getMethod(zwrocpolejpk(r.getPolenetto()),BigInteger.class);
                method.invoke(pozycjeSzczegolowe, BigInteger.valueOf(Z.zUD(r.getNetto())));
                if (!r.getWierszSchemaEwidencja().getEvewidencja().isTylkoNetto()) {
                    method = pl.gov.crd.wzor._2020._05._08._9393.JPK.Deklaracja.PozycjeSzczegolowe.class.getMethod(zwrocpolejpk(r.getPolevat()),BigInteger.class);
                    method.invoke(pozycjeSzczegolowe, BigInteger.valueOf(Z.zUD(r.getVat())));
                }
            } catch (Exception e){
                E.e(e);
            }
        }
        for (DeklaracjaVatSchemaWierszSum p : schemawierszsumarycznylista) {
            try {
                if (p.getNetto1vat2czek3tekst4()==3) {
                    Method method = pl.gov.crd.wzor._2020._05._08._9393.JPK.Deklaracja.PozycjeSzczegolowe.class.getMethod(zwrocpolejpk(p.getPolenetto()),Byte.class);
                    if (p.getDeklaracjaVatWierszSumaryczny().isCzekpole()) {
                        method.invoke(pozycjeSzczegolowe, Byte.valueOf("1"));
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
    
    private pl.gov.crd.wzor._2021._12._27._11148.JPK.Deklaracja.PozycjeSzczegolowe deklaracja_pozycjeszczegoloweM2(List<DeklaracjaVatSchemaWierszSum> schemawierszsumarycznylista, List<EwidPoz> ewidpozlista) {
        pl.gov.crd.wzor._2021._12._27._11148.JPK.Deklaracja.PozycjeSzczegolowe pozycjeSzczegolowe = new pl.gov.crd.wzor._2021._12._27._11148.JPK.Deklaracja.PozycjeSzczegolowe();
        for (EwidPoz r : ewidpozlista) {
            try {
                Method method = pl.gov.crd.wzor._2021._12._27._11148.JPK.Deklaracja.PozycjeSzczegolowe.class.getMethod(zwrocpolejpk(r.getPolenetto()),BigInteger.class);
                method.invoke(pozycjeSzczegolowe, BigInteger.valueOf(Z.zUD(r.getNetto())));
                if (!r.getWierszSchemaEwidencja().getEvewidencja().isTylkoNetto()) {
                    method = pl.gov.crd.wzor._2021._12._27._11148.JPK.Deklaracja.PozycjeSzczegolowe.class.getMethod(zwrocpolejpk(r.getPolevat()),BigInteger.class);
                    method.invoke(pozycjeSzczegolowe, BigInteger.valueOf(Z.zUD(r.getVat())));
                }
            } catch (Exception e){
                E.e(e);
            }
        }
        for (DeklaracjaVatSchemaWierszSum p : schemawierszsumarycznylista) {
            try {
                if (p.getNetto1vat2czek3tekst4()==3) {
                    Method method = pl.gov.crd.wzor._2021._12._27._11148.JPK.Deklaracja.PozycjeSzczegolowe.class.getMethod(zwrocpolejpk(p.getPolenetto()),Byte.class);
                    if (p.getDeklaracjaVatWierszSumaryczny().isCzekpole()) {
                        method.invoke(pozycjeSzczegolowe, Byte.valueOf("1"));
                    }
                } else if (p.getNetto1vat2czek3tekst4()==4) {
                    Method method = pl.gov.crd.wzor._2021._12._27._11148.JPK.Deklaracja.PozycjeSzczegolowe.class.getMethod(zwrocpolejpk(p.getPolenetto()),String.class);
                    method.invoke(pozycjeSzczegolowe, p.getDeklaracjaVatWierszSumaryczny().getStringpole());
                } else {
                    Method method = pl.gov.crd.wzor._2021._12._27._11148.JPK.Deklaracja.PozycjeSzczegolowe.class.getMethod(zwrocpolejpk(p.getPolenetto()),BigInteger.class);
                    method.invoke(pozycjeSzczegolowe, BigInteger.valueOf(p.getDeklaracjaVatWierszSumaryczny().getSumanetto()));
                    method = pl.gov.crd.wzor._2021._12._27._11148.JPK.Deklaracja.PozycjeSzczegolowe.class.getMethod(zwrocpolejpk(p.getPolevat()),BigInteger.class);
                    method.invoke(pozycjeSzczegolowe, BigInteger.valueOf(p.getDeklaracjaVatWierszSumaryczny().getSumavat()));
                }
            } catch (Exception e){
                E.e(e);
            }
        }
        return pozycjeSzczegolowe;
    }
    
    private pl.gov.crd.wzor._2020._05._08._9394.JPK.Deklaracja.Naglowek.KodFormularzaDekl deklaracja_naglowek_kodK() {
        pl.gov.crd.wzor._2020._05._08._9394.JPK.Deklaracja.Naglowek.KodFormularzaDekl zwrot = new pl.gov.crd.wzor._2020._05._08._9394.JPK.Deklaracja.Naglowek.KodFormularzaDekl();
        zwrot.setKodSystemowy(zwrot.getKodSystemowy());
        zwrot.setKodPodatku(zwrot.getKodPodatku());
        zwrot.setRodzajZobowiazania(zwrot.getRodzajZobowiazania());
        zwrot.setWersjaSchemy(zwrot.getWersjaSchemy());
        zwrot.setValue(pl.gov.crd.wzor._2020._05._08._9394.TKodFormularzaVATK.VAT_7_K);
        return zwrot;
    }
    
    private pl.gov.crd.wzor._2021._12._27._11149.JPK.Deklaracja.Naglowek.KodFormularzaDekl deklaracja_naglowek_kodK2() {
        pl.gov.crd.wzor._2021._12._27._11149.JPK.Deklaracja.Naglowek.KodFormularzaDekl zwrot = new pl.gov.crd.wzor._2021._12._27._11149.JPK.Deklaracja.Naglowek.KodFormularzaDekl();
        zwrot.setKodSystemowy(zwrot.getKodSystemowy());
        zwrot.setKodPodatku(zwrot.getKodPodatku());
        zwrot.setRodzajZobowiazania(zwrot.getRodzajZobowiazania());
        zwrot.setWersjaSchemy(zwrot.getWersjaSchemy());
        zwrot.setValue(pl.gov.crd.wzor._2021._12._27._11149.TKodFormularzaVATK.VAT_7_K);
        return zwrot;
    }
    
    private pl.gov.crd.wzor._2020._05._08._9394.JPK.Deklaracja.Naglowek deklaracja_naglowekK() {
        pl.gov.crd.wzor._2020._05._08._9394.JPK.Deklaracja.Naglowek zwrot = new pl.gov.crd.wzor._2020._05._08._9394.JPK.Deklaracja.Naglowek();
        pl.gov.crd.wzor._2020._05._08._9394.JPK.Deklaracja.Naglowek.KodFormularzaDekl kod = deklaracja_naglowek_kodK();
        zwrot.setKodFormularzaDekl(kod);
        zwrot.setWariantFormularzaDekl(Byte.parseByte("15"));
        zwrot.setKwartal(Byte.valueOf(Kwartaly.getMapamckw().get(wpisView.getMiesiacWpisu())));
        return zwrot;
    }
    
    private pl.gov.crd.wzor._2021._12._27._11149.JPK.Deklaracja.Naglowek deklaracja_naglowekK2() {
        pl.gov.crd.wzor._2021._12._27._11149.JPK.Deklaracja.Naglowek zwrot = new pl.gov.crd.wzor._2021._12._27._11149.JPK.Deklaracja.Naglowek();
        pl.gov.crd.wzor._2021._12._27._11149.JPK.Deklaracja.Naglowek.KodFormularzaDekl kod = deklaracja_naglowek_kodK2();
        zwrot.setKodFormularzaDekl(kod);
        zwrot.setWariantFormularzaDekl(Byte.parseByte("16"));
        zwrot.setKwartal(Byte.valueOf(Kwartaly.getMapamckw().get(wpisView.getMiesiacWpisu())));
        return zwrot;
    }
    
    private pl.gov.crd.wzor._2020._05._08._9394.JPK.Deklaracja.PozycjeSzczegolowe deklaracja_pozycjeszczegoloweK(List<DeklaracjaVatSchemaWierszSum> schemawierszsumarycznylista, List<EwidPoz> ewidpozlista) {
        pl.gov.crd.wzor._2020._05._08._9394.JPK.Deklaracja.PozycjeSzczegolowe pozycjeSzczegolowe = new pl.gov.crd.wzor._2020._05._08._9394.JPK.Deklaracja.PozycjeSzczegolowe();
        for (EwidPoz r : ewidpozlista) {
            try {
                Method method = pl.gov.crd.wzor._2020._05._08._9394.JPK.Deklaracja.PozycjeSzczegolowe.class.getMethod(zwrocpolejpk(r.getPolenetto()),BigInteger.class);
                method.invoke(pozycjeSzczegolowe, BigInteger.valueOf(Z.zUD(r.getNetto())));
                if (!r.getWierszSchemaEwidencja().getEvewidencja().isTylkoNetto()) {
                    method = pl.gov.crd.wzor._2020._05._08._9394.JPK.Deklaracja.PozycjeSzczegolowe.class.getMethod(zwrocpolejpk(r.getPolevat()),BigInteger.class);
                    method.invoke(pozycjeSzczegolowe, BigInteger.valueOf(Z.zUD(r.getVat())));
                }
            } catch (Exception e){
                E.e(e);
            }
        }
        for (DeklaracjaVatSchemaWierszSum p : schemawierszsumarycznylista) {
            try {
                if (p.getNetto1vat2czek3tekst4()==3) {
                    Method method = pl.gov.crd.wzor._2020._05._08._9394.JPK.Deklaracja.PozycjeSzczegolowe.class.getMethod(zwrocpolejpk(p.getPolenetto()),Byte.class);
                    if (p.getDeklaracjaVatWierszSumaryczny().isCzekpole()) {
                        method.invoke(pozycjeSzczegolowe, Byte.valueOf("1"));
                    }
                } else if (p.getNetto1vat2czek3tekst4()==4) {
                    Method method = pl.gov.crd.wzor._2020._05._08._9394.JPK.Deklaracja.PozycjeSzczegolowe.class.getMethod(zwrocpolejpk(p.getPolenetto()),String.class);
                    method.invoke(pozycjeSzczegolowe, p.getDeklaracjaVatWierszSumaryczny().getStringpole());
                } else {
                    Method method = pl.gov.crd.wzor._2020._05._08._9394.JPK.Deklaracja.PozycjeSzczegolowe.class.getMethod(zwrocpolejpk(p.getPolenetto()),BigInteger.class);
                    method.invoke(pozycjeSzczegolowe, BigInteger.valueOf(p.getDeklaracjaVatWierszSumaryczny().getSumanetto()));
                    method = pl.gov.crd.wzor._2020._05._08._9394.JPK.Deklaracja.PozycjeSzczegolowe.class.getMethod(zwrocpolejpk(p.getPolevat()),BigInteger.class);
                    method.invoke(pozycjeSzczegolowe, BigInteger.valueOf(p.getDeklaracjaVatWierszSumaryczny().getSumavat()));
                }
            } catch (Exception e){
                E.e(e);
            }
        }
        return pozycjeSzczegolowe;
    }
    
    private pl.gov.crd.wzor._2021._12._27._11149.JPK.Deklaracja.PozycjeSzczegolowe deklaracja_pozycjeszczegoloweK2(List<DeklaracjaVatSchemaWierszSum> schemawierszsumarycznylista, List<EwidPoz> ewidpozlista) {
        pl.gov.crd.wzor._2021._12._27._11149.JPK.Deklaracja.PozycjeSzczegolowe pozycjeSzczegolowe = new pl.gov.crd.wzor._2021._12._27._11149.JPK.Deklaracja.PozycjeSzczegolowe();
        for (EwidPoz r : ewidpozlista) {
            try {
                Method method = pl.gov.crd.wzor._2021._12._27._11149.JPK.Deklaracja.PozycjeSzczegolowe.class.getMethod(zwrocpolejpk(r.getPolenetto()),BigInteger.class);
                method.invoke(pozycjeSzczegolowe, BigInteger.valueOf(Z.zUD(r.getNetto())));
                if (!r.getWierszSchemaEwidencja().getEvewidencja().isTylkoNetto()) {
                    method = pl.gov.crd.wzor._2021._12._27._11149.JPK.Deklaracja.PozycjeSzczegolowe.class.getMethod(zwrocpolejpk(r.getPolevat()),BigInteger.class);
                    method.invoke(pozycjeSzczegolowe, BigInteger.valueOf(Z.zUD(r.getVat())));
                }
            } catch (Exception e){
                E.e(e);
            }
        }
        for (DeklaracjaVatSchemaWierszSum p : schemawierszsumarycznylista) {
            try {
                if (p.getNetto1vat2czek3tekst4()==3) {
                    Method method = pl.gov.crd.wzor._2021._12._27._11149.JPK.Deklaracja.PozycjeSzczegolowe.class.getMethod(zwrocpolejpk(p.getPolenetto()),Byte.class);
                    if (p.getDeklaracjaVatWierszSumaryczny().isCzekpole()) {
                        method.invoke(pozycjeSzczegolowe, Byte.valueOf("1"));
                    }
                } else if (p.getNetto1vat2czek3tekst4()==4) {
                    Method method = pl.gov.crd.wzor._2021._12._27._11149.JPK.Deklaracja.PozycjeSzczegolowe.class.getMethod(zwrocpolejpk(p.getPolenetto()),String.class);
                    method.invoke(pozycjeSzczegolowe, p.getDeklaracjaVatWierszSumaryczny().getStringpole());
                } else {
                    Method method = pl.gov.crd.wzor._2021._12._27._11149.JPK.Deklaracja.PozycjeSzczegolowe.class.getMethod(zwrocpolejpk(p.getPolenetto()),BigInteger.class);
                    method.invoke(pozycjeSzczegolowe, BigInteger.valueOf(p.getDeklaracjaVatWierszSumaryczny().getSumanetto()));
                    method = pl.gov.crd.wzor._2021._12._27._11149.JPK.Deklaracja.PozycjeSzczegolowe.class.getMethod(zwrocpolejpk(p.getPolevat()),BigInteger.class);
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
            if (true) {
                String[] wiadomosc = SzachMatJPK.pobierzupo(selected.getReferenceNumber(), selected);
                selected.setDataupo(new Date());
                uPODAO.edit(selected);
                if (selected.getDeklaracja()!=null&&(selected.getCode()<299||selected.getCode()>399)) {
                        DeklSuper deklaracja = selected.getDeklaracja();
                        deklaracja.setStatus(String.valueOf(selected.getCode()));
                        deklaracja.setOpis(selected.getDescription()+" "+selected.getDetails());
                        deklaracja.setUpo(selected.getUpoString());
                        deklaracja.setDataupo(selected.getDataupo());
                        deklaracja.setIdentyfikator(selected.getReferenceNumber());
                        deklaracja.setDatazlozenia(selected.getDatajpk());
                        if (selected.getJpk() instanceof pl.gov.crd.wzor._2020._05._08._9393.JPK) {
                            deklaracja.setDeklaracja(((pl.gov.crd.wzor._2020._05._08._9393.JPK)selected.getJpk()).getDeklaracja().toString());
                        } else if (selected.getJpk() instanceof pl.gov.crd.wzor._2020._05._08._9394.JPK) {
                            deklaracja.setDeklaracja(((pl.gov.crd.wzor._2020._05._08._9394.JPK)selected.getJpk()).getDeklaracja().toString());
                        } else if (selected.getJpk() instanceof pl.gov.crd.wzor._2021._12._27._11148.JPK) {
                            deklaracja.setDeklaracja(((pl.gov.crd.wzor._2021._12._27._11148.JPK)selected.getJpk()).getDeklaracja().toString());
                        } else if (selected.getJpk() instanceof pl.gov.crd.wzor._2021._12._27._11149.JPK) {
                            deklaracja.setDeklaracja(((pl.gov.crd.wzor._2021._12._27._11149.JPK)selected.getJpk()).getDeklaracja().toString());
                        }
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
                JPKSuper jpk = selected.getJpkblob()!=null?selected.getJpkblob().getJpk():null;
                if (jpk==null) {
                    Msg.msg("e","Z jakiegoś powodu nie zachował się xml z plikiem jpk");
                    return "brak";
                }
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
                uPODAO.remove(selected);
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
        if (item.getJpk()!=null) {
            if (item.getJpk() instanceof pl.gov.crd.wzor._2021._12._27._11148.JPK) {
                PdfUPO.drukujJPK2022M(item, wpisView);
            } else if (item.getJpk() instanceof pl.gov.crd.wzor._2021._12._27._11149.JPK) {
                PdfUPO.drukujJPK2022K(item, wpisView);
            } else if (item.getJpk() instanceof pl.gov.crd.wzor._2020._05._08._9393.JPK) {
                PdfUPO.drukujJPK2020M(item, wpisView);
            } else if (item.getJpk() instanceof pl.gov.crd.wzor._2020._05._08._9394.JPK) {
                PdfUPO.drukujJPK2020K(item, wpisView);
            } else if (item.getJpk() instanceof jpk201801.JPK) {
                PdfUPO.drukuj_JPK3(item, wpisView);
            } else {
                PdfUPO.drukuj_JPK2(item, wpisView);
            }
        } else {
            Msg.msg("e", "Wystąpił błąd i nei zachowano wysłanego JPK. Nie można wydrukować");
        }
    }
    
    
    public void generujXMLPodgladDedra(List<KlientJPK> kliencijpk, List<EVatwpisDedra> wiersze, Podatnik podatnik, boolean nowa0korekta1, int werjsajpkrecznie1pierowtna2korekta) {
        List<EVatwpisSuper> lista = new ArrayList<>(wiersze);
        JPKSuper jpk = genJPK(kliencijpk, lista, podatnik, nowa0korekta1, werjsajpkrecznie1pierowtna2korekta);
        try {
            if (jpk instanceof pl.gov.crd.wzor._2020._05._08._9393.JPK) {
                PdfUPO.drukujJPK2020M(jpk, wpisView, podatnik);
            } else if (jpk instanceof pl.gov.crd.wzor._2020._05._08._9394.JPK) {
                PdfUPO.drukujJPK2020K(jpk, wpisView, podatnik);
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
    
    public void generujXMLPodglad(List<KlientJPK> kliencijpk, List<EVatwpisSuper> wiersze, Podatnik podatnik, boolean nowa0korekta1, int werjsajpkrecznie1pierowtna2korekta) {
        JPKSuper jpk = genJPK(kliencijpk, wiersze, podatnik, nowa0korekta1, werjsajpkrecznie1pierowtna2korekta);
        try {
             if (jpk instanceof pl.gov.crd.wzor._2021._12._27._11148.JPK) {
                PdfUPO.drukujJPK2022M(jpk, wpisView, podatnik);
            } else if (jpk instanceof pl.gov.crd.wzor._2021._12._27._11149.JPK) {
                PdfUPO.drukujJPK2022K(jpk, wpisView, podatnik);
            } else if (jpk instanceof pl.gov.crd.wzor._2020._05._08._9393.JPK) {
                PdfUPO.drukujJPK2020M(jpk, wpisView, podatnik);
            } else if (jpk instanceof pl.gov.crd.wzor._2020._05._08._9394.JPK) {
                PdfUPO.drukujJPK2020K(jpk, wpisView, podatnik);
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
    
    
    public String[] generujXML(List<KlientJPK> kliencijpk, List<EVatwpisSuper> wiersze, Podatnik podatnik, boolean nowa0korekta1, int werjsajpkrecznie1pierowtna2korekta) {
        String[] zwrot = new String[3];
        JPKSuper jpk = genJPK(kliencijpk, wiersze, podatnik, nowa0korekta1, werjsajpkrecznie1pierowtna2korekta);
        String sciezka = null;
        if (jpk!=null) {
            try {
                sciezka = marszajuldoplikuxml(podatnik, jpk);
                zwrot[0] = sciezka;
                zwrot[1] = "ok";
                if (jpk instanceof pl.gov.crd.wzor._2021._12._27._11148.JPK) {
                    pl.gov.crd.wzor._2021._12._27._11148.JPK jpk2 = (pl.gov.crd.wzor._2021._12._27._11148.JPK) jpk;
                    String mainfilename = "jpk"+podatnik.getNip()+"mcrok"+wpisView.getMiesiacWpisu()+wpisView.getRokWpisuSt()+".xml";
                        Object[] walidacja = XMLValid.walidujJPK2022View(mainfilename,0, jpk2.getNaglowek().getKodFormularza().getWersjaSchemy());
                        if (walidacja!=null && walidacja[0]==Boolean.TRUE) {
                            zwrot[0] = sciezka;
                            zwrot[1] = "ok";
                            Msg.msg("Walidacja JPK pomyślna");
                        } else if (walidacja!=null && walidacja[0]==Boolean.FALSE){
                            zwrot[0] = sciezka;
                            zwrot[1] = null;
                            Msg.msg("e", (String) walidacja[1]);
                        }
                } else if (jpk instanceof pl.gov.crd.wzor._2021._12._27._11149.JPK) {
                    pl.gov.crd.wzor._2021._12._27._11149.JPK jpk2 = (pl.gov.crd.wzor._2021._12._27._11149.JPK) jpk;
                    String mainfilename = "jpk"+podatnik.getNip()+"mcrok"+wpisView.getMiesiacWpisu()+wpisView.getRokWpisuSt()+".xml";
                        Object[] walidacja = XMLValid.walidujJPK2022View(mainfilename,1, jpk2.getNaglowek().getKodFormularza().getWersjaSchemy());
                        if (walidacja!=null && walidacja[0]==Boolean.TRUE) {
                            zwrot[0] = sciezka;
                            zwrot[1] = "ok";
                            Msg.msg("Walidacja JPK pomyślna");
                        } else if (walidacja!=null && walidacja[0]==Boolean.FALSE){
                            zwrot[0] = sciezka;
                            zwrot[1] = null;
                            Msg.msg("e", (String) walidacja[1]);
                        }
                } else if (jpk instanceof pl.gov.crd.wzor._2020._05._08._9393.JPK) {
                    pl.gov.crd.wzor._2020._05._08._9393.JPK jpk2 = (pl.gov.crd.wzor._2020._05._08._9393.JPK) jpk;
                    String mainfilename = "jpk"+podatnik.getNip()+"mcrok"+wpisView.getMiesiacWpisu()+wpisView.getRokWpisuSt()+".xml";
                        Object[] walidacja = XMLValid.walidujJPK2020View(mainfilename,0, jpk2.getNaglowek().getKodFormularza().getWersjaSchemy());
                        if (walidacja!=null && walidacja[0]==Boolean.TRUE) {
                            zwrot[0] = sciezka;
                            zwrot[1] = "ok";
                            Msg.msg("Walidacja JPK pomyślna");
                        } else if (walidacja!=null && walidacja[0]==Boolean.FALSE){
                            zwrot[0] = sciezka;
                            zwrot[1] = null;
                            Msg.msg("e", (String) walidacja[1]);
                        }
                } else if (jpk instanceof pl.gov.crd.wzor._2020._05._08._9394.JPK) {
                    pl.gov.crd.wzor._2020._05._08._9394.JPK jpk2 = (pl.gov.crd.wzor._2020._05._08._9394.JPK) jpk;
                    String mainfilename = "jpk"+podatnik.getNip()+"mcrok"+wpisView.getMiesiacWpisu()+wpisView.getRokWpisuSt()+".xml";
                        Object[] walidacja = XMLValid.walidujJPK2020View(mainfilename,1, jpk2.getNaglowek().getKodFormularza().getWersjaSchemy());
                        if (walidacja!=null && walidacja[0]==Boolean.TRUE) {
                            zwrot[0] = sciezka;
                            zwrot[1] = "ok";
                            Msg.msg("Walidacja JPK pomyślna");
                        } else if (walidacja!=null && walidacja[0]==Boolean.FALSE){
                            zwrot[0] = sciezka;
                            zwrot[1] = null;
                            Msg.msg("e", (String) walidacja[1]);
                        }
                }
                Msg.msg("Wygenerowano plik JPK");
            } catch(Exception e) {
                Msg.msg("e", "Wystąpił błąd, nie wygenerowano pliku JPK");
                E.e(e);
            }
        } else {
            zwrot[2] = "Nie udało się wygenerować pliku JPK";
        }
        return zwrot;
    }
    
    private String marszajuldoplikuxml(Podatnik podatnik, JPKSuper jpk) {
        String sciezka = null;
        try {
            if (jpk!=null) {
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
            } else {
                Msg.msg("e","Nie mozna zachowac danych jpk do pliku. Plik jpk pusty");
            }
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
//            Logger.getLogger(Jpk_VAT2NView.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                fileStream.close();
//            } catch (IOException ex) {
//                Logger.getLogger(Jpk_VAT2NView.class.getName()).log(Level.SEVERE, null, ex);
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
    
    private Object[] utworzWierszeJpkSprzedaz2020M(List<KlientJPK> kliencijpk, List wiersze, Map<Evewidencja, JPKvatwersjaEvewidencja> mapa) {
        Object[] zwrot = new Object[2];
        zwrot[0] = Collections.synchronizedList(new ArrayList<>());
        if (wiersze.size() >0) {
            Class c = wiersze.get(0).getClass();
            List<pl.gov.crd.wzor._2020._05._08._9393.JPK.Ewidencja.SprzedazWiersz> lista = Collections.synchronizedList(new ArrayList<>());
            pl.gov.crd.wzor._2020._05._08._9393.JPK.Ewidencja.SprzedazCtrl sprzedazCtrl = new pl.gov.crd.wzor._2020._05._08._9393.JPK.Ewidencja.SprzedazCtrl();
            sprzedazCtrl.setLiczbaWierszySprzedazy(BigInteger.ZERO);
            sprzedazCtrl.setPodatekNalezny(BigDecimal.ZERO);
            int lp = 1;
            if (c.getName().equals("entity.EVatwpis1")) {
                for (Object p : wiersze) {
                    EVatwpis1 wiersz = (EVatwpis1) p;
                    if (!wiersz.getEwidencja().getTypewidencji().equals("z") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                        lista.add(JPK_VAT2020M_Bean.dodajwierszsprzedazy(wiersz, BigInteger.valueOf(lp++),sprzedazCtrl, mapa.get(wiersz.getEwidencja())));
                    }
                }
            } else if (c.getName().equals("entityfk.EVatwpisDedra")) {
                for (Object p : wiersze) {
                    EVatwpisDedra wiersz = (EVatwpisDedra) p;
                    if (!wiersz.getEwidencja().getTypewidencji().equals("z") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                        lista.add(JPK_VAT2020M_Bean.dodajwierszsprzedazy(wiersz, BigInteger.valueOf(lp++),sprzedazCtrl, mapa.get(wiersz.getEwidencja())));
                    }
                }
            } else {
                for (Object p : wiersze) {
                    EVatwpisFK wiersz = (EVatwpisFK) p;
                    if (!wiersz.getEwidencja().getTypewidencji().equals("z") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                        lista.add(JPK_VAT2020M_Bean.dodajwierszsprzedazyFK(wiersz, BigInteger.valueOf(lp++),sprzedazCtrl, mapa.get(wiersz.getEwidencja())));
                    }
                }
            }
            if (kliencijpk!=null&& !kliencijpk.isEmpty()) {
                for (KlientJPK p : kliencijpk) {
                    if (p.getNetto()==6164.15) {
                        System.out.println("");
                    }
                     if (!p.getEwidencjaVAT().get(0).getEwidencja().getTypewidencji().equals("z") && (Z.z(p.getNetto()) != 0.0 || Z.z(p.getVat()) != 0.0)) {
                        lista.add(JPK_VAT2020M_Bean.dodajwierszsprzedazy(p, BigInteger.valueOf(lp++),sprzedazCtrl, mapa.get(p.getEwidencjaVAT().get(0).getEwidencja())));
                     } else {
                         System.out.println("");
                     }
                }
            }
            zwrot[0] = lista;
            zwrot[1] = sprzedazCtrl;
        } else {
            pl.gov.crd.wzor._2020._05._08._9393.JPK.Ewidencja.SprzedazCtrl sprzedazCtrl = new pl.gov.crd.wzor._2020._05._08._9393.JPK.Ewidencja.SprzedazCtrl();
            sprzedazCtrl.setLiczbaWierszySprzedazy(BigInteger.ZERO);
            sprzedazCtrl.setPodatekNalezny(BigDecimal.ZERO);
            zwrot[1] = sprzedazCtrl;
        }
        return zwrot;
    }
    
    private Object[] utworzWierszeJpkSprzedaz2020M2(List<KlientJPK> kliencijpk, List wiersze, Map<Evewidencja, JPKvatwersjaEvewidencja> mapa) {
        Object[] zwrot = new Object[2];
        zwrot[0] = Collections.synchronizedList(new ArrayList<>());
        int i = 0;
        int j = 0;
        if (wiersze.size() >0) {
            Class c = wiersze.get(0).getClass();
            List<pl.gov.crd.wzor._2021._12._27._11148.JPK.Ewidencja.SprzedazWiersz> lista = Collections.synchronizedList(new ArrayList<>());
            pl.gov.crd.wzor._2021._12._27._11148.JPK.Ewidencja.SprzedazCtrl sprzedazCtrl = new pl.gov.crd.wzor._2021._12._27._11148.JPK.Ewidencja.SprzedazCtrl();
            sprzedazCtrl.setLiczbaWierszySprzedazy(BigInteger.ZERO);
            sprzedazCtrl.setPodatekNalezny(BigDecimal.ZERO);
            int lp = 1;
            if (c.getName().equals("entity.EVatwpis1")) {
                for (Object p : wiersze) {
                    EVatwpis1 wiersz = (EVatwpis1) p;
                    if (!wiersz.getEwidencja().getTypewidencji().equals("z") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                        System.out.println(i+" "+"wiersz "+wiersz.getNrWlDk()+" "+wiersz.getDok().getDataWyst());
                        i++;
                        lista.add(JPK_VAT2020M2_Bean.dodajwierszsprzedazy(wiersz, BigInteger.valueOf(lp++),sprzedazCtrl, mapa.get(wiersz.getEwidencja())));
                    }
                }
            } else if (c.getName().equals("entityfk.EVatwpisDedra")) {
                for (Object p : wiersze) {
                    EVatwpisDedra wiersz = (EVatwpisDedra) p;
                    if (!wiersz.getEwidencja().getTypewidencji().equals("z") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                        lista.add(JPK_VAT2020M2_Bean.dodajwierszsprzedazy(wiersz, BigInteger.valueOf(lp++),sprzedazCtrl, mapa.get(wiersz.getEwidencja())));
                    }
                }
            } else {
                for (Object p : wiersze) {
                    EVatwpisFK wiersz = (EVatwpisFK) p;
                    if (!wiersz.getEwidencja().getTypewidencji().equals("z") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                        lista.add(JPK_VAT2020M2_Bean.dodajwierszsprzedazyFK(wiersz, BigInteger.valueOf(lp++),sprzedazCtrl, mapa.get(wiersz.getEwidencja())));
                    }
                }
            }
            if (kliencijpk!=null&& !kliencijpk.isEmpty()) {
                for (KlientJPK p : kliencijpk) {
                     if (!p.getEwidencjaVAT().get(0).getEwidencja().getTypewidencji().equals("z") && (Z.z(p.getNetto()) != 0.0 || Z.z(p.getVat()) != 0.0)) {
                         System.out.println(j+" "+"wiersz "+p.getSerial()+" "+p.getDataSprzedazy());
                         j++;
                        lista.add(JPK_VAT2020M2_Bean.dodajwierszsprzedazy(p, BigInteger.valueOf(lp++),sprzedazCtrl, mapa.get(p.getEwidencjaVAT().get(0).getEwidencja())));
                     } else {
                         System.out.println("");
                     }
                }
            }
            zwrot[0] = lista;
            zwrot[1] = sprzedazCtrl;
        } else {
            pl.gov.crd.wzor._2021._12._27._11148.JPK.Ewidencja.SprzedazCtrl sprzedazCtrl = new pl.gov.crd.wzor._2021._12._27._11148.JPK.Ewidencja.SprzedazCtrl();
            sprzedazCtrl.setLiczbaWierszySprzedazy(BigInteger.ZERO);
            sprzedazCtrl.setPodatekNalezny(BigDecimal.ZERO);
            zwrot[1] = sprzedazCtrl;
        }
        return zwrot;
    }
    
    private Object[] utworzWierszeJpkSprzedaz2020K(List<KlientJPK> kliencikjpk, List wiersze, Map<Evewidencja, JPKvatwersjaEvewidencja> mapa) {
        Object[] zwrot = new Object[2];
        zwrot[0] = Collections.synchronizedList(new ArrayList<>());
        if (wiersze.size() >0) {
            Class c = wiersze.get(0).getClass();
            List<pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.SprzedazWiersz> lista = Collections.synchronizedList(new ArrayList<>());
            pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.SprzedazCtrl sprzedazCtrl = new pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.SprzedazCtrl();
            sprzedazCtrl.setLiczbaWierszySprzedazy(BigInteger.ZERO);
            sprzedazCtrl.setPodatekNalezny(BigDecimal.ZERO);
            if (c.getName().equals("entity.EVatwpis1")) {
                int lp = 1;
                for (Object p : wiersze) {
                    EVatwpis1 wiersz = (EVatwpis1) p;
                    if (!wiersz.getEwidencja().getTypewidencji().equals("z") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                        lista.add(JPK_VAT2020K_Bean.dodajwierszsprzedazy(wiersz, BigInteger.valueOf(lp++),sprzedazCtrl, mapa.get(wiersz.getEwidencja())));
                    }
                }
            } else if (c.getName().equals("entityfk.EVatwpisDedra")) {
                int lp = 1;
                for (Object p : wiersze) {
                    EVatwpisDedra wiersz = (EVatwpisDedra) p;
                    if (!wiersz.getEwidencja().getTypewidencji().equals("z") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                        lista.add(JPK_VAT2020K_Bean.dodajwierszsprzedazy(wiersz, BigInteger.valueOf(lp++),sprzedazCtrl, mapa.get(wiersz.getEwidencja())));
                    }
                }
            } else {
                int lp = 1;
                for (Object p : wiersze) {
                    EVatwpisFK wiersz = (EVatwpisFK) p;
                    if (!wiersz.getEwidencja().getTypewidencji().equals("z") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                        lista.add(JPK_VAT2020K_Bean.dodajwierszsprzedazyFK(wiersz, BigInteger.valueOf(lp++),sprzedazCtrl, mapa.get(wiersz.getEwidencja())));
                    }
                }
            }
            zwrot[0] = lista;
            zwrot[1] = sprzedazCtrl;
        } else {
            pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.SprzedazCtrl sprzedazCtrl = new pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.SprzedazCtrl();
            sprzedazCtrl.setLiczbaWierszySprzedazy(BigInteger.ZERO);
            sprzedazCtrl.setPodatekNalezny(BigDecimal.ZERO);
            zwrot[1] = sprzedazCtrl;
        }
        return zwrot;
    }
    
    private Object[] utworzWierszeJpkSprzedaz2020K2(List<KlientJPK> kliencikjpk, List wiersze, Map<Evewidencja, JPKvatwersjaEvewidencja> mapa) {
        Object[] zwrot = new Object[2];
        zwrot[0] = Collections.synchronizedList(new ArrayList<>());
        if (wiersze.size() >0) {
            Class c = wiersze.get(0).getClass();
            List<pl.gov.crd.wzor._2021._12._27._11149.JPK.Ewidencja.SprzedazWiersz> lista = Collections.synchronizedList(new ArrayList<>());
            pl.gov.crd.wzor._2021._12._27._11149.JPK.Ewidencja.SprzedazCtrl sprzedazCtrl = new pl.gov.crd.wzor._2021._12._27._11149.JPK.Ewidencja.SprzedazCtrl();
            sprzedazCtrl.setLiczbaWierszySprzedazy(BigInteger.ZERO);
            sprzedazCtrl.setPodatekNalezny(BigDecimal.ZERO);
            if (c.getName().equals("entity.EVatwpis1")) {
                int lp = 1;
                for (Object p : wiersze) {
                    EVatwpis1 wiersz = (EVatwpis1) p;
                    if (!wiersz.getEwidencja().getTypewidencji().equals("z") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                        lista.add(JPK_VAT2020K2_Bean.dodajwierszsprzedazy(wiersz, BigInteger.valueOf(lp++),sprzedazCtrl, mapa.get(wiersz.getEwidencja())));
                    }
                }
            } else if (c.getName().equals("entityfk.EVatwpisDedra")) {
                int lp = 1;
                for (Object p : wiersze) {
                    EVatwpisDedra wiersz = (EVatwpisDedra) p;
                    if (!wiersz.getEwidencja().getTypewidencji().equals("z") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                        lista.add(JPK_VAT2020K2_Bean.dodajwierszsprzedazy(wiersz, BigInteger.valueOf(lp++),sprzedazCtrl, mapa.get(wiersz.getEwidencja())));
                    }
                }
            } else {
                int lp = 1;
                for (Object p : wiersze) {
                    EVatwpisFK wiersz = (EVatwpisFK) p;
                    if (!wiersz.getEwidencja().getTypewidencji().equals("z") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                        lista.add(JPK_VAT2020K2_Bean.dodajwierszsprzedazyFK(wiersz, BigInteger.valueOf(lp++),sprzedazCtrl, mapa.get(wiersz.getEwidencja())));
                    }
                }
            }
            zwrot[0] = lista;
            zwrot[1] = sprzedazCtrl;
        } else {
            pl.gov.crd.wzor._2021._12._27._11149.JPK.Ewidencja.SprzedazCtrl sprzedazCtrl = new pl.gov.crd.wzor._2021._12._27._11149.JPK.Ewidencja.SprzedazCtrl();
            sprzedazCtrl.setLiczbaWierszySprzedazy(BigInteger.ZERO);
            sprzedazCtrl.setPodatekNalezny(BigDecimal.ZERO);
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
    
    private Object[] utworzwierszjpkZakup2020M(List<KlientJPK> kliencijpk, List wiersze, Map<Evewidencja, JPKvatwersjaEvewidencja> mapa) {
        Object[] zwrot = new Object[2];
        zwrot[0] = Collections.synchronizedList(new ArrayList<>());
        if (wiersze.size() >0) {
            Class c = wiersze.get(0).getClass();
            List<pl.gov.crd.wzor._2020._05._08._9393.JPK.Ewidencja.ZakupWiersz> lista = Collections.synchronizedList(new ArrayList<>());
            pl.gov.crd.wzor._2020._05._08._9393.JPK.Ewidencja.ZakupCtrl zakupCtrl = new pl.gov.crd.wzor._2020._05._08._9393.JPK.Ewidencja.ZakupCtrl();
            zakupCtrl.setLiczbaWierszyZakupow(BigInteger.ZERO);
            zakupCtrl.setPodatekNaliczony(BigDecimal.ZERO);
            int lp = 1;
            if (c.getName().equals("entity.EVatwpis1")) {
                for (Object p : wiersze) {
                    EVatwpis1 wiersz = (EVatwpis1) p;
                    if (!wiersz.getEwidencja().getTypewidencji().equals("s") && !wiersz.getEwidencja().getTypewidencji().equals("sz") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                        lista.add(JPK_VAT2020M_Bean.dodajwierszzakupu(wiersz, BigInteger.valueOf(lp++),zakupCtrl, mapa.get(wiersz.getEwidencja())));
                    }
                }
            } else if (c.getName().equals("entityfk.EVatwpisDedra")) {
                for (Object p : wiersze) {
                    EVatwpisDedra wiersz = (EVatwpisDedra) p;
                    if (!wiersz.getEwidencja().getTypewidencji().equals("s") && !wiersz.getEwidencja().getTypewidencji().equals("sz") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                        lista.add(JPK_VAT2020M_Bean.dodajwierszzakupu(wiersz, BigInteger.valueOf(lp++),zakupCtrl, mapa.get(wiersz.getEwidencja())));
                    }
                }
            } else {
                for (Object p : wiersze) {
                    EVatwpisFK wiersz = (EVatwpisFK) p;
                    if (!wiersz.getEwidencja().getTypewidencji().equals("s") && !wiersz.getEwidencja().getTypewidencji().equals("sz") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                        lista.add(JPK_VAT2020M_Bean.dodajwierszzakupu(wiersz, BigInteger.valueOf(lp++),zakupCtrl, mapa.get(wiersz.getEwidencja())));
                    }
                }
            }
            if (kliencijpk!=null&& !kliencijpk.isEmpty()) {
                for (KlientJPK p : kliencijpk) {
                    if (p.getNetto()==6164.15) {
                        System.out.println("");
                    }
                     if (p.getEwidencjaVAT().get(0).getEwidencja().getTypewidencji().equals("z") && (Z.z(p.getNetto()) != 0.0 || Z.z(p.getVat()) != 0.0)) {
                        lista.add(JPK_VAT2020M_Bean.dodajwierszzakupu(p, BigInteger.valueOf(lp++),zakupCtrl, mapa.get(p.getEwidencjaVAT().get(0).getEwidencja())));
                     } else {
                         System.out.println("");
                     }
                }
            }
            zwrot[0] = lista;
            zwrot[1] = zakupCtrl;
        } else {
            pl.gov.crd.wzor._2020._05._08._9393.JPK.Ewidencja.ZakupCtrl zakupCtrl = new pl.gov.crd.wzor._2020._05._08._9393.JPK.Ewidencja.ZakupCtrl();
            zakupCtrl.setLiczbaWierszyZakupow(BigInteger.ZERO);
            zakupCtrl.setPodatekNaliczony(BigDecimal.ZERO);
            zwrot[1] = zakupCtrl;
        }
        return zwrot;
    }
    
    private Object[] utworzwierszjpkZakup2020M2(List<KlientJPK> kliencijpk, List wiersze, Map<Evewidencja, JPKvatwersjaEvewidencja> mapa) {
        Object[] zwrot = new Object[2];
        zwrot[0] = Collections.synchronizedList(new ArrayList<>());
        if (wiersze.size() >0) {
            Class c = wiersze.get(0).getClass();
            List<pl.gov.crd.wzor._2021._12._27._11148.JPK.Ewidencja.ZakupWiersz> lista = Collections.synchronizedList(new ArrayList<>());
            pl.gov.crd.wzor._2021._12._27._11148.JPK.Ewidencja.ZakupCtrl zakupCtrl = new pl.gov.crd.wzor._2021._12._27._11148.JPK.Ewidencja.ZakupCtrl();
            zakupCtrl.setLiczbaWierszyZakupow(BigInteger.ZERO);
            zakupCtrl.setPodatekNaliczony(BigDecimal.ZERO);
            int lp = 1;
            if (c.getName().equals("entity.EVatwpis1")) {
                for (Object p : wiersze) {
                    EVatwpis1 wiersz = (EVatwpis1) p;
                    if (!wiersz.getEwidencja().getTypewidencji().equals("s") && !wiersz.getEwidencja().getTypewidencji().equals("sz") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                        lista.add(JPK_VAT2020M2_Bean.dodajwierszzakupu(wiersz, BigInteger.valueOf(lp++),zakupCtrl, mapa.get(wiersz.getEwidencja())));
                    }
                }
            } else if (c.getName().equals("entityfk.EVatwpisDedra")) {
                for (Object p : wiersze) {
                    EVatwpisDedra wiersz = (EVatwpisDedra) p;
                    if (!wiersz.getEwidencja().getTypewidencji().equals("s") && !wiersz.getEwidencja().getTypewidencji().equals("sz") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                        lista.add(JPK_VAT2020M2_Bean.dodajwierszzakupu(wiersz, BigInteger.valueOf(lp++),zakupCtrl, mapa.get(wiersz.getEwidencja())));
                    }
                }
            } else {
                for (Object p : wiersze) {
                    EVatwpisFK wiersz = (EVatwpisFK) p;
                    if (!wiersz.getEwidencja().getTypewidencji().equals("s") && !wiersz.getEwidencja().getTypewidencji().equals("sz") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                        lista.add(JPK_VAT2020M2_Bean.dodajwierszzakupu(wiersz, BigInteger.valueOf(lp++),zakupCtrl, mapa.get(wiersz.getEwidencja())));
                    }
                }
            }
            if (kliencijpk!=null&& !kliencijpk.isEmpty()) {
                for (KlientJPK p : kliencijpk) {
                    if (p.getNetto()==6164.15) {
                        System.out.println("");
                    }
                     if (p.getEwidencjaVAT().get(0).getEwidencja().getTypewidencji().equals("z") && (Z.z(p.getNetto()) != 0.0 || Z.z(p.getVat()) != 0.0)) {
                        lista.add(JPK_VAT2020M2_Bean.dodajwierszzakupu(p, BigInteger.valueOf(lp++),zakupCtrl, mapa.get(p.getEwidencjaVAT().get(0).getEwidencja())));
                     } else {
                         System.out.println("");
                     }
                }
            }
            zwrot[0] = lista;
            zwrot[1] = zakupCtrl;
        } else {
            pl.gov.crd.wzor._2021._12._27._11148.JPK.Ewidencja.ZakupCtrl zakupCtrl = new pl.gov.crd.wzor._2021._12._27._11148.JPK.Ewidencja.ZakupCtrl();
            zakupCtrl.setLiczbaWierszyZakupow(BigInteger.ZERO);
            zakupCtrl.setPodatekNaliczony(BigDecimal.ZERO);
            zwrot[1] = zakupCtrl;
        }
        return zwrot;
    }
    
    private Object[] utworzwierszjpkZakup2020K(List<KlientJPK> kliencijpk,List wiersze, Map<Evewidencja, JPKvatwersjaEvewidencja> mapa) {
        Object[] zwrot = new Object[2];
        zwrot[0] = Collections.synchronizedList(new ArrayList<>());
        if (wiersze.size() >0) {
            Class c = wiersze.get(0).getClass();
            List<pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.ZakupWiersz> lista = Collections.synchronizedList(new ArrayList<>());
            pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.ZakupCtrl zakupCtrl = new pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.ZakupCtrl();
            zakupCtrl.setLiczbaWierszyZakupow(BigInteger.ZERO);
            zakupCtrl.setPodatekNaliczony(BigDecimal.ZERO);
            int lp = 1;
            if (c.getName().equals("entity.EVatwpis1")) {
                for (Object p : wiersze) {
                    EVatwpis1 wiersz = (EVatwpis1) p;
                    if (!wiersz.getEwidencja().getTypewidencji().equals("s") && !wiersz.getEwidencja().getTypewidencji().equals("sz") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                        lista.add(JPK_VAT2020K_Bean.dodajwierszzakupu(wiersz, BigInteger.valueOf(lp++),zakupCtrl, mapa.get(wiersz.getEwidencja())));
                    }
                }
            } else if (c.getName().equals("entityfk.EVatwpisDedra")) {
                for (Object p : wiersze) {
                    EVatwpisDedra wiersz = (EVatwpisDedra) p;
                    if (!wiersz.getEwidencja().getTypewidencji().equals("s") && !wiersz.getEwidencja().getTypewidencji().equals("sz") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                        lista.add(JPK_VAT2020K_Bean.dodajwierszzakupu(wiersz, BigInteger.valueOf(lp++),zakupCtrl, mapa.get(wiersz.getEwidencja())));
                    }
                }
            } else {
                for (Object p : wiersze) {
                    EVatwpisFK wiersz = (EVatwpisFK) p;
                    if (!wiersz.getEwidencja().getTypewidencji().equals("s") && !wiersz.getEwidencja().getTypewidencji().equals("sz") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                        lista.add(JPK_VAT2020K_Bean.dodajwierszzakupu(wiersz, BigInteger.valueOf(lp++),zakupCtrl, mapa.get(wiersz.getEwidencja())));
                    }
                }
            }
            if (kliencijpk!=null&& !kliencijpk.isEmpty()) {
                for (KlientJPK p : kliencijpk) {
                    if (p.getNetto()==6164.15) {
                        System.out.println("");
                    }
                     if (p.getEwidencjaVAT().get(0).getEwidencja().getTypewidencji().equals("z") && (Z.z(p.getNetto()) != 0.0 || Z.z(p.getVat()) != 0.0)) {
                        lista.add(JPK_VAT2020K_Bean.dodajwierszzakupu(p, BigInteger.valueOf(lp++),zakupCtrl, mapa.get(p.getEwidencjaVAT().get(0).getEwidencja())));
                     } else {
                         System.out.println("");
                     }
                }
            }
            zwrot[0] = lista;
            zwrot[1] = zakupCtrl;
        } else {
            pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.ZakupCtrl zakupCtrl = new pl.gov.crd.wzor._2020._05._08._9394.JPK.Ewidencja.ZakupCtrl();
            zakupCtrl.setLiczbaWierszyZakupow(BigInteger.ZERO);
            zakupCtrl.setPodatekNaliczony(BigDecimal.ZERO);
            zwrot[1] = zakupCtrl;
        }
        return zwrot;
    }
    
    private Object[] utworzwierszjpkZakup2020K2(List<KlientJPK> kliencijpk,List wiersze, Map<Evewidencja, JPKvatwersjaEvewidencja> mapa) {
        Object[] zwrot = new Object[2];
        zwrot[0] = Collections.synchronizedList(new ArrayList<>());
        if (wiersze.size() >0) {
            Class c = wiersze.get(0).getClass();
            List<pl.gov.crd.wzor._2021._12._27._11149.JPK.Ewidencja.ZakupWiersz> lista = Collections.synchronizedList(new ArrayList<>());
            pl.gov.crd.wzor._2021._12._27._11149.JPK.Ewidencja.ZakupCtrl zakupCtrl = new pl.gov.crd.wzor._2021._12._27._11149.JPK.Ewidencja.ZakupCtrl();
            zakupCtrl.setLiczbaWierszyZakupow(BigInteger.ZERO);
            zakupCtrl.setPodatekNaliczony(BigDecimal.ZERO);
            int lp = 1;
            if (c.getName().equals("entity.EVatwpis1")) {
                for (Object p : wiersze) {
                    EVatwpis1 wiersz = (EVatwpis1) p;
                    if (!wiersz.getEwidencja().getTypewidencji().equals("s") && !wiersz.getEwidencja().getTypewidencji().equals("sz") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                        lista.add(JPK_VAT2020K2_Bean.dodajwierszzakupu(wiersz, BigInteger.valueOf(lp++),zakupCtrl, mapa.get(wiersz.getEwidencja())));
                    }
                }
            } else if (c.getName().equals("entityfk.EVatwpisDedra")) {
                for (Object p : wiersze) {
                    EVatwpisDedra wiersz = (EVatwpisDedra) p;
                    if (!wiersz.getEwidencja().getTypewidencji().equals("s") && !wiersz.getEwidencja().getTypewidencji().equals("sz") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                        lista.add(JPK_VAT2020K2_Bean.dodajwierszzakupu(wiersz, BigInteger.valueOf(lp++),zakupCtrl, mapa.get(wiersz.getEwidencja())));
                    }
                }
            } else {
                for (Object p : wiersze) {
                    EVatwpisFK wiersz = (EVatwpisFK) p;
                    if (!wiersz.getEwidencja().getTypewidencji().equals("s") && !wiersz.getEwidencja().getTypewidencji().equals("sz") && (Z.z(wiersz.getNetto()) != 0.0 || Z.z(wiersz.getVat()) != 0.0)) {
                        lista.add(JPK_VAT2020K2_Bean.dodajwierszzakupu(wiersz, BigInteger.valueOf(lp++),zakupCtrl, mapa.get(wiersz.getEwidencja())));
                    }
                }
            }
            if (kliencijpk!=null&& !kliencijpk.isEmpty()) {
                for (KlientJPK p : kliencijpk) {
                    if (p.getNetto()==6164.15) {
                        System.out.println("");
                    }
                     if (p.getEwidencjaVAT().get(0).getEwidencja().getTypewidencji().equals("z") && (Z.z(p.getNetto()) != 0.0 || Z.z(p.getVat()) != 0.0)) {
                        lista.add(JPK_VAT2020K2_Bean.dodajwierszzakupu(p, BigInteger.valueOf(lp++),zakupCtrl, mapa.get(p.getEwidencjaVAT().get(0).getEwidencja())));
                     } else {
                         System.out.println("");
                     }
                }
            }
            zwrot[0] = lista;
            zwrot[1] = zakupCtrl;
        } else {
            pl.gov.crd.wzor._2021._12._27._11149.JPK.Ewidencja.ZakupCtrl zakupCtrl = new pl.gov.crd.wzor._2021._12._27._11149.JPK.Ewidencja.ZakupCtrl();
            zakupCtrl.setLiczbaWierszyZakupow(BigInteger.ZERO);
            zakupCtrl.setPodatekNaliczony(BigDecimal.ZERO);
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
                uPODAO.create(upo);
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

    public boolean isOdnalezionodeklaracje() {
        return odnalezionodeklaracje;
    }

    public void setOdnalezionodeklaracje(boolean odnalezionodeklaracje) {
        this.odnalezionodeklaracje = odnalezionodeklaracje;
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

    public boolean isJpkfk() {
        return jpkfk;
    }

    public void setJpkfk(boolean jpkfk) {
        this.jpkfk = jpkfk;
    }

    public boolean isJpkfkkorekta() {
        return jpkfkkorekta;
    }

    public void setJpkfkkorekta(boolean jpkfkkorekta) {
        this.jpkfkkorekta = jpkfkkorekta;
    }

    public boolean isJpkpkpir() {
        return jpkpkpir;
    }

    public void setJpkpkpir(boolean jpkpkpir) {
        this.jpkpkpir = jpkpkpir;
    }

    public boolean isJpkpkpirkorekta() {
        return jpkpkpirkorekta;
    }

    public Deklaracjevat getDeklaracjadlajpk() {
        return deklaracjadlajpk;
    }

    public void setDeklaracjadlajpk(Deklaracjevat deklaracjadlajpk) {
        this.deklaracjadlajpk = deklaracjadlajpk;
    }

    public void setJpkpkpirkorekta(boolean jpkpkpirkorekta) {
        this.jpkpkpirkorekta = jpkpkpirkorekta;
    }

    
    private int pobierznumerkorekty() {
        int numer = 1;
        for (UPO p : lista) {
            if (p.getJpk()!=null) {
                try {
                    if (p.getJpk().getClass().getName().equals("jpk201701.JPK")) {
                        numer = 1;
                    } else if (p.getJpk().getClass().getName().equals("jpk201801.JPK")) {
                        int celzlozenia = ((jpk201801.JPK)p.getJpk()).getNaglowek().getCelZlozenia();
                        if (p.getRok().equals(wpisView.getRokWpisuSt()) && p.getMiesiac().equals(wpisView.getMiesiacWpisu()) && celzlozenia > numer && p.getCode()==200) {
                            numer = celzlozenia;
                        }
                    } else if (p.getJpk().getClass().getName().equals("pl.gov.crd.wzor._2020._05._08._9393.JPK")) {
                        int celzlozenia = (int) ((pl.gov.crd.wzor._2020._05._08._9393.JPK)p.getJpk()).getNaglowek().getCelZlozenia().getValue();
                        if (p.getRok().equals(wpisView.getRokWpisuSt()) && p.getMiesiac().equals(wpisView.getMiesiacWpisu()) && celzlozenia == numer && (p.getCode()==200||p.getCode()>300)) {
                            numer = 1;
                        }
                    } else if (p.getJpk().getClass().getName().equals("pl.gov.crd.wzor._2020._05._08._9394.JPK")) {
                        int celzlozenia = (int) ((pl.gov.crd.wzor._2020._05._08._9394.JPK)p.getJpk()).getNaglowek().getCelZlozenia().getValue();
                        if (p.getRok().equals(wpisView.getRokWpisuSt()) && p.getMiesiac().equals(wpisView.getMiesiacWpisu()) && celzlozenia == numer && (p.getCode()==200||p.getCode()>300)) {
                            numer = 1;
                        }
                    } else if (p.getJpk().getClass().getName().equals("pl.gov.crd.wzor._2021._12._27._11148.JPK")) {
                        if ((pl.gov.crd.wzor._2021._12._27._11148.JPK)p.getJpk()!=null) {
                            int celzlozenia = (int) ((pl.gov.crd.wzor._2021._12._27._11148.JPK)p.getJpk()).getNaglowek().getCelZlozenia().getValue();
                            if (p.getRok().equals(wpisView.getRokWpisuSt()) && p.getMiesiac().equals(wpisView.getMiesiacWpisu()) && celzlozenia == numer && (p.getCode()==200||p.getCode()>300)) {
                                numer = 2;
                            }
                        }
                    } else if (p.getJpk().getClass().getName().equals("pl.gov.crd.wzor._2021._12._27._11149.JPK")) {
                        if ((pl.gov.crd.wzor._2021._12._27._11149.JPK)p.getJpk()!=null) {
                            int celzlozenia = (int) ((pl.gov.crd.wzor._2021._12._27._11149.JPK)p.getJpk()).getNaglowek().getCelZlozenia().getValue();
                            if (p.getRok().equals(wpisView.getRokWpisuSt()) && p.getMiesiac().equals(wpisView.getMiesiacWpisu()) && celzlozenia == numer && (p.getCode()==200||p.getCode()>300)) {
                                numer = 2;
                            }
                        }
                    }
                } catch (Exception e){}
                
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

    private void duplikujjpkwnt(List<KlientJPK> kliencijpk) {
        List<KlientJPK> wierszedodatkowe = Collections.synchronizedList(new ArrayList<>());
        for (KlientJPK ewid : kliencijpk) {
            if (ewid.isWnt()) {
                wierszedodatkowe.add(duplikujduplikujjpk(ewid,ewidencjazakupu));
            }
        }
        kliencijpk.addAll(wierszedodatkowe);
    }

    public static KlientJPK duplikujduplikujjpk(KlientJPK wiersz, Evewidencja ewidencjazak) {
        KlientJPK duplikat = null;
        duplikat = new KlientJPK(wiersz);
        //wpisuje pola zakupu
        EVatwpisKJPK get = wiersz.getEwidencjaVAT().get(0);
        EVatwpisKJPK duplit = new EVatwpisKJPK(get);
        duplit.setEwidencja(ewidencjazak);
        duplit.setNazwaewidencji(ewidencjazak);
        duplikat.getEwidencjaVAT().add(duplit);
        return duplikat;
    }
    
    
    
}
