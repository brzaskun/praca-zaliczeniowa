/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deklaracjaVAT7_13;

import embeddable.Parametr;
import embeddable.Vatpoz;
import entity.DeklaracjaVatSchema;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import view.ParametrView;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Named
public class VAT713 implements Serializable{
    private final String wiersz;

   
    public VAT713(Vatpoz selected, DeklaracjaVatSchema schema) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String wstep = schema.getWstep();
        Naglowek naglowek = new Naglowek(selected, schema);
        Podmiot podmiot = new Podmiot(selected);
        PozycjeSzczegolowe pozycjeSzczegolowe = new PozycjeSzczegolowe(selected, schema);
        KwadracikiNaDole kwadracikiNaDole = new KwadracikiNaDole(selected, schema);
        String oswiadczenie = schema.getOswiadczenie();
        DaneAutoryzujace daneAutoryzujace = new DaneAutoryzujace(selected);
        Pouczenie pouczenie = new Pouczenie(schema.getPouczenie());
        wiersz = wstep+naglowek.getNaglowek()+podmiot.getPodmiot()+pozycjeSzczegolowe.getPozycjeSzczegolowe()+kwadracikiNaDole.getKwadracikiNaDole()+pouczenie.getPouczenie()+oswiadczenie+daneAutoryzujace.getDaneAutoryzujace();
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