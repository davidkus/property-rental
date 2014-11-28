package models;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Query;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class Agent extends User {
    private static final long serialVersionUID = 1L;
    
    public static Agent getByAccount(UserAccount account, EntityManager em) {
        Query query = em.createQuery("SELECT a FROM Agent a WHERE a.userAccount = :userAccount");
        query.setParameter("userAccount", account);
        return performQuery(Agent.class, query);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Agent)) {
            return false;
        }
        Agent other = (Agent) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Agent[ id=" + getId() + " ]";
    }
    
}
