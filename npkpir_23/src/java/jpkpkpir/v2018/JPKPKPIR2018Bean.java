/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpkpkpir.v2018;

import data.Data;
import embeddable.DokKsiega;
import embeddable.TKodUS;
import entity.Podatnik;
import error.E;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import msg.Msg;
import view.ParametrView;
import view.WpisView;import waluty.Z;

/**
 *
 * @author Osito
 */
public class JPKPKPIR2018Bean {


    
    static TNaglowek naglowek(WpisView wpisView, byte celzlozenia, TKodUS tKodUS) throws Exception {
        TNaglowek tNaglowek = new TNaglowek();
        try {
            tNaglowek.celZlozenia = celzlozenia;
            tNaglowek.dataOd = Data.dataoddo(Data.dzienpierwszy(wpisView));
            tNaglowek.dataDo = Data.dataoddo(Data.ostatniDzien(wpisView));
            tNaglowek.dataWytworzeniaJPK = Data.databiezaca();
            tNaglowek.domyslnyKodWaluty = CurrCodeType.PLN;
            tNaglowek.kodFormularza = kodformularza();
            tNaglowek.kodUrzedu = tKodUS.getMapaUrzadKod().get(wpisView.getPodatnikObiekt().getUrzadskarbowy());
            byte b = 2;
            tNaglowek.wariantFormularza = b;
        } catch (Exception e) {
            E.e(e);
            throw e;
        }
        return  tNaglowek;
    }

    private static TNaglowek.KodFormularza kodformularza() {
        TNaglowek.KodFormularza kf = new TNaglowek.KodFormularza();
        kf.kodSystemowy = kf.getKodSystemowy();
        kf.wersjaSchemy = kf.getWersjaSchemy();
        kf.value = TKodFormularza.JPK_PKPIR;
        return kf;
    }

    static JPK.Podmiot1 podmiot(WpisView wpisView) {
        JPK.Podmiot1 p1 = new JPK.Podmiot1();
        p1.identyfikatorPodmiotu = osobaniefizyczna(wpisView);
        p1.adresPodmiotu = adrespodmiotu(wpisView);
        return p1;
    }

    private static TIdentyfikatorOsobyNiefizycznej osobaniefizyczna(WpisView wpisView) {
        TIdentyfikatorOsobyNiefizycznej ionf = new TIdentyfikatorOsobyNiefizycznej();
        ionf.nip = wpisView.getPodatnikObiekt().getNip();
        ionf.regon = wpisView.getPodatnikObiekt().getRegon();
        ionf.pelnaNazwa = wpisView.getPrintNazwa();
        return ionf;
    }

    private static TAdresJPK adrespodmiotu(WpisView wpisView) {
        TAdresJPK ap = new TAdresJPK();
        try {
            Podatnik p = wpisView.getPodatnikObiekt();
            ap.kodKraju = TKodKraju.PL;
            ap.wojewodztwo = p.getWojewodztwo();
            ap.powiat = p.getPowiat();
            ap.gmina = p.getGmina();
            ap.miejscowosc = p.getMiejscowosc();
            ap.nrDomu = p.getNrdomu();
            ap.nrLokalu = p.getNrlokalu() != null ? p.getNrlokalu() : "";
            ap.kodPocztowy = p.getKodpocztowy();
            ap.poczta = p.getPoczta();
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e","Niewypełnione wsztyskie pola w adresie");
            throw e;
        }
        return ap;
    }

    static JPK.PKPIRInfo pkpirinfo(Map<String,Object> danepkpir_info) {
        JPK.PKPIRInfo pi = new JPK.PKPIRInfo();
        pi.p1 = (BigDecimal) danepkpir_info.get("p1");
        pi.p2 = (BigDecimal) danepkpir_info.get("p2");
        pi.p3 = (BigDecimal) danepkpir_info.get("p3");
        pi.p4 = (BigDecimal) danepkpir_info.get("p4");
        return pi;
    }

    static Collection<? extends JPK.PKPIRWiersz> generujwiersze(List<DokKsiega> wierszeksiegi) {
        List<JPK.PKPIRWiersz> lista = new ArrayList<>();
        try {
            for (DokKsiega p : wierszeksiegi) {
                if (p.getKontr()!=null) {
                    JPK.PKPIRWiersz w = new JPK.PKPIRWiersz();
                    w.typ = "G";
                    w.k1 = BigInteger.valueOf(p.getNrWpkpir());
                    w.k2 = Data.dataoddo(p.getDokument().getDataSprz());
                    w.k3 = p.getNrWlDk();
                    w.k4 = p.getKontr().getNpelna();
                    w.k5 = p.getKontr().getAdres();
                    w.k6 = p.getOpis();
                    w.k7 = p.getKolumna7() != null ? BigDecimal.valueOf(Z.z(p.getKolumna7())) : BigDecimal.ZERO;
                    w.k8 = p.getKolumna8() != null ? BigDecimal.valueOf(Z.z(p.getKolumna8())) : BigDecimal.ZERO;
                    w.k9 = p.getKolumna9() != null ? BigDecimal.valueOf(Z.z(p.getKolumna9())) : BigDecimal.ZERO;
                    w.k10 = p.getKolumna10() != null ? BigDecimal.valueOf(Z.z(p.getKolumna10())) : BigDecimal.ZERO;
                    w.k11 = p.getKolumna11() != null ? BigDecimal.valueOf(Z.z(p.getKolumna11())) : BigDecimal.ZERO;
                    w.k12 = p.getKolumna12() != null ? BigDecimal.valueOf(Z.z(p.getKolumna12())) : BigDecimal.ZERO;
                    w.k13 = p.getKolumna13() != null ? BigDecimal.valueOf(Z.z(p.getKolumna13())) : BigDecimal.ZERO;
                    w.k14 = p.getKolumna14() != null ? BigDecimal.valueOf(Z.z(p.getKolumna14())) : BigDecimal.ZERO;
                    w.k15 = p.getKolumna15() != null ? BigDecimal.valueOf(Z.z(p.getKolumna15())) : BigDecimal.ZERO;
                    lista.add(w);
                }
            }
        } catch (Exception e) {}
        return lista;
    }

    static JPK.PKPIRCtrl kontrola(Collection<? extends JPK.PKPIRWiersz> listawierszy) {
        JPK.PKPIRCtrl jp = new JPK.PKPIRCtrl();
        jp.liczbaWierszy = BigInteger.valueOf(listawierszy.size());
        jp.sumaPrzychodow = podliczprzychody(listawierszy);
        return jp;
    }

    private static BigDecimal podliczprzychody(Collection<? extends JPK.PKPIRWiersz> listawierszy) {
        BigDecimal przychod = BigDecimal.ZERO;
        if (listawierszy!=null) {
            for (JPK.PKPIRWiersz w : listawierszy) {
                if (w.k9!=null) {
                    przychod = przychod.add(w.k9);
                }
            }
        }
        return przychod;
    }

    static Map<String, Object> zrobwierszeinfo(List<DokKsiega> wierszeksiegi, WpisView wpisView) {
        Map<String, Object> infolista = new HashMap<>();
        infolista.put("p1", spisznaturynapoczatek(wpisView));
        infolista.put("p2", spisznaturynakoniec(wpisView));
        infolista.put("p3", kosztyuzyskanianar(wierszeksiegi));
        infolista.put("p4", dochodnar(wierszeksiegi));
        return infolista;
    }

    private static BigDecimal spisznaturynapoczatek(WpisView wpisView) {
        BigDecimal zwrot = BigDecimal.ZERO;
        String wartosc = ParametrView.zwrocParametr(wpisView.getPodatnikObiekt().getRemanent(), wpisView.getRokWpisu(), 1);
        if (!wartosc.equals("blad")) {
            zwrot = new BigDecimal(wartosc);
        } else {
            zwrot = null;
        }
        return zwrot;
    }

    private static BigDecimal spisznaturynakoniec(WpisView wpisView) {
        BigDecimal zwrot = BigDecimal.ZERO;
        String wartosc = ParametrView.zwrocParametr(wpisView.getPodatnikObiekt().getRemanent(), wpisView.getRokWpisu(), 12);
        if (!wartosc.equals("blad")) {
            zwrot = new BigDecimal(wartosc);
        } else {
            zwrot = null;
        }
        return zwrot;
    }

    private static BigDecimal kosztyuzyskanianar(List<DokKsiega> wierszeksiegi) {
        BigDecimal zwrot = BigDecimal.ZERO;
        DokKsiega ostatnialinijka = wierszeksiegi.get(wierszeksiegi.size()-1);
        Double razemkoszty = ostatnialinijka.getKolumna10()+ostatnialinijka.getKolumna11()+ostatnialinijka.getKolumna14();
        zwrot = BigDecimal.valueOf(Z.z(razemkoszty));
        return zwrot;
    }

    private static BigDecimal dochodnar(List<DokKsiega> wierszeksiegi) {
        BigDecimal zwrot = BigDecimal.ZERO;
        DokKsiega ostatnialinijka = wierszeksiegi.get(wierszeksiegi.size()-1);
        Double razemkoszty = ostatnialinijka.getKolumna10()+ostatnialinijka.getKolumna11()+ostatnialinijka.getKolumna14();
        Double razemprzychod = ostatnialinijka.getKolumna9();
        Double dochod = razemprzychod-razemkoszty;
        zwrot = BigDecimal.valueOf(Z.z(dochod));
        return zwrot;
    }
    
}
