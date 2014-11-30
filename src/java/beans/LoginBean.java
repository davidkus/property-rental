package beans;

import facades.UserAccountFacade;
import facades.UserFacade;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import models.User;
import models.UserAccount;

@RequestScoped
@ManagedBean
public class LoginBean extends BaseBean {
    
    @ManagedProperty(value="#{userAccountFacade}")
    UserAccountFacade userAccountFacade;
    
    @ManagedProperty(value="#{userFacade}")
    UserFacade userFacade;

    private String username;
    private String password;
    private String status;
    
    /**
     * Creates a new instance of LogInBean
     */
    public LoginBean() {
    }

    public UserAccountFacade getUserAccountFacade() {
        return userAccountFacade;
    }

    public void setUserAccountFacade(UserAccountFacade userAccountFacade) {
        this.userAccountFacade = userAccountFacade;
    }

    public UserFacade getUserFacade() {
        return userFacade;
    }

    public void setUserFacade(UserFacade userFacade) {
        this.userFacade = userFacade;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void login() {
        UserAccount account = userAccountFacade.findByUsername(username, em);
         if (account != null && userAccountFacade.checkPassword(account, password) && !account.isDeleted()) {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            User user = userFacade.getByAccount(account, em);
            sessionBean.setUser(user);
            try {
                context.redirect(context.getRequestContextPath() + "/index.xhtml");
            } catch (Exception e) {}
         } else {
             status="Invalid Login, Please Try again";
         }
    }
    
}
