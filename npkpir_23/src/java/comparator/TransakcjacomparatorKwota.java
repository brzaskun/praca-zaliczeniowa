/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import entityfk.Dokfk;
import entityfk.Transakcja;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import waluty.Z;

/**
 *
 * @author Osito
 */
public class TransakcjacomparatorKwota  implements Comparator<Transakcja>{
    
    @Override
    public int compare(Transakcja o1, Transakcja o2) {
        double datao1 = Z.z(o1.getNowaTransakcja().getKwotaR());
        double datao2 = Z.z(o2.getNowaTransakcja().getKwotaR());
        return datao1 > datao2 ? -1 : (datao1 < datao2 ? 1 : 0);
    }

    
}
