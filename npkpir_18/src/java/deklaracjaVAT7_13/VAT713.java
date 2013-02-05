/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deklaracjaVAT7_13;

import embeddable.Vatpoz;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class VAT713 implements Serializable{
     String wiersz;
     Wstep wstep;
     Naglowek naglowek;
     Podmiot podmiot;
     PozycjeSzczegolowe pozycjeSzczegolowe;
     Pouczenie pouczenie;
     Oswiadczenie oswiadczenie;
     ZalacznikVATZD zalacznikVATZD;
     DaneAutoryzujace daneAutoryzujace;
     @Inject Vatpoz selected;

    public VAT713() {
    }
     
    public VAT713(Vatpoz selected) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.selected = selected;
        wstep = new Wstep();
        naglowek = new Naglowek(selected);
        podmiot = new Podmiot(selected);
        pouczenie = new Pouczenie();
        oswiadczenie = new Oswiadczenie();
        daneAutoryzujace = new DaneAutoryzujace(selected);
        pozycjeSzczegolowe = new PozycjeSzczegolowe(selected);
        wiersz = wstep.getWestep()+naglowek.getNaglowek()+podmiot.getPodmiot()+pozycjeSzczegolowe.getPozycjeSzczegolowe()+pouczenie.getPouczenie()+oswiadczenie.getOswiadczenie()+daneAutoryzujace.getDaneAutoryzujace();
    }

   
    public String getWiersz() {
        return wiersz;
    }
    
    

//    public static void main(String[] args){
//        FileWriter fileWriter = null;
//        try {
//            File newTextFile = new File("C:/uslugi/testvat1.xml");
//            fileWriter = new FileWriter(newTextFile);
//            fileWriter.write(wiersz);
//            fileWriter.close();
//        } catch (IOException ex) {
//            Logger.getLogger(VAT713.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                fileWriter.close();
//            } catch (IOException ex) {
//                Logger.getLogger(VAT713.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }
//    
//     final String wiersz;
//        Wstep wstep = new Wstep();
//        Naglowek naglowek = new Naglowek();
//        Podmiot podmiot = new Podmiot();
//        PozycjeSzczegolowe pozycjeSzczegolowe = new PozycjeSzczegolowe();
//        Pouczenie pouczenie = new Pouczenie();
//        Oswiadczenie oswiadczenie = new Oswiadczenie();
//        ZalacznikVATZD zalacznikVATZD = new ZalacznikVATZD();
//        DaneAutoryzujace daneAutoryzujace = new DaneAutoryzujace();
//        wiersz = wstep.getWestep()+naglowek.getNaglowek()+podmiot.getPodmiot()+pozycjeSzczegolowe.getPozycjeSzczegolowe()+pouczenie.getPouczenie()
//                +oswiadczenie.getOswiadczenie()+zalacznikVATZD.getZalacznikVATZD()+daneAutoryzujace.getDaneAutoryzujace();
//        System.out.println(wiersz);
//        FileWriter fileWriter = null;
//        try {
//            File newTextFile = new File("C:/uslugi/testvat1.xml");
//            fileWriter = new FileWriter(newTextFile);
//            fileWriter.write(wiersz);
//            fileWriter.close();
//        } catch (IOException ex) {
//            Logger.getLogger(VAT713.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                fileWriter.close();
//            } catch (IOException ex) {
//                Logger.getLogger(VAT713.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }

}