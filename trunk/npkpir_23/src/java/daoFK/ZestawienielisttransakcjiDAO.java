/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entityfk.Transakcja;
import entityfk.WierszStronafkPK;
import entityfk.Rozrachunekfk;
import entityfk.Zestawienielisttransakcji;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named
@Singleton
public class ZestawienielisttransakcjiDAO extends DAO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private SessionFacade zestawienielisttransakcjiFacade;

    public ZestawienielisttransakcjiDAO() {
        super(Zestawienielisttransakcji.class);
    }

    public ZestawienielisttransakcjiDAO(Class entityClass) {
        super(entityClass);
    }
    
    

    public List<Zestawienielisttransakcji> findAll() {
        try {
            return zestawienielisttransakcjiFacade.findAll(Zestawienielisttransakcji.class);
        } catch (Exception e) {
            return null;
        }
    }

    public Zestawienielisttransakcji findByKlucz(WierszStronafkPK klucz) {
        try {
            return zestawienielisttransakcjiFacade.findByKlucz(klucz);
        } catch (Exception e) {
            return null;
        }
    }

    public void dodajListeTransakcji(WierszStronafkPK klucz, List<Transakcja> lista) {
        try {
            Zestawienielisttransakcji nowezestawienie = new Zestawienielisttransakcji();
            //nowezestawienie.setKluczlisty(klucz);
            //nowezestawienie.setListatransakcji(lista);
            zestawienielisttransakcjiFacade.create(nowezestawienie);
        } catch (Exception e) {
        }


    }

    public void usunniezaksiegowane() {
        try {
            List<Zestawienielisttransakcji> zest = zestawienielisttransakcjiFacade.findAll(Zestawienielisttransakcji.class);
            for (Zestawienielisttransakcji p : zest) {
//                if (p.isZaksiegowanodokument() == false) {
//                    skorygujzapisywrozliczanych(p.getListatransakcji());
//                    zestawienielisttransakcjiFacade.remove(p);
//                }
            }
        } catch (Exception e) {
        }
    }

    private void skorygujzapisywrozliczanych(List<Transakcja> listatransakcji) {
        for (Transakcja p : listatransakcji) {
            WierszStronafkPK rozliczanePK = p.idSparowany();
            Rozrachunekfk dotyczyrozrachunku = zestawienielisttransakcjiFacade.findRozrachunkifkByWierszStronafk(rozliczanePK);
            double roznica = p.getKwotatransakcji();
            if (roznica != 0) {
                dotyczyrozrachunku.setRozliczono(dotyczyrozrachunku.getRozliczono() - roznica);
                dotyczyrozrachunku.setPozostalo(dotyczyrozrachunku.getKwotapierwotna() - dotyczyrozrachunku.getRozliczono());
            }
            zestawienielisttransakcjiFacade.edit(dotyczyrozrachunku);
        }
    }
}
