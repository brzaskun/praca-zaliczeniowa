/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansVAT;

import embeddable.Daneteleadresowe;
import embeddable.EVatwpisSuma;
import embeddable.PozycjeSzczegoloweVAT;
import entity.Evewidencja;
import entity.Podatnik;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Singleton;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@Singleton
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
                Class[] paramString = new Class[1];
                paramString[0] = String.class;
                Method met;
                met = PozycjeSzczegoloweVAT.class.getDeclaredMethod("setPole" + nrpolanetto, paramString);
                met.invoke(pozycjeSzczegoloweVAT, new String(netto));
                paramString = new Class[1];
                paramString[0] = Integer.class;
                try {
                    met = PozycjeSzczegoloweVAT.class.getDeclaredMethod("setPoleI" + nrpolanetto, paramString);
                    met.invoke(pozycjeSzczegoloweVAT, new Integer(nettoI));
                } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                }
                if ((nrpolavat != null) && (!nrpolavat.isEmpty())) {
                    paramString = new Class[1];
                    paramString[0] = String.class;
                    met = PozycjeSzczegoloweVAT.class.getDeclaredMethod("setPole" + nrpolavat, paramString);
                    met.invoke(pozycjeSzczegoloweVAT, new String(vat));
                    paramString = new Class[1];
                    paramString[0] = Integer.class;
                    try {
                        met = PozycjeSzczegoloweVAT.class.getDeclaredMethod("setPoleI" + nrpolavat, paramString);
                        met.invoke(pozycjeSzczegoloweVAT, new Integer(vatI));
                    } catch (Exception e) {
                    }
                }
                if (nowaWartoscVatZPrzeniesienia != null) {
                    pozycjeSzczegoloweVAT.setPoleI47(nowaWartoscVatZPrzeniesienia);
                    pozycjeSzczegoloweVAT.setPole47(String.valueOf(nowaWartoscVatZPrzeniesienia));
                }
            } catch (Exception ex) {
                System.out.println("Blad VATDeklaracja przyporzadkujPozycjeSzczegolowe "+ex.getMessage());
            }
        }
    }

    public static void duplikujZapisyDlaTransakcji(ArrayList<EVatwpisSuma> ewidencjeUzupelniane, ArrayList<EVatwpisSuma> ewidencjeDoPrzegladu) {
        for (EVatwpisSuma ew : ewidencjeDoPrzegladu) {
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
}
