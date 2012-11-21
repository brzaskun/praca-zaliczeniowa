/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import net.webservicex.BarcodeOption;
import net.webservicex.CheckSumMethod;
import net.webservicex.ImageFormats;

/**
 *
 * @author Osito
 */
@Named
public class BarcodeOptionS implements Serializable{
    private final static List<BarcodeOption>bco;
    private final static List<CheckSumMethod>csm;
    private final static List<ImageFormats>image;
    
    static {
        bco = new ArrayList<>();
        bco.add(BarcodeOption.BOTH);
        bco.add(BarcodeOption.CODE);
        bco.add(BarcodeOption.NONE);
        bco.add(BarcodeOption.TYP);
        csm = new ArrayList<>();
        csm.add(CheckSumMethod.NONE);
        csm.add(CheckSumMethod.MODULO_10);
        image = new ArrayList<>();
        image.add(ImageFormats.BMP);
        image.add(ImageFormats.EMF);
        image.add(ImageFormats.EXIF);
        image.add(ImageFormats.GIF);
        image.add(ImageFormats.ICON);
        image.add(ImageFormats.JPEG);
        image.add(ImageFormats.MEMORY_BMP);
        image.add(ImageFormats.PNG);
        image.add(ImageFormats.TIFF);
        image.add(ImageFormats.WMF);
    }

    public List<BarcodeOption> getBco() {
        return bco;
    }

    public List<CheckSumMethod> getCsm() {
        return csm;
    }

    public List<ImageFormats> getImage() {
        return image;
    }

  
    
    
}
