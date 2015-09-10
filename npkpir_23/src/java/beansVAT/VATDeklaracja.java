/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansVAT;

import dao.DeklaracjaVatSchemaWierszSumDAO;
import dao.DeklaracjaVatWierszSumarycznyDAO;
import data.Data;
import deklaracjapit37.DeklaracjaVAT;
import embeddable.Daneteleadresowe;
import embeddable.EVatwpisSuma;
import embeddable.Kwartaly;
import embeddable.PozycjeSzczegoloweVAT;
import embeddable.Schema;
import embeddable.SchemaEwidencjaSuma;
import entity.DeklaracjaVatSchema;
import entity.DeklaracjaVatSchemaWierszSum;
import entity.DeklaracjaVatWierszSumaryczny;
import entity.Evewidencja;
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
import javax.ejb.Stateless;
import javax.inject.Named;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Named
@Stateless
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
        for (EVatwpisSuma ew : wyciagnieteewidencje) {
            try {
                SchemaEwidencja odnalezionyWierszSchemaEwidencja = szukaniewieszaSchemy(schemaewidencjalista, ew.getEwidencja());
                String nrpolanetto = odnalezionyWierszSchemaEwidencja.getPolenetto();
                String nrpolavat = odnalezionyWierszSchemaEwidencja.getPolevat();
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
    
    public static void przyporzadkujPozycjeSzczegoloweSumaryczne(List<DeklaracjaVatSchemaWierszSum> schemawierszelista, List<DeklaracjaVatWierszSumaryczny> wierszesumaryczne, PozycjeSzczegoloweVAT pozycjeSzczegoloweVAT, Integer nowaWartoscVatZPrzeniesienia) {
        for (DeklaracjaVatWierszSumaryczny ew : wierszesumaryczne) {
            try {
                DeklaracjaVatSchemaWierszSum odnalezionyWierszSchemaEwidencja = szukaniewieszaSchemy(schemawierszelista, ew);
                String nrpolanetto = odnalezionyWierszSchemaEwidencja.getPolenetto();
                String nrpolavat = odnalezionyWierszSchemaEwidencja.getPolevat();
                String netto = String.valueOf(ew.getSumanetto());
                int nettoI = (int) Z.z0(ew.getSumanetto());
                String vat = String.valueOf(ew.getSumavat());
                int vatI = (int) Z.z0(ew.getSumavat());
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
    
    private static void ustawPozycje(PozycjeSzczegoloweVAT pozycjeSzczegoloweVAT, String nrpola, String kwotaString, int kwotaInt) {
        try {
            Class[] paramString = new Class[1];
            paramString[0] = String.class;
            Method met;
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
                Evewidencja tmp = new Evewidencja(ew.getEwidencja().getNazwa(), ew.getEwidencja().getPole(), ew.getEwidencja().getNrpolanetto(), ew.getEwidencja().getNrpolavat(), ew.getEwidencja().getRodzajzakupu(), ew.getEwidencja().getTransakcja(), ew.getEwidencja().isTylkoNetto());
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
                Evewidencja tmp = new Evewidencja(ew.getEwidencja().getNazwa(), ew.getEwidencja().getPole(), ew.getEwidencja().getNrpolanetto(), ew.getEwidencja().getNrpolavat(), ew.getEwidencja().getRodzajzakupu(), ew.getEwidencja().getTransakcja(), ew.getEwidencja().isTylkoNetto());
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

    public static void agregacjaEwidencjiZakupowych5152(ArrayList<EVatwpisSuma> ewidencjeUzupelniane) {
        Evewidencja ewidencjaSumarycznaZakupy = new Evewidencja("sumaryczna", "Nabycie towarów i usług pozostałych", "51", "52", "opodatkowane", "zakup suma", false);
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

    public static DeklaracjaVatWierszSumaryczny podsumujewidencje(ArrayList<EVatwpisSuma> pobraneewidencje, DeklaracjaVatWierszSumarycznyDAO deklaracjaVatWierszSumarycznyDAO, int przychod0srodki1koszt2) {
        DeklaracjaVatWierszSumaryczny wierszsumaryczny = null;
        if (przychod0srodki1koszt2 == 0) {
            for (EVatwpisSuma ew : pobraneewidencje) {
                if (!ew.getEwidencja().getTypewidencji().equals("z")) {
                    wierszsumaryczny = deklaracjaVatWierszSumarycznyDAO.findWiersz("Razem (suma przychodów)");
                    wierszsumaryczny.setSumanetto(wierszsumaryczny.getSumanetto()+ew.getNetto().doubleValue());
                    wierszsumaryczny.setSumavat(wierszsumaryczny.getSumavat()+ew.getVat().doubleValue());
                }
            }
        }
        if (przychod0srodki1koszt2 == 1) {
            for (EVatwpisSuma ew : pobraneewidencje) {
                if (ew.getEwidencja().getNazwa().equals("środki trwałe")) {
                    wierszsumaryczny = deklaracjaVatWierszSumarycznyDAO.findWiersz("Nabycie środków trwałych");
                    wierszsumaryczny.setSumanetto(wierszsumaryczny.getSumanetto()+ew.getNetto().doubleValue());
                    wierszsumaryczny.setSumavat(wierszsumaryczny.getSumavat()+ew.getVat().doubleValue());
                }
            }
        }
        if (przychod0srodki1koszt2 == 2) {
            for (EVatwpisSuma ew : pobraneewidencje) {
                wierszsumaryczny = deklaracjaVatWierszSumarycznyDAO.findWiersz("Nabycie towarów i usług pozostałych");
                if (ew.getEwidencja().getTypewidencji().equals("z") && !ew.getEwidencja().getNazwa().equals("środki trwałe")) {
                    wierszsumaryczny.setSumanetto(wierszsumaryczny.getSumanetto()+ew.getNetto().doubleValue());
                    wierszsumaryczny.setSumavat(wierszsumaryczny.getSumavat()+ew.getVat().doubleValue());
                }
            }
        }
        return wierszsumaryczny;
    }

    public static List<SchemaEwidencjaSuma> uzupelnijSchemyoKwoty(List<SchemaEwidencja> schemaewidencjalista, ArrayList<EVatwpisSuma> pobraneewidencje) {
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
                        SchemaEwidencjaSuma se = new SchemaEwidencjaSuma();
                        se.setSchemaEwidencja(p);
                        se.setEVatwpisSuma(s);
                        lista.add(se);
                        break;
                    }
                }
            }
        }
        return lista;
    }

  
   
}
