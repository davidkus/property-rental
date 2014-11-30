package facades;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import models.Agent;
import models.Customer;
import models.Owner;
import models.User;
import models.UserAccount;

@ManagedBean
@SessionScoped
public class UserFacade extends BaseFacade {
    
    @ManagedProperty(value="#{customerFacade}")
    CustomerFacade customerFacade;
    
    @ManagedProperty(value="#{ownerFacade}")
    OwnerFacade ownerFacade;
    
    @ManagedProperty(value="#{agentFacade}")
    AgentFacade agentFacade;

    public CustomerFacade getCustomerFacade() {
        return customerFacade;
    }

    public void setCustomerFacade(CustomerFacade customerFacade) {
        this.customerFacade = customerFacade;
    }

    public OwnerFacade getOwnerFacade() {
        return ownerFacade;
    }

    public void setOwnerFacade(OwnerFacade ownerFacade) {
        this.ownerFacade = ownerFacade;
    }

    public AgentFacade getAgentFacade() {
        return agentFacade;
    }

    public void setAgentFacade(AgentFacade agentFacade) {
        this.agentFacade = agentFacade;
    }
    
    public User getByAccount(UserAccount userAccount, EntityManager em) {
        Agent agent = agentFacade.getByAccount(userAccount, em);
        if( agent != null ) {
            return agent;
        }
        
        Customer customer = customerFacade.getByAccount(userAccount, em);
        if( customer != null ) {
            return customer;
        }
        
        Owner owner = ownerFacade.getByAccount(userAccount, em);
        
        return owner;
    }
    
}
