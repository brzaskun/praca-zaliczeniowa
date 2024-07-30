/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import entity.Dok;
import entity.Sesja;
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
public class Sesjacomparator implements Comparator<Sesja> {

    @Override
    public int compare(Sesja o1, Sesja o2) {
        Date datao1date = o1.getWylogowanie();
        Date datao2date = o2.getWylogowanie();
        return (datao1date.before(datao2date) ? 1 : (datao1date.equals(datao2date) ? 0 : -1));
    }
    
}
