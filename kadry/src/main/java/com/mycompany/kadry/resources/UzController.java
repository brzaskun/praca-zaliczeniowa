package com.mycompany.kadry.resources;

import session.SessionFacade;
import com.mycompany.kadry.resources.util.JsfUtil;
import com.mycompany.kadry.resources.util.JsfUtil.PersistAction;
import entity.Uz;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

@Named("uzController")
@SessionScoped
public class UzController implements Serializable {

     @Inject
    private SessionFacade sessionFacade;
    private List<Uz> items = null;
    private Uz selected;

    public UzController() {
    }

    public Uz getSelected() {
        return selected;
    }

    public void setSelected(Uz selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private SessionFacade getFacade() {
        return sessionFacade;
    }

    public Uz prepareCreate() {
        selected = new Uz();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/messagesbundle/Bundle").getString("UzCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/messagesbundle/Bundle").getString("UzUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/messagesbundle/Bundle").getString("UzDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Uz> getItems() {
        if (items == null) {
            items = getFacade().findAll(Uz.class);
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
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

    public Uz getUz(java.lang.String id) {
        return (Uz) getFacade().findEntity(UzController.class, id);
    }

    public List<Uz> getItemsAvailableSelectMany() {
        return getFacade().findAll(Uz.class);
    }

    public List<Uz> getItemsAvailableSelectOne() {
        return getFacade().findAll(Uz.class);
    }

    @FacesConverter(forClass = Uz.class)
    public static class UzControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UzController controller = (UzController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "uzController");
            return controller.getUz(getKey(value));
        }

        java.lang.String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Uz) {
                Uz o = (Uz) object;
                return getStringKey(o.getLogin());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Uz.class.getName()});
                return null;
            }
        }

    }

}
