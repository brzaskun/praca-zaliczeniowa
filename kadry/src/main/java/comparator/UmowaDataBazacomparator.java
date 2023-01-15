/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import entity.Umowa;
import java.util.Comparator;
import java.util.Date;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class UmowaDataBazacomparator implements Comparator<Umowa> {

    //najstarsza jest pierwsza
    @Override
    public int compare(Umowa o1, Umowa o2) {
        Date datao1 = o1.getDatasystem();
        Date datao2 = o2.getDatasystem();
        
        return (datao1.before(datao2) ? 1 : (datao1.equals(datao2) ? 0 : -1));
    }
    
}
