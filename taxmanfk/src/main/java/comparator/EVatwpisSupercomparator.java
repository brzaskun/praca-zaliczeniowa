/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;


import entity.EVatwpis1;
import entity.EVatwpisSuper;
import entityfk.EVatwpisDedra;
import entityfk.EVatwpisFK;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class EVatwpisSupercomparator implements Comparator<EVatwpisSuper> {

    @Override
    public int compare(EVatwpisSuper o1, EVatwpisSuper o2) {
        String datao1 = null;
        String datao2 = null;
       if (o1 instanceof EVatwpis1) {
            datao1 = ((EVatwpis1) o1).getDataoperacji();
            datao2 = ((EVatwpis1) o2).getDataoperacji();
       } else if (o1 instanceof EVatwpisDedra) {
            datao1 = ((EVatwpisDedra) o1).getDataoperacji();
            datao2 = ((EVatwpisDedra) o2).getDataoperacji();
        } else {
            datao1 = ((EVatwpisFK) o1).getDataoperacji();
            datao2 = ((EVatwpisFK) o2).getDataoperacji();
        }
        DateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date datao1date = null;
        Date datao2date = null;
        try {
             datao1date = formatter.parse(datao1);
        } catch (ParseException ex) {
            // Logger.getLogger(Dokcomparator.class.getName()).log(Level.SEVERE, null, ex);
            return 1;
        }
        try {
            datao2date = formatter.parse(datao2);
        } catch (ParseException ex) {
            // Logger.getLogger(Dokcomparator.class.getName()).log(Level.SEVERE, null, ex);
            return 1;
        }
        return (datao1date.before(datao2date) ? -1 : (datao1date.equals(datao2date) ? 0 : 1));
    }
    
}
