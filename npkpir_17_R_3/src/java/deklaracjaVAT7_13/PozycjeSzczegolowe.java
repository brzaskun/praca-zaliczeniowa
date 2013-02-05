/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deklaracjaVAT7_13;

import embeddable.PozycjeSzczegoloweVAT;
import embeddable.Vatpoz;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Osito
 */
class PozycjeSzczegolowe {
    
    static String PozycjeSzczegolowe;

    public PozycjeSzczegolowe(Vatpoz selected) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        List<String> lista = new ArrayList<>();
        PozycjeSzczegoloweVAT pozycjelista = selected.getPozycjeszczegolowe();
        for(int i = 20;i<66;i++){
            Class[] noparams = {};	
            Method met = PozycjeSzczegoloweVAT.class.getDeclaredMethod("getPole"+i, noparams);
            Object ob = met.invoke(pozycjelista, null);
            String wynik = ob.toString();
            lista.add(wynik);
        }
        PozycjeSzczegolowe = "</ns:PozycjeSzczegolowe>";
        int i = 0;
        int j = 20;
        for(String p : lista){
            PozycjeSzczegolowe = PozycjeSzczegolowe.concat("<ns:P"+j+">"+lista.get(i)+"</ns:P_"+j+">");
            i++;
            j++;
        }
        PozycjeSzczegolowe = PozycjeSzczegolowe.concat("</ns:PozycjeSzczegolowe>");
    }
    
    public String getPozycjeSzczegolowe() {
        return PozycjeSzczegolowe;
    }
    
    
}
