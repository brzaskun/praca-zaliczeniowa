/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansDok;

import beansFK.TabelaNBPBean;
import dao.DokDAO;
import daoFK.TabelanbpDAO;
import entity.Dok;
import entity.Podatnik;
import entityfk.Tabelanbp;
import org.joda.time.DateTime;
import waluty.Z;

/**
 *
 * @author Osito
 */
public class BeansJPK {

    public static Double przeliczpln(double vat, Tabelanbp innatabela) {
        if (innatabela != null) {
            return Z.z(vat * innatabela.getKurssredniPrzelicznik());
        } else {
            return vat;
        }
    }

    public static Dok sprawdzCzyNieDuplikat(Dok selD, Podatnik podatnik, DokDAO dokDAO) {
        Dok tmp = null;
        tmp = dokDAO.znajdzDuplikatwtrakcie(selD, podatnik, selD.getRodzajedok().getSkrot());
        return tmp;
    }

    public static Tabelanbp pobierztabele(String waldok, String dataWyst, TabelanbpDAO tabelanbpDAO) {
        DateTime dzienposzukiwany = new DateTime(dataWyst);
        return TabelaNBPBean.pobierzTabeleNBP(dzienposzukiwany, tabelanbpDAO, waldok);
    }
    
}
