package beans;

import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import models.UserAccount;

@ManagedBean
@RequestScoped
public class LoginBean {

    @PersistenceContext(unitName = "property-rentalPU")
    private EntityManager em;

    @Resource
    private UserTransaction utx;

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
         if (account != null) {
            if (account.checkPassword(password)) {
                //login ok - set user in session context
                HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
                session.setAttribute("user", account.getUser());
                status="Login Successful - " + "Welcome " + account.getFirstname(); 
            } else {
               status="Invalid Login, Please Try again"; 
            }
         } else {
             status="Invalid Login, Please Try again";
         }
    }
    
    public static String redirectToLogin() {
        return "/login.xhtml?faces-redirect=true";
    }
    
}
