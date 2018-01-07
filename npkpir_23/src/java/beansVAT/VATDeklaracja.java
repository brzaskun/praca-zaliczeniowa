/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansVAT;

import data.Data;
import embeddable.Daneteleadresowe;
import embeddable.EVatwpisSuma;
import embeddable.PozycjeSzczegoloweVAT;
import embeddable.SchemaEwidencjaSuma;
import entity.DeklaracjaVatSchema;
import entity.DeklaracjaVatSchemaWierszSum;
import entity.DeklaracjaVatWierszSumaryczny;
import entity.Evewidencja;
import entity.Evpozycja;
import entity.Podatnik;
import entity.SchemaEwidencja;
import error.E;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.inject.Named;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Named

public class VATDeklaracja implements Serializable {

    private static final long serialVersionUID = 1L;

    public static void przyporzadkujPozycjeSzczegolowe(List<EVatwpisSuma> wyciagnieteewidencje, PozycjeSzczegoloweVAT pozycjeSzczegoloweVAT, Integer nowaWartoscVatZPrzeniesienia) {
        for (EVatwpisSuma ew : wyciagnieteewidencje) {
            try {
                String nrpolanetto = ew.getEwidencja().getNrpolanetto();
                String nrpolavat = ew.getEwidencja().getNrpolavat();
                String netto = String.valueOf(ew.getNetto().setScale(0, RoundingMode.HALF_EVEN));
                int nettoI = Integer.parseInt(ew.getNetto().setScale(0, RoundingMode.HALF_EVEN).toString());
                String vat = String.valueOf(ew.getVat().setScale(0, RoundingMode.HALF_EVEN).toString());
                int vatI = Integer.parseInt(ew.getVat().setScale(0, RoundingMode.HALF_EVEN).toString());
                ustawPozycje(pozycjeSzczegoloweVAT, nrpolanetto, netto, nettoI);
                if ((nrpolavat != null) && (!nrpolavat.isEmpty())) {
                    ustawPozycje(pozycjeSzczegoloweVAT, nrpolavat, vat, vatI);
                }
                //to jest uzywane przy korektach
                if (nowaWartoscVatZPrzeniesienia != null) {
                    pozycjeSzczegoloweVAT.setPoleI47(nowaWartoscVatZPrzeniesienia);
                    pozycjeSzczegoloweVAT.setPole47(String.valueOf(nowaWartoscVatZPrzeniesienia));
                }
            } catch (Exception ex) {
                E.e(ex);
                System.out.println("Blad VATDeklaracja przyporzadkujPozycjeSzczegolowe "+ex.getMessage());
            }
        }
    }
    
    public static void przyporzadkujPozycjeSzczegoloweNowe(List<SchemaEwidencja> schemaewidencjalista, List<EVatwpisSuma> wyciagnieteewidencje, PozycjeSzczegoloweVAT pozycjeSzczegoloweVAT, Integer nowaWartoscVatZPrzeniesienia) {
        List<EwidPoz> pozycje = new ArrayList<>();
        for (EVatwpisSuma ew : wyciagnieteewidencje) {
            SchemaEwidencja se = szukaniewieszaSchemy(schemaewidencjalista, ew.getEwidencja());
            SchemaEwidencja sm = se.getSchemamacierzysta();
            pozycje.add(new EwidPoz(se, sm, ew.getNetto(), ew.getVat(), ew.getEwidencja().isTylkoNetto()));
        }
        for (EwidPoz ew : pozycje) {
            try {
                if (ew.odnalezionyWierszSchemaEwidencja != null) {
                    String netto = String.valueOf(ew.getNetto());
                    int nettoI = Z.zUD(ew.getNetto());
                    String vat = String.valueOf(ew.getVat());
                    int vatI = Z.zUD(ew.getVat());
                    if ((ew.polenetto != null) && (!ew.polenetto.isEmpty()) && nettoI != 0.0) {
                        ustawPozycje(pozycjeSzczegoloweVAT, ew.polenetto, netto, nettoI);
                    }
                    if (!ew.isTylkonetto()) {
                        if ((ew.polevat != null) && (!ew.polevat.isEmpty()) && (nettoI != 0.0 || vatI != 0.0)) {
                            ustawPozycje(pozycjeSzczegoloweVAT, ew.polevat, vat, vatI);
                        }
                    }
                    //to jest uzywane przy korektach
                    if (nowaWartoscVatZPrzeniesienia != null) {
                        pozycjeSzczegoloweVAT.setPoleI47(nowaWartoscVatZPrzeniesienia);
                        pozycjeSzczegoloweVAT.setPole47(String.valueOf(nowaWartoscVatZPrzeniesienia));
                    }
                } else {
                    System.out.println("Ewidencja nie jest podczepiona pod schemat "+ew.toString());
                }
            } catch (Exception ex) {
                E.e(ex);
                System.out.println("Blad VATDeklaracja przyporzadkujPozycjeSzczegolowe "+ew.toString());
            }
        }
    }
    
    public static void przyporzadkujPozycjeSzczegoloweSumaryczne(List<DeklaracjaVatSchemaWierszSum> schemawierszelista, PozycjeSzczegoloweVAT pozycjeSzczegoloweVAT, Integer nowaWartoscVatZPrzeniesienia) {
        for (DeklaracjaVatSchemaWierszSum ew : schemawierszelista) {
            try {
                DeklaracjaVatWierszSumaryczny ws = ew.getDeklaracjaVatWierszSumaryczny();
                String nrpolanetto = ew.getPolenetto();
                String nrpolavat = ew.getPolevat();
                String netto = String.valueOf(ws.getSumanetto());
                int nettoI = (int) Z.z0(ws.getSumanetto());
                String vat = String.valueOf(ws.getSumavat());
                int vatI = (int) Z.z0(ws.getSumavat());
                if ((nrpolanetto != null) && (!nrpolanetto.isEmpty()) && ew.getNetto1vat2() != 2) {
                    ustawPozycjeSumaryczne(pozycjeSzczegoloweVAT, nrpolanetto, netto, nettoI);
                }
                if ((nrpolavat != null) && (!nrpolavat.isEmpty()) && ew.getNetto1vat2() != 1) {
                    ustawPozycjeSumaryczne(pozycjeSzczegoloweVAT, nrpolavat, vat, vatI);
                }
                //to jest uzywane przy korektach
                if (nowaWartoscVatZPrzeniesienia != null) {
                    pozycjeSzczegoloweVAT.setPoleI47(nowaWartoscVatZPrzeniesienia);
                    pozycjeSzczegoloweVAT.setPole47(String.valueOf(nowaWartoscVatZPrzeniesienia));
                }
            } catch (Exception ex) {
                E.e(ex);
                System.out.println("Blad VATDeklaracja przyporzadkujPozycjeSzczegolowe "+ex.getMessage());
            }
        }
    }
    
    private static void ustawPozycje(PozycjeSzczegoloweVAT pozycjeSzczegoloweVAT, String nrpola, String kwotaString, int kwotaInt) {
        try {
            Class[] paramString = new Class[1];
            paramString[0] = String.class;
            Method met;
            met = PozycjeSzczegoloweVAT.class.getDeclaredMethod("getPoleI" + nrpola);
            Integer pobranakwota = (Integer) met.invoke(pozycjeSzczegoloweVAT);
            kwotaInt = kwotaInt + pobranakwota;
            kwotaString = String.valueOf(kwotaInt);
            met = PozycjeSzczegoloweVAT.class.getDeclaredMethod("setPole" + nrpola, paramString);
            met.invoke(pozycjeSzczegoloweVAT, new String(kwotaString));
            paramString = new Class[1];
            paramString[0] = Integer.class;
            met = PozycjeSzczegoloweVAT.class.getDeclaredMethod("setPoleI" + nrpola, paramString);
            met.invoke(pozycjeSzczegoloweVAT, new Integer(kwotaInt));
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            E.e(e);
        }
    }
    
    private static void ustawPozycjeSumaryczne(PozycjeSzczegoloweVAT pozycjeSzczegoloweVAT, String nrpola, String kwotaString, int kwotaInt) {
        try {
            Class[] paramString = new Class[1];
            paramString[0] = String.class;
            Method met;
            kwotaString = String.valueOf(kwotaInt);
            met = PozycjeSzczegoloweVAT.class.getDeclaredMethod("setPole" + nrpola, paramString);
            met.invoke(pozycjeSzczegoloweVAT, new String(kwotaString));
            paramString = new Class[1];
            paramString[0] = Integer.class;
            met = PozycjeSzczegoloweVAT.class.getDeclaredMethod("setPoleI" + nrpola, paramString);
            met.invoke(pozycjeSzczegoloweVAT, new Integer(kwotaInt));
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            E.e(e);
        }
    }
    
    
        
    private static SchemaEwidencja szukaniewieszaSchemy(List<SchemaEwidencja> schemaewidencjalista, Evewidencja evewidencja) {
        SchemaEwidencja s = null;
        for (SchemaEwidencja p : schemaewidencjalista) {
            if (p.getEvewidencja().equals(evewidencja)) {
                s = p;
            }
        }
        return s;
    }
    
    private static DeklaracjaVatSchemaWierszSum szukaniewieszaSchemy(List<DeklaracjaVatSchemaWierszSum> schemawierszelista, DeklaracjaVatWierszSumaryczny deklaracjaVatWierszSumaryczny) {
        DeklaracjaVatSchemaWierszSum s = null;
        for (DeklaracjaVatSchemaWierszSum p : schemawierszelista) {
            if (p.getDeklaracjaVatWierszSumaryczny().equals(deklaracjaVatWierszSumaryczny)) {
                s = p;
            }
        }
        return s;
    }

    public static void duplikujZapisyDlaTransakcji(ArrayList<EVatwpisSuma> ewidencjeDoPrzegladu) {
        ArrayList<EVatwpisSuma> ewidencjeUzupelniane = new ArrayList<>();
        for (Iterator<EVatwpisSuma> it = ewidencjeDoPrzegladu.iterator(); it.hasNext();) {
            EVatwpisSuma ew = (EVatwpisSuma) it.next();
            //dodaje wartosci ujete pierwotnie jako przychod, drugi raz jako koszt
            if (ew.getEwidencja().getNazwa().equals("import usług") || ew.getEwidencja().getNazwa().equals("rejestr WNT") || ew.getEwidencja().getNazwa().equals("odwrotne obciążenie")) {
                EVatwpisSuma suma = new EVatwpisSuma(ew.getEwidencja(), ew.getNetto(), ew.getVat(), ew.getEstawka());
                //pobieram i kopiuje stara ewidencje
                Evewidencja tmp = new Evewidencja(ew.getEwidencja().getNazwa(), ew.getEwidencja().getNazwapola(), ew.getEwidencja().getNrpolanetto(), ew.getEwidencja().getNrpolavat(), ew.getEwidencja().getRodzajzakupu(), ew.getEwidencja().getTransakcja(), ew.getEwidencja().isTylkoNetto());
                //wpisuje pola zakupu
                tmp.setNrpolanetto("51");
                tmp.setNrpolavat("52");
                //zachowuje ewidecje do tymczasowej sumy
                suma.setEwidencja(tmp);
                //dodaje tymczasowa sume do calosci 
                ewidencjeUzupelniane.add(suma);
            }
            if (ew.getEwidencja().getNazwa().equals("import usług")) {
                EVatwpisSuma suma = new EVatwpisSuma(ew.getEwidencja(), ew.getNetto(), ew.getVat(), ew.getEstawka());
                //pobieram i kopiuje stara ewidencje
                Evewidencja tmp = new Evewidencja(ew.getEwidencja().getNazwa(), ew.getEwidencja().getNazwapola(), ew.getEwidencja().getNrpolanetto(), ew.getEwidencja().getNrpolavat(), ew.getEwidencja().getRodzajzakupu(), ew.getEwidencja().getTransakcja(), ew.getEwidencja().isTylkoNetto());
                //wpisuje pola zakupu
                tmp.setNrpolanetto("39");
                tmp.setNrpolavat("40");
                //zachowuje ewidecje do tymczasowej sumy
                suma.setEwidencja(tmp);
                //dodaje tymczasowa sume do calosci 
                ewidencjeUzupelniane.add(suma);
            }
        }
        ewidencjeDoPrzegladu.addAll(ewidencjeUzupelniane);
    }

    public static void agregacjaEwidencjiZakupowych5152(ArrayList<EVatwpisSuma> ewidencjeUzupelniane, Evpozycja evpozycjanabycie) {
        Evewidencja ewidencjaSumarycznaZakupy = new Evewidencja("sumaryczna", evpozycjanabycie, "51", "52", "opodatkowane", "zakup suma", false);
        EVatwpisSuma zakupyVatwpis = new EVatwpisSuma(ewidencjaSumarycznaZakupy, BigDecimal.ZERO, BigDecimal.ZERO, "");
        for (Iterator<EVatwpisSuma> it = ewidencjeUzupelniane.iterator(); it.hasNext();) {
            EVatwpisSuma ew = it.next();
            if (ew.getEwidencja().getNrpolanetto().equals("51")) {
                zakupyVatwpis.setNetto(zakupyVatwpis.getNetto().add(ew.getNetto()));
                zakupyVatwpis.setVat(zakupyVatwpis.getVat().add(ew.getVat()));
                //usuwam te ewidencje ktore podsumowalem
                it.remove();
            }

        }
        ewidencjeUzupelniane.add(zakupyVatwpis);
    }

    public static Daneteleadresowe uzupelnijAdres(Podatnik pod) {
        Daneteleadresowe adres = new Daneteleadresowe();
        adres.setNIP(pod.getNip());
        adres.setImiePierwsze(pod.getImie().toUpperCase());
        adres.setNazwisko(pod.getNazwisko().toUpperCase());
        adres.setDataUrodzenia(pod.getDataurodzenia());
        adres.setWojewodztwo(pod.getWojewodztwo().toUpperCase());
        adres.setPowiat(pod.getPowiat().toUpperCase());
        adres.setGmina(pod.getGmina().toUpperCase());
        adres.setUlica(pod.getUlica().toUpperCase());
        adres.setNrDomu(pod.getNrdomu());
        adres.setNrLokalu(pod.getNrlokalu());
        adres.setMiejscowosc(pod.getMiejscowosc().toUpperCase());
        adres.setKodPocztowy(pod.getKodpocztowy());
        adres.setPoczta(pod.getPoczta().toUpperCase());
        return adres;
    }

    public static void podsumujSzczegolowe(PozycjeSzczegoloweVAT pozycjeSzczegoloweVAT) {
//        if (pole43zreki == true) {
//            pozycjeSzczegoloweVAT.setPoleI43(Integer.parseInt(pozycjeSzczegoloweVAT.getPole43()));
//        }
        PozycjeSzczegoloweVAT p = pozycjeSzczegoloweVAT;//podsumowanie pol szsczegolowych z pobranych czastkowych
        p.setPoleI45(p.getPoleI20() + p.getPoleI21() + p.getPoleI23() + p.getPoleI25() + p.getPoleI27() + p.getPoleI29() + p.getPoleI31() + p.getPoleI32() + p.getPoleI33() + p.getPoleI35() + p.getPoleI37() + p.getPoleI41());
        p.setPole45(String.valueOf(p.getPoleI45()));
        p.setPoleI46(p.getPoleI26() + p.getPoleI28() + p.getPoleI30() + p.getPoleI34() + p.getPoleI36() + p.getPoleI38() + p.getPoleI42() + p.getPoleI43() + p.getPoleI44());
        p.setPole46(String.valueOf(p.getPoleI46()));
//        if (pole47zreki == true) {
//            p.setPoleI47(Integer.parseInt(p.getPole47()));
//        }
        p.setPoleI55(p.getPoleI47() + p.getPoleI48() + p.getPoleI50() + p.getPoleI52() + p.getPoleI53() + p.getPoleI54());
        p.setPole55(String.valueOf(p.getPoleI55()));
        Integer dozaplaty = p.getPoleI46() - p.getPoleI55();
        boolean pokaz56lub59;
        if (dozaplaty < 0) {
            pokaz56lub59 = true;
        }
        //to jets gupie bo kwota na kasy powinna byc jakos inaczej wstawiana to jest caly temat do zrobienia
//        if (pole56zreki == true) {
//            Integer kwota = Integer.parseInt(p.getPole56());
//            if (dozaplaty > kwota) {
//                p.setPoleI56(kwota);
//            } else {
//                p.setPoleI56(dozaplaty);
//                p.setPole56(dozaplaty.toString());
//            }
//        }
        p.setPole57("0");
        p.setPoleI57(0);
        Integer roznica = p.getPoleI46() - p.getPoleI55() - p.getPoleI56() - p.getPoleI57();
        if (roznica > 0) {
            p.setPoleI58(roznica);
            p.setPole58(roznica.toString());
        } else {
            p.setPole58("0");
            p.setPoleI58(0);
        }
        Integer dozwrotu = p.getPoleI55() - p.getPoleI46();
//        if (pole59zreki == true) {
//            p.setPoleI59(Integer.parseInt(p.getPole59()));
//        }

        roznica = p.getPoleI55() - p.getPoleI46() + p.getPoleI59();
        if (dozwrotu > 0) {
            p.setPoleI60(roznica);
            p.setPole60(roznica.toString());
        } else {
            p.setPole60("0");
            p.setPoleI60(0);
        }
        if (!"".equals(p.getPole61())) {
            p.setPoleI61(Integer.parseInt(p.getPole61()));
            if (p.getPoleI61() > p.getPoleI60()) {
                p.setPoleI61(p.getPoleI60());
                p.setPole61(p.getPoleI61().toString());
            }
        } else {
            p.setPole61("0");
            p.setPoleI61(0);
        }
        if (!"".equals(p.getPole62())) {
            p.setPoleI62(Integer.parseInt(p.getPole62()));
            if (p.getPoleI62() > p.getPoleI61()) {
                p.setPoleI62(p.getPoleI61());
                p.setPole62(p.getPoleI62().toString());
            }
        }
        if (!"".equals(p.getPole63())) {
            p.setPoleI63(Integer.parseInt(p.getPole63()));
            if (p.getPoleI63() > p.getPoleI61()) {
                p.setPoleI63(p.getPoleI61());
                p.setPole63(p.getPoleI63().toString());
            }
        }
        if (!"".equals(p.getPole64())) {
            p.setPoleI64(Integer.parseInt(p.getPole64()));
            if (p.getPoleI64() > p.getPoleI61()) {
                p.setPoleI64(p.getPoleI61());
                p.setPole64(p.getPoleI64().toString());
            }
        }
        roznica = p.getPoleI60() - p.getPoleI61();
        p.setPoleI65(roznica);
        p.setPole65(roznica.toString());
        pozycjeSzczegoloweVAT = p;
    }
    
     public static DeklaracjaVatSchema odnajdzscheme(String okres, String rok, String mckw, List<DeklaracjaVatSchema> schemydoprzeszukania) {
        DeklaracjaVatSchema pasujaca = null;
        if (okres.equals("miesięczne")) {
            for (DeklaracjaVatSchema p : schemydoprzeszukania) {
                if (p.isMc0kw1() == false) {
                    int wynik = Data.compare(rok, mckw, p.getRokOd(), p.getMcOd());
                    if (wynik > -1) {
                        pasujaca = p;
                    }
                }
            }
        } else {
            for (DeklaracjaVatSchema p : schemydoprzeszukania) {
                if (p.isMc0kw1() == true) {
                    int wynik = Data.compare(rok, mckw, p.getRokOd(), p.getMcOd());
                    if (wynik > -1) {
                        pasujaca = p;
                    }
                }
            }
        }
        return pasujaca;
    }

    public static void podsumujewidencje(List<SchemaEwidencja> schemaewidencjalista, ArrayList<EVatwpisSuma> pobraneewidencje, DeklaracjaVatSchemaWierszSum p) {
        DeklaracjaVatWierszSumaryczny wierszsumaryczny = p.getDeklaracjaVatWierszSumaryczny();
        int n = 0;
        int v = 0;
            for (EVatwpisSuma ew : pobraneewidencje) {
                if (!ew.getEwidencja().getTypewidencji().equals("z") && !ew.isNiesumuj()) {
                    if (wierszsumaryczny.getNazwapozycji().equals("Razem (suma przychodów)")) {
                        n += Z.zUD(ew.getNetto().doubleValue());
                        v += Z.zUD(ew.getVat().doubleValue());
                    }
                }
                if (ew.getEwidencja().getNazwa().equals("środki trwałe")) {
                    if (wierszsumaryczny.getNazwapozycji().equals("Nabycie środków trwałych")) {
                        n += Z.zUD(ew.getNetto().doubleValue());
                        v += Z.zUD(ew.getVat().doubleValue());
                    }
                    if (wierszsumaryczny.getNazwapozycji().equals("Razem kwota podatku naliczonego do odliczenia")) {
                        n += Z.zUD(ew.getNetto().doubleValue());
                        v += Z.zUD(ew.getVat().doubleValue());
                    }
                }
                 if (ew.getEwidencja().getTypewidencji().equals("z") && !ew.getEwidencja().getNazwa().equals("środki trwałe")) {
                    if (wierszsumaryczny.getNazwapozycji().equals("Nabycie towarów i usług pozostałych")) {
                        n += Z.zUD(ew.getNetto().doubleValue());
                        v += Z.zUD(ew.getVat().doubleValue());
                    }
                    if (wierszsumaryczny.getNazwapozycji().equals("Razem kwota podatku naliczonego do odliczenia")) {
                        n += Z.zUD(ew.getNetto().doubleValue());
                        v += Z.zUD(ew.getVat().doubleValue());
                    }
                }
            }
            wierszsumaryczny.setSumanetto(n);
            wierszsumaryczny.setSumavat(v);
    }
    
    public static void podsumujewidencjeKoszty(ArrayList<EVatwpisSuma> pobraneewidencje, DeklaracjaVatSchemaWierszSum p) {
        DeklaracjaVatWierszSumaryczny wierszsumaryczny = p.getDeklaracjaVatWierszSumaryczny();
            for (EVatwpisSuma ew : pobraneewidencje) {
                if (ew.getEwidencja().getNazwa().equals("środki trwałe")) {
                    if (wierszsumaryczny.getNazwapozycji().equals("Nabycie środków trwałych")) {
                        wierszsumaryczny.setSumanetto(Z.zUD( wierszsumaryczny.getSumanetto()+ew.getNetto().intValue()));
                        wierszsumaryczny.setSumavat(Z.zUD( wierszsumaryczny.getSumavat()+ew.getVat().intValue()));
                    }
                    if (wierszsumaryczny.getNazwapozycji().equals("Razem kwota podatku naliczonego do odliczenia")) {
                        wierszsumaryczny.setSumanetto(Z.zUD( wierszsumaryczny.getSumanetto()+ew.getNetto().intValue()));
                        wierszsumaryczny.setSumavat(Z.zUD( wierszsumaryczny.getSumavat()+ew.getVat().intValue()));
                    }
                }
                 if (ew.getEwidencja().getTypewidencji().equals("z") && !ew.getEwidencja().getNazwa().equals("środki trwałe")) {
                    if (wierszsumaryczny.getNazwapozycji().equals("Nabycie towarów i usług pozostałych")) {
                        wierszsumaryczny.setSumanetto(Z.zUD( wierszsumaryczny.getSumanetto()+ew.getNetto().intValue()));
                        wierszsumaryczny.setSumavat(Z.zUD( wierszsumaryczny.getSumavat()+ew.getVat().intValue()));
                    }
                    if (wierszsumaryczny.getNazwapozycji().equals("Razem kwota podatku naliczonego do odliczenia")) {
                        wierszsumaryczny.setSumanetto(Z.zUD( wierszsumaryczny.getSumanetto()+ew.getNetto().intValue()));
                        wierszsumaryczny.setSumavat(Z.zUD( wierszsumaryczny.getSumavat()+ew.getVat().intValue()));
                    }
                }
            }
    }

    public static List<SchemaEwidencjaSuma> wyluskajiPrzyporzadkujSprzedaz(List<SchemaEwidencja> schemaewidencjalista, ArrayList<EVatwpisSuma> pobraneewidencje) {
        List<SchemaEwidencjaSuma> lista = new ArrayList<>();
        for (Iterator<SchemaEwidencja> it = schemaewidencjalista.iterator(); it.hasNext();) {
            SchemaEwidencja p = it.next();
            if (p.getEvewidencja().getNazwa().equals("sprzedaż 23%")) {
                System.out.println("s");
            }
            if(!p.getCzescdeklaracji().equals("C")) {
                it.remove();
            } else {
                for (EVatwpisSuma s : pobraneewidencje) {
                    if (s.getEwidencja().equals(p.getEvewidencja())) {
                        lista.add(tworznowa(p, s));
                        if (p.getSchemamacierzysta() != null) {
                        }
                        break;
                    }
                }
            }
        }
        return lista;
    }
    
    public static DeklaracjaVatSchemaWierszSum pobierzschemawiersz(List<DeklaracjaVatSchemaWierszSum> schemawierszsumarycznylista, String opis) {
        DeklaracjaVatSchemaWierszSum wiersz = null;
        for (DeklaracjaVatSchemaWierszSum p : schemawierszsumarycznylista) {
            if (p.getDeklaracjaVatWierszSumaryczny().getNazwapozycji().equals(opis)) {
                wiersz = p;
            }
        }
        return wiersz;
    }

  
   public static void main(String[] args) {
       double i = 1+0.49499;
       //double j = Z.zUD(i);
       System.out.println("i "+i);
       //System.out.println("j "+j);
   }

//    private static SchemaEwidencjaSuma niezawiera(List<SchemaEwidencjaSuma> lista, SchemaEwidencja s) {
//        SchemaEwidencjaSuma zwrot = tworznowa(s, s);
//        for (SchemaEwidencjaSuma p : lista) {
//            if (p.getSchemaEwidencja().equals(s)) {
//
//            }
//        }
//        return zwrot;
//    }

    private static SchemaEwidencjaSuma tworznowa (SchemaEwidencja p, EVatwpisSuma s) {
        SchemaEwidencjaSuma se = new SchemaEwidencjaSuma();
        se.setSchemaEwidencja(p);
        se.setEVatwpisSuma(s);
        return se;
    }
    
}
