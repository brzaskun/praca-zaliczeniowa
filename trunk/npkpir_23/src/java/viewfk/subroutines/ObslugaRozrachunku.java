/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewfk.subroutines;

import daoFK.RozrachunekfkDAO;
import entityfk.Rozrachunekfk;
import java.util.List;
import javax.ejb.Singleton;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@Singleton
public class ObslugaRozrachunku {
    
    public static void utrwalNoweRozachunki(List<Rozrachunekfk> pobierznowododane, RozrachunekfkDAO rozrachunekfkDAO) {
            for (Rozrachunekfk p : pobierznowododane) {
                p.setZaksiegowanodokument(true);
                rozrachunekfkDAO.edit(p);
            }
    }
    
}
