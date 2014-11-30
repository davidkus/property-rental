package beans;

import facades.PropertyFacade;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import models.Customer;
import models.Property;
import models.UserAccount;

@ManagedBean
@SessionScoped
public class ViewPropertiesBean extends BaseBean {

    @ManagedProperty(value="#{propertyFacade}")
    PropertyFacade propertyFacade;
    
    private static final int PAGE_SIZE = 25;
    
    private String orderBy = "rent";
    private boolean ascending = true;
    private int pageNumber = 1;
    private String location;
    private List<Property> properties;
    private Long propertyCount;
    private String status;
    
    /**
     * Creates a new instance of ViewPropertiesBean
     */
    public ViewPropertiesBean() {
    }

    public PropertyFacade getPropertyFacade() {
        return propertyFacade;
    }

    public void setPropertyFacade(PropertyFacade propertyFacade) {
        this.propertyFacade = propertyFacade;
    }
    
    public String viewPropertiesByLocation() {
        properties = propertyFacade.getByLocation(location, "rent", true, em);
        pageNumber = 1;
        orderBy = "rent";
        ascending = true;
        return "/visitor/view_properties";
    }
    
    public boolean hasPreviousPage() {
        return pageNumber != 1;
    }
    
    public boolean hasNextPage() {
        return (pageNumber * PAGE_SIZE < getPropertyCount());
    }
    
    public void nextPage() {
        if ( hasNextPage() ) {
            pageNumber++;
        }
    }
    
    public void previousPage() {
        if( hasPreviousPage() ) {
            pageNumber--;
        }
    }
    
    public void sort() {
        pageNumber = 1;
        properties = propertyFacade.getByLocation(location, orderBy, ascending, em);
    }
    
    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public boolean isAscending() {
        return ascending;
    }

    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Property> getProperties() {
        if( properties.isEmpty() ) {
            return properties;
        }
        
        int from = PAGE_SIZE * (pageNumber - 1);
        int to = from + PAGE_SIZE;
        
        if ( to >= properties.size() ) {
            to = properties.size() - 1;
        }
                
        return properties.subList(from, to);
    }
    
    public long getPropertyCount() {
        return properties.size();
    }
    
    public long getMaxPages() {
        long maxPages = (long)Math.ceil(getPropertyCount() / PAGE_SIZE);
        return maxPages;
    }
    
    public boolean inVisitingList (Property property){
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
    
    public void addToVisitingList(Property property){
        try {
            utx.begin();
            Customer customer = sessionBean.getCustomer();
            UserAccount user = customer.getUserAccount();
            List<Property> visitingList = customer.getVisitingList();
            if (!inVisitingList(property)){
                if (property.getRent()<=user.getMaxrent()){
                    visitingList.add(property);
                    em.merge(visitingList);
                }
                else {
                    status = "Property rent too high";
                }
            }
            else {
                status = "Property already in visiting list";
            }
            utx.commit();
        } catch (Exception e) {}
    }
    
}
