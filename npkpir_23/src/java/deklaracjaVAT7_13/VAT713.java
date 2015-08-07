/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deklaracjaVAT7_13;

import deklaracjeSchemy.SchemaVAT7;
import embeddable.Parametr;
import embeddable.Schema;
import embeddable.Vatpoz;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import javax.ejb.Singleton;
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
     private String wiersz;
     private Wstep wstep;
     private Naglowek naglowek;
     private Podmiot podmiot;
     private PozycjeSzczegolowe pozycjeSzczegolowe;
     private KwadracikiNaDole kwadracikiNaDole;
     private Pouczenie pouczenie;
     private Oswiadczenie oswiadczenie;
     private ZalacznikVATZD zalacznikVATZD;
     private DaneAutoryzujace daneAutoryzujace;
     @Inject 
     private Vatpoz selected;

    public VAT713() {
    }
     
    public VAT713(Vatpoz selected, WpisView wpisView) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.selected = selected;
        String vatokres = sprawdzjakiokresvat(wpisView);
        Schema schema = SchemaVAT7.odnajdzscheme(vatokres, wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        wstep = new Wstep(schema.getWstep());
        naglowek = new Naglowek(selected, vatokres);
        podmiot = new Podmiot(selected);
        pozycjeSzczegolowe = new PozycjeSzczegolowe(selected, schema);
        kwadracikiNaDole = new KwadracikiNaDole(selected, schema);
        oswiadczenie = new Oswiadczenie();
        daneAutoryzujace = new DaneAutoryzujace(selected);
        pouczenie = new Pouczenie(schema.getPouczenie());
        wiersz = wstep.getWestep()+naglowek.getNaglowek()+podmiot.getPodmiot()+pozycjeSzczegolowe.getPozycjeSzczegolowe()+kwadracikiNaDole.getKwadracikiNaDole()+pouczenie.getPouczenie()+oswiadczenie.getOswiadczenie()+daneAutoryzujace.getDaneAutoryzujace();
    }

    private String sprawdzjakiokresvat(WpisView wpisView) {
        Integer rok = wpisView.getRokWpisu();
        Integer mc = Integer.parseInt(wpisView.getMiesiacWpisu());
        List<Parametr> parametry = wpisView.getPodatnikObiekt().getVatokres();
        return ParametrView.zwrocParametr(parametry, rok, mc);
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