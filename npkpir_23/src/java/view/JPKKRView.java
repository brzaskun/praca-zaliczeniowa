/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.DokfkNrDziennikacomparator;
import comparator.SaldoKontocomparator;
import dao.DokDAOfk;
import data.Data;
import static deklaracje.vatuek.m4.VATUEKM4Bean.StringToDocument;
import embeddablefk.SaldoKonto;
import entity.Podatnik;
import entityfk.Dokfk;
import entityfk.Konto;
import entityfk.StronaWiersza;
import entityfk.Wiersz;
import error.E;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamResult;
import msg.Msg;
import org.primefaces.PrimeFaces;
import org.w3c.dom.Document;
import pl.gov.crd.xml.schematy.dziedzinowe.mf._2013._05._23.ed.kodycechkrajow.CurrCodeType;
import pl.gov.crd.xml.schematy.dziedzinowe.mf._2016._01._25.ed.definicjetypy.TAdresPolski;
import pl.gov.crd.xml.schematy.dziedzinowe.mf._2016._01._25.ed.definicjetypy.TIdentyfikatorOsobyNiefizycznej;
import pl.gov.crd.xml.schematy.dziedzinowe.mf._2016._01._25.ed.definicjetypy.TKodKraju;
import pl.gov.mf.jpk.wzor._2016._03._09._03091.JPK;
import pl.gov.mf.jpk.wzor._2016._03._09._03091.TKodFormularza;
import pl.gov.mf.jpk.wzor._2016._03._09._03091.TNaglowek;
import viewfk.SaldoAnalitykaView;
import waluty.Z;
import xml.XMLValid;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class JPKKRView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    @Inject
    private DokDAOfk dokDAOfk;
    @Inject
    private SaldoAnalitykaView saldoAnalitykaView;
    
    
    public void przygotuj() {
        String dataod = Data.pierwszyDzien(wpisView);
        String datado = Data.ostatniDzien(wpisView);
        List<Dokfk> dokumenty = dokDAOfk.findDokfkPodatnikRokMc(wpisView);
        sumujdokumenty(dokumenty);
        Collections.sort(dokumenty, new DokfkNrDziennikacomparator());
        saldoAnalitykaView.init();
        List<SaldoKonto> salda = saldoAnalitykaView.getListaSaldoKonto();
        Collections.sort(salda, new SaldoKontocomparator());
        JPK rob = rob(false, dataod, datado, "3215", wpisView.getPodatnikObiekt(), dokumenty, salda);
        marszajuldoplikuxml(rob, wpisView);
        System.out.println("");
        //porownaj(dokumenty, kontozapisy);
    }
    
    
    private JPK rob(boolean pierwotna0korekta1, String dataod, String datado, String kodurzedu, Podatnik podatnik, List<Dokfk> dokumenty, List<SaldoKonto> salda) {
        JPK jpk = new JPK();
        jpk.setNaglowek(zrobnaglowek(jpk, pierwotna0korekta1, dataod, datado, kodurzedu));
        jpk.setPodmiot1(zrobpodmiot(podatnik));
        List<JPK.Dziennik> dziennik = zrobdziennik(dokumenty);
        jpk.getDziennik().addAll(dziennik);
        jpk.setDziennikCtrl(zrobdziennikCtrl(jpk.getDziennik()));
        List<JPK.KontoZapis> kontozapis = zrobkontozapis(dokumenty);
        jpk.getKontoZapis().addAll(kontozapis);
        jpk.setKontoZapisCtrl(zrobkontozapisCtrl(kontozapis));
        List<JPK.ZOiS> saldalista = zrobsalda(salda);
        jpk.getZOiS().addAll(saldalista);
        return jpk;
    }

    private TNaglowek zrobnaglowek(JPK jpk,boolean pierwotna0korekta1, String dataod, String datado, String kodurzedu) {
        TNaglowek naglowek = new TNaglowek();
        if (pierwotna0korekta1) {
            naglowek.setCelZlozenia((byte)2);
        } else {
            naglowek.setCelZlozenia((byte)1);
        }
        naglowek.setDataOd(Data.dataStringToXMLGregorian(dataod));
        naglowek.setDataDo(Data.dataStringToXMLGregorian(datado));
        naglowek.setDataWytworzeniaJPK(Data.databiezaca());
        naglowek.setDomyslnyKodWaluty(CurrCodeType.PLN);
        naglowek.setKodFormularza(zrobkodsystemowy());
        naglowek.setKodUrzedu(kodurzedu);
        naglowek.setWariantFormularza((byte)1);
        return naglowek;
    }

    private TNaglowek.KodFormularza zrobkodsystemowy() {
        TNaglowek.KodFormularza kod = new TNaglowek.KodFormularza();
        kod.setKodSystemowy(kod.getKodSystemowy());
        kod.setValue(TKodFormularza.JPK_KR);
        kod.setWersjaSchemy(kod.getWersjaSchemy());
        return kod;
    }

    private JPK.Podmiot1 zrobpodmiot(Podatnik podatnik) {
        JPK.Podmiot1 podmiot = new JPK.Podmiot1();
        podmiot.setIdentyfikatorPodmiotu(zrobidentyfikator(podatnik));
        podmiot.setAdresPodmiotu(zrobadres(podatnik));
        return podmiot;
    }

    private TIdentyfikatorOsobyNiefizycznej zrobidentyfikator(Podatnik podatnik) {
        TIdentyfikatorOsobyNiefizycznej ident = new TIdentyfikatorOsobyNiefizycznej();
        ident.setNIP(podatnik.getNip());
        ident.setREGON(podatnik.getRegon());
        ident.setPelnaNazwa(podatnik.getNazwaRejestr());
        return ident;
    }

    private TAdresPolski zrobadres(Podatnik podatnik) {
        TAdresPolski adres = new TAdresPolski();
        adres.setKodKraju(TKodKraju.PL);
        adres.setWojewodztwo(podatnik.getWojewodztwo());
        adres.setPowiat(podatnik.getPowiat());
        adres.setGmina(podatnik.getGmina());
        adres.setMiejscowosc(podatnik.getMiejscowosc());
        adres.setKodPocztowy(podatnik.getKodpocztowy());
        adres.setUlica(podatnik.getUlica());
        adres.setNrDomu(podatnik.getNrdomu());
        adres.setNrLokalu(podatnik.getNrlokalu());
        adres.setPoczta(podatnik.getPoczta());
        return adres;
    }

    private List<JPK.Dziennik> zrobdziennik(List<Dokfk> dokumenty) {
         List<JPK.Dziennik> listadziennik = new ArrayList<>();
         for (Dokfk p : dokumenty) {
             JPK.Dziennik dziennik = new JPK.Dziennik();
             dziennik.setDataDowodu(Data.dataStringToXMLGregorian(p.getDatadokumentu()));
             dziennik.setDataKsiegowania(Data.dataStringToXMLGregorian(p.getDatawplywu()));
             dziennik.setDataOperacji(Data.dataStringToXMLGregorian(p.getDataoperacji()));
             dziennik.setDziennikKwotaOperacji(BigDecimal.valueOf(Z.z(sumujdokument(p))));
             dziennik.setKodOperatora(p.getWprowadzil()!=null?p.getWprowadzil():"taxman");
             dziennik.setLpZapisuDziennika(BigInteger.valueOf(Integer.valueOf(p.getNrdziennika())));
             dziennik.setNrDowoduKsiegowego(p.getNumerwlasnydokfk());
             dziennik.setNrZapisuDziennika(p.getDokfkSN());
             dziennik.setOpisDziennika(p.getRodzajedok().getRodzajtransakcji());
             dziennik.setOpisOperacji(p.getOpisdokfk());
             dziennik.setTyp(dziennik.getTyp());
             dziennik.setRodzajDowodu(p.getRodzajedok().getNazwa());
             listadziennik.add(dziennik);
         }
         return listadziennik;
    }

    private List<JPK.KontoZapis> zrobkontozapis(List<Dokfk> dokumenty) {
        List<JPK.KontoZapis> listakontozapisy= new ArrayList<>();
        for (Dokfk p : dokumenty) {
            int i = 1;
            for (StronaWiersza r : p.getStronyWierszy()) {
                JPK.KontoZapis zapis = new JPK.KontoZapis();
                if (r.isWn()) {
                    zapis.setKodKontaWinien(r.getKonto().getPelnynumer());
                    if (!r.getSymbolWaluty().equals("PLN")) {
                        zapis.setKodWalutyWinien(pobierzwalute(r));
                        zapis.setKwotaWinienWaluta(BigDecimal.valueOf(Z.z(r.getKwotaWaluta())));
                    }
                    zapis.setKwotaWinien(BigDecimal.valueOf(Z.z(r.getKwotaPLN())));
                    zapis.setLpZapisu(BigInteger.valueOf(i++));
                    zapis.setNrZapisu(r.getDokfkS());
                    zapis.setOpisZapisuWinien(r.getWiersz().getOpisWiersza());
                    zapis.setTyp(zapis.getTyp());
                    //Ma
                    zapis.setKodKontaMa(r.getKonto().getPelnynumer());
                    zapis.setKwotaMa(BigDecimal.valueOf(0.0));
                } else {
                    zapis.setKodKontaMa(r.getKonto().getPelnynumer());
                    if (!r.getSymbolWaluty().equals("PLN")) {
                        zapis.setKodWalutyMa(pobierzwalute(r));
                        zapis.setKwotaMaWaluta(BigDecimal.valueOf(Z.z(r.getKwotaWaluta())));
                    }
                    zapis.setKwotaMa(BigDecimal.valueOf(Z.z(r.getKwotaPLN())));
                    zapis.setLpZapisu(BigInteger.valueOf(i++));
                    zapis.setNrZapisu(r.getDokfkS());
                    zapis.setOpisZapisuMa(r.getWiersz().getOpisWiersza());
                    zapis.setTyp(zapis.getTyp());
                    //Wn
                    zapis.setKodKontaWinien(r.getKonto().getPelnynumer());
                    zapis.setKwotaWinien(BigDecimal.valueOf(0.0));
                }
                listakontozapisy.add(zapis);
            }
        }
        return listakontozapisy;
    }

    private CurrCodeType pobierzwalute(StronaWiersza r) {
        String symbol = r.getSymbolWaluty();
        CurrCodeType zwrot = CurrCodeType.valueOf(symbol);
        return zwrot;
    }

    private List<JPK.ZOiS> zrobsalda(List<SaldoKonto> salda) {
        List<JPK.ZOiS> listakontozapisy= new ArrayList<>();
        for (SaldoKonto p : salda) {
            JPK.ZOiS saldo = new JPK.ZOiS();
            Konto macierzyste = p.getKonto().getKontomacierzyste()!=null?p.getKonto().getKontomacierzyste():p.getKonto();
            saldo.setBilansOtwarciaWinien(BigDecimal.valueOf(p.getBoWn()));
            saldo.setBilansOtwarciaMa(BigDecimal.valueOf(p.getBoMa()));
            saldo.setObrotyWinien(BigDecimal.valueOf(p.getObrotyWnMc()));
            saldo.setObrotyMa(BigDecimal.valueOf(p.getObrotyMaMc()));
            saldo.setObrotyWinienNarast(BigDecimal.valueOf(p.getObrotyWn()));
            saldo.setObrotyMaNarast(BigDecimal.valueOf(p.getObrotyMa()));
            saldo.setSaldoWinien(BigDecimal.valueOf(p.getSaldoWn()));
            saldo.setSaldoMa(BigDecimal.valueOf(p.getSaldoMa()));
            saldo.setKodKonta(p.getKonto().getPelnynumer());
            saldo.setKodKategorii(macierzyste.getPelnynumer());
//            saldo.setKodPodkategorii(macierzyste.getPelnynumer());
            saldo.setKodZespolu(macierzyste.getPelnynumer().substring(0, 1));
            saldo.setOpisKategorii(macierzyste.getNazwapelna());
            saldo.setOpisKonta(p.getKonto().getNazwapelna());
            saldo.setOpisZespolu(macierzyste.getNazwapelna());
            saldo.setTypKonta(p.getKonto().getBilansowewynikowe()+"/"+p.getKonto().getZwyklerozrachszczegolne());
            saldo.setTyp(saldo.getTyp());
            listakontozapisy.add(saldo);
        }
        return listakontozapisy;
    }

    private JPK.DziennikCtrl zrobdziennikCtrl(List<JPK.Dziennik> dziennik) {
        JPK.DziennikCtrl suma = new JPK.DziennikCtrl();
        suma.setLiczbaWierszyDziennika(BigInteger.valueOf(dziennik.size()));
        double summa = 0.0;
        for (JPK.Dziennik p  : dziennik) {
            summa = summa+p.getDziennikKwotaOperacji().doubleValue();
        }
        suma.setSumaKwotOperacji(BigDecimal.valueOf(Z.z(summa)));
        return suma;
    }

    private JPK.KontoZapisCtrl zrobkontozapisCtrl(List<JPK.KontoZapis> kontozapis) {
        JPK.KontoZapisCtrl suma = new JPK.KontoZapisCtrl();
        suma.setLiczbaWierszyKontoZapisj(BigInteger.valueOf(kontozapis.size()));
        double summawn = 0.0;
        double summama = 0.0;
        for (JPK.KontoZapis p : kontozapis) {
            if (p.getKwotaWinien()!=null) {
                summawn = summawn+p.getKwotaWinien().doubleValue();
            }
            if (p.getKwotaMa()!=null) {
                summama = summama+p.getKwotaMa().doubleValue();
            }
        }
        suma.setSumaWinien(BigDecimal.valueOf(Z.z(summawn)));
        suma.setSumaMa(BigDecimal.valueOf(Z.z(summama)));
        return suma;
    }
    
    public static void marszajuldoplikuxml(JPK jpk, WpisView wpisView) {
        try {
            JAXBContext context = JAXBContext.newInstance(JPK.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            StringWriter sw = new StringWriter();
            marshaller.marshal(jpk, new StreamResult(sw));
            Document dokmt = StringToDocument(sw.toString());
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String mainfilename = "JPKKR"+wpisView.getPodatnikObiekt().getNip()+".xml";
            String plik = ctx.getRealPath("/")+"resources\\xml\\"+mainfilename;
            FileOutputStream fileStream = new FileOutputStream(new File(plik));
            OutputStreamWriter writer = new OutputStreamWriter(fileStream, "UTF-8");
            marshaller.marshal(jpk, writer);
            Object[] walidacja = XMLValid.walidujJPKKR(mainfilename);
            String[] zwrot = new String[2];
            if (walidacja!=null && walidacja[0]==Boolean.TRUE) {
                zwrot[0] = mainfilename;
                zwrot[1] = "ok";
                Msg.msg("Walidacja JPK pomyślna");
                Msg.msg("Zachowano JPK");
                String exec = "wydrukJPK('"+mainfilename+"')";
                PrimeFaces.current().executeScript(exec);
            } else if (walidacja!=null && walidacja[0]==Boolean.FALSE){
                zwrot[0] = mainfilename;
                zwrot[1] = null;
                Msg.msg("Nie zachowano JPK");
                Msg.msg("e", (String) walidacja[1]);
            }
           
        } catch (Exception ex) {
            E.e(ex);
        }
    }
    
    private double sumujdokument(Dokfk d) {
        double suma = 0.0;
        for (Wiersz p : d.getListawierszy()) {
            double sumawiersza = dodajKwotyWierszaDoSumyDokumentu(p);
            suma += Z.z(sumawiersza);
        }
        return suma;
    }

    private void sumujdokumenty(List<Dokfk> dokumenty) {
        for (Dokfk d : dokumenty) {
            d.setWartoscdokumentu(0.0);
            for (Wiersz p : d.getListawierszy()) {
                double sumawiersza = dodajKwotyWierszaDoSumyDokumentu(p);
                d.setWartoscdokumentu(Z.z(d.getWartoscdokumentu()+sumawiersza));
            }
        }
    }
    
     public double dodajKwotyWierszaDoSumyDokumentu(Wiersz biezacywiersz) {
        double suma = 0.0;
        try {//robimy to bo sa nowy wiersz jest tez podsumowywany, ale moze byc przeciez pusty wiec wyrzuca blad
            int typwiersza = biezacywiersz.getTypWiersza();
            double wn = biezacywiersz.getStronaWn() != null ? biezacywiersz.getStronaWn().getKwotaPLN() : 0.0;
            double ma = biezacywiersz.getStronaMa() != null ? biezacywiersz.getStronaMa().getKwotaPLN() : 0.0;
            
            if (typwiersza == 1) {
                suma += wn;
            } else if (typwiersza == 2) {
                suma += ma;
            } else {
                double kwotaWn = wn;
                double kwotaMa = ma;
                if (Math.abs(kwotaMa) > Math.abs(kwotaWn)) {
                    suma += wn;
                } else {
                    suma += ma;
                }
            }
        } catch (Exception e) {
            System.out.println("");
        }
        return Z.z(suma);
    }


    private void porownaj(List<Dokfk> dokumenty, List<StronaWiersza> strony) {
        for (Iterator<StronaWiersza> it = strony.iterator(); it.hasNext();) {
            StronaWiersza p = it.next();
            if (dokumenty.contains(p.getDokfk())) {
                it.remove();
            }
        }
        System.out.println("");
    }

}
