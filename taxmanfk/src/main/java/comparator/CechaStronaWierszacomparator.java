/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import entity.Dok;
import error.E;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import javax.inject.Named;
import viewfk.CechyzapisuPrzegladView;

/**
 *
 * @author Osito
 */
@Named
public class CechaStronaWierszacomparator implements Comparator<CechyzapisuPrzegladView.CechaStronaWiersza> {

    @Override
    public int compare(CechyzapisuPrzegladView.CechaStronaWiersza o1, CechyzapisuPrzegladView.CechaStronaWiersza o2) {
        String datao1 = o1.getStronaWiersza().getDokfk().getDatawystawienia();
        String datao2 = o2.getStronaWiersza().getDokfk().getDatawystawienia();
        DateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date datao1date = null;
        Date datao2date = null;
        try {
             datao1date = formatter.parse(datao1);
        } catch (ParseException ex) {
            E.e(ex);
        }
        try {
            datao2date = formatter.parse(datao2);
        } catch (ParseException ex) {
            E.e(ex);
        }
        return (datao1date.before(datao2date) ? -1 : (datao1date.equals(datao2date) ? 0 : 1));
    }
    
}
