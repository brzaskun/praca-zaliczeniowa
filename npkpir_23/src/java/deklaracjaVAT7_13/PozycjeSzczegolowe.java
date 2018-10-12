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
        boolean robdalej = true;
        boolean doprzeniesienia = false;
        for(String p : lista){
            try {
                boolean poleWypelnione = !p.isEmpty();
                if(poleWypelnione){
                    if (j==54 && !p.equals("0")) {
                        doprzeniesienia=true;
                    }
                    if (j==56 && !p.equals("0")) {
                        robdalej=false;
                        PozycjeSzczegolowe = PozycjeSzczegolowe.concat("<P_"+j+">"+p+"</P_"+j+">");
                        break;
                    } else if (j==56 && p.equals("0") && doprzeniesienia) {
                        PozycjeSzczegolowe = PozycjeSzczegolowe.concat("<P_"+j+">"+p+"</P_"+j+">");
                        break;
                    }
                    if (robdalej) {
                        if (j==59 && !p.equals("0")) {
                            PozycjeSzczegolowe = PozycjeSzczegolowe.concat("<P_"+j+">"+p+"</P_"+j+">");
                            PozycjeSzczegolowe = PozycjeSzczegolowe.concat("<P_70>1</P_70>");
                            PozycjeSzczegolowe = PozycjeSzczegolowe.concat("<P_69>1</P_69>");
                       } else if ((j==60 || j==61) && !p.equals("0")) {
                            PozycjeSzczegolowe = PozycjeSzczegolowe.concat("<P_"+j+">"+p+"</P_"+j+">");
                            PozycjeSzczegolowe = PozycjeSzczegolowe.concat("<P_69>1</P_69>");
                       } else {
                            PozycjeSzczegolowe = PozycjeSzczegolowe.concat("<P_"+j+">"+p+"</P_"+j+">");
                       }
                    }
                }
            } catch (Exception e){}
            j++;
        }
        
    }
    
    public String getPozycjeSzczegolowe() {
        return PozycjeSzczegolowe;
    }
    
    
}
