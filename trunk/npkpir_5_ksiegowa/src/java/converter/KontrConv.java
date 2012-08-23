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
        Kontr kontr = new Kontr();
        KontrDAO kontrDAO = new KontrDAO();
        kontr = kontrDAO.getKontrObject(value);
        return kontr;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        KontrDAO kontrDAO = new KontrDAO();
        String wyraz = "8765463212";
        return wyraz;
    }

      
    
}
