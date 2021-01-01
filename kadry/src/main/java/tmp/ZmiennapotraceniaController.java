package tmp;

import entity.Zmiennapotracenia;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;
import tmp.util.JsfUtil;
import tmp.util.JsfUtil.PersistAction;

@Named("zmiennapotraceniaController")
@SessionScoped
public class ZmiennapotraceniaController implements Serializable {

    @EJB
    private tmp.ZmiennapotraceniaFacade ejbFacade;
    private List<Zmiennapotracenia> items = null;
    private Zmiennapotracenia selected;

    public ZmiennapotraceniaController() {
    }

    public Zmiennapotracenia getSelected() {
        return selected;
    }

    public void setSelected(Zmiennapotracenia selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ZmiennapotraceniaFacade getFacade() {
        return ejbFacade;
    }

    public Zmiennapotracenia prepareCreate() {
        selected = new Zmiennapotracenia();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ZmiennapotraceniaCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ZmiennapotraceniaUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ZmiennapotraceniaDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Zmiennapotracenia> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Zmiennapotracenia getZmiennapotracenia(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Zmiennapotracenia> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Zmiennapotracenia> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Zmiennapotracenia.class)
    public static class ZmiennapotraceniaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ZmiennapotraceniaController controller = (ZmiennapotraceniaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "zmiennapotraceniaController");
            return controller.getZmiennapotracenia(getKey(value));
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
            if (object instanceof Zmiennapotracenia) {
                Zmiennapotracenia o = (Zmiennapotracenia) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Zmiennapotracenia.class.getName()});
                return null;
            }
        }

    }

}
