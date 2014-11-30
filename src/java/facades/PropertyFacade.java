package facades;

import static facades.BaseFacade.performQueryList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import models.Owner;
import models.Property;

@ManagedBean
@SessionScoped
public class PropertyFacade extends BaseFacade {
    
    public List<Property> getByLocation(String location, String orderBy, boolean ascending, EntityManager em) {
        String queryString = "SELECT p FROM Property p WHERE p.location = :location AND p.status = :status";
        
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
        
        query.setParameter("location", location);
        query.setParameter("status", "Active");
        
        return performQueryList(Property.class, query);
    }
    
    public List<Property> getOwnerProperties(Owner owner, String orderBy, boolean ascending, EntityManager em) {
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
        
        query.setParameter("owner", owner);
        
        return performQueryList(Property.class, query);
    }
    
}
