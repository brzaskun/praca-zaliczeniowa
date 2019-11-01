/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFK;

import daoFK.DokDAOfk;
import daoFK.TabelanbpDAO;
import daoFK.WalutyDAOfk;
import daoFK.WierszBODAO;
import entity.Dok;
import entity.Klienci;
import entity.Rodzajedok;
import entityfk.Dokfk;
import entityfk.Konto;
import entityfk.StronaWiersza;
import entityfk.Tabelanbp;
import entityfk.Wiersz;
import entityfk.WierszBO;
import error.E;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.inject.Named;
import view.WpisView; import org.primefaces.PrimeFaces;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Named

public class DokFKBean {

    public static void dodajWaluteDomyslnaDoDokumentu(Tabelanbp tabelanbpPLN, Dokfk selected) {
        selected.setWalutadokumentu(tabelanbpPLN.getWaluta());
        selected.setTabelanbp(tabelanbpPLN);
        List<Wiersz> wiersze = selected.getListawierszy();
        for (Wiersz p : wiersze) {
            p.setTabelanbp(tabelanbpPLN);
        }
    }
    
    public static Tabelanbp dodajWaluteDomyslnaDoDokumentu(WalutyDAOfk walutyDAOfk, TabelanbpDAO tabelanbpDAO, Dok selected) {
        selected.setWalutadokumentu(walutyDAOfk.findWalutaBySymbolWaluty("PLN"));
        Tabelanbp tabelanbpPLN = null;
        try {
            tabelanbpPLN = tabelanbpDAO.findByTabelaPLN();
        } catch (Exception e) {
            E.e(e);
        }
        if (tabelanbpPLN == null) {
            tabelanbpDAO.dodaj(new Tabelanbp("000/A/NBP/0000", walutyDAOfk.findWalutaBySymbolWaluty("PLN"), "2012-01-01", 1.0));
            tabelanbpPLN = tabelanbpDAO.findByTabelaPLN();
        }
        return tabelanbpPLN;
    }

    public static Tabelanbp pobierzWaluteDomyslnaDoDokumentu(WalutyDAOfk walutyDAOfk, TabelanbpDAO tabelanbpDAO) {
        Tabelanbp tabelanbpPLN = null;
        try {
            tabelanbpPLN = tabelanbpDAO.findByTabelaPLN();
        } catch (Exception e) {
            E.e(e);
        }
        if (tabelanbpPLN == null) {
            tabelanbpDAO.dodaj(new Tabelanbp("000/A/NBP/0000", walutyDAOfk.findWalutaBySymbolWaluty("PLN"), "2012-01-01", 1.0));
            tabelanbpPLN = tabelanbpDAO.findByTabelaPLN();
        }
        return tabelanbpPLN;
    }
    
    
    public static Rodzajedok odnajdzZZ(List<Rodzajedok> rodzajedokKlienta) {
        for (Rodzajedok p : rodzajedokKlienta) {
            if (p.getSkrot().equals("ZZ")) {
                return p;
            }
        }
        return null;
    }

    public static int sortZaksiegowaneDok(Object o1, Object o2) {
        String datao1 = ((Dokfk) o1).getDatadokumentu();
        String datao2 = ((Dokfk) o2).getDatadokumentu();
        DateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date datao1date = null;
        Date datao2date = null;
        try {
            datao1date = formatter.parse(datao1);
            datao2date = formatter.parse(datao2);
            if (datao1date.before(datao2date)) {
                return -1;
            } else if (datao1date.after(datao2date)) {
                return 1;
            } else {
                return porownajseriedok(((Dokfk) o1), ((Dokfk) o2));
            }
        } catch (Exception e) {
            E.e(e);
            return 0;
        }
    }

    private static int porownajseriedok(Dokfk o1, Dokfk o2) {
        String seriao1 = o1.getSeriadokfk();
        String seriao2 = o2.getSeriadokfk();
        if (seriao1.equals(seriao2)) {
            return porownajnrserii(o1, o2);
        } else {
            return seriao1.compareTo(seriao2);
        }
    }

    private static int porownajnrserii(Dokfk o1, Dokfk o2) {
        int seriao1 = o1.getNrkolejnywserii();
        int seriao2 = o2.getNrkolejnywserii();
        if (seriao1 == seriao2) {
            return 0;
        } else if (seriao1 < seriao2) {
            return -1;
        } else {
            return 1;
        }
    }

    public static boolean sprawdzczyWnRownasieMa(Wiersz wierszbiezacy) {
        Konto kontoWn;
        Konto kontoMa;
        boolean czyWszystkoWprowadzono = false;
        int typ = wierszbiezacy.getTypWiersza();
        if (!wierszbiezacy.getDokfk().getRodzajedok().isJednostronny()) {
            if ((typ == 0 || typ == 5)) {
                kontoWn = wierszbiezacy.getStronaWn().getKonto();
                kontoMa = wierszbiezacy.getStronaMa().getKonto();
                double kwotaWn = Z.z(wierszbiezacy.getStronaWn().getKwota());
                double kwotaMa = Z.z(wierszbiezacy.getStronaMa().getKwota());
                if (kwotaWn == 0.0 && kwotaMa == 0.0) {
                    czyWszystkoWprowadzono = false;
                } else if (kontoWn instanceof Konto && kontoMa instanceof Konto) {
                    czyWszystkoWprowadzono = true;
                }
            } else if (typ == 7 || typ == 2) {
                kontoMa = wierszbiezacy.getStronaMa().getKonto();
                if (kontoMa instanceof Konto) {
                    czyWszystkoWprowadzono = true;
                }
            } else if (typ == 6 || typ == 1) {
                kontoWn = wierszbiezacy.getStronaWn().getKonto();
                if (kontoWn instanceof Konto) {
                    czyWszystkoWprowadzono = true;
                }
            }
        } else {
            czyWszystkoWprowadzono = true;
        }
        return czyWszystkoWprowadzono;
    }

    public static String wygenerujnumerkolejny(Dokfk selected, WpisView wpisView, DokDAOfk dokDAOfk, Klienci klient, WierszBODAO wierszBODAO) {
        String wzorzec = pobierzWzorzec(selected);
        String skrotDokfk = pobierzSkrotDokfk(selected);
        Dokfk ostatnidokument = pobierzOstatniWMc(wpisView, dokDAOfk, skrotDokfk);
        Dokfk ostatnidokumentR = pobierzOstatniWRok(wpisView, dokDAOfk, skrotDokfk);
        int numerserii = obliczostatninumer(ostatnidokumentR);
        selected.setNrkolejnywserii(numerserii);
        selected.setLp(numerserii);
        String numerwlasny = "";
        if (selected.getRodzajedok() != null) {
            if (wzorzec != null && !wzorzec.equals("")) {
                Dokfk dokfk = wzorzec.contains("N") ? ostatnidokument : ostatnidokumentR;
                if (dokfk != null) {
                    numerwlasny = oblicznumerwlasny(wzorzec, dokfk, wpisView);
                } else {
                    numerwlasny = wzorzec;
                }
            } 
           if (selected.getRodzajedok().getKategoriadokumentu() == 0 && numerserii > 1) {
               Dokfk dokfk = ostatnidokument != null ? ostatnidokument : ostatnidokumentR;
               obsluzsalda(dokfk, selected);
           } else if (selected.getRodzajedok().getKategoriadokumentu() == 0 && numerserii == 1){
               obliczsaldoBO(selected, wpisView, wierszBODAO);
           }
        }
       return numerwlasny;
    }
    
    public static String wygenerujnumerkolejnyRozrach(Dokfk selected, WpisView wpisView, DokDAOfk dokDAOfk, String seria) {
        String wzorzec = pobierzWzorzec(selected);
        String skrotDokfk = pobierzSkrotDokfk(selected);
        Dokfk ostatnidokument = pobierzOstatniWMc(wpisView, dokDAOfk, skrotDokfk);
        Dokfk ostatnidokumentR = pobierzOstatniWRok(wpisView, dokDAOfk, skrotDokfk);
        int numerserii = obliczostatninumer(ostatnidokumentR);
        selected.setNrkolejnywserii(numerserii);
        selected.setLp(numerserii);
        String numerwlasny = "";
        if (selected.getRodzajedok() != null) {
            if (wzorzec != null) {
                Dokfk dokfk = wzorzec.contains("N") ? ostatnidokument : ostatnidokumentR;
                if (dokfk != null) {
                    numerwlasny = oblicznumerwlasny(wzorzec, dokfk, wpisView);
                } else {
                    numerwlasny = "1/"+wpisView.getMiesiacWpisu()+"/"+wpisView.getRokWpisuSt()+"/"+seria;
                }
            } 
        }
       return numerwlasny;
    }

    public static String pobierzWzorzec(Dokfk selected) {
        Rodzajedok rodzajdok = selected.getRodzajedok();
        if (rodzajdok != null) {
            return rodzajdok.getWzorzec();
        } else {
            return null;
        }
    }

    private static String pobierzSkrotDokfk(Dokfk selected) {
        Rodzajedok rodzajdok = selected.getRodzajedok();
        if (rodzajdok != null) {
            return rodzajdok.getSkrot();
        } else {
            return null;
        }
    }

    private static Dokfk pobierzOstatniWMc(WpisView wpisView, DokDAOfk dokDAOfk, String skrotDokfk) {
        return dokDAOfk.findDokfkLastofaTypeMc(wpisView.getPodatnikObiekt(), skrotDokfk, wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
    }

    private static Dokfk pobierzOstatniWRok(WpisView wpisView, DokDAOfk dokDAOfk, String skrotDokfk) {
        return dokDAOfk.findDokfkLastofaType(wpisView.getPodatnikObiekt(), skrotDokfk, wpisView.getRokWpisuSt());
    }

    private static int obliczostatninumer(Dokfk ostatnidokumentR) {
        int numerserii = 1;
        if (ostatnidokumentR != null) {
            numerserii = ostatnidokumentR.getNrkolejnywserii();
            numerserii = numerserii + 1;
        }
        return numerserii;
    }

    private static String oblicznumerwlasny(String wzorzec, Dokfk ostatnidokument, WpisView wpisView) {
        String nowynumer = "";
        String separator = "/";
        String[] elementy = wzorzec.split(separator);
        String[] elementyold = ostatnidokument.getNumerwlasnydokfk().split(separator);
        try {
            for (int i = 0; i < elementy.length; i++) {
                String typ = elementy[i];
                switch (typ) {
                    case "N":
                    case "n":
                        String tmp = elementyold != null && elementyold.length > 0 ? elementyold[i] : "0";
                        Integer tmpI = Integer.parseInt(tmp);
                        tmpI++;
                        nowynumer = nowynumer.concat(tmpI.toString()).concat(separator);
                        break;
                    case "m":
                        nowynumer = nowynumer.concat(wpisView.getMiesiacWpisu()).concat(separator);
                        break;
                    case "r":
                        nowynumer = nowynumer.concat(wpisView.getRokWpisuSt()).concat(separator);
                        break;
                    //to jest wlasna wstawka typu FVZ
                    case "s":
                        nowynumer = elementyold != null && elementyold.length > 0 ? nowynumer.concat(elementyold[i]).concat(separator) : nowynumer.concat(elementy[i]).concat(separator);
                        break;
                }
            }
        } catch (Exception e) {
            nowynumer = "poprzedni nr niezgodny z wzorcem";
        }
        if (nowynumer.endsWith(separator)) {
            nowynumer = nowynumer.substring(0, nowynumer.lastIndexOf(separator));
        }
        return nowynumer;
    }

    private static void obsluzsalda(Dokfk ostatnidokument, Dokfk selected) {
        selected.setSaldopoczatkowe(ostatnidokument.getSaldokoncowe());
        selected.getListawierszy().get(0).setSaldoWBRK(ostatnidokument.getSaldokoncowe());
    }

    private static void obliczsaldoBO(Dokfk selected, WpisView wpisView, WierszBODAO wierszBODAO) {
        double saldoBO = pobierzwartosczBO(selected.getRodzajedok().getKontorozrachunkowe(), wpisView, wierszBODAO);
        selected.getListawierszy().get(0).setSaldoWBRK(saldoBO);
        selected.setSaldopoczatkowe(Z.z(saldoBO));
    }
  
    public static double pobierzwartosczBO(Konto kontorozrachunkowe, WpisView wpisView, WierszBODAO wierszBODAO) {
        List<WierszBO> wierszBOlista = wierszBODAO.findPodatnikRokKonto(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), kontorozrachunkowe);
        double kwota = 0.0;
        if (wierszBOlista != null && !wierszBOlista.isEmpty()) {
            for (WierszBO p : wierszBOlista) {
                if (p.getKwotaWn() != 0) {
                    kwota += p.getKwotaWn();
                } else {
                    kwota -= p.getKwotaMa();
                }
            }

        }
        return kwota;
    }
    
    public static double szukajpobierzwartosczBO(Konto kontorozrachunkowe, List<WierszBO> wierszBOlista) {
        double kwota = 0.0;
        if (wierszBOlista != null && !wierszBOlista.isEmpty()) {
            for (WierszBO p : wierszBOlista) {
                if (p.getKonto().equals(kontorozrachunkowe)) {
                    if (p.getKwotaWn() != 0) {
                        kwota += p.getKwotaWn();
                    } else {
                        kwota -= p.getKwotaMa();
                    }
                }
            }

        }
        return kwota;
    }


    public static void obsluzWstawKontoWBRK(StronaWiersza wybranastronawiersza, Konto kontorozrachunkowe, int lpWierszaWpisywanie) {
        String wn = "formwpisdokument:dataList:" + lpWierszaWpisywanie + ":kontown";
        String ma = "formwpisdokument:dataList:" + lpWierszaWpisywanie + ":kontoma";
        Wiersz w = wybranastronawiersza.getWiersz();
        if (w.getTypWiersza() == 0) {
            if (kontorozrachunkowe != null && wybranastronawiersza.getDrugaStrona() != null) {
                wybranastronawiersza.getDrugaStrona().setKonto(kontorozrachunkowe);
                if (wybranastronawiersza.isWn()) {
                    PrimeFaces.current().ajax().update(ma);
                } else {
                    PrimeFaces.current().ajax().update(wn);
                }
            }
        }
    }

}
