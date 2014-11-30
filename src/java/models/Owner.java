package models;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Query;
import static models.BaseEntity.performQuery;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class Owner extends User {
    private static final long serialVersionUID = 1L;
    
    @OneToMany(mappedBy = "owner")
    private List<Property> properties;

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }
    
    public List<Property> getProperties(String orderBy, boolean ascending, EntityManager em) {
        String queryString = "SELECT p FROM Property p WHERE p.owner = :owner";
        
        if (orderBy.equals("bedrooms")) {
            queryString += "  ORDER BY p.numberOfBedrooms ";
        } else if (orderBy.equals("bathrooms")) {
            queryString += "  ORDER BY p.numberOfBathrooms ";
        } else if (orderBy.equals("otherrooms")) {
            queryString += "  ORDER BY p.numberOtherRooms ";
        } else {
            queryString += "  ORDER BY p.rent ";
        }
        
        if (ascending) {
            queryString += "ASC";
        } else {
            queryString += "DESC";
        }
        
        Query query = em.createQuery(queryString);
        
        query.setParameter("owner", this);
        
        return performQueryList(Property.class, query);
    }
    
    public static Owner getByAccount(UserAccount account, EntityManager em) {
        Query query = em.createQuery("SELECT o FROM Owner o WHERE o.userAccount = :userAccount");
        query.setParameter("userAccount", account);
        return performQuery(Owner.class, query);
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
        if (!(object instanceof Owner)) {
            return false;
        }
        Owner other = (Owner) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Owner[ id=" + getId() + " ]";
    }
    
}
