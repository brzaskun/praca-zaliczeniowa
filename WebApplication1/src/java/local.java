
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class local implements Serializable{
    public  void lolo() {
        try {
            Properties prop = new Properties();
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            //prop.load(externalContext.getResourceAsStream("/WEB-INF/file.properties"));
            FacesContext contex = FacesContext.getCurrentInstance();
            String attributes = contex.getExternalContext().getApplicationContextPath();
            Properties p = System.getProperties();
            String path = p.getProperty("user.dir");
            String pathtmp = p.getProperty("java.io.tmpdir");
            String sRootPath = new File("").getAbsolutePath();
            new File(sRootPath+"\\dir").mkdir();
            File f = new File(sRootPath+"\\dir\\lolo.txt");
            String text = "Hello world";
            BufferedWriter output = null;
            try {
                File file = new File(pathtmp+"example.txt");
                file.deleteOnExit();
                output = new BufferedWriter(new FileWriter(file));
                output.write(text);
            } catch ( IOException e ) {
                e.printStackTrace();
            } finally {
                if ( output != null ) output.close();
            }
           //File p = path[0];
            System.out.println("d");
        } catch (Exception ex) {
            Logger.getLogger(local.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
