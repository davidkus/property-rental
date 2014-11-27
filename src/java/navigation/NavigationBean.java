package navigation;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class NavigationBean {

    /**
     * Creates a new instance of NavigationBean
     */
    public NavigationBean() {
    }
    
    public String toLoginRedirect() {
        return "/login.xhtml?faces-redirect=true";
    }
    
}
