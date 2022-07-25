/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import entityfk.Transakcja;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Osito
 */
public class Transakcjacomparator  implements Comparator<Transakcja>{
    
    @Override
    public int compare(Transakcja o1, Transakcja o2) {
        String datao1 = o1.getNowaTransakcja().getDokfk().getDatawystawienia();
        String datao2 = o2.getNowaTransakcja().getDokfk().getDatawystawienia();
        DateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date datao1date = null;
        Date datao2date = null;
        try {
             datao1date = formatter.parse(datao1);
        } catch (ParseException ex) {
            // Logger.getLogger(Transakcjacomparator.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            datao2date = formatter.parse(datao2);
        } catch (ParseException ex) {
            // Logger.getLogger(Transakcjacomparator.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (datao1date.before(datao2date)) {
            return -1; 
        } else if (datao1date.equals(datao2date)) {
            return porownajwiersze(o1.getNowaTransakcja().getWiersz().getIdporzadkowy(), o2.getNowaTransakcja().getWiersz().getIdporzadkowy());
        } else {
            return 1;
        }
    }

    private int porownajwiersze(Integer idporzadkowy, Integer idporzadkowy0) {
        return idporzadkowy < idporzadkowy0 ? -1 : idporzadkowy == idporzadkowy0 ? 0 : 1;
    }
    
}
