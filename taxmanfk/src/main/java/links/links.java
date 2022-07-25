/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package links;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;


/**
 *
 * @author Osito
 */
@Named
@RequestScoped
public class links {
    private String adminIndexURL="admin/adminIndex";

    public String getAdminIndexURL() {
        return adminIndexURL;
    }
    
   
}
