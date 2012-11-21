/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import net.webservicex.ShowTextPosition;

/**
 *
 * @author Osito
 */
@Named
public class Fonts implements Serializable{
    private final static List<String>fonts;
    private final static List<Float>fontsize;
    private final static List<ShowTextPosition>textposition;
    
    static {
        fonts = new ArrayList<>();
        fonts.add("Arial");
        fonts.add("Courier New");
        fonts.add("Times New Roman");
        fonts.add("Verdana");
        fonts.add("Tahoma");
        fonts.add("Lcida Console");
        fonts.add("MS Sans Serif");
        fontsize = new ArrayList<>();
        fontsize.add(new Float(8));
        fontsize.add(new Float(10));
        fontsize.add(new Float(12));
        fontsize.add(new Float(14));
        fontsize.add(new Float(16));
        fontsize.add(new Float(18));
        textposition = new ArrayList<>();
        textposition.add(ShowTextPosition.BOTTOM_CENTER);
        textposition.add(ShowTextPosition.BOTTOM_LEFT);
        textposition.add(ShowTextPosition.BOTTOM_RIGHT);
        textposition.add(ShowTextPosition.TOP_CENTER);
        textposition.add(ShowTextPosition.TOP_LEFT);
        textposition.add(ShowTextPosition.TOP_RIGHT);
    }

    public List<String> getFonts() {
        return fonts;
    }

    public List<Float> getFontsize() {
        return fontsize;
    }

    public List<ShowTextPosition> getTextposition() {
        return textposition;
    }

    
    
    
    
}
