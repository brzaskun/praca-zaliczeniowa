/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Podatnik;
import entity.SrodekTrw;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;


/**
 *
 * @author Osito
 */
@Named
public class STRDAO extends DAO implements Serializable{
   
    @Inject
    private SessionFacade strFacade;
    //tablica wciagnieta z bazy danych
  
    public STRDAO() {
        super(SrodekTrw.class);
    }
    
   public boolean findSTR(String podatnik, Double netto, String numer){
       return strFacade.findSTR(podatnik, netto, numer);
   }
//    
//    public SrodekTrw znajdzDuplikat(SrodekTrw selD) throws Exception{
//        SrodekTrw tmp = null;
//        tmp = strFacade.duplicat(selD);
//        return tmp;
//        }
   
   public  List<SrodekTrw> findAll(){
        try {
            System.out.println("Pobieram SrodekTrwDAO");
            return strFacade.findAll(SrodekTrw.class);
        } catch (Exception e) {
            return null;
        }
   }
   
   public  List<SrodekTrw> findStrPod(String pod){
        try {
            System.out.println("Pobieram SrodekTrwDAO");
            return strFacade.findStrPod(pod);
        } catch (Exception e) {
            return null;
        }
   }
}
