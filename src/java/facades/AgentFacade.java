package facades;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import models.Agent;
import models.UserAccount;

@ManagedBean
@SessionScoped
public class AgentFacade extends BaseFacade {
    
    public Agent getByAccount(UserAccount account, EntityManager em) {
        Query query = em.createQuery("SELECT a FROM Agent a WHERE a.userAccount = :userAccount");
        query.setParameter("userAccount", account);
        return performQuery(Agent.class, query);
    }
    
}
