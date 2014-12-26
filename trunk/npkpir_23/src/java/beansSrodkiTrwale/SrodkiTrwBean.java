/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beansSrodkiTrwale;

import com.itextpdf.text.pdf.PdfName;
import embeddable.Umorzenie;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Singleton;
import javax.inject.Named;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Named
@Singleton
public class SrodkiTrwBean implements Serializable{

    public static double sumujumorzenia(List<Umorzenie> umorzenia) {
        double kwotaumorzenia = 0.0;
        Iterator it = umorzenia.iterator();
            while (it.hasNext()) {
                Umorzenie tmp = (Umorzenie) it.next();
                kwotaumorzenia += tmp.getKwota().doubleValue();
            }
            return Z.z(kwotaumorzenia);
    }
    
}
