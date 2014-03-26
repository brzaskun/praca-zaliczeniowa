/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import embeddablefk.Transakcja;
import embeddablefk.WierszStronafkPK;
import entityfk.Rozrachunekfk;
import entityfk.Zestawienielisttransakcji;
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
public class ZestawienielisttransakcjiDAO extends DAO implements Serializable {

    @Inject
    private SessionFacade zestawienielisttransakcjiFacade;

    public ZestawienielisttransakcjiDAO() {
        super(Zestawienielisttransakcji.class);
    }

    public List<Zestawienielisttransakcji> findAll() {
        try {
            System.out.println("Pobieram ZestawienielisttransakcjiDAO");
            return zestawienielisttransakcjiFacade.findAll(Zestawienielisttransakcji.class);
        } catch (Exception e) {
            return null;
        }
    }

    public Zestawienielisttransakcji findByKlucz(WierszStronafkPK klucz) {
        try {
            System.out.println("Pobieram ZestawienielisttransakcjiDAO by klucz");
            return zestawienielisttransakcjiFacade.findByKlucz(klucz);
        } catch (Exception e) {
            return null;
        }
    }

    public void dodajListeTransakcji(WierszStronafkPK klucz, List<Transakcja> lista) {
        try {
            System.out.println("Zachowuje ZestawienielisttransakcjiDAO");
            Zestawienielisttransakcji nowezestawienie = new Zestawienielisttransakcji();
            nowezestawienie.setKluczlisty(klucz);
            nowezestawienie.setListatransakcji(lista);
            zestawienielisttransakcjiFacade.create(nowezestawienie);
        } catch (Exception e) {
        }


    }

    public void usunniezaksiegowane() {
        try {
            List<Zestawienielisttransakcji> zest = zestawienielisttransakcjiFacade.findAll(Zestawienielisttransakcji.class);
            for (Zestawienielisttransakcji p : zest) {
                if (p.isZaksiegowanodokument() == false) {
                    skorygujzapisywrozliczanych(p.getListatransakcji());
                    zestawienielisttransakcjiFacade.remove(p);
                }
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
