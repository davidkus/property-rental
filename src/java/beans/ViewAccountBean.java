package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import models.UserAccount;

@ManagedBean
@RequestScoped
public class ViewAccountBean extends BaseBean {

    private UserAccount userAccount;
    private String accountType;
    
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
    
    public String getAccountType() {
        if ( accountType == null ) {
            if ( sessionBean.isAgent() ) {
                accountType = "Agent";
            } else if ( sessionBean.isCustomer() ) {
                accountType = "Customer";
            } else if ( sessionBean.isOwner() ) {
                accountType = "Owner";
            }
        }
        return accountType;
    }
    
}
