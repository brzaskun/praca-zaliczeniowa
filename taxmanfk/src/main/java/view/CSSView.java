/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;


/**
 *
 * @author Osito
 */
@Named(value="CSSView")
@SessionScoped
public class CSSView implements Serializable {
    private static final long serialVersionUID = 1L;
    private String css;
    private List<String> cssList;

    public CSSView() {
        css = "css/style.css";
        cssList  = new ArrayList<String>();
        cssList.add("css/style.css");
        cssList.add("css/bigstyle.css");
    }

    public String getCss() {
        return css;
    }

    public void setCss(String css) {
        this.css = css;
    }

    public List<String> getCssList() {
        return cssList;
    }

    public void setCssList(List<String> cssList) {
        this.cssList = cssList;
    }
    
    public void checkCSS(){
        String c = css;
        c = css;
    }
   
    
}
