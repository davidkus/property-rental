package validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("postalCodeValidator")
public class PostalCodeValidator implements Validator {
    
    private static final String POSTAL_CODE_REGEX =
            "^[a-zA-Z]\\d[a-zA-Z]( )?\\d[a-zA-Z]\\d$";
    
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
        try {
            String postalCode = (String)value;
            
            if (!postalCode.matches(POSTAL_CODE_REGEX)) {
                FacesMessage message = new FacesMessage("Must be a valid postal code of the form A1X 1A1.");
                throw new ValidatorException(message);
            }
            
        } catch (ClassCastException e) {
            FacesMessage message = new FacesMessage("Value must be a a String.");
            throw new ValidatorException(message);
        }
        
    }
    
}
