/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewmenu;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Osito
 */
@ManagedBean
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
    private final String ADRES = "/ksiegowaFK/ksiegowaFKPlanKont.xhtml?faces-redirect=true";

    public String ufile1(boolean war) {
        reset();
        file1 = war;
        return ADRES;
    }
    
    public String ufile2(boolean war) {
        reset();
        file2 = war;
        return ADRES;
    }
    
    public String ufile3(boolean war) {
        reset();
        file3 = war;
        return ADRES;
    }
    
    public String ufile4(boolean war) {
        reset();
        file4 = war;
        return ADRES;
    }
    public String ufile5(boolean war) {
        reset();
        file5 = war;
        return ADRES;
    }
    public String ufile6(boolean war) {
        reset();
        file6 = war;
        return ADRES;
    }
    
    public String ufile7(boolean war) {
        reset();
        file7 = war;
        return ADRES;
    }
    
    public String ufile8(boolean war) {
        reset();
        file8 = war;
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
    
    
}
