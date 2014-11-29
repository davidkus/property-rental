package beans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import models.Agent;
import models.Customer;
import models.Owner;
import models.User;

@ManagedBean
@SessionScoped
public class SessionBean implements Serializable {
    
    private User user;

    public SessionBean() {
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public boolean isLoggedIn() {
        return user != null;
    }
    
    public boolean isAgent() {        
        return user instanceof Agent;
    }
    
    public Agent getAgent() {
        if (user instanceof Agent) {
            return (Agent)user;
        }
        return null;
    }
    
    public boolean isCustomer() {        
        return user instanceof Customer;
    }
    
    public Customer getCustomer() {        
        if (user instanceof Customer) {
            return (Customer)user;
        }
        return null;
    }
    
    public boolean isOwner() {        
        return user instanceof Owner;
    }
    
    public Owner getOwner() {        
        if (user instanceof Owner) {
            return (Owner)user;
        }
        return null;
    }    
}
