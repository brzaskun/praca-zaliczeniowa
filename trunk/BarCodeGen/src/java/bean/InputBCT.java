/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import net.webservicex.BarcodeType;

/**
 *
 * @author Osito
 */
@Named
public class InputBCT implements Serializable{
    private static List<BarcodeType> lista;
    
    @PostConstruct
    private void init(){
        lista = new ArrayList<>();
        lista.add(BarcodeType.CODE_128_A);
        lista.add(BarcodeType.CODE_128_B);
        lista.add(BarcodeType.CODE_128_C);
        lista.add(BarcodeType.CODE_2_5_INDUSTRIAL);
        lista.add(BarcodeType.CODE_2_5_INTERLEAVED);
        lista.add(BarcodeType.CODE_2_5_MATRIX);
        lista.add(BarcodeType.CODE_39);
        lista.add(BarcodeType.CODE_39_EXTENDED);
        lista.add(BarcodeType.CODE_93);
        lista.add(BarcodeType.CODE_93_EXTENDED);
        lista.add(BarcodeType.CODE_CODABAR);
        lista.add(BarcodeType.CODE_EAN_128_A);
        lista.add(BarcodeType.CODE_EAN_128_B);
        lista.add(BarcodeType.CODE_EAN_128_C);
        lista.add(BarcodeType.CODE_EAN_13);
        lista.add(BarcodeType.CODE_EAN_8);
        lista.add(BarcodeType.CODE_MSI);
        lista.add(BarcodeType.CODE_POST_NET);
        lista.add(BarcodeType.CODE_UPC_A);
        lista.add(BarcodeType.CODE_UPC_E_0);
        lista.add(BarcodeType.CODE_UPC_E_1);
        lista.add(BarcodeType.CODE_UPC_SUPP_2);
        lista.add(BarcodeType.CODE_UPC_SUPP_5);
                
    }

    public List<BarcodeType> getLista() {
        return lista;
    }
    
    
}
