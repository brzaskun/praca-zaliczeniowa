/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deklaracje.vatue.m4;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 *
 * @author Osito
 */
public class VATUEM4Bean {
    public static TNaglowek tworznaglowek(String mc, String r, String kodurzedu) {
        TNaglowek n = new TNaglowek();
        try {
            byte p = 1;
            byte p1 = 4;
            byte[] mcb = mc.getBytes("UTF-8");
            n.setCelZlozenia(p);
            n.setWariantFormularza(p1);
            TNaglowek.KodFormularza k = new TNaglowek.KodFormularza();
            k.setValue(TKodFormularza.VAT_UE);
            k.setKodSystemowy(k.getKodSystemowy());
            k.setWersjaSchemy(k.getWersjaSchemy());
            n.setKodFormularza(k);
            n.setMiesiac(mcb[0]);
            n.setRok(rok(r));
            n.setKodUrzedu(kodurzedu);
        } catch (Exception ex) {

        }
        return n;
    }
    
    public static XMLGregorianCalendar rok(String rok) throws DatatypeConfigurationException {
        String f = "yyyy";
        DateFormat format = new SimpleDateFormat(f);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(f.format(rok));
    }

   
}
