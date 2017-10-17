/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deklaracjaVAT7_13;

import embeddable.Vatpoz;
import entity.DeklaracjaVatSchema;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class VAT713 implements Serializable{
    private final String wiersz;

   
    public VAT713(Vatpoz vatpoz, DeklaracjaVatSchema schema, boolean cert) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String wstep = schema.getWstep();
        Naglowek naglowek = new Naglowek(vatpoz, schema);
        //Podmiot podmiot = new Podmiot(vatpoz);
        PodmiotFirma podmiotfirma = new PodmiotFirma(vatpoz);
        PozycjeSzczegolowe pozycjeSzczegolowe = new PozycjeSzczegolowe(vatpoz, schema);
        KwadracikiNaDole kwadracikiNaDole = new KwadracikiNaDole(vatpoz, schema);
        String oswiadczenie = schema.getOswiadczenie();
        String pouczenie = schema.getPouczenie();
        if (cert) {
            wiersz = wstep+naglowek.getNaglowek()+podmiotfirma.getPodmiot()+pozycjeSzczegolowe.getPozycjeSzczegolowe()+kwadracikiNaDole.getKwadracikiNaDole()+pouczenie+oswiadczenie+"</Deklaracja>";
        } else {
            DaneAutoryzujace daneAutoryzujace = new DaneAutoryzujace(vatpoz);
            wiersz = wstep+naglowek.getNaglowek()+podmiotfirma.getPodmiot()+pozycjeSzczegolowe.getPozycjeSzczegolowe()+kwadracikiNaDole.getKwadracikiNaDole()+pouczenie+oswiadczenie+daneAutoryzujace.getDaneAutoryzujace();
        }
    }
    
   
    
    
   
    
    public String getWiersz() {
        return wiersz;
    }
    
    

//    public static void main(String[] args){
//        FileWriter fileWriter = null;
//        try {
//            File newTextFile = Plik.plik("C:/uslugi/testvat1.xml");
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
//            File newTextFile = Plik.plik("C:/uslugi/testvat1.xml");
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