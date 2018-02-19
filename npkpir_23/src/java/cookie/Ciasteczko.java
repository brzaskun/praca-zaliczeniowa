/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cookie;

import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScoped
public class Ciasteczko {
    
    public void ciastko() {
        PrintWriter pw = null;
        try {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            Cookie[] cookies = request.getCookies();
            Cookie cookie = new Cookie("program_ks_haslo","mkyong dot com");
            cookie.setPath("/");
            cookie.setMaxAge(60*60); //1 hour
            response.addCookie(cookie);
            //pw.println("Cookies created");
        } catch (Exception ex) {
            Logger.getLogger(Ciasteczko.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
                pw.close();
        }
        
    }
    
}
