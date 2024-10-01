/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beansResponsive;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class ResponsiveBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private boolean mobile;

    @PostConstruct
    private void init() {
        checkScreenWidth();
    }
    
    public void checkScreenWidth() {
        // Sprawdza szerokość ekranu przez JavaScript i ustawia odpowiednio
        String userAgent = FacesContext.getCurrentInstance().getExternalContext().getRequestHeaderMap().get("User-Agent");
        mobile = userAgent != null && (userAgent.contains("Mobi") || userAgent.contains("Android"));
    }

    public boolean isMobile() {
        return mobile;
    }

    public void setMobile(boolean mobile) {
        this.mobile = mobile;
    }

}
