/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansPodpis.Xad;
import dao.DeklaracjavatUEDAO;
import dao.DokDAO;
import dao.KlienciDAO;
import dao.PodatnikDAO;
import daoFK.DokDAOfk;
import daoFK.VatuepodatnikDAO;
import daoFK.ViesDAO;
import data.Data;
import deklaracje.vatue.m4.VATUEM4Bean;
import deklaracje.vatuek.m4.VATUEKM4Bean;
import embeddable.Kwartaly;
import embeddable.Parametr;
import embeddable.TKodUS;
import entity.DeklaracjavatUE;
import entity.Dok;
import entity.DokSuper;
import entity.Klienci;
import entity.Podatnik;
import entity.VatUe;
import entityfk.Dokfk;
import error.E;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;import pdf.PdfVATUEdekl;
import pdf.PdfVatUE;
import pdffk.PdfVIES;
import vies.VIESCheckBean;
import vies.Vies;
import waluty.Z;
import xml.XMLValid;

/**
 *
 * @author Osito
 */
@ManagedBean(name = "vatUeFKView")
@ViewScoped
public class VatUeFKView implements Serializable {

    //lista gdzie beda podsumowane wartosci
    private List<VatUe> klienciWDTWNT;
    private List<VatUe> listawybranych;
    private List<DeklaracjavatUE> deklaracjeUE;
    private List<DeklaracjavatUE> deklaracjeUE_biezace;
    private List<DokSuper> listadokumentowUE;
    private List<Dok> listaDok;
    private List<Dokfk> listaDokfk;
    @Inject
    private DeklaracjavatUE deklUEselected;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private DokDAOfk dokDAOfk;
    @Inject
    private DokDAO dokDAO;
    @Inject
    private DeklaracjavatUEDAO deklaracjavatUEDAO;
    @Inject
    private VatuepodatnikDAO vatuepodatnikDAO;
    private double sumawybranych;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private ViesDAO viesDAO;
    @Inject
    private KlienciDAO klienciDAO;
    @Inject
    private TKodUS tKodUS;
    private String opisvatuepkpir;
    boolean deklaracja0korekta1;

    public VatUeFKView() {
        klienciWDTWNT = Collections.synchronizedList(new ArrayList<>());
    }

    @PostConstruct
    public void init() { //E.m(this);
        listaDok = Collections.synchronizedList(new ArrayList<>());
        listaDokfk = Collections.synchronizedList(new ArrayList<>());
        //List<Dokfk> dokvatmc = Collections.synchronizedList(new ArrayList<>());
        Integer rok = wpisView.getRokWpisu();
        String m = wpisView.getMiesiacWpisu();
        try {
            String vatUEokres = ParametrView.zwrocParametr(wpisView.getPodatnikObiekt().getParamVatUE(), rok, m);
            if (vatUEokres.equals("miesięczne")) {
                if (wpisView.isKsiegirachunkowe()) {
                    listadokumentowUE = dokDAOfk.findDokfkPodatnikRokMcVAT(wpisView);
                } else {
                    listadokumentowUE = dokDAO.zwrocBiezacegoKlientaRokMCVAT(wpisView);
                }
                opisvatuepkpir = wpisView.getPodatnikWpisu()+" Miesięczne zestawienie dokumentów do deklaracji VAT-UE na koniec "+ rok+"/"+m;
            } else {
                if (wpisView.isKsiegirachunkowe()) {
                    listadokumentowUE = dokDAOfk.findDokfkPodatnikRokMc(wpisView);
                } else {
                    listadokumentowUE = dokDAO.zwrocBiezacegoKlientaRokKW(wpisView);
                }
                opisvatuepkpir = wpisView.getPodatnikWpisu()+" Kwartalne zestawienie dokumentów do deklaracji VAT-UE na koniec "+ rok+"/"+m;
            }            
        } catch (Exception e) { 
            E.e(e); 
        }
        //jest miesiecznie wiec nie ma co wybierac
        if (listadokumentowUE != null) {
            //a teraz podsumuj klientów
            klienciWDTWNT.addAll(kontrahenciUE(listadokumentowUE));
            double sumanettovatue = 0.0;
            double sumanettovatuewaluta = 0.0;
            for (DokSuper p : listadokumentowUE) {
                String typdokumentu = null;
                if (p instanceof Dok) {
                    typdokumentu = ((Dok) p).getRodzajedok().getSkrot();
                } else {
                    typdokumentu = ((Dokfk) p).getRodzajedok().getSkrot();
                }
                for (VatUe s : klienciWDTWNT) {
                    if (p.getKontr()!= null && p.getKontr().getNip().equals(s.getKontrahent().getNip()) && typdokumentu.equals(s.getTransakcja())) {
                            double[] t = p.pobierzwartosci();
                            double netto = t[0];
                            double nettowaluta = t[1];
                            s.setNetto(netto + s.getNetto());
                            s.setNettowaluta(nettowaluta + s.getNettowaluta());
                            s.setLiczbadok(s.getLiczbadok() + 1);
                            if (p.getClass().getSimpleName().equals("Dokfk")) {
                                s.getZawierafk().add((Dokfk)p);
                                Dokfk dodod = ((Dokfk)p);
                                dodod.setVatUe(s);
                                listaDokfk.add(dodod);
                            } else {
                                s.getZawiera().add((Dok) p);
                                Dok dodod = ((Dok)p);
                                dodod.setVatUe(s);
                                listaDok.add(dodod);
                            }
                            s.setNazwawaluty(p.getWalutadokumentu());
                            sumanettovatue += netto;
                            sumanettovatuewaluta += nettowaluta;
                            break;
                        }
                }
            }
//            if (klienciWDTWNT.size() > 0) {
//                VatUe rzadpodsumowanie = new VatUe("podsum.", null, Z.z(sumanettovatue), Z.z(sumanettovatuewaluta));
//                klienciWDTWNT.add(rzadpodsumowanie);
//                zachowajwbazie(String.valueOf(rok), wpisView.getMiesiacWpisu(), wpisView.getPodatnikWpisu());
//            }
        }
        try {
            pobierzdeklaracjeUE();
            DeklaracjavatUE d = deklaracjavatUEDAO.findbyPodatnikRokMc(wpisView);
            if (d != null) {
                deklaracja0korekta1 = true;
            }
        } catch (Exception e) { 
            E.e(e); 
        }
    }
    
    public void init2() {
        try {
            pobierzdeklaracjeUE();
            DeklaracjavatUE d = deklaracjavatUEDAO.findbyPodatnikRokMc(wpisView);
            if (d != null) {
                deklaracja0korekta1 = true;
            } else {
                deklaracja0korekta1 = false;
            }
        } catch (Exception e) { 
            E.e(e); 
        }
    }
    
    public void init3() {
        try {
            DeklaracjavatUE d = deklaracjavatUEDAO.findbyPodatnikRokMc(wpisView);
            if (d != null) {
                deklaracja0korekta1 = true;
            } else {
                deklaracja0korekta1 = false;
            }
            for (DeklaracjavatUE dek : deklaracjeUE) {
                if (dek.getRok().equals(wpisView.getRokWpisuSt()) && dek.getMiesiac().equals(wpisView.getMiesiacWpisu())) {
                    deklaracja0korekta1 = true;
                }
            }
        } catch (Exception e) { 
            E.e(e); 
        }
    }
    
    
    

//    private void zachowajwbazie(String rok, String symbolokresu, String klient) {
//        if (klienciWDTWNT != null) {
//            Vatuepodatnik vatuepodatnik = new Vatuepodatnik();
//            VatuepodatnikPK vatuepodatnikPK = new VatuepodatnikPK();
//            vatuepodatnikPK.setRok(rok);
//            vatuepodatnikPK.setSymbolokresu(symbolokresu);
//            vatuepodatnikPK.setKlient(klient);
//            vatuepodatnik.setVatuepodatnikPK(vatuepodatnikPK);
//            vatuepodatnik.setKlienciwdtwnt(klienciWDTWNT);
//            vatuepodatnik.setMc0kw1(Boolean.TRUE);
//            vatuepodatnik.setRozliczone(Boolean.FALSE);
//            //bo czasami nie edytowalo nie wiem dlaczego
//            try {
//                vatuepodatnikDAO.destroy(vatuepodatnik);
//            } catch (Exception e) { E.e(e); };
//            try {
//                vatuepodatnikDAO.dodaj(vatuepodatnik);
//                Msg.msg("i", "Zachowano dane do VAT-EU");
//            } catch (Exception e) { E.e(e); 
//                Msg.msg("e", "Błąd podczas zachowywania danych do VAT-UE");
//            }
//        }
//    }
    
    private Set<VatUe> kontrahenciUE(List<DokSuper> listadokumentow) {
        Set<VatUe> klienty = new HashSet<>();
        for (DokSuper p : listadokumentow) {
            if (warunekkontrahenci(p)) {
                //wyszukujemy dokumenty WNT i WDT dodajemu do sumy
                if (p.getKontr()!=null) {
                    VatUe veu = new VatUe(p.getRodzajedok().getSkrot(), p.getKontr(), 0.0, 0);
                    veu.setZawierafk(new ArrayList<>());
                    klienty.add(veu);
                    uzupelnijdanekontrahenta(p.getKontr());
                }
            }
        }
        return klienty;
    }
    
    private boolean warunekkontrahenci(DokSuper p) {
//        Dok dok = (Dok) p;
//        if (dok.getNrWlDk().equals("6/2017/VAŠE")) {
//            System.out.println("dok "+p.toString());
//        }
        String typdokumentu = null;
        if (p instanceof Dok) {
            typdokumentu = ((Dok) p).getRodzajedok().getSkrot();
        } else {
            typdokumentu = ((Dokfk) p).getRodzajedok().getSkrot();
        }
        boolean zwrot = false;
        if (Data.czyjestpo("2016-11-30", wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu())) {
            zwrot = typdokumentu.equals("WNT") || typdokumentu.equals("WDT")  || typdokumentu.equals("UPTK100");
        } else {
            zwrot = typdokumentu.equals("WNT") || typdokumentu.equals("WDT")  || typdokumentu.equals("UPTK");
        }
        return zwrot;
    }
    
   
//    private boolean dobrymiesiac(Dokfk p) {
//        boolean dobry = false;
//        if (p.getEwidencjaVAT() != null && !p.getEwidencjaVAT().isEmpty()) {
//            String mc = null;
//            for (EVatwpisFK r : p.getEwidencjaVAT()) {
//                mc = r.getMcEw();
//            }
//            if (mc.equals(wpisView.getMiesiacWpisu())) {
//                dobry = true;
//                System.out.println("dobry mc "+p.getDokfkSN());
//            }
//        }
//        return dobry;
//    }
    public void podsumuj() {
        sumawybranych = 0.0;
        for (VatUe p : listawybranych) {
            sumawybranych += p.getNetto();
        }
    }
    
    private String sprawdzjakiokresvat() {
        Integer rok = wpisView.getRokWpisu();
        Integer mc = Integer.parseInt(wpisView.getMiesiacWpisu());
        List<Parametr> parametry = wpisView.getPodatnikObiekt().getVatokres();
        return ParametrView.zwrocParametr(parametry, rok, mc);
    }
    
   
    public void pobierzdeklaracjeUE()  {
       deklaracjeUE_biezace = Collections.synchronizedList(new ArrayList<>());
       deklaracjeUE = deklaracjavatUEDAO.findbyPodatnikRok(wpisView);
       if (deklaracjeUE == null) {
           for (DeklaracjavatUE p : deklaracjeUE) {
               if (p.getStatus()==null || !p.getStatus().equals("200")) {
                   deklaracjeUE_biezace.add(p);
               }
           }
       }
    }

    public void drukujewidencjeUEfk() {
      try {
          if (listawybranych != null && !listawybranych.isEmpty()) {
              PdfVatUE.drukujewidencje(listawybranych, wpisView,"VAT-UE");
          } else {
              PdfVatUE.drukujewidencje(klienciWDTWNT, wpisView,"VAT-UE");
          }
      }  catch (Exception e) { E.e(e); 
          
      }
    } 
   
    public void drukujewidencjeUEfkTabela() {
      try {
          if (listawybranych != null && !listawybranych.isEmpty()) {
              PdfVatUE.drukujewidencjeTabela(listawybranych, wpisView,"VAT-UE");
          } else {
              PdfVatUE.drukujewidencjeTabela(klienciWDTWNT, wpisView,"VAT-UE");
          }
      }  catch (Exception e) { E.e(e); 
          
      }
    } 
    
    public void sprawdzVIES() {
       try {
        boolean zwrot = true;
        zwrot = VIESCheckBean.sprawdz(klienciWDTWNT, viesDAO, wpisView.getPodatnikObiekt(), wpisView.getUzer());
        if (zwrot) {
            Msg.msg("Sprawdzono VIES");
        } else {
            Msg.msg("e","Problem z NIP-em. Przerwano sprawdzanie VIES. Przejrzyj niesprawdzone pozycje");
        }
       } catch (Exception e) {
           E.e(e);
       }
    }
   
    public void drukujVIES() {
        try {
            if (klienciWDTWNT == null) {
                Msg.msg("e", "Lista VIES pusta - nie ma czego drukować");
            } else {
                List<Vies> lista = Collections.synchronizedList(new ArrayList<>());
                for (VatUe p : klienciWDTWNT) {
                    if (p.getVies() != null && p.getVies().getPodatnik() != null) {
                        lista.add(p.getVies());
                    }
                }
                PdfVIES.drukujVIES(lista, wpisView);
            }
        }  catch (Exception e) { 
            E.e(e); 
        }
    }
    
     private void uzupelnijdanekontrahenta(Klienci kontr) {
        if (kontr != null) {
            if (kontr.getKrajkod()==null && kontr.getNip() != null) {
                String nip = kontr.getNip();
                String prefix = nip.substring(0, 2);
                if (sprawdznip(nip)) {
                    kontr.setKrajkod(prefix.toUpperCase());
                    klienciDAO.edit(kontr);
                }
            }
//            if (kontr.getKrajkod()!=null && !sprawdznip(kontr.getNip())) {
//                kontr.setNip(kontr.getKrajkod()+kontr.getNip());
//                klienciDAO.edit(kontr);
//            }
        }
    }

    private boolean sprawdznip(String nip) {
        //jezeli false to dobrze
        String prefix = nip.substring(0, 2);
        Pattern p = Pattern.compile("[0-9]");
        boolean isnumber = p.matcher(prefix).find();
        return !isnumber;
    }

    public void drukuj(DeklaracjavatUE d) {
        try {
            if (d == null) {
                Msg.msg("e", "Nie wybrano deklaracji");
            } else {
                Podatnik podatnik = podatnikDAO.find(d.getPodatnik());
                PdfVATUEdekl.drukujVATUE(podatnikDAO, d, wpisView, podatnik);
                Msg.msg("Wydrukowano deklaracje");
            }
        } catch (Exception e) {
            Msg.msg("e", "Wystąpił błąd, nie wydrukowano ewidencji");
        }
    }
    
    public void usundekl(DeklaracjavatUE d) {
        try {
            deklaracjavatUEDAO.destroy(d);
            deklaracjeUE.remove(d);
            for (VatUe p : d.getPozycje()) {
                if (!p.getZawiera().isEmpty()) {
                    for (Dok dok:p.getZawiera()) {
                        dok.setVatUe(null);
                    }
                    dokDAO.editList(p.getZawiera());
                } else if (!p.getZawierafk().isEmpty()) {
                    for (Dokfk dok:p.getZawierafk()) {
                        dok.setVatUe(null);
                    }
                    dokDAOfk.editList(p.getZawierafk());
                }
            }
            Msg.dP();
            init2();
        } catch (Exception e) {
            Msg.dPe();
        }
    }
    
     public void tworzdeklaracjekorekta(List<VatUe> lista) {
        DeklaracjavatUE stara = deklaracjavatUEDAO.findbyPodatnikRokMc(wpisView);
        if (stara.getDatazlozenia() == null) {
            Msg.msg("e", "Pierwotna deklaracja nie została wysłana, nie można zrobić korekty");
        } else {
            List<VatUe> staralista = stara.getPozycje();
            for(Iterator<VatUe> it = staralista.iterator();it.hasNext();) {
                if (it.next().getTransakcja().equals("podsum.")) {
                    it.remove();
                }
            }
            boolean robickorekte = false;
            int nrkolejny = 0;
            if (staralista.size()!=lista.size()) {
                robickorekte = true;
            } else {
                for (VatUe s : staralista) {
                    for (VatUe t : lista) {
                        if (t.getKontrahent() != null && s.getKontrahent() != null && t.getKontrahent().equals(s.getKontrahent())) {
                            if (Z.z(t.getNetto()) != Z.z(s.getNetto())) {
                                t.setKorekta(true);
                                robickorekte = true;
                                nrkolejny = stara.getNrkolejny()+1;
                                break;
                            }
                        }
                    }
                }
            }
            if (robickorekte) {
                nrkolejny = stara.getNrkolejny()+1;
                boolean ok = robdeklaracjekorekta(lista, staralista, true, nrkolejny);
            } else {
                Msg.msg("w","Nie ma różnic w pozycjach deklaracji. Nie ma sensu robic korekty");
            }
        }
    }
    
    public void tworzdeklaracje(List<VatUe> lista) {
        boolean ok = robdeklaracje(lista, false, 0);
        init3();
    }
    
   
    
    public boolean robdeklaracje(List<VatUe> lista, boolean korekta, int nrkolejny) {
        boolean zwrot = false;
        try {
            String deklaracja = sporzadz(lista, korekta);
            Object[] walidacja = XMLValid.walidujCMLVATUE(deklaracja);
            if (walidacja!=null && walidacja[0]==Boolean.TRUE) {
                Object[] podpisanadeklaracja = podpiszDeklaracje(deklaracja);
                if (podpisanadeklaracja != null) {
                    DeklaracjavatUE deklaracjavatUE = generujdeklaracje(podpisanadeklaracja);
                    deklaracjavatUE.setNrkolejny(nrkolejny);
                    for (VatUe p : lista) {
                        p.setDeklaracjavatUE(deklaracjavatUE);
                    }
                    deklaracjavatUE.setPozycje(lista);
                    //deklaracjavatUEDAO.dodaj(deklaracjavatUE); dodamy ja przy wysylce bo wtedy robimy edit dok
                    deklaracjeUE.add(deklaracjavatUE);
                    deklaracjeUE_biezace.add(deklaracjavatUE);
                    Msg.msg("Sporządzono deklarację VAT-UE miesięczną wersja 4");
                    zwrot = true;
                } else {
                    Msg.msg("e","Wystąpił błąd. Niesporządzono deklaracji VAT-UE. Sprawdź czy włożono kartę z podpisem! Sprawdź oznaczenia krajów i NIP-y");
                }
            } else {
                Msg.msg("e", (String) walidacja[1]);
            }
        } catch (Exception e) {
            Msg.msg("e","Wystąpił błąd. Niesporządzono deklaracji VAT-UE miesięczną wersja. Sprawdź parametry podatnika!");
        }
        return zwrot;
    }
    
     public boolean robdeklaracjekorekta(List<VatUe> lista, List<VatUe> staralista, boolean korekta, int nrkolejny) {
        boolean zwrot = false;
        try {
            String deklaracja = sporzadzkorekta(lista, staralista, korekta);
                Object[] podpisanadeklaracja = podpiszDeklaracje(deklaracja);
            if (podpisanadeklaracja != null) {
                DeklaracjavatUE deklaracjavatUE = generujdeklaracje(podpisanadeklaracja);
                deklaracjavatUE.setNrkolejny(nrkolejny);
                for (VatUe p : lista) {
                    p.setDeklaracjavatUE(deklaracjavatUE);
                }
                deklaracjavatUE.setPozycje(lista);
                //deklaracjavatUEDAO.dodaj(deklaracjavatUE);
                deklaracjeUE.add(deklaracjavatUE);
                deklaracjeUE_biezace.add(deklaracjavatUE);
                Msg.msg("Sporządzono deklarację VAT-UEK miesięczną wersja 4");
                zwrot = true;
            } else {
                Msg.msg("e","Wystąpił błąd. Niesporządzono deklaracji VAT-UEK. Sprawdź czy włożono kartę z podpisem! Sprawdź oznaczenia krajów i NIP-y");
            }
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e","Wystąpił błąd. Niesporządzono deklaracji VAT-UEK miesięczną wersja 4");
        }
        return zwrot;
    }
    
    private String sporzadz(List<VatUe> lista, boolean korekta) {
        deklaracje.vatue.m4.Deklaracja deklaracja = new deklaracje.vatue.m4.Deklaracja();
        String kodurzedu = tKodUS.getMapaUrzadKod().get(wpisView.getPodatnikObiekt().getUrzadskarbowy());
        deklaracja.setNaglowek(VATUEM4Bean.tworznaglowek(wpisView.getMiesiacWpisu(),wpisView.getRokWpisuSt(),kodurzedu));
        deklaracja.setPodmiot1(VATUEM4Bean.podmiot1(wpisView));
        deklaracja.setPozycjeSzczegolowe(VATUEM4Bean.pozycjeszczegolowe(lista));
        deklaracja.setPouczenie(BigDecimal.ONE);
        return VATUEM4Bean.marszajuldoStringu(deklaracja, wpisView);
    }
    
    private String sporzadzkorekta(List<VatUe> lista, List<VatUe> staralista, boolean korekta) {
        List<VatUe> listaroznic = sporzadzroznice(lista, staralista);
        deklaracje.vatuek.m4.Deklaracja deklaracja = new deklaracje.vatuek.m4.Deklaracja();
        String kodurzedu = tKodUS.getMapaUrzadKod().get(wpisView.getPodatnikObiekt().getUrzadskarbowy());
        deklaracja.setNaglowek(VATUEKM4Bean.tworznaglowek(wpisView.getMiesiacWpisu(),wpisView.getRokWpisuSt(),kodurzedu));
        deklaracja.setPodmiot1(VATUEKM4Bean.podmiot1(wpisView));
        deklaracja.setPozycjeSzczegolowe(VATUEKM4Bean.pozycjeszczegolowe(listaroznic));
        deklaracja.setPouczenie(BigDecimal.ONE);
        return VATUEKM4Bean.marszajuldoStringu(deklaracja, wpisView);
    }

    private Object[] podpiszDeklaracje(String xml) {
        Object[] deklaracjapodpisana = null;
        try {
            deklaracjapodpisana = Xad.podpisz(xml, wpisView.getPodatnikObiekt().getKartacert());
        } catch (Exception e) {
            E.e(e);
        }
        return deklaracjapodpisana;
    }
    
    private DeklaracjavatUE generujdeklaracje(Object[] podpisanadeklaracja) {
        DeklaracjavatUE deklaracjavatUE = new DeklaracjavatUE();
        deklaracjavatUE.setPodatnik(wpisView.getPodatnikWpisu());
        deklaracjavatUE.setMiesiac(wpisView.getMiesiacWpisu());
        deklaracjavatUE.setRok(wpisView.getRokWpisuSt());
        deklaracjavatUE.setDeklaracja((String) podpisanadeklaracja[1]);
        deklaracjavatUE.setDeklaracjapodpisana((byte[]) podpisanadeklaracja[0]);
        deklaracjavatUE.setNrkwartalu(Kwartaly.getMapamckw().get(wpisView.getMiesiacWpisu()));
        deklaracjavatUE.setJestcertyfikat(true);
        deklaracjavatUE.setKodurzedu(tKodUS.getMapaUrzadKod().get(wpisView.getPodatnikObiekt().getUrzadskarbowy()));
        String sporzadzil = wpisView.getUzer().getImie()+" "+wpisView.getUzer().getNazw();
        deklaracjavatUE.setSporzadzil(sporzadzil);
        deklaracjavatUE.setWzorschemy("http://crd.gov.pl/wzor/2017/01/11/3846/");
        return deklaracjavatUE;
    }
    
   

    private List<VatUe> sporzadzroznice(List<VatUe> nowalista, List<VatUe> staralista) {
        List<VatUe> lista = Collections.synchronizedList(new ArrayList<>());
        for (VatUe p : nowalista) {
            if (p.getKontrahent()!=null) {
                boolean niebylojeszcze = true;
                for (VatUe s : staralista) {
                    if (s.getKontrahent()!=null) {
                        if (p.getKontrahent().equals(s.getKontrahent())) {
                            if (Z.z(p.getNetto()) != Z.z(s.getNetto())) {
                                p.setNettoprzedkorekta(s.getNetto());
                                lista.add(p);
                            }
                            niebylojeszcze = false;
                            break;
                        }
                    }
                }
                if (niebylojeszcze) {
                    lista.add(p);
                }
            }
        }
        for (VatUe s : staralista) {
            if (s.getKontrahent()!=null) {
                boolean bylojuzaleniema = true;
                for (VatUe p : nowalista) {
                    if (p.getKontrahent()!=null) {
                        if (p.getKontrahent().equals(s.getKontrahent())) {
                            bylojuzaleniema = false;
                            break;
                        }
                    }
                }
                if (bylojuzaleniema) {
                    double wartoscprzedkorekta = s.getNetto();
                    s.setNettoprzedkorekta(wartoscprzedkorekta);
                    s.setNetto(0.0);
                    lista.add(s);
                }
            }
        }
        return lista;
    }

    

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public List<VatUe> getKlienciWDTWNT() {
        return klienciWDTWNT;
    }

    public String getOpisvatuepkpir() {
        return opisvatuepkpir;
    }

    public void setOpisvatuepkpir(String opisvatuepkpir) {
        this.opisvatuepkpir = opisvatuepkpir;
    }

    public DeklaracjavatUE getDeklUEselected() {
        return deklUEselected;
    }

    public void setDeklUEselected(DeklaracjavatUE deklUEselected) {
        this.deklUEselected = deklUEselected;
    }

    public void setKlienciWDTWNT(List<VatUe> klienciWDTWNT) {
        this.klienciWDTWNT = klienciWDTWNT;
    }

    public List<VatUe> getListawybranych() {
        return listawybranych;
    }

    public void setListawybranych(List<VatUe> listawybranych) {
        this.listawybranych = listawybranych;
    }

    public List<DeklaracjavatUE> getDeklaracjeUE() {
        return deklaracjeUE;
    }

    public void setDeklaracjeUE(List<DeklaracjavatUE> deklaracjeUE) {
        this.deklaracjeUE = deklaracjeUE;
    }

    public double getSumawybranych() {
        return sumawybranych;
    }

    public void setSumawybranych(double sumawybranych) {
        this.sumawybranych = sumawybranych;
    }

    public boolean isDeklaracja0korekta1() {
        return deklaracja0korekta1;
    }

    public void setDeklaracja0korekta1(boolean deklaracja0korekta1) {
        this.deklaracja0korekta1 = deklaracja0korekta1;
    }

    public List<DokSuper> getListadokumentowUE() {
        return listadokumentowUE;
    }

    public void setListadokumentowUE(List<DokSuper> listadokumentowUE) {
        this.listadokumentowUE = listadokumentowUE;
    }

    public List<Dok> getListaDok() {
        return listaDok;
    }

    public void setListaDok(List<Dok> listaDok) {
        this.listaDok = listaDok;
    }

    public List<Dokfk> getListaDokfk() {
        return listaDokfk;
    }

    public void setListaDokfk(List<Dokfk> listaDokfk) {
        this.listaDokfk = listaDokfk;
    }

    public List<DeklaracjavatUE> getDeklaracjeUE_biezace() {
        return deklaracjeUE_biezace;
    }

    public void setDeklaracjeUE_biezace(List<DeklaracjavatUE> deklaracjeUE_biezace) {
        this.deklaracjeUE_biezace = deklaracjeUE_biezace;
    }

   
 

   
}
