package facades;

import static facades.BaseFacade.performQueryList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import models.Customer;
import models.Owner;
import models.Property;
import models.UserAccount;

@ManagedBean
@SessionScoped
public class PropertyFacade extends BaseFacade {
    
    public String addToVisitingList(Property property) {        
        try {
            utx.begin();
            Customer customer = sessionBean.getCustomer();
            UserAccount account = customer.getUserAccount();
            List<Property> visitingList = customer.getVisitingList();
            if (!inVisitingList(property)){
                if ( property.getRent() <= account.getMaxrent() ){
                    visitingList.add(property);
                    em.merge(visitingList);
                }
                else {
                    return "Property rent too high";
                }
            }
            else {
                return "Property already in visiting list";
            }
            utx.commit();
        } catch (Exception e) {
            return "Unable to add property to visiting list";
        }
        return null;
    }
    
    public boolean inVisitingList(Property property){
        boolean inList = false;
        Customer customer = sessionBean.getCustomer();
        List<Property> visitingList = customer.getVisitingList();
        for (Property prop : visitingList){
            if (prop.equals(property)){
                inList = true;
            }
        }
        return inList;
    }
    
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
