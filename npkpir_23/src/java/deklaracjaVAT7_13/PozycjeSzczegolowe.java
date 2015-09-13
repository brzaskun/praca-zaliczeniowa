/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deklaracjaVAT7_13;

import embeddable.PozycjeSzczegoloweVAT;
import embeddable.Schema;
import embeddable.Vatpoz;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Osito
 */
public class PozycjeSzczegolowe {
    private static String PozycjeSzczegolowe;

    public PozycjeSzczegolowe(Vatpoz selected, Schema schema) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        // from Joda to JDK
        List<String> lista = new ArrayList<>();
        PozycjeSzczegoloweVAT pozycjelista = selected.getPozycjeszczegolowe();
        for(int i = 10;i<71;i++){
            Class[] noparams = {};	
            Method met = PozycjeSzczegoloweVAT.class.getDeclaredMethod("getPole"+i, noparams);
            String wynik = (String) met.invoke(pozycjelista, (Object[]) null);
            lista.add(wynik);
        }
       
        String nazwaschemy = schema.getNazwaschemy();
        switch (nazwaschemy) {
            case "M-13":
            case "K-7":
                this.schemaM13K7(lista);
                break;
            case "M-14":
            case "K-8":
                this.schemaM14K8(lista);
                break;
        }
        
    }
    
    private void schemaM13K7(List<String> lista){
        PozycjeSzczegolowe = "<PozycjeSzczegolowe>";
        pobierzSzczegolowe(lista, 20);
    }
    
    private void schemaM14K8(List<String> lista){
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
