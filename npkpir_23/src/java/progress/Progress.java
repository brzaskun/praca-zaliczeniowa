package progress;  
  
import java.io.Serializable;  
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;  

@ManagedBean(name="Progress")
@SessionScoped
public class Progress implements Serializable {  
  
    private Integer progress;  
  
    public Integer getProgress() {  
        if(progress == null) {
            progress = 0;  
        }  
        else {  
            progress = progress + (int)(Math.random() * 35);  
              
            if(progress > 100) {
                progress = 100;
            }  
        }  
          
        return progress;  
    }  
  
    public void setProgress(Integer progress) {  
        this.progress = progress;  
    }  
      
    public void onComplete() {  
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Progress Completed", "Progress Completed"));  
    }  
      
    public void cancel() {  
        progress = null;  
    }  
}  