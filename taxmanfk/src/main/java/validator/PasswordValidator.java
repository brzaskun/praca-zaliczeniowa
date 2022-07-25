/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Osito
 */
public class PasswordValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object toValidate) {
        boolean isValid = true;
        String value = null;

        if ((context == null) || (component == null)) {
            throw new NullPointerException();
        }
        if (!(component instanceof UIInput)) {
            return;
        }
        if (null == toValidate) {
            return;
        }
        value = toValidate.toString();
        int atIndex = value.indexOf('@');
        if (atIndex < 0) {
            isValid = false;
        } else if (value.lastIndexOf('.') < atIndex) {
            isValid = false;
        }
        if (!isValid) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hasła nie pasuja. Sprawdź.", "");
            throw new ValidatorException(msg);
        }
    }

}
