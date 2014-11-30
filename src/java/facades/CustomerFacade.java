package facades;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import models.Customer;
import models.UserAccount;

@ManagedBean
@SessionScoped
public class CustomerFacade extends BaseFacade {
    
    public Customer getByAccount(UserAccount account, EntityManager em) {
        Query query = em.createQuery("SELECT c FROM Customer c WHERE c.userAccount = :userAccount");
        query.setParameter("userAccount", account);
        return performQuery(Customer.class, query);
    }
    
}
