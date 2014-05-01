/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beansVAT;

import embeddable.EVatwpisSuma;
import embeddable.PozycjeSzczegoloweVAT;
import entity.Evewidencja;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Singleton;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@Singleton
public class VATDeklaracja implements Serializable{
    
    public static void przyporzadkujPozycjeSzczegolowe(List<EVatwpisSuma> wyciagnieteewidencje, PozycjeSzczegoloweVAT pozycjeSzczegoloweVAT) {
         for (EVatwpisSuma ew : wyciagnieteewidencje) {
             try {
                 String nrpolanetto = ew.getEwidencja().getNrpolanetto();
                 String nrpolavat = ew.getEwidencja().getNrpolavat();
                 String netto = String.valueOf(ew.getNetto());
                 int nettoI = Integer.parseInt(ew.getNetto().toString());
                 String vat = String.valueOf(ew.getVat().toString());
                 int vatI = Integer.parseInt(ew.getVat().toString());
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
             } catch (Exception ex) {
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
    
}
