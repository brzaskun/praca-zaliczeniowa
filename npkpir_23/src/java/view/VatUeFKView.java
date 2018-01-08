/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DeklaracjavatUEDAO;
import dao.DokDAO;
import dao.KlienciDAO;
import dao.PodatnikDAO;
import daoFK.DokDAOfk;
import daoFK.VatuepodatnikDAO;
import daoFK.ViesDAO;
import data.Data;
import embeddable.Parametr;
import embeddable.VatUe;
import entity.DeklaracjavatUE;
import entity.Dok;
import entity.DokSuper;
import entity.Klienci;
import entityfk.Dokfk;
import entityfk.Vatuepodatnik;
import entityfk.VatuepodatnikPK;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import pdf.PdfVATUEdekl;
import pdf.PdfVatUE;
import pdffk.PdfVIES;
import vies.VIESCheckBean;
import vies.Vies;

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
    private String opisvatuepkpir;
    boolean deklaracja0korekta1;

    public VatUeFKView() {
        klienciWDTWNT = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        List<DokSuper> listadokumentowUE = new ArrayList<>();
        //List<Dokfk> dokvatmc = new ArrayList<>();
        Integer rok = wpisView.getRokWpisu();
        String m = wpisView.getMiesiacWpisu();
        try {
            String vatUEokres = ParametrView.zwrocParametr(wpisView.getPodatnikObiekt().getParamVatUE(), rok, m);
            if (vatUEokres.equals("miesięczne")) {
                if (wpisView.isKsiegirachunkowe()) {
                    listadokumentowUE = dokDAOfk.findDokfkPodatnikRokMc(wpisView);
                } else {
                    listadokumentowUE = dokDAO.zwrocBiezacegoKlientaRokMC(wpisView);
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
                            } else {
                                s.getZawiera().add((Dok) p);
                            }
                            String nazwawal = p.getWalutadokumentu() != null ? p.getWalutadokumentu().getSymbolwaluty() : "";
                            s.setNazwawaluty(nazwawal);
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
    
    private void init2() {
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
    
    

    private void zachowajwbazie(String rok, String symbolokresu, String klient) {
        if (klienciWDTWNT != null) {
            Vatuepodatnik vatuepodatnik = new Vatuepodatnik();
            VatuepodatnikPK vatuepodatnikPK = new VatuepodatnikPK();
            vatuepodatnikPK.setRok(rok);
            vatuepodatnikPK.setSymbolokresu(symbolokresu);
            vatuepodatnikPK.setKlient(klient);
            vatuepodatnik.setVatuepodatnikPK(vatuepodatnikPK);
            vatuepodatnik.setKlienciwdtwnt(klienciWDTWNT);
            vatuepodatnik.setMc0kw1(Boolean.TRUE);
            vatuepodatnik.setRozliczone(Boolean.FALSE);
            //bo czasami nie edytowalo nie wiem dlaczego
            try {
                vatuepodatnikDAO.destroy(vatuepodatnik);
            } catch (Exception e) { E.e(e); };
            try {
                vatuepodatnikDAO.dodaj(vatuepodatnik);
                Msg.msg("i", "Zachowano dane do VAT-EU");
            } catch (Exception e) { E.e(e); 
                Msg.msg("e", "Błąd podczas zachowywania danych do VAT-UE");
            }
        }
    }
    
    private Set<VatUe> kontrahenciUE(List<DokSuper> listadokumentow) {
        Set<VatUe> klienty = new HashSet<>();
        for (DokSuper p : listadokumentow) {
            if (warunekkontrahenci(p)) {
                //wyszukujemy dokumenty WNT i WDT dodajemu do sumy
                VatUe veu = new VatUe(p.getTypdokumentu(), p.getKontr(), 0.0, 0);
                veu.setZawierafk(new ArrayList<>());
                klienty.add(veu);
                uzupelnijdanekontrahenta(p.getKontr());
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
       deklaracjeUE = deklaracjavatUEDAO.findbyPodatnikRok(wpisView);
       if (deklaracjeUE == null) {
           deklaracjeUE = new ArrayList<>();
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
        VIESCheckBean.sprawdz(klienciWDTWNT, viesDAO, wpisView.getPodatnikObiekt(), wpisView.getWprowadzil());
        Msg.msg("Sprawdzono VIES");
       } catch (Exception e) {
           E.e(e);
       }
    }
   
    public void drukujVIES() {
        try {
            if (klienciWDTWNT == null) {
                Msg.msg("e", "Lista VIES pusta - nie ma czego drukować");
            } else {
                List<Vies> lista = new ArrayList<>();
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
                PdfVATUEdekl.drukujVATUE(podatnikDAO, d, wpisView);
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
            Msg.dP();
            init2();
        } catch (Exception e) {
            Msg.dPe();
        }
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

   
 

   
}
