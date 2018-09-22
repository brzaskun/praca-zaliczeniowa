/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import embeddable.Kwartaly;
import entity.EVatwpis1;
import entity.Podatnik;
import entityfk.EVatwpisFK;
import error.E;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
public class EVatwpisFKDAO  extends DAO implements Serializable {

    @Inject
    private SessionFacade sessionFacade;

    public  List<EVatwpisFK> findAll(){
        try {
            return sessionFacade.findAll(EVatwpisFK.class);
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
   }

    public List<EVatwpisFK> zwrocBiezacegoKlientaRokMc(Podatnik podatnikWpisu, String rokWpisuSt, String miesiacWpisu) {
        return sessionFacade.zwrocEVatwpisFKKlientRokMc(podatnikWpisu, rokWpisuSt, miesiacWpisu);
    }

    public List<EVatwpisFK> zwrocBiezacegoKlientaRokKW(Podatnik podatnikWpisu, String rokWpisuSt, String miesiacWpisu) {
        List<String> mce = Kwartaly.mctoMcewKw(miesiacWpisu);
        return sessionFacade.zwrocEVatwpisFKKlientRokKw(podatnikWpisu, rokWpisuSt, mce);
    }
}
