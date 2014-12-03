package facades;

import beans.UpdatePropertyBean;
import static facades.BaseFacade.performQueryList;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import models.Address;
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
    
    public List<Property> getByEverything(List<String> location, String type, int numberofbedrooms, int numberofbathrooms,
            int numberofotherrooms, double minrent, double maxrent, String orderBy, boolean ascending, EntityManager em) {
        String queryString = "SELECT p FROM Property p WHERE p.status = :status";
        
        // Find out what we need to search with
        if(location != null && location.size() > 0)
            queryString += " AND p.location in :location";
        if(type != null)
            queryString += " AND p.type = :type";
        if(numberofbedrooms > 0)
            queryString += " AND p.numberOfBedrooms = :numberofbedrooms";
        if(numberofbathrooms > 0)
            queryString += " AND p.numberOfBathrooms = :numberofbathrooms";
        if(numberofotherrooms > 0)
            queryString += " AND p.numberOtherRooms = :numberofotherrooms";
        if(minrent > 0)
            queryString += " AND p.rent >= :minrent";
        if(maxrent > 0)
            queryString += " AND p.rent <= :maxrent";
        
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
                
        query.setParameter("status", "Active");
        if(location != null && location.size() > 0)
            query.setParameter("location", location);
        if(type != null)
            query.setParameter("type", type);
        if(numberofbedrooms > 0)
            query.setParameter("numberofbedrooms", numberofbedrooms);
        if(numberofbathrooms > 0)
            query.setParameter("numberofbathrooms", numberofbathrooms);
        if(numberofotherrooms > 0)
            query.setParameter("numberofotherrooms", numberofotherrooms);
        if(minrent > 0)
            query.setParameter("minrent", minrent);
        if(maxrent > 0)
            query.setParameter("maxrent", maxrent);
        
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
        
        List<Property> properties = performQueryList(Property.class, query);
        
        if( properties == null ) {
            properties = new ArrayList<Property>();
        }
        
        return properties;
    }
    
    public boolean addProperty(Property property, Address address, List<models.Photo> photos) {
        try {
            utx.begin();

            em.persist(address);
            for( models.Photo photo : photos ) {
                em.persist(photo);
            }
            em.persist(property);
            
            utx.commit();
            return true;

        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean updateProperty(UpdatePropertyBean propertyUpdate, List<models.Photo> photos) {
        try {
            utx.begin();
            String queryString = "SELECT p FROM Property p WHERE p.id = :id";
            Query query = em.createQuery(queryString);
            query.setParameter("id", propertyUpdate.getId());
            Property property = performQuery(Property.class, query);
            
            for( models.Photo photo : photos ) {
                em.persist(photo);
            }
            property.setNumberOfBathrooms(propertyUpdate.getNumberofbathrooms());
            property.setNumberOfBedrooms(propertyUpdate.getNumberofbedrooms());
            property.setNumberOtherRooms(propertyUpdate.getNumberofotherrooms());
            property.setRent(propertyUpdate.getRent());
            property.setPhotos(photos);
            em.persist(property);
            
            utx.commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean deleteProperty(Property property) {
        try{
            utx.begin();
            property.setStatus("Deleted");
            em.merge(property);
            utx.commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
