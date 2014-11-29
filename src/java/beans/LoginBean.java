package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import models.User;
import models.UserAccount;

@RequestScoped
@ManagedBean
public class LoginBean extends BaseBean {

    private String username;
    private String password;
    private String status;
    
    /**
     * Creates a new instance of LogInBean
     */
    public LoginBean() {
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
        UserAccount account = UserAccount.findByUsername(username, em);
         if (account != null && account.checkPassword(password)) {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            User user = account.getUser(em);
            sessionBean.setUser(user);
            try {
                context.redirect(context.getRequestContextPath() + "/index.xhtml");
            } catch (Exception e) {}
         } else {
             status="Invalid Login, Please Try again";
         }
    }
    
}
