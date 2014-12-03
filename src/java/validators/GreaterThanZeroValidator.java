package validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("gtZeroValidator")
public class GreaterThanZeroValidator implements Validator {
    
    /**
     *
     * @param context
     * @param component
     * @param value
     */
    @Override
    public void validate(FacesContext context,
                        UIComponent component,
                        Object value)  throws ValidatorException {
        if(value == null)
            return;
        try {
            if( value instanceof Double ) {
                double val = (double)value;
                if (val <= 0) {
                    FacesMessage message = new FacesMessage("Value must be greater than zero.");
                    throw new ValidatorException(message);
                }
            } else {
                long val = (long)value;
                if (val <= 0) {
                    FacesMessage message = new FacesMessage("Value must be greater than zero.");
                    throw new ValidatorException(message);
                }
            }
        } catch (ClassCastException e) {
            FacesMessage message = new FacesMessage("Value must be a number.");
            throw new ValidatorException(message);
        }
        
    }
    
}
