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
            String wynik = (String) met.invoke(pozycjelista, null);
            lista.add(wynik);
        }
        PozycjeSzczegolowe = "<PozycjeSzczegolowe>";
        int j = 20;
        for(String p : lista){
            try {
                boolean i = !p.isEmpty();
                if(!p.equals("")){
                    PozycjeSzczegolowe = PozycjeSzczegolowe.concat("<P_"+j+">"+p+"</P_"+j+">");
                }
            } catch (Exception e){}
            j++;
        }
        PozycjeSzczegolowe = PozycjeSzczegolowe.concat("</PozycjeSzczegolowe>");
    }
    
    public String getPozycjeSzczegolowe() {
        return PozycjeSzczegolowe;
    }
    
    
}
