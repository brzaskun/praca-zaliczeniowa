/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deklaracjaVAT7_13;

import embeddable.PozycjeSzczegoloweVAT;
import embeddable.Vatpoz;
import entity.DeklaracjaVatSchema;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Osito
 */
public class PozycjeSzczegolowe {
    private static String PozycjeSzczegolowe;

    public PozycjeSzczegolowe(Vatpoz selected, DeklaracjaVatSchema schema) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        // from Joda to JDK
        List<String> lista = Collections.synchronizedList(new ArrayList<>());
        PozycjeSzczegoloweVAT pozycjelista = selected.getPozycjeszczegolowe();
        for(int i = 10;i<71;i++){
            Class[] noparams = {};	
            Method met = PozycjeSzczegoloweVAT.class.getDeclaredMethod("getPole"+i, noparams);
            String wynik = (String) met.invoke(pozycjelista, (Object[]) null);
            lista.add(wynik);
        }
        PozycjeSzczegolowe = "<PozycjeSzczegolowe>";
        String nazwaschemy = schema.getNazwaschemy();
        switch (nazwaschemy) {
            case "M-18":
            case "K-12":
                pobierzSzczegoloweM18(lista, 10);
                break;
            default:
                pobierzSzczegolowe(lista, 10);
                break;
        }
    }
    
        
    private void pobierzSzczegolowe(List<String> lista, int poczatek) {
        int j = poczatek;
        for(String p : lista){
            try {
                boolean poleWypelnione = !p.isEmpty();
                if(poleWypelnione){
                    PozycjeSzczegolowe = PozycjeSzczegolowe.concat("<P_"+j+">"+p+"</P_"+j+">");
                }
            } catch (Exception e){}
            j++;
        }
    }
    
    private void pobierzSzczegoloweM18(List<String> lista, int poczatek) {
        int j = poczatek;
        String dodajdla59 = "<P_70>1</P_70><P_69>1</P_69>";
        String dodajdla6061 = "<P_69>1</P_69>";
        boolean jest59 = false;
        boolean jest60 = false;
        boolean jest61 = false;
        for(String p : lista){
            try {
                boolean poleWypelnione = !p.isEmpty();
                if(poleWypelnione){
                    //25 dni na konto
                    if (j==59 && !p.equals("0")) {
                        jest59=true;
                    //60dni i 180 dni na konto
                    } else if (j==60 && !p.equals("0")) {
                        jest60 = true;
                    } else if (j==61 && !p.equals("0")) {
                        jest61 = true;
                    } 
                    PozycjeSzczegolowe = PozycjeSzczegolowe.concat("<P_"+j+">"+p+"</P_"+j+">");
                }
            } catch (Exception e){}
            j++;
        }
        if (jest59) {
            doklej("</P_59>",dodajdla59);
        }
        if (jest60) {
            doklej("</P_60>",dodajdla6061);
        }
        if (jest61) {
            doklej("</P_61>",dodajdla6061);
        }
        
    }
    
    public String getPozycjeSzczegolowe() {
        return PozycjeSzczegolowe;
    }

    private void doklej(String p_59, String dodajdla59) {
        int ind = PozycjeSzczegolowe.indexOf(p_59)+7;
        String pocz = PozycjeSzczegolowe.substring(0,ind);
        String koniec = PozycjeSzczegolowe.substring(ind);
        String suma = pocz+dodajdla59+koniec;
        PozycjeSzczegolowe = suma;
    }

    public static void main(String[] main) {
        String dekla = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Deklaracja xmlns=\"http://crd.gov.pl/wzor/2018/08/27/5658/\" xmlns:etd=\"http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/\"><Naglowek><KodFormularza kodSystemowy=\"VAT-7 (18)\" kodPodatku=\"VAT\" rodzajZobowiazania=\"Z\" wersjaSchemy=\"1-1E\">VAT-7</KodFormularza><WariantFormularza>18</WariantFormularza><CelZlozenia poz=\"P_7\">1</CelZlozenia><Rok>2018</Rok><Miesiac>08</Miesiac><KodUrzedu>3217</KodUrzedu></Naglowek><Podmiot1 rola=\"Podatnik\"><OsobaNiefizyczna><NIP>8513203267</NIP><PelnaNazwa>INTERIO MARINE SP. Z O.O.</PelnaNazwa></OsobaNiefizyczna></Podmiot1><PozycjeSzczegolowe><P_11>487593</P_11><P_12>487593</P_12><P_40>487593</P_40><P_41>0</P_41><P_42>247514</P_42><P_43>0</P_43><P_44>0</P_44><P_45>579091</P_45><P_46>61387</P_46><P_51>308901</P_51><P_52>0</P_52><P_54>0</P_54><P_55>0</P_55><P_56>308901</P_56><P_57>308901</P_57><P_60>308901</P_60><P_69>1</P_69><P_75>918120976</P_75><P_76>2018-10-15</P_76></PozycjeSzczegolowe><Pouczenia>1</Pouczenia></Deklaracja>";
        int ind = dekla.indexOf("</P_60>")+7;
        String pocz = dekla.substring(0,ind);
        String koniec = dekla.substring(ind);
        System.out.println(ind);
        System.out.println(pocz);
        System.out.println(koniec);
        String suma = pocz+"<lalala>"+koniec;
        System.out.println(suma);
        
    }
    
    
}
