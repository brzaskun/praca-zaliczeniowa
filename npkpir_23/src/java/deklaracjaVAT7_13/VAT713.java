/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deklaracjaVAT7_13;

import data.Data;
import embeddable.Parametr;
import embeddable.Vatpoz;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import view.WpisView;

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
     
    public VAT713(Vatpoz selected, WpisView wpisView) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.selected = selected;
        String vatokres = sprawdzjakiokresvat(wpisView);
        if(vatokres.equals("miesięczne")){
            if(wpisView.getSumarokmiesiac()<2017){
                wstep = new Wstep("2013/01/17/1085/");
                pouczenie = new Pouczenie(13);
            } else {
                wstep = new Wstep("2013/04/09/1113/");
                pouczenie = new Pouczenie(14);
            }
            
        } else {
            if(wpisView.getSumarokmiesiac()<2017){
                //kwartalne do 04/2013
               wstep = new Wstep("2013/01/17/1084/");
               pouczenie = new Pouczenie(13);
            } else {
               wstep = new Wstep("2013/04/09/1114/");
               pouczenie = new Pouczenie(14);
            }
            
        }
        naglowek = new Naglowek(selected, vatokres);
        podmiot = new Podmiot(selected);
        oswiadczenie = new Oswiadczenie();
        daneAutoryzujace = new DaneAutoryzujace(selected);
        pozycjeSzczegolowe = new PozycjeSzczegolowe(selected);
        wiersz = wstep.getWestep()+naglowek.getNaglowek()+podmiot.getPodmiot()+pozycjeSzczegolowe.getPozycjeSzczegolowe()+pouczenie.getPouczenie()+oswiadczenie.getOswiadczenie()+daneAutoryzujace.getDaneAutoryzujace();
    }

    private String sprawdzjakiokresvat(WpisView wpisView) {
        Integer rok = wpisView.getRokWpisu();
        Integer mc = Integer.parseInt(wpisView.getMiesiacWpisu());
        Integer sumaszukana = rok+mc;
        List<Parametr> parametry = wpisView.getPodatnikObiekt().getVatokres();
        //odszukaj date w parametrze - kandydat na metode statyczna
      for(Parametr p : parametry){
            if(p.getRokDo()!=null&&!"".equals(p.getRokDo())){
            int wynikPo = Data.compare(rok, mc, Integer.parseInt(p.getRokOd()), Integer.parseInt(p.getMcOd()));
            int wynikPrzed = Data.compare(rok, mc, Integer.parseInt(p.getRokDo()), Integer.parseInt(p.getMcDo()));
            if(wynikPo > 1 && wynikPrzed < 0){
                return p.getParametr();
            }
            } else {
            int wynik = Data.compare(rok, mc, Integer.parseInt(p.getRokOd()), Integer.parseInt(p.getMcOd()));
            if(wynik > 0){
                return p.getParametr();
            }
            }
        }
        return "blad";
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