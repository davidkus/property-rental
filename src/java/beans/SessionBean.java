package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import models.Agent;
import models.Customer;
import models.Owner;
import models.User;

@ManagedBean
@SessionScoped
public class SessionBean {
    
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
        User user = getUser();
        return user != null;
    }
    
    public boolean isAgent() {
        User user = getUser();
        
        if (user instanceof Agent) {
            return true;
        }
        
        return false;
    }
    
    public Agent getAgent() {
        User user = getUser();
        
        if (user instanceof Agent) {
            return (Agent)user;
        }
        
        return null;
    }
    
    public boolean isCustomer() {
        User user = getUser();
        
        if (user instanceof Customer) {
            return true;
        }
        
        return false;
    }
    
    public Customer getCustomer() {
        User user = getUser();
        
        if (user instanceof Customer) {
            return (Customer)user;
        }
        
        return null;
    }
    
    public boolean isOwner() {
        User user = getUser();
        
        if (user instanceof Owner) {
            return true;
        }
        
        return false;
    }
    
    public Owner getOwner() {
        User user = getUser();
        
        if (user instanceof Owner) {
            return (Owner)user;
        }
        
        return null;
    }
    
}
