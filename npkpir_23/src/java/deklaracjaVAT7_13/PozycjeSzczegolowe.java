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
        pobierzSzczegolowe(lista, 10);
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
    
    public String getPozycjeSzczegolowe() {
        return PozycjeSzczegolowe;
    }
    
    
}
