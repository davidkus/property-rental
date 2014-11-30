package validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;

@FacesValidator("imageValidator")
public class ImageValidator implements Validator {
    
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
        Part file = (Part)value;
        
        if (file != null) {
            String contentType = file.getContentType();
            long size = file.getSize();

            if (!contentType.contains("image/jpg") && !contentType.contains("image/jpeg")) {
                FacesMessage message = new FacesMessage();
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                message.setSummary("File format is not valid.");
                message.setDetail("File format is not valid. Must be a JPG.");
                context.addMessage("", message);
                throw new ValidatorException(message);
            }

            if (size > 5000000) {
                FacesMessage message = new FacesMessage();
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                message.setSummary("File size is too large.");
                message.setDetail("File size is too large. Must be less than 5MB.");
                context.addMessage("", message);
                throw new ValidatorException(message);
            }
        }
        
    }
    
}
