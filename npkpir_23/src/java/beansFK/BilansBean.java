/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFK;

import dao.KlienciDAO;
import dao.KontoDAOfk;
import dao.KontopozycjaZapisDAO;
import dao.PozycjaRZiSDAO;
import dao.RodzajedokDAO;
import dao.TabelanbpDAO;
import dao.UkladBRDAO;
import dao.WalutyDAOfk;
import embeddable.Mce;
import entity.Klienci;
import entity.Podatnik;
import entity.Rodzajedok;
import entityfk.Dokfk;
import entityfk.Konto;
import entityfk.PozycjaRZiS;
import entityfk.PozycjaRZiSBilans;
import entityfk.StronaWiersza;
import entityfk.Tabelanbp;
import entityfk.UkladBR;
import entityfk.Waluty;
import entityfk.Wiersz;
import error.E;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.DoubleAccumulator;
import msg.Msg;
import view.WpisView;
import waluty.Z;
/**
 *
 * @author Osito
 */
public class BilansBean {
    
    
    public static void zmianaukladprzegladRZiSBO(UkladBR uklad, UkladBRDAO ukladBRDAO, WpisView wpisView, KontoDAOfk kontoDAO, KontopozycjaZapisDAO kontopozycjaZapisDAO, PozycjaRZiSDAO pozycjaRZiSDAO) {
        UkladBRBean.ustawAktywny(uklad, ukladBRDAO);
        wyczyscKonta("wynikowe", wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), kontoDAO);
        PozycjaRZiSFKBean.naniesZachowanePozycjeNaKonta(kontoDAO,kontopozycjaZapisDAO, uklad, "wynikowe");
    }
    
    public static List<PozycjaRZiSBilans> pobierzPoszerzPozycje(UkladBR ukladBR, PozycjaRZiSDAO pozycjaRZiSDAO, String granica) {
        List<PozycjaRZiSBilans> pozycje = Collections.synchronizedList(new ArrayList<>());
        try {
            pozycje.addAll(pozycjaRZiSDAO.findRzisuklad(ukladBR));
            if (pozycje.isEmpty()) {
               pozycje.add(new PozycjaRZiS(1, "A", "A", null, 0, "Kliknij tutaj i dodaj pierwszą pozycję", false));
                Msg.msg("i", "Dodaje pusta pozycje");
            }
            for (Iterator<PozycjaRZiSBilans> it = pozycje.iterator(); it.hasNext();) {
                    PozycjaRZiS p = (PozycjaRZiS) it.next();
                    p.setPrzyporzadkowanestronywiersza(null);
                    p.setMce(new HashMap<String,Double>());
                    for (String r : Mce.getMceListS()) {
                        p.getMce().put(r, 0.0);
                    }
                }
        } catch (Exception e) {  
            E.e(e);
        }
        return pozycje;
    }
    
    private static void wyczyscKonta(String rb, Podatnik podatnik, String rok, KontoDAOfk kontoDAO) {
        if (rb.equals("wynikowe")) {
            List<Konto> listakont = kontoDAO.findWszystkieKontaWynikowePodatnika(podatnik, rok);
            UkladBRBean.czyscPozycjeKont(kontoDAO, listakont);
        } else {
            List<Konto> listakont = kontoDAO.findWszystkieKontaBilansowePodatnika(podatnik, rok);
            UkladBRBean.czyscPozycjeKont(kontoDAO, listakont);
        }
    }
    
    public static double[] rozliczzapisy(List<StronaWiersza> strony) {
        DoubleAccumulator obrotywn = new DoubleAccumulator(Double::sum, 0.d);
        DoubleAccumulator obrotyma = new DoubleAccumulator(Double::sum, 0.d);
        DoubleAccumulator obrotywnwaluta = new DoubleAccumulator(Double::sum, 0.d);
        DoubleAccumulator obrotymawaluta = new DoubleAccumulator(Double::sum, 0.d);
        strony.forEach(p -> {
            if (p.isWn()) {
                obrotywn.accumulate(Z.z(p.getKwotaPLN()));
                if (!p.getSymbolWalutBOiSW().equals("PLN")) {
                    obrotywnwaluta.accumulate(Z.z(p.getKwota()));
                }
            } else {
                obrotyma.accumulate(Z.z(p.getKwotaPLN()));
                if (!p.getSymbolWalutBOiSW().equals("PLN")) {
                    obrotymawaluta.accumulate(Z.z(p.getKwota()));
                }
            }
        });
        double saldo = obrotywn.doubleValue() - obrotyma.doubleValue();
        double saldowaluta = obrotywnwaluta.doubleValue() - obrotymawaluta.doubleValue();
        double kurs = 0.0;
        if (saldowaluta != 0.0) {
            kurs = Math.abs(saldo / saldowaluta);
        }
        double[] sumy = new double[3];
        sumy[0] = Z.z(saldo);
        sumy[1] = Z.z(saldowaluta);
        sumy[2] = Z.z(kurs);
        return sumy;
    }
    
    public static Dokfk stworznowydokument(int nrkolejny, List<StronaWiersza> zachowaneWiersze, String seriadokumentu, WpisView wpisView, 
            KlienciDAO klienciDAO, RodzajedokDAO rodzajedokDAO, TabelanbpDAO tabelanbpDAO, WalutyDAOfk walutyDAOfk, boolean pokaztransakcje) {
        Dokfk nd = new Dokfk(nrkolejny, wpisView.getRokWpisuSt());
        ustawdaty(nd, wpisView, seriadokumentu);
        ustawkontrahenta(nd, wpisView, klienciDAO);
        ustawnumerwlasny(nd, nrkolejny, seriadokumentu, wpisView);
        if (seriadokumentu.equals("BO")) {
            nd.setOpisdokfk("bilans otwarcia roku: " + wpisView.getRokWpisuSt());
        } else if (seriadokumentu.equals("BZ")) {
            nd.setOpisdokfk("bilans zamknięcia roku: " + wpisView.getRokWpisuSt());
        } else {
            nd.setOpisdokfk("obroty rozpoczęcia na koniec: " + wpisView.getRokWpisuSt()+"/"+wpisView.getMiesiacWpisu());
        }
        nd.setPodatnikObj(wpisView.getPodatnikObiekt());
        nd.setWprowadzil(wpisView.getUzer().getLogin());
        ustawrodzajedok(nd, seriadokumentu, wpisView, rodzajedokDAO);
        ustawtabelenbp(nd, tabelanbpDAO, walutyDAOfk);
        if (seriadokumentu.equals("BZ")) {
            ustawwierszeBZ(nd, zachowaneWiersze, wpisView, seriadokumentu, pokaztransakcje);
        } else {
            ustawwiersze(nd, zachowaneWiersze, wpisView, seriadokumentu);
        }
        return nd;
    }
    
    public static Dokfk edytujnowydokument(Dokfk nd, List<StronaWiersza> zachowaneWiersze, String seriadokumentu, WpisView wpisView, boolean pokaztransakcje) {
        if (seriadokumentu.equals("BZ")) {
            usunwiersze(nd, zachowaneWiersze, wpisView, seriadokumentu);
            ustawwierszeBZ(nd, zachowaneWiersze, wpisView, seriadokumentu, pokaztransakcje);
        } else {
            ustawwiersze(nd, zachowaneWiersze, wpisView, seriadokumentu);
        }
        return nd;
    }
    
     private static void ustawdaty(Dokfk nd, WpisView wpisView, String seriadokumentu) {
        String datadokumentu = wpisView.getRokWpisuSt() + "-" + wpisView.getMiesiacWpisu() + "-01";
        if (seriadokumentu.equals("BZ")) {
            datadokumentu = wpisView.getRokWpisuSt() + "-" + wpisView.getMiesiacWpisu() + "-31";
        }
        nd.setDatadokumentu(datadokumentu);
        nd.setDataoperacji(datadokumentu);
        nd.setDatawplywu(datadokumentu);
        nd.setDatawystawienia(datadokumentu);
        nd.setDataujecia(new Date());
        nd.setMiesiac(wpisView.getMiesiacWpisu());
        nd.setVatM(wpisView.getMiesiacWpisu());
        nd.setVatR(wpisView.getRokWpisuSt());
    }

    private static void ustawkontrahenta(Dokfk nd, WpisView wpisView, KlienciDAO klienciDAO) {
        try {
            Klienci k = klienciDAO.findKlientByNip(wpisView.getPodatnikObiekt().getNip());
            nd.setKontr(k);
        } catch (Exception e) {

        }
    }
    
    
    private static void ustawnumerwlasny(Dokfk nd, int nrkolejny, String seriadokumentu, WpisView wpisView) {
        String numer = nrkolejny+"/" + wpisView.getRokWpisuSt() + "/" + seriadokumentu;
        nd.setNumerwlasnydokfk(numer);
    }

    private static void ustawrodzajedok(Dokfk nd, String typ, WpisView wpisView, RodzajedokDAO rodzajedokDAO) {
        Rodzajedok rodzajedok = rodzajedokDAO.find(typ, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        if (rodzajedok != null) {
            nd.setSeriadokfk(rodzajedok.getSkrot());
            nd.setRodzajedok(rodzajedok);
        } else {
            Msg.msg("e", "Brak zdefiniowanego dokumentu BO/BOR");
        }
    }

    private static void ustawtabelenbp(Dokfk nd, TabelanbpDAO tabelanbpDAO, WalutyDAOfk walutyDAOfk) {
        Tabelanbp t = tabelanbpDAO.findByTabelaPLN();
        nd.setTabelanbp(t);
        Waluty w = walutyDAOfk.findWalutaBySymbolWaluty("PLN");
        nd.setWalutadokumentu(w);
    }
    
    public static void ustawwiersze(Dokfk nd, List<StronaWiersza> listabiezaca, WpisView wpisView, String seriadokumentu) {
        nd.setListawierszy(new ArrayList<Wiersz>());
        int idporzadkowy = 1;
        if (listabiezaca != null && listabiezaca.size() > 0) {
            for (StronaWiersza p : listabiezaca) {
                //bo sa takie co sa zapisane w bazie i sa generowane jako saldo i te nie maja id
                boolean warunek = true;
                if (seriadokumentu.equals("BO")) {
                    warunek = p.getWiersz().getIdwiersza()==null;
                }
                //przetwarzamy tylko wiersze ktorych nie ma w bazie
                boolean sakwotynakontach = p.getKwotaWn() != 0.0 || p.getKwotaMa() != 0.0 || p.getKwotaPLN() != 0.0 || p.getKwotaPLN() != 0.0;
                if (p != null && warunek && sakwotynakontach) {
                    Wiersz wierszstary = p.getWiersz();
                    Wiersz w = new Wiersz();
                    Tabelanbp tabela = wierszstary!=null?wierszstary.getTabelanbp():p.getTabelanbp();
                    w.setTabelanbp(tabela);
                    if (p.getWnma().equals("Wn")) {
                        w.setStronaWn(p);
                    } else {
                        w.setStronaMa(p);
                    }
                    uzupelnijwiersz(w, nd, idporzadkowy++);
                    
                    String opiswiersza = "";
                    String opiswstepny = p.getWiersz()!=null?p.getWiersz().getOpisWiersza():p.getKonto().getPelnynumer()+" saldo na koniec "+nd.getRok();
                    if (opiswstepny!=null||opiswiersza.isBlank()) {
                        opiswstepny = opiswstepny.replace("zapis BO ", "");
                        opiswstepny = opiswstepny.replace("zapis BO: ", "");
                        opiswstepny = opiswstepny.replace("kwota obrotów: ", "");
                        opiswstepny = opiswstepny.replace("zapis BZ: ", "");
                    }
                    if (seriadokumentu.equals("BO")) {
                        opiswiersza = "zapis BO: " + opiswstepny;
                    } else if (seriadokumentu.equals("BOR")) {
                        opiswiersza = "kwota obrotów: " + opiswstepny;
                    } else if (seriadokumentu.equals("BZ")) {
                        opiswiersza = "zapis BZ: " + opiswstepny;
                    }
                    w.setOpisWiersza(opiswiersza);
                    nd.getListawierszy().add(w);
                }
            }
        }
    }
    
    public static void ustawwierszeBZ(Dokfk nd, List<StronaWiersza> listabiezaca, WpisView wpisView, String seriadokumentu, boolean pokaztransakcje) {
        int idporzadkowy = 1;
        if (nd.getListawierszy()==null) {
            nd.setListawierszy(new ArrayList<Wiersz>());
        }
        if (listabiezaca != null && listabiezaca.size() > 0) {
            for (StronaWiersza starastrona : listabiezaca) {
//                boolean warunek = p.getWiersz()!=null?p.getWiersz().getIdwiersza()==null:true;
//                if (seriadokumentu.equals("BZ")) {
//                    warunek = p.getWiersz()!=null?p.getWiersz().getIdwiersza()!=null:false;
//                }
                StronaWiersza p = new StronaWiersza(starastrona);
                if (pokaztransakcje) {
                    double kwotawaluta = starastrona.getRozliczono();
                    double kwotapoln = starastrona.getRozliczono();
                    if (p.isNowatransakcja()) {
                        kwotawaluta = starastrona.getPozostalo();
                        kwotapoln = starastrona.getPozostaloPLN();
                    }
                    p.setKwota(kwotawaluta);
                    p.setKwotaWaluta(kwotawaluta);
                    p.setKwotaPLN(kwotapoln);
                }
                p.setBilanszamkniecia(true);
                //przetwarzamy tylko wiersze ktorych nie ma w bazie
                if (p != null && (p.getKwotaWn() != 0.0 || p.getKwotaMa() != 0.0 || p.getKwotaPLN() != 0.0 || p.getKwotaPLN() != 0.0)) {
                    Wiersz wierszstary = starastrona.getWiersz();
                    Wiersz nowywiersz = new Wiersz();
                    if (wierszstary!=null) {
                        nowywiersz.setTabelanbp(wierszstary.getTabelanbp());
                    } else {
                        nowywiersz.setTabelanbp(p.getTabelanbp());
                    }
                    if (p.getWnma().equals("Wn")) {
                        nowywiersz.setStronaWn(p);
                    } else {
                        nowywiersz.setStronaMa(p);
                    }
                    uzupelnijwiersz(nowywiersz, nd, idporzadkowy++);
                    String opiswiersza = "";
                    String opiswstepny = p.getWiersz()!=null?p.getWiersz().getOpisWiersza():"";
                    if (opiswstepny!=null) {
                        opiswstepny = opiswstepny.replace("zapis BO ", "");
                        opiswstepny = opiswstepny.replace("zapis BO: ", "");
                        opiswstepny = opiswstepny.replace("kwota obrotów: ", "");
                        opiswstepny = opiswstepny.replace("zapis BZ: ", "");
                    }
                    if (seriadokumentu.equals("BO")) {
                        opiswiersza = "zapis BO: " + opiswstepny;
                    } else if (seriadokumentu.equals("BOR")) {
                        opiswiersza = "kwota obrotów: " + opiswstepny;
                    } else if (seriadokumentu.equals("BZ")) {
                        opiswiersza = "zapis BZ: " + opiswstepny;
                    }
                    nowywiersz.setOpisWiersza(opiswiersza);
                    p.setWiersz(nowywiersz);
                    nd.getListawierszy().add(nowywiersz);
                }
            }
        }
    }
    
    public static void usunwiersze(Dokfk nd, List<StronaWiersza> listabiezaca, WpisView wpisView, String seriadokumentu) {
        int idporzadkowy = 1;
        if (listabiezaca != null && listabiezaca.size() > 0) {
            boolean sazmiany=  false;
            for (StronaWiersza p : listabiezaca) {
                //nie moze byc bo jak jest lista sald to nie generuje
//                boolean warunek = p.getWiersz()!=null?p.getWiersz().getIdwiersza()==null:true;
//                if (seriadokumentu.equals("BZ")) {
//                    warunek = p.getWiersz()!=null?p.getWiersz().getIdwiersza()!=null:false;
//                }
                //przetwarzamy tylko wiersze ktorych nie ma w bazie
                if (p != null && (p.getKwotaWn() != 0.0 || p.getKwotaMa() != 0.0 || p.getKwotaPLN() != 0.0 || p.getKwotaPLN() != 0.0)) {
                    List<Wiersz> listawierszy = nd.getListawierszy();
                    for (Iterator<Wiersz> it = listawierszy.iterator(); it.hasNext();) {
                        Wiersz w = it.next();
                        StronaWiersza stronaWn = w.getStronaWn();
                        StronaWiersza stronaMa = w.getStronaMa();
                        if (stronaWn!=null&&stronaWn.getKonto().equals(p.getKonto())) {
                            it.remove();
                            sazmiany = true;
                        } else if (stronaMa!=null&&stronaMa.getKonto().equals(p.getKonto())) {
                            it.remove();
                            sazmiany = true;
                        }
                    }
                }
            }
            if (sazmiany) {
                int i = 1;
                List<Wiersz> listawierszy = nd.getListawierszy();
                for (Iterator<Wiersz> it = listawierszy.iterator(); it.hasNext();) {
                    Wiersz w = it.next();
                    w.setIdporzadkowy(i);
                    i = i+1;
                }
            }
        }
    }

      private static void uzupelnijwiersz(Wiersz w, Dokfk nd, int idporzadkowy) {
        w.setIdporzadkowy(idporzadkowy);
        w.setTypWiersza(0);
        w.setDokfk(nd);
        w.setLpmacierzystego(0);
        w.setTabelanbp(w.getTabelanbp());
        w.setDataksiegowania(nd.getDatawplywu());
    }
}
