/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package valueChangeListener;

/**
 *
 * @author Osito
 */
import embeddable.Kl;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;
 

@ManagedBean(name="KlVChL")
@RequestScoped
public class KlientValueChangeListener implements ValueChangeListener{
 
    private String linia;

    public String getLinia() {
        return linia;
    }

    public void setLinia(String linia) {
        this.linia = linia;
    }
    
    
    
    
	@Override
	public void processValueChange(ValueChangeEvent event)
			throws AbortProcessingException {
 
		//access country bean directly
		String klient = (String) FacesContext.getCurrentInstance().
			getExternalContext().getSessionMap().get("DokDAO.selDokument.rodzTrans");
 
		setLinia("korek");
 
	}
 
}
