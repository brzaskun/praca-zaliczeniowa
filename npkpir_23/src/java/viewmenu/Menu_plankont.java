package viewmenu;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class Menu_plankont implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private static final int FILE_COUNT = 10;
    private final boolean[] files = new boolean[FILE_COUNT];
    private final String[] fileStyles = new String[FILE_COUNT];
    
    private final String ADRES = "/ksiegowaFK/ksiegowaFKPlanKont.xhtml?faces-redirect=true";
    private final String ALERT = "color: red;font-weight: 900;";

    public Menu_plankont() {
        files[0] = true;
        fileStyles[0] = ALERT;
    }

    public String updateFile(int index, boolean war) {
        reset();
        files[index] = war;
        fileStyles[index] = ALERT;
        return ADRES;
    }

    private void reset() {
        for (int i = 0; i < FILE_COUNT; i++) {
            files[i] = false;
            fileStyles[i] = "";
        }
    }

    // Getters and setters for files and fileStyles
    public boolean isFile(int index) {
        return files[index];
    }

    public void setFile(int index, boolean value) {
        files[index] = value;
    }

    public String getFileStyle(int index) {
        return fileStyles[index];
    }

    public void setFileStyle(int index, String style) {
        fileStyles[index] = style;
    }
}
