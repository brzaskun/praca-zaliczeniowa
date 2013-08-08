/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deklaracjaVAT7_13;

import embeddable.PozycjeSzczegoloweVAT;
import embeddable.Vatpoz;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.joda.time.DateTime;

/**
 *
 * @author Osito
 */
class PozycjeSzczegolowe {
    Integer Rok;
    Integer Miesiac;
    static String PozycjeSzczegolowe;

    public PozycjeSzczegolowe(Vatpoz selected) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Rok = Integer.parseInt(selected.getRok());
        Miesiac = Integer.parseInt(selected.getMiesiac());
        // from Joda to JDK
        Date date = Calendar.getInstance().getTime();
        DateFormat formatt = new SimpleDateFormat("yyyy-MM-dd");
        String today = formatt.format(date);
        List<String> lista = new ArrayList<>();
        PozycjeSzczegoloweVAT pozycjelista = selected.getPozycjeszczegolowe();
        for(int i = 20;i<66;i++){
            Class[] noparams = {};	
            Method met = PozycjeSzczegoloweVAT.class.getDeclaredMethod("getPole"+i, noparams);
            String wynik = (String) met.invoke(pozycjelista, null);
            lista.add(wynik);
        }
        if(Rok>2012&&Miesiac<4){
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
        } else {
        PozycjeSzczegolowe = "<PozycjeSzczegolowe>";
        int j = 10;
        for(String p : lista){
            try {
                boolean i = !p.isEmpty();
                if(!p.equals("")){
                    PozycjeSzczegolowe = PozycjeSzczegolowe.concat("<P_"+j+">"+p+"</P_"+j+">");
                }
            } catch (Exception e){}
            j++;
        }
        if(pozycjelista.getPoleI51()>0){
            PozycjeSzczegolowe = PozycjeSzczegolowe.concat("<P_61>1</P_61>");
        }
        PozycjeSzczegolowe = PozycjeSzczegolowe.concat("<P_69>"+today+"</P_69>");
        PozycjeSzczegolowe = PozycjeSzczegolowe.concat("</PozycjeSzczegolowe>");
        }
        
    }
    
    public String getPozycjeSzczegolowe() {
        return PozycjeSzczegolowe;
    }
    
    
}
