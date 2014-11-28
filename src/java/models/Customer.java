package models;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Query;
import static models.BaseEntity.performQuery;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class Customer extends User {
    private static final long serialVersionUID = 1L;

    public static Customer getByAccount(UserAccount account, EntityManager em) {
        Query query = em.createQuery("SELECT c FROM Customer c WHERE c.userAccount = :userAccount");
        query.setParameter("userAccount", account);
        return performQuery(Customer.class, query);
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
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Customer[ id=" + getId() + " ]";
    }
    
}
