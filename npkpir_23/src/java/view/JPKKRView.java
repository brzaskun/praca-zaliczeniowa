/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import data.Data;
import embeddablefk.SaldoKonto;
import entity.Podatnik;
import entityfk.Dokfk;
import entityfk.KontoZapisy;
import entityfk.StronaWiersza;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.gov.crd.xml.schematy.dziedzinowe.mf._2013._05._23.ed.kodycechkrajow.CurrCodeType;
import pl.gov.crd.xml.schematy.dziedzinowe.mf._2016._01._25.ed.definicjetypy.TAdresPolski;
import pl.gov.crd.xml.schematy.dziedzinowe.mf._2016._01._25.ed.definicjetypy.TIdentyfikatorOsobyNiefizycznej;
import pl.gov.crd.xml.schematy.dziedzinowe.mf._2016._01._25.ed.definicjetypy.TKodKraju;
import pl.gov.mf.jpk.wzor._2016._03._09._03091.JPK;
import pl.gov.mf.jpk.wzor._2016._03._09._03091.TKodFormularza;
import pl.gov.mf.jpk.wzor._2016._03._09._03091.TNaglowek;

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
    
    private JPK rob(boolean pierwotna0korekta1, String dataod, String datado, String kodurzedu, Podatnik podatnik, List<Dokfk> dokumenty, List<KontoZapisy> kontozapisy, List<SaldoKonto> salda) {
        JPK jpk = new JPK();
        jpk.setNaglowek(zrobnaglowek(jpk, pierwotna0korekta1, dataod, datado, kodurzedu));
        jpk.setPodmiot1(zrobpodmiot(podatnik));
        List<JPK.Dziennik> dziennik = zrobdziennik(dokumenty);
        jpk.getDziennik().addAll(dziennik);
        jpk.setDziennikCtrl(zrobdziennikCtrl(jpk.getDziennik()));
        List<JPK.KontoZapis> kontozapis = zrobkontozapis(kontozapisy);
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
        return adres;
    }

    private List<JPK.Dziennik> zrobdziennik(List<Dokfk> dokumenty) {
         List<JPK.Dziennik> listadziennik = new ArrayList<>();
         for (Dokfk p : dokumenty) {
             JPK.Dziennik dziennik = new JPK.Dziennik();
             dziennik.setDataDowodu(Data.dataStringToXMLGregorian(p.getDatadokumentu()));
             dziennik.setDataKsiegowania(Data.dataStringToXMLGregorian(p.getDatawplywu()));
             dziennik.setDataOperacji(Data.dataStringToXMLGregorian(p.getDataoperacji()));
             dziennik.setDziennikKwotaOperacji(BigDecimal.valueOf(p.getWartoscdokumentu()));
             dziennik.setKodOperatora(p.getWprowadzil());
             dziennik.setLpZapisuDziennika(BigInteger.valueOf(Integer.valueOf(p.getNrdziennika())));
             dziennik.setNrDowoduKsiegowego(p.getNumerwlasnydokfk());
             dziennik.setNrZapisuDziennika(p.getDokfkSN());
             dziennik.setOpisDziennika(p.getRodzajedok().getNazwa());
             dziennik.setOpisOperacji(p.getOpisdokfk());
             dziennik.setTyp(p.getRodzajedok().getRodzajtransakcji());
             listadziennik.add(dziennik);
         }
         return listadziennik;
    }

    private List<JPK.KontoZapis> zrobkontozapis(List<KontoZapisy> kontozapisy) {
        List<JPK.KontoZapis> listakontozapisy= new ArrayList<>();
        for (KontoZapisy p : kontozapisy) {
            int i = 1;
            for (StronaWiersza r : p.getStronywiersza()) {
                JPK.KontoZapis zapis = new JPK.KontoZapis();
                if (r.isWn()) {
                    zapis.setKodKontaWinien(p.getKonto().getPelnynumer());
                    if (r.getSymbolWaluty().equals("PLN")) {
                        zapis.setKodWalutyWinien(pobierzwalute(r));
                        zapis.setKwotaWinienWaluta(BigDecimal.valueOf(r.getKwotaWaluta()));
                    }
                    zapis.setKwotaWinien(BigDecimal.valueOf(r.getKwotaPLN()));
                    zapis.setLpZapisu(BigInteger.valueOf(i++));
                    zapis.setNrZapisu(r.getDokfkS());
                    zapis.setOpisZapisuWinien(r.getWiersz().getOpisWiersza());
                    zapis.setTyp(r.getTyp());
                } else {
                    zapis.setKodKontaMa(p.getKonto().getPelnynumer());
                    if (r.getSymbolWaluty().equals("PLN")) {
                        zapis.setKodWalutyMa(pobierzwalute(r));
                        zapis.setKwotaMaWaluta(BigDecimal.valueOf(r.getKwotaWaluta()));
                    }
                    zapis.setKwotaMa(BigDecimal.valueOf(r.getKwotaPLN()));
                    zapis.setLpZapisu(BigInteger.valueOf(i++));
                    zapis.setNrZapisu(r.getDokfkS());
                    zapis.setOpisZapisuWinien(r.getWiersz().getOpisWiersza());
                    zapis.setTyp(r.getTyp());
                }
            
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
            saldo.setBilansOtwarciaWinien(BigDecimal.valueOf(p.getBoWn()));
            saldo.setBilansOtwarciaMa(BigDecimal.valueOf(p.getBoMa()));
            saldo.setObrotyWinien(BigDecimal.valueOf(p.getObrotyWnMc()));
            saldo.setObrotyMa(BigDecimal.valueOf(p.getObrotyMaMc()));
            saldo.setObrotyWinienNarast(BigDecimal.valueOf(p.getObrotyWn()));
            saldo.setObrotyMaNarast(BigDecimal.valueOf(p.getObrotyMa()));
            saldo.setSaldoWinien(BigDecimal.valueOf(p.getSaldoWn()));
            saldo.setSaldoMa(BigDecimal.valueOf(p.getSaldoMa()));
            saldo.setKodKonta(p.getKonto().getPelnynumer());
            saldo.setKodKategorii(p.getNrpelnymacierzystego());
            saldo.setKodPodkategorii(p.getKonto().getKontomacierzyste().getPelnynumer());
            saldo.setKodZespolu(p.getNrpelnymacierzystego().substring(0, 1));
            saldo.setOpisKategorii(p.getKonto().getKontomacierzyste().getNazwapelna());
            saldo.setOpisKonta(p.getKonto().getNazwapelna());
            saldo.setOpisZespolu(p.getKonto().getKontomacierzyste().getNazwapelna());
            saldo.setTypKonta(p.getKonto().getBilansowewynikowe()+"/"+p.getKonto().getZwyklerozrachszczegolne());
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
        suma.setSumaKwotOperacji(BigDecimal.valueOf(summa));
        return suma;
    }

    private JPK.KontoZapisCtrl zrobkontozapisCtrl(List<JPK.KontoZapis> kontozapis) {
        JPK.KontoZapisCtrl suma = new JPK.KontoZapisCtrl();
        suma.setLiczbaWierszyKontoZapisj(BigInteger.valueOf(kontozapis.size()));
        double summawn = 0.0;
        double summama = 0.0;
        for (JPK.KontoZapis p : kontozapis) {
            summawn = summawn+p.getKwotaWinien().doubleValue();
            summama = summama+p.getKwotaMa().doubleValue();
        }
        suma.setSumaWinien(BigDecimal.valueOf(summawn));
        suma.setSumaMa(BigDecimal.valueOf(summama));
        return suma;
    }

}
