package com.mycompany.kadry.resources;

import session.SessionFacade;
import com.mycompany.kadry.resources.util.JsfUtil;
import com.mycompany.kadry.resources.util.JsfUtil.PersistAction;
import entity.Naliczenieskladnikawynagrodzenia;
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

@Named("naliczenieskladnikawynagrodzeniaController")
@SessionScoped
public class NaliczenieskladnikawynagrodzeniaController implements Serializable {

    @Inject
    private SessionFacade sessionFacade;
    private List<Naliczenieskladnikawynagrodzenia> items = null;
    private Naliczenieskladnikawynagrodzenia selected;

    public NaliczenieskladnikawynagrodzeniaController() {
    }

    public Naliczenieskladnikawynagrodzenia getSelected() {
        return selected;
    }

    public void setSelected(Naliczenieskladnikawynagrodzenia selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private SessionFacade getFacade() {
        return sessionFacade;
    }

    public Naliczenieskladnikawynagrodzenia prepareCreate() {
        selected = new Naliczenieskladnikawynagrodzenia();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/messagesbundle/Bundle").getString("NaliczenieskladnikawynagrodzeniaCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/messagesbundle/Bundle").getString("NaliczenieskladnikawynagrodzeniaUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/messagesbundle/Bundle").getString("NaliczenieskladnikawynagrodzeniaDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Naliczenieskladnikawynagrodzenia> getItems() {
        if (items == null) {
            items = getFacade().findAll(Naliczenieskladnikawynagrodzenia.class);
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

    public Naliczenieskladnikawynagrodzenia getNaliczenieskladnikawynagrodzenia(java.lang.Integer id) {
        return (Naliczenieskladnikawynagrodzenia) getFacade().findEntity(NaliczenieskladnikawynagrodzeniaController.class, id);
    }

    public List<Naliczenieskladnikawynagrodzenia> getItemsAvailableSelectMany() {
        return getFacade().findAll(Naliczenieskladnikawynagrodzenia.class);
    }

    public List<Naliczenieskladnikawynagrodzenia> getItemsAvailableSelectOne() {
        return getFacade().findAll(Naliczenieskladnikawynagrodzenia.class);
    }

    @FacesConverter(forClass = Naliczenieskladnikawynagrodzenia.class)
    public static class NaliczenieskladnikawynagrodzeniaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            NaliczenieskladnikawynagrodzeniaController controller = (NaliczenieskladnikawynagrodzeniaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "naliczenieskladnikawynagrodzeniaController");
            return controller.getNaliczenieskladnikawynagrodzenia(getKey(value));
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
            if (object instanceof Naliczenieskladnikawynagrodzenia) {
                Naliczenieskladnikawynagrodzenia o = (Naliczenieskladnikawynagrodzenia) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Naliczenieskladnikawynagrodzenia.class.getName()});
                return null;
            }
        }

    }

}
