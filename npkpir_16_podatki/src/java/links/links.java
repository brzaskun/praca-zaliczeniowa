/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package links;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Osito
 */
@ManagedBean(name="Links")
@RequestScoped
public class links {
    private String adminIndexURL="admin/adminIndex";

    public String getAdminIndexURL() {
        return adminIndexURL;
    }
    
   
}
