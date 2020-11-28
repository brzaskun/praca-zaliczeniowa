package com.mycompany.kadry.resources;

import session.SessionFacade;
import com.mycompany.kadry.resources.util.JsfUtil;
import com.mycompany.kadry.resources.util.JsfUtil.PersistAction;
import entity.Firma;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;
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
import view.WpisView;

@Named("firmaController")
@SessionScoped
public class FirmaController implements Serializable {

    @Inject
    private SessionFacade sessionFacade;
    private List<Firma> items = null;
    @Inject
    private Firma selected;
    @Inject
    private WpisView wpisView;

    public FirmaController() {
    }

    public Firma getSelected() {
        return selected;
    }

    public void setSelected(Firma selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private SessionFacade getFacade() {
        return sessionFacade;
    }

    public void firmaaktywuj(Firma firma) {
        wpisView.setFirma(firma);
    }
    
    public Firma prepareCreate() {
        selected = new Firma();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        FacesContext.getCurrentInstance().getViewRoot().setLocale(Locale.ENGLISH);
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/messagesbundle/Bundle").getString("FirmaCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/messagesbundle/Bundle").getString("FirmaUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/messagesbundle/Bundle").getString("FirmaDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Firma> getItems() {
        if (items == null) {
            items = getFacade().findAll(Firma.class);
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

    public Firma getFirma(java.lang.Integer id) {
        return (Firma) getFacade().findEntity(Firma.class,id);
    }

    public List<Firma> getItemsAvailableSelectMany() {
        return getFacade().findAll(Firma.class);
    }

    public List<Firma> getItemsAvailableSelectOne() {
        return getFacade().findAll(Firma.class);
    }

    @FacesConverter(forClass = Firma.class)
    public static class FirmaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            FirmaController controller = (FirmaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "firmaController");
            return controller.getFirma(getKey(value));
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
            if (object instanceof Firma) {
                Firma o = (Firma) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Firma.class.getName()});
                return null;
            }
        }

    }

}
