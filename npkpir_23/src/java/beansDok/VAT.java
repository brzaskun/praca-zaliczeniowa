/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beansDok;

import data.Data;
import entity.Dok;
import entity.Evewidencja;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Osito
 */
@Named

public class VAT {
    
    public static void zweryfikujokresvat(Dok selDokument) {
        if (selDokument.getVatR()==null || selDokument.getVatM()==null) {
            String datafaktury = selDokument.getDataWyst();
            String dataobowiazku = selDokument.getDataSprz();
            int porownaniedat = Data.compare(datafaktury, dataobowiazku);
            String rok;
            String mc;
            if (porownaniedat >= 0) {
                rok = dataobowiazku.substring(0,4);
                mc = dataobowiazku.substring(5,7);
            } else {
                rok = datafaktury.substring(0,4);
                mc = datafaktury.substring(5,7);
            }
            selDokument.setVatR(rok);
            selDokument.setVatM(mc);
            RequestContext.getCurrentInstance().update("dodWiad:ostatnipanel");
        }
    }
    
     public static List<String> getNazwy(List<Evewidencja> ewidencje) {
        List<String> nazwyewidencji = new ArrayList<>();
        for (Evewidencja p : ewidencje) {
            nazwyewidencji.add(p.getNazwa());
        }
        return nazwyewidencji;
    }
     
     
}
