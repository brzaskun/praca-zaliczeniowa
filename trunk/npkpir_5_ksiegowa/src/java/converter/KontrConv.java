/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import dao.KontrDAO;
import entity.Kontr;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author School
 */
public class KontrConv  implements Converter{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
        Kontr kontr = new Kontr("8765463212","22-087","Szczecin","Minala","Min","Pokonia 34");
        System.out.println("utworzono nowegokontrahenta"+kontr.toString());
        KontrDAO kontrDAO = new KontrDAO();
        kontr = kontrDAO.getKontrObject(value);
        return kontr;
        } catch (Exception e){
            System.out.println("Blad konvertera "+e.toString());
        return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        KontrDAO kontrDAO = new KontrDAO();
        String wyraz = kontrDAO.getKontrString((Kontr) value);
        return wyraz;
    }

   
    
    
}
