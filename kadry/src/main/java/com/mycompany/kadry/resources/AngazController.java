package com.mycompany.kadry.resources;

import session.SessionFacade;
import com.mycompany.kadry.resources.util.JsfUtil;
import com.mycompany.kadry.resources.util.JsfUtil.PersistAction;
import entity.Angaz;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;
import view.WpisView;

@Named("angazController")
@SessionScoped
public class AngazController implements Serializable {

    @Inject
    private SessionFacade sessionFacade;
    private List<Angaz> items = null;
    @Inject
    private Angaz selected;
    @Inject
    private WpisView wpisView;

    public AngazController() {
    }

    public Angaz getSelected() {
            return selected;
    }

    public void setSelected(Angaz selected) {
        this.selected = selected;
    }
  
    
    private SessionFacade getFacade() {
        return sessionFacade;
    }
  

    @PostConstruct
    public void init() {
        System.out.println("");
    }
    
 

    public Angaz prepareCreate() {
        selected = new Angaz();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/messagesbundle/Bundle").getString("AngazCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/messagesbundle/Bundle").getString("AngazUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/messagesbundle/Bundle").getString("AngazDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Angaz> getItems() {
        if (items == null) {
            items = getFacade().findAll(Angaz.class);
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().create(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/messagesbundle/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/messagesbundle/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Angaz getAngaz(java.lang.Integer id) {
        return (Angaz) getFacade().findEntity(Angaz.class,id);
    }

    public List<Angaz> getItemsAvailableSelectMany() {
        return getFacade().findAll(Angaz.class);
    }

    public List<Angaz> getItemsAvailableSelectOne() {
        return getFacade().findAll(Angaz.class);
    }

    @FacesConverter(forClass = Angaz.class)
    public static class AngazControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AngazController controller = (AngazController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "angazController");
            return controller.getAngaz(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Angaz) {
                Angaz o = (Angaz) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Angaz.class.getName()});
                return null;
            }
        }

    }

}
