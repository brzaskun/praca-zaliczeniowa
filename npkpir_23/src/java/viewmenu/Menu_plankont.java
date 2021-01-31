/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewmenu;

import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author Osito
 */
@Named
@SessionScoped
public class Menu_plankont implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private boolean file1;
    private boolean file2;
    private boolean file3;
    private boolean file4;
    private boolean file5;
    private boolean file6;
    private boolean file7;
    private boolean file8;
    private String file1style;
    private String file2style;
    private String file3style;
    private String file4style;
    private String file5style;
    private String file6style;
    private String file7style;
    private String file8style;
    private final String ADRES = "/ksiegowaFK/ksiegowaFKPlanKont.xhtml?faces-redirect=true";
    private final String ALERT = "color: red;font-weight: 900;";

    public Menu_plankont() {
        file1 = true;
        file1style = ALERT;
    }
    
    

    public String ufile1(boolean war) {
        reset();
        file1 = war;
        file1style = ALERT;
        return ADRES;
    }
    
    public String ufile2(boolean war) {
        reset();
        file2 = war;
        file2style = ALERT;
        return ADRES;
    }
    
    public String ufile3(boolean war) {
        reset();
        file3 = war;
        file3style = ALERT;
        return ADRES;
    }
    
    public String ufile4(boolean war) {
        reset();
        file4 = war;
        file4style = ALERT;
        return ADRES;
    }
    public String ufile5(boolean war) {
        reset();
        file5 = war;
        file5style = ALERT;
        return ADRES;
    }
    public String ufile6(boolean war) {
        reset();
        file6 = war;
        file6style = ALERT;
        return ADRES;
    }
    
    public String ufile7(boolean war) {
        reset();
        file7 = war;
        file7style = ALERT;
        return ADRES;
    }
    
    public String ufile8(boolean war) {
        reset();
        file8 = war;
        file8style = ALERT;
        return ADRES;
    }
    
    private void reset() {
        file1 = false;
        file2 = false;
        file3 = false;
        file4 = false;
        file5 = false;
        file6 = false;
        file7 = false;
        file8 = false;
        file1style = "";
        file2style = "";
        file3style = "";
        file4style = "";
        file5style = "";
        file6style = "";
        file7style = "";
        file8style = "";
    }
    
    public boolean isFile1() {
        return file1;
    }

    public void setFile1(boolean file1) {
        this.file1 = file1;
    }

    public boolean isFile2() {
        return file2;
    }

    public void setFile2(boolean file2) {
        this.file2 = file2;
    }

    public boolean isFile3() {
        return file3;
    }

    public void setFile3(boolean file3) {
        this.file3 = file3;
    }

    public boolean isFile4() {
        return file4;
    }

    public void setFile4(boolean file4) {
        this.file4 = file4;
    }

    public boolean isFile5() {
        return file5;
    }

    public void setFile5(boolean file5) {
        this.file5 = file5;
    }

    public boolean isFile6() {
        return file6;
    }

    public void setFile6(boolean file6) {
        this.file6 = file6;
    }

    public boolean isFile7() {
        return file7;
    }

    public void setFile7(boolean file7) {
        this.file7 = file7;
    }

    public boolean isFile8() {
        return file8;
    }

    public void setFile8(boolean file8) {
        this.file8 = file8;
    }

    public String getFile1style() {
        return file1style;
    }

    public void setFile1style(String file1style) {
        this.file1style = file1style;
    }

    public String getFile2style() {
        return file2style;
    }

    public void setFile2style(String file2style) {
        this.file2style = file2style;
    }

    public String getFile3style() {
        return file3style;
    }

    public void setFile3style(String file3style) {
        this.file3style = file3style;
    }

    public String getFile4style() {
        return file4style;
    }

    public void setFile4style(String file4style) {
        this.file4style = file4style;
    }

    public String getFile5style() {
        return file5style;
    }

    public void setFile5style(String file5style) {
        this.file5style = file5style;
    }

    public String getFile6style() {
        return file6style;
    }

    public void setFile6style(String file6style) {
        this.file6style = file6style;
    }

    public String getFile7style() {
        return file7style;
    }

    public void setFile7style(String file7style) {
        this.file7style = file7style;
    }

    public String getFile8style() {
        return file8style;
    }

    public void setFile8style(String file8style) {
        this.file8style = file8style;
    }
    
    
}
