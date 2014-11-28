package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import models.UserAccount;

/**
 *
 * @author david
 */
@ManagedBean
@RequestScoped
public class ViewAccountBean extends BaseBean {

    private UserAccount userAccount;
    
    /**
     * Creates a new instance of ViewAccountBean
     */
    public ViewAccountBean() {
    }
    
    public UserAccount getAccount() {
        if ( userAccount == null ) {
            userAccount = sessionBean.getUser().getUserAccount();
        }
        return userAccount;
    }
    
}
