/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deklaracjaVAT7_13;

import java.util.Collection;
import java.util.List;
import javax.faces.bean.ManagedProperty;
import view.Vat7DKView;

/**
 *
 * @author Osito
 */
class PozycjeSzczegolowe {
    
    private List<String> listapozycji;
    @ManagedProperty(value="#{Vat7DKView}")
    private Vat7DKView vat7DKView;
    
    static String PozycjeSzczegolowe;

    public PozycjeSzczegolowe() {
        listapozycji.addAll((Collection) vat7DKView.getLista());
        PozycjeSzczegolowe = "</ns:PozycjeSzczegolowe>";
        int i = 0;
        int j = 20;
        for(String p : listapozycji){
            PozycjeSzczegolowe = PozycjeSzczegolowe.concat("<ns:P"+j+listapozycji.get(i)+"</ns:P_"+j+">");
        }
        PozycjeSzczegolowe = PozycjeSzczegolowe.concat("</ns:PozycjeSzczegolowe>");
    }
    
    public String getPozycjeSzczegolowe() {
        return PozycjeSzczegolowe;
    }
    
    
}
